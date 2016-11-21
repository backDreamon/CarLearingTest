package kr.hdd.carleamingTest.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class GsonRequest<T> extends Request<T> {
	
	private Gson mGson = new GsonBuilder().setPrettyPrinting().create();
    private Class<T> mClazz = null;
    private Map<String, String> mHeaders = null, mParams = null;
    private Listener<T> mListener = null;
    private String mBody = null;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
            Listener<T> listener, ErrorListener errorListener) {
        super(method/*Method.GET*/, url, errorListener);
        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mParams = params;
        this.mListener = listener;
    }
    
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, String body,
            Listener<T> listener, ErrorListener errorListener) {
        super(method/*Method.GET*/, url, errorListener);
        this.mClazz = clazz;
        this.mHeaders = headers;
        this.mBody = body;
        this.mListener = listener;
    }
 
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
    	if(mBody != null){
    		mHeaders.put("Content-Type", "application/json");
    	}else{
    	}
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    @Override
	public void setRetryPolicy(RetryPolicy retryPolicy) {
		super.setRetryPolicy(retryPolicy);
		
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams;
	}
    
	@Override
	public String getBodyContentType() {
		if(mBody != null){
			return "application/json; charset=utf-8";
		}else{
			return super.getBodyContentType();
		}
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		// TODO Auto-generated method stub
		if(mBody== null) mBody= "";
		return mBody != null ? mBody.getBytes() : super.getBody();
	}

	@Override
    protected void deliverResponse(T response) {
    	mListener.onResponse(response);
    }
 
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
            		mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
