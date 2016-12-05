package kr.hdd.carleamingTest.http;


public class UrlConstants {
	
	public static final String SERVER = "http://27.102.198.45:8080/smartcar";//"http://192.168.0.11:8080";//"http://211.58.11.2:8088";

	public static final String URL_COMMON = "/generic/execute";// ?entityAlias=userinfo&method=";

	//사용자 등록
	public static final String USER_REGISTRATION = "insert&userPhoneBTMac=";

	//사용자 정보 수정
	public static final String USER_INFO_REVISE = "update&userId=";
	
	//사용자 등록 여부 
	public static final String USER_CHECK = "findOne&userPhoneBTMac=";
	
	//OBD 데이터 등록 
	public static final String OBD_DATA = "/obddata";
	
	/**
	 * 사용자 등록
	 * ex) http://192.168.0.11:8080/generic/execute?entityAlias=userinfo&method=insert&userPhoneBTMac=[사용자휴대전화BT mac 주소 : 필수]&telephone=[사용자휴대전화번호 : 옵션]
	 * */
	public static String getUserRegistration(String macAddress, String phoneNumber){
		return SERVER + URL_COMMON; // + USER_REGISTRATION + Utf8Util.convertUTF8(macAddress) + "&telephone="+Utf8Util.convertUTF8(phoneNumber);
	}
	
	/**
	 * 사용자 정보수정 
	 * ex) /generic/execute?entityAlias=userinfo&method=update&userId=[사용자 아이디 : 필수]&userPhoneBTMac=[사용자휴대전화BT mac 주소 : 필수]&userOBDBTMac=[사용자OBD BT mac 주소 : 필수]&telephone=[사용자휴대전화번호 : 필수]
	 * */
	public static String getUserRevise(String userid, String macAddress, String obdAddress, String phoneNumber){
		return SERVER + URL_COMMON; //+ USER_INFO_REVISE + Utf8Util.convertUTF8(userid) + "&userPhoneBTMac=" + Utf8Util.convertUTF8(macAddress) + "&userOBDBTMac=" + Utf8Util.convertUTF8(obdAddress) + "&telephone=" + Utf8Util.convertUTF8(phoneNumber);
	}
	
	/**
	 * 사용자 등록 여부 (조회)
	 * ex)/generic/execute?entityAlias=userinfo&method=findOne&userPhoneBTMac=[사용자휴대전화BT mac 주소 : 필수]
	 * */
	public static String getUserCheck(/*String macAddress*/){
		return SERVER + URL_COMMON;// + USER_CHECK;// + Utf8Util.convertUTF8(macAddress);
	}
	
	/**
	 * OBD 데이터 입력
	 * ex) /obddata?
	 * */
	public static String getObdData(){
		return SERVER + OBD_DATA;//"userId=" + Utf8Util.convertUTF8(userid) +"&date=" + Utf8Util.convertUTF8(date) + "&pidData=41001245,41011247";
	}
}
