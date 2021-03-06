package com.yixin.nfyh.cloud.bll;

import android.graphics.PixelFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;

/**
 * 悬浮框View
 * 
 * @author ChenRui
 * 
 */
public class DragView implements OnTouchListener {
	private View mView;
	private WindowManager mWin;
	private int mStartX, mStartY, mParamX, mParamY;
	private WindowManager.LayoutParams params;
	private View.OnClickListener mOnClickListener;
	private OnTouchListener mTouchListener;

	public DragView(WindowManager win, View view) {
		this.mWin = win;
		this.mView = view;
		init();

	}

	public void drag() {
		this.mView.setOnTouchListener(this);
	}

	/**
	 * 初始化，显示到窗口上。
	 */
	private void init() {
		try {
			this.mWin.removeView(this.mView);
		} catch (Exception e) {
			e.printStackTrace();
			params = new WindowManager.LayoutParams();
			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
			params.format = PixelFormat.RGBA_8888;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
					| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			params.width = LayoutParams.WRAP_CONTENT;
			params.height = LayoutParams.WRAP_CONTENT;
			params.x = 300;
			params.y = 100;

			this.mWin.addView(this.mView, params);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_MOVE:
			this.touchMove(event);
			break;
		case MotionEvent.ACTION_DOWN:
			this.touchDown(event);
			break;
		case MotionEvent.ACTION_UP:
			if (Math.abs(event.getRawX() - this.mStartX) < 6
					&& Math.abs(event.getRawY() - this.mStartY) < 6
					&& this.mOnClickListener != null) {
				this.mOnClickListener.onClick(mView); // 触发单击事件
			}
		default:
			break;
		}

		if (mTouchListener != null) {
			mTouchListener.onTouch(mView, event);
		}

		return true;
	}

	void show(Object msg) {
		Log.v("cr", msg.toString());
	}

	/**
	 * 按下事件
	 * 
	 * @param event
	 */
	private void touchDown(MotionEvent event) {
		this.mStartX = (int) event.getRawX();
		this.mStartY = (int) event.getRawY();
		this.mParamX = params.x;
		this.mParamY = params.y;
		Log.v("cr", "view_down");
	}

	/**
	 * 移动事件
	 * 
	 * @param event
	 */
	private void touchMove(MotionEvent event) {
		Log.v("cr", "view_move");
		int curX = (int) (event.getRawX() - this.mStartX);
		int curY = (int) (event.getRawY() - this.mStartY);

		params.x = curX + this.mParamX;
		params.y = curY + this.mParamY;

		this.mWin.updateViewLayout(this.mView, params);

	}

	/**
	 * 设置单击监听
	 * 
	 * @param l
	 * 
	 */
	public void setOnClickListener(View.OnClickListener l) {
		this.mOnClickListener = l;
	}

	public void setOnTouchListener(OnTouchListener l) {
		mTouchListener = l;
	}

	/**
	 * 移除悬浮框
	 */
	public void remove() {
		this.mWin.removeView(this.mView);
	}
}
