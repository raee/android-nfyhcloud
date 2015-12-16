package com.rae.im.sdk.rongcloud;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient.ConnectCallback;
import io.rong.imlib.RongIMClient.ErrorCode;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * ����IM �ӿ�
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
	 * ��¼�ص�
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
		 * Token ���������ϻ�������Ҫ����Ϊ Token �Ѿ����ڣ�����Ҫ�� App Server ��������һ���µ� Token
		 */
		@Override
		public void onTokenIncorrect() {
			Log.w("rae", "��¼����");
			mListener.onTokenIncorrect();
		}

		/**
		 * ��������ʧ��
		 * 
		 * @param errorCode
		 *            �����룬�ɵ����� �鿴�������Ӧ��ע��
		 */
		@Override
		public void onError(ErrorCode code) {
			Log.w("rae", "��������ʧ��" + code.getValue());
			mListener.onError(code);
		}

		/**
		 * �������Ƴɹ�
		 * 
		 * @param userid
		 *            ��ǰ token
		 */
		@Override
		public void onSuccess(String token) {
			Log.i("rae", "���������Ƴɹ�" + token);
			mListener.onSuccess(token);
		}

	}

	@Override
	public void init(Application application) {
		/**
		 * OnCreate �ᱻ����������룬��α������룬ȷ��ֻ������Ҫʹ�� RongIM �Ľ��̺� Push ����ִ���� init��
		 * io.rong.push Ϊ���� push �������ƣ������޸ġ�
		 */
		if (application.getApplicationInfo().packageName
				.equals(getCurProcessName(application))
				|| "io.rong.push".equals(getCurProcessName(application))) {

			/**
			 * IMKit SDK���õ�һ�� ��ʼ��
			 */
			RongIM.init(application);

			RaeRongCloudIMConfig.init(application.getApplicationContext());

			Log.i("rae", "��ʼ��SDK");
		}

	}

	/**
	 * ��õ�ǰ���̵�����
	 * 
	 * @param context
	 * @return ���̺�
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

						Log.d("RongCloudIM", "���Ƶ�¼��" + uid + "," + passwrod
								+ "," + extra + "," + token);

						// ��¼����
						connect(token, l);
					}

					@Override
					public void onUserTokenErrror(int code, String msg) {
						l.onIMLoginError(username, passwrod, code, msg);
					}
				});
	}
}
