//package com.yixin.nfyh.cloud.service;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.http.HttpResponse;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.Resources.NotFoundException;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.text.Html;
//import android.text.Spanned;
//import android.widget.RemoteViews;
//import cn.rui.framework.ui.RuiDialog;
//import cn.rui.framework.utils.AppInfo;
//import cn.rui.framework.utils.SoapCallback;
//
//import com.yixin.nfyh.cloud.BroadcastReceiverFlag;
//import com.yixin.nfyh.cloud.R;
//
//public class CheckVersionService implements SoapCallback
//{
//	/**
//	 * 正在下载
//	 */
//	public static final int		STATUS_DOWNING				= 0;
//	/**
//	 * 有新版本
//	 */
//	public static final int		STATUS_DOWN_NEW_VERSION		= 10;
//	/**
//	 * 下载成功
//	 */
//	public static final int		STATUS_DOWN_DONE			= 1;
//
//	/**
//	 * 下载失败
//	 */
//	public static final int		STATUS_DOWN_FAILD			= -1;
//	/**
//	 * 没有新版本
//	 */
//	public static final int		STATUS_DOWN_NO_VERSION		= -2;
//	private static final int	NOTIFICATION_NEW_VERSION_ID	= 0;
//	private Context				context;
//	private AppInfo				appinfo;
//	private Handler				handler;
//	private String				apkMiaosu;
//	private NotificationManager	nfManger;
//	private Notification		nf;
//	private int					downloadCount				= 0;
//
//	// private static CheckVersionService instance;
//
//	// public static CheckVersionService getInstance(Context context)
//	// {
//	// if (instance == null)
//	// instance = new CheckVersionService(context);
//	// return instance;
//	// }
//
//	public CheckVersionService(final Context context)
//	{
//		super();
//		this.handler = new Handler(new Handler.Callback()
//		{
//
//			@Override
//			public boolean handleMessage(Message msg)
//			{
//				switch (msg.what)
//				{
//				// 下载成功，发送广播
//					case STATUS_DOWN_DONE:
//						// sendBroadcast((File) msg.obj);
//						break;
//
//					default:
//						break;
//				}
//				return false;
//			}
//
//		});
//		this.context = context;
//		this.appinfo = new AppInfo(context);
//		nfManger = (NotificationManager) context
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//	}
//
//	private void sendBroadcast(File result)
//	{
//		Intent intent = new Intent(BroadcastReceiverFlag.ACTION_NEW_VERSION);
//		intent.putExtra("filename", result.getPath());
//		intent.putExtra("comment", apkMiaosu);
//		context.sendBroadcast(intent);
//	}
//
//	public void setHandler(Handler h)
//	{
//		this.handler = h;
//	}
//
//	public void check()
//	{
//		SoapService soap = new SoapService(context);
//		Map<String, Object> params = new HashMap<String, Object>();
//		String version = appinfo.getVersion();
//		params.put("version", version);
//		soap.setParams(params);
//		soap.setCallbackListener(this);
//		soap.call("CheckAppUpdate");
//	}
//
//	/**
//	 * 当有新版本的时候被调用
//	 * 
//	 * @param miaosu
//	 * @param link
//	 * @param version
//	 */
//	public void hasNewVersion(String version, String link, String miaosu)
//	{
//		this.apkMiaosu = miaosu;
//		Message.obtain(handler, STATUS_DOWN_NEW_VERSION).sendToTarget();
//		notifiNewVersion();
//		downloadVersionApk(link);
//
//	}
//
//	/**
//	 * 没有新版本或者调用失败的时候调用该方法
//	 */
//	public void noVersion()
//	{
//		Message.obtain(handler, STATUS_DOWN_NO_VERSION).sendToTarget();
//	}
//
//	/**
//	 * 显示下载文件成功
//	 * 
//	 * @param file
//	 */
//	public void showDownloadNewVersionDone(final File file, String updateComment)
//	{
//		Spanned ms = Html.fromHtml("<p>检查到有新版本，是否立即下载？</p>更新详情：<br/>"
//				+ updateComment);
//		RuiDialog dialog = new RuiDialog(context);
//		dialog.setTitle("检查新版本");
//		dialog.setMessage(ms.toString());
//		dialog.setLeftButton("返回", null);
//		dialog.setRightButton("安装", new DialogInterface.OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				intent.setDataAndType(Uri.fromFile(file),
//						"application/vnd.android.package-archive");
//				context.startActivity(intent);
//				try
//				{
//					nfManger.cancel(NOTIFICATION_NEW_VERSION_ID);
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
//
//	}
//
//	/**
//	 * 通知栏显示新版本
//	 */
//	public void notifiNewVersion()
//	{
//		nf = new Notification();
//		nf.tickerText = "检测到有新版本，正在下载...";
//		nf.flags |= Notification.FLAG_NO_CLEAR;
//		nf.icon = R.drawable.ic_launcher;
//		nf.when = System.currentTimeMillis();
//		nf.contentView = new RemoteViews(context.getPackageName(),
//				R.layout.remote_view_notification_process);
//		nfManger.notify(NOTIFICATION_NEW_VERSION_ID, nf);
//	}
//
//	/**
//	 * 通知进度条
//	 * 
//	 * @param value
//	 */
//	public void notifiDownloadVersionProgress(int value)
//	{
//		try
//		{
//			if (value - downloadCount > downloadCount)
//			{
//				downloadCount += 8;
//				CharSequence text = context.getResources().getString(
//						R.string.downingapk)
//						+ "(" + value + "%)";
//				nf.contentView.setProgressBar(R.id.pro_notification_download,
//						100, value, false);
//				nf.contentView.setTextViewText(R.id.tv_notification_download,
//						text);
//				nfManger.notify(NOTIFICATION_NEW_VERSION_ID, nf);
//
//			}
//		}
//		catch (NotFoundException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 通知文件下载完成
//	 * 
//	 * @param result
//	 */
//	public void notifiDownloadDoneProgress(File result)
//	{
//		try
//		{
//			nf.flags = Notification.FLAG_AUTO_CANCEL;
//			nf.contentView.setTextViewText(R.id.tv_notification_download,
//					"下载完成，点击安装");
//			nf.contentView.setProgressBar(R.id.pro_notification_download, 100,
//					100, false);
//			nf.defaults = Notification.DEFAULT_ALL;
//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.setDataAndType(Uri.fromFile(result),
//					"application/vnd.android.package-archive");
//			nf.contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
//			nfManger.notify(NOTIFICATION_NEW_VERSION_ID, nf);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 下载APK
//	 * 
//	 * @param link
//	 */
//	public void downloadVersionApk(String link)
//	{
//		new DownloadFileTask().execute(link);
//	}
//
//	/**
//	 * 下载文件线程
//	 * 
//	 * @author MrChenrui
//	 * 
//	 */
//	private class DownloadFileTask extends AsyncTask<String, Integer, File>
//			implements OnHttpCallback
//	{
//
//		private File	downFile;
//		private boolean	isDownlogind	= true;
//		private String	dwonloadFilepath;
//
//		@Override
//		protected File doInBackground(String... params)
//		{
//			isDownlogind = true;
//			downFile = new File(Environment.getExternalStorageDirectory()
//					.getPath() + "/" + context.getPackageName());
//			if (!downFile.exists())
//			{
//				downFile.mkdirs();
//			}
//			HttpUtil http = new HttpUtil(params[0]);
//			http.setResponseListener(this);
//			http.request();
//			long time = System.currentTimeMillis();
//			while (isDownlogind)
//			{
//				if (System.currentTimeMillis() - time > 1000 * 1000)
//				{
//					break;
//				}
//			}
//
//			return downFile;
//		}
//
//		/*
//		 * 下载完成
//		 */
//		@Override
//		protected void onPostExecute(File result)
//		{
//			super.onPostExecute(result);
//			notifiDownloadDoneProgress(result);
//			sendBroadcast(result);
//			Message.obtain(handler, STATUS_DOWN_DONE, result).sendToTarget();
//		}
//
//		/*
//		 * 正在下载文件，进度条显示
//		 */
//		@Override
//		protected void onProgressUpdate(Integer... values)
//		{
//			Message.obtain(handler, STATUS_DOWNING, values[0]).sendToTarget();
//			notifiDownloadVersionProgress(values[0]);
//			super.onProgressUpdate(values);
//		}
//
//		/*
//		 * 从服务器获取文件，下载APK
//		 */
//		@Override
//		public void OnResonseFileSuccess(int code, InputStream stream,
//				HttpResponse response)
//		{
//			try
//			{
//				String fileName = "nfyhCloud.apk";
//				long fileLength = response.getEntity().getContentLength();
//				dwonloadFilepath = downFile.getPath() + "/" + fileName;
//				downFile = null;
//				downFile = new File(dwonloadFilepath);
//				if (downFile.exists())
//					downFile.delete();
//				downFile.createNewFile();
//
//				FileOutputStream out = new FileOutputStream(downFile);
//				byte[] buffer = new byte[6 * 1024];
//				int readLen = 0;
//				int curReadLen = 0;
//				while ((curReadLen = stream.read(buffer)) != -1)
//				{
//					out.write(buffer, 0, curReadLen);
//					Integer value = (int) (((float) readLen / fileLength) * 100);
//					publishProgress(value);
//					readLen += curReadLen;
//				}
//				out.flush();
//				out.close();
//
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//				Message.obtain(handler, STATUS_DOWN_FAILD).sendToTarget();
//			}
//			finally
//			{
//				isDownlogind = false;
//			}
//		}
//
//		@Override
//		public void OnResponseSuccess(int code, String html,
//				HttpResponse response)
//		{
//		}
//
//		@Override
//		public void OnHttpError(int code, String msg)
//		{
//			isDownlogind = false;
//			Message.obtain(handler, STATUS_DOWN_FAILD).sendToTarget();
//		}
//	}
//
//	@Override
//	public void onSoapResponse(Object response)
//	{
//		// {"HasNewVersion":true,"DownloadLink":"http://192.168.162.13/download.aspx?action=apk&id=48","Model":{"Id":48,"Apkname":"南方院后云服务","Comment":"较为完善版本","Updatecomment":"<span>较为完善版本</span>","Apkpics":"","Apkversion":1.0,"Isnewupdate":false,"Postdate":"2013-11-22T00:00:00","Downloadcount":25,"Apksize":4409.0,"Apkicon":"南方院后云服务V1.png"}}
//
//		// {"HasNewVersion":false,"DownloadLink":"新版本没有出来呢！当前最新版本：1，您请求的版本：5","Model":null}
//
//		try
//		{
//			JSONObject json = new JSONObject(response.toString());
//			String hasNew = json.getString("HasNewVersion");
//
//			if (Boolean.valueOf(hasNew))
//			{
//				String link = json.getString("DownloadLink");
//				JSONObject model = json.getJSONObject("Model");
//				String version = model.getString("Apkversion");
//				String miaosu = model.getString("Updatecomment");
//				hasNewVersion(version, link, miaosu);
//			}
//			else
//			{
//				noVersion();
//			}
//		}
//		catch (JSONException e)
//		{
//			e.printStackTrace();
//			noVersion();
//		}
//
//	}
//
//	@Override
//	public void onSoapError(int code, String msg)
//	{
//		Message.obtain(handler, STATUS_DOWN_FAILD).sendToTarget();
//		noVersion();
//	}
//}
