package cn.rui.framework.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.yixin.nfyh.cloud.R;

/**
 * CheckBox
 * 
 * @author ChenRui
 * 
 */
public class RuiSwitch extends ImageView {
	private static final int TEXT_SIZE = 12;
	private OnCheckedChangeListener mOnCheckedChangeListener;
	private boolean mChecked;
	private Paint mPaint;

	// private String mCheckedString, mUnCheckedString;

	public interface OnCheckedChangeListener {
		public void onCheckedChanged(RuiSwitch switchView, boolean isChecked);
	}

	public RuiSwitch(Context context) {
		super(context, null);
	}

	//
	// public RuiSwitch(Context context, AttributeSet attrs)
	// {
	// this(context, attrs, 0);
	// }

	public RuiSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		// mCheckedString = "ON";// 业务功能开启时的图片显示文字字符串：即ON
		// mUnCheckedString = "OFF";// OFF
		setChecked(false);// 初始化控件为关的状态
		configPaint();// 设置画布属性（去掉密度，直接以字体12显示）
	}

	private void configPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);// 去文字锯齿
		// mPaint.setTextSize(TEXT_SIZE * mDensity);
		mPaint.setTextSize(TEXT_SIZE);
		mPaint.setColor(Color.WHITE);// 画布背景即文字字体的颜色
	}

	public void setChecked(boolean checked) {

		if (checked) {
			setImageResource(R.drawable.switch_on);
		} else {
			setImageResource(R.drawable.switch_off);
		}
		if (mChecked != checked && mOnCheckedChangeListener != null) {
			mOnCheckedChangeListener.onCheckedChanged(this, checked);
		}
		mChecked = checked;
		postInvalidate();
	}

	public boolean isChecked() {
		return mChecked;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			setChecked(!mChecked);
			if (mOnCheckedChangeListener != null) {
				mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
			}
			break;
		default:
			// Do nothing
			break;
		}

		return true;
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		mOnCheckedChangeListener = listener;
	}

	@Override
	public boolean performClick() {
		setChecked(!mChecked);
		return this.isChecked();
	}
}
