package com.yixin.nfyh.cloud.bll;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yixin.nfyh.cloud.OneKeySoSActivity;
import com.yixin.nfyh.cloud.R;

/**
 * 桌面紧急呼救
 * 
 * @author 睿
 * 
 */
public class DesktopSOS
{
	private Context			mContext;
	private WindowManager	mWm;
	private View			mFloatView;

	public void remove()
	{
		try
		{
			if (mFloatView != null)
			{
				mWm.removeView(mFloatView);
				mFloatView = null;
			}
		}
		catch (Exception e)
		{
			Log.e("cr", "删除桌面悬浮框错误！");
			e.printStackTrace();
		}
		finally
		{
		}
	}

	public DesktopSOS(Context context)
	{

		this.mContext = context;

		// 窗体管理
		mWm = (WindowManager) this.mContext
				.getSystemService(Context.WINDOW_SERVICE);
	}

	/**
	 * 初始化浮动框
	 */
	public void initFloatView()
	{
		Log.i("test", "添加浮动框");

		// 浮动框布局
		mFloatView = LayoutInflater.from(this.mContext).inflate(
				R.layout.view_layout_float, null);
		mFloatView.setClickable(true);
		mFloatView.setTag("KEY_FLOAT_ADD");

		DragView drag = new DragView(mWm, mFloatView);
		drag.drag();

		// 点击事件
		drag.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(mContext, OneKeySoSActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra(OneKeySoSActivity.EXTRA_EVENT_TYPE,
						OneKeySoSActivity.EXTRA_EVENT_DATA_AUTO); // 自动呼救
				mContext.startActivity(intent); // 启动一键急救

				// // 发出广播通知启动一键急救
				//
				// Intent intent = new Intent(
				// BroadcastReceiverFlag.ACTION_DESKTOP_FLOAT);
				// mContext.sendBroadcast(intent);
			}
		});

		// 移动事件
		drag.setOnTouchListener(new View.OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				ImageView img = (ImageView) v
						.findViewById(R.id.windows_float_img);
				img.setBackgroundResource(R.drawable.car);
				Log.v("xr", event.getAction() + "");

				if (event.getAction() == MotionEvent.ACTION_UP)
				{
					img.setBackgroundResource(R.drawable.onekey);
				}

				return false;
			}
		});
	}
}
