package kr.hdd.carleamingTest.activity;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.FileData;
import kr.hdd.carleamingTest.common.CommonConst;
import kr.hdd.carleamingTest.common.FileExplorerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class FileExplorerActivity extends Activity {
	private final String TAG= "FileExplorerActivity";
	
	private final static int HISTORY_NONE = 0x00;
	
	private ListView lvFileLst;
	
	private FileExplorerAdapter mFileAdapter;
	private ArrayList<FileData> mFileDataList;
	
	private FileData mClickData;
	private String mSDCardPath;
	private int mHistory;
	
	private Builder mAlertDialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.file_explorer_activity);
		
		lvFileLst = (ListView) findViewById(R.id.lv_file_list);
		
		mSDCardPath = CommonConst.STORAGE.VIDEO_PATH;
		mFileDataList = getDirectoryListing(new File(mSDCardPath));
//		mFileDataList = sortDir(mFileDataList);
		Log.w(TAG, "mFileDataList.size(): "+ mFileDataList.size());
		
		mFileAdapter = new FileExplorerAdapter(getApplicationContext(), R.layout.inflate_file_row, mFileDataList);
		mClickData = new FileData();
		mClickData.file_path = mSDCardPath;
		
		lvFileLst.setAdapter(mFileAdapter);
		
		lvFileLst.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int pos, long arg3) {
				
				mClickData = mFileDataList.get(pos);
				
				if (!mClickData.isDir) {
				
					mAlertDialog = new AlertDialog.Builder(FileExplorerActivity.this);
					mAlertDialog.setTitle(getString(R.string.Alert_noty))
					.setMessage(getString(R.string.delete_file))
					.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener(){
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							
							File file = new File(mClickData.file_path);
							String parent = file.getParent();
							String fileNm = file.getName();
							Log.i("YKLEE", "fileNm : " + fileNm);
//							DataHelper dbHelper = new DataHelper(getApplicationContext());
//							if (dbHelper.isEvent(fileNm)) {
//								Logger.i("YKLEE", "Event Video !!!!!!!!!!!");
//							} else {
//								dbHelper.deleteVideoData(fileNm);
//								dbHelper.deleteHudData(fileNm);
//							}
//							dbHelper.close();
							file.delete();
							mFileDataList = getDirectoryListing(new File(parent));
							mFileAdapter = new FileExplorerAdapter(getApplicationContext(), R.layout.inflate_file_row, mFileDataList);
							lvFileLst.setAdapter(mFileAdapter);
							
							mHistory++;
							onBackPressed();
							
						}})
					.setNegativeButton(getString(R.string.Esc), new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
							mHistory++;
							onBackPressed();
							dialog.dismiss();
							
						}
					});
					mAlertDialog.show();
					
				}
				
				return true;
			}
		});
		
		lvFileLst.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> av, final View v, int pos,
					long arg3) {
				mClickData = mFileDataList.get(pos);
				mHistory++;
				if (mClickData.isDir) {
					Log.i("YKLEE", "File file_path !!!!!!!!!!! : " + mClickData.file_path);
					mFileDataList = getDirectoryListing(new File(mClickData.file_path));
//					mFileDataList = sortDir(mFileDataList);
					mFileAdapter = new FileExplorerAdapter(getApplicationContext(), R.layout.inflate_file_row, mFileDataList);
					lvFileLst.setAdapter(mFileAdapter);
				} else {
					//선택된 이미지로 반전
					Handler haHandler = new Handler();
					haHandler.postDelayed(new Runnable() {
						
						public void run() {
							
							Log.i("YKLEE", "SELECTED!!!");
							Log.i("YKLEE", "File file_path !!!!!!!!!!! : " + mClickData.file_path);
							
							Intent intent = new Intent(FileExplorerActivity.this, VideoPlayerActivity.class);
							intent.putExtra(CommonConst.EXTRAKEY.VIDEO_DATA, mClickData.file_path);
							intent.putExtra(CommonConst.EXTRAKEY.VIDEO_LIST, mFileDataList);
							startActivity(intent);
							
							onBackPressed();							
						}
					}, 100);
				}
			}
		});
		
	}
	
//	private ArrayList<FileData> sortDir(ArrayList<FileData> original){
//		ArrayList<FileData> retDataList = new ArrayList<FileData>();
//		
//		for (int i = 0; i < original.size(); i++) {
//			FileData data = original.get(i);
//			if (data.isDir) {
//				retDataList.add(data);
//			}			
//		}
//		for (int i = 0; i < original.size(); i++) {
//			FileData data = original.get(i);
//			if (!data.isDir) {
//				retDataList.add(data);
//			}			
//		}
//		
//		return retDataList;
//	}
	
	// Comparator 를 만든다.

	private final static Comparator<FileData> myComparator = new Comparator<FileData>() {

		private final Collator collator = Collator.getInstance();

		@Override
		public int compare(FileData object1, FileData object2) {

			return collator.compare(object1.file_path, object2.file_path);

		}

	};
	
	private ArrayList<FileData> getDirectoryListing(File file) {
		ArrayList<FileData> fileList = new ArrayList<FileData>();
		
		if (file.isDirectory()) {
			if (null != file.listFiles()) {
				for (File child : file.listFiles()) {
					fileList.add(getChildDirectoryListing(child));
				}
			}
		}
		
		Collections.sort(fileList, myComparator);
		return fileList;
	}
	private FileData getChildDirectoryListing(File file) {
		FileData fileData = new FileData();
		
		fileData.file_path = file.getPath();
		fileData.file_name = file.getName();
		fileData.isDir = file.isDirectory();
		
		return fileData;
	}
	
	@Override
	public void onBackPressed() {
		if (mHistory > HISTORY_NONE) {
//			Logger.i("YKLEE", "File file_path !!!!!!!!!!! : " + mClickData.file_path);
			mHistory--;
			File file = new File(mClickData.file_path);
			String parent = file.getParent();
			mClickData.file_path = new File(parent).getPath();
			mFileDataList = getDirectoryListing(new File(parent));
//			mFileDataList = sortDir(mFileDataList);
			mFileAdapter = new FileExplorerAdapter(getApplicationContext(), R.layout.inflate_file_row, mFileDataList);
			lvFileLst.setAdapter(mFileAdapter);
		} else {
			super.onBackPressed();
		}		
	}

}
