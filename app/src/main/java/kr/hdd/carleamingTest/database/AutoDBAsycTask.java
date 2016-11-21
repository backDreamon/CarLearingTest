package kr.hdd.carleamingTest.database;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.activity.data.AllDataContentData;
import kr.hdd.carleamingTest.database.DBAdapter.All_Date_content;
import kr.hdd.carleamingTest.util.CarLLog;

public class AutoDBAsycTask extends AsyncTask<Void, Void, AllDataContentData>{
	private String TAG= "AutoDBAsycTask";

	private Handler mStopHandler = null;
	private Context mContext = null;

	/**
	 * @param context	 
	 * @param stopHandler
	 */
	public AutoDBAsycTask(Context context, Handler stopHandler){
		mStopHandler = stopHandler;
		mContext = context;
	}

	@Override
	protected AllDataContentData doInBackground(Void... params) {
		AllDataContentData alldatainfo = null;
		try {
			// DB 받아오는 용도			
			alldatainfo = AllDataDB();
			CarLLog.i("kch", "Mileage mLastSaveKmCount alldatainfo:"+alldatainfo);

			if(!isCancelled())
			{

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return alldatainfo; 
	}	


	@Override
	protected void onPostExecute(AllDataContentData result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		CarLLog.i(TAG, "onPostExecute()");

		Message msg = mStopHandler.obtainMessage();
		msg.obj = result;
		msg.arg1 = MainApplication.handlerAuto;	
		mStopHandler.sendMessage(msg);				
		onCancelled();

	}

	@Override
	protected void onPreExecute() {
		// Things to be done before execution of long running operation. For
		// example showing ProgessDialog
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// Things to be done while execution of long running operation is in
		// progress. For example updating ProgessDialog
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();

		if(mStopHandler != null){
			mStopHandler = null;
		}			

	}
	
	private AllDataContentData AllDataDB(){

		DBAdapter db = new DBAdapter(mContext); 
		db.open();
		AllDataContentData auto = null;

		Cursor cursor = db.getAll_DataManager().getAll_All_Data();

		if (cursor.moveToFirst())
		{
			do {          
				auto = new AllDataContentData();

				int idIndex = cursor.getColumnIndex(All_Date_content.KEY_ROWID);
				auto.setdbId(cursor.getLong(idIndex));
				
				int userIdIndex = cursor.getColumnIndex(All_Date_content.KEY_All_USERID);
				auto.setUserId(cursor.getString(userIdIndex));
				
				int car_NameIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_NAME);
				auto.setCar_Name(cursor.getString(car_NameIndex));
				
				int car_vehicle_numberIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_VEHICLE_NUMBER);
				auto.setCar_vehicle_number(cursor.getString(car_vehicle_numberIndex));
				
				int car_numberIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_NUMBER);
				auto.setCar_number(cursor.getString(car_numberIndex));
				
				int car_modelIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_MODEL);
				auto.setCar_model(cursor.getInt(car_modelIndex));
				
				int oil_typeIndex = cursor.getColumnIndex(All_Date_content.KEY_All_OIL_TYPE);
				auto.setOil_type(cursor.getString(oil_typeIndex));
				
				int mileageIndex = cursor.getColumnIndex(All_Date_content.KEY_All_MILEAGE);
				auto.setMileage(cursor.getDouble(mileageIndex));
//				CarLLog.i(TAG, "mileageIndex: "+ cursor.getDouble(mileageIndex));
				
				int auto_timeIndex =cursor.getColumnIndex(All_Date_content.KEY_All_TMIE);
				auto.setAuto_time(cursor.getInt(auto_timeIndex));
//				CarLLog.i(TAG, "auto_timeIndex: "+ cursor.getInt(auto_timeIndex));
				
				int oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_OIL);
				auto.setOil(cursor.getDouble(oilIndex));
//				CarLLog.i(TAG, "oilIndex: "+ cursor.getDouble(oilIndex));
				
				int auto_coolantIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT);
				auto.setAuto_coolant(cursor.getDouble(auto_coolantIndex));
				
				int fuel_consumptionIndex = cursor.getColumnIndex(All_Date_content.KEY_All_FUEL_CONSUMPTION);//총소모량
				auto.setFuel_consumption(cursor.getDouble(fuel_consumptionIndex));
