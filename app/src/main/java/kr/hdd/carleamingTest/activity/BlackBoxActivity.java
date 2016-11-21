package kr.hdd.carleamingTest.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.common.CameraView;
import kr.hdd.carleamingTest.common.CommonConst;

public class BlackBoxActivity extends Activity implements OnClickListener{
	private static final String TAG= "BlackBoxActivity";

	private Context mContext;

//	private Timer mTimer = null;
//	private TimerTask mTimerTask = null;
	
	private CameraView mCameraView;
	private Button mBtnRec;
	private Button mBtnStop;
	private Button mBtnFolder;
	private TextView mTvRecoding;
	private TextView mtv_cpu_per;
	
//	private boolean isShown= false;
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	
	private float mCpu;
	private int myPid= 0;

	public static int	MSG_TEXT_SHOWN= 0x714;
	public static int	MSG_TEXT_STOP= 0x715;
	public static int	MSG_TEXT_FINISH= 0x716;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mContext = this;
		
		setContentView(R.layout.activity_black_box);

		init();

		mTimerTask = new TimerTask() {

			@Override
			public void run() {
//				Log.w(TAG, "readUsage(): "+ mCpu);
				
				String myMsg= "";

//				myMsg= String.valueOf(readCpuUsagePid());
				myMsg= readCpuUser();
				
				mHandler.obtainMessage(0, myMsg).sendToTarget();
//				mHandler.sendEmptyMessage(1);
			}
		};

		mTimer = new Timer();
		mTimer.schedule(mTimerTask, 0, 700);

