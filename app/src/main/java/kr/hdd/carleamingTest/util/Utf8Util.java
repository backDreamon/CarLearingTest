package kr.hdd.carleamingTest.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utf8Util {
	public static String convertUTF8(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
