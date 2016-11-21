package kr.hdd.carleamingTest.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.FileData;
import kr.hdd.carleamingTest.common.CommonConst;
import kr.hdd.carleamingTest.common.VideoView;
import kr.hdd.carleamingTest.util.Utils;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class VideoPlayerActivity extends Activity implements OnSeekBarChangeListener, OnClickListener {
	
    private static final String TAG = VideoPlayerActivity.class.getSimpleName();
	/** Called when the activity is first created. */
    private final String TIME_FORMAT = "k:mm:ss";
	
    private SeekBar mBar;
	private Button mStartBtn;
	private Button mCloseBtn;
//	private TextView mSpeedText;
	private TextView mTimeText;
	private ImageView mThumbImg;
		
	private static VideoView mVideoView;
	
	// Thread 관련
    private ProgressThread mThread = null;
	private boolean mThreadState = true;
	
	private Uri videoUri;
	private String videoPath;
	private ArrayList<FileData> mVideoList;
	
    private boolean mIsFirstPlay = false;
    
    private int mSeekTime = 0;
    private int mTime = 0;
    
//    private HudData mHudData;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.video_view);
        
        mBar = (SeekBar)findViewById(R.id.seek);
        mStartBtn = (Button)findViewById(R.id.startBtn);
        mCloseBtn = (Button)findViewById(R.id.exitBtn);
        mVideoView = (VideoView)findViewById(R.id.video_view);
//        mSpeedText = (TextView)findViewById(R.id.speedText);
        mTimeText = (TextView)findViewById(R.id.timeText);
        mThumbImg = (ImageView)findViewById(R.id.video_thumb_view);
        
        mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_play_off);
        
        videoUri = null;
        
        Intent intent = getIntent();
        videoPath = intent.getStringExtra(CommonConst.EXTRAKEY.VIDEO_DATA);
        mVideoList = (ArrayList<FileData>) intent.getSerializableExtra(CommonConst.EXTRAKEY.VIDEO_LIST);
        for (FileData data : mVideoList) {
			Log.i("YKLEE", "File List : " + data.file_path);
		}
        
        mBar.setOnSeekBarChangeListener(this);
        
        mStartBtn.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);
        
        mThreadState = true;
        mIsFirstPlay = true;
        
        Log.d(TAG, "onCreate");
        
        mBar.setProgress(0);
        mVideoView.setStartBtn(mStartBtn);
        mTime = 0;
		
        Bitmap bmThumbnail;
//		bmThumbnail = MediaStore.Video.Thumbnails.getThumbnail(cr, id, MediaStore.Video.Thumbnails.MINI_KIND, null); 
        bmThumbnail = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(videoPath, Thumbnails.FULL_SCREEN_KIND)
        		, Utils.getDeviceDisplay(this, true), Utils.getDeviceDisplay(this, false), 4);//FULL_SCREEN_KIND MINI_KIND
        mThumbImg.setImageBitmap(bmThumbnail);

		mTimeText.setVisibility(View.GONE);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    	Log.i(TAG, "Video Player onPause");
    	mVideoView.pause();
    	mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_play_off);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	Log.i(TAG, "Video Player onResume");
    	if (!mIsFirstPlay) {
    		mVideoView.start();
    		mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_pause_off);
		}    	
    }

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		mVideoView.seekTo(seekBar.getProgress());
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

    	return super.onKeyDown(keyCode, event);
    }
    
    private void startPlayer()
    {
		if(mVideoView == null) return;
		
		if(mIsFirstPlay) {
	        if( videoUri != null) {
	        	mVideoView.setVideoPath(videoUri);
	        }
	        else if( videoPath != null) {
	        	File mFile = new File(videoPath);
	        	mThread = new ProgressThread();
	        	mVideoView.setVideoInput(mFile, mBar, mThread, mVideoList);
	        	mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_pause_off);
	        }
	        mIsFirstPlay = false;
	        findViewById(R.id.control).setBackgroundColor(Color.parseColor("#55000000"));
		}
		else {
			
			if(mVideoView.isPlaying()){
				mVideoView.pause();
				mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_play_off);
			}
			else {
				mVideoView.start();
				mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_pause_off);
			}
		}

        
		mThumbImg.setVisibility(View.GONE);
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == mStartBtn) {
			startPlayer();
		}
		else if(v == mCloseBtn) {
			finish();
		}
	}
	
	class ProgressThread extends Thread {
		
		@Override
		public void run() {
			while(mThreadState){
				
				//Log.d(TAG, "mVideoView.getCurrenPosition() = " + mVideoView.getCurrenPosition());
				
				if(mVideoView.isPlaying()){
					int pos = mVideoView.getCurrenPosition();
					mHandler.sendEmptyMessage(pos);
//					mHudData = mVideoView.getHudData(Math.round((pos / 1000)*2));
				}
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					
				}
			}
		}
	}
	
    Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			
			Log.i(TAG, "handleMessage msg.what = " + msg.what);
//			mTime++;

			if(!mTimeText.isShown())
				mTimeText.setVisibility(View.VISIBLE);
			mTimeText.setText(getIntTime(msg.what));
			
			mBar.setProgress(msg.what);
		}
	};
	
	private String getIntTime(int millisec) {
		// TODO Auto-generated method stub
		
        int totalSeconds = millisec / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        
        return mFormatter.format("%02d : %02d : %02d", hours, minutes, seconds).toString();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(mThread != null){
			mThreadState = false;
			mThread.interrupt();
		}
		
		if(mVideoView != null) {
			
			mVideoView.stopPlayback();
		}
		
		super.onDestroy();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		
		Log.d(TAG, "onWindowFocusChanged hasFocus = " + hasFocus);
	
		if(mVideoView == null) return;
		
		if(!hasFocus) {
			mVideoView.pause();
			mSeekTime = mVideoView.getCurrenPosition();
		}
		else {
			mVideoView.seekTo(mSeekTime);
			mVideoView.start();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		Log.d(TAG, "onConfigurationChanged");
	}
}