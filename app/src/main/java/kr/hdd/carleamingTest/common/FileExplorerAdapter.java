package kr.hdd.carleamingTest.common;

import java.util.ArrayList;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.FileData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileExplorerAdapter extends ArrayAdapter<FileData> {
	
	private Context mContext;
	private LayoutInflater mInflater;
	private FileViewHolder mFileViewHolder;
	private ArrayList<FileData> mFileDataList;
	private int mTextViewResourceId;

	public FileExplorerAdapter(Context context, int textViewResourceId,
			ArrayList<FileData> data) {
		super(context, textViewResourceId, data);
		
		mContext = context;
		mFileDataList = data;
		mTextViewResourceId = textViewResourceId;
	}
	
	public void setArrayData(ArrayList<FileData> data){
		mFileDataList = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFileDataList.size();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final FileData data = mFileDataList.get(position);

		if(convertView == null){
			mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(mTextViewResourceId, null);
			mFileViewHolder = new FileViewHolder();
			
			mFileViewHolder.tvFileName = (TextView) convertView.findViewById(R.id.tv_file_name);
	
			convertView.setTag(mFileViewHolder);
		} else {
			mFileViewHolder = (FileViewHolder) convertView.getTag();
		}
		
		mFileViewHolder.tvFileName.setText(data.file_name);
		
		return convertView;
	}
	
	class FileViewHolder {
		private TextView tvFileName;
	}

}