//				CarLLog.i(TAG, "fuel_consumptionIndex: "+ cursor.getDouble(fuel_consumptionIndex));
				
				int start_dateIndex = cursor.getColumnIndex(All_Date_content.KEY_All_START_DATE);
				auto.setStart_date(cursor.getString(start_dateIndex));
				
				int fuel_efficienctIndex = cursor.getColumnIndex(All_Date_content.KEY_All_FUEL_EFFICIENCT);
				auto.setFuel_efficienct(cursor.getDouble(fuel_efficienctIndex));
				
				int drive_timeIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_TIME);
				auto.setDrive_time(cursor.getInt(drive_timeIndex));
				
				int drive_down_speedIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_DOWN_SPEEd);
				auto.setDrive_down_speed(cursor.getInt(drive_down_speedIndex));
//				CarLLog.w(TAG, "2222 drive_down_speedIndex: "+ cursor.getInt(drive_down_speedIndex));
				
				int drive_up_speedIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_UP_SPEEd);
				auto.setDrive_up_speed(cursor.getInt(drive_up_speedIndex));
				
				int idleIndex = cursor.getColumnIndex(All_Date_content.KEY_All_IDLE);
				auto.setIdle(cursor.getInt(idleIndex));
				
				int driveIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE);
				auto.setDrive(cursor.getInt(driveIndex));
				
				int engine_oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_ENGINE_OIL);
				auto.setEngine_oil(cursor.getDouble(engine_oilIndex));
				
				int tireIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TIRE);
				auto.setTire(cursor.getDouble(tireIndex));
				
				int brake_liningIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BRAKE_LINING);
				auto.setBrake_lining(cursor.getDouble(brake_liningIndex));
				
				int aircon_filterIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AIRCON_FILTER);
				auto.setAircon_filter(cursor.getDouble(aircon_filterIndex));
				
				int wiperIndex = cursor.getColumnIndex(All_Date_content.KEY_All_WIPER);
				auto.setWiper(cursor.getDouble(wiperIndex));
				
				int equip_coolantIndex = cursor.getColumnIndex(All_Date_content.KEY_All_EQUIP_COOLANT);
				auto.setEquip_coolant(cursor.getDouble(equip_coolantIndex));
				
				int transmission_oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TRANSMISSION_OIL);
				auto.setTransmission_oil(cursor.getDouble(transmission_oilIndex));
				
				int batteryIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BATTERY);
				auto.setBattery(cursor.getDouble(batteryIndex));
				
				int engine_oil_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_ENGINE_OIL_DAY);
				auto.setEngine_oil_day(cursor.getString(engine_oil_dayIndex));
				
				int tire_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TIRE_DAY);
				auto.setTire_day(cursor.getString(tire_dayIndex));
				
				int brake_lining_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BRAKE_LINING_DAY);
				auto.setBrake_lining_day(cursor.getString(brake_lining_dayIndex));
				
				int aircon_filter_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AIRCON_FILTER_DAY);
				auto.setAircon_filter_day(cursor.getString(aircon_filter_dayIndex));
				
				int wiper_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_WIPER_DAY);
				auto.setWiper_day(cursor.getString(wiper_dayIndex));
				
				int equip_coolant_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_EQUIP_COOLANT_DAY);
				auto.setEquip_coolant_day(cursor.getString(equip_coolant_dayIndex));
				
				int transmission_oil_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TRANSMISSION_OIL_DAY);
				auto.setTransmission_oil_day(cursor.getString(transmission_oil_dayIndex));
				
				int battery_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BATTERY_DAY);
				auto.setBattery_day(cursor.getString(battery_dayIndex));
				
				int dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DAY);
				auto.setDay(cursor.getString(dayIndex));
				
				int save_mileageIndex = cursor.getColumnIndex(All_Date_content.KEY_All_SAVE_MILEAGE);
				auto.setSave_mileage(cursor.getDouble(save_mileageIndex));
//				CarLLog.i(TAG, "88888 save_mileageIndex: "+ cursor.getDouble(save_mileageIndex));

			} while (cursor.moveToNext());
		} else {
//			saveDBsetting();
		}

		cursor.close();

		db.close();

		return auto;
	}
}
