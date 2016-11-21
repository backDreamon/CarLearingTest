package kr.hdd.carleamingTest.common.data;

import com.google.gson.annotations.SerializedName;

public class UserReviseData {
	@SerializedName("message")
	public String message;
	
	@SerializedName("result")
	public boolean result;
	
	@SerializedName("resDate")
	public String resData;
}
