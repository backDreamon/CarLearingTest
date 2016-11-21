package kr.hdd.carleamingTest.activity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.AllDataContentData;
import kr.hdd.carleamingTest.common.circle.PieChart;
import kr.hdd.carleamingTest.common.circle.PieDetailsItem;
import kr.hdd.carleamingTest.common.popup.PopupDateCheck;
import kr.hdd.carleamingTest.database.DBAdapter;
import kr.hdd.carleamingTest.database.DBAdapter.All_Date_content;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.Utils;

public class DriveStyleFragment extends Fragment implements OnClickListener{

	private static final String TAG = DriveStyleFragment.class.getSimpleName();

	private TextView mTestText = null;
	private Button mTestBtn = null;

	private View view = null;

	private TextView mTitleCarName = null;
	private TextView mMileageText = null;
	private TextView mTimeText_mm = null;
	private TextView mTimeText_ss = null;
	private TextView moilText = null;
	private TextView mAllFuleText = null;
	private TextView mStartDate = null;
	private TextView mEndDate = null;
	private TextView mDriveTime = null;
	private TextView mDriveTime2 = null;
	private TextView mSpeedDown = null;
	private TextView mSpeedDown2 = null;
	private TextView mSpeedUp = null;
	private TextView mSpeedUp2 = null;
	private TextView mSpeedUpOilValue = null;
	private TextView mGong = null;
	private TextView mDrive = null;
	private TextView mGong2 = null;
	private TextView mDrive2 = null;
	private TextView mGongOilValue = null;

	private Button mBtnToday = null;
	private Button mBtnMonth = null;
	private Button mBtnDateCheck = null;

	private List<PieDetailsItem> mItem = new ArrayList<PieDetailsItem>(0);
	private int mMaxCount = 0;
	private double mItemCount = 0;

	private AllDBAsycTask mAutoDBAsycTask = null;

//	private double mdbMileage = 0;
//	private double mdbTime = 0;
//	private double mdbFuleEFF = 0;
//	private double mdbAllFuleEff = 0;

	private String mDbDate = null;
	private String mStrStartData = null;
	private String mStrEndData = null;

