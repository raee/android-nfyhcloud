package cn.rui.framework.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * 公共的工具类
 * 
 * @author MrChenrui
 * 
 */
@SuppressLint("SimpleDateFormat")
public final class CommonUtil {

	/**
	 * 发送短信，注意添加发送短信权限
	 * 
	 * @param message
	 *            短信内容，自动拆分发送
	 */
	public static void sendSms(Context context, String number, String message) {
		try {
			if (isNullorEmpt(number, message)) return;
			SmsManager sms = SmsManager.getDefault();
			Intent intent = new Intent();
			PendingIntent sentIntent = PendingIntent.getActivity(context, 0, intent, 0);
			if (message.length() > 70) {
				// 拆分短信
				ArrayList<String> messages = sms.divideMessage(message);
				for (String msg : messages) {
					sms.sendTextMessage(number, null, msg, sentIntent, null);
				}
			}
			else {
				sms.sendTextMessage(number, null, message, sentIntent, null);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字符串是否为空，主要一个不满足都会返回
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean isNullorEmpt(String... strs) {
		for (String str : strs) {
			if (str == null || str.trim().length() < 1)
				return true;
			else
				return false;
		}

		return false;
	}

	/**
	 * 设置操作栏的Icon
	 * 
	 * @param actionView
	 *            项视图
	 * @param resid
	 *            图片资源
	 */
	public static void setActionViewItemIcon(View actionView, int resid) {
		if (actionView == null || resid == 0) return;
		if (actionView instanceof TextView) // ActionMenuItemView 是继承于 textview
		{
			try {
				Drawable icon = actionView.getContext().getResources().getDrawable(resid);
				Method method = actionView.getClass().getMethod("setIcon", Drawable.class);
				method.invoke(actionView, icon);
			}
			catch (Exception e) {
				Log.e("tt", "设置ActionView错误：" + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/**
	 * 检查网络是否连通
	 * 
	 * @return 连通返回真
	 */
	public static boolean checkNetConnected(Context context) {
		ConnectivityManager net = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (net == null) return false;

		for (NetworkInfo info : net.getAllNetworkInfo()) {
			if (info.getState() == NetworkInfo.State.CONNECTED && info.isAvailable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 */
	public static String decryptBASE64(String key) {
		byte[] data = android.util.Base64.decode(key.getBytes(), android.util.Base64.DEFAULT);

		return new String(data);
	}

	/**
	 * * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(String key) {
		if (TextUtils.isEmpty(key)) {
			return "";
		}
		return android.util.Base64.encodeToString(key.getBytes(), android.util.Base64.DEFAULT);
	}

	/**
	 * 获取ASP.NET 中Json的日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static Date getDateFromJson(String datestring) {
		Date date;
		try {
			String format = datestring.replace("/Date(", "").replace("+0800)/", "");
			long milliseconds = Long.valueOf(format);
			date = new Date(milliseconds);
		}
		catch (Exception e) {
			date = new Date();
		}

		return date;
	}

	/**
	 * 获取ASP.NET 中Json的日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static String getDateString(String datestring) {
		String result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getDateFromJson(datestring));
		return result;
	}

	/**
	 * 获取ASP.NET 中Json的日期格式
	 * 
	 * @param str
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateStringFromJson(String datestring) {
		Date date = getDateFromJson(datestring);
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

}
