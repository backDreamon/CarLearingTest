package kr.hdd.carleamingTest;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import kr.hdd.carleamingTest.activity.data.AllDataContentData;
import kr.hdd.carleamingTest.database.DBAdapter;
import kr.hdd.carleamingTest.model.BluetoothService;
import kr.hdd.carleamingTest.model.DeviceListActivity;
import kr.hdd.carleamingTest.util.CarLLog;

import static kr.hdd.carleamingTest.model.BluetoothService.REQUEST_CONNECT_DEVICE;
import static kr.hdd.carleamingTest.model.BluetoothService.REQUEST_ENABLE_BT;

public class MainApplication extends Application {

    public static final String TAG = "VolleyPatterns";

    private Context mContext = null;
    private BluetoothService mBluetoothService = null;
    private BluetoothAdapter btAdapter = null;

    private static MainApplication sInstance;

    private static RequestQueue mQueue = null;

    private static String mUserId = null;  //userid
    private static String mObdMacAddress = null; //obd 주소
    private static String mUserCarName = "";  //자동차 이름

    public static boolean Debug = true;

    /**
     * Check the enabled Bluetooth
     */


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        sInstance = this;

        mQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
        if (mQueue != null) {
            return mQueue;
        } else {
            CarLLog.v(TAG, "RequestQueue");
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    public static synchronized MainApplication getInstance() {
        return sInstance;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mQueue != null) {
            if (tag != null)
                mQueue.cancelAll(tag);
            else
                mQueue.cancelAll(TAG);
        }
    }

    public static String getmUserId() {
        return mUserId;
    }

    public static void setmUserId(String mUserId) {
        MainApplication.mUserId = mUserId;
    }

    public static String getmObdMacAddress() {
        return mObdMacAddress;
    }

    public static void setmObdMacAddress(String mObdMacAddress) {
        MainApplication.mObdMacAddress = mObdMacAddress;
    }

    public static String getmUserCarName() {
        return mUserCarName;
    }

    public static void setmUserCarName(String mUserCarName) {
        MainApplication.mUserCarName = mUserCarName;
    }

    public static final String LIST21 = "list_21";
    public static final String LIST0C = "list_0c";
    public static final String LIST03 = "list_0303";
    public static final String LIST05 = "list_05";
    public static final String LIST1F = "list_1F";
    public static final String LISTFUELEFF = "ListFuelEfficiency";
    public static final String LISTALLFUELEFF = "ListallFuelEfficiency";
    public static String TIME = "0";
    //	public static int RPM_TEMP = 0;
    public static int RPM_TEMP = 0;

    public static final int handlerAuto = 1;

    public static boolean isConnectBT = false;
    //	public static int SPEED_UP = 0;
//	public static int SPEED_DOWN = 0;
//	public static int GONG = 0;
//	public static int DRIVE = 0;
//	public static String MILEAGECOUNT = "0";		//현재 이동거리
//	public static String OILCOUNT = "0";
    public static int ERROR_KM = 0;
    public static String FUEL_CONSUMPTION_COUNT = "0";    //총유류소모량
    public static String TIMECOUNT = "0";
    public static String FULE_EFFICIENCY = "0";

    //action
    public class ACTION {
        public static final String BT_CONNECT_FAILED = "BT_CONNECT_FAILED";
        public static final String BT_CONNECTED = "BT_CONNECTED";
    }

    public static boolean IsBtFlag = false;

    public static final String ENGINE_COOLANT_TEMP = "0105",  //A-40
            ENGINE_RPM = "010C",  //((A*256)+B)/4
            ENGINE_LOAD = "0104",  // A*100/255
            VEHICLE_SPEED = "010D",  //A
            INTAKE_AIR_TEMP = "010F",  //A-40
            CONT_MODULE_VOLT = "0142",  //((A*256)+B)/1000
            AMBIENT_AIR_TEMP = "0146",  //A-40
            ENGINE_OIL_TEMP = "015C",  //A-40
            CATALYST_TEMP_B1S1 = "013C";  //(((A*256)+B)/10)-40

