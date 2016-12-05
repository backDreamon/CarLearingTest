package kr.hdd.carleamingTest.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.common.Constants;
import kr.hdd.carleamingTest.database.AllDataDBsetting;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.Utils;

public class CarBodyActivity extends FragmentActivity implements OnClickListener, LocationListener {

    private static final String TAG = "CarBodyActivity";

    private static FragmentManager mFragmentManager = null;

    private ImageView mTitle = null;

    private LinearLayout mBodyLayout = null;
    private RelativeLayout mBodyMapLayout = null;

    private TextView mMapAddressDate = null;
    private TextView mMapAddress = null;

    private Button mBtnTitleRight = null;
    private Button mBtnTitleLeft = null;
    private Button mBtnMap_01 = null;
    private Button mBtnMap_02 = null;
    private Button mBtnMap_03 = null;
    private Button mBtnFinish = null;
    private Button mBtnHome = null;

    private double mMap_lat = 0;
    private double mMap_lng = 0;

    private GoogleMap mGoogleMap = null;

    private int DEFAULT_ZOOM_LEVEL = 6;
    private int mPageFlag = 0;

    private String mPageName = null;

    //map
    private String mProvider = null;
    private LocationManager mLocationManager = null;

    private boolean mMapFlag = true;

    private GoogleApiAvailability ggApiA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mFragmentManager = getSupportFragmentManager();
        CarLLog.w(TAG, "onCreate()");

        setContentView(R.layout.title_body_layout);

        mTitle = (ImageView) findViewById(R.id.title);  //타이틀
        mBtnTitleLeft = (Button) findViewById(R.id.car_body_title_left_btn);  //타이틀 왼쪽 버튼
        mBtnTitleRight = (Button) findViewById(R.id.car_body_title_right_btn);  //타이틀 오른쪽 버튼
        mBodyLayout = (LinearLayout) findViewById(R.id.main_body_fragment);  //중간 레이아웃
        mBodyMapLayout = (RelativeLayout) findViewById(R.id.main_body_fragment2);
        mBtnMap_01 = (Button) findViewById(R.id.map_btn_01);
        mBtnMap_02 = (Button) findViewById(R.id.map_btn_02);
        mBtnMap_03 = (Button) findViewById(R.id.map_btn_03);
        mBtnFinish = (Button) findViewById(R.id.btn_finish);
        mBtnHome = (Button) findViewById(R.id.home);

        mMapAddressDate = (TextView) findViewById(R.id.map_address_date);
        mMapAddress = (TextView) findViewById(R.id.map_address_text);

        mBtnTitleLeft.setOnClickListener(this);
        mBtnTitleRight.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
        mBtnHome.setOnClickListener(this);
        mBtnMap_01.setOnClickListener(this);
        mBtnMap_02.setOnClickListener(this);
        mBtnMap_03.setOnClickListener(this);

        Intent getIntent = getIntent();

        if (getIntent.getExtras().getString("gps_lat") != null) {
            mMap_lat = Double.parseDouble(getIntent.getExtras().getString("gps_lat"));
        }
        if (getIntent.getExtras().getString("gps_lng") != null) {
            mMap_lng = Double.parseDouble(getIntent.getExtras().getString("gps_lng"));
        }

        mapLocation();

        mPageName = getIntent.getType();
        LayoutTypeSetting(mPageName, mPageFlag);


        new AllDataDBsetting(this, MainApplication.getmUserId(), MainApplication.getmUserCarName(), MainApplication.getCar_vehicle_number(),
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


        ggApiA = GoogleApiAvailability.getInstance();
    }

