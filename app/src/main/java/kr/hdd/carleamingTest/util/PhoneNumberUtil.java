package kr.hdd.carleamingTest.util;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneNumberUtil {
	
	public static String getPhoneNumber(Context context) {

		try {
			String number = null;

			TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			if(telManager.getLine1Number().startsWith("+82")) {
				number = "0" + telManager.getLine1Number().substring(3);
			} else {
				number = telManager.getLine1Number();
			}
			return Utf8Util.convertUTF8(number);
		} catch(NullPointerException e) {
//			Toast.makeText(context, context.getResources().getString(R.string.toast_no_phone_number), Toast.LENGTH_SHORT);
			return null;
		}
		
	}
}
