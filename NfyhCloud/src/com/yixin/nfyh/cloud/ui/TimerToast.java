package com.yixin.nfyh.cloud.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.rui.framework.ui.IToast;

import com.yixin.nfyh.cloud.R;

/**
 * 自定义的土司信息
 * 
 * @author ChenRui
 * 
 */
public class TimerToast extends Toast implements IToast {
	/**
	 * 警告
	 */
	public static final int TYPE_ALERT = 0;

	/**
	 * 成功
	 */
	public static final int TYPE_SUCCESS = 1;

	/**
	 * 失败
	 */
	public static final int TYPE_FIALID = 2;

	private TextView tvMsg;
	private View view;
	private ImageView img;
	private Context mContext;

	public static TimerToast makeText(Context context, CharSequence text,
			int duration) {
		TimerToast t = new TimerToast(context);
		t.setText(text);
		t.setDuration(duration);
		return t;
	}

	public static TimerToast makeText(Context context, int resID, int duration) {
		TimerToast t = new TimerToast(context);
		t.setText(resID);
		t.setDuration(duration);
		return t;
	}

	public static void show(Context context, CharSequence msg) {
		makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public TimerToast(Context context) {
		super(context);
		mContext = context;
		view = LayoutInflater.from(context).inflate(R.layout.ui_timer_toast,
				null);
		tvMsg = (TextView) view.findViewById(R.id.tvMsg);
		img = (ImageView) view.findViewById(R.id.img_toast_icon);
		super.setView(view);
		// super.setGravity(Gravity.FILL_HORIZONTAL | Gravity.CENTER, 0, 0);
	}

	@Override
	public void setText(CharSequence s) {
		tvMsg.setText(s);
	}

	@Override
	public void setText(int resId) {
		String msg = mContext.getResources().getString(resId);
		tvMsg.setText(msg);
	}

	public TimerToast setType(int type) {
		switch (type) {

		case TYPE_FIALID:
			img.setImageResource(R.drawable.icon_write_faild);
			break;
		case TYPE_SUCCESS:
			img.setImageResource(R.drawable.icon_write_success);
			break;
		case TYPE_ALERT:
		default:
			img.setImageResource(R.drawable.icon_write_alert);
			break;
		}
		return this;
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void show(String msg) {
		this.setText(msg);
		this.show();
	}

	@Override
	public void show(String msg, int code) {
		this.setText(msg);
		this.setType(code);
		this.show();
	}

}
