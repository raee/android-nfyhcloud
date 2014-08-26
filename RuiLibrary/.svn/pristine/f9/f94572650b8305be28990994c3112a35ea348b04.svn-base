package com.yixin.nfyh.cloud.server;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;

/**
 * 跌倒服务
 * 
 * @author MrChenrui
 * 
 */
public class FallServer
{

	private Context				context;
	private String				ACTION_SEND	= "FALL_ACTION_SEND";
	private String				ACTION_BACK	= "FALL_ACTION_BACK";
	private BroadcastReceiver	receiver;
	private IFallCallback		listener;

	public FallServer(Context context, IFallCallback l)
	{
		this.context = context;
		this.listener = l;
	}

	/**
	 * 注销广播绑定
	 */
	public void unReceive()
	{
		context.unregisterReceiver(receiver);
		receiver = new SmsSendBroadcastReceiver();
		IntentFilter filter = new IntentFilter(ACTION_SEND);
		filter.addAction(ACTION_BACK);
		context.registerReceiver(receiver, filter);
	}

	/**
	 * 绑定设备
	 */
	public void bind(String number)
	{
		SmsManager sms = SmsManager.getDefault();
		String msg = "BD";

		// 发送状态
		PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
				new Intent(ACTION_SEND), 0);

		// 返回状态
		PendingIntent deliveryIntent = PendingIntent.getBroadcast(context, 0,
				new Intent(ACTION_BACK), 0);

		sms.sendTextMessage(number, null, msg, sentIntent, deliveryIntent);

	}

	private class SmsSendBroadcastReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			int code = getResultCode();

			// String action = intent.getAction();

			if (code == Activity.RESULT_OK)
			{
				listener.onBindSuccess("绑定信息发送成功");
			}
			else
			{
				listener.onBindError("发送信息失败");
			}
		}

	}
}
