package kr.hdd.carleamingTest.activity;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.database.AutoDBAsycTask;
import kr.hdd.carleamingTest.model.BluetoothReadData;
import kr.hdd.carleamingTest.util.SupportedPidUtill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DriveMonitringFragment extends Fragment {
	private static final String TAG= DriveMonitringFragment.class.getSimpleName();

	private Context mContext = null;

	private View view = null;

//	private BluetoothReadData mService;
//	private RelativeLayout mFuelEfficienctImg = null;
	private String mStrMaf = "0";
	private String mStrErrorKm = "0";
//	private double mFuelEfficiencyTemp = 0;  //평균연비 임시저장

	private TextView mRpmText = null;
	private TextView mOilText = null;
	private TextView mFuelEfficiencyText = null;//mFuelEfficiencyTextE, 
	private TextView mEngineLoadText = null;
	private TextView mCarNameText = null;
	private TextView mFuelConsumptionText = null;
	private TextView mSpeedText = null;
	private TextView mTimeMMText = null;
	private TextView mTimeSSText = null;
	private TextView mMileageText = null;
	private TextView mAllFuelConsumptionText = null;

	private AutoDBAsycTask mAllDataDBAsycTask = null;

//	private boolean mBound = false;

	private String mStrCarName = "그랜져 HG";
	private String mStrRpm = "0";
//	private String mStrOil = "0";
	private String mStrFuelEfficiency = "0";
	private String mStrEngineLoad = "0";
	private String mStrFuelConsumption = "0.0";
	private String mStrSpeed = "0";
	private String mStrTime = "0";
	private String mStrTimeMM = "0";
	private String mStrTimeSS = "0";
	private String mStrMileage = "0";
	private String mStrAllFuelConsumption = "0";
	private String mStrFuleType = "0";
	private String mStrEngine = "0";  //순간소모량

	private int mMm = 0;
	private int mSs = 0;
	private double mKmCount = 0;
	
//	private double use2 = 0;
	private double mFuelEfficiency = 0;  //평균연비
	private double mFueltotal = 0;//Double.parseDouble(MainApplication.FUEL_CONSUMPTION_COUNT); //총소모량
	private double mFuelprice = 0;

	private Integer mImgId[] = { R.id.drive_monitring_rs_img_1, R.id.drive_monitring_rs_img_2, R.id.drive_monitring_rs_img_3, R.id.drive_monitring_rs_img_4,
			R.id.drive_monitring_rs_img_5, R.id.drive_monitring_rs_img_6, R.id.drive_monitring_rs_img_7, R.id.drive_monitring_rs_img_8,
			R.id.drive_monitring_rs_img_9, R.id.drive_monitring_rs_img_10, R.id.drive_monitring_rs_img_11, R.id.drive_monitring_rs_img_12,
			R.id.drive_monitring_rs_img_13, R.id.drive_monitring_rs_img_14, R.id.drive_monitring_rs_img_15, R.id.drive_monitring_rs_img_16,
			R.id.drive_monitring_rs_img_17, R.id.drive_monitring_rs_img_18, R.id.drive_monitring_rs_img_19, R.id.drive_monitring_rs_img_20};
	
	private Integer mImgOnId[] = { R.drawable.monitor_graph_01_on, R.drawable.monitor_graph_01_on, R.drawable.monitor_graph_01_on, R.drawable.monitor_graph_01_on,
			R.drawable.monitor_graph_02_on, R.drawable.monitor_graph_02_on, R.drawable.monitor_graph_02_on, R.drawable.monitor_graph_02_on,
			R.drawable.monitor_graph_03_on, R.drawable.monitor_graph_03_on, R.drawable.monitor_graph_03_on,
			R.drawable.monitor_graph_04_on, R.drawable.monitor_graph_04_on, R.drawable.monitor_graph_04_on,
			R.drawable.monitor_graph_05_on, R.drawable.monitor_graph_05_on,
			R.drawable.monitor_graph_06_on, R.drawable.monitor_graph_06_on,
			R.drawable.monitor_graph_07_on, R.drawable.monitor_graph_07_on};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.drive_monitring_layout, null);
		mContext = getActivity();

