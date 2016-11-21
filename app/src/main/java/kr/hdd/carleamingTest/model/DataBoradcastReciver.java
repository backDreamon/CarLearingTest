package kr.hdd.carleamingTest.model;

import kr.hdd.carleamingTest.MainApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DataBoradcastReciver extends BroadcastReceiver{
	 final String TAG = "DataBoradcastReciver";
	
	private String mData = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String name = intent.getAction();
		String dataId = intent.getExtras().getString(MainApplication.RECIVER.DATAID);
		String data = intent.getExtras().getString(MainApplication.RECIVER.DATA);
		
		if(name.equals(MainApplication.RECIVER.RECIVER)) {
		
			if(dataId.equals(MainApplication.RECIVER.RPM_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.SPEED_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.DRIVE_TIME_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.ENGINE_LOAD_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.KM_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.MAF_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.FEUL_TYPE_RECIVER)){
				mData = data;
				
			} else if(dataId.equals(MainApplication.RECIVER.ERROR_KM_RECIVER)){
				mData = data;
			} else if(dataId.equals(MainApplication.RECIVER.ENGINE_FUEL_RATE_RECIVER)){
				mData = data;
			}
		}
	}

}
