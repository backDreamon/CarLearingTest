package kr.hdd.carleamingTest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kr.hdd.carleamingTest.util.CarLLog;

public class DBAdapter {

    private static final String TAG = DBAdapter.class.getSimpleName();

    public static final String DATABASE_NAME = "CarLeaming.db";
    public static final String DATABASE_ALL_DATA_TABLE = "all_data";  //전체 데이터

    private static final int DATABASE_VERSION = 9;

    //전체 데이터
    public static final class All_Date_content {
        public static final String KEY_ROWID = "_id";
        public static final String KEY_All_USERID = "userid";
        public static final String KEY_All_CAR_NAME = "car_name";
        public static final String KEY_All_CAR_VEHICLE_NUMBER = "car_vehicle_number";
        public static final String KEY_All_CAR_NUMBER = "car_number";
        public static final String KEY_All_CAR_MODEL = "car_model";
        public static final String KEY_All_OIL_TYPE = "oil_type";
        public static final String KEY_All_MILEAGE = "auto_diagnosis_mileage";  //주행거리
        public static final String KEY_All_TMIE = "auto_diagnosis_time";  //주행시간
        public static final String KEY_All_OIL = "auto_diagnosis_oil";  //유류비
        public static final String KEY_All_AUTO_DIAGNOSIS_COOLANT = "auto_diagnosis_coolant";  //자동진단-냉각수
        public static final String KEY_All_FUEL_CONSUMPTION = "auto_diagnosis_fuel_consumption";  //총소모량
        public static final String KEY_All_START_DATE = "drive_style_start_date";  // 저장 시작 날짜
        public static final String KEY_All_FUEL_EFFICIENCT = "drive_style_fuel_efficienct"; // 연비
        public static final String KEY_All_DRIVE_TIME = "drive_style_drive_time"; //주행시간
        public static final String KEY_All_DRIVE_DOWN_SPEEd = "drive_style_drive_down_speed"; //감속
        public static final String KEY_All_DRIVE_UP_SPEEd = "drive_style_drive_up_speed"; //가속
        public static final String KEY_All_IDLE = "drive_style_idle"; //공회전
        public static final String KEY_All_DRIVE = "drive_style_drive"; //일반주행
        public static final String KEY_All_ENGINE_OIL = "equip_engine_oil";  //엔진오일
        public static final String KEY_All_TIRE = "equip_tire";  //타이어
        public static final String KEY_All_BRAKE_LINING = "equip_brake_lining"; //브레이크 라이닝
        public static final String KEY_All_AIRCON_FILTER = "equip_aircon_filter";  //에어컨 필터
        public static final String KEY_All_WIPER = "equip_wiper";  //와이퍼
        public static final String KEY_All_EQUIP_COOLANT = "equip_coolant";  //소모품 관리-냉각수
        public static final String KEY_All_TRANSMISSION_OIL = "equip_transmission_oil"; //오토미션 오일
        public static final String KEY_All_BATTERY = "equip_battery"; // 베터리

        public static final String KEY_All_ENGINE_OIL_DAY = "equip_engine_oil_day";  //엔진오일
        public static final String KEY_All_TIRE_DAY = "equip_tire_day";  //타이어
        public static final String KEY_All_BRAKE_LINING_DAY = "equip_brake_lining_day"; //브레이크 라이닝
        public static final String KEY_All_AIRCON_FILTER_DAY = "equip_aircon_filter_day";  //에어컨 필터
        public static final String KEY_All_WIPER_DAY = "equip_wiper_day";  //와이퍼
        public static final String KEY_All_EQUIP_COOLANT_DAY = "equip_coolant_day";  //소모품 관리-냉각수
        public static final String KEY_All_TRANSMISSION_OIL_DAY = "equip_transmission_oil_day"; //오토미션 오일
        public static final String KEY_All_BATTERY_DAY = "equip_battery_day"; // 베터리

        public static final String KEY_All_SAVE_MILEAGE = "save_mileage";  //누적 주행거리

