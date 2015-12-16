package com.rae.im.sdk.rongcloud;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author ChenRui
 * 
 */
public final class RaeRongCloudIMConfig {

	public static final String API_SERVER_URL = "https://api.cn.ronghub.com";
	private static String AppKey;
//	private static String AppSecret;

	public static String getAppKey() {

		return AppKey;
	}

	// public static String getAppSecret() {
	// return AppSecret;
	// }
	//
	// public static void setAppSecret(String value) {
	// AppSecret = value;
	// }

	public static void init(Context applicationContext) {

		// 获取应用程序的meta的开发者key
		if (AppKey == null) {
			try {
				ApplicationInfo info = applicationContext.getPackageManager()
						.getApplicationInfo(
								applicationContext.getPackageName(),
								PackageManager.GET_META_DATA);
				AppKey = info.metaData.getString("RONG_CLOUD_APP_KEY");

			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
