package com.rae.im.sdk.rongcloud;

import java.util.Random;

import org.json.JSONObject;

import android.util.Log;

import com.rae.im.sdk.http.IRaeAsyncResponse;
import com.rae.im.sdk.http.RaeAsyncHttpClient;

/**
 * 融云服务端API
 * 
 * @author ChenRui
 * 
 */
public class RongServerApi implements IRongServerApi {

	@Override
	public void getUserToken(String appKey, String appSecret, String uid,
			String userName, String headUrl, final IRongUserTokenCallback l) {

		// 生成随机数
		int nonce = Math.abs(new Random(1000000).nextInt());
		long timestamp = System.currentTimeMillis();

		// 异步请求
		RaeAsyncHttpClient client = new RaeAsyncHttpClient();
		client.addHead("App-Key", appKey);
		client.addHead("Nonce", nonce);
		client.addHead("Timestamp", timestamp);
		client.addHead("Signature", SHA1(appSecret + nonce + timestamp));

		client.addHttpRequestParam("userId", uid);
		client.addHttpRequestParam("name", userName);
		client.addHttpRequestParam("portraitUri", headUrl);

		client.post(RaeRongCloudIMConfig.API_SERVER_URL + "/user/getToken.json",
				new IRaeAsyncResponse() {

					@Override
					public void onHttpSuccess(int statusCode, String html) {
						try {
							JSONObject obj = new JSONObject(html);
							l.onUserTokenSuccess(obj.getString("userId"),
									obj.getString("token"));
						} catch (Exception e) {
							onHttpFaild(0, html, e);
						}
					}

					@Override
					public void onHttpFaild(int statusCode, String html,
							Throwable e) {
						Log.e("rae", "onHttpFaild");

						if (html != null) {
							Log.e("rae", html);
							l.onUserTokenErrror(statusCode, html);
						} else {
							l.onUserTokenErrror(statusCode, "获取Token错误！");
						}

						if (e != null)
							e.printStackTrace();

					}
				});

	}

	/**
	 * SHA1 加密
	 * 
	 * @param decript
	 *            要加密的字符串
	 * @return 返回加密后的字符串
	 */
	private static String SHA1(String decript) {
		try {
			java.security.MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

}