        public static final String KEY_All_DAY = "day"; //저장된 날짜
    }


    private static final String DATABASE_ALL_DATA_TABLE_CREATE =
            "CREATE TABLE " + DATABASE_ALL_DATA_TABLE +
                    "(_id integer primary key autoincrement, " +
                    "userid TEXT NOT NULL, " +
                    "car_name TEXT NOT NULL, " +
                    "car_vehicle_number TEXT NOT NULL, " +
                    "car_number TEXT NOT NULL, " +
                    "car_model integer NOT NULL, " +
                    "oil_type TEXT NOT NULL, " +
                    "auto_diagnosis_mileage REAL NOT NULL, " +
                    "auto_diagnosis_time integer NOT NULL, " +
                    "auto_diagnosis_oil REAL NOT NULL, " +
                    "auto_diagnosis_coolant REAL NOT NULL, " +
                    "auto_diagnosis_fuel_consumption REAL NOT NULL, " +
                    "drive_style_start_date TEXT NOT NULL, " +
                    "drive_style_fuel_efficienct REAL NOT NULL, " +
                    "drive_style_drive_time integer NOT NULL, " +
                    "drive_style_drive_down_speed integer NOT NULL, " +
                    "drive_style_drive_up_speed integer NOT NULL, " +
                    "drive_style_idle integer NOT NULL, " +
                    "drive_style_drive integer NOT NULL, " +
                    "equip_engine_oil REAL NOT NULL, " +
                    "equip_tire REAL NOT NULL, " +
                    "equip_brake_lining REAL NOT NULL, " +
                    "equip_aircon_filter REAL NOT NULL, " +
                    "equip_wiper REAL NOT NULL, " +
                    "equip_coolant REAL NOT NULL, " +
                    "equip_transmission_oil REAL NOT NULL, " +
                    "equip_battery REAL NOT NULL, " +
                    "day TEXT NOT NULL, "
                    + All_Date_content.KEY_All_ENGINE_OIL_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_TIRE_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_BRAKE_LINING_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_AIRCON_FILTER_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_WIPER_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_EQUIP_COOLANT_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_TRANSMISSION_OIL_DAY + " TEXT NOT NULL, "
                    + All_Date_content.KEY_All_BATTERY_DAY + " TEXT NOT NULL,"
                    + All_Date_content.KEY_All_SAVE_MILEAGE + " REAL NOT NULL);";


    public All_DataManager mAll_DataManager = null;

    private Context mContext = null;

    private DatabaseHelper mDBHelper = null;
    private static SQLiteDatabase mDB = null;

    private static DBAdapter mInstance = null;

    public static DBAdapter getInstance(Context context) {
        if (mInstance == null) mInstance = new DBAdapter(context);
        return mInstance;
    }

    public DBAdapter(Context context) {
        this.mContext = context;
        mAll_DataManager = new All_DataManager();

        mDBHelper = new DatabaseHelper(context);
    }


    public All_DataManager getAll_DataManager() {
        return mAll_DataManager;
    }


    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(DATABASE_ALL_DATA_TABLE_CREATE);
            db.execSQL("PRAGMA foreign_keys = ON;");
            CarLLog.d(TAG, "DB onCreate");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
//			CarLLog.w(TAG, "Upgrading database from version " + oldVersion 
//					+ " to "
//					+ newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ALL_DATA_TABLE);
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException {

        mDB = mDBHelper.getWritableDatabase();
//		CarLLog.v("", "mDBHelper : "+mDB.isOpen());  
//		CarLLog.v("", "mDBHelper : "+mDB.isDbLockedByCurrentThread());  
//		CarLLog.v("", "mDBHelper : "+mDB.isDbLockedByOtherThreads());  

        if (mDB != null && !mDB.isOpen() || !mDB.isDbLockedByCurrentThread() || !mDB.isDbLockedByOtherThreads()) { //db is locked
            //mDB = mDBHelper.getWritableDatabase();
            return this;
        }
        return null;
    }