		myPid= android.os.Process.myPid();
		Log.i(TAG, "myPid: "+ android.os.Process.myPid());
	}

	private Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {

			if(msg != null){
//				if(msg.what== 0) {
//					mStr= String.valueOf(readUsage());
//				}
//				else if(msg.what== 1) {
//					mStr= readUser();
//				}
				mtv_cpu_per.setText((String)msg.obj);
			}

			return false;
		}
	});
	
	private String readCpuUser() {
		Runtime runtime = Runtime.getRuntime(); 
		Process process; 
		String res = "-0-"; 
		try { 
		        String cmd = "top -n 1"; 
		        process = runtime.exec(cmd); 
		        InputStream is = process.getInputStream(); 
		        InputStreamReader isr = new InputStreamReader(is); 
		        BufferedReader br = new BufferedReader(isr); 
		        String line ; 
		        while ((line = br.readLine()) != null) { 
		        	//Log.w(TAG, "line: "+ line);
		        	String segs[] = line.trim().split("[ ]+"); 
//		        	Log.w(TAG, "segs[]: "+ segs[1]);
		        	if (segs[0].equalsIgnoreCase("User")) {
		        		res = segs[1].replace(",", "");
		        		Log.w(TAG, "res1: "+ res);
		        		return res;
		        	} 
//		        	else if (segs[2].equalsIgnoreCase("System")) {
//		        		res = res+ segs[3].replace(",", "");
//		        		Log.w(TAG, "res2: "+ res);
//		        		return res;
//		        	} 
		        } 
		} catch (Exception e) { 
			e.fillInStackTrace(); 
			Log.e("Process Manager", "Unable to execute top command"); 
		} 
		return "-%";
 		
//	    try {
//
////	        RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
////	        RandomAccessFile reader = new RandomAccessFile("/proc/"+myPid+"/stat", "r");
//	        String load = reader.readLine();
//
//			Log.w(TAG, "load: "+ load);
//	        /*
//	        String[] toks = load.split(" ");
//
//	        long idle1 = Long.parseLong(toks[5]);
//	        long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
//	              + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
//
//	        try {
//	            Thread.sleep(360);
//	        } catch (Exception e) {}
//
//	        reader.seek(0);
//	        load = reader.readLine();
//	        reader.close();
//
//	        toks = load.split(" ");
//
//	        long idle2 = Long.parseLong(toks[5]);
//	        long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
//	            + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
//
//	        return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));
//
//	    } catch (IOException ex) {
//	        ex.printStackTrace();
//	    }
//
//	    */
//
//	        String[] toks = load.split("[ ]+");
//	 
//	        float userMode1 = Float.parseFloat(toks[1]);
//	        float niceMode1 = Float.parseFloat(toks[2]);
//	        float systemMode1 = Float.parseFloat(toks[3]);
//	        float idleMode1 = Float.parseFloat(toks[4]);
//	 
//	        try {
//	            Thread.sleep(360);
//	        } catch (Exception e) {}
//	 
//	        reader.seek(0);
//	        load = reader.readLine();
//	        reader.close();
//	 
//	        toks = load.split("[ ]+");
//	 
//	        float userMode2 = Float.parseFloat(toks[1]);
//	        float niceMode2 = Float.parseFloat(toks[2]);
//	        float systemMode2 = Float.parseFloat(toks[3]);
//	        float idleMode2 = Float.parseFloat(toks[4]);
//	 
//	        float userMode = userMode2 - userMode1;
//	        float niceMode = niceMode2 - niceMode1;
//	        float systemMode = systemMode2 - systemMode1;
//	        float idleMode = idleMode2 - idleMode1;
//	 
//	        float total = userMode + niceMode + systemMode + idleMode;
//	 
//	        float user = (userMode / total) * 100;
//	        float system = (systemMode / total) * 100;
//
//		    return user;//+system;
//	 
//	    } catch (IOException e) {
//	        Log.e(TAG, e.getMessage());
//	    } catch (Exception e) {
//	        Log.e(TAG, e.getMessage());
//	    }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if(mTimer!= null) {
			mTimer.cancel();
			mTimer= null;
		}
		if(mTimerTask!= null) {
			mTimerTask.cancel();
			mTimerTask= null;
		}
	}

	private void init(){
		mCameraView = (CameraView) findViewById(R.id.camera_view);
		
		mBtnRec = (Button) findViewById(R.id.camera_view_btn_rec);
		mBtnStop = (Button) findViewById(R.id.camera_view_btn_stop);
		mBtnFolder = (Button) findViewById(R.id.camera_view_btn_folder);
		mTvRecoding = (TextView) findViewById(R.id.camera_view_recoding);
		mtv_cpu_per = (TextView) findViewById(R.id.tv_cpu_per);

		mBtnRec.setOnClickListener(this);
		mBtnStop.setOnClickListener(this);
		mBtnFolder.setOnClickListener(this);
		
		mCameraView.setHandler(textHandler);
		mTvRecoding.setVisibility(View.GONE);
		
		checkStorage();
	}

	private Handler textHandler= new Handler(){

		public void handleMessage(Message msg) {
			if(msg.what== MSG_TEXT_SHOWN)
				mTvRecoding.setVisibility(View.VISIBLE);

			else if(msg.what== MSG_TEXT_STOP) {
				mTvRecoding.setVisibility(View.GONE);
			}
			else if(msg.what== MSG_TEXT_FINISH) {
				finish();
				
				mTvRecoding.setVisibility(View.GONE);
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.camera_view_btn_rec:
				if( mCameraView.isRecording()){
					Toast.makeText(mContext, getResources().getString(R.string.now_recording), Toast.LENGTH_SHORT).show();
				}else{
					mCameraView.startRecording();
				}

				mTvRecoding.setVisibility(View.VISIBLE);
				break;
				
			case R.id.camera_view_btn_stop:
				if( mCameraView.isRecording())	{
					
					mCameraView.stopRecording();		
				}

				mTvRecoding.setVisibility(View.GONE);
				break;
				
			case R.id.camera_view_btn_folder:
				if( mCameraView.isRecording()){
					Toast.makeText(mContext, getResources().getString(R.string.now_recording), Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent(this, FileExplorerActivity.class);
					startActivity(intent);
				}
				break;
		}
		
	}

	/**
	 * ming 
	 * 디렉토리 생성 
	 */
	private void makeDir(){
		
		File dir = new File(CommonConst.STORAGE.VIDEO_PATH);
		if( ! dir.exists()){
			dir.mkdirs();			
		}
	}
	/**
	 * check the storage and notification
	 */
	private void checkStorage(){
		makeDir();
		
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long availableSize= (long)stat.getBlockSize()* (long)stat.getAvailableBlocks()/ 1024/ 1024;
		Log.w("", "availableSize: "+ availableSize);

		if( availableSize >= 100){
			Log.w("", "availableSize: "+ String.format(mContext.getResources().getString(R.string.available_storage), ""+ availableSize));
//			Toast.makeText(mContext, String.format(mContext.getResources().getString(R.string.available_storage), ""+ availableSize), Toast.LENGTH_LONG).show();
		}
		else {
			Toast.makeText(mContext, mContext.getResources().getString(R.string.fill_storage), Toast.LENGTH_LONG).show();
		}
	}
}
