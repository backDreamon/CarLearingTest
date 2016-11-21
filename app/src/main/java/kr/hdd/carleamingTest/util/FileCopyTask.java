package kr.hdd.carleamingTest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import kr.hdd.carleamingTest.common.CommonConst;
import android.os.AsyncTask;


/**
 * 저장된 파일을 이벤트 폴더로 복사
 * @author ming
 */
public class FileCopyTask extends AsyncTask<Void, Void, Void>{
	
	private String mFileName = null;
	
	public FileCopyTask(String fileName){
		mFileName = fileName;
	}
	

	@Override
	protected Void doInBackground(Void... params) {
		
		File file = new File(CommonConst.STORAGE.getNormalPath() + "/" + mFileName);
//		File copyFile = new File(CommonConst.STORAGE.getEventPath() + "/" + mFileName);
		
		FileInputStream inStream = null;
//		FileOutputStream outStream = null;
		
		try {
			inStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		try {
//			outStream = new FileOutputStream(copyFile);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		
		FileChannel chIn = inStream.getChannel();
//		FileChannel chOut = outStream.getChannel();
		
		long size = 0;
		try {
			size = chIn.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			chIn.transferTo(0, size, chOut);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
//			chOut.close();
			chIn.close();
//			outStream.close();
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
