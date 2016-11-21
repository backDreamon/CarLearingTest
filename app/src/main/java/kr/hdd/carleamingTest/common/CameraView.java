package kr.hdd.carleamingTest.common;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import kr.hdd.carleamingTest.activity.BlackBoxActivity;
import kr.hdd.carleamingTest.activity.data.VideoData;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


/**
 * 녹화, 프리뷰 용 뷰
 * @author ming
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
	private final String TAG= "CameraView";
	
	private Context mContext;
	private Handler mTextHandler= null;
	
	private SurfaceHolder mHolder;
	private Camera mCamera;
	private MediaRecorder mediaRecorder;
	
	private VideoData mData;
	
	private boolean isRecording = false;
	private int mCameraId= -1;
	
	public CameraView(Context context) {
		super(context);
		mContext = context;
		init();		
	}
	
	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init(){
		mCamera = getCameraInstance();
		if (mCamera == null) {
			Toast.makeText(mContext, "Fail to get Camera", Toast.LENGTH_LONG).show();
		}

//		checkGet(mCameraId);
        getSupportedVideoSizes(mCameraId);
        
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	private int findFrontFacingCamera() {
		int cameraId = -1;
		// Search for the front facing camera
		int numberOfCameras = Camera.getNumberOfCameras();
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	private int findBackFacingCamera() {
		int cameraId = -1;
		//Search for the back facing camera
		//get the number of cameras
		int numberOfCameras = Camera.getNumberOfCameras();
		//for every camera check
		for (int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}
	
	private Camera getCameraInstance() {

		Camera c = null;
		try {
			mCameraId = findBackFacingCamera();
//			Log.w(TAG, "222 cameraId: "+ cameraId);
			if (mCameraId < 0) {
				//open the backFacingCamera
				mCameraId = findFrontFacingCamera();
			}
//			Log.w(TAG, "111 cameraId: "+ cameraId);

			if (mCameraId >= 0) 
				c = Camera.open(mCameraId);
			else {
				Toast.makeText(mContext, "카메라를 찾을 수 없습니다.", Toast.LENGTH_LONG).show();
				
				Activity activity = (Activity) mContext;
				activity.finish();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	/**
	 * start recording
	 */
	public void startRecording(){
		
		mData = new VideoData();
		
		releaseCamera();

		try {
			if (!prepareMediaRecorder()) {
				Toast.makeText(mContext, "Fail in MediaRecorder \n prepare", Toast.LENGTH_LONG).show();
				
				Activity activity = (Activity) mContext;
				activity.finish();
			}
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(mContext, "Fail in MediaRecorder \n prepare", Toast.LENGTH_LONG).show();
			Activity activity = (Activity) mContext;
			activity.finish();			
		}

		isRecording = true;
//		mediaRecorder.start();
		startHandler.sendEmptyMessage(0);
		mData.setStartDate(System.currentTimeMillis());
		
	}

	private Handler startHandler= new Handler(){

		public void handleMessage(Message msg) {
			mediaRecorder.start();
		}
	};
	
	/**
	 * stop the recording and release camera, mediaRecorder object
	 */
	public void stopRecording(){
		
		if( ! isRecording)	return;
		isRecording = false;

		try {
			if(mediaRecorder!= null)
				mediaRecorder.stop();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

		releaseMediaRecorder();
		if(mTextHandler!= null) mTextHandler.sendEmptyMessage(BlackBoxActivity.MSG_TEXT_STOP);
		
		mData.setEndDate(System.currentTimeMillis());		

		//10개 이상일때 오래된 것 삭제
		FilenameFilter fileFilter = new FilenameFilter()  //이부분은 특정 확장자만 가지고 오고 싶을 경우 사용하시면 됩니다.
		{
			public boolean accept(File dir, String name) 
			{
				return name.endsWith("mp4"); //이 부분에 사용하고 싶은 확장자를 넣으시면 됩니다.
			} //end accept
		};
		File[] files = new File(CommonConst.STORAGE.getNormalPath()).listFiles(fileFilter); //경로에 있는 파일목록구함
		files = sortFileList(files);  // Name으로 Sort실행		

		if(files!= null && files.length> 10) {
			for(int i= 0; i< files.length- 10; i++) {
				File dFile= files[i];
//				Log.w(TAG, "111 dFile: "+ dFile);
				if(dFile.exists()) dFile.delete();
			}
		}
	}
	
	public File[] sortFileList(File[] files)
	{
		Arrays.sort(files, new Comparator<Object>() {
			
			@Override
			public int compare(Object object1, Object object2) {

				String s1 = "";
				String s2 = "";

				s1 = ((File)object1).getName();
				s2 = ((File)object2).getName();         

				return s1.compareTo(s2);
			}
		});

		return files;
	}

	/**
	 * set MediaRecorder object
	 */
	private boolean prepareMediaRecorder() throws IOException {		

		if(mCamera== null) mCamera = getCameraInstance();
		int rate= 30;

		try {
			mediaRecorder = new MediaRecorder();
			mediaRecorder.setOnInfoListener(mMaxReachedListener);
			mediaRecorder.setOnErrorListener(mRecordingErrorListener);

			mCamera.unlock();
			mediaRecorder.setCamera(mCamera);

			//아웃풋포맷, 인코더가 프로파일로 대체됨.
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

			mediaRecorder.setProfile(CamcorderProfile.get(mCameraId, CamcorderProfile.QUALITY_720P));

//			mediaRecorder.setProfile(CamcorderProfile.get(mCameraId, CamcorderProfile.QUALITY_HIGH));
//			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//			mediaRecorder.setVideoSize(1280, 720);
//			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//			mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			
//			mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));
			mediaRecorder.setMaxDuration(CommonConst.STORAGE.MAX_DURATION); 
			mediaRecorder.setMaxFileSize(CommonConst.STORAGE.MAX_FILE_SIZE);
			mediaRecorder.setVideoFrameRate(30);

			mediaRecorder.setOutputFile(getFilePath());
			mediaRecorder.setPreviewDisplay(getHolder().getSurface());
			mediaRecorder.prepare();

		} catch (IllegalStateException e) {
			e.printStackTrace();
			releaseAll();
			return false;
		}
		return true;
	}
	

	private String getFilePath(){
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(CommonConst.STORAGE.getNormalPath()).append("/");
		
		String fileName = CommonConst.STORAGE.getFileName();
		mData.setFileName(fileName);
		
		buffer.append(fileName);
		
		return buffer.toString();
	}
	
	/**
	 * clear recorder object and release 
	 */
	private void releaseMediaRecorder() {
		
		if (mediaRecorder != null) {
			
			mediaRecorder.reset(); 
			mediaRecorder.release();
			mediaRecorder = null;			
		}
	}
	
	public String getVodFileName(){
		return mData.file_name;
	}	

	/**
	 * release camera object
	 */
	private void releaseCamera() {
		
		if (mCamera != null) {
			
			mCamera.lock();
			mCamera.release();			
			mCamera = null;
		}
	}
	
	/**
	 * release all object
	 */
	public void releaseAll(){
		
		releaseMediaRecorder(); 
		releaseCamera(); 
	}
	
	/**
	 * called when mediaRecorder file size or duration limit over
	 */
	private MediaRecorder.OnInfoListener mMaxReachedListener = new MediaRecorder.OnInfoListener() {		
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			
			if(what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED ||
					what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED){
				
				if( isRecording) {
					stopRecording();
					startRecording();
				}				
			}
		}
	};
	
	
	/**
	 * called when occur error during recording
	 */
	private MediaRecorder.OnErrorListener mRecordingErrorListener = new MediaRecorder.OnErrorListener() {		
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			// TODO Auto-generated method stub
			
		}
	};
	

	public boolean isRecording(){
		return isRecording;
	}

	public void setHandler(Handler handle){
		mTextHandler= handle;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int weight, int height) {
		Log.w(TAG, "surfaceChanged()");

		if (mHolder.getSurface() == null) {
			return;
		}
		
		if(mCamera != null){
			try {
				mCamera.stopPreview();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		try {

			if(mCamera == null)	mCamera = getCameraInstance();

			Camera.Parameters params = mCamera.getParameters();
//			params.setPreviewFrameRate(30);
//			params.setPreviewFpsRange(30000, 30000);
			params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
			mCamera.setParameters(params);

			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.w(TAG, "surfaceCreated()");

		try {			
			if(mCamera == null)	mCamera = getCameraInstance();

			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.w(TAG, "surfaceDestroyed()");
		stopRecording();
		releaseAll();
	}
	
	private void getSupportedPreviewFrameRates() {
        Parameters parameters = mCamera.getParameters();
        List<int[]> Frame = parameters.getSupportedPreviewFpsRange();
        for(int i= 0; i< Frame.size(); i++) {
            for(int j= 0; j< Frame.get(i).length; j++) {
            Log.w(TAG, "FpsRange: "+ Frame.get(i)[j]);
            }
        }
        
        List<Integer> Frame1 = parameters.getSupportedPreviewFrameRates();
        for(int i= 0; i< Frame1.size(); i++) {
        	Log.w(TAG, "FrameRates: "+ Frame1.get(i));
        }
	}

	private List<Size> getSupportedVideoSizes(int cameraId) {
		getSupportedPreviewFrameRates();
		
//        Camera camera = (cameraId == -1)? Camera.open(): Camera.open(cameraId);
        Parameters parameters = mCamera.getParameters();
        List<Size> videoSizes = parameters.getSupportedVideoSizes();
        for(int i= 0; i< videoSizes.size(); i++) {
            Log.w(TAG, String.format("specific profile: width = %d, height = %d",
                    videoSizes.get(i).width, videoSizes.get(i).height));
        }
//        if (videoSizes == null) {
//            videoSizes = parameters.getSupportedPreviewSizes();
//        }
        return videoSizes;
    }
	
	/*
	private void checkGet(int cameraId) {
        Log.w(TAG, "checkGet(): "+ cameraId);
        
        CamcorderProfile lowProfile = getWithOptionalId(CamcorderProfile.QUALITY_LOW, cameraId);
        CamcorderProfile highProfile = getWithOptionalId(CamcorderProfile.QUALITY_HIGH, cameraId);
        
        checkProfile(lowProfile);
        checkProfile(highProfile);
                
        int[] specificProfileQualities = {CamcorderProfile.QUALITY_QCIF, CamcorderProfile.QUALITY_QVGA,
                                          CamcorderProfile.QUALITY_CIF, CamcorderProfile.QUALITY_480P,
                                          CamcorderProfile.QUALITY_720P, CamcorderProfile.QUALITY_1080P};
        
        checkSpecificProfiles(cameraId, lowProfile, highProfile, specificProfileQualities);
    }

    private void checkProfile(CamcorderProfile profile) {
        Log.v(TAG, String.format("profile: duration=%d, quality=%d, " +
            "fileFormat=%d, videoCodec=%d, videoBitRate=%d, videoFrameRate=%d, " +
            "videoFrameWidth=%d, videoFrameHeight=%d, audioCodec=%d, " +
            "audioBitRate=%d, audioSampleRate=%d, audioChannels=%d",
            profile.duration, profile.quality, profile.fileFormat, profile.videoCodec, profile.videoBitRate,
            profile.videoFrameRate, profile.videoFrameWidth, profile.videoFrameHeight, profile.audioCodec,
            profile.audioBitRate, profile.audioSampleRate, profile.audioChannels));
    }

	// Uses get without id if cameraId == -1 and get with id otherwise.
    private CamcorderProfile getWithOptionalId(int quality, int cameraId) {
        Log.w(TAG, "quality: "+ quality);
        if (cameraId == -1) {
            return CamcorderProfile.get(quality);
        } else {
            return CamcorderProfile.get(cameraId, quality);
        }
    }
    
    private void checkSpecificProfiles( int cameraId, CamcorderProfile low, CamcorderProfile high, int[] specificQualities) 
    {
        CamcorderProfile minProfile = null;
        CamcorderProfile maxProfile = null;
        for (int i = 0; i < specificQualities.length; i++) {
            int quality = specificQualities[i];
            if ((cameraId != -1 && CamcorderProfile.hasProfile(cameraId, quality)) ||
                (cameraId == -1 && CamcorderProfile.hasProfile(quality))) {
                CamcorderProfile profile = getWithOptionalId(quality, cameraId);
                Log.v(TAG, String.format("specific profile: quality=%d, width = %d, height = %d",
                        profile.quality, profile.videoFrameWidth, profile.videoFrameHeight));
                if (minProfile == null) {
                    minProfile = profile;
                }
                maxProfile = profile;
            }
        }

        Log.i(TAG, String.format("min profile: quality=%d, width = %d, height = %d",
                    minProfile.quality, minProfile.videoFrameWidth, minProfile.videoFrameHeight));
        Log.i(TAG, String.format("max profile: quality=%d, width = %d, height = %d",
                    maxProfile.quality, maxProfile.videoFrameWidth, maxProfile.videoFrameHeight));
    }*/
	
}