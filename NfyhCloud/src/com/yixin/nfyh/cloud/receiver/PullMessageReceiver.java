package com.yixin.nfyh.cloud.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.i.IPushMessageCallback;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.utils.NotificationManager;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;

/**
 * 消息推送闹钟广播接收者。 当设置的闹钟时间到达时，开始向服务请求是否有新的消息。
 * 
 * @author ChenRui
 * 
 */
public class PullMessageReceiver extends BroadcastReceiver implements IPushMessageCallback {
	
	private NotificationManager	mNotificationManager;
	private IPushMessage		mPushMessage;
	private Context				mContext;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.mContext = context;
		NfyhApplication application = (NfyhApplication) context.getApplicationContext();
		if (!application.isLogin() || !"com.yixin.nfyh.cloud.action.pullmessage".equals(intent.getAction())) { return; }
		if (mPushMessage == null) {
			mPushMessage = NfyhWebserviceFactory.getFactory(context).getPushMessage();
			mPushMessage.setPushMessageListener(this);
		}
		
		mPushMessage.setCookie(application.getCurrentUser().getCookie());
		mPushMessage.check();
	}
	
	@Override
	public void onPushNewMessage(int count, int index, Messages model, Intent intent) {
		if (mContext == null) { return; }
		if (mNotificationManager == null) {
			mNotificationManager = NotificationManager.getManager(mContext);
		}
		// 发出通知
		mNotificationManager.notify(model.getTitle(), model.getSummary(), intent);
	}
	
}
