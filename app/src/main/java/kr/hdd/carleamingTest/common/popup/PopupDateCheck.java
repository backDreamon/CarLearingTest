package kr.hdd.carleamingTest.common.popup;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.util.CarLLog;
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
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class PopupDateCheck extends Dialog implements OnClickListener {
	
	private Context mContext = null;
	
	private Button mBtnOk = null;
	private Button mBtnCancel = null;
	
	private DatePicker mStartDate = null;
	private DatePicker mEndDate = null;
	
	private Handler mHandler = null;
	
	private String mStartTime = null;
	private String mEndTime = null;

	public PopupDateCheck (Context context, Handler Handelr){
		super(context, R.style.picker);
		mContext = context;
		mHandler = Handelr;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.date_check_dialog);
		getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);
		
		mBtnOk = (Button) findViewById(R.id.date_check_btn_ok);
		mBtnCancel = (Button) findViewById(R.id.date_check_btn_cancel);

		mStartDate = (DatePicker) findViewById(R.id.popup_start_datepicker);
		mEndDate = (DatePicker) findViewById(R.id.popup_end_datepicker);
		
		mStartDate.init(mStartDate.getYear(), mStartDate.getMonth(), mStartDate.getDayOfMonth(), onstartdatepickerlistener);
		mEndDate.init(mEndDate.getYear(), mEndDate.getMonth(), mEndDate.getDayOfMonth(), onenddatepickerlistener);
		
		mStartTime = Utils.DayDate();
		mEndTime = Utils.DayDate();
		
		mBtnOk.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);
	}
	
	OnDateChangedListener onstartdatepickerlistener = new OnDateChangedListener() {

		@Override
		public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//			String m_selectTime = String.format("%d%d%d", year, monthOfYear + 1, dayOfMonth);
			mStartTime = String.valueOf(year) + Utils.CutStr(monthOfYear +1) + Utils.CutStr(dayOfMonth);
		}
	};
	
	OnDateChangedListener onenddatepickerlistener = new OnDateChangedListener() {

		@Override
		public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

//			String m_selectTime = String.format("%d%d%d", year, monthOfYear + 1, dayOfMonth);

			mEndTime = String.valueOf(year) + Utils.CutStr(monthOfYear +1) + Utils.CutStr(dayOfMonth);

		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.date_check_btn_ok:
			Message msg = mHandler.obtainMessage();
			msg.arg1 = Integer.parseInt(mStartTime);
			msg.arg2 = Integer.parseInt(mEndTime);
			CarLLog.w("", "mEndTime: "+ mEndTime);
			msg.what = 3;
			mHandler.sendMessage(msg);
			dismiss();
			break;
			
		case R.id.date_check_btn_cancel:
			dismiss();
			break;
			
		}
	}
	
}