    @Override
    public void onPause() {
        super.onPause();
        CarLLog.i(TAG, "Fuel_consumption: " + MainApplication.getFuel_consumption());

        new AllDataDBsetting(this, MainApplication.getmUserId(), MainApplication.getmUserCarName(), MainApplication.getCar_vehicle_number(),
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
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
//		CarLLog.e(TAG,"onResume() cccccccccccccccc");

//		if(!MainApplication.isConnectBT)
//			finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//			CarLLog.e(TAG, "onKeyDown(int keyCode, KeyEvent event)");
                finish();
                return false;
//			break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void LayoutTypeSetting(String activityName, int pageflag) {

        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment mNewFragMent = null;
        String tag = null;

        if (activityName == null) {
            Toast.makeText(this, getResources().getString(R.string.toast_restart), Toast.LENGTH_LONG).show();
            return;
        }

        if (activityName.equals(Constants.AUTO_DIAGNOSIS) || pageflag == 1) {  //차량자동진단

            mTitle.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.diagno_title));


            mBodyLayout.setVisibility(View.VISIBLE);
            mBodyMapLayout.setVisibility(View.GONE);
            mNewFragMent = new AutoDiagnosisFragment();
            tag = Constants.AUTO_DIAGNOSIS;

            mPageFlag = 1;

        } else if (activityName.equals(Constants.EQUIP) || pageflag == 2) {  //소모품관리
            mTitle.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.equip_title));

            mBodyLayout.setVisibility(View.VISIBLE);
            mBodyMapLayout.setVisibility(View.GONE);
            mNewFragMent = new EquipFragment();
            tag = Constants.EQUIP;

            mPageFlag = 2;

        } else if (activityName.equals(Constants.DRIVE_STYLE) || pageflag == 3) {  //주행스타일
            mTitle.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.driving_title));

            mBodyLayout.setVisibility(View.VISIBLE);
            mBodyMapLayout.setVisibility(View.GONE);
            mNewFragMent = new DriveStyleFragment();
            tag = Constants.DRIVE_STYLE;

            mPageFlag = 3;

        } else if (activityName.equals(Constants.DRIVE_MONITORING) || pageflag == 4) { //주행 모니터링
            mTitle.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.monitor_title));

            mBodyLayout.setVisibility(View.VISIBLE);
            mBodyMapLayout.setVisibility(View.GONE);
            mNewFragMent = new DriveMonitringFragment();
            tag = Constants.DRIVE_MONITORING;

            mPageFlag = 4;

        } else if (activityName.equals(Constants.PAKING_GPS_SEARCH) || pageflag == 5) { //주차위치찾기

            if (isNetworkStat(this)) {

                mMapFlag = true;

                mTitle.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.location_title));
                mBodyLayout.setVisibility(View.GONE);
                mBodyMapLayout.setVisibility(View.VISIBLE);
                mNewFragMent = new ParingGpsSearchFragment();
                tag = Constants.PAKING_GPS_SEARCH;

                //BitmapDescriptorFactory 생성하기 위한 소스
                MapsInitializer.initialize(this);

                ggApiA.isGooglePlayServicesAvailable(this);
                //mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                // GPS 맵이동
                setGpsCurrent(String.valueOf(mMap_lat), String.valueOf(mMap_lng), null);
            } else {
                mMapFlag = false;

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("네트워크 오류");
//		        네트워크 상태를 확인해 주십시요.
                dlg.setMessage("인터넷 연결을 할 수 없습니다.\n네트워크 통신을 확인해주세요");
                dlg.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        finish();
                    }
                });
                dlg.show();
            }

            mPageFlag = 5;

        }

        if (mMapFlag) {
            mFragmentTransaction.replace(R.id.main_body_fragment, mNewFragMent, tag);
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            mFragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * 인터넷 연결 체크
     * API 21버전에 맞추어 업데이트함
     */
    public static boolean isNetworkStat(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = manager.getAllNetworks();
            NetworkInfo networkInfo;
            for(Network mNetwork : networks) {
                networkInfo = manager.getNetworkInfo(mNetwork);
                if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if(manager != null) {
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (activeInfo != null) {
                    if(activeInfo.getType() == ConnectivityManager.TYPE_MOBILE
                            || activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        return true;
                    }
                }
            }
        }

        /*
        기존코드
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        boolean blte_4g = false;
        if (lte_4g != null)
            blte_4g = lte_4g.isConnected();
        if (mobile != null) {
            if (mobile.isConnected() || wifi.isConnected() || blte_4g)
                return true;
        } else {
            if (wifi.isConnected() || blte_4g)
                return true;
        } */

        return false;
    }

    //위도와 경도 기반으로 주소를 리턴하는 메서드
    public String getmapAddress(double lat, double lng) {
        String address = null;

        //위치 정보를 활용하기 위한 구글 API 객체
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        //주소 목록을 담기 위한HashMap
        List<Address> list = null;

        try {
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (list == null) {
            Log.v(TAG, "주소 데이터 얻기 실패");
            return null;
        }

        if (list.size() > 0) {
            Address addr = list.get(0);
//					address = addr.getCountryName() + " "
//							   + addr.getPostalCode() + " "
//							   + addr.getLocality() + " "
//							   + addr.getThoroughfare() + " "
//							   + addr.getFeatureName();

            address = addr.getLocality() + " "
                    + addr.getThoroughfare() + " "
                    + addr.getFeatureName();

        }

        return address;
    }

    private void setGpsCurrent(String strLat, String strLng, String strMapSavaData) {

        double latitude = 0;
        double longitude = 0;

//        GpsInfo gps = new GpsInfo(InsertMapMoveActivity.this);
//        // GPS 사용유무 가져오기
//        if (gps.isGetLocation()) {

        if (strLat.equals("") || strLng.equals("")) {
//                latitude = gps.getLatitude();
//                longitude = gps.getLongitude();

        } else {
            latitude = Double.parseDouble(strLat);
            longitude = Double.parseDouble(strLng);
        }

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Map 을 zoom 합니다.
        this.setZoomLevel(DEFAULT_ZOOM_LEVEL);

        // 마커 설정.
        MarkerOptions optFirst = new MarkerOptions();
        optFirst.position(latLng);// 위도 • 경도
//            optFirst.title("Current Position");// 제목 미리보기
//            optFirst.snippet("Snippet");
        optFirst.title(getmapAddress(latitude, longitude));
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon_point));
        mGoogleMap.addMarker(optFirst).showInfoWindow();

        if (strMapSavaData == null) {
            mMapAddressDate.setText(Utils.JunmDate());
        } else {
            mMapAddressDate.setText(strMapSavaData);
        }
        mMapAddress.setText(getmapAddress(latitude, longitude));
//        }
    }

    /**
     * 맵의 줌레벨을 조절합니다.
     *
     * @param level
     */
    private void setZoomLevel(int level) {
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(level));
//        Toast.makeText(this, "Zoom Level : " + String.valueOf(level), Toast.LENGTH_LONG).show();
    }

    /**
     * Map 클릭시 터치 이벤트
     */
    public void onMapClick(LatLng point) {

        // 현재 위도와 경도에서 화면 포인트를 알려준다
        Point screenPt = mGoogleMap.getProjection().toScreenLocation(point);

        // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
        LatLng latLng = mGoogleMap.getProjection().fromScreenLocation(screenPt);

        CarLLog.d("맵좌표", "좌표: 위도(" + String.valueOf(point.latitude) + "), 경도(" + String.valueOf(point.longitude) + ")");
        CarLLog.d("화면좌표", "화면좌표: X(" + String.valueOf(screenPt.x) + "), Y(" + String.valueOf(screenPt.y) + ")");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                finish();
                break;
            case R.id.home:
                finish();
                break;

            case R.id.map_btn_01:

                ggApiA.isGooglePlayServicesAvailable(this);
              //  mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                // GPS 맵이동
                setGpsCurrent(String.valueOf(mMap_lat), String.valueOf(mMap_lng), null);
                break;

            case R.id.map_btn_02:

                SharedPreferences prefs = getSharedPreferences("Map_sava", MODE_PRIVATE);

                ggApiA.isGooglePlayServicesAvailable(this);
              //  mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                String MapsavaData = prefs.getString("Map_Data", "");

                CarLLog.e("hhm", "lat : " + prefs.getString("Map_lat", "") + ", lng : " + prefs.getString("Map_lat", "") + ", data : " + MapsavaData);

//			if((prefs.getString("Map_lat", "") != null && "".equals(prefs.getString("Map_lat", "")))
//					&& (prefs.getString("Map_lng", "") != null && "".equals(prefs.getString("Map_lng", "")))){
                // GPS 맵이동
                setGpsCurrent(prefs.getString("Map_lat", ""), prefs.getString("Map_lng", ""), MapsavaData);
//			}
                break;

            case R.id.map_btn_03:
                ggApiA.isGooglePlayServicesAvailable(this);
                //mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();

                // GPS 맵이동
                setGpsCurrent(String.valueOf(mMap_lat), String.valueOf(mMap_lng), null);
                break;

            case R.id.car_body_title_left_btn:
                //타이틀 왼쪽버튼
                if (mPageFlag == 1 && mPageName.equals(Constants.AUTO_DIAGNOSIS)) {
                    mPageFlag = 5;
                    mPageName = Constants.PAKING_GPS_SEARCH;
                } else if (mPageFlag == 2 && mPageName.equals(Constants.EQUIP)) {
                    mPageFlag = 1;
                    mPageName = Constants.AUTO_DIAGNOSIS;
                } else if (mPageFlag == 3 && mPageName.equals(Constants.DRIVE_STYLE)) {
                    mPageFlag = 2;
                    mPageName = Constants.EQUIP;
                } else if (mPageFlag == 4 && mPageName.equals(Constants.DRIVE_MONITORING)) {
                    mPageFlag = 3;
                    mPageName = Constants.DRIVE_STYLE;
                } else if (mPageFlag == 5 && mPageName.equals(Constants.PAKING_GPS_SEARCH)) {
                    mPageFlag = 4;
                    mPageName = Constants.DRIVE_MONITORING;
                }

                LayoutTypeSetting(mPageName, mPageFlag);
                break;

            case R.id.car_body_title_right_btn:
                //타이틀 오른쪽 버튼
                if (mPageFlag == 1 && mPageName.equals(Constants.AUTO_DIAGNOSIS)) {
                    mPageFlag = 2;
                    mPageName = Constants.EQUIP;
                } else if (mPageFlag == 2 && mPageName.equals(Constants.EQUIP)) {
                    mPageFlag = 3;
                    mPageName = Constants.DRIVE_STYLE;
                } else if (mPageFlag == 3 && mPageName.equals(Constants.DRIVE_STYLE)) {
                    mPageFlag = 4;
                    mPageName = Constants.DRIVE_MONITORING;
                } else if (mPageFlag == 4 && mPageName.equals(Constants.DRIVE_MONITORING)) {
                    mPageFlag = 5;
                    mPageName = Constants.PAKING_GPS_SEARCH;
                } else if (mPageFlag == 5 && mPageName.equals(Constants.PAKING_GPS_SEARCH)) {
                    mPageFlag = 1;
                    mPageName = Constants.AUTO_DIAGNOSIS;
                }

                LayoutTypeSetting(mPageName, mPageFlag);
                break;
        }
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
            Toast.makeText(this, "사용 가능한 위치 정보 제공자가 없습니다.", Toast.LENGTH_LONG);
            CarLLog.e(TAG, "사용 가능한 위치 정보 제공자가 없습니다. mMap_lat: " + mMap_lat + ", mMap_lng: " + mMap_lng);
        } else {
            //최종 위해에서부터 이어서 GPS시작
            onLocationChanged(location);

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // 위치가 변했을 경우 호출
        //위도, 경도
        mMap_lat = location.getLatitude();
        mMap_lng = location.getLongitude();

        CarLLog.v(TAG, "위도 : " + String.valueOf(mMap_lat) + ", 경도 : " + String.valueOf(mMap_lng));
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
}
