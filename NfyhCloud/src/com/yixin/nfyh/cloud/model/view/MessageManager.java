package com.yixin.nfyh.cloud.model.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.yixin.nfyh.cloud.model.Messages;

public class MessageManager {
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
			intent = null;
		}
		else {
			intent.setClassName(context, item.getIntentName());
			if (!TextUtils.isEmpty(item.getIntentCategroy())) {
				intent.addCategory(item.getIntentCategroy());
			}
			if (!TextUtils.isEmpty(item.getIntentFlag())) {
				intent.addFlags(getIntentFlags(item.getIntentFlag()));
			}
			if (!TextUtils.isEmpty(item.getIntentAction())) {
				intent.setAction(item.getIntentAction());
			}
			if (!TextUtils.isEmpty(item.getIntentDate())) {
				intent.setData(Uri.parse(item.getIntentDate()));
			}
			if (!TextUtils.isEmpty(item.getIntentExtra())) {
				intent.putExtras(getExtras(item.getIntentExtra()));
			}
		}
		return intent;
	}
	
	/**
	 * 解析意图数据
	 * 
	 * @param extras
	 * @return
	 */
	private static Bundle getExtras(String extras) {
		
		// 数据格式：[{"key":"type","value":"test"}]
		Bundle result = new Bundle();
		if (extras == null) { return result; }
		try {
			JSONArray arr = new JSONArray(extras);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				String key = obj.getString("key");
				String value = obj.getString("value");
				result.putString(key, value);
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static int getIntentFlags(String flags) {
		// TODO：根据名称获取标签
		return 0;
	}
}
