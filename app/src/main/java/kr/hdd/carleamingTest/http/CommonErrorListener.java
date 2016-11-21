package kr.hdd.carleamingTest.http;

import kr.hdd.carleamingTest.util.CarLLog;
import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class CommonErrorListener implements Response.ErrorListener{

	private static final String TAG = CommonErrorListener.class.getSimpleName();
	private Context mContext;
	
	public CommonErrorListener(Context context) {
		mContext = context;
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		NetworkResponse res = error.networkResponse;
//		res.statusCode;
//		if(error != null && error.networkResponse != null) {
//			CarLLog.v(TAG, "error.networkResponse != null");
//		} else {
//			
//		}
		
		if(error.networkResponse == null){
			CarLLog.v(TAG,"res.null");
		}else {
			CarLLog.v(TAG,"res.statusCode : "+res.statusCode);
		}
		
//		Toast.makeText(mContext, mContext.getString(R.string.toast_not_server), Toast.LENGTH_LONG).show();
	}
}

//if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//    Toast.makeText(context,
//            context.getString(R.string.error_network_timeout),
//            Toast.LENGTH_LONG).show();
//} else if (error instanceof AuthFailureError) {
//    //TODO
//} else if (error instanceof ServerError) {
//   //TODO
//} else if (error instanceof NetworkError) {
//  //TODO
//} else if (error instanceof ParseError) {
//   //TODO
//}

//http://stackoverflow.com/questions/24700582/handle-volley-error
