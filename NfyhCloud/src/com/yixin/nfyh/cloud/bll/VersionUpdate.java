package com.yixin.nfyh.cloud.bll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.Header;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;
import cn.rui.framework.ui.RuiDialog;

import com.rae.core.http.async.AsyncHttpClient;
import com.rae.core.http.async.FileAsyncHttpResponseHandler;
import com.yixin.nfyh.cloud.model.VersionUpdateModel;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.VersionUpdateServer;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 版本更新
 * 
 * @author ChenRui
 * 
 */
public class VersionUpdate implements SoapConnectionCallback<VersionUpdateModel> {
	
	private Context		mContext;
	private File		mApkFile;
	private String		mDownloadPath;
	private RuiDialog	ruiDialog;
	private int			mCurrentVersionCode	= 1;
	
	public VersionUpdate(Context context) {
		this.mContext = context;
		try {
			mCurrentVersionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		}
		catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		int version = mContext.getSharedPreferences("VersionUpdate", Context.MODE_PRIVATE).getInt("Version", 0);
		mDownloadPath = Environment.getExternalStorageDirectory().getPath() + "/nfyhcloudV";
		mApkFile = new File(mDownloadPath + version + ".apk");
		ruiDialog = new RuiDialog(mContext);
	}
	
	public void check() {
		ruiDialog.setTitle("正在检查更新");
		ruiDialog.setMessage("检查更新中，请稍候。");
		ruiDialog.setRightButton("返回", null);
		ruiDialog.show();
		VersionUpdateServer versionApi = NfyhWebserviceFactory.getFactory(mContext).getVersionUpdateServer();
		versionApi.check(this);
	}
	
	@Override
	public void onSoapConnectSuccess(final VersionUpdateModel data) {
		
		SharedPreferences share = mContext.getSharedPreferences("VersionUpdate", Context.MODE_PRIVATE);
		int version = share.getInt("Version", 0);
		if (mApkFile.exists() && version >= data.getVersionCode()) { //已经下载完成，并且下载的版本一定大于等于当前的版本。
			Toast.makeText(mContext, "新版本已经下载完成，正准备安装。", Toast.LENGTH_SHORT).show();
			installApk(mApkFile); //已经下载。
			return;
		}
		
		if (mCurrentVersionCode >= data.getVersionCode()) {
			show("太牛了，当前版本已经是最新了！");
			return;
		}
		
		Editor edit = share.edit();
		edit.putInt("Version", data.getVersionCode());
		edit.commit();
		
		String msg = Html.fromHtml("<p>版本号：" + data.getVersionCode() + "，更新内容：</p>" + data.getUpdateContent()).toString();
		
		ruiDialog.setTitle("发现新版本，是否更新？");
		ruiDialog.setMessage(msg);
		ruiDialog.setLeftButton("不更新", null);
		ruiDialog.setRightButton("更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 下载APK文件
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(data.getDownloadUrl(), new FileAsyncHttpResponseHandler(mContext) {
					
					@Override
					public void onSuccess(int arg0, Header[] arg1, File file) {
						try {
							if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
								File newPath = new File(mDownloadPath + data.getVersionCode() + ".apk");
								FileInputStream in = new FileInputStream(file);
								FileOutputStream out = new FileOutputStream(newPath);
								byte[] buffer = new byte[512];
								int len = 0;
								while ((len = in.read(buffer)) != -1) {
									out.write(buffer, 0, len);
								}
								in.close();
								out.close();
								mApkFile = newPath;
								installApk(newPath);
								file.delete();
								
								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setDataAndType(Uri.fromFile(newPath), "application/vnd.android.package-archive");
								Notification notification = new Notification();
								notification.icon = mContext.getApplicationInfo().icon;
								notification.defaults = Notification.DEFAULT_ALL;
								notification.tickerText = "南方院后云服务版本下载完成！";
								notification.when = System.currentTimeMillis();
								notification.flags = Notification.FLAG_AUTO_CANCEL;
								notification.setLatestEventInfo(mContext, "新版本下载完成", "点击更新", PendingIntent.getActivity(mContext, 0, intent, 0));
								
								((NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE)).notify(10245, notification);
								
								Log.i("VersionUpdate", "版本下载成功！");
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
						ruiDialog.dismiss();
					}
					
					@Override
					public void onFailure(int arg0, Header[] arg1, Throwable arg2, File arg3) {
						Log.e("VersionUpdate", "版本下载失败！");
					}
					
					@Override
					public void onProgress(int bytesWritten, int totalSize) {
						super.onProgress(bytesWritten, totalSize);
						double current = bytesWritten * 0.01;
						double count = totalSize * 0.01;
						int progress = (int) ((current / count) * 100);
						ruiDialog.setTitle("正在下载...");
						String message = "正在下载新版本，共" + totalSize / 1024 / 1024 + "MB（" + progress + "%)";
						ruiDialog.setMessage(Html.fromHtml(message).toString());
						Log.i("VersionUpdate", message);
					}
				});
			}
		});
		
		ruiDialog.show();
	}
	
	@Override
	public void onSoapConnectedFalid(WebServerException e) {
		show("检查版本出错！" + e.getMessage());
	}
	
	private void show(String msg) {
		ruiDialog.setMessage(msg);
		ruiDialog.setRightButton("确定", null);
		ruiDialog.show();
	}
	
	private void installApk(File file) {
		ruiDialog.dismiss();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}
	
}