//		mFuelEfficienctImg = (RelativeLayout) view.findViewById(R.id.drive_monitring_layout_2);
		mRpmText = (TextView) view.findViewById(R.id.drive_monitring_rpm_text); // RPM
		mOilText = (TextView) view.findViewById(R.id.drive_monitring_oil_text); // 사용 유류비
//		mFuelEfficiencyTextE = (TextView) view.findViewById(R.id.drive_monitring_fuel_efficiency); // 연비
		mFuelEfficiencyText = (TextView) view.findViewById(R.id.drive_monitring_fuel_efficiency_text); // 연비
		mEngineLoadText = (TextView) view.findViewById(R.id.drive_monitring_engine_load_text); // 엔진부하
		mCarNameText = (TextView) view.findViewById(R.id.drive_monitring_car_name_text); // 자동차이름
		mFuelConsumptionText = (TextView) view.findViewById(R.id.drive_monitring_all_fuel_consumption_text); // 순간소모량
		mSpeedText = (TextView) view.findViewById(R.id.drive_monitring_speed_text); // 속도
		mTimeMMText = (TextView) view.findViewById(R.id.drive_monitring_time_mm_text); // 주행시간 분
		mTimeSSText = (TextView) view.findViewById(R.id.drive_monitring_time_ss_text); // 주행시간 초
		mMileageText = (TextView) view.findViewById(R.id.drive_monitring_mileage_text); // 주행거리
		mAllFuelConsumptionText = (TextView) view.findViewById(R.id.drive_monitring_all_oil_fuel_consumption_text); // 총 유류 소모량

//		mAllDataDBAsycTask = new AutoDBAsycTask(mContext, mStopAsyncTaskHandler);
//		mAllDataDBAsycTask.execute();

		mStrCarName = MainApplication.getmUserCarName();

		if(MainApplication.getmUserCarName() != null && !"".equals(MainApplication.getmUserCarName())){
			mStrCarName = MainApplication.getmUserCarName();
		} else {
			mStrCarName = "차량이름을 입력해주세요.";
		}

		mCarNameText.setText(mStrCarName);

		mRpmText.setText(mStrRpm);
		mOilText.setText("0");
		mFuelEfficiencyText.setText(mStrFuelEfficiency);// 연비
		mEngineLoadText.setText(mStrEngineLoad);
		mFuelConsumptionText.setText(mStrFuelConsumption);
		mSpeedText.setText(mStrSpeed);
		mMileageText.setText(mStrMileage);
		mTimeMMText.setText(mStrTimeMM);
		mTimeSSText.setText(mStrTimeSS);
		mAllFuelConsumptionText.setText(mStrAllFuelConsumption);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		getActivity().registerReceiver(mReceiver, BluetoothReadData.makeGattUpdateIntentFilter());

	}
	
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getActivity().unregisterReceiver(mReceiver);
	}

	private Handler mUiHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			double fueleff = 0; // 연비
			double fuelcon = 0;

			if(mStrEngineLoad.equals("0")){
				mStrEngineLoad = "1";
			} else if(mStrMileage == null){
				mStrMileage = "0";
			} else if(fueleff == 0){
				fueleff = 0;
			} else if(mStrAllFuelConsumption == null){
				mStrAllFuelConsumption = "0";
			} else if(mStrTime.equals("0")){
				mStrTime = "0";
			} else if(mStrEngineLoad == null){
				mStrEngineLoad = "0.0";
			}

//			if(mStrFuleType.equals("1")){
//				fuletype = 1;
//				fuletypecc = 0.75;
//			}else if(mStrFuleType.equals("4")){
//				fuletype = 0.97;
//				fuletypecc = 0.85;
//			}

			//거리
			mMileageText.setText(mStrMileage);
			mStrMileage = String.format("%.2f", mKmCount); 
//			Temp = Double.parseDouble(mStrMileage);
			SupportedPidUtill.getInstance().setSupportedPid("MileageHap", mStrMileage);

			int mTempCool= (int)MainApplication.getAuto_coolant();
			int Enginedisplacement= 1;
			int RpmTemp= Integer.parseInt(mStrRpm);
			double calcLoad= Double.parseDouble(mStrEngineLoad)* 100./ 255.;
//			CarLLog.i(TAG, "555 mTempCool: "+ mTempCool);

