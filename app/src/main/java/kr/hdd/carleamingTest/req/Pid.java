package kr.hdd.carleamingTest.req;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Pid {
//	@SerializedName("userId")
//	private String m_UserId = null;
	@SerializedName("pidDatas")
	private List<PidData> m_PidData = null;
//	private PidData[] m_PidData = null;
	
//	public void setUserId(String userId){
//		m_UserId = userId;
//	}
//	public String getUserId(){
//		return m_UserId;
//	}
	
//	public void setPidData(PidData[] data){
	public void setPidData(List<PidData> data){
		m_PidData = data;
	}
//	public PidData[] getPidData(){
	public List<PidData> getPidData(){
		return m_PidData;
	}
}
