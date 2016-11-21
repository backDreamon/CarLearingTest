package kr.hdd.carleamingTest.common.popup.adapter;

import java.util.ArrayList;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.util.TroubleCodeUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EnginCheckAdatper extends ArrayAdapter<String>{

	private Context mContext = null;
	private ArrayList<String> mArrList = null;
	private LayoutInflater mInflater = null;
	private ViewHolder holder = null;
	
	public EnginCheckAdatper(Context context, int resource, ArrayList<String> arr) {
		super(context, resource);
		
		mContext = context;
		mArrList = arr;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		return mArrList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(R.layout.engin_check_list_item, null);

			holder.mErrorCode = (TextView) convertView.findViewById(R.id.engin_check_bottom_error_code_text);  //에러코드
			holder.mErrorContent = (TextView) convertView.findViewById(R.id.engin_check_bottom_error_code_content);   //에러코드내용

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.mErrorCode.setText(mArrList.get(position));
		holder.mErrorContent.setText(cutStr(mArrList.get(position)));
		
		return convertView;
	}
	
	private String cutStr(String str){
		
		if(str.startsWith("C")){
			return "샤시(Chassis) 에러";
		} else if(str.startsWith("B")) {
			return "바디(Boby) 에러";
		} else if(str.startsWith("U")) {
			return "네트워크(Network) 에러";
		} else {
			return TroubleCodeUtil.getInstance(mContext).getTroubleCodeUtil(str);
		}
	}
	
	private class ViewHolder {
		TextView mErrorCode = null;
		TextView mErrorContent = null;
	} 

}