//			CarLLog.i(TAG, "777 getOil_type: "+ MainApplication.getOil_type());
			if(MainApplication.getOil_type().equalsIgnoreCase(MainApplication.OIL_GASOL))
			{
				if(mTempCool<=55)
					fuelcon=0.001*0.004*4*1.35*Enginedisplacement*RpmTemp*60*calcLoad/20;
				else if(mTempCool>55)
					fuelcon=0.001*0.003*4*1.35*Enginedisplacement*RpmTemp*60*calcLoad/20;
			}
			else// if(Enginetype==1)
			{
				if(mTempCool<=55)
					fuelcon=0.001*0.004*4*Enginedisplacement*RpmTemp*60*calcLoad/20;
				else if(mTempCool>55)
					fuelcon=0.001*0.003*4*Enginedisplacement*RpmTemp*60*calcLoad/20;
			}
//			CarLLog.i(TAG, "777 fuelcon: "+ fuelcon);
			
//			fuelcon = (Double.parseDouble(mStrEngineLoad)* 0.02* 4)* 1000./ 3600.* 5.; //3600/2 하고 1800/2 하고 900/2 해서 근사값나옴
			if(Double.isNaN(fuelcon) || Double.isInfinite(fuelcon)){
				fuelcon = 0.00;
			} 

			if(mStrTime.equals("0")){
				mMm = 0;
				mSs = 0;
			} else{
				mMm = Integer.parseInt(mStrTime) / 60;
				mSs = Integer.parseInt(mStrTime) % 60;
			}
			//시간
			mStrTimeMM = String.valueOf(mMm);
			mStrTimeSS = String.valueOf(mSs);

			mTimeMMText.setText(mStrTimeMM);
			mTimeSSText.setText(mStrTimeSS);
			
			mStrEngine = String.format("%.2f", fuelcon);
			//순간소모량
			mFuelConsumptionText.setText(mStrEngine);
			setFuelConGraphLayout(Double.parseDouble(mStrEngine));

			//총유류소모량
			mStrAllFuelConsumption = String.format("%.2f", mFueltotal);
			mAllFuelConsumptionText.setText(mStrAllFuelConsumption);
//			//총 소모량 평균값내는 리스트
			SupportedPidUtill.getInstance().setSupportedPid(MainApplication.LISTALLFUELEFF, String.valueOf(mStrAllFuelConsumption));

			//연비
			mFuelEfficiency= mKmCount/ mFueltotal;
			if(mFuelEfficiency> 100.)	mFuelEfficiency= 99.99;
			else if(Double.isNaN(mFuelEfficiency)) mFuelEfficiency= 0.00;
			else if(Double.isInfinite(mFuelEfficiency)) mFuelEfficiency= 99.99;
			
			mStrFuelEfficiency = String.format("%.2f", mFuelEfficiency);
			mFuelEfficiencyText.setText(mStrFuelEfficiency); //연비
			MainApplication.setFuel_efficienct(mFuelEfficiency);
			
//			double tt= Double.parseDouble(mStrSpeed)* (1000./ 3600.)/ fuelcon;
//			mFuelEfficiencyTextE.setText(String.format("%.2f", tt)); //순간 연비
			

			//rpm
			mRpmText.setText(mStrRpm);
			setRpmGraphLayout(Integer.parseInt(mStrRpm));

			//속도
			mSpeedText.setText(mStrSpeed);
			setSpeedGraphLayout(Integer.parseInt(mStrSpeed));

			//엔진부하
			mEngineLoadText.setText(mStrEngineLoad);
			
//			use2 = mFueltotal * Utils.getPrice(MainApplication.getOil_type())/*1700 + Double.parseDouble(MainApplication.OILCOUNT)*/; //사용유류비
			mOilText.setText(String.format("%.0f", mFuelprice));

			return false;
		}
	});

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			String dataid = intent.getExtras().getString(MainApplication.RECIVER.DATAID);
			String data = intent.getExtras().getString(MainApplication.RECIVER.DATA);
			mStrTime = intent.getExtras().getString(MainApplication.RECIVER.DRIVE_TIME_RECIVER);
			mKmCount = intent.getExtras().getDouble(MainApplication.RECIVER.KM_RECIVER);
			mFueltotal = intent.getExtras().getDouble(MainApplication.RECIVER.TOTAL_OIL);
			mFuelprice = intent.getExtras().getDouble(MainApplication.RECIVER.OIL_PRICE);