	private ArrayList<AllDataContentData> mArrAllData = new ArrayList<AllDataContentData>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.drive_style_layout, null);

		mBtnToday = (Button) view.findViewById(R.id.equip_today_btn);  //오늘 그래프  보기 버튼
		mBtnMonth = (Button) view.findViewById(R.id.equip_month_btn);  //이번달 그래프 보기 버튼
		mBtnDateCheck = (Button) view.findViewById(R.id.drive_style_date_check);   //선택 날짜 그래프 보기 버튼
		//		
		mTitleCarName = (TextView) view.findViewById(R.id.drive_style_title_car_name);  //자동차 이름 
		mMileageText = (TextView) view.findViewById(R.id.drive_style_mileage);  //거리 
		mAllFuleText  = (TextView) view.findViewById(R.id.drive_style__fuel_consumption);  //연비
		moilText = (TextView) view.findViewById(R.id.drive_style_oil);  //사용 유류비
		mTimeText_mm = (TextView) view.findViewById(R.id.drive_style_time_mm);  //시간 분
		mTimeText_ss = (TextView) view.findViewById(R.id.drive_style_time_ss);  //시간 초


		mStartDate = (TextView) view.findViewById(R.id.date);  //기간 시작 날짜
		mEndDate = (TextView) view.findViewById(R.id.date2);  //기간 끝 날짜 

		mDriveTime = (TextView) view.findViewById(R.id.drive_time);  //주행시간 분
		mDriveTime2 = (TextView) view.findViewById(R.id.drive_time2);  //주행시간 초

		mSpeedDown = (TextView) view.findViewById(R.id.drive_speed_down);  // 감속 분
		mSpeedDown2 = (TextView) view.findViewById(R.id.drive_speed_down2);  //감속 초

		mSpeedUp = (TextView) view.findViewById(R.id.drive_speed_up);  // 가속 분
		mSpeedUp2 = (TextView) view.findViewById(R.id.drive_speed_up2);  //가속 초
		mSpeedUpOilValue = (TextView) view.findViewById(R.id.drive_oil_value1);  //가속 측정 유류비

		mGong = (TextView) view.findViewById(R.id.drive_gong);  //공회전 분 
		mGong2 = (TextView) view.findViewById(R.id.drive_gong2);  // 공회전 초
		mGongOilValue = (TextView) view.findViewById(R.id.drive_oil_value2);  //공회전 측정 유류비 

		mDrive = (TextView) view.findViewById(R.id.drive);  //일반 주행 분
		mDrive2 = (TextView) view.findViewById(R.id.drive2);  //일반 주행 초


		mBtnToday.setOnClickListener(this);
		mBtnMonth.setOnClickListener(this);
		mBtnDateCheck.setOnClickListener(this);

		mAutoDBAsycTask = new AllDBAsycTask(getActivity(), mStopAsyncTaskHandler);
		mAutoDBAsycTask.execute();

		return view;
	}
	
	private int mArrcount = 0;
	private int mSpeedDownTemp = 0;
	private int mSpeedUpTemp = 0;
	private int mGongTemp = 0;
	private int mDdriveTemp = 0;
	
	private double mKmTemp = 0;
	private double mOilTemp = 0;
	private double mPriceTemp = 0;

	private Handler mUiHandelr = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {

//			long DriveTi = 0;
			mItem.clear(); 
			mMaxCount = 0;

			int arrcountY= 0, arrcountT= 0;
			int speedDownY= 0, speedDownT= 0, speedDownP= 0;
			int speedUpY= 0, speedUpT= 0, speedUpP= 0;
			int gongY= 0, gongT= 0, gongP= 0;
			int driveY= 0, driveT= 0, driveP= 0;

			double kmY= 0, kmT= 0, oilY= 0, oilT= 0, priceY= 0, priceT= 0;//, eEff, eWon;


			switch (msg.what) {
			case 1:
				mStartDate.setText(Utils.JunmDate());
				mEndDate.setVisibility(View.GONE);
//				mLayoutGraphView = (ViewGroup) view.findViewById(R.id.drive_style_pie_Layout);
//				mImgView = (ImageView) view.findViewById(R.id.drive_style_pie_img);

//				ArrayList<AllDataContentData> ddata = new ArrayList<AllDataContentData>(new HashSet<AllDataContentData>(mArrAllData));

				CarLLog.i(TAG, "mArrAllData.size(): "+ mArrAllData.size());
				for(int i=0; i< mArrAllData.size(); i++){
					mDbDate = mArrAllData.get(i).getDay().replace(".", "");
					if(!mDbDate.equals("0")){
						mDbDate = mDbDate.substring(0, 8);
						CarLLog.w(TAG, "mDbDate: "+ mDbDate);
						CarLLog.w(TAG, "Utils.DayDate(): "+ Utils.DayDate());
						CarLLog.w(TAG, "compareTo: "+ mDbDate.compareTo(Utils.DayDate()));

						if(mDbDate.compareTo(Utils.DayDate())< 0)
						{
							arrcountY = mArrAllData.get(i).getDrive_time();
							speedDownY = mArrAllData.get(i).getDrive_down_speed();
							speedUpY = mArrAllData.get(i).getDrive_up_speed();
							gongY = mArrAllData.get(i).getIdle();
							driveY = mArrAllData.get(i).getDrive();
							CarLLog.w(TAG, "111 driveY: "+ driveY);

							kmY = mArrAllData.get(i).getSave_mileage();
							oilY = mArrAllData.get(i).getFuel_consumption();
							priceY = mArrAllData.get(i).getOil();
						}
						else if(mDbDate.equals(Utils.DayDate())){
							arrcountT = mArrAllData.get(i).getDrive_time();
							speedDownT = mArrAllData.get(i).getDrive_down_speed();
							speedUpT = mArrAllData.get(i).getDrive_up_speed();
							gongT = mArrAllData.get(i).getIdle();
							driveT = mArrAllData.get(i).getDrive();

//							CarLLog.w(TAG, "111 getDrive: "+ mArrAllData.get(i).getDrive());
							CarLLog.w(TAG, "111 driveT: "+ driveT);

//							arrcountToday++;
							
							kmT = mArrAllData.get(i).getSave_mileage();
							oilT = mArrAllData.get(i).getFuel_consumption();
							priceT = mArrAllData.get(i).getOil();
//							mdbAllFuleEff= mArrAllData.get(i).getFuel_efficienct();
						}
					}
				}

//				arrcountToday = arrcountT- arrcountY > 0 ? arrcountT- arrcountY : 0;
				mSpeedDownTemp = speedDownT- speedDownY > 0 ? speedDownT- speedDownY : 0;
				mSpeedUpTemp = speedUpT- speedUpY > 0 ? speedUpT- speedUpY : 0;
				mGongTemp = gongT- gongY > 0 ? gongT- gongY : 0;
				mDdriveTemp = driveT- driveY > 0 ? driveT- driveY : 0;
				mKmTemp = (int) (kmT- kmY > 0 ? kmT- kmY : 0);
				mOilTemp = (int) (oilT- oilY > 0 ? oilT- oilY : 0);
				mPriceTemp = (int) (priceT- priceY > 0 ? priceT- priceY : 0);

				mArrcount = mSpeedDownTemp + mSpeedUpTemp + mGongTemp + mDdriveTemp;
				
				setDataView();
				
				break;

			case 2:
				mEndDate.setVisibility(View.VISIBLE);
				mStartDate.setText(Utils.StrDayJunmDate(Utils.getMonthFirstAgoDate()));
				mEndDate.setText(" ~ " + Utils.JunmDate());

				for(int i=0; i<mArrAllData.size(); i++){
					mDbDate = mArrAllData.get(i).getDay().replace(".", "");
					if(!mDbDate.equals("0")){
						mDbDate = mDbDate.substring(0, 6);
						CarLLog.w(TAG, "mDbDate: "+ mDbDate);
						CarLLog.w(TAG, "Utils.MonthDate(): "+ Utils.MonthDate());
						CarLLog.w(TAG, "mDbDate.compareTo: "+ mDbDate.compareTo(Utils.MonthDate()));


						if(mDbDate.compareTo(Utils.MonthDate())< 0)
						{
							arrcountY = mArrAllData.get(i).getDrive_time();
							speedDownY = mArrAllData.get(i).getDrive_down_speed();
							speedUpY = mArrAllData.get(i).getDrive_up_speed();
							gongY = mArrAllData.get(i).getIdle();
							driveY = mArrAllData.get(i).getDrive();
//							CarLLog.w(TAG, "111 speedDownY: "+ speedDownY);

							kmY = mArrAllData.get(i).getSave_mileage();
							oilY = mArrAllData.get(i).getFuel_consumption();
							priceY = mArrAllData.get(i).getOil();
							CarLLog.w(TAG, "111 speedDownY: "+ speedDownY);
						}
						else if(mDbDate.equals(Utils.MonthDate())){
							arrcountT = mArrAllData.get(i).getDrive_time();
							speedDownT = mArrAllData.get(i).getDrive_down_speed();
							speedUpT = mArrAllData.get(i).getDrive_up_speed();
							gongT = mArrAllData.get(i).getIdle();
							driveT = mArrAllData.get(i).getDrive();

//							CarLLog.w(TAG, "111 getDrive: "+ mArrAllData.get(i).getDrive());
							CarLLog.w(TAG, "111 speedDownT: "+ speedDownT);

//							arrcountToday++;
							
							kmT = mArrAllData.get(i).getSave_mileage();
							oilT = mArrAllData.get(i).getFuel_consumption();
							priceT = mArrAllData.get(i).getOil();
//							mdbAllFuleEff= mArrAllData.get(i).getFuel_efficienct();
						}

					}
				}
				CarLLog.i(TAG, "111 speedDownY: "+ speedDownY);
				CarLLog.i(TAG, "111 speedDownT: "+ speedDownT);


//				arrcountToday = arrcountT- arrcountY > 0 ? arrcountT- arrcountY : 0;
				mSpeedDownTemp = speedDownT- speedDownY > 0 ? speedDownT- speedDownY : 0;
				mSpeedUpTemp = speedUpT- speedUpY > 0 ? speedUpT- speedUpY : 0;
				mGongTemp = gongT- gongY > 0 ? gongT- gongY : 0;
				mDdriveTemp = driveT- driveY > 0 ? driveT- driveY : 0;
				mDdriveTemp = driveT- driveY > 0 ? driveT- driveY : 0;
				mKmTemp = (int) (kmT- kmY > 0 ? kmT- kmY : 0);
				mOilTemp = (int) (oilT- oilY > 0 ? oilT- oilY : 0);
				mPriceTemp = (int) (priceT- priceY > 0 ? priceT- priceY : 0);

				mArrcount = mSpeedDownTemp + mSpeedUpTemp + mGongTemp + mDdriveTemp;

				setDataView();
				
				break;

			case 3:
				if(msg.arg1 > 0 && msg.arg2 > 0){
					mEndDate.setVisibility(View.VISIBLE);
					mStartDate.setText(Utils.StrDayJunmDate(msg.arg1));
					mEndDate.setText(" ~ " + Utils.StrDayJunmDate(msg.arg2));

					mStrStartData = Utils.StrDayJunmDate(msg.arg1).replace(".", "").substring(0, 8);
					mStrEndData = Utils.StrDayJunmDate(msg.arg2).replace(".", "").substring(0, 8);
//					int EndDay = Integer.parseInt(mStrEndData.substring(0, 6));
					CarLLog.i(TAG, "333 mStrStartData: "+ mStrStartData);
					CarLLog.i(TAG, "333 mStrEndData: "+ mStrEndData);

					for(int i=0; i<mArrAllData.size(); i++){
						mDbDate = mArrAllData.get(i).getDay().replace(".", "");
						CarLLog.i(TAG, "333 mDbDate: "+ mDbDate);
						if(!mDbDate.equals("0")){
							CarLLog.i(TAG, "333 mStrEndData: "+ mStrEndData);
							mDbDate = mDbDate.substring(0, 8);

							if(mDbDate.compareTo(mStrStartData)< 0/* && mDbDate.compareTo(mStrEndData)<= 0*/)
							{
								arrcountY = mArrAllData.get(i).getDrive_time();
								speedDownY = mArrAllData.get(i).getDrive_down_speed();
								speedUpY = mArrAllData.get(i).getDrive_up_speed();
								gongY = mArrAllData.get(i).getIdle();
								driveY = mArrAllData.get(i).getDrive();
//								CarLLog.w(TAG, "111 speedDownY: "+ speedDownY);

								kmY = mArrAllData.get(i).getSave_mileage();
								oilY = mArrAllData.get(i).getFuel_consumption();
								priceY = mArrAllData.get(i).getOil();
								CarLLog.w(TAG, "333 speedDownY: "+ speedDownY);
							}
							else if(mDbDate.compareTo(mStrStartData)>= 0 && mDbDate.compareTo(mStrEndData)<= 0) {
								arrcountT = mArrAllData.get(i).getDrive_time();
								speedDownT = mArrAllData.get(i).getDrive_down_speed();
								speedUpT = mArrAllData.get(i).getDrive_up_speed();
								gongT = mArrAllData.get(i).getIdle();
								driveT = mArrAllData.get(i).getDrive();

//								CarLLog.w(TAG, "111 getDrive: "+ mArrAllData.get(i).getDrive());
								CarLLog.w(TAG, "333 speedDownT: "+ speedDownT);

//								arrcountToday++;
								
								kmT = mArrAllData.get(i).getSave_mileage();
								oilT = mArrAllData.get(i).getFuel_consumption();
								priceT = mArrAllData.get(i).getOil();
//								mdbAllFuleEff= mArrAllData.get(i).getFuel_efficienct();
							}
						}
					}
					CarLLog.e(TAG, "111 speedDownY: "+ speedDownY);
					CarLLog.e(TAG, "111 speedDownT: "+ speedDownT);


//					arrcountToday = arrcountT- arrcountY > 0 ? arrcountT- arrcountY : 0;
					mSpeedDownTemp = speedDownT- speedDownY > 0 ? speedDownT- speedDownY : 0;
					mSpeedUpTemp = speedUpT- speedUpY > 0 ? speedUpT- speedUpY : 0;
					mGongTemp = gongT- gongY > 0 ? gongT- gongY : 0;
					mDdriveTemp = driveT- driveY > 0 ? driveT- driveY : 0;
					mKmTemp = (int) (kmT- kmY > 0 ? kmT- kmY : 0);
					mOilTemp = (int) (oilT- oilY > 0 ? oilT- oilY : 0);
					mPriceTemp = (int) (priceT- priceY > 0 ? priceT- priceY : 0);

					mArrcount = mSpeedDownTemp + mSpeedUpTemp + mGongTemp + mDdriveTemp;
					
					setDataView();
				}
				
				break;
			}

			return false;
		}
	});
	
	private void setDataView()
	{
		if(mKmTemp> 0)
			mMileageText.setText(String.format("%.1f", mKmTemp) + "km");//거리
		else
			mMileageText.setText("0km");  //거리
		
		if(mOilTemp> 0)
			mAllFuleText.setText(String.format("%.1f", mKmTemp/ mOilTemp) + "km/L");  //연비
		else
			mAllFuleText.setText("99.9km/L");  //연비

		if(mPriceTemp> 0)
			moilText.setText(String.format("%.1f", mPriceTemp) + " ￦");  //유류비
		else
			moilText.setText("0  ￦");  //유류비
//		moilText.setText(String.format("%.0f", (oilT- oilY)* Utils.getPrice(MainApplication.getOil_type())) + " ￦");  //유류비
		
		mTimeText_mm.setText(Utils.CutStr(mArrcount / 60) + "분");
		mTimeText_ss.setText(Utils.CutStr(mArrcount % 60) + "초");

		mDriveTime.setText(Utils.CutStr(mArrcount / 60) + " : ");
		mDriveTime2.setText(Utils.CutStr(mArrcount % 60));
		mSpeedDown.setText(Utils.CutStr(mSpeedDownTemp / 60) + " : ");
		mSpeedDown2.setText(Utils.CutStr(mSpeedDownTemp % 60));
		mSpeedUp.setText(Utils.CutStr(mSpeedUpTemp / 60)+" : ");
		mSpeedUp2.setText(Utils.CutStr(mSpeedUpTemp % 60));
		mGong.setText(Utils.CutStr(mGongTemp / 60)+" : ");
		mGong2.setText(Utils.CutStr(mGongTemp % 60));
		mDrive.setText(Utils.CutStr(mDdriveTemp / 60)+" : ");
		mDrive2.setText(Utils.CutStr(mDdriveTemp % 60));

//		mItemCount = mItem.size();

//		PieDetailsItem item1 = null;
//		PieDetailsItem item2 = null;
//		PieDetailsItem item3 = null;
//		PieDetailsItem item4 = null;

		if(mArrcount ==0){
			mArrcount = 100;
		}
		CarLLog.i(TAG, "mArrcount: "+ mArrcount);

		int speedDownP, speedUpP, gongP, driveP;
		if(mSpeedDownTemp == 0 && mSpeedUpTemp == 0 && mGongTemp == 0 && mDdriveTemp == 0){
			speedDownP = speedUpP= gongP= driveP=25;
		} 
		else {
			speedDownP = mSpeedDownTemp * 100 / mArrcount;
			speedUpP = mSpeedUpTemp * 100 / mArrcount;
			gongP = mGongTemp * 100 / mArrcount;
			driveP = mDdriveTemp * 100 / mArrcount;
		} 
		mSpeedUpOilValue.setText(String.valueOf(mPriceTemp * speedUpP/ 100));
		mGongOilValue.setText(String.valueOf(mPriceTemp * gongP/ 100));

//		mGongOilValue.setText(String.valueOf(mdbAllFuleEff > 0 ? mdbAllFuleEff : 0 * (mSpeedUpTemp*100))+")");
//		mSpeedUpOilValue.setText(String.valueOf(mdbAllFuleEff > 0 ? mdbAllFuleEff : 0 * (mGongTemp * 100))+")");

		PieDetailsItem item1 = new PieDetailsItem();
		PieDetailsItem item2 = new PieDetailsItem();
		PieDetailsItem item3 = new PieDetailsItem();
		PieDetailsItem item4 = new PieDetailsItem();

		item1 = new PieDetailsItem();
		item2 = new PieDetailsItem();
		item3 = new PieDetailsItem();
		item4 = new PieDetailsItem();

		if(speedDownP> 0)
		{
			item1.count = speedDownP;//mItemCount;
			item1.label = speedDownP + "%";
			item1.color = Color.parseColor("#0e93ab");
			mItem.add(item1);
			mMaxCount += item1.count;
		}

		if(speedUpP> 0)
		{
			item2.count = speedUpP;//mItemCount;
			item2.label = speedUpP + "%";
			item2.color = Color.parseColor("#cc791f");
			mItem.add(item2);
			mMaxCount += item2.count;
		}

		if(gongP> 0)
		{
			item3.count = gongP;//mItemCount;
			item3.label = gongP + "%";
			item3.color = Color.parseColor("#c3b622");
			mItem.add(item3);
			mMaxCount += item3.count;
		}

		if(driveP> 0)
		{
			item4.count = driveP;//mItemCount;
			item4.label = driveP + "%";
			item4.color = Color.parseColor("#0ba65b");
			mItem.add(item4);
			mMaxCount += item4.count;
		}
//		mGongOilValue.setText(String.format("%.0f", (oilT- oilY)* 17* gongP)+"원)");
//		mSpeedUpOilValue.setText(String.format("%.0f", (oilT- oilY)* 17* speedUpP)+"원)");

//		ViewGroup mLayoutGraphView = null;
//		mLayoutGraphView = (ViewGroup) view.findViewById(R.id.drive_style_pie_Layout);
		ImageView mImgView = (ImageView) view.findViewById(R.id.drive_style_pie_img);
		Bitmap mBaggroundImage=Bitmap.createBitmap(600,600,Bitmap.Config.ARGB_8888);
		PieChart piechart=new PieChart(getActivity());
		
//		PieChart piechart = null;
//		Bitmap mBaggroundImage=Bitmap.createBitmap(600,600,Bitmap.Config.ARGB_8888);
		piechart= new PieChart(getActivity());
		piechart.setLayoutParams(new LayoutParams(600,600));
		piechart.setGeometry(600, 600, 2, 2, 2, 2, 2130837504);
		piechart.setSkinparams(getResources().getColor(android.R.color.transparent));
		piechart.setData(mItem, mMaxCount);
		piechart.invalidate();
		piechart.draw(new Canvas(mBaggroundImage));
		mImgView.setImageBitmap(mBaggroundImage);
		mImgView.invalidate();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.equip_today_btn:
			mUiHandelr.sendEmptyMessage(1);
			break;

		case R.id.equip_month_btn : 
			mUiHandelr.sendEmptyMessage(2);
			break;
		case R.id.drive_style_date_check:
			PopupDateCheck popupdate = new PopupDateCheck(getActivity(), mUiHandelr);
			popupdate.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
			popupdate.show();

			break;
		}
	}

	private final Handler mStopAsyncTaskHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			if(mAutoDBAsycTask != null && mAutoDBAsycTask.getStatus() == AsyncTask.Status.FINISHED && msg.arg1 == MainApplication.handlerAuto) {
				AllDataContentData auto = (AllDataContentData) msg.obj;

//				if(msg.obj != null){
//					if(auto.getdbId() >= 0){
//						MainApplication.setdbId(auto.getdbId());
//					}
//
//					if(auto.getCar_Name() != null){
//						MainApplication.setmUserCarName(auto.getCar_Name());
//					}
//
//					if(auto.getUserId() != null){
//						MainApplication.setmUserId(auto.getUserId());
//					}
//					if(auto.getCar_vehicle_number() != null){
//						MainApplication.setCar_vehicle_number(auto.getCar_vehicle_number());
//					}
//					if(auto.getCar_number() != null){
//						MainApplication.setCar_number(auto.getCar_number());
//					}
//					if(auto.getCar_model() >= 0){
//						MainApplication.setCar_model(auto.getCar_model());
//					}
//					if(auto.getOil_type() != null){
//						MainApplication.setOil_type(auto.getOil_type());
//					}
//					if(auto.getMileage() >= 0){
//						MainApplication.setMileage(auto.getMileage());
//					}
//					if(auto.getAuto_time() >= 0){
//						MainApplication.setAuto_time(auto.getAuto_time());
//					}
//					if(auto.getOil() >= 0){
//						MainApplication.setOil(auto.getOil());
//					}
//					if(auto.getAuto_coolant() >= 0){
//						MainApplication.setAuto_coolant(auto.getAuto_coolant());
//					}
//					if(auto.getFuel_consumption() >= 0){
//						MainApplication.setFuel_consumption(auto.getFuel_consumption());
//					}
//					if(auto.getStart_date() != null){
//						MainApplication.setStart_date(auto.getStart_date());
//					}
//					if(auto.getFuel_efficienct() >= 0){
//						MainApplication.setFuel_efficienct(auto.getFuel_efficienct());
//					}
//					if(auto.getDrive_time() >= 0){
//						MainApplication.setDrive_time(auto.getDrive_time());
//					}
//					if(auto.getDrive_down_speed() >= 0){
//						MainApplication.setDrive_down_speed(auto.getDrive_down_speed());
//					}
//					if(auto.getDrive_up_speed() >= 0){
//						MainApplication.setDrive_up_speed(auto.getDrive_up_speed());
//					}
//					if(auto.getIdle() >= 0){
//						MainApplication.setIdle(auto.getIdle());
//					}
//					if(auto.getDrive() >= 0){
//						MainApplication.setDrive(auto.getDrive());
//					}
//					if(auto.getEngine_oil() >= 0){
//						MainApplication.setEngine_oil(auto.getEngine_oil());
//					}
//					if(auto.getTire() >= 0){
//						MainApplication.setTire(auto.getTire());
//					}
//					if(auto.getBrake_lining() >= 0){
//						MainApplication.setBrake_lining(auto.getBrake_lining());
//					}
//					if(auto.getAircon_filter() >= 0){
//						MainApplication.setAircon_filter(auto.getAircon_filter());
//					}
//					if(auto.getWiper() >= 0){
//						MainApplication.setWiper(auto.getWiper());
//					}
//					if(auto.getEquip_coolant() >= 0){
//						MainApplication.setEquip_coolant(auto.getEquip_coolant());
//					}
//					if(auto.getTransmission_oil() >= 0){
//						MainApplication.setTransmission_oil(auto.getTransmission_oil());
//					}
//					if(auto.getBattery() >= 0){
//						MainApplication.setBattery(auto.getBattery());
//					}
//					if(auto.getEngine_oil_day() != null){
//						MainApplication.setEngine_oil_day(auto.getEngine_oil_day());
//					}
//					if(auto.getTire_day() != null){
//						MainApplication.setTire_day(auto.getTire_day());
//					}
//					if(auto.getBrake_lining_day() != null){
//						MainApplication.setBrake_lining_day(auto.getBrake_lining_day());
//					}
//					if(auto.getAircon_filter_day() != null){
//						MainApplication.setAircon_filter_day(auto.getAircon_filter_day());
//					}
//					if(auto.getWiper_day() != null){
//						MainApplication.setWiper_day(auto.getWiper_day());
//					}
//					if(auto.getEquip_coolant_day() != null){
//						MainApplication.setEquip_coolant_day(auto.getEquip_coolant_day());
//					}
//					if(auto.getTransmission_oil_day() != null){
//						MainApplication.setTransmission_oil_day(auto.getTransmission_oil_day());
//					}
//					if(auto.getBattery_day() != null){
//						MainApplication.setBattery_day(auto.getBattery_day());
//					}
//					if(auto.getDay() != null){
//						MainApplication.setDay(auto.getDay());
//					}
//					if(auto.getSave_mileage() > 0){
//						MainApplication.setSava_mileage(auto.getSave_mileage());
//					}
//
//				}

				mUiHandelr.sendEmptyMessage(1);

			}

			return false;
		}
	});

	/**
	 * item db
	 * **/
	private AllDataContentData AllDataDB(){

		DBAdapter db = new DBAdapter(getActivity()); 
		db.open();
		AllDataContentData auto = null;

		Cursor cursor = db.getAll_DataManager().getAll_All_Data();

		if (cursor.moveToFirst())
		{
			do {          
				auto = new AllDataContentData();

				int idIndex = cursor.getColumnIndex(All_Date_content.KEY_ROWID);
				auto.setdbId(cursor.getLong(idIndex));

				int userIdIndex = cursor.getColumnIndex(All_Date_content.KEY_All_USERID);
				auto.setUserId(cursor.getString(userIdIndex));

				int car_NameIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_NAME);
				auto.setCar_Name(cursor.getString(car_NameIndex));

				int car_vehicle_numberIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_VEHICLE_NUMBER);
				auto.setCar_vehicle_number(cursor.getString(car_vehicle_numberIndex));

				int car_numberIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_NUMBER);
				auto.setCar_number(cursor.getString(car_numberIndex));

				int car_modelIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_MODEL);
				auto.setCar_model(cursor.getInt(car_modelIndex));

				int oil_typeIndex = cursor.getColumnIndex(All_Date_content.KEY_All_OIL_TYPE);
				auto.setOil_type(cursor.getString(oil_typeIndex));

				int mileageIndex = cursor.getColumnIndex(All_Date_content.KEY_All_MILEAGE);
				auto.setMileage(cursor.getDouble(mileageIndex));

				int auto_timeIndex =cursor.getColumnIndex(All_Date_content.KEY_All_TMIE);
				auto.setAuto_time(cursor.getInt(auto_timeIndex));

				int oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_OIL);
				auto.setOil(cursor.getDouble(oilIndex));

				int auto_coolantIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT);
				auto.setAuto_coolant(cursor.getDouble(auto_coolantIndex));

				int fuel_consumptionIndex = cursor.getColumnIndex(All_Date_content.KEY_All_FUEL_CONSUMPTION);
				auto.setFuel_consumption(cursor.getDouble(fuel_consumptionIndex));

				int start_dateIndex = cursor.getColumnIndex(All_Date_content.KEY_All_START_DATE);
				auto.setStart_date(cursor.getString(start_dateIndex));

				int fuel_efficienctIndex = cursor.getColumnIndex(All_Date_content.KEY_All_FUEL_EFFICIENCT);
				auto.setFuel_efficienct(cursor.getDouble(fuel_efficienctIndex));

				int drive_timeIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_TIME);
				auto.setDrive_time(cursor.getInt(drive_timeIndex));

				int drive_down_speedIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_DOWN_SPEEd);
				auto.setDrive_down_speed(cursor.getInt(drive_down_speedIndex));

				int drive_up_speedIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_UP_SPEEd);
				auto.setDrive_up_speed(cursor.getInt(drive_up_speedIndex));

				int idleIndex = cursor.getColumnIndex(All_Date_content.KEY_All_IDLE);
				auto.setIdle(cursor.getInt(idleIndex));

				int driveIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE);
				auto.setDrive(cursor.getInt(driveIndex));

				int engine_oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_ENGINE_OIL);
				auto.setEngine_oil(cursor.getDouble(engine_oilIndex));

				int tireIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TIRE);
				auto.setTire(cursor.getDouble(tireIndex));

				int brake_liningIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BRAKE_LINING);
				auto.setBrake_lining(cursor.getDouble(brake_liningIndex));

				int aircon_filterIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AIRCON_FILTER);
				auto.setAircon_filter(cursor.getDouble(aircon_filterIndex));

				int wiperIndex = cursor.getColumnIndex(All_Date_content.KEY_All_WIPER);
				auto.setWiper(cursor.getDouble(wiperIndex));

				int equip_coolantIndex = cursor.getColumnIndex(All_Date_content.KEY_All_EQUIP_COOLANT);
				auto.setEquip_coolant(cursor.getDouble(equip_coolantIndex));

				int transmission_oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TRANSMISSION_OIL);
				auto.setTransmission_oil(cursor.getDouble(transmission_oilIndex));

				int batteryIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BATTERY);
				auto.setBattery(cursor.getDouble(batteryIndex));

				int engine_oil_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_ENGINE_OIL_DAY);
				auto.setEngine_oil_day(cursor.getString(engine_oil_dayIndex));

				int tire_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TIRE_DAY);
				auto.setTire_day(cursor.getString(tire_dayIndex));

				int brake_lining_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BRAKE_LINING_DAY);
				auto.setBrake_lining_day(cursor.getString(brake_lining_dayIndex));

				int aircon_filter_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AIRCON_FILTER_DAY);
				auto.setAircon_filter_day(cursor.getString(aircon_filter_dayIndex));

				int wiper_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_WIPER_DAY);
				auto.setWiper_day(cursor.getString(wiper_dayIndex));

				int equip_coolant_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_EQUIP_COOLANT_DAY);
				auto.setEquip_coolant_day(cursor.getString(equip_coolant_dayIndex));

				int transmission_oil_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TRANSMISSION_OIL_DAY);
				auto.setTransmission_oil_day(cursor.getString(transmission_oil_dayIndex));

				int battery_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BATTERY_DAY);
				auto.setBattery_day(cursor.getString(battery_dayIndex));

				int dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DAY);
				auto.setDay(cursor.getString(dayIndex));

				int save_mileageIndex = cursor.getColumnIndex(All_Date_content.KEY_All_SAVE_MILEAGE);
				auto.setSave_mileage(cursor.getDouble(save_mileageIndex));

				mArrAllData.add(auto);


			} while (cursor.moveToNext());
		} else {
			//			saveDBsetting();
		}

		cursor.close();

		db.close();

		return auto;
	}

	/**
	 * item DB를 받아오는곳 
	 * */
	public class AllDBAsycTask extends AsyncTask<Void, Void, AllDataContentData>{

		private Handler mStopHandler = null;

		/**
		 * @param context	 
		 * @param stopHandler
		 */
		public AllDBAsycTask(Context context, Handler stopHandler){
			mStopHandler = stopHandler;
		}

		@Override
		protected AllDataContentData doInBackground(Void... params) {
			AllDataContentData alldatainfo = null;
			try {
				// DB 받아오는 용도			
				alldatainfo = AllDataDB();


				if(!isCancelled())
				{

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return alldatainfo; 
		}	


		@Override
		protected void onPostExecute(AllDataContentData result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Message msg = mStopHandler.obtainMessage();
			msg.obj = result;
			msg.arg1 = MainApplication.handlerAuto;	
			mStopHandler.sendMessage(msg);				
			onCancelled();

		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();

			if(mStopHandler != null){
				mStopHandler = null;
			}			

		}
	}
}
