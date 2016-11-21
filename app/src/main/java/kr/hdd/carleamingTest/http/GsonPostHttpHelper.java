package kr.hdd.carleamingTest.http;

import java.util.Map;

import kr.hdd.carleamingTest.util.CarLLog;
import android.content.Context;
import android.os.Handler;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class GsonPostHttpHelper<T> {
	
	public final static int RES_SUCCESS = 1;
	public final static int RES_FAIL = 0;
	
	private final String TAG = GsonPostHttpHelper.class.getSimpleName();
	private RequestQueue mVolleyRequestQueue = null;
	private Handler mHandler = null;

	public GsonPostHttpHelper(Context context, Handler handler){
		mVolleyRequestQueue = Volley.newRequestQueue(context);//, new ExtHttpClientStack(new DefaultHttpClient()));
		mHandler = handler;
	}
	
	public void startRequest(Map<String, String> params, String url, Class<T> clazz){
		GsonRequest<T> gsonRequest = 
				new GsonRequest<T>(Method.POST, url, clazz, 
						ConnectionInit.getHeaders(), params, new MyResponseListener(), new MyErrorListener());
		mVolleyRequestQueue.add(gsonRequest);
	}
	
	public void startRequest(String body, String url, Class<T> clazz){
		GsonRequest<T> gsonRequest = 
				new GsonRequest<T>(Method.POST, url, clazz, 
						ConnectionInit.getHeaders(), body, new MyResponseListener(), new MyErrorListener());
//		http://blog.lemberg.co.uk/volley-part-1-quickstart
		gsonRequest.setRetryPolicy(new DefaultRetryPolicy(15000, 
															25000, 
															30000));
		mVolleyRequestQueue.add(gsonRequest);
	}
	
	private class MyResponseListener implements Listener<T> {
		 
        @Override
        public void onResponse(T response) {
            try {
                //CarLLog.e(TAG, "response.getInjectMsg() : " + response.getInjectMsg());
            	mHandler.obtainMessage(RES_SUCCESS, response).sendToTarget();
            } catch (Exception e) {
                CarLLog.e(TAG, e.getLocalizedMessage());
            }
        }
    }
	
	private class MyErrorListener implements ErrorListener {
		 
        @Override
        public void onErrorResponse(VolleyError error) {
            //CarLLog.e(TAG, error.getCause() + "");
            if (error.getCause() != null) {
                CarLLog.e(TAG, error.getLocalizedMessage());
                mHandler.obtainMessage(RES_FAIL, error.getLocalizedMessage()).sendToTarget();
            }else{
            	mHandler.obtainMessage(RES_FAIL, error.getCause()).sendToTarget();
            }
            mVolleyRequestQueue.cancelAll(this);
        }
    }
}
