package kr.hdd.carleamingTest.common;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public interface CommonConst {
	
	class EXTRAKEY {
		static public final String VIDEO_DATA = "video_data";
		static public final String VIDEO_LIST = "video_list";
	}
	
	class STORAGE {
		
		private static final String ROOT_FOLDER_NAME = "CarLearning";
		private static final String VIDEO_FOLDER_NAME = "Video";					//비디오 폴더

		public static final String VIDEO_PATH = 
				Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + 
						ROOT_FOLDER_NAME + "/" + VIDEO_FOLDER_NAME;
		
		public static final int MAX_DURATION = 5 * 60 * 1000;
		public static final int MAX_FILE_SIZE = 200 * 1024 * 1024;
//		public static final float PERCENTAGE_OF_FILLED = 0.8f;
		
		
		public static String getNormalPath(){

			StringBuffer buffer = new StringBuffer();

			buffer.append(VIDEO_PATH);

			return buffer.toString();		 
		}		

		public static String getFileName(){

			return new StringBuffer().append(getTime()).append(".mp4").toString();			
		}

		private static String getTime(){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			return formatter.format(Calendar.getInstance().getTime());
		}
	}
	
	String KEYCODE_FOLDERSIZE="maxiumFolderSize";
	String defaultFolderSize = "500";
}
