package kr.hdd.carleamingTest.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.FileData;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.SeekBar;

public class VideoView extends SurfaceView {

	private static final String TAG = VideoView.class.getSimpleName();
	private Context mContext;

	// 가로,세로 변수
	private int mVideoWidth;
	private int mVideoHeight;
	private int mSurfaceWidth;
	private int mSurfaceHeight;

	// player 변수
	private SurfaceHolder mSurfaceHolder = null;
	private MediaPlayer mMediaPlayer = null;
	
	// 리스너
	private OnPreparedListener mOnPreparedListener;
	protected OnCompletionListener mOnCompletionListener;

	// State 변수
	private boolean mIsPrepared = false;
	private boolean mStartWhenPrepared = false;
	private int mSeekWhenPrepared = 0;
	private int mDuration;

	private Uri mUri;

	private File mFile;
	private SeekBar mBar;
	private Thread mThread;
	private ArrayList<FileData> mVideoList;

	private int mSeekPosition = 0;

	private int screenMode = 0;
	
	private Button mStartBtn;
	
//	private ArrayList<HudData> mHudDataList;
//	private DataHelper mDbHelper;
	private int mHudDataIndex;

	// 상수
	public static int ORIGINAL = 0;    // fit to screen
	public static int FULL_SCREEN = 1;       // original aspect ratio
	public static int ZOOM = 2;            // zoom in
	public static int PORTRAIT_MODE = 3; // 가로모드

	public VideoView(Context context)
	{
		super(context);
		mContext = context;
		initView();
	}

