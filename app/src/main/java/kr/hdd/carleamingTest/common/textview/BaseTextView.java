package kr.hdd.carleamingTest.common.textview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BaseTextView extends TextView{

	public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontType(attrs);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFontType(attrs);
    }
    
    public void setFontType(AttributeSet attrs){
    	
		if(attrs != null) {
			String textStyle = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "textStyle");
	    	if(textStyle != null && (textStyle.equals("0x1") || textStyle.equals("0x3")))
	    		setPaintFlags(getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
		}
    }
    
	
}
