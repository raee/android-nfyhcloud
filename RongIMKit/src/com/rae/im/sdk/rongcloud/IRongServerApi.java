package com.rae.im.sdk.rongcloud;

/**
 * ���Ʒ����API
 * 
 * @author ChenRui
 * 
 */
public interface IRongServerApi {

	/**
	 * ��ȡ�û�Token
	 * 
	 * <p>
	 * �����û���¼��
	 * 
	 * <p>
	 * ��������������������ķ����ȥ������Ϊ���App Secret �ǻᷢ���ı䣬�ɷ����ȥ����Ƚϼ򵥡�
	 * <p>
	 * ���ȵ�һ���ȵ������Լ�����˵�API ���е�¼����¼�ɹ��󣬷����û�Tokenֱ�ӵ�¼���ƾͲ��ø÷�������
	 * 
	 * @param appKey
	 *            ������KEY
	 * @param appSecret
	 *            App Secret�������ƿ���̨ APP KEY ����ģ�������������ķ�������ȡ��
	 * @param uid
	 *            ���Լ�ƽ̨���û�ID
	 * @param userName
	 *            ���Լ�ƽ̨���û���
	 * @param headUrl
	 *            ���Լ�ƽ̨���û�ͷ����ַ·����
	 */
	void getUserToken(String appKey, String appSecret, String uid,
			String userName, String headUrl, IRongUserTokenCallback l);
}
