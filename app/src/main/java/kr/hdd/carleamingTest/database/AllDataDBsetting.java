package kr.hdd.carleamingTest.database;

import android.content.Context;
import android.database.Cursor;

import kr.hdd.carleamingTest.util.CarLLog;

public class AllDataDBsetting {
    private static final String TAG = AllDataDBsetting.class.getSimpleName();
    private Context mContext = null;

    String userId = null, car_Name = null, car_vehicle_number = null, car_number = null, oil_type = null, start_date = null, day = null;
    int car_model = 0, auto_time = 0;
    double mileage = 0, oil = 0, auto_coolant = 0, fuel_consumption = 0, fuel_efficienct = 0;
    int drive_time = 0, drive_down_speed = 0, drive_up_speed = 0, idle = 0, drive = 0;
    double engine_oil = 0, tire = 0, brake_lining = 0, aircon_filter = 0, wiper = 0, equip_coolant = 0, transmission_oil = 0, battery = 0, save_mileage = 0;
    String enging_oil_day = null, tire_day = null, brake_lining_day = null, aircon_filter_day = null, wiper_day = null, equip_coolant_day = null, transmission_oil_day = null, battery_day = null;


    public AllDataDBsetting(Context context, String userId, String car_Name, String car_vehicle_number, String car_number, int car_model, String oil_type,
                            double mileage, int auto_time, double oil, double auto_coolant, double fuel_consumption,
                            String start_date, double fuel_efficienct, int drive_time, int drive_down_speed, int drive_up_speed, int idle, int drive,
                            double engine_oil, double tire, double brake_lining, double aircon_filter, double wiper, double equip_coolant, double transmission_oil, double battery, String day,
                            String enging_oil_day, String tire_day, String brake_lining_day, String aircon_filter_day, String wiper_day, String equip_coolant_day, String transmission_oil_day, String battery_day, double save_mileage) {
        // TODO Auto-generated constructor stub
        mContext = context;
        onInit(context, userId, car_Name, car_vehicle_number, car_number, car_model, oil_type,
                mileage, auto_time, oil, auto_coolant, fuel_consumption,
                start_date, fuel_efficienct, drive_time, drive_down_speed, drive_up_speed, idle, drive,
                engine_oil, tire, brake_lining, aircon_filter, wiper, equip_coolant, transmission_oil, battery, day,
                enging_oil_day, tire_day, brake_lining_day, aircon_filter_day, wiper_day, equip_coolant_day, transmission_oil_day, battery_day, save_mileage);
    }

