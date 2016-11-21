package kr.hdd.carleamingTest.database;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.activity.data.AllDataContentData;

import android.content.Context;
import android.os.AsyncTask;

public class CommonUpdateAsycTask extends AsyncTask<Void, Void, AllDataContentData> {
	private String TAG= "CommonUpdateAsycTask";

	private Context mContext = null;
	private String userId = null;
	private String car_Name = null;
	private String car_vehicle_number  = null;
	private String car_number = null; 
	private int car_model = 0;
	private String oil_type = null;
	private double mileage = 0; 
	private int auto_time = 0; 
	private double oil = 0; 
	private double auto_coolant = 0; 
	private double fuel_consumption = 0;
	private String start_date = null; 
	private double fuel_efficienct = 0; 
	private int drive_time = 0; 
	private int drive_down_speed = 0; 
	private int drive_up_speed = 0; 
	private int idle = 0; 
	private int drive = 0;
	private double engine_oil = 0; 
	private double tire = 0; 
	private double brake_lining = 0; 
	private double aircon_filter = 0; 
	private double wiper = 0; 
	private double equip_coolant = 0; 
	private double transmission_oil = 0; 
	private double battery = 0;
	private String enging_oil_day = null;
	private String tire_day = null; 
	private String brake_lining_day = null;
	private String aircon_filter_day = null;
	private String wiper_day = null;
	private String equip_coolant_day = null;
	private String transmission_oil_day = null;
	private String battery_day = null;
	private String day = null;
	private double save_mileage = 0;

	/**
	 * @param context
	 * @param stopHandler
	 */
	public CommonUpdateAsycTask(Context context, String userid, String carName, double enging_oil, double tire,
			String car_vehicle_number, String car_number, int car_model, String oil_type,
			double mileage, int auto_time, double oil, double auto_coolant, double fuel_consumption,
			String start_date, double fuel_efficienct, int drive_style_time, int down_speed,
			int up_speed, int idle, int drive_style_drive,
			double brake_lining, double aircon_filter, double wiper, 
			double equip_coolant, double transmission_oil, double battery, String day,
			String enging_oil_day, String tire_day,String brake_lining_day, String aircon_filter_day, String wiper_day, 
			String equip_coolant_day, String transmission_oil_day, String battery_day, double save_milege) {

		this.mContext = context;
		this.userId = userid;
		this.car_Name = carName;
		this.car_vehicle_number  = car_vehicle_number;
		this.car_number = car_number; 
		this.car_model = car_model;
		this.oil_type = oil_type;
		this.mileage = mileage; 
		this.auto_time = auto_time; 
		this.oil = oil; 
		this.auto_coolant = auto_coolant; 
		this.fuel_consumption = fuel_consumption;
		this.start_date = start_date; 
		this.fuel_efficienct = fuel_efficienct; 
		this.drive_time = drive_style_time; 
		this.drive_down_speed = down_speed; 
		this.drive_up_speed = up_speed; 
		this.idle = idle; 
		this.drive = drive_style_drive;
		this.engine_oil = enging_oil; 
		this.tire = tire; 
		this.brake_lining = brake_lining; 
		this.aircon_filter = aircon_filter; 
		this.wiper = wiper; 
		this.equip_coolant = equip_coolant; 
		this.transmission_oil = transmission_oil; 
		this.battery = battery; 
		this.day = day;
		this.enging_oil_day = enging_oil_day;
		this.tire_day = tire_day; 
		this.brake_lining_day = brake_lining_day;
		this.aircon_filter_day = aircon_filter_day;
		this.wiper_day = wiper_day;
		this.equip_coolant_day = equip_coolant_day;
		this.transmission_oil_day = transmission_oil_day;
		this.battery_day = battery_day;
		this.save_mileage = save_milege;
	}

	@Override
	protected AllDataContentData doInBackground(Void... params) {
		AllDataContentData auto = null;
		try {
//			CarLLog.w("", "save_mileage: "+ save_mileage);

			DBAdapter db = new DBAdapter(mContext);
			db.open();

			db.getAll_DataManager().updateAll_Data(MainApplication.getdbId(), userId, car_Name, engine_oil, tire, car_vehicle_number, 
					car_number, car_model, oil_type, mileage, auto_time, oil, 
					auto_coolant, fuel_consumption, start_date, fuel_efficienct, 
					drive_time, drive_down_speed, drive_up_speed, idle, drive, 
					brake_lining, aircon_filter, wiper, equip_coolant, transmission_oil, 
					battery, enging_oil_day, tire_day, brake_lining_day, aircon_filter_day,
					wiper_day, equip_coolant_day, transmission_oil_day, battery_day, day, save_mileage );

			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return auto;
	}

	@Override
	protected void onPostExecute(AllDataContentData result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//	loadingstart.onDismiss();
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
	}
}
