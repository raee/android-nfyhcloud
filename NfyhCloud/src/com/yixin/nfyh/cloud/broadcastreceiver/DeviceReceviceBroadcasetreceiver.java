package com.yixin.nfyh.cloud.broadcastreceiver;

import java.io.IOException;

import com.yixin.nfyh.cloud.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import cn.rui.framework.utils.CommonUtil;
import cn.rui.framework.utils.MediaUtil;

import com.yixin.nfyh.cloud.service.CoreServerBinder;
import com.yixin.nfyh.cloud.ui.TopMsgView;

/**
 * 蓝牙设备连接广播
 * 
 * @author MrChenrui
 * 
 */
public class DeviceReceviceBroadcasetreceiver extends BroadcastReceiver
{
	private TopMsgView			mMsgView;
	
	private ViewGroup			mContentView;
	
	private View				mActionView;
	
	private Context				mContext;
	
	private static MediaPlayer	player;
	
	public DeviceReceviceBroadcasetreceiver(Context context, View contentView,
			View actionView)
	{
		this.mContext = context;
		mMsgView = new TopMsgView(context, null);
		mContentView = (ViewGroup) contentView;
	}
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		String msg = intent.getStringExtra(Intent.EXTRA_TEXT);
		if (action != null)
		{
			changeState(action, msg); //改变状态
		}
	}
	
	private void showMsg(String msg, int iconId, int color)
	{
		if (mContentView == null || msg == null || msg.equals("")) { return; }
		if (iconId != 0)
		{
			mMsgView.setIcon(iconId);
		}
		if (color != 0)
		{
			color = mContext.getResources().getColor(color);
			mMsgView.setBackgroundColor(color);
		}
		mMsgView.anim();
		mMsgView.setMsg(msg);
		mMsgView.show(mContentView);
	}
	
	private void unShowMsg()
	{
		MediaUtil.stopPlayMusic(player);
		mMsgView.stopAnim();
		mMsgView.setVisibility(View.GONE);
	}
	
	/**
	 * 改变状态
	 * 
	 * @param action
	 * @param msg
	 */
	private void changeState(String action, String msg)
	{
		
		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTED.equals(action)) // 已经连接
		{
			showMsg(msg, R.drawable.ic_connected, R.color.green);
			CommonUtil.setActionViewItemIcon(mActionView,
					R.drawable.ic_switch_device_connected);
		}
		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_CONNECTING.equals(action)) // 正在连接
		{
			showMsg(msg, R.drawable.ic_bluetooth, R.color.green);
		}
		if (action
				.equals(CoreServerBinder.ACTION_BLUETOOTH_DEVICE_DISCONNECTED)) // 断开连接
		{
			showMsg(msg, R.drawable.ic_diable_connect, R.color.device_yellow);
			CommonUtil.setActionViewItemIcon(mActionView,
					R.drawable.ic_menu_switch_device);
		}
		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_FAILD.equals(action)) // 连接失败
		{
			showMsg(msg, R.drawable.ic_diable_connect, R.color.device_red);
			CommonUtil.setActionViewItemIcon(mActionView,
					R.drawable.ic_menu_switch_device);
		}
		
		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICED.equals(action)) // 接收到数据
		{
			unShowMsg();
		}
		if (CoreServerBinder.ACTION_BLUETOOTH_DEVICE_RECEVICEING.equals(action))// 接收数据中
		{
			playMusic();
			showMsg(msg, R.drawable.ic_bluetooth, 0);
		}
		
	}
	
	private void playMusic()
	{
		if (player == null)
		{
			player = MediaUtil.playMusic(mContext, R.raw.ring);
		}
		else
		{
			if (!player.isPlaying())
			{
				try
				{
					player.prepare();
					player.start();
				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			
		}
	}
}