	public VideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
	}

	public VideoView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mContext = context;
		initView();
	}
	
	public void setStartBtn(Button startBtn){
		this.mStartBtn = startBtn;
	}

	private void initView() {
		// TODO Auto-generated method stub
		// 변수 초기화
		mVideoWidth = 0;
		mVideoHeight = 0;

		Log.d(TAG, "initView");

		//Surface Callback 등록
		getHolder().addCallback(mSHCallback);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
	}

	public void setVideoPath(Uri uri){
		mUri = uri;

		preparedVideo();
	}

	public void setVideoInput(File file, SeekBar bar, Thread thread, ArrayList<FileData> list){
		mFile = file;
		mBar = bar;
		mThread = thread;
		mVideoList = list;

		preparedVideo();
	}

	private void preparedVideo(){

		mStartWhenPrepared = false;
		mSeekWhenPrepared = 0;

		if ( mMediaPlayer != null )
		{
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

		openVideo();
		requestLayout();
		invalidate();
	}

	private void openVideo() {
		// TODO Auto-generated method stub
		if ( mFile == null || mSurfaceHolder == null ) {
			// not ready for playback just yet, will try again later
			Log.d(TAG, "mFile = " + mFile);
			Log.d(TAG, "mSurfaceHolder = " + mSurfaceHolder);
			return;
		}

		// Tell the music playback service to pause
		// TODO: these constants need to be published somewhere in the framework.
		Intent i = new Intent("com.android.music.musicservicecommand");
		i.putExtra("command", "pause");
		i.addFlags(32);
		mContext.sendBroadcast(i);

		Log.d(TAG, "openVideo");
		Log.d(TAG, "mFile = " + mFile);

		try {
			if(mFile.isFile()) {
				Log.d(TAG, "file is exist");
			}
			else {
				Log.d(TAG, "not file");
			}

		}
		catch (Exception e) {
			Log.d(TAG, "Exception e = " + e);
			return;
		}        

		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.reset();
			mMediaPlayer.setOnPreparedListener(mPreparedListener);  //동영상이 재생준비가 완료 되었을때
			//mMediaPlayer.setDataSource(new FileInputStream(mFile).getFD(), 1, mFile.length());
			//String mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Movie.mp4";
			//mMediaPlayer.setDataSource(mPath);
			mMediaPlayer.setDataSource(mFile.getAbsolutePath());
			mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);  //뷰의 크기를 변경할수 있다.
			mMediaPlayer.setOnCompletionListener(mCompletionListener);  //재생완료를 알수있음
			mDuration = -1;
			mMediaPlayer.setDisplay(mSurfaceHolder); // 동영상출력
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//벨소리
			mMediaPlayer.setScreenOnWhilePlaying(true);  //화면 꺼지지 않게 
			mMediaPlayer.prepareAsync();  //다른 동작을 수행하면서 음원의 재생이 준비가 되면 위의 setOnPreparedListener()를 통해서 이를 알려줍니다
		}
		catch ( IOException ex )
		{
			Log.w(TAG, "IOException Unable to open content: " + mFile, ex);
			return;
		}
		catch ( IllegalArgumentException ex )
		{
			Log.w(TAG, "Unable to open content: " + mFile, ex);
			return;
		}

	}

	public void start() {

		if ( mMediaPlayer != null && mIsPrepared )
		{
			mMediaPlayer.start();
			mStartWhenPrepared = false;
			if(mThread.getState() == Thread.State.NEW)
				mThread.start();
		}
		else
		{
			mStartWhenPrepared = true;
		}
	}

	public void pause()
	{
		if ( mMediaPlayer != null && mIsPrepared )
		{
			if ( mMediaPlayer.isPlaying() )
			{
				mMediaPlayer.pause();
				if(mThread.getState() == Thread.State.RUNNABLE)
					mThread.interrupt();
			}
		}
		mStartWhenPrepared = false;
	}

	public int getCurrenPosition() {

		if ( mMediaPlayer != null && mIsPrepared ) {
//			Log.i("YKLEE", "mMediaPlayer.getCurrentPosition() : " + mMediaPlayer.getCurrentPosition());
			return mMediaPlayer.getCurrentPosition();
		}
		else return -1; 
	}

	public boolean isPlaying()
	{
		if ( mMediaPlayer != null && mIsPrepared )
			return mMediaPlayer.isPlaying();
		return false;
	}

	public void seekTo(int msec)
	{

		if ( mMediaPlayer != null && mIsPrepared )
		{

			mMediaPlayer.seekTo(msec);
		}
		else
		{
			mSeekWhenPrepared = msec;
		}
	}

	public int getDuration()
	{
		if ( mMediaPlayer != null && mIsPrepared )
		{
			if ( mDuration > 0 )
				return mDuration;
			mDuration = mMediaPlayer.getDuration();
//			Log.i("YKLEE", "mDuration : " + mDuration);
			return mDuration;
		}
		mDuration = -1;
		return mDuration;
	}

	public void stopPlayback()
	{
		if ( mMediaPlayer != null )
		{
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	public void setSeekPosition(int position){
		mSeekPosition = position;
	}

	public void changeVideoSize(int width, int height)
	{
		mVideoWidth = width;       
		mVideoHeight = height;

		// not sure whether it is useful or not but safe to do so
		getHolder().setFixedSize(width, height);
		
		screenMode = 1;

		requestLayout();
		invalidate();     // very important, so that onMeasure will be triggered
	}

	public void setScreenMode(int mode){
		screenMode = mode;
		
		requestLayout();
		invalidate();     // very important, so that onMeasure will be triggered
	}
	
    public int resolveAdjustedSize(int desiredSize, int measureSpec)
    {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch ( specMode )
        {
            case MeasureSpec.UNSPECIFIED:
                /* Parent says we can be as big as we want. Just don't be larger
                 * than max size imposed on ourselves.
                 */
                result = desiredSize;
                break;

            case MeasureSpec.AT_MOST:
                /* Parent says we can be as big as we want, up to specSize.
                 * Don't be larger than specSize, and don't be larger than
                 * the max size imposed on ourselves.
                 */
                result = Math.min(desiredSize, specSize);
                break;

            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = specSize;
                break;
        }
        return result;
    }

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//Log.d(TAG, "keycode = " + keyCode);

		if ( mIsPrepared && keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_VOLUME_UP
				&& keyCode != KeyEvent.KEYCODE_VOLUME_DOWN && keyCode != KeyEvent.KEYCODE_MENU
				&& keyCode != KeyEvent.KEYCODE_CALL && keyCode != KeyEvent.KEYCODE_ENDCALL && mMediaPlayer != null
		)
		{
			if ( keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE )
			{
				if ( mMediaPlayer.isPlaying() )
				{
					pause();

				}
				else
				{
					start();

				}
				return true;
			}
			else if ( keyCode == KeyEvent.KEYCODE_MEDIA_STOP && mMediaPlayer.isPlaying() )
			{
				pause();

			}
			else
			{

			}
		}

		return super.onKeyDown(keyCode, event);
	}	

	// 상태변화 감지
	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {

		// 소멸
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Log.e(TAG, "surfaceCreated");
			mSurfaceHolder = null;

			pause();
		}

		// 생성
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			Log.e(TAG, "surfaceCreated");
			mSurfaceHolder = holder;
			
			if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
				mMediaPlayer.setDisplay(mSurfaceHolder);
				start();
			}
			
		}

		// 사이즈나 포맷 변화
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
			Log.d(TAG, "surfaceChanged = " + mSurfaceWidth + "/" + mSurfaceHeight);

			mSurfaceWidth = width;
			mSurfaceHeight = height;

			Log.d(TAG, "mVideo = " + width + "/" + height);

			if(mMediaPlayer != null && mIsPrepared && mVideoWidth == width && mVideoHeight == height) {

				if (mSeekWhenPrepared != 0) {
					mSeekWhenPrepared = 0;
				}

				if(mStartWhenPrepared && mSeekPosition > 0) {
					seekTo(mSeekPosition);
					start();

					if(!mThread.isAlive()){
						mThread.start();
					}
				}
			}
		}
	};

	MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {
			// TODO Auto-generated method stub
			//동영상이 준비 되었습니다. 재성버튼을 누르세요.
			
			Log.d(TAG, "onPrepared");

			if(mThread != null && !mThread.isAlive()){
				mThread.start();
			}

			mIsPrepared = true;
			mStartWhenPrepared = true;

			if( mOnPreparedListener != null) {
				mOnPreparedListener.onPrepared(mMediaPlayer);
			}

			mVideoHeight = mp.getVideoHeight();
			mVideoWidth = mp.getVideoWidth();

			if(mVideoWidth != 0 && mVideoHeight != 0) {
				Log.i(TAG, "video size: " + mVideoWidth +"/"+ mVideoHeight);
				Log.i(TAG, "mSurface = " + mSurfaceWidth + "/" + mSurfaceHeight);

				if ( mSurfaceWidth >= mVideoWidth && mSurfaceHeight >= mVideoHeight )
				{
					getHolder().setFixedSize(mVideoWidth, mVideoHeight);
					Log.i(TAG, "video size: " + mVideoWidth +"/"+ mVideoHeight);
				}
				else {
					getHolder().setFixedSize(mSurfaceWidth, mSurfaceHeight);
					Log.i(TAG, "mSurface = " + mSurfaceWidth + "/" + mSurfaceHeight);
				}

				if ( mSeekWhenPrepared != 0 )
				{
					seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				else if ( mStartWhenPrepared )
				{
					start();
					mStartWhenPrepared = false;
				}

				mBar.setMax(getDuration());

			}else
			{
				// We don't know the video size yet, but should start anyway.
				// The video size might be reported to us later.
				if ( mSeekWhenPrepared != 0 )
				{
					seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				else if ( mStartWhenPrepared )
				{
					start();
					mStartWhenPrepared = false;
				}
			}
		}

	};
	
    MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener()
    {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
        {
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            
            Log.d(TAG, "onVideoSizeChanged = " + mVideoWidth + "/" + mVideoHeight);
            
            if ( mVideoWidth != 0 && mVideoHeight != 0 )
            {
                getHolder().setFixedSize(mVideoWidth, mVideoHeight);
            }
        }
    };
    
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener()
    {
        public void onCompletion(MediaPlayer mp)
        {
        	//동영상 재생이 완료된걸 알수 있는 리스너
        	Log.i("YKLEE", "onCompletion");
           
            if ( mOnCompletionListener != null )
            {
                mOnCompletionListener.onCompletion(mMediaPlayer);
            }
        	mBar.setProgress(0);
        	if(null != mStartBtn){
        		mStartBtn.setBackgroundResource(R.drawable.btn_s_btm_play_off);
        	}
        	if (null != mVideoList) {
				nextVidoPlay();
			}
        }
    };
    
    private void nextVidoPlay(){
    	Log.i("YKLEE", "PrevFile : " + mFile.getAbsoluteFile());
    	boolean isFind = false;
    	for (FileData data : mVideoList) {
    		if (isFind) {
				mFile = new File(data.file_path);
				Log.i("YKLEE", "NextFile : " + mFile.getAbsoluteFile());
				preparedVideo();
				break;
			}
			if (data.file_path.equalsIgnoreCase(mFile.getAbsolutePath())) {
				isFind = true;
			}
		}
    }
    
    /**
     * Register a callback to be invoked when the media file
     * is loaded and ready to go.
     * 
     * @param l
     *            The callback that will be run
     */
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l)
    {
        mOnPreparedListener = l;
    }

    /**
     * Register a callback to be invoked when the end of a media file
     * has been reached during playback.
     * 
     * @param l
     *            The callback that will be run
     */
    public void setOnCompletionListener(OnCompletionListener l)
    {
        mOnCompletionListener = l;
    }
    

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        
		Log.d(TAG, "onMeasure = " + width + "/" + height);
		Log.d(TAG, "onVideo = " + mVideoWidth + "/" + mVideoHeight);
		Log.d(TAG, "screenMode = " + screenMode);
		
		Log.d(TAG, "onMeasure = " + width + "/" + height);
		// must set this at the end
		setMeasuredDimension(width, height);
	}
}