    public class DRIVE_MONITRING_DATA {
        public static final int RPM = 1;
        public static final int SPEED = 2;
        public static final int DRIVE_TIME = 3;
        public static final int ENGINE_LOAD = 4;
    }

    public class RECIVER {
        public static final String DATAID = "dataId";
        public static final String DATA = "data";
        public static final String RECIVER = "carleaming.model.sendreciver";
        public static final String RPM_RECIVER = "carleaming.model.sendreciver.rpm";
        public static final String SPEED_RECIVER = "carleaming.model.sendreciver.speed";
        public static final String DRIVE_TIME_RECIVER = "carleaming.model.sendreciver.drive_time";
        public static final String ENGINE_LOAD_RECIVER = "carleaming.model.sendreciver.engine_load";
        public static final String KM_RECIVER = "carleaming.model.sendreciver.KM";
        public static final String TOTAL_OIL = "carleaming.model.sendreciver.TOIL";
        public static final String OIL_PRICE = "carleaming.model.sendreciver.Oil_price";
        public static final String ERROR_KM_RECIVER = "carleaming.model.sendreciver.KM.error";
        public static final String MAF_RECIVER = "carleaming.model.sendreciver.maf";
        public static final String FEUL_TYPE_RECIVER = "carleaming.model.sendreciver.feul_type";
        public static final String ENGINE_FUEL_RATE_RECIVER = "carleaming.model.sendreciver.engine_fuel_rate";
    }

    // Debugging
    public static final String[] PIDS00 = {
            "01", "02", "03", "04", "05", "06", "07", "08",
            "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
            "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "1A", "1B", "1C", "1D", "1E", "1F", "20"};

    public static final String[] PIDS20 = {
            "21", "22", "23", "24", "25", "26", "27", "28",
            "29", "2A", "2B", "2C", "2D", "2E", "2F", "30",
            "31", "32", "33", "34", "35", "36", "37", "38",
            "39", "3A", "3B", "3C", "3D", "3E", "3F", "40"};

    public static final String[] PIDS40 = {
            "41", "42", "43", "44", "45", "46", "47", "48",
            "4F", "4A", "4B", "4C", "4D", "4E", "4F", "50",
            "51", "52", "53", "54", "55", "56", "57", "58",
            "59", "5A", "5B", "5C", "5D", "5E", "5F", "60"};

    public static final String[] PIDS60 = {
            "61", "62", "63", "64", "65", "66", "67", "68",
            "69", "6A", "6B", "6C", "6D", "6E", "6F", "70",
            "71", "72", "73", "74", "75", "76", "77", "78",
            "79", "7A", "7B", "7C", "7D", "7E", "7F", "80"};

    public static final String[] PIDS80 = {
            "81", "82", "83", "84", "85", "86", "87", "88",
            "89", "8A", "8B", "8C", "8D", "8E", "8F", "90",
            "91", "92", "93", "94", "95", "96", "97", "98",
            "99", "9A", "9B", "9C", "9D", "9E", "9F", "10"};

    public static boolean BLUETOOTHFLAG = false;

    /**
     * 디비 파일을 sd카드로 복사
     *
     * @param pkgName 패키지 명
     * @param dbName  디비 파일 명
     * @throws IOException
     */
    public static void copyDBtoSdCard(String pkgName, String dbName) throws IOException {
        FileChannel src = null, dst = null;

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            CarLLog.e(TAG, "sd : " + sd);
            CarLLog.e(TAG, "data : " + data);

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + pkgName + "//databases//" + dbName;
                String backupDBPath = DBAdapter.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    src = new FileInputStream(currentDB).getChannel();
                    dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (src != null) {
                src.close();
                dst.close();
            }
        }
    }

    public final static String OIL_GASOL = "휘발유";
    public final static String OIL_DISEL = "디젤";
    public final static String OIL_LPG = "LPG";

