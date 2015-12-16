package com.rae.im.sdk.rongcloud;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * 融云IM 接口
 * 
 * @author ChenRui
 * 
 */
class RongCloudIM extends RaeRongCloudIM {

	@Override
	public void connect(String token, IMConnectionListener l) {
		RongIM.connect(token, new RongCloudConnectCallback(l));
	}

	/**
	 * 登录回调
	 * 
	 * @author ChenRui
	 * 
	 */
	class RongCloudConnectCallback extends ConnectCallback {

		private IMConnectionListener mListener;

		public RongCloudConnectCallback(IMConnectionListener l) {
			mListener = l;
		}

		/**
		 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
		 */
		@Override
		public void onTokenIncorrect() {
			Log.w("rae", "登录过期");
			mListener.onTokenIncorrect();
		}

		/**
		 * 连接融云失败
		 * 
		 * @param errorCode
		 *            错误码，可到官网 查看错误码对应的注释
		 */
		@Override
		public void onError(ErrorCode code) {
			Log.w("rae", "连接融云失败" + code.getValue());
			mListener.onError(code);
		}

		/**
		 * 连接融云成功
		 * 
		 * @param userid
		 *            当前 token
		 */
		@Override
		public void onSuccess(String token) {
			Log.i("rae", "登连接融云成功" + token);
			mListener.onSuccess(token);
		}

	}

	@Override
	public void init(Application application) {
		/**
		 * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
		 * io.rong.push 为融云 push 进程名称，不可修改。
		 */
		if (application.getApplicationInfo().packageName
				.equals(getCurProcessName(application))
				|| "io.rong.push".equals(getCurProcessName(application))) {

			/**
			 * IMKit SDK调用第一步 初始化
			 */
			RongIM.init(application);

			RaeRongCloudIMConfig.init(application.getApplicationContext());

			Log.i("rae", "初始化SDK");
		}

	}

	/**
	 * 获得当前进程的名字
	 * 
	 * @param context
	 * @return 进程号
	 */
	private static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	@Override
	public void disconnect() {
		RongIM.getInstance().disconnect();
	}

	@Override
	public void logout() {
		RongIM.getInstance().logout();

	}

	@Override
	public void login(String appSecret, final String username,
			final String passwrod, final String extra, final IMLoginListener l) {
		IRongServerApi api = new RongServerApi();
		api.getUserToken(RaeRongCloudIMConfig.getAppKey(), appSecret, username,
				passwrod, extra, new IRongUserTokenCallback() {
					@Override
					public void onUserTokenSuccess(String uid, String token) {

						Log.d("RongCloudIM", "融云登录：" + uid + "," + passwrod
								+ "," + extra + "," + token);

						// 登录融云
						connect(token, l);
					}

					@Override
					public void onUserTokenErrror(int code, String msg) {
						l.onIMLoginError(username, passwrod, code, msg);
					}
				});
	}
}