    /**
     * 반드시 open() 후에 사용
     *
     * @return
     */
    public SQLiteDatabase getDB() {
        return mDB;
    }

    //---closes the database---
    public void close() {
//		CarLLog.v("", "mDBHelper close:" );  
        mDBHelper.close();
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        Cursor cursor = mDB.rawQuery(sql, selectionArgs);
        return cursor;
    }


    /**
     * All Data 테이블 관리
     */
    public class All_DataManager {

        public long insertAll_Data(String userid, String carName, double enging_oil, double tire,
                                   String car_vehicle_number, String car_number, int car_model, String oil_type,
                                   double mileage, int auto_time, double oil, double auto_coolant, double fuel_consumption,
                                   String start_date, double fuel_efficienct, int drive_style_time, int down_speed,
                                   int up_speed, int idle, int drive_style_drive,
                                   double brake_lining, double aircon_filter, double wiper,
                                   double equip_coolant, double transmission_oil, double battery,
                                   String enging_oil_day, String tire_day, String brake_lining_day, String aircon_filter_day, String wiper_day,
                                   String equip_coolant_day, String transmission_oil_day, String battery_day, String day, double save_mileage) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(All_Date_content.KEY_All_USERID, userid);
            initialValues.put(All_Date_content.KEY_All_CAR_NAME, carName);
            initialValues.put(All_Date_content.KEY_All_CAR_VEHICLE_NUMBER, car_vehicle_number);
            initialValues.put(All_Date_content.KEY_All_CAR_NUMBER, car_number);
            initialValues.put(All_Date_content.KEY_All_CAR_MODEL, car_model);
            initialValues.put(All_Date_content.KEY_All_OIL_TYPE, oil_type);
            initialValues.put(All_Date_content.KEY_All_MILEAGE, mileage);
            initialValues.put(All_Date_content.KEY_All_TMIE, auto_time);
            initialValues.put(All_Date_content.KEY_All_OIL, oil);
            initialValues.put(All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT, auto_coolant);
            initialValues.put(All_Date_content.KEY_All_FUEL_CONSUMPTION, fuel_consumption);
            initialValues.put(All_Date_content.KEY_All_START_DATE, start_date);
            initialValues.put(All_Date_content.KEY_All_FUEL_EFFICIENCT, fuel_efficienct);
            initialValues.put(All_Date_content.KEY_All_DRIVE_TIME, drive_style_time);
            initialValues.put(All_Date_content.KEY_All_DRIVE_DOWN_SPEEd, down_speed);
            initialValues.put(All_Date_content.KEY_All_DRIVE_UP_SPEEd, up_speed);
            initialValues.put(All_Date_content.KEY_All_IDLE, idle);
            initialValues.put(All_Date_content.KEY_All_DRIVE, drive_style_drive);
            initialValues.put(All_Date_content.KEY_All_ENGINE_OIL, enging_oil);
            initialValues.put(All_Date_content.KEY_All_TIRE, tire);
            initialValues.put(All_Date_content.KEY_All_BRAKE_LINING, brake_lining);
            initialValues.put(All_Date_content.KEY_All_AIRCON_FILTER, aircon_filter);
            initialValues.put(All_Date_content.KEY_All_WIPER, wiper);
            initialValues.put(All_Date_content.KEY_All_EQUIP_COOLANT, equip_coolant);
            initialValues.put(All_Date_content.KEY_All_TRANSMISSION_OIL, transmission_oil);
            initialValues.put(All_Date_content.KEY_All_BATTERY, battery);
            initialValues.put(All_Date_content.KEY_All_ENGINE_OIL_DAY, enging_oil_day);
            initialValues.put(All_Date_content.KEY_All_TIRE_DAY, tire_day);
            initialValues.put(All_Date_content.KEY_All_BRAKE_LINING_DAY, brake_lining_day);
            initialValues.put(All_Date_content.KEY_All_AIRCON_FILTER_DAY, aircon_filter_day);
            initialValues.put(All_Date_content.KEY_All_WIPER_DAY, wiper_day);
            initialValues.put(All_Date_content.KEY_All_EQUIP_COOLANT_DAY, equip_coolant_day);
            initialValues.put(All_Date_content.KEY_All_TRANSMISSION_OIL_DAY, transmission_oil_day);
            initialValues.put(All_Date_content.KEY_All_BATTERY_DAY, battery_day);
            initialValues.put(All_Date_content.KEY_All_DAY, day);
            initialValues.put(All_Date_content.KEY_All_SAVE_MILEAGE, save_mileage);
            return mDB.insert(DATABASE_ALL_DATA_TABLE, null, initialValues);
        }

