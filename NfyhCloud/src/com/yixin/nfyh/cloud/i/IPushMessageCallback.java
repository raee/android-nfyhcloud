package com.yixin.nfyh.cloud.i;

import android.content.Intent;

import com.yixin.nfyh.cloud.model.Messages;

/**
 * 消息推送回调
 * 
 * @author Chenrui
 * 
 */
public interface IPushMessageCallback
{

	/**
	 * 当有新消息的时候回调
	 * 
	 * @param model
	 */
	public void onPushNewMessage(int count, int index, Messages model,
			Intent intent);
}
