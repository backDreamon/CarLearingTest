package kr.hdd.carleamingTest;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.hdd.carleamingTest.activity.BlackBoxActivity;
import kr.hdd.carleamingTest.activity.CarBodyActivity;
import kr.hdd.carleamingTest.activity.data.AllDataContentData;
import kr.hdd.carleamingTest.common.Constants;
import kr.hdd.carleamingTest.common.data.OBDData;
import kr.hdd.carleamingTest.common.data.UserCheckDataRes;
import kr.hdd.carleamingTest.common.data.UserReviseData;
import kr.hdd.carleamingTest.common.popup.PopupCarInfoEdit;
import kr.hdd.carleamingTest.database.AllDataDBsetting;
import kr.hdd.carleamingTest.database.AutoDBAsycTask;
import kr.hdd.carleamingTest.http.GsonPostHttpHelper;
import kr.hdd.carleamingTest.http.UrlConstants;
import kr.hdd.carleamingTest.model.BluetoothReadData;
import kr.hdd.carleamingTest.model.BluetoothService;
import kr.hdd.carleamingTest.model.DeviceListActivity;
import kr.hdd.carleamingTest.req.PidConfig;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.PhoneNumberUtil;
import kr.hdd.carleamingTest.util.SupportedPidUtill;
import kr.hdd.carleamingTest.util.Utils;

import static kr.hdd.carleamingTest.MainApplication.IsBtFlag;
import static kr.hdd.carleamingTest.MainApplication.isConnectBT;

public class MainActivity extends Activity implements OnClickListener, LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
//	private final String PKG_NAME = "kr.hud.carleaming";

    private Context mContext = null;
    private BluetoothService mBluetoothService = null;

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int MAINTITLECARNAME = 3;

    private TextView mMainCarName = null;
    private TextView mMainTitle = null;

    private Button mBtnAtuoDiagnosis = null;
    private Button mBtnConsumableManagement = null;
    private Button mBtnDriveStyle = null;
    private Button mBtnDriveMonitoring = null;
    private Button mBtnPakingGpsSearch = null;
    private Button mBtnBlackBox = null;
    private Button mBtnCarInfo = null;

    private BluetoothAdapter mBtAdapter = null;

    // String buffer for outgoing messages