        public boolean delete_All_Data(long rowId) {
            return mDB.delete(DATABASE_ALL_DATA_TABLE, All_Date_content.KEY_ROWID + "=?" + rowId, new String[]{String.valueOf(rowId)}) > 0;
        }

        public boolean deleteAll_All_Data() {
            return mDB.delete(DATABASE_ALL_DATA_TABLE, null, null) > 0;
        }

        public Cursor getAll_Data(long rowId) {
            Cursor cursor = mDB.query(DATABASE_ALL_DATA_TABLE, new String[]{
                    All_Date_content.KEY_ROWID,
                    All_Date_content.KEY_All_USERID,
                    All_Date_content.KEY_All_CAR_NAME,
                    All_Date_content.KEY_All_CAR_VEHICLE_NUMBER,
                    All_Date_content.KEY_All_CAR_NUMBER,
                    All_Date_content.KEY_All_CAR_MODEL,
                    All_Date_content.KEY_All_OIL_TYPE,
                    All_Date_content.KEY_All_MILEAGE,
                    All_Date_content.KEY_All_TMIE,
                    All_Date_content.KEY_All_OIL,
                    All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT,
                    All_Date_content.KEY_All_FUEL_CONSUMPTION,
                    All_Date_content.KEY_All_START_DATE,
                    All_Date_content.KEY_All_FUEL_EFFICIENCT,
                    All_Date_content.KEY_All_DRIVE_TIME,
                    All_Date_content.KEY_All_DRIVE_DOWN_SPEEd,
                    All_Date_content.KEY_All_DRIVE_UP_SPEEd,
                    All_Date_content.KEY_All_IDLE,
                    All_Date_content.KEY_All_DRIVE,
                    All_Date_content.KEY_All_ENGINE_OIL,
                    All_Date_content.KEY_All_TIRE,
                    All_Date_content.KEY_All_BRAKE_LINING,
                    All_Date_content.KEY_All_AIRCON_FILTER,
                    All_Date_content.KEY_All_WIPER,
                    All_Date_content.KEY_All_EQUIP_COOLANT,
                    All_Date_content.KEY_All_TRANSMISSION_OIL,
                    All_Date_content.KEY_All_BATTERY,
                    All_Date_content.KEY_All_ENGINE_OIL_DAY,
                    All_Date_content.KEY_All_TIRE_DAY,
                    All_Date_content.KEY_All_BRAKE_LINING_DAY,
                    All_Date_content.KEY_All_AIRCON_FILTER_DAY,
                    All_Date_content.KEY_All_WIPER_DAY,
                    All_Date_content.KEY_All_EQUIP_COOLANT_DAY,
                    All_Date_content.KEY_All_TRANSMISSION_OIL_DAY,
                    All_Date_content.KEY_All_BATTERY_DAY,
                    All_Date_content.KEY_All_DAY,
                    All_Date_content.KEY_All_SAVE_MILEAGE
            }, All_Date_content.KEY_ROWID + "=" + rowId, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }

            return cursor;
        }

