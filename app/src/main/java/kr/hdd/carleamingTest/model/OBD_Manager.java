package kr.hdd.carleamingTest.model;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.SupportedPidUtill;

public class OBD_Manager {

	private final String TAG = OBD_Manager.class.getSimpleName();
	
	public interface OBD_TimeOut{
		void obdTimeOut();
	}
	
	private final int TIME_OUT = 3000;
	private final int SUPPORT_TIME_OUT_CHECK = 100;
	private final int BASE_TIME_OUT_CHECK = 200;
	private final int All_BASE_TIME_OUT_CHECK = 300;
	
	private String[] mOBD_SupportPidList = {"0100", "0120", "0140", "0160", "0180", "03"};
	private String[] mOBD_BasePidList = {"010C","010D","0104","0105"};
	private String[] mOBD_AllPidList = {
			"0100","0101","0102","0103", "0104","0105","0106","0107","0108","0109",
			"010A","010B","010C","010D", "010E","010F","0110","0111","0112","0113",
			"0114","0115","0116","0117", "0118","0119","011A","011B","011C","011D",
			"011E","011F","0120","0121", "0122","0123","0124","0125","0126","0127",
			"0128","0129","012A","012B", "012C","012D","012E","012F","0130","0131",
			"0132","0133","0134","0135", "0136","0137","0138","0139","013A","013B",
			"013C","013D","013E","013F", "0140","0141","0142","0143","0144","0145",
			"0146","0147","0148","0149", "014A","014B","014C","014D","014E","014F",
			"0150","0151","0152","0153", "0154","0155","0156","0157","0158","0159",
			"015A","015B","015C","015D", "015E","015F","0160","0161","0162","0163",
			"0164","0165","0166","0167", "0168","0169","016A","016B","016C","016D",
			"016E","016F","0170","0171", "0172","0173","0174","0175","0176","0177",
			"0178","0179","017A","017B", "017C","017D","017E","017F","0180","0181",
			"0182","0183","0184","0185", "0186","0187","03"
	};
	private String[] mCheckResSupportAllIds = {
			"4100","4141","4102","4103", "4104","4105","4106","4107","4108","4109",
			"410A","410B","410C","410D", "410E","410F","4110","4111","4112","4113",
			"4114","4115","4116","4117", "4118","4119","411A","411B","411C","411D",
			"411E","411F","4120","4121", "4122","4123","4124","4125","4126","4127",
			"4128","4129","412A","412B", "412C","412D","412E","412F","4130","4131",
			"4132","4133","4134","4135", "4136","4137","4138","4139","413A","413B",
			"413C","413D","413E","413F", "4140","4141","4142","4143","4144","4145",
			"4146","4147","4148","4149", "414A","414B","414C","414D","414E","414F",
			"4150","4151","4152","4153", "4154","4155","4156","4157","4158","4159",
			"415A","415B","415C","415D", "415E","415F","4160","4161","4162","4163",
			"4164","4165","4166","4167", "4168","4169","416A","416B","416C","416D",
			"416E","416F","4170","4171", "4172","4173","4174","4175","4176","4177",
			"4178","4179","417A","417B", "417C","417D","417E","417F","4180","4181",
			"4182","4183","4184","4185", "4186","4187","0300E0:43"
	};
	private String[] mCheckResSupportIds = {"4100", "4120", "4140", "4160", "4180"};
	private int[] mCheckResValInfoAll = {
			8, 8, 4, 4, 2, 2, 2, 2, 2, 2,
			2, 2, 4, 2, 2, 2, 4, 2, 2, 2,
			4, 4, 4, 4, 4, 4, 4, 4, 2, 2,
			2, 4, 8, 4, 4, 4, 8, 8, 8, 8,
			8, 8, 8, 8, 2, 2, 2, 2, 2, 4,
			4, 2, 8, 8, 8, 8, 8, 8, 8, 8,
			4, 4, 4, 4, 8, 8, 4, 4, 4, 2,
			2, 2, 2, 2, 2, 2, 2, 4, 4, 8,
			8, 2, 2, 4, 4, 4, 4, 4, 4, 4,
			2, 2, 2, 4, 4, 2, 8, 2, 2, 4,
			10, 4, 10, 6, 14, 14, 10, 10, 10, 12,
			10, 6, 18, 10, 10, 10, 10, 14, 14, 10,
			18, 18, 14, 14, 18, 2, 2, 26, 7, 42,
			42, 10, 0, 0, 0, 0, 2
};
	
