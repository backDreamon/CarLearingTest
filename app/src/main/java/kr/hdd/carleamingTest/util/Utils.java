package kr.hdd.carleamingTest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

import kr.hdd.carleamingTest.MainApplication;
import android.app.Activity;
import android.util.DisplayMetrics;

public class Utils {

	public static String Date(){
		Date date = new Date();
	    SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMddhhmmss");
	    return SimpleDate.format(date);
//	    return SimpleDate.format(date);
	}
	
	public static String JunmDate(){
		Date date = new Date();
	    SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyy.MM.dd");
	    return SimpleDate.format(date);
//	    return SimpleDate.format(date);
	}
	
	public static String DayDate(){
		Date date = new Date();
	    SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMdd");
	    return SimpleDate.format(date);
//	    return SimpleDate.format(date);
	}
	
	public static String MonthDate(){
		Date date = new Date();
	    SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMM");
	    return SimpleDate.format(date);
//	    return SimpleDate.format(date);
	}
	
	public static String YearDate(){
		Date date = new Date();
	    SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyy");
	    return SimpleDate.format(date);
//	    return SimpleDate.format(date);
	}
	
	public static String NotJunmDayDate(String str){
		String strValue  = null;
		strValue+= str.replace(".", "");
		
		if(strValue.equalsIgnoreCase("null")){
			String notnullstrValue = strValue.replace("null", "");
			return notnullstrValue;
		}
		
		return strValue;
	}
	
	public static String getMonthAgoDate() {
	     Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST"));
	     cal.add(Calendar.MONTH ,-1); // 한달전 날짜 가져오기
	        java.util.Date monthago = cal.getTime();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
	        return formatter.format(monthago);
    }
	
	public static String getFomatNotJunmMonthAgoDate() {
	     Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST"));
	     cal.add(Calendar.MONTH ,-1); // 한달전 날짜 가져오기
	        java.util.Date monthago = cal.getTime();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
	        String format = formatter.format(monthago);
	        return format.replace("null", "");
   }
	
	public static String getMonthFirstAgoDate() {
		Calendar calendar = Calendar.getInstance();

		String today = "";
		
		today += calendar.get(Calendar.YEAR);

		int month = calendar.get(Calendar.MONTH)+1;

		int day = calendar.get(Calendar.DAY_OF_MONTH);

		today += month < 10 ? "0"+month : month;

		today += day < 10 ? "0"+day : day;
		
        int monthFirst = calendar.getActualMinimum(Calendar.DATE);
        return MonthDate() + "0" + String.valueOf(monthFirst);
	}
	
	public static String get6MonthDate() {
	     Calendar cal = Calendar.getInstance(new SimpleTimeZone(0x1ee6280, "KST"));
	     cal.add(Calendar.MONTH ,+6); // 한달전 날짜 가져오기
	        java.util.Date monthago = cal.getTime();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
	        return formatter.format(monthago);
   }
	
	public static String JunmFomatDate(int num){
		String date = String.valueOf(num);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return formatter.format(date);
	}
	
	public static String StrDayJunmDate(int num){
		String date = String.valueOf(num);
		
		if(date.length() > 0){
			date = date.substring(0, 4)
				+ "." + date.subSequence(4, 6)
				+ "." + date.substring(6, date.length());
		}
		
		return date;
	}
	
	public static String StrDayJunmDate(String num){
		String date = num;
		
		if(date.length() > 0){
			date = date.substring(0, 4)
				+ "." + date.subSequence(4, 6)
				+ "." + date.substring(6, 8);
		}
		
		return date;
	}
	
	public static String CutStr(int num){
		String str = String.valueOf(num);
		if(str.length() < 2){
			return "0" + String.valueOf(num);
		}else {
			return String.valueOf(num);
		}
	}
	
	public static String CutStr(double num){
		String str = String.valueOf(num);
		if(str.length() < 2){
			return "0" + String.valueOf(num);
		}else {
			return String.valueOf(num);
		}
	}

	public static int getPrice(String type){
		if(type.equalsIgnoreCase(MainApplication.OIL_GASOL)){
			return 1500;
		}else {
			return 1300;
		}
	}

	public static int getDeviceDisplay(Activity act, boolean isWidth){
		DisplayMetrics outMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);		
		
		int wpixel= outMetrics.widthPixels;
		int hpixel= outMetrics.heightPixels;
		
		if(isWidth){
			return wpixel;
		}else {
			return hpixel;
		}
	}
	

}
