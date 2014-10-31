package com.yixin.nfyh.cloud.receiver;

import com.yixin.nfyh.cloud.BroadcastReceiverFlag;
import com.yixin.nfyh.cloud.OneKeySoSActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * 跌倒信息接受者
 * 
 * 
 * @author Chenrui
 * 
 */
public class CoreBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent == null || !BroadcastReceiverFlag.ACTION_REC_SMS.equals(intent.getAction())) { return; }
		Log.v("cr", "接受跌倒报警信息");
		Bundle bun = intent.getExtras();
		if (bun == null) { return; }
		Object[] contents = (Object[]) bun.get("pdus");
		
		for (Object obj : contents) {
			byte[] pdu = (byte[]) obj;
			SmsMessage sms = SmsMessage.createFromPdu(pdu);
			String content = sms.getMessageBody(); // 信息内容
			if (content.contains("自动报警") || content.contains("手动报警")) {
				Log.v("cr", "发生跌倒情况");
				Intent intentSend = new Intent(context, OneKeySoSActivity.class);
				intentSend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentSend.putExtra(OneKeySoSActivity.EXTRA_EVENT_TYPE, "用户发生跌倒情况~");
				context.startActivity(intentSend);
			}
			if (content.contains("状态正常") || content.contains("报警通过按键主动解除")) {
				Log.v("cr", "跌倒被解除");
			}
			
		}
	}
	
}
