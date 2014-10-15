package com.yixin.nfyh.cloud;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import cn.rui.framework.utils.DateUtil;

import com.rae.core.image.cache.disc.naming.HashCodeFileNameGenerator;
import com.rae.core.image.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.rae.core.image.loader.DisplayImageOptions;
import com.rae.core.image.loader.ImageLoader;
import com.rae.core.image.loader.ImageLoaderConfiguration;
import com.rae.core.image.loader.assist.QueueProcessingType;
import com.yixin.monitors.sdk.api.ApiMonitor;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.bll.DesktopSOS;
import com.yixin.nfyh.cloud.bll.GlobalSetting;
import com.yixin.nfyh.cloud.data.IUser;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.server.NfyhCloudUnHanderException;
import com.yixin.nfyh.cloud.service.CoreServerBinder;
import com.yixin.nfyh.cloud.service.CoreService;
import com.yixin.nfyh.cloud.service.PushNotificationService;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 全局APP
 * 
 * @author MrChenrui
 * 
 */
@SuppressLint("SimpleDateFormat")
public class NfyhApplication extends Application {
	
	private Context					context;
	
	private GlobalSetting			globalsetting;
	
	public static final int			ACTIVITY_RESULT_CAMARA_OK	= 0;
	
	private String					takePhotoCurrentPath;
	
	private DesktopSOS				desktopSos;
	
	private boolean					isLogined					= false;
	
	private List<Activity>			activitys					= new ArrayList<Activity>();
	
	private ConfigServer			config;
	
	private IUser					apiUser;
	
	private CoreServicerConnection	conn;
	
	private CoreServerBinder		binder;

	private String	mCurrentUserId;
	
	public void addActivity(Activity at) {
		this.activitys.add(at);
	}
	
	public void delActivity(Activity at) {
		if (this.activitys.contains(at)) {
			this.activitys.remove(at);
		}
	}
	
	public List<Activity> getActivitys() {
		return this.activitys;
	}
	
	public CoreServerBinder getBinder() {
		return binder;
	}
	
	/**
	 * 连接设备
	 */
	public void connect() {
		if (binder != null) {
			binder.conncet();
		}
	}
	
	/**
	 * 断开设备
	 */
	public void disconnect() {
		if (binder != null) {
			binder.disconnect();
		}
	}
	
	/**
	 * 是否连接
	 * 
	 * @return
	 */
	public boolean isConnected() {
		if (binder == null) return false;
		ApiMonitor api = binder.getDevice();
		if (api == null) return false;
		return api.isConnected();
	}
	
	/**
	 * 获取当前监测设备
	 * 
	 * @return
	 */
	public ApiMonitor getApiMonitor() {
		if (binder == null) { return null; }
		return binder.getDevice();
	}
	
	/**
	 * 设置是否登录
	 * 
	 * @param value
	 */
	public void setIsLogin(boolean value) {
		this.isLogined = value;
	}
	
	/**
	 * 是否已经登录
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return isLogined;
	}
	
	// private boolean isInDesktop = false;
	@Override
	public void onCreate() {
		context = getApplicationContext();
		initImageLoader(context);
		globalsetting = new GlobalSetting(context);
		config = new ConfigServer(context);
		this.desktopSos = new DesktopSOS(context);
		this.apiUser = NfyhCloudDataFactory.getFactory(getApplicationContext()).getUser();
		startServices();
		commitLogFile();// 上传日志文件
		NfyhCloudUnHanderException.init(getApplicationContext());
		LogUtil.getLog().info("Appliaction", "应用程序启动");
	}
	
	/**
	 * 启动各项服务
	 */
	private void startServices() {
		bindMonitorService();
		
		// 推送服务
		startService(new Intent(context, PushNotificationService.class));
	}
	
