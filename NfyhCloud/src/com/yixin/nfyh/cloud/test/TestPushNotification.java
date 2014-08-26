package com.yixin.nfyh.cloud.test;

import android.content.Intent;

import com.yixin.nfyh.cloud.activity.SMSListActivity;
import com.yixin.nfyh.cloud.utils.NotificationManager;

public class TestPushNotification extends TestBase
{

	public void testNotify()
	{
		NotificationManager manager = NotificationManager.getManager(getContext());
		Intent intent = new Intent(getContext(), SMSListActivity.class);
		manager.notify("惠侨楼满意度调查", "您好，来帮助我们进行调查吧！", intent);
	}
}
