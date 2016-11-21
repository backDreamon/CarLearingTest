package kr.hdd.carleamingTest.common.textview;

import kr.hdd.carleamingTest.util.FontUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class NanumBarunGothicBold_TextView extends TextView{
	
	public NanumBarunGothicBold_TextView(Context context) {
		super(context);
		setTypeface(FontUtil.getInstance(context).getFontTypeface("NanumBarunGothicBold.ttf"));
	}
	
	public NanumBarunGothicBold_TextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface(FontUtil.getInstance(context).getFontTypeface("NanumBarunGothicBold.ttf"));
	}

	public NanumBarunGothicBold_TextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		setTypeface(FontUtil.getInstance(context).getFontTypeface("NanumBarunGothicBold.ttf"));
	}
	
	

}
