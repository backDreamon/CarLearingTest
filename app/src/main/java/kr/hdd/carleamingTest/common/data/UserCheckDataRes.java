package kr.hdd.carleamingTest.common.data;

import com.google.gson.annotations.SerializedName;

public class UserCheckDataRes {
	@SerializedName("one")
	public UserCheckDataItem mItem = null;
	
	@SerializedName("result")
	public boolean result = false;
	
	@SerializedName("resDate")
	public String m_ResDate = null;
	
	@SerializedName("pk")
	public String mUserId = null;
}