//	private StringBuffer mOutStringBuffer;
    private String mMacAddress = null;
    private String mMyPhoneNumber = null;
    private String mCarName = null;

    private String mProvider = null;
    private LocationManager mLocationManager = null;

    private AutoDBAsycTask mAllDataDBAsycTask = null;

    private double lat = 0;
    private double lng = 0;

    //	private boolean isConnectBT= false;
    private boolean isreConnectBT = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mContext = this;

        mMainCarName = (TextView) findViewById(R.id.main_car_name);
        mMainTitle = (TextView) findViewById(R.id.main_icon);

        mBtnAtuoDiagnosis = (Button) findViewById(R.id.main_btn_auto_diagnosis);  //차량 자동진단
        mBtnConsumableManagement = (Button) findViewById(R.id.main_btn_equip);  //소모품 관리
        mBtnDriveStyle = (Button) findViewById(R.id.main_btn_drive_style);  //주행 스타일 분석
        mBtnDriveMonitoring = (Button) findViewById(R.id.main_btn_drive_monitoring); // 주행 모니터링
        mBtnPakingGpsSearch = (Button) findViewById(R.id.main_btn_paking_gps_search); //주차위치찾기
        mBtnBlackBox = (Button) findViewById(R.id.btn_blackBox);  //블랙박스
        mBtnCarInfo = (Button) findViewById(R.id.btn_car_info);  //차량정보입력

        mBtnAtuoDiagnosis.setOnClickListener(this);
        mBtnConsumableManagement.setOnClickListener(this);
        mBtnDriveStyle.setOnClickListener(this);
        mBtnDriveMonitoring.setOnClickListener(this);
        mBtnPakingGpsSearch.setOnClickListener(this);
        mBtnBlackBox.setOnClickListener(this);
        mBtnCarInfo.setOnClickListener(this);
        mMainTitle.setOnClickListener(this);

        CarLLog.d(TAG, "BT state : " + MainApplication.IsBtFlag);
        if(IsBtFlag) {
            mMainTitle.setBackgroundResource(R.drawable.main_title_on);
        } else {
            mMainTitle.setBackgroundResource(R.drawable.main_title);
        }


        mMacAddress = getMacAddress(this);
        mMyPhoneNumber = PhoneNumberUtil.getPhoneNumber(this);//"01011112132";//PhoneNumberUtil.getPhoneNumber(this);

        CarLLog.v(TAG, "macAddress : " + mMacAddress);
        CarLLog.v(TAG, "mMyPhoneNumber : " + mMyPhoneNumber);

        //파라미터 설정
        Map<String, String> params = new HashMap<String, String>();
        params.put("entityAlias", "userinfo");
        params.put("method", "insert");
        params.put("userPhoneBTMac", getCutMacAddress(mMacAddress));
        params.put("telephone", getCutMacAddress(mMyPhoneNumber));

        new GsonPostHttpHelper<UserCheckDataRes>(this, mCheckResHandler).
                startRequest(params, UrlConstants.SERVER + UrlConstants.URL_COMMON, UserCheckDataRes.class);

        mapLocation();

        chkGpsService();

        reConnectBT();

        mAllDataDBAsycTask = new AutoDBAsycTask(mContext, mStopAsyncTaskHandler);
        mAllDataDBAsycTask.execute();
    }

    public void mapLocation() {
        //map
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria c = new Criteria();
        mProvider = mLocationManager.getBestProvider(c, true);

        if (mProvider == null || !mLocationManager.isProviderEnabled(mProvider)) {
            List<String> list = mLocationManager.getAllProviders();

            for (int i = 0; i < list.size(); i++) {
                String temp = list.get(i);

                if (mLocationManager.isProviderEnabled(mProvider)) {
                    mProvider = temp;
                    break;
                }
            }
        }

        //마지막으로 조회했던 위치 얻기
        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(mProvider);

        if (location == null) {
            Toast.makeText(this, "사용 가능한 위치 정보 제공자가 없습니다.", Toast.LENGTH_LONG).show();
            CarLLog.e(TAG, "사용 가능한 위치 정보 제공자가 없습니다. lat: " + lat + ", lng: " + lng);
        } else {
            //최종 위해에서부터 이어서 GPS시작
            onLocationChanged(location);

        }
    }

    //GPS 설정 체크
    private boolean chkGpsService() {

        String gps = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        CarLLog.d(gps, "gps");

        if (!(gps.matches(".*gps.*") || gps.matches(".*network.*"))) {

            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
            gsDialog.setTitle("위치 서비스 설정");
            gsDialog.setMessage("무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정해주세요."/*"무선 네트워크 사용, GPS 위성 사용을 모두 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?"*/);
            gsDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // GPS설정 화면으로 이동
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            }).create().show();
//			.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					return;
//				}
//			}).create().show();
            return false;

        } else {
            return true;
        }
    }

    private String getCutMacAddress(String address) {
        CarLLog.w(TAG, "address: " + address);
        if (address != null)
            return address.replace(":", "");
        else
            return address = "001122AABBCC";
    }

    /**
     * 안드로이드 맥어드레스가져오기
     */
    public String getMacAddress(Context context) {
        String macAddress = BluetoothAdapter.getDefaultAdapter().getAddress();
        return macAddress;
    }

    public Handler moniterHandler = new Handler() {

        public void handleMessage(Message msg) {
            //주행모니터링
            Intent activityIntent = new Intent(mContext, CarBodyActivity.class);
            activityIntent.putExtra("gps_lat", String.valueOf(lat));
            activityIntent.putExtra("gps_lng", String.valueOf(lng));
            activityIntent.setType(Constants.DRIVE_MONITORING);
            startActivity(activityIntent);
        }
    };

    private Intent mDeviceList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            /** 추가된 부분 시작 **/
            case REQUEST_CONNECT_DEVICE:
                CarLLog.e(TAG, "REQUEST_CONNECT_DEVICE");
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    isConnectBT = true;

                    mAllDataDBAsycTask = new AutoDBAsycTask(mContext, mStopAsyncTaskHandler);
                    mAllDataDBAsycTask.execute();

                    //파라미터 설정
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("entityAlias", "userinfo");
                    params.put("method", "update");
                    params.put("userId", MainApplication.getmUserId());
                    params.put("userPhoneBTMac", getCutMacAddress(mMacAddress));
                    params.put("userOBDBTMac", MainApplication.getmObdMacAddress());
                    params.put("telephone", mMyPhoneNumber);

                    new GsonPostHttpHelper<UserReviseData>(getApplicationContext(), mReviseHandler2).
                            startRequest(params, UrlConstants.SERVER + UrlConstants.URL_COMMON, UserReviseData.class);


                    mDeviceList = new Intent(MainActivity.this, BluetoothReadData.class);
                    mDeviceList.putExtra("intentdata", data);
                    this.startService(mDeviceList);

                    if (isreConnectBT) {
                        isreConnectBT = false;

                        moniterHandler.sendEmptyMessageDelayed(0, 100);
                    }
                } else {
                    Toast.makeText(this, "블루투스가 연결 중이지 않습니다.", Toast.LENGTH_SHORT).show();
//				isreConnectBT= true;
                    isConnectBT = false;
                }
                break;

            case REQUEST_ENABLE_BT:
                CarLLog.e(TAG, "REQUEST_ENABLE_BT");
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // 확인 눌렀을 때

                    // Initialize the BluetoothChatService to perform bluetooth connections
                    Intent serverIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

                    // Initialize the buffer for outgoing messages
