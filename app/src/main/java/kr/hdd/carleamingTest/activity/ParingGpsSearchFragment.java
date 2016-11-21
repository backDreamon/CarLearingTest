package kr.hdd.carleamingTest.activity;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.util.CarLLog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ParingGpsSearchFragment extends Fragment {

	private static final String TAG = ParingGpsSearchFragment.class.getSimpleName();
	
	private GoogleMap mGoogleMap = null;
	
	private int DEFAULT_ZOOM_LEVEL = 13;
	
	private TextView mTestText = null;
	private Button mTestBtn = null;
	
	private String mStrLat = null;
	private String mStrLng = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getArguments() != null){
			if(getArguments().getString("Map_lat") != null){
				mStrLat = getArguments().getString("Map_lat");
				CarLLog.v(TAG,"mStrLat : "+mStrLat);
			}
			if(getArguments().getString("Map_lng") != null){
				mStrLng = getArguments().getString("Map_lng");
				CarLLog.v(TAG,"mStrLng : "+mStrLng);
			}
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.paring_gps_search_layout, null);
		//테스트  코드
//		mTestText = (TextView) view.findViewById(R.id.auto_diagnosis_test_text);
//		mTestBtn = (Button) view.findViewById(R.id.auto_diagnosis_test_btn);
//		mTestText.setText("주차위치찾기페이지 텍스트를 바꿨음 ");
//		
//		mTestBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//			}
//		});
		
//		//BitmapDescriptorFactory 생성하기 위한 소스
//		MapsInitializer.initialize(getActivity());
//				
//		GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
//		mGoogleMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map_fragment)).getMap();
//		
//		// GPS 맵이동
//        setGpsCurrent(mStrLat, mStrLng);
		
		return view;
	}
	
	private void setGpsCurrent(String strLat, String strLng) {
		 
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
            mGoogleMap.moveCamera(CameraUpdateFactory
                    .newLatLng(latLng));
 
            // Map 을 zoom 합니다.
            this.setZoomLevel(DEFAULT_ZOOM_LEVEL);
 
            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(latLng);// 위도 • 경도
            optFirst.title("Current Position");// 제목 미리보기
            optFirst.snippet("Snippet");
            optFirst.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_launcher));
            mGoogleMap.addMarker(optFirst).showInfoWindow();
//        }
    }
	
//	private void setSatellite() {
//        if (mSatellite.isChecked()) {
//            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//        } else {
//            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        }
//    };
 
    /**
     * 맵의 줌레벨을 조절합니다.
     * 
     * @param level
     */
    private void setZoomLevel(int level) {
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(level));
        Toast.makeText(getActivity(), "Zoom Level : " + String.valueOf(level),
                Toast.LENGTH_LONG).show();
    }
 
    /** Map 클릭시 터치 이벤트 */
    public void onMapClick(LatLng point) {
 
        // 현재 위도와 경도에서 화면 포인트를 알려준다
        Point screenPt = mGoogleMap.getProjection().toScreenLocation(
                point);
 
        // 현재 화면에 찍힌 포인트로 부터 위도와 경도를 알려준다.
        LatLng latLng = mGoogleMap.getProjection()
                .fromScreenLocation(screenPt);
 
        CarLLog.d("맵좌표", "좌표: 위도(" + String.valueOf(point.latitude)
                + "), 경도(" + String.valueOf(point.longitude) + ")");
        CarLLog.d("화면좌표", "화면좌표: X(" + String.valueOf(screenPt.x)
                + "), Y(" + String.valueOf(screenPt.y) + ")");
    }


}