	private void bindMonitorService() {
		// 核心服务
		Intent service = new Intent(context, CoreService.class);
		conn = new CoreServicerConnection();
		bindService(service, conn, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * 上传日志文件
	 */
	private void commitLogFile() {
		String key = "last-log-commit";
		String now = DateUtil.getCurrentDateString("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date nowDate = new Date();
			Date lastDate = df.parse(globalsetting.getValue(key, now));
			long diff = nowDate.getTime() - lastDate.getTime();
			long minute = diff / (1000 * 60);
			if (minute > 1 && minute < 30) {
				return; // 提交间隔小于30分钟不提交
			}
			else {
				LogUtil.getLog().commit(getApplicationContext());
				globalsetting.setValue(key, now);
				globalsetting.commit();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class CoreServicerConnection implements ServiceConnection {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (service instanceof CoreServerBinder) binder = (CoreServerBinder) service;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	}
	
	public void showSOSinDesktop() {
		if (this.config.getBooleanConfig(ConfigServer.KEY_ENABLE_DESKTOP)) {
			this.removeSOSinDesktop();
			this.desktopSos.initFloatView();
		}
	}
	
	public void removeSOSinDesktop() {
		this.desktopSos.remove();
	}
	
	// /**
	// * 获取当前用户的基本信息
	// *
	// * @return
	// */
	// public UserInfo getUserInfo(String uid)
	// {
	// MonitorDataHandle.initialConfig(context, context.getResources()
	// .getString(R.string.dbname));
	//
	// userinfo = MonitorDataHandle.getUserInfo(uid);
	// return userinfo;
	// }
	public void setUserInfo(Users user) {
		try {
			this.mCurrentUserId = user.getUid();
			apiUser.createUser(user);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// /**
	// * 打开照相机
	// *
	// * @return 照片所在的路径
	// * @throws Exception
	// */
	// public String openCamera(Activity activity) throws Exception
	// {
	// if (!Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) throw new Exception(
	// "SDCard没有准备好，无法打开相机！");
	// String filePath = Environment.getExternalStorageDirectory().getPath()
	// + "/DCIM/Camera/IMG" + createRandomFileName();
	//
	// File file = new File(filePath);
	// if (!file.exists())
	// {
	// file.getParentFile().mkdirs();
	// file.createNewFile();
	// }
	//
	// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// Uri uri = Uri.fromFile(file);
	// intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	// activity.startActivityForResult(intent, ACTIVITY_RESULT_CAMARA_OK);
	// takePhotoCurrentPath = filePath;
	// return filePath;
	// }
	/**
	 * 获取当前照片的路径
	 * 
	 * @return
	 */
	public String getCurrentCameraPath() {
		return takePhotoCurrentPath;
	}
	
	public static class DesktopBroderRecevice extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			context.startActivity(new Intent(context, OneKeySoSActivity.class));
		}
	}
	
	//
	// /**
	// * 创建图片的文件名：2013-12-12-12-10-10-10-23566.jpg
	// *
	// * @return
	// */
	// private String createRandomFileName()
	// {
	// Calendar cal = Calendar.getInstance();
	// Date date = cal.getTime();
	// SimpleDateFormat dataFormat = new SimpleDateFormat(
	// "yyyy-MM-dd-HH-mm-ss");
	// String strDate = dataFormat.format(date);
	//
	// Random rand = new Random();
	// int num = rand.nextInt(9999) * 10;
	// return strDate + "-" + num + ".jpg";
	// }
	/**
	 * @return the globalsetting
	 */
	public GlobalSetting getGlobalsetting() {
		return globalsetting;
	}
	
	public Users getCurrentUser() {
		String uid = this.mCurrentUserId;
		Users user = null;
		try {
			user = apiUser.getUser(uid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		// if (user == null || TextUtils.isEmpty(user.getCookie()))
		// {
		// Intent intent = new Intent(this, LoginActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(intent); // 跳转到登录界面
		// Toast.makeText(context, "登录异常，请重新登陆！", Toast.LENGTH_LONG).show();
		// // Process.killProcess(Process.myPid()); // 重新启动
		// }
		
		return user;
	}
	
	public static void initImageLoader(final Context context) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_stub).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
		// .displayer(new FadeInBitmapDisplayer(1000))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCacheExtraOptions(480, 800).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1).denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // TODO:发布时，移除调试模式
				.defaultDisplayImageOptions(options) // 默认配置
				.build();
		ImageLoader.getInstance().init(config);
	}
}