//				mOutStringBuffer = new StringBuffer("");
                } else {
                    // 취소 눌렀을 때
                    CarLLog.d(TAG, "Bluetooth is not enabled");

//				Toast.makeText(this, R.string.toast_bluetooth_not_enable, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case MAINTITLECARNAME:

                mCarName = MainApplication.getmUserCarName();

                if (mCarName != null && !"".equals(mCarName) && mCarName.length() > 0) {
                    mMainCarName.setText(mCarName);
                    MainApplication.setmUserCarName(mCarName);
                } else {
                    mMainCarName.setText("차량이름을 입력해주세요");
                }

                break;
        }
    }

    private void reConnectBT() {
        CarLLog.v(TAG, "reConnectBT()");
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBtAdapter == null) {

            finish();

        } else {
            if (mBtAdapter.isEnabled()) {
                CarLLog.v(TAG, "블루투스 온");

                getMacAddress(MainActivity.this);

                mBtAdapter.cancelDiscovery();
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);

            } else {
                CarLLog.v(TAG, "블루투스 오프");

                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }

        }
    }

    @Override
    public void onClick(View v) {

        Intent activityIntent = null;

        switch (v.getId()) {
            case R.id.main_icon:

                activityIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(activityIntent, REQUEST_CONNECT_DEVICE);
                break;

            case R.id.main_btn_auto_diagnosis:
                if (!MainApplication.isConnectBT) {
                    CarLLog.d(TAG, isConnectBT+"");
                    Toast.makeText(mContext, "블루투스가 연결 중이지 않습니다.", Toast.LENGTH_SHORT).show();
                    enableBluetooth();
                } else {
                    //차량 자동 진단
                    activityIntent = new Intent(this, CarBodyActivity.class);
                    activityIntent.putExtra("oilVelue", MainApplication.getOil());
                    activityIntent.putExtra("KmVelue", MainApplication.getAuto_time());
                    activityIntent.setType(Constants.AUTO_DIAGNOSIS);
                    startActivity(activityIntent);
                    break;
                }

            case R.id.main_btn_equip:
                if (!isConnectBT)
                    Toast.makeText(mContext, "블루투스가 연결 중이지 않습니다.", Toast.LENGTH_SHORT).show();

                //소모품관리
                activityIntent = new Intent(this, CarBodyActivity.class);
                activityIntent.putExtra("gps_lat", String.valueOf(lat));
                activityIntent.putExtra("gps_lng", String.valueOf(lng));
                activityIntent.setType(Constants.EQUIP);
                startActivityForResult(activityIntent, MAINTITLECARNAME);

                break;
            case R.id.main_btn_drive_style:
                if (!isConnectBT)
                    Toast.makeText(mContext, "블루투스가 연결 중이지 않습니다.", Toast.LENGTH_SHORT).show();

                //주행스타일
                activityIntent = new Intent(this, CarBodyActivity.class);
                activityIntent.putExtra("gps_lat", String.valueOf(lat));
                activityIntent.putExtra("gps_lng", String.valueOf(lng));
                activityIntent.setType(Constants.DRIVE_STYLE);
                startActivity(activityIntent);

                break;
            case R.id.main_btn_drive_monitoring:
                if (isConnectBT) {
                    //주행모니터링
                    activityIntent = new Intent(this, CarBodyActivity.class);
                    activityIntent.putExtra("gps_lat", String.valueOf(lat));
                    activityIntent.putExtra("gps_lng", String.valueOf(lng));
                    activityIntent.setType(Constants.DRIVE_MONITORING);
                    startActivity(activityIntent);

                    isreConnectBT = false;
                } else {
                    isreConnectBT = true;
                    reConnectBT();
                }

                break;

            case R.id.main_btn_paking_gps_search:
                //주차위치찾기
                activityIntent = new Intent(this, CarBodyActivity.class);
                activityIntent.putExtra("gps_lat", String.valueOf(lat));
                activityIntent.putExtra("gps_lng", String.valueOf(lng));
                activityIntent.setType(Constants.PAKING_GPS_SEARCH);
                startActivity(activityIntent);

                break;

            case R.id.btn_blackBox:
                //블랙박스
                activityIntent = new Intent(this, BlackBoxActivity.class);
                startActivity(activityIntent);

                break;

            case R.id.btn_car_info:
                //차량정보입력
                PopupCarInfoEdit popup = new PopupCarInfoEdit(this, mHandler);
                popup.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                popup.show();
                break;

        }
    }

    public void enableBluetooth() {
        CarLLog.i(TAG, "Check the enabled Bluetooth");

        if (mBtAdapter.isEnabled()) {
            // 기기의 블루투스 상태가 On인 경우
            CarLLog.d(TAG, "Bluetooth Enable Now");

            // Next Step
            scanDevice();
        } else {
            // 기기의 블루투스 상태가 Off인 경우
            CarLLog.d(TAG, "Bluetooth Enable Request");

            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i, REQUEST_ENABLE_BT);
        }
    }

    /**
     * Available device search
     */
    public void scanDevice() {
        CarLLog.d(TAG, "Scan Device");

        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

    public  boolean btConnected() {
        if(!isConnectBT) {
            Toast.makeText(mContext, "블루투스가 연결 중이지 않습니다.", Toast.LENGTH_SHORT).show();
            enableBluetooth();
            return false;
        } else {
            return true;
        }

    }

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (msg != null) {
                mCarName = (String) msg.obj;

                if (mCarName != null && !"".equals(mCarName) && mCarName.length() > 0) {
                    mMainCarName.setText(mCarName);
                    MainApplication.setmUserCarName(mCarName);
                } else {
                    mMainCarName.setText("차량이름을 입력해주세요");
                }
            }

            return false;
        }
    });

    @Override
    public void onBackPressed() {
        CarLLog.e(TAG, "onBackPressed()");

        mDeviceList = new Intent(this, BluetoothReadData.class);
        this.stopService(mDeviceList);

        super.onBackPressed();

    }

    public String getLat() {
        return String.valueOf(lat);
    }

    public String getLng() {
        return String.valueOf(lng);
    }

    @Override
    public void onLocationChanged(Location location) {
        // 위치가 변했을 경우 호출
        //위도, 경도
        lat = location.getLatitude();
        lng = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    private Handler mCheckResHandler = new Handler(new Handler.Callback() {

        @SuppressWarnings("unused")
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GsonPostHttpHelper.RES_SUCCESS:
                    if (msg != null) {
                        UserCheckDataRes response = (UserCheckDataRes) msg.obj;
                        if (response.result) {
                            CarLLog.e(TAG, "response.getInjectMsg() : " + response.mUserId);
                            MainApplication.setmUserId(response.mUserId);
                        } else {
                            CarLLog.e(TAG, "response.getInjectMsg() null 등록 : ");
                        }
                    } else {
                        CarLLog.e(TAG, "response Error : " + msg.obj);
                    }
                    break;

                case GsonPostHttpHelper.RES_FAIL:
                    CarLLog.e(TAG, "VolleyError : " + msg.obj);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    private Handler mReviseHandler2 = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GsonPostHttpHelper.RES_SUCCESS:
                    if (msg != null) {
                        UserReviseData res = (UserReviseData) msg.obj;
                        CarLLog.e(TAG, "res : " + res.message);
                    }
                    break;

                case GsonPostHttpHelper.RES_FAIL:
                    CarLLog.e(TAG, "VolleyError : " + msg.obj);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    private Handler mObdDataHandler2 = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case GsonPostHttpHelper.RES_SUCCESS:
                    if (msg != null) {

                        CarLLog.v(TAG, "json respone : " + msg.obj.toString());

                    }
                    break;

                case GsonPostHttpHelper.RES_FAIL:
                    CarLLog.e(TAG, "VolleyError : " + msg.obj);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public void onResume() {
        super.onResume();

        //위치 정보 객체에 이벤트 연결
        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(mProvider, 500, 1, this);

        MainApplication.setDay(Utils.Date());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//		BluetoothReadData btData = new BluetoothReadData();
        //		btData.mThread = null;
        mDeviceList = new Intent(MainActivity.this, BluetoothReadData.class);
        this.stopService(mDeviceList);

//		CarLLog.v("hhm","getAuto_coolant : " + MainApplication.getAuto_coolant());
//		CarLLog.v("hhm","getFuel_consumption : " + MainApplication.getFuel_consumption());
//		CarLLog.v("hhm","getIdle : " + MainApplication.getIdle());
//		CarLLog.v("hhm","getAuto_time: " + MainApplication.getAuto_time());
//		CarLLog.v(TAG,"getMileage: " + MainApplication.getMileage());

        //거리를 소수점 첫번째자리 외에 자리수는 자름
//		String TempMileage = String.format("%.2f", MainApplication.getMileage());

//		if(MainApplication.getFuel_consumption()<= 0)
//			Toast.makeText(mContext, "데이터 오류", Toast.LENGTH_SHORT).show();
//		else
//			Toast.makeText(mContext, "정상 저장", Toast.LENGTH_SHORT).show();

        //DB저장
        new AllDataDBsetting(mContext, MainApplication.getmUserId(), MainApplication.getmUserCarName(), MainApplication.getCar_vehicle_number(),
                MainApplication.getCar_number(), MainApplication.getCar_model(), MainApplication.getOil_type(),
                MainApplication.getMileage(), MainApplication.getAuto_time(), MainApplication.getOil(), MainApplication.getAuto_coolant(),
                MainApplication.getFuel_consumption(), MainApplication.getStart_date(), MainApplication.getFuel_efficienct(),
                MainApplication.getDrive_time(), MainApplication.getDrive_down_speed(), MainApplication.getDrive_up_speed(),
                MainApplication.getIdle(), MainApplication.getDrive(), MainApplication.getEngine_oil(), MainApplication.getTire(),
                MainApplication.getBrake_lining(), MainApplication.getAircon_filter(), MainApplication.getWiper(),
                MainApplication.getEquip_coolant(), MainApplication.getTransmission_oil(), MainApplication.getBattery(),
                Utils.Date(), MainApplication.getEngine_oil_day(), MainApplication.getTire_day(), MainApplication.getBrake_lining_day(),
                MainApplication.getAircon_filter_day(), MainApplication.getWiper_day(), MainApplication.getEquip_coolant_day(),
                MainApplication.getTransmission_oil_day(), MainApplication.getBattery_day(), MainApplication.getSava_mileage());


        SharedPreferences prefs = getSharedPreferences("Map_sava", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Map_lat", String.valueOf(lat));
        editor.putString("Map_lng", String.valueOf(lng));
        editor.putString("Map_Data", Utils.JunmDate());
        editor.commit();

        //서버에 데이터 전송
        new GsonPostHttpHelper<OBDData>(this, mObdDataHandler2).
                startRequest(new PidConfig().getJson(), UrlConstants.SERVER + UrlConstants.OBD_DATA, OBDData.class);

        SupportedPidUtill.getInstance().setListsAllClear();
    }

    //다음화면으로 넘어갈 때 일시정지 처리
    @Override
    public void onPause() {
        super.onPause();

        CarLLog.v(TAG, "onPause");

        //위치 정보 객체에 이벤트 해제
        if(Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(this);

        MainApplication.setDay(Utils.Date());
    }

    private final Handler mStopAsyncTaskHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (mAllDataDBAsycTask != null && mAllDataDBAsycTask.getStatus() == AsyncTask.Status.FINISHED && msg.arg1 == MainApplication.handlerAuto) {
                mAllDataDBAsycTask.cancel(true);
                mAllDataDBAsycTask = null;

                AllDataContentData auto = (AllDataContentData) msg.obj;
                if (msg.obj != null) {

                    if (auto.getdbId() >= 0) {
                        MainApplication.setdbId(auto.getdbId());
                    }

                    if (auto.getCar_Name() != null) {
                        mMainCarName.setText(auto.getCar_Name());
                        MainApplication.setmUserCarName(auto.getCar_Name());
                    }

                    if (auto.getUserId() != null) {
                        MainApplication.setmUserId(auto.getUserId());
                    }
                    if (auto.getCar_vehicle_number() != null) {
                        MainApplication.setCar_vehicle_number(auto.getCar_vehicle_number());
                    }
                    if (auto.getCar_number() != null) {
                        MainApplication.setCar_number(auto.getCar_number());
                    }
                    if (auto.getCar_model() >= 0) {
                        MainApplication.setCar_model(auto.getCar_model());
                    }
                    if (auto.getOil_type() != null) {
                        MainApplication.setOil_type(auto.getOil_type());
                    }
                    if (auto.getMileage() >= 0) {
                        MainApplication.setMileage(auto.getMileage());
                    }
                    if (auto.getAuto_time() >= 0) {
                        MainApplication.setAuto_time(auto.getAuto_time());
                    }
                    if (auto.getOil() >= 0) {
                        MainApplication.setOil(auto.getOil());
                    }
                    if (auto.getAuto_coolant() >= 0) {
                        MainApplication.setAuto_coolant(auto.getAuto_coolant());
                    }
                    if (auto.getFuel_consumption() >= 0) {
                        MainApplication.setFuel_consumption(auto.getFuel_consumption());
                    }
                    if (auto.getStart_date() != null) {
                        MainApplication.setStart_date(auto.getStart_date());
                    }
                    if (auto.getFuel_efficienct() >= 0) {
                        MainApplication.setFuel_efficienct(auto.getFuel_efficienct());
                    }
                    if (auto.getDrive_time() >= 0) {
                        MainApplication.setDrive_time(auto.getDrive_time());
                    }
                    if (auto.getDrive_down_speed() >= 0) {
                        MainApplication.setDrive_down_speed(auto.getDrive_down_speed());
                    }
                    if (auto.getDrive_up_speed() >= 0) {
                        MainApplication.setDrive_up_speed(auto.getDrive_up_speed());
                    }
                    if (auto.getIdle() >= 0) {
                        MainApplication.setIdle(auto.getIdle());
                    }
                    if (auto.getDrive() >= 0) {
                        MainApplication.setDrive(auto.getDrive());
                    }
                    if (auto.getEngine_oil() >= 0) {
                        MainApplication.setEngine_oil(auto.getEngine_oil());
                    }
                    if (auto.getTire() >= 0) {
                        MainApplication.setTire(auto.getTire());
                    }
                    if (auto.getBrake_lining() >= 0) {
                        MainApplication.setBrake_lining(auto.getBrake_lining());
                    }
                    if (auto.getAircon_filter() >= 0) {
                        MainApplication.setAircon_filter(auto.getAircon_filter());
                    }
                    if (auto.getWiper() >= 0) {
                        MainApplication.setWiper(auto.getWiper());
                    }
                    if (auto.getEquip_coolant() >= 0) {
                        MainApplication.setEquip_coolant(auto.getEquip_coolant());
                    }
                    if (auto.getTransmission_oil() >= 0) {
                        MainApplication.setTransmission_oil(auto.getTransmission_oil());
                    }
                    if (auto.getBattery() >= 0) {
                        MainApplication.setBattery(auto.getBattery());
                    }
                    if (auto.getEngine_oil_day() != null) {
                        MainApplication.setEngine_oil_day(auto.getEngine_oil_day());
                    }
                    if (auto.getTire_day() != null) {
                        MainApplication.setTire_day(auto.getTire_day());
                    }
                    if (auto.getBrake_lining_day() != null) {
                        MainApplication.setBrake_lining_day(auto.getBrake_lining_day());
                    }
                    if (auto.getAircon_filter_day() != null) {
                        MainApplication.setAircon_filter_day(auto.getAircon_filter_day());
                    }
                    if (auto.getWiper_day() != null) {
                        MainApplication.setWiper_day(auto.getWiper_day());
                    }
                    if (auto.getEquip_coolant_day() != null) {
                        MainApplication.setEquip_coolant_day(auto.getEquip_coolant_day());
                    }
                    if (auto.getTransmission_oil_day() != null) {
                        MainApplication.setTransmission_oil_day(auto.getTransmission_oil_day());
                    }
                    if (auto.getBattery_day() != null) {
                        MainApplication.setBattery_day(auto.getBattery_day());
                    }
                    if (auto.getDay() != null) {
                        MainApplication.setDay(auto.getDay());
                    }
                    if (auto.getSave_mileage() >= 0) {
                        MainApplication.setSava_mileage(auto.getSave_mileage());
                    }

                    mCarName = MainApplication.getmUserCarName();

                    if (mCarName != null && !"".equals(mCarName) && mCarName.length() > 0) {
                        CarLLog.v(TAG, "mCarName : " + mCarName);
                        mMainCarName.setText(mCarName);
                        MainApplication.setmUserCarName(mCarName);
                    } else {
                        mMainCarName.setText("차량이름을 입력해주세요");
                    }
                }
            }

            return false;
        }
    });

}