	private String[] mCheckResIds = {"410C", "410D", "4104", "4105", "0300E0:43"};
	private int[] mCheckResValInfo = {4, 2, 2, 2, 2};

	private int mResultValue = -1;
	private int mBasePos = 0, mBasePrePos = 0, mBaseCome = 0;  //주요 보여지는 data
	private int mSupportPos = 0, mSupportPrePos = 0;  //처음 한번만 태움
	private int mAllPos = 0, mAllPrePos = 0;  // 전체 데이터 (수집용)
	private int mTimeTemp = 0, SpeedTemp = 0, mCool = 0;
	private double mTotalCool = 0, mTempCool = 0;
	
	private boolean isSupportPIDFinish = false;
	private String mNowPid = null;
	private String mResultType = null;
	
	private OBD_TimeOut mOBD_TimeOut = null;
	private Handler mRequestTimeoutHandler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			String pid = "";
			int what = -1;
			switch(msg.what){
			case BASE_TIME_OUT_CHECK : 
				pid = mOBD_BasePidList[mBasePrePos];
				what = BASE_TIME_OUT_CHECK;
				break;
				
			case All_BASE_TIME_OUT_CHECK : 
				pid = mOBD_AllPidList[mBasePrePos];
				what = BASE_TIME_OUT_CHECK;
				break;
				
			case SUPPORT_TIME_OUT_CHECK : 
				pid = mOBD_SupportPidList[mSupportPrePos];
				what = BASE_TIME_OUT_CHECK;
				break;
			}
			
			if(mNowPid != null && mNowPid.equals(pid)){
				CarLLog.i(TAG, "obd time out");
				if(mOBD_TimeOut != null) {
					CarLLog.i(TAG, "mOBD_TimeOut is not null");
					mOBD_TimeOut.obdTimeOut();
				} else {
					CarLLog.i(TAG, "mOBD_TimeOut is null");
				}
			}
			
