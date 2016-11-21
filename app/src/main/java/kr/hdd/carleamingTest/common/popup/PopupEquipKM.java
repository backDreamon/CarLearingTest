package kr.hdd.carleamingTest.common.popup;

import kr.hdd.carleamingTest.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class PopupEquipKM extends Dialog implements OnClickListener{

	private Context mContext = null;

	private EditText mCarKMEdit = null; 

	private Button mBtnOk = null;
	private Button mBtnCancel = null;

	private String mSpinnerValue = null;

	private Handler mHandler = null;


	public PopupEquipKM(Context context, Handler handler) {
		super(context);
//		setContentView(R.layout.car_info_edit_dialog);
		mContext = context;
		mHandler = handler;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.eqip_km_edit_dialog);
		getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);

		mCarKMEdit = (EditText) findViewById(R.id.edit_car_km);  

		mBtnOk = (Button) findViewById(R.id.btn_ok);
		mBtnCancel = (Button) findViewById(R.id.btn_cancel);

		mBtnOk.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);

		mCarKMEdit.addTextChangedListener(textwatcherlistener);
	}
	
	TextWatcher textwatcherlistener = new TextWatcher() {
		
		@Override
		public void onTextChanged(final CharSequence s, int start, int before, int count) {
			if(s.length() > 7){
				AlertDialog.Builder gsDialog = new AlertDialog.Builder(mContext); 
				gsDialog.setTitle("알림");   
				gsDialog.setMessage("더이상 입력 할 수 없습니다."); 
				gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() { 
					public void onClick(DialogInterface dialog, int which) { 
						String string = s.toString();
						string.substring(0, s.length()-1);
						dialog.dismiss();
					} 
				}).create().show();
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		}
	};


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:

			Message msg = mHandler.obtainMessage();
			msg.obj = mCarKMEdit.getText().toString();
			mHandler.sendMessage(msg);
			break;

		case R.id.btn_cancel:
			break;

		}
		dismiss();
	}
}