    public static long dbId = 0;
    public static String userId = "";
    public static String car_vehicle_number = "";
    public static String car_number = "";
    public static int car_model = -1;
    public static String oil_type = OIL_GASOL;    //기본 디젤은 테스트	OIL_GASOL
    public static double mileage = 0;            //현재 이동거리
    public static int auto_time = 0;
    public static double oil = 0;                //총유류비
    public static double fuel_consumption = 0;    //총 유류 소모량
    public static double auto_coolant = 0;
    public static String start_date = "0";
    public static double fuel_efficienct = 0;
    public static int drive_time = 0;            //총 시간
    public static int drive_down_speed = 0;
    public static int drive_up_speed = 0;
    public static int idle = 0;
    public static int drive = 0;
    public static double engine_oil = 0;
    public static double tire = 0;
    public static double brake_lining = 0;
    public static double aircon_filter = 0;
    public static double wiper = 0;
    public static double equip_coolant = 0;
    public static double transmission_oil = 0;
    public static double battery = 0;
    public static String engine_oil_day = "0";
    public static String tire_day = "0";
    public static String brake_lining_day = "0";
    public static String aircon_filter_day = "0";
    public static String wiper_day = "0";
    public static String equip_coolant_day = "0";
    public static String transmission_oil_day = "0";
    public static String battery_day = "0";
    public static String day = "2015.01.01";
    public static double sava_mileage = 0;        //총 이동 거리
    public static ArrayList<AllDataContentData> AllDataArraylist = new ArrayList<AllDataContentData>();

    public static String mBox01_date = null;
    public static String mBox02_date = null;
    public static String mBox03_date = null;
    public static String mBox04_date = null;
    public static String mBox05_date = null;
    public static String mBox06_date = null;
    public static String mBox07_date = null;
    public static String mBox08_date = null;

    public static String mBox01_check_date = null;
    public static String mBox02_check_date = null;
    public static String mBox03_check_date = null;
    public static String mBox04_check_date = null;
    public static String mBox05_check_date = null;
    public static String mBox06_check_date = null;
    public static String mBox07_check_date = null;
    public static String mBox08_check_date = null;

    public static long getdbId() {
        return dbId;
    }

    public static void setdbId(long dbId) {
        MainApplication.dbId = dbId;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        MainApplication.userId = userId;
    }

    public static String getCar_vehicle_number() {
        return car_vehicle_number;
    }

    public static void setCar_vehicle_number(String car_vehicle_number) {
        MainApplication.car_vehicle_number = car_vehicle_number;
    }

    public static String getCar_number() {
        return car_number;
    }

    public static void setCar_number(String car_number) {
        MainApplication.car_number = car_number;
    }

    public static int getCar_model() {
        return car_model;
    }

    public static void setCar_model(int car_model) {
        MainApplication.car_model = car_model;
    }

    public static String getOil_type() {
        return oil_type;
    }

    public static void setOil_type(String oil_type) {
        MainApplication.oil_type = oil_type;
    }

    public static double getMileage() {
        return mileage;
    }

    public static void setMileage(double mileage) {
        MainApplication.mileage = mileage;
    }

    public static int getAuto_time() {
        return auto_time;
    }

    public static void setAuto_time(int auto_time) {
        MainApplication.auto_time = auto_time;
    }

    public static double getOil() {
        return oil;
    }

    public static void setOil(double oil) {
        MainApplication.oil = oil;
    }

    public static double getAuto_coolant() {
        return auto_coolant;
    }

    public static void setAuto_coolant(double auto_coolant) {
        MainApplication.auto_coolant = auto_coolant;
    }

    public static double getFuel_consumption() {
        return fuel_consumption;
    }

    public static void setFuel_consumption(double fuel_consumption) {
        MainApplication.fuel_consumption = fuel_consumption;
    }

    public static String getStart_date() {
        return start_date;
    }

    public static void setStart_date(String start_date) {
        MainApplication.start_date = start_date;
    }

    public static double getFuel_efficienct() {
        return fuel_efficienct;
    }

