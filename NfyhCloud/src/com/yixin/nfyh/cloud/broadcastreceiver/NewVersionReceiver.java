//package com.yixin.nfyh.cloud.broadcastreceiver;
//
//import java.io.File;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//
//import com.yixin.nfyh.cloud.BroadcastReceiverFlag;
//import com.yixin.nfyh.cloud.service.CheckVersionService;
//import com.yixin.nfyh.cloud.ui.TimerToast;
//
///**
// * 新版本的广播接受者
// * 
// * @author MrChenrui
// * 
// */
//public class NewVersionReceiver extends BroadcastReceiver
//{
//	public Activity	mContext;
//
//	public NewVersionReceiver(Activity context)
//	{
//		this.mContext = context;
//	}
//
//	@Override
//	public void onReceive(Context context, Intent intent)
//	{
//		String actionName = intent.getAction();
//		if (actionName.equals(BroadcastReceiverFlag.ACTION_NEW_VERSION))
//		{
//			try
//			{
//				File file = new File(intent.getStringExtra("filename"));
//				String comment = intent.getStringExtra("comment");
//				new CheckVersionService(mContext).showDownloadNewVersionDone(
//						file, comment);
//			}
//			catch (Exception e)
//			{
//				TimerToast.show(context, "文件下载失败");
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static BroadcastReceiver registerReceiver(Activity context)
//	{
//		IntentFilter filter = new IntentFilter(
//				BroadcastReceiverFlag.ACTION_NEW_VERSION);
//		BroadcastReceiver rec = new NewVersionReceiver(context);
//		context.registerReceiver(rec, filter);
//		return rec;
//
//	}
//
//}