        public Cursor getAll_All_Data() {
            Cursor cursor = mDB.query(DATABASE_ALL_DATA_TABLE, new String[]{
                    All_Date_content.KEY_ROWID,
                    All_Date_content.KEY_All_USERID,
                    All_Date_content.KEY_All_CAR_NAME,
                    All_Date_content.KEY_All_CAR_VEHICLE_NUMBER,
                    All_Date_content.KEY_All_CAR_NUMBER,
                    All_Date_content.KEY_All_CAR_MODEL,
                    All_Date_content.KEY_All_OIL_TYPE,
                    All_Date_content.KEY_All_MILEAGE,
                    All_Date_content.KEY_All_TMIE,
                    All_Date_content.KEY_All_OIL,
                    All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT,
                    All_Date_content.KEY_All_FUEL_CONSUMPTION,
                    All_Date_content.KEY_All_START_DATE,
                    All_Date_content.KEY_All_FUEL_EFFICIENCT,
                    All_Date_content.KEY_All_DRIVE_TIME,
                    All_Date_content.KEY_All_DRIVE_DOWN_SPEEd,
                    All_Date_content.KEY_All_DRIVE_UP_SPEEd,
                    All_Date_content.KEY_All_IDLE,
                    All_Date_content.KEY_All_DRIVE,
                    All_Date_content.KEY_All_ENGINE_OIL,
                    All_Date_content.KEY_All_TIRE,
                    All_Date_content.KEY_All_BRAKE_LINING,
                    All_Date_content.KEY_All_AIRCON_FILTER,
                    All_Date_content.KEY_All_WIPER,
                    All_Date_content.KEY_All_EQUIP_COOLANT,
                    All_Date_content.KEY_All_TRANSMISSION_OIL,
                    All_Date_content.KEY_All_BATTERY,
                    All_Date_content.KEY_All_ENGINE_OIL_DAY,
                    All_Date_content.KEY_All_TIRE_DAY,
                    All_Date_content.KEY_All_BRAKE_LINING_DAY,
                    All_Date_content.KEY_All_AIRCON_FILTER_DAY,
                    All_Date_content.KEY_All_WIPER_DAY,
                    All_Date_content.KEY_All_EQUIP_COOLANT_DAY,
                    All_Date_content.KEY_All_TRANSMISSION_OIL_DAY,
                    All_Date_content.KEY_All_BATTERY_DAY,
                    All_Date_content.KEY_All_DAY,
                    All_Date_content.KEY_All_SAVE_MILEAGE
            }, null, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }

            return cursor;
        }