    public static void setFuel_efficienct(double fuel_efficienct) {
        MainApplication.fuel_efficienct = fuel_efficienct;
    }

    public static int getDrive_time() {
        return drive_time;
    }

    public static void setDrive_time(int drive_time) {
        MainApplication.drive_time = drive_time;
    }

    public static int getDrive_down_speed() {
        return drive_down_speed;
    }

    public static void setDrive_down_speed() {
        MainApplication.drive_down_speed++;
    }

    public static void setDrive_down_speed(int drive_down_speed) {
        MainApplication.drive_down_speed = drive_down_speed;
    }

    public static int getDrive_up_speed() {
        return drive_up_speed;
    }

    public static void setDrive_up_speed() {
        MainApplication.drive_up_speed++;
    }

    public static void setDrive_up_speed(int drive_up_speed) {
        MainApplication.drive_up_speed = drive_up_speed;
    }

    public static int getIdle() {
        return idle;
    }

    public static void setIdle() {
        MainApplication.idle++;
    }

    public static void setIdle(int idle) {
        MainApplication.idle = idle;
    }

    public static int getDrive() {
        return drive;
    }

    public static void setDrive() {
        MainApplication.drive++;
    }

    public static void setDrive(int drive) {
        MainApplication.drive = drive;
    }

    public static double getEngine_oil() {
        return engine_oil;
    }

    public static void setEngine_oil(double engine_oil) {
        MainApplication.engine_oil = engine_oil;
    }

    public static double getTire() {
        return tire;
    }

    public static void setTire(double tire) {
        MainApplication.tire = tire;
    }

    public static double getBrake_lining() {
        return brake_lining;
    }

    public static void setBrake_lining(double brake_lining) {
        MainApplication.brake_lining = brake_lining;
    }

    public static double getAircon_filter() {
        return aircon_filter;
    }

    public static void setAircon_filter(double aircon_filter) {
        MainApplication.aircon_filter = aircon_filter;
    }

    public static double getWiper() {
        return wiper;
    }

    public static void setWiper(double wiper) {
        MainApplication.wiper = wiper;
    }

    public static double getEquip_coolant() {
        return equip_coolant;
    }

    public static void setEquip_coolant(double equip_coolant) {
        MainApplication.equip_coolant = equip_coolant;
    }

    public static double getTransmission_oil() {
        return transmission_oil;
    }

    public static void setTransmission_oil(double transmission_oil) {
        MainApplication.transmission_oil = transmission_oil;
    }

    public static double getBattery() {
        return battery;
    }

    public static void setBattery(double battery) {
        MainApplication.battery = battery;
    }

    public static String getDay() {
        return day;
    }

    public static void setDay(String day) {
        MainApplication.day = day;
    }

    public static ArrayList<AllDataContentData> getAllDataArraylist() {
        return AllDataArraylist;
    }

    public static void setAllDataArraylist(
            ArrayList<AllDataContentData> allDataArraylist) {
        AllDataArraylist = allDataArraylist;
    }

    public static String getmBox01_date() {
        return mBox01_date;
    }

    public static void setmBox01_date(String mBox01_date) {
        MainApplication.mBox01_date = mBox01_date;
    }

    public static String getmBox02_date() {
        return mBox02_date;
    }

    public static void setmBox02_date(String mBox02_date) {
        MainApplication.mBox02_date = mBox02_date;
    }

    public static String getmBox03_date() {
        return mBox03_date;
    }

    public static void setmBox03_date(String mBox03_date) {
        MainApplication.mBox03_date = mBox03_date;
    }

    public static String getmBox04_date() {
        return mBox04_date;
    }

    public static void setmBox04_date(String mBox04_date) {
        MainApplication.mBox04_date = mBox04_date;
    }

    public static String getmBox05_date() {
        return mBox05_date;
    }

    public static void setmBox05_date(String mBox05_date) {
        MainApplication.mBox05_date = mBox05_date;
    }

    public static String getmBox06_date() {
        return mBox06_date;
    }

