package kr.hdd.carleamingTest.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.common.popup.PopupEnginCheck;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.SupportedPidUtill;

/**
 * 차량 자동 진단 페이지
 * */
public class AutoDiagnosisFragment extends Fragment implements OnClickListener{
	private String TAG= "AutoDiagnosisFragment";

	private Context mContext = null;
	
	private TextView mMileageText = null; //주행거리
	private TextView mTimeText1 = null;  //시간
	private TextView mTimeText2 = null;  //시간
	private TextView mTimeText3 = null;  //시간
	private TextView mOilText = null; //유류비
	private TextView mCoolantText = null;  //냉각수
	private TextView mFuelConsumptionText = null; //총소모량

	private Button mBtnEngineCheck = null;  //엔진체크 ok
	
	//주행 거리
	private double mDBMileage = 0;
//	private double mileage = 0;
	//주행시간
//	private double time = 0;
	private int mDBTime = 0;
	//유류비
	private double mDBAllFulePri = 0;
//	private double oilSum = 0;
	//냉각수
	private double mDBCool = 0;
//	private double mCool = 0;
//	private double mCoolListSize = 0;
	private double mTotalCool = 0;
	//총 소모량
	private double mDBAllFuleEff = 0;
	//엔진체크
	private double mError = 0;
	private double mTotalError = 0;
	
	private ArrayList<String> mArrError = null;
//	private AutoDBAsycTask mAllDataDBAsycTask = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View view = inflater.inflate(R.layout.auto_diagnosis_layout, null);

		mMileageText = (TextView) view.findViewById(R.id.diagno_mileage_text);  //주행거리
		mTimeText1 = (TextView) view.findViewById(R.id.diagno_time_text1);  //주행시간
		mTimeText2 = (TextView) view.findViewById(R.id.diagno_time_text2);  //주행시간
		mTimeText3 = (TextView) view.findViewById(R.id.diagno_time_text3);  //주행시간
		mOilText = (TextView) view.findViewById(R.id.diagno_oil_text);  //유류비
		mCoolantText = (TextView) view.findViewById(R.id.diagno_coolant_text);  //냉각수
		mFuelConsumptionText = (TextView) view.findViewById(R.id.diagno_fuel_consumption_text);  //총소모량		
		
		mBtnEngineCheck = (Button) view.findViewById(R.id.diagno_btn_ok);
		
//		mAllDataDBAsycTask = new AutoDBAsycTask(mContext, mStopAsyncTaskHandler);
//		mAllDataDBAsycTask.execute();
		setData();
		
		mBtnEngineCheck.setOnClickListener(this);
		
		mArrError = new ArrayList<String>();
		
		if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03) != null){
			
//			Log.w(TAG, "LIST03 size: "+ SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).size());
			
			for(int i=0; i<SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).size(); i++){
//				Log.w(TAG, "LIST03 toString: "+ SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).get(i).toString());
				if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).get(i).toString().equalsIgnoreCase("null") 
						&& SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).get(i).toString().equalsIgnoreCase("no data")){
					mBtnEngineCheck.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_diagno_ok));  
				} else {
					mBtnEngineCheck.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_diagno_error));  //에러가 나면 바꾸기 위해 
				}
			}
			
		} else {
			mArrError.add("00");
			mBtnEngineCheck.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_diagno_ok));  
		}

		UiHandler.sendEmptyMessage(0);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.diagno_btn_ok :
			
			PopupEnginCheck popup = new PopupEnginCheck(getActivity());
			popup.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
			popup.show();
			
//			mBtnEngineCheck.setBackground(getActivity().getResources().getDrawable(R.drawable.btn_diagno_error));  //에러가 나면 바꾸기 위해 
			
			break;

		}
	}
	
	private Handler UiHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {

			//주행거리O
//			mileage = mDBMileage;			
			mMileageText.setText(String.format("%.1f", mDBMileage));
			
			//시간
			int time1 = 0;
			int time2 = 0;
			int time3 = 0;
//			int DBTime = MainApplication.getAuto_time();
			
			if(mDBTime > 0){
				int tTimee = mDBTime;
				tTimee = mDBTime / 60;
				time3 = mDBTime % 60;

				time1 = tTimee / 60;
				time2 = tTimee % 60;
			}
			
			mTimeText1.setText(String.valueOf(time1) + ":");
			if(time2>= 10)
				mTimeText2.setText(String.valueOf(time2) + ":");
			else
				mTimeText2.setText("0"+ String.valueOf(time2) + ":");
			if(time3>= 10)
				mTimeText3.setText(String.valueOf(time3));
			else
				mTimeText3.setText("0"+ String.valueOf(time3));
//			MainApplication.setAuto_time((int)time);
			
			//총 소모량
			mFuelConsumptionText.setText(String.format("%.2f", mDBAllFuleEff));
			
			
			//유류비
//			oilSum = mDBAllFuleEff * Utils.getPrice(MainApplication.getOil_type());
			mOilText.setText(String.format("%.0f", mDBAllFulePri));
			
			//냉각수
			if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST05) != null){
//				mCoolListSize = SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST05).size();
				
//				for(int i=0; i<mCoolListSize; i++){
//					
//					int integerCool = Integer.parseInt(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST05).get(i), 16);
//					
//					mCool += (double)integerCool;
//				}
				
//				mTotalCool = mCool / SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST05).size()+1;
				mTotalCool = Double.parseDouble(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST05).get(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST05).size()-1));
				
			} else{
				mTotalCool = mDBCool;
			}
			
