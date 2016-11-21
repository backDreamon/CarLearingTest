package kr.hdd.carleamingTest.common.data;

import com.google.gson.annotations.SerializedName;

public class UserCheckDataItem {
	@SerializedName("result")
	public boolean result;
	
	@SerializedName("userId")
	public String userId;
	
	@SerializedName("userPhoneBTMac")
	public String userPhoneBTMac;
	
	@SerializedName("userOBDBTMac")
	public String userOBDBTMac;
	
	@SerializedName("telephone")
	public String telephone;
	
	@SerializedName("alias")
	public String alias;
	
	@SerializedName("pk")
	public String pk;
}
