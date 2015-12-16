package com.rae.im.sdk.rongcloud;

import io.rong.imlib.RongIMClient.ErrorCode;

public interface IMConnectionListener {
	/**
	 * Token ���������ϻ�������Ҫ����Ϊ Token �Ѿ����ڣ�����Ҫ�� App Server ��������һ���µ� Token
	 */
	public void onTokenIncorrect();

	/**
	 * ��������ʧ��
	 * 
	 * @param errorCode
	 *            �����룬�ɵ����� �鿴�������Ӧ��ע��
	 */
	public void onError(ErrorCode code);

	/**
	 * �������Ƴɹ�
	 * 
	 * @param userid
	 *            ��ǰ token
	 */
	public void onSuccess(String token);
}
