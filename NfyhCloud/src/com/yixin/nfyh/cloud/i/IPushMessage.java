package com.yixin.nfyh.cloud.i;

import java.util.ArrayList;

import com.yixin.nfyh.cloud.model.Messages;

/**
 * 消息推送接口
 * 
 * @author Chenrui
 * 
 */
public interface IPushMessage extends IAuthentication
{

	/**
	 * 是否有新的消息
	 * 
	 * @return
	 */
	public boolean hasNewMessage();

	/**
	 * 获取新消息的总数
	 * 
	 * @return 未读消息总数
	 */
	public int getNewMessageCount();

	/**
	 * 设置回调监听
	 * 
	 * @param l
	 */
	public void setPushMessageListener(IPushMessageCallback l);

	/**
	 * 检查是否有新消息
	 */
	public void check();

	/**
	 * 保存消息
	 */
	public void save(Messages model);

	/**
	 * 删除消息
	 */
	public void delete(Messages model);

	/**
	 * 获取消息
	 * 
	 * @param isReader
	 *            是否只获取未读的消息
	 */
	public ArrayList<Messages> getMessage(boolean isReader);
}