			return false;
		}
	});
	
	public String getRequestPID(){
		if(mRequestTimeoutHandler != null) mRequestTimeoutHandler.removeMessages(SUPPORT_TIME_OUT_CHECK);
		if(mRequestTimeoutHandler != null) mRequestTimeoutHandler.removeMessages(BASE_TIME_OUT_CHECK);
		if(mRequestTimeoutHandler != null) mRequestTimeoutHandler.removeMessages(All_BASE_TIME_OUT_CHECK);
		
//		Log.w(TAG, "isSupportPIDFinish: "+ isSupportPIDFinish);
		if(!isSupportPIDFinish){
			String pid = mOBD_SupportPidList[mSupportPos];
			mSupportPrePos = mSupportPos;
			mSupportPos = mSupportPos >= mOBD_SupportPidList.length ? 0 : mSupportPos+1;
			
			mNowPid = pid;
			if(mNowPid != null && !mNowPid.equals("")) checkTimeout(SUPPORT_TIME_OUT_CHECK);

//			CarLLog.i(TAG, "mNowPid:"+mNowPid+", mSupportPos:"+mSupportPos+", mOBD_SupportPidList.length:"+mOBD_SupportPidList.length);
			if(mSupportPos >= mOBD_SupportPidList.length){
				isSupportPIDFinish = true;
			}
			
			return pid+(pid != null && !pid.equals("") ? "\r" : "");
		} else {
			String pid = ""; 
			
			if((mBaseCome % 5) == 0){
				pid = mOBD_AllPidList[mAllPos];
				mAllPrePos = mAllPos;
				mAllPos = mAllPos >= mOBD_AllPidList.length-1 ? 0 : mAllPos+1;
				
			} else {
				pid = mOBD_BasePidList[mBasePos];
				mBasePrePos = mBasePos;
				mBasePos = mBasePos >= mOBD_BasePidList.length-1 ? 0 : mBasePos+1;
			}
			
			mBaseCome = mBaseCome >= 101 ? 0 : mBaseCome+1;
			
			mNowPid = pid;
			if(mNowPid != null && !mNowPid.equals("")) checkTimeout(BASE_TIME_OUT_CHECK);
			
			return pid+(pid != null && !pid.equals("") ? "\r" : "");
		}
	}
	
	private void checkTimeout(int what){
		if(mRequestTimeoutHandler != null) {
			mRequestTimeoutHandler.sendEmptyMessageDelayed(what, TIME_OUT);
		}
	}
	
	public void setObdTimeOutLinstener(OBD_TimeOut obdTimeOut){
		this.mOBD_TimeOut = obdTimeOut;
	}
	
	public void cancel(){
		if(mRequestTimeoutHandler != null) {
			mRequestTimeoutHandler.removeMessages(TIME_OUT);
			mRequestTimeoutHandler = null;
		}
	}
	
	private String dataReplace(String data){
		data = data.replace("null", "");
		data = data.replace("\n", "");
		data = data.replace("\r", "");
		data = data.replace(">", "");
		data = data.replace(" ", "");
		 
		if(data.length() >= 4){
//			CarLLog.i(TAG, "messa:"+data);
			String etcErrorCheck = data.substring(0, 2);
			if(!etcErrorCheck.equals("41") && !etcErrorCheck.equals("[@")){
				String msgCheck = data.substring(4, data.length());
				if(msgCheck.length() > 2 && msgCheck.substring(0, 2).equals("41")){
					data = data.substring(4, data.length());
				}
			}
//			CarLLog.i(TAG, "messa: "+data);
		}
		
		return data;
	}
	
	public boolean getOBDConverSupportData(String obdData){
		if(obdData == null){
//			CarLLog.i(TAG, "data is null");
			return false;
		}

		if(mResultType != null) mResultType = null;
		if(mResultValue > -1) mResultValue = -1;
		
		String data = dataReplace(obdData);
		
		boolean isSupportData = false;
		for(int i=0; i<mCheckResSupportIds.length; i++){
			String resId = mCheckResSupportIds[i];
			int idx = data.indexOf(resId);
			int length = 8;

			if(idx > -1){
				isSupportData = true;
				int start = idx+resId.length();
				int end = (start+length);
				if(data.length() >= end){
					String convertData = data.substring(start, end);
					
					String[] strBuf = null;
					if(data.indexOf("4100") == 0){
						strBuf = MainApplication.PIDS00;
					} else if(data.indexOf("4120") == 0){
						strBuf = MainApplication.PIDS20;
					} else if(data.indexOf("4140") == 0){
						strBuf = MainApplication.PIDS40;
					} else if(data.indexOf("4160") == 0){
						strBuf = MainApplication.PIDS60;
					} else if(data.indexOf("4180") == 0){
						strBuf = MainApplication.PIDS80;
					} else {
						strBuf = null;
					}

					try {
						if(strBuf != null){
							StringBuilder flags = new StringBuilder();
							for(int j = 0; j < convertData.length(); j++ ){
								String tmp; 
								int val = 0;

								tmp = convertData.substring(j, j+1);
								val = Integer.valueOf(tmp, 16).intValue();

								flags.append((val & 0x08) == 0x08 ? "1" : "0");
								flags.append((val  & 0x04) == 0x04 ? "1" : "0");
								flags.append((val  & 0x02) == 0x02 ? "1" : "0");
								flags.append((val  & 0x01) == 0x01 ? "1" : "0");
							}

							StringBuilder supportedPID = new StringBuilder();
							supportedPID.append("SupportedPID: ");

							StringBuilder unSupportedPID = new StringBuilder();
							unSupportedPID.append("UnSupportedPID: ");

							for(int j = 0; j < flags.length(); j++){
								if(flags.charAt(j) == '1'){
									supportedPID.append(strBuf[j] + " ");
								}else{
									unSupportedPID.append(" "+ strBuf[j] + " ");
								}
							}

							supportedPID.append("\n");

//							CarLLog.e(TAG,"KCH SUPPORT resId : "+resId);
//							CarLLog.e(TAG,"KCH SUPPORT supportedPID.toString() : "+supportedPID.toString());
//							CarLLog.e(TAG,"KCH SUPPORT unSupportedPID.toString() : "+unSupportedPID.toString());
							SupportedPidUtill.getInstance().setSupportedPid(resId, convertData);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return isSupportData;
	}
	
	public void getOBDConvertData(String obdData){
		if(obdData == null){
			return;
		}

//		Log.w(TAG, "obdData: "+ obdData);

		if(obdData.contains("03") && obdData.contains("00E"))
		{
			if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03) != null){
				SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).clear();
			}
			
			ArrayList<String> troubleList= getTroubleCode(obdData);
			for(int i= 0; i< troubleList.size(); i++) {
//				Log.w(TAG, "troubleList: "+ troubleList.get(i));
//				getOBDFormula("0300E0:43", troubleList.get(i));					
				SupportedPidUtill.getInstance().setSupportedPid(MainApplication.LIST03, troubleList.get(i));
			}
		}
		else
		{
			String data = dataReplace(obdData);

			for(int i=0; i<mCheckResSupportAllIds.length; i++){
				String resId = mCheckResSupportAllIds[i];
				int idx = data.indexOf(resId);
				int length = mCheckResValInfoAll[i];

				if(idx > -1){
					int start = idx+resId.length();
					int end = (start+length);
					if(data.length() >= end){
						String convertData = data.substring(start, end);

						int reusltVal = getOBDFormula(resId, convertData);
					}
				}
			}
		}
	}
	
	private ArrayList<String> getTroubleCode(String convertData)
	{
		ArrayList<String> dataList= new ArrayList<String>();
		ArrayList<String> troubleList= new ArrayList<String>();
		String data= convertData;

		data = data.replace("null", "");
		data = data.replace("\n", "");
		data = data.replace("\r", "");
		data = data.replace(">", "");
		data = data.replace(" ", "");

		if(data.startsWith("7E")) {	//데이터 오류
			Log.w(TAG, "7E: "+ data);
		}
		//필요 없는 값 제거
		if(data.startsWith("03"))
			data = data.substring(2, data.length());
		if(data.startsWith("00E"))
			data = data.substring(3, data.length());
		
		for(int i= 0; i< data.length(); i++) {
			int colonIndex= data.indexOf(":");
//			Log.w(TAG, "colonIndex: "+ colonIndex);
			if(colonIndex>= 0)
			{
				if(colonIndex== 0)
					data = data.replace(":", "");
				else {
					data = data.substring(0, colonIndex- 1)+ data.substring(colonIndex+ 1, data.length());
					
//					Log.w(TAG, "data: "+ data);
				}
			}
			else
				break;
		}

		for(int i= 0; i< data.length(); i= i+ 4) {
//			Log.w(TAG, "i+ 4: "+ data.substring(i, i+ 4));
			dataList.add(data.substring(i, i+ 4));
		}
		
		for(int i= 0; i< dataList.size(); i++) {
//			Log.w(TAG, "dataList: "+ dataList.get(i));
			int cnt= 0;
			if(dataList.get(i).startsWith("43")) {
				cnt= Integer.parseInt(dataList.get(i).substring(2, 4));
//				Log.w(TAG, "cnt: "+ cnt);
				
				for(int j= 0; j< cnt; j++) {
					String troble= dataList.get(i+ j+ 1);
					troubleList.add(getTroubleCase(troble));
				}
				i= i+ cnt;
			}
		}
		
		return troubleList;
	}
	
	private String getTroubleCase(String code) {
		if(code.startsWith("0")) code= "P0"+ code.substring(1, 4);
		else if(code.startsWith("1")) code= "P1"+ code.substring(1, 4);
		else if(code.startsWith("2")) code= "P2"+ code.substring(1, 4);
		else if(code.startsWith("3")) code= "P3"+ code.substring(1, 4);
		else if(code.startsWith("4")) code= "C0"+ code.substring(1, 4);
		else if(code.startsWith("5")) code= "C1"+ code.substring(1, 4);
		else if(code.startsWith("6")) code= "C2"+ code.substring(1, 4);
		else if(code.startsWith("7")) code= "C3"+ code.substring(1, 4);
		else if(code.startsWith("8")) code= "B0"+ code.substring(1, 4);
		else if(code.startsWith("9")) code= "B1"+ code.substring(1, 4);
		else if(code.startsWith("A")) code= "B2"+ code.substring(1, 4);
		else if(code.startsWith("B")) code= "B3"+ code.substring(1, 4);
		else if(code.startsWith("C")) code= "U0"+ code.substring(1, 4);
		else if(code.startsWith("D")) code= "U1"+ code.substring(1, 4);
		else if(code.startsWith("E")) code= "U2"+ code.substring(1, 4);
		else if(code.startsWith("F")) code= "U3"+ code.substring(1, 4);

		Log.w(TAG, "code: "+ code);
		
		return code;
	}
	
	private int getOBDFormula(String resId, String convertData){
//		Log.w(TAG, "7777 resId: "+ resId);
//		Log.w(TAG, "7777 convertData: "+ convertData);
		
		if(convertData.equals("")){
			// 16진수 값
			SupportedPidUtill.getInstance().setSupportedPid(resId, String.valueOf(convertData));
			return mResultValue;
		}
		
		int radix = 16;
		int converDataA = 0, converDataB = 0;
//		int RpmTemp = 0;
		
		if(resId.equals("410C")){ //rpm
			
			converDataA = Integer.parseInt(convertData.substring(0, 2), radix);
			converDataB = Integer.parseInt(convertData.substring(2, convertData.length()), radix);
			
			mResultValue = ((converDataA*256)+converDataB)/4;
			mResultType = MainApplication.RECIVER.RPM_RECIVER;

//			RpmTemp = mResultValue;
			MainApplication.RPM_TEMP = mResultValue;
			
		} else if(resId.equals("410D")) { //speed
			
			mResultValue = Integer.parseInt(convertData, radix);
			mResultType = MainApplication.RECIVER.SPEED_RECIVER;
			
		} else if(resId.equals("4104")) { //engine load

			converDataA = Integer.parseInt(convertData, radix);
			mResultValue = converDataA;
			mResultType = MainApplication.RECIVER.ENGINE_LOAD_RECIVER;
			
		} else if(resId.equals("4105")) { //coolant

			converDataA = Integer.parseInt(convertData, radix);
			mResultValue = converDataA-40;
			mResultType = null;
			
			
			mTotalCool = (double)mResultValue + mTempCool / 2;
			
			SupportedPidUtill.getInstance().setSupportedPid(MainApplication.LIST05, String.format("%.1f",  mTotalCool));
			
			mTempCool = (double)mResultValue;
			MainApplication.setAuto_coolant(mTempCool);
		}
		else if(resId.equals("0300E0:43")){ //	
//			mResultValue = Integer.parseInt(convertData, radix);
//			mResultType = null;
//			Log.w(TAG, "mResultValue: "+ mResultValue);
			Log.w(TAG, "convertData: "+ convertData);
			
			if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03) != null){
				SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).clear();
			}
			
			SupportedPidUtill.getInstance().setSupportedPid(MainApplication.LIST03, convertData);
			
		} else {
			mResultType = null;
			
		}

		// 16진수 값
		SupportedPidUtill.getInstance().setSupportedPid(resId, String.valueOf(convertData));
		return mResultValue;
	}

//	private String getTroubleCode(int error){
//		String StrError = String.valueOf(error);
//		
//		if(StrError.length() == 1 ){
//			return "P00" + StrError;
//		} else if(StrError.length() == 2){
//			return "P0" + StrError;
//		} else {
//			return "P" + StrError;
//		}
//	}
	
	public int getResultValue() {
		return mResultValue;
	}

	public void setResultValue(int mResultValue) {
		this.mResultValue = mResultValue;
	}

	public String getResultType() {
		return mResultType;
	}

	public void setResultType(String mResultType) {
		this.mResultType = mResultType;
	}
	
}
