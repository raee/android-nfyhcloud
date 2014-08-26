package com.yixin.nfyh.cloud.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmBrocastRecevice extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (!AlarmWakeupOpetation.ACTION.equals(action)) { return; }
		
		Bundle data = intent.getExtras();
		if (data != null && data.containsKey(Intent.EXTRA_TEXT))
		{
			intent.setClass(context, AlarmServer.class);
			context.startService(intent);
		}
	}
	
}
