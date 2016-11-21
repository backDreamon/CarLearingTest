package kr.hdd.carleamingTest.common.popup;

import java.util.ArrayList;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.common.popup.adapter.EnginCheckAdatper;
import kr.hdd.carleamingTest.util.SupportedPidUtill;
import kr.hdd.carleamingTest.util.Utils;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PopupEnginCheck extends Dialog implements OnClickListener{
	private final String TAG= "PopupEnginCheck";
	
	private Context mContext = null;
	
	private TextView mTimeMM = null;
	private TextView mTimeSS = null;
	private TextView mMileage = null;
	private ListView mErrorList = null;
	
	private Button mBtnClose = null;
	
	private EnginCheckAdatper mAdapter = null;
	private ArrayList<String> mCodeList = null;
	
	public PopupEnginCheck(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.engin_check_dialog);
		getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);
		
		mTimeMM = (TextView) findViewById(R.id.engin_check_time_mm);   //시간 분
		mTimeSS = (TextView) findViewById(R.id.engin_check_time_ss);    //시간 초
		mMileage = (TextView) findViewById(R.id.engin_check_mileage_mm);  //거리 분
		mErrorList = (ListView) findViewById(R.id.engin_check_bottom_error_list);
		mCodeList = new ArrayList<String>();

//		Log.w(TAG, "LIST03: "+ SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03));
		
		if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03) != null){
//			Log.w(TAG, "LIST03 size: "+ SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).size());
			for(int i=0; i<SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).size(); i++){
				if(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).get(i) != null){
					mCodeList.add(SupportedPidUtill.getInstance().getSupportedPid(MainApplication.LIST03).get(i).toString());
				}
			}
		}else {
			for(int i=0; i</*SupportedPidUtill.getInstance(mContext).getSupportedPid("0303").size()*/1; i++){
//				mCodeList.add(SupportedPidUtill.getInstance(mContext).getSupportedPid("0303").get(i).toString());
				mCodeList.add("00");
			}
		}
		
		mAdapter = new EnginCheckAdatper(mContext, 0, mCodeList);
		mErrorList.setClickable(false);
		mErrorList.setFocusable(false);
//		mErrorList.setEnabled(false);
		mErrorList.setOverScrollMode(View.OVER_SCROLL_NEVER);
		mErrorList.setAdapter(mAdapter);
		
		mBtnClose = (Button) findViewById(R.id.engin_check_close_btn);
		mBtnClose.setOnClickListener(this);
		
		mUiHandler.sendEmptyMessage(0);
	}
	
	private Handler mUiHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			
			String time = null;
			double mileage = 0;
			
			//시간
			if(MainApplication.TIME != null){
				time = MainApplication.TIME;
			}
			
			//거리
//			mileage = Double.parseDouble(MainApplication.MILEAGECOUNT);
			mileage = MainApplication.getSava_mileage();
			
			int timemm = Integer.parseInt(time) / 60;
			int timess = Integer.parseInt(time) % 60;
			
			mTimeMM.setText(Utils.CutStr(timemm > 0 ? timemm : 0)+" : ");
			mTimeSS.setText(Utils.CutStr(timess > 0 ? timess : 0));
			mMileage.setText(String.format("%.1f", mileage));
			
			return false;
		}
	});
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.engin_check_close_btn:
			dismiss();
			break;

		}
		
	}

}
