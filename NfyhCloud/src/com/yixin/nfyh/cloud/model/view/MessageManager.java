package com.yixin.nfyh.cloud.model.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.rae.alarm.NfyhAlarmEntity;
import com.rae.core.alarm.AlarmEntity;
import com.rae.core.alarm.AlarmUtils;
import com.rae.core.alarm.provider.AlarmProviderFactory;
import com.yixin.nfyh.cloud.data.NfyhCloudDataOpenHelp;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.utils.LogUtil;

public final class MessageManager {

	/**
	 * 闹钟类型
	 */
	public static final String MESSAGE_TYPE_ALARM = "alarm";
	/**
	 * 数据库操作
	 */
	public static final String MESSAGE_TYPE_QUERY = "query";

	/**
	 * 文本消息
	 */
	public static final String MESSAGE_TYPE_TEXT = "text";

	/**
	 * 获取消息的意图
	 * 
	 * @param context
	 * @param item
	 * @return
	 */
	public static Intent getIntent(Context context, Messages item) {
		Intent intent = new Intent();
		if (TextUtils.isEmpty(item.getIntentName())) {
			return intent;
		}

		intent.setClassName(context, item.getIntentName());

		if (!TextUtils.isEmpty(item.getIntentCategroy())) { // 分类
			intent.addCategory(item.getIntentCategroy());
		}

		if (!TextUtils.isEmpty(item.getIntentFlag())) { // 标志
			intent.addFlags(getIntentFlags(item.getIntentFlag()));
		}

		if (!TextUtils.isEmpty(item.getIntentAction())) {// action
			intent.setAction(item.getIntentAction());
		}

		if (!TextUtils.isEmpty(item.getIntentDate())) { // 数据
			intent.setData(Uri.parse(item.getIntentDate()));
		}

		if (!TextUtils.isEmpty(item.getIntentExtra())) { // 附加数据
			intent.putExtras(getExtras(item.getIntentExtra()));
		}

		if (!TextUtils.isEmpty(item.getTypeCode())) { // 类型
			intent.setType(item.getTypeCode());
			setViewMessageIntent(context, item, intent);
		}

		return intent;
	}

	private static void setViewMessageIntent(Context context, Messages item,
			Intent intent) {
		if (MESSAGE_TYPE_TEXT.equals(item.getTypeCode())) {
			intent.setClassName(context,
					"cn.rui.framework.ui.WebViewerActivity");
			intent.putExtra("message", item.getContent());
			intent.putExtra("title", item.getTitle());
		}
	}

	public static Intent getStartIntent(Context context, Messages item) {
		String type = item.getTypeCode();

		Intent intent = getIntent(context, item);

		if (MESSAGE_TYPE_ALARM.equals(type)) {
			intent.setClassName(context, "com.rae.alarm.AlarmListActivity");
		} else if (MESSAGE_TYPE_TEXT.equals(type)) {
			setViewMessageIntent(context, item, intent);
		}
		return intent;
	}

	// 创建闹钟
	public static void createAlarm(Context context, Messages item,
			String entityJson) {
		// 闹钟处理
		if (MESSAGE_TYPE_ALARM.equals(item.getTypeCode())) {
			// 保存闹钟
			AlarmEntity entity = AlarmUtils.converEntity(entityJson);
			NfyhAlarmEntity nfyhEntity = new NfyhAlarmEntity(entity);
			AlarmProviderFactory.create(context, nfyhEntity); // 建立闹钟
		}
	}

	public static boolean query(Messages model, Context context) {
		if (TextUtils.isEmpty(model.getIntentDate())) {
			return false;
		}

		if (MESSAGE_TYPE_QUERY.equals(model.getTypeCode())) {
			NfyhCloudDataOpenHelp dbHelper = new NfyhCloudDataOpenHelp(context);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String sqls = model.getIntentDate();

			String[] sqlSplits = sqls.split(";");
			try {
				db.beginTransaction();
				for (String sql : sqlSplits) {
					if (!TextUtils.isEmpty(sql)) {
						db.execSQL(sql);
					}
				}
				db.setTransactionSuccessful();
				db.endTransaction();

			} catch (SQLException e) {
				LogUtil.getLog().setExcetion("PullMessageQuery", e);
			}

			return true;
		}

		return false;
	}

	/**
	 * 解析意图数据
	 * 
	 * @param extras
	 * @return
	 */
	public static Bundle getExtras(String extras) {

		// 数据格式：[{"key":"type","value":"test"}]
		Bundle result = new Bundle();
		if ("null".equals(extras) || extras == null) {
			return result;
		}
		try {
			JSONArray arr = new JSONArray(extras);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				String key = obj.getString("key");
				String value = obj.getString("value");
				result.putString(key, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static int getIntentFlags(String flags) {
		// TODO：根据名称获取标签
		return 0;
	}
}
