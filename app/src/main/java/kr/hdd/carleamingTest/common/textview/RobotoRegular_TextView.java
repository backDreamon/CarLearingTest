package kr.hdd.carleamingTest.common.textview;

import android.content.Context;
import android.util.AttributeSet;

public class RobotoRegular_TextView extends BaseTextView {

	public RobotoRegular_TextView(Context context) {
        super(context);
        setCustomFont(context, null);
    }

    public RobotoRegular_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public RobotoRegular_TextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    public void setCustomFont(Context context, AttributeSet attrs) {
//    	Typeface tf = FontUtil.getInstance(context).getFontTypeface(context.getResources().getString(R.string.Roboto_Regular));
//		if(tf!=null)setTypeface(tf);
    }
}
