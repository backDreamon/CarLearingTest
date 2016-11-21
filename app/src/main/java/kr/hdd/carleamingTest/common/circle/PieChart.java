package kr.hdd.carleamingTest.common.circle;

import java.util.List;

import kr.hdd.carleamingTest.util.FontUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView.LayoutParams;

@SuppressLint("DrawAllocation")
public class PieChart extends View {

	public static final String TAG = PieChart.class.getSimpleName();
	
	public static final int WAIT = 0;
	public static final int IS_READY_TO_DRAW = 1;
	public static final int IS_DRAW = 2;
	private static final float START_INC = 30;

	private Paint bagPaints = new Paint();
	private Paint linePaints = new Paint();
	private Paint fontPaints = new Paint();
	private Paint bagCenterPaints = new Paint();
	private Paint Onepaint = new Paint();
	private Paint Textpaint1 = new Paint();
	private Paint Textpaint2 = new Paint();
	private Paint Textpaint3 = new Paint();
	private Paint Textpaint4 = new Paint();
	

	private int lineStrokeColor = 0x88FFFFFF;
	private int bagStrokeColor = 0x88FF0000;
	private float lineStrokeWidth = 3.0f;
	private float bagStrokeWidth = 0.0f;
	private boolean antiAlias = true;

	private int width;
	private int height;
	private int gapTop;
	private int gapBottm;
	private int backgroundColor;
	private int gapLeft;
	private int gapRight;
	private int state = WAIT;
	private float start;
	private float sweep;
	private int maxConnection;

	private List<PieDetailsItem> pieDetailsList;

	public PieChart(Context context) {
		super(context);
	}