        public boolean updateAll_Data(long rowId, String userid, String carName, double enging_oil, double tire,
                                      String car_vehicle_number, String car_number, int car_model, String oil_type,
                                      double mileage, int auto_time, double oil, double auto_coolant, double fuel_consumption,
                                      String start_date, double fuel_efficienct, int drive_style_time, int down_speed,
                                      int up_speed, int idle, int drive_style_drive,
                                      double brake_lining, double aircon_filter, double wiper,
                                      double equip_coolant, double transmission_oil, double battery,
                                      String enging_oil_day, String tire_day, String brake_lining_day, String aircon_filter_day,
                                      String wiper_day, String equip_coolant_day, String transmission_oil_day, String battery_day, String day, double save_mileage) {

            ContentValues initialValues = new ContentValues();

            if (rowId > 0) {
                initialValues.put(All_Date_content.KEY_ROWID, rowId);
            }

            if (userid != null) {
                initialValues.put(All_Date_content.KEY_All_USERID, userid);
            }

            if (carName != null) {
                initialValues.put(All_Date_content.KEY_All_CAR_NAME, carName);
            }

            if (car_vehicle_number != null) {
                initialValues.put(All_Date_content.KEY_All_CAR_VEHICLE_NUMBER, car_vehicle_number);
            }

            if (car_number != null) {
                initialValues.put(All_Date_content.KEY_All_CAR_NUMBER, car_number);
            }

            if (car_model >= 0) {
                initialValues.put(All_Date_content.KEY_All_CAR_MODEL, car_model);
            }

            if (oil_type != null) {
                initialValues.put(All_Date_content.KEY_All_OIL_TYPE, oil_type);
            }

            if (mileage >= 0) {
                initialValues.put(All_Date_content.KEY_All_MILEAGE, mileage);
            }

            if (auto_time >= 0) {
                initialValues.put(All_Date_content.KEY_All_TMIE, auto_time);
            }

            if (oil >= 0) {
                initialValues.put(All_Date_content.KEY_All_OIL, oil);
            }

            if (auto_coolant >= 0) {
                initialValues.put(All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT, auto_coolant);
            }

            if (fuel_consumption >= 0) {
                initialValues.put(All_Date_content.KEY_All_FUEL_CONSUMPTION, fuel_consumption);
            }

            if (start_date != null) {
                initialValues.put(All_Date_content.KEY_All_START_DATE, start_date);
            }

            if (fuel_efficienct >= 0) {
                initialValues.put(All_Date_content.KEY_All_FUEL_EFFICIENCT, fuel_efficienct);
            }

            if (down_speed >= 0) {
                initialValues.put(All_Date_content.KEY_All_DRIVE_DOWN_SPEEd, down_speed);
            }

            if (up_speed >= 0) {
                initialValues.put(All_Date_content.KEY_All_DRIVE_UP_SPEEd, up_speed);
            }

            if (idle >= 0) {
                initialValues.put(All_Date_content.KEY_All_IDLE, idle);
            }

            if (drive_style_drive >= 0) {
                initialValues.put(All_Date_content.KEY_All_DRIVE, drive_style_drive);
            }

            if (enging_oil >= 0) {
                initialValues.put(All_Date_content.KEY_All_ENGINE_OIL, enging_oil);
            }

            if (tire >= 0) {
                initialValues.put(All_Date_content.KEY_All_TIRE, tire);
            }

            if (brake_lining >= 0) {
                initialValues.put(All_Date_content.KEY_All_BRAKE_LINING, brake_lining);
            }

            if (aircon_filter >= 0) {
                initialValues.put(All_Date_content.KEY_All_AIRCON_FILTER, aircon_filter);
            }

            if (wiper >= 0) {
                initialValues.put(All_Date_content.KEY_All_WIPER, wiper);
            }

            if (equip_coolant >= 0) {
                initialValues.put(All_Date_content.KEY_All_EQUIP_COOLANT, equip_coolant);
            }

            if (transmission_oil >= 0) {
                initialValues.put(All_Date_content.KEY_All_TRANSMISSION_OIL, transmission_oil);
            }

            if (battery >= 0) {
                initialValues.put(All_Date_content.KEY_All_BATTERY, battery);
            }

            if (enging_oil_day != null) {
                initialValues.put(All_Date_content.KEY_All_ENGINE_OIL_DAY, enging_oil_day);
            }

            if (tire_day != null) {
                initialValues.put(All_Date_content.KEY_All_TIRE_DAY, tire_day);
            }

            if (brake_lining_day != null) {
                initialValues.put(All_Date_content.KEY_All_BRAKE_LINING_DAY, brake_lining_day);
            }

            if (aircon_filter_day != null) {
                initialValues.put(All_Date_content.KEY_All_AIRCON_FILTER_DAY, aircon_filter_day);
            }

            if (wiper_day != null) {
                initialValues.put(All_Date_content.KEY_All_WIPER_DAY, wiper_day);
            }

            if (equip_coolant_day != null) {
                initialValues.put(All_Date_content.KEY_All_EQUIP_COOLANT_DAY, equip_coolant_day);
            }

            if (transmission_oil_day != null) {
                initialValues.put(All_Date_content.KEY_All_TRANSMISSION_OIL_DAY, transmission_oil_day);
            }

            if (battery_day != null) {
                initialValues.put(All_Date_content.KEY_All_BATTERY_DAY, battery_day);
            }

            if (day != null) {
                initialValues.put(All_Date_content.KEY_All_DAY, day);
            }

            if (save_mileage > 0) {
                initialValues.put(All_Date_content.KEY_All_SAVE_MILEAGE, save_mileage);
            }

            return mDB.update(DATABASE_ALL_DATA_TABLE, initialValues, All_Date_content.KEY_ROWID + "=?", new String[]{String.valueOf(rowId)}) > 0;
        }
    }

}
