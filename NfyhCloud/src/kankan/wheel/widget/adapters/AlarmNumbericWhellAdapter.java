package kankan.wheel.widget.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class AlarmNumbericWhellAdapter extends NumericWheelAdapter {
	
	// TODO:高亮当前选择行
	
	public AlarmNumbericWhellAdapter(Context context) {
		super(context);
	}
	
	public AlarmNumbericWhellAdapter(Context context, int minValue, int maxValue, String format) {
		super(context, minValue, maxValue, format);
	}
	
	public AlarmNumbericWhellAdapter(Context context, int minValue, int maxValue) {
		super(context, minValue, maxValue);
	}
	
	@Override
	protected void configureTextView(TextView view) {
		super.configureTextView(view);
		int padding = 20;
		view.setPadding(0, padding, 0, padding);
		view.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
		//TODO：高亮当前选择
	}
	

	public void setMinValue(int minValue) {
		super.minValue = minValue;
	}
	
	public void setMaxValue(int maxValue){
		super.maxValue = maxValue;
	}
}
