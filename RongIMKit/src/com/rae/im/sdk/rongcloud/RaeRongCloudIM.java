package com.rae.im.sdk.rongcloud;

import android.app.Application;

public abstract class RaeRongCloudIM {

	private static final RaeRongCloudIM instance = new RongCloudIM();

	public static RaeRongCloudIM getInstance() {
		return instance;
	}

	/**
	 * ��ʼ������������ӿڣ�<b>ע�⣺</b>�÷���ֻ�ܱ�����һ�Ρ�
	 * 
	 * @param application
	 *            Ӧ�ó�����дһ����̳С�
	 */
	public abstract void init(Application application);

	/**
	 * ���ӷ�����
	 * 
	 * @param token
	 *            ��¼ƾ֤
	 * @param l
	 *            ���ӻص�
	 */
	public abstract void connect(String token, IMConnectionListener l);

	/**
	 * ��¼���������
	 * 
	 * @param appSecret
	 *            �������ʺ������appSecret
	 * @param username
	 *            �û���
	 * @param passwrod
	 *            ����
	 * @param extra
	 *            ������Ϣ��JSON ��ʽ��
	 */
	public abstract void login(String appSecret, String username,
			String passwrod, String extra, IMLoginListener l);

	/**
	 * �Ͽ�����
	 */
	public abstract void disconnect();

	/**
	 * �ǳ�
	 */
	public abstract void logout();
}