//			mStrEngineLoad = intent.getExtras().getString(MainApplication.RECIVER.ENGINE_LOAD_RECIVER);
//			CarLLog.w(TAG, "mKmCount: "+ mKmCount);

			if(action.equals(MainApplication.RECIVER.RECIVER)) {

				if(dataid.equals(MainApplication.RECIVER.RPM_RECIVER)){
					if(data.equals("")){
						data = "0";
					}
					mStrRpm = data;

				} else if(dataid.equals(MainApplication.RECIVER.SPEED_RECIVER)){
					mStrSpeed = data;
//					double kmc = Double.parseDouble(mStrSpeed);
//					mSpeedCount = (kmc * 1000) / 3600;
				} else if(dataid.equals(MainApplication.RECIVER.MAF_RECIVER)){
					mStrMaf = data;
				} else if(dataid.equals(MainApplication.RECIVER.FEUL_TYPE_RECIVER)){
					mStrFuleType = data;
				} else if(dataid.equals(MainApplication.RECIVER.ERROR_KM_RECIVER)){
					mStrErrorKm = data;
				} else if(dataid.equals(MainApplication.RECIVER.ENGINE_FUEL_RATE_RECIVER)){
					mStrEngine = data;
				} 
				else if(dataid.equals(MainApplication.RECIVER.ENGINE_LOAD_RECIVER)){
//					mStrEngineLoad = data;
					double tLoad = (Double.parseDouble(data)* 100.)/ 255.;
					mStrEngineLoad = String.format("%.2f", tLoad); 
				}
//				CarLLog.w(TAG, "mStrEngineLoad: "+ mStrEngineLoad);

				mUiHandler.sendEmptyMessage(0);
			}
		}
	};

	private void setRpmGraphLayout(int number){
		View rmpView = view.findViewById(R.id.drive_monitring_rpm);
		ImageView mImg[] = new ImageView[20];

		for(int i= 0; i< mImgId.length; i++)
			mImg[i] = (ImageView) rmpView.findViewById(mImgId[i]);
	
		int tNum= number/ 500 + 1;
		if(number== 0) tNum= 0;
		else if(tNum> 20)	tNum= 20;

		for(int i= 0; i< tNum; i++)
		{	
			mImg[i].setBackground(rmpView.getResources().getDrawable(mImgOnId[i]));
		}
		for(int i= tNum; i< mImg.length; i++)
		{	
			mImg[i].setBackground(rmpView.getResources().getDrawable(R.drawable.monitor_graph_off_1));
		}
	
	}

	private void setFuelConGraphLayout(double number){
		//순간소모량

		View graphView = view.findViewById(R.id.drive_monitring_graph);
		ImageView img_arr[] = new ImageView[10];

		img_arr[0] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_1);
		img_arr[1] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_2);
		img_arr[2] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_3);
		img_arr[3] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_4);
		img_arr[4] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_5);
		img_arr[5] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_6);
		img_arr[6] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_7);
		img_arr[7] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_8);
		img_arr[8] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_9);
		img_arr[9] = (ImageView) graphView.findViewById(R.id.drive_monitring_img_10);

		int[] drawable = {R.drawable.monitor_panel_graph_off, R.drawable.monitor_panel_graph_on };

		int tNum= (int) (number/ 2 + 1);
		if(number== 0) tNum= 0;
		else if(tNum> 10)	tNum= 10;

		for(int i= 0; i< tNum; i++)
		{	
			img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
		}
		for(int i= tNum; i< img_arr.length; i++)
		{	
			img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
		}

	}

	private void setSpeedGraphLayout(int number){
		View speedView = view.findViewById(R.id.drive_monitring_speed);
		ImageView mImg[] = new ImageView[20];

		for(int i= 0; i< mImgId.length; i++)
			mImg[i] = (ImageView) speedView.findViewById(mImgId[i]);
	
		int tNum= number/ 12 + 1;
		if(number== 0) tNum= 0;
		else if(tNum> 20)	tNum= 20;

		for(int i= 0; i< tNum; i++)
		{	
			mImg[i].setBackground(speedView.getResources().getDrawable(mImgOnId[i]));
		}
		for(int i= tNum; i< mImg.length; i++)
		{	
			mImg[i].setBackground(speedView.getResources().getDrawable(R.drawable.monitor_graph_off_1));
		}
	}


}