    public void onInit(Context context, String userId, String car_Name, String car_vehicle_number, String car_number, int car_model, String oil_type,
                       double mileage, int auto_time, double oil, double auto_coolant, double fuel_consumption,
                       String start_date, double fuel_efficienct, int drive_time, int drive_down_speed, int drive_up_speed, int idle, int drive,
                       double engine_oil, double tire, double brake_lining, double aircon_filter, double wiper, double equip_coolant, double transmission_oil, double battery, String day,
                       String enging_oil_day, String tire_day, String brake_lining_day, String aircon_filter_day, String wiper_day, String equip_coolant_day, String transmission_oil_day, String battery_day, double save_mileage) {

        DBAdapter db = new DBAdapter(mContext);
        db.open();

        //user_info 테이블 초기화
        final Cursor all_dataCursor = db.getAll_DataManager().getAll_All_Data();

        //int _id = cursor.getColumnIndex(Quiz_Info.KEY_ROWID);
        if (all_dataCursor.getCount() > 0) {
            //CarLLog.e(TAG, "userInfoCursor.getCount() : " + userInfoCursor.getCount());
        }
        if (all_dataCursor != null) {

//			db.getAll_DataManager().deleteAll_All_Data();

//			String rowId = null;
//			String userId = null, car_Name = null, car_vehicle_number  = null, car_number = null, car_model = null, oil_type = null;
//			String mileage = null, auto_time = null, oil = null, auto_coolant = null, fuel_consumption = null;
//			String start_date = null, fuel_efficienct = null, drive_time = null, drive_down_speed = null, drive_up_speed = null, idle = null, drive = null;
//			String engine_oil = null, tire = null, brake_lining = null, aircon_filter = null, wiper = null, equip_coolant = null, transmission_oil = null, battery = null, day = null;
//			String enging_oil_day = null, tire_day = null, brake_lining_day = null,  aircon_filter_day = null,  wiper_day = null, equip_coolant_day = null,  transmission_oil_day = null,  battery_day = null;
//			
            this.userId = userId;
            CarLLog.v(TAG, "user_id : " +  userId);
            this.car_Name = car_Name;
            CarLLog.v(TAG, "car_name : " + car_Name);
            this.car_vehicle_number = car_vehicle_number;
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
            this.drive_time = drive_time;
            this.drive_down_speed = drive_down_speed;
            this.drive_up_speed = drive_up_speed;
            this.idle = idle;
            this.drive = drive;
            this.engine_oil = engine_oil;
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
            this.save_mileage = save_mileage;


            if (all_dataCursor.getCount() > 0) {
                if (userId == null || userId == "") {
                    userId = "0";
                }


            } else {

                if (userId == null || userId == "") {
                    userId = "0";
                }

                if (car_Name == null || car_Name == "") {
                    car_Name = "";
                }

                if (car_vehicle_number == null || car_vehicle_number == "") {
                    car_vehicle_number = "";
                }

                if (car_number == null || car_number == "") {
                    car_number = "";
                }

                if (car_model < 0) {
                    car_model = -1;
                }

                if (oil_type == null || oil_type == "") {
                    oil_type = "0";
                }

                if (mileage < 0) {
                    mileage = 0;
                }

                if (auto_time < 0) {
                    auto_time = 0;
                }

                if (oil < 0) {
                    oil = 0;
                }

                if (auto_coolant < 0) {
                    auto_coolant = 0;
                }

                if (fuel_consumption < 0) {
                    fuel_consumption = 0;
                }

                if (start_date == null || start_date == "") {
                    start_date = "0";
                }

                if (fuel_efficienct < 0) {
                    fuel_efficienct = 0;
                }

                if (drive_time < 0) {
                    drive_time = 0;
                }

                if (drive_down_speed < 0) {
                    drive_down_speed = 0;
                }

                if (drive_up_speed < 0) {
                    drive_up_speed = 0;
                }

                if (idle < 0) {
                    idle = 0;
                }

                if (drive < 0) {
                    drive = 0;
                }

                if (engine_oil < 0) {
                    engine_oil = 0;
                }

                if (tire < 0) {
                    tire = 0;
                }

                if (brake_lining < 0) {
                    brake_lining = 0;
                }

                if (aircon_filter < 0) {
                    aircon_filter = 0;
                }


                if (wiper < 0) {
                    wiper = 0;
                }

                if (equip_coolant < 0) {
                    equip_coolant = 0;
                }

                if (transmission_oil < 0) {
                    transmission_oil = 0;
                }


                if (battery < 0) {
                    battery = 0;
                }

                if (enging_oil_day == null || enging_oil_day == "") {
                    enging_oil_day = "0";
                }

                if (tire_day == null || tire_day == "") {
                    tire_day = "0";
                }

                if (brake_lining_day == null || brake_lining_day == "") {
                    brake_lining_day = "0";
                }

                if (aircon_filter_day == null || aircon_filter_day == "") {
                    aircon_filter_day = "0";
                }

                if (wiper_day == null || wiper_day == "") {
                    wiper_day = "0";
                }

                if (equip_coolant_day == null || equip_coolant_day == "") {
                    equip_coolant_day = "0";
                }

                if (transmission_oil_day == null || transmission_oil_day == "") {
                    transmission_oil_day = "0";
                }

                if (battery_day == null || battery_day == "") {
                    battery_day = "0";
                }

                if (day == null || day == "") {
                    day = "0";
                }

                if (save_mileage < 0) {
                    save_mileage = 0;
                }

            }


            long id = 0l;
            if (fuel_consumption <= 0) {
                all_dataCursor.close();
                db.close();
                return;
            }

            id = db.getAll_DataManager().insertAll_Data(userId, car_Name, engine_oil, tire, car_vehicle_number,
                    car_number, car_model, oil_type, mileage, auto_time, oil,
                    auto_coolant, fuel_consumption, start_date, fuel_efficienct, drive_time,
                    drive_down_speed, drive_up_speed, idle, drive, brake_lining,
                    aircon_filter, wiper, equip_coolant, transmission_oil, battery,
                    enging_oil_day, tire_day, brake_lining_day, aircon_filter_day, wiper_day,
                    equip_coolant_day, transmission_oil_day, battery_day, day, save_mileage);
        }

        all_dataCursor.close();
        db.close();

    }

}