//			mTotalCool = mTotalCool + mDBCool / 2;
			mCoolantText.setText(String.format("%.1f", mTotalCool));
			
			return false;
		}
	});
		
//	@Override
//	public void onPause() {
//		super.onPause();
//		CarLLog.v("AutoDiagnosisFragment","onPause");
//		
////		if(MainApplication.IsBtFlag){
//			
//			new AllDataDBsetting(getActivity(), MainApplication.getmUserId(), MainApplication.getmUserCarName(), MainApplication.getCar_vehicle_number(), 
//					MainApplication.getCar_number(),  MainApplication.getCar_model(), MainApplication.getOil_type(), 
//					MainApplication.getMileage(), MainApplication.getAuto_time(),  MainApplication.getOil(), MainApplication.getAuto_coolant(), 
//					MainApplication.getFuel_consumption(), MainApplication.getStart_date(), MainApplication.getFuel_efficienct(), 
//					MainApplication.getDrive_time(), MainApplication.getDrive_down_speed(), MainApplication.getDrive_up_speed(), 
//					MainApplication.getIdle(), MainApplication.getDrive(), MainApplication.getEngine_oil(), MainApplication.getTire(), 
//					MainApplication.getBrake_lining(), MainApplication.getAircon_filter(), MainApplication.getWiper(), 
//					MainApplication.getEquip_coolant(), MainApplication.getTransmission_oil(), MainApplication.getBattery(), 
//					Utils.Date(), MainApplication.getEngine_oil_day(), MainApplication.getTire_day(), MainApplication.getBrake_lining_day(), 
//					MainApplication.getAircon_filter_day(), MainApplication.getWiper_day(), MainApplication.getEquip_coolant_day(),
//					MainApplication.getTransmission_oil_day(), MainApplication.getBattery_day(), MainApplication.getSava_mileage());
////		}
//	}
	
	private void setData()
	{
		mDBMileage = MainApplication.getSava_mileage();
//		mDBTime = auto.getAuto_time();
		mDBTime = MainApplication.getDrive_time();
		mDBCool = MainApplication.getAuto_coolant();
		mDBAllFuleEff = MainApplication.getFuel_consumption();
		mDBAllFulePri = MainApplication.getOil();

		CarLLog.w(TAG,"aaaaa mDBTime : "+ mDBMileage);
		CarLLog.w(TAG,"getFuel_consumption : "+ mDBAllFuleEff);
	}
	
//	private final Handler mStopAsyncTaskHandler = new Handler(new Handler.Callback() {
//		
//		@Override
//		public boolean handleMessage(Message msg) {
//			
//			if(mAllDataDBAsycTask != null && mAllDataDBAsycTask.getStatus() == AsyncTask.Status.FINISHED && msg.arg1 == MainApplication.handlerAuto) {
//				mAllDataDBAsycTask.cancel(true);
//				mAllDataDBAsycTask = null;	
//				
//				AllDataContentData auto = (AllDataContentData)msg.obj;
//				
//				if(msg.obj != null){
//					
//					mDBMileage = auto.getSave_mileage();
////					mDBTime = auto.getAuto_time();
//					mDBTime = auto.getDrive_time();
//					mDBCool = auto.getAuto_coolant();
//					mDBAllFuleEff = auto.getFuel_consumption();
//
//					CarLLog.w(TAG,"aaaaa mDBTime : "+auto.getAuto_time());
//					CarLLog.w(TAG,"getFuel_consumption : "+auto.getFuel_consumption());
//				}
//				
//				UiHandler.sendEmptyMessage(0);
//			}
//			
//			return false;
//		}
//	});

}