	public PieChart(Context context, AttributeSet attr) {
		super(context, attr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (state != IS_READY_TO_DRAW) {
			return;
		}
		canvas.drawColor(backgroundColor);
		fontPaints.setAntiAlias(antiAlias);
		fontPaints.setStyle(Paint.Style.FILL);
		fontPaints.setColor(Color.WHITE);
		fontPaints.setTextSize(50);
		fontPaints.setTypeface(Typeface.create(FontUtil.getInstance(getContext()).getFontTypeface("NanumBarunGothicBold.ttf"), Typeface.BOLD));
		
		bagPaints.setAntiAlias(antiAlias);
		bagPaints.setStyle(Paint.Style.FILL);
		bagPaints.setColor(bagStrokeColor);
		bagPaints.setStrokeWidth(bagStrokeWidth);
		
		linePaints.setAntiAlias(antiAlias);
		linePaints.setColor(lineStrokeColor);
		linePaints.setStrokeWidth(lineStrokeWidth);
		linePaints.setStyle(Paint.Style.STROKE);
		
		Onepaint.setAntiAlias(antiAlias);
		Onepaint.setStyle(Paint.Style.FILL);
		Onepaint.setColor(Color.BLACK);
		Onepaint.setStrokeWidth(bagStrokeWidth);
		
		//% 나타내는 텍스트
		Textpaint1.setAntiAlias(antiAlias);
		Textpaint1.setStyle(Paint.Style.FILL);
		Textpaint1.setTextSize(30);
		
		Textpaint2.setAntiAlias(antiAlias);
		Textpaint2.setStyle(Paint.Style.FILL);
		Textpaint2.setTextSize(30);
		
		Textpaint3.setAntiAlias(antiAlias);
		Textpaint3.setStyle(Paint.Style.FILL);
		Textpaint3.setTextSize(30);
		
		Textpaint4.setAntiAlias(antiAlias);
		Textpaint4.setStyle(Paint.Style.FILL);
		Textpaint4.setTextSize(30);
		
		RectF mOvals = new RectF(gapLeft, gapTop, width - gapRight, height - gapBottm);
		RectF rect = new RectF();
		start = START_INC;
		PieDetailsItem item;
		
		int centerX = (int) ((rect.left + rect.right) / 2);
        int centerY = (int) ((rect.top + rect.bottom) / 2);
        int radius = (int) ((rect.right - rect.left) / 2);

        radius *= 0.5;
		
		rect.set(width/2- radius, height/2 - radius, width/2 + radius, height/2 + radius); 
		bagCenterPaints.setColor(Color.GREEN);
		bagCenterPaints.setStrokeWidth(30);
	    bagCenterPaints.setAntiAlias(true);
	    bagCenterPaints.setStrokeCap(Paint.Cap.ROUND);
	    bagCenterPaints.setStyle(Paint.Style.STROKE);
	    
	    
		for (int i = 0; i < pieDetailsList.size(); i++) {
			item = pieDetailsList.get(i);
			bagPaints.setColor(item.color);
			
			sweep = (float) 360 * ((float) item.count / (float) maxConnection);
			canvas.drawArc(mOvals, start, sweep, true, bagPaints);
			canvas.drawArc(mOvals, start, sweep, true, linePaints);
//			canvas.drawArc(mOvals, start, sweep, bagCenterPaints);/*rect, start, sweep, true, bagCenterPaints);*/
			
//			CarLLog.v(TAG,"start : "+ start);
//			CarLLog.v(TAG,"start sweep : "+ sweep);
			
//			if(i == 0){
//				
//				if(!pieDetailsList.get(i).label.equals("1%")){
//					canvas.drawText(pieDetailsList.get(i).label, start+55/*170*/, sweep+300/*200*/, fontPaints);
////				} else{
////					CarLLog.v(TAG,"0 item : "+pieDetailsList.get(i).label);
////					canvas.drawText("25%", 260, 540, fontPaints);
//				}
//				//텍스트
//				canvas.drawText("11 "+pieDetailsList.get(0).label.toString(), (float) (start) , (float) (sweep + 10.0), Textpaint1);
//			} 
//			if(i == 1){
//				if(!pieDetailsList.get(i).label.equals("1%")){
//					canvas.drawText(pieDetailsList.get(i).label, start+20, sweep-70, fontPaints);
////				} else {
////					CarLLog.v(TAG,"1 item : "+pieDetailsList.get(i).label);
////					canvas.drawText("25%", 40/*170*/, 330/*200*/, fontPaints);
//				}
//				canvas.drawText("22 "+pieDetailsList.get(1).label.toString(), (float) (start) , (float) (sweep + 20.0), Textpaint2);
//			} 
//			if(i == 2){
//				if(!pieDetailsList.get(i).label.equals("1%")){
//					canvas.drawText(pieDetailsList.get(i).label, start+180/*200*/, sweep+300/*400*/, fontPaints);
////				} else{
////					canvas.drawText("25%", 220/*170*/, 100/*200*/, fontPaints);
//				}
//				CarLLog.v(TAG,"2 item : "+pieDetailsList.get(i).label);
//				canvas.drawText("33 "+pieDetailsList.get(2).label.toString(), (float) (start) , (float) (sweep + 30.0), Textpaint3);
//		
//			} 
//			if(i == 3){
//				if(!pieDetailsList.get(i).label.equals("1%")){
//					canvas.drawText(pieDetailsList.get(i).label, start+120, sweep+420, fontPaints);
////				} else{
////					canvas.drawText("25%", 500, 280, fontPaints);
//				}
//				CarLLog.v(TAG,"3 item : "+pieDetailsList.get(i).label);
//				canvas.drawText("44 "+pieDetailsList.get(3).label.toString(), (float) (start) , (float) (sweep + 40.0), Textpaint4);
//			}
			
			start = start + sweep;
			
//			//채워진 원 그리기
			canvas.drawCircle(300, 300, 150, Onepaint);
		}
		
//		int x, y;
//		for(int j=0; j<pieDetailsList.size(); j++){
//			if(j <= 0){
//				canvas.drawText(pieDetailsList.get(j).label, 200/*170*/, 400/*200*/, fontPaints);
//				CarLLog.v(TAG,"0 item : "+pieDetailsList.get(j).label);
//			} 
//			if(j == 1){
//				canvas.drawText(pieDetailsList.get(j).label, 400, 200, fontPaints);
//				CarLLog.v(TAG,"1 item : "+pieDetailsList.get(j).label);
//			} 
//			if(j == 2){
//				canvas.drawText(pieDetailsList.get(j).label, 170/*200*/, 200/*400*/, fontPaints);
//				CarLLog.v(TAG,"2 item : "+pieDetailsList.get(j).label);
//			} 
//			if(j == 3){
//				canvas.drawText(pieDetailsList.get(j).label, 410, 350, fontPaints);
//				CarLLog.v(TAG,"3 item : "+pieDetailsList.get(j).label);
//			}
//		}

		state = IS_DRAW;
	}

	public void setGeometry(int width, int height, int gapleft, int gapright,
			int gaptop, int gapbottom, int overlayid) {

		this.width = width;
		this.height = height;
		this.gapLeft = gapleft;
		this.gapRight = gapright;
		this.gapBottm = gapbottom;
		this.gapTop = gaptop;

	}

	public void setSkinparams(int bgcolor) {
		backgroundColor = bgcolor;
	}

	public void setData(List<PieDetailsItem> data, int maxconnection) {
		pieDetailsList = data;
		maxConnection = maxconnection;
		state = IS_READY_TO_DRAW;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getColorValues(int index) {
		if (pieDetailsList == null) {
			return 0;
		}

		else if (index < 0)
			return pieDetailsList.get(0).color;
		else if (index > pieDetailsList.size())
			return pieDetailsList
					.get(pieDetailsList.size() - 1).color;
		else
			return pieDetailsList
					.get(pieDetailsList.size() - 1).color;

	}

	@SuppressLint("InlinedApi")
	public void expand() {
		final View v = this;
		v.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1 ? LayoutParams.WRAP_CONTENT
						: (int) (targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration((int) (targtetHeight * 3 / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public void collapse() {
		final View v = this;
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		a.setDuration((int) (initialHeight * 3 / v.getContext().getResources()
				.getDisplayMetrics().density));
		v.startAnimation(a);
	}

	/**
	 * @return the antiAlias
	 */
	public boolean isAntiAlias() {
		return antiAlias;
	}

	/**
	 * @param antiAlias
	 *            the antiAlias to set
	 */
	public void setAntiAlias(boolean antiAlias) {
		this.antiAlias = antiAlias;
	}

	/**
	 * @return the lineStrokeColor
	 */
	public int getLineStrokeColor() {
		return lineStrokeColor;
	}

	/**
	 * @param lineStrokeColor
	 *            the lineStrokeColor to set
	 */
	public void setLineStrokeColor(int lineStrokeColor) {
		this.lineStrokeColor = lineStrokeColor;
	}

	/**
	 * @return the bagStrokeColor
	 */
	public int getBagStrokeColor() {
		return bagStrokeColor;
	}

	/**
	 * @param bagStrokeColor
	 *            the bagStrokeColor to set
	 */
	public void setBagStrokeColor(int bagStrokeColor) {
		this.bagStrokeColor = bagStrokeColor;
	}

	/**
	 * @return the lineStrokeWidth
	 */
	public float getLineStrokeWidth() {
		return lineStrokeWidth;
	}

	/**
	 * @param lineStrokeWidth
	 *            the lineStrokeWidth to set
	 */
	public void setLineStrokeWidth(float lineStrokeWidth) {
		this.lineStrokeWidth = lineStrokeWidth;
	}

	/**
	 * @return the bagStrokeWidth
	 */
	public float getBagStrokeWidth() {
		return bagStrokeWidth;
	}

	/**
	 * @param bagStrokeWidth
	 *            the bagStrokeWidth to set
	 */
	public void setBagStrokeWidth(float bagStrokeWidth) {
		this.bagStrokeWidth = bagStrokeWidth;
	}

	/**
	 * @return the backgroundColor
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
