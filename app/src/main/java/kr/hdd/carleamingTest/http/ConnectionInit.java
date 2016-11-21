package kr.hdd.carleamingTest.http;

import java.util.HashMap;
import java.util.Map;



public class ConnectionInit {
	
	public ConnectionInit(){
		
	}
	
	public static Map<String, String> getHeaders() {
		//헤더 설정
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Connection", "Keep-Alive");
		headers.put("Accept-Charset", "UTF-8" );
		
		return headers;
	}
}