    public static void setmBox06_date(String mBox06_date) {
        MainApplication.mBox06_date = mBox06_date;
    }

    public static String getmBox07_date() {
        return mBox07_date;
    }

    public static void setmBox07_date(String mBox07_date) {
        MainApplication.mBox07_date = mBox07_date;
    }

    public static String getmBox08_date() {
        return mBox08_date;
    }

    public static void setmBox08_date(String mBox08_date) {
        MainApplication.mBox08_date = mBox08_date;
    }

    public static String getmBox01_check_date() {
        return mBox01_check_date;
    }

    public static void setmBox01_check_date(String mBox01_check_date) {
        MainApplication.mBox01_check_date = mBox01_check_date;
    }

    public static String getmBox02_check_date() {
        return mBox02_check_date;
    }

    public static void setmBox02_check_date(String mBox02_check_date) {
        MainApplication.mBox02_check_date = mBox02_check_date;
    }

    public static String getmBox03_check_date() {
        return mBox03_check_date;
    }

    public static void setmBox03_check_date(String mBox03_check_date) {
        MainApplication.mBox03_check_date = mBox03_check_date;
    }

    public static String getmBox04_check_date() {
        return mBox04_check_date;
    }

    public static void setmBox04_check_date(String mBox04_check_date) {
        MainApplication.mBox04_check_date = mBox04_check_date;
    }

    public static String getmBox05_check_date() {
        return mBox05_check_date;
    }

    public static void setmBox05_check_date(String mBox05_check_date) {
        MainApplication.mBox05_check_date = mBox05_check_date;
    }

    public static String getmBox06_check_date() {
        return mBox06_check_date;
    }

    public static void setmBox06_check_date(String mBox06_check_date) {
        MainApplication.mBox06_check_date = mBox06_check_date;
    }

    public static String getmBox07_check_date() {
        return mBox07_check_date;
    }

    public static void setmBox07_check_date(String mBox07_check_date) {
        MainApplication.mBox07_check_date = mBox07_check_date;
    }

    public static String getmBox08_check_date() {
        return mBox08_check_date;
    }

    public static void setmBox08_check_date(String mBox08_check_date) {
        MainApplication.mBox08_check_date = mBox08_check_date;
    }

    public static String getEngine_oil_day() {
        return engine_oil_day;
    }

    public static void setEngine_oil_day(String engine_oil_day) {
        MainApplication.engine_oil_day = engine_oil_day;
    }

    public static String getTire_day() {
        return tire_day;
    }

    public static void setTire_day(String tire_day) {
        MainApplication.tire_day = tire_day;
    }

    public static String getBrake_lining_day() {
        return brake_lining_day;
    }

    public static void setBrake_lining_day(String brake_lining_day) {
        MainApplication.brake_lining_day = brake_lining_day;
    }

    public static String getAircon_filter_day() {
        return aircon_filter_day;
    }

    public static void setAircon_filter_day(String aircon_filter_day) {
        MainApplication.aircon_filter_day = aircon_filter_day;
    }

    public static String getWiper_day() {
        return wiper_day;
    }

    public static void setWiper_day(String wiper_day) {
        MainApplication.wiper_day = wiper_day;
    }

    public static String getEquip_coolant_day() {
        return equip_coolant_day;
    }

    public static void setEquip_coolant_day(String equip_coolant_day) {
        MainApplication.equip_coolant_day = equip_coolant_day;
    }

    public static String getTransmission_oil_day() {
        return transmission_oil_day;
    }

    public static void setTransmission_oil_day(String transmission_oil_day) {
        MainApplication.transmission_oil_day = transmission_oil_day;
    }

    public static String getBattery_day() {
        return battery_day;
    }

    public static void setBattery_day(String battery_day) {
        MainApplication.battery_day = battery_day;
    }

    public static double getSava_mileage() {
        return sava_mileage;
    }

    public static void setSava_mileage(double sava_mileage) {
        MainApplication.sava_mileage = sava_mileage;
    }


}
