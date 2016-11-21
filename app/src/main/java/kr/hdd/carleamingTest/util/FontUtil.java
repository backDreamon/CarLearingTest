package kr.hdd.carleamingTest.util;

import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputFilter;
import android.text.Spanned;

public class FontUtil {
	private static FontUtil	mInstance 	=	null;
	
	private String[] FONT_NAMES = {			
			"NanumBarunGothicBold.ttf",
	};

	private Typeface[] mTypeFace = new Typeface[FONT_NAMES.length];
	private Context mContext = null;
	
	private static final String DIR = "font/";

	public FontUtil(Context context){
		mContext =	context;
	}
	
	public static FontUtil getInstance(Context context){
		if(mInstance == null){
			mInstance = new FontUtil(context);
			mInstance.initFonts();
		}
		return mInstance;
	}
	
	public void initFonts(){
		
		int i = 0;
		for(String fontName : FONT_NAMES){
			mTypeFace[i] = Typeface.createFromAsset(mContext.getResources().getAssets(), DIR + fontName);
			i++;
		}
	}
	
	public Typeface getFontTypeface(String type){
		
		for(int i = 0; i < FONT_NAMES.length; i++){
			if(type.equals(FONT_NAMES[i])){
				return mTypeFace[i];
			}
		}
		return null;
	}
	
	/** 영문, 한글만 허용 **/
	 public InputFilter filterAlphaNumKor = new InputFilter() {
	  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//	   Pattern ps = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-흐ㄱ-ㅣ가-힣ᆢᆞ]+$"); //삼성키보드 천지인 . .. 처리
		  Pattern ps = Pattern.compile("^[a-zA-Zㄱ-ㅎ가-흐ㄱ-ㅣ가-힣ᆢᆞ]+$"); //삼성키보드 천지인 . .. 처리
	   if (!ps.matcher(source).matches()) {
	    return "";
	   }
	   return null;
	  }
	 };
	
}
