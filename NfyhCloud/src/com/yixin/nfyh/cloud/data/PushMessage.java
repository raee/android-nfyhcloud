package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.i.IPushMessageCallback;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 保存到本地的消息提醒
 * 
 * @author ChenRui
 * 
 */
public class PushMessage implements IPushMessage {

	private Dao<Messages, String> mDbMessage;
	private ILog log = LogUtil.getLog();
	private String tag = "PushMessage";

	// private String mUserId;

	public PushMessage(Context context) {
		try {
			mDbMessage = NfyhCloudDataBase.getDataOpenHelp(context)
					.getMessages();
		} catch (SQLException e) {
			log.setExcetion(tag, e);
		}
	}

	@Override
	public void setCookie(String cookie) {
	}

	@Override
	public void setUserId(String uid) {
		// mUserId = uid;
	}

	@Override
	public boolean hasNewMessage() {
		return getNewMessageCount() > 0;
	}

	@Override
	public int getNewMessageCount() {
		ArrayList<Messages> result = getMessage(true);
		return result == null ? 0 : result.size();
	}

	@Override
	public void setPushMessageListener(IPushMessageCallback l) {
	}

	@Override
	public void check() {

	}

	@Override
	public void save(Messages model) {
		try {
			if (model == null) {
				return;
			}

			if (exist(model)) {
				mDbMessage.update(model);
			} else {
				mDbMessage.create(model);
			}
		} catch (SQLException e) {
			log.setExcetion(tag, e);
		}
	}

	public boolean exist(Messages model) {
		try {
			boolean result = mDbMessage.queryForId(model.getMsgId()) != null;
			return result;
		} catch (SQLException e) {
			log.setExcetion(tag, e);
		}
		return false;
	}

	@Override
	public void delete(Messages model) {
		try {
			mDbMessage.delete(model);
		} catch (SQLException e) {
			log.setExcetion(tag, e);
		}
	}

	@Override
	public ArrayList<Messages> getMessage(boolean isReader) {
		try {
			List<Messages> result = null;
			QueryBuilder<Messages, String> builder = mDbMessage.queryBuilder();
			if (isReader) {
				// 获取未读的消息
				result = builder.orderBy("status", false)
						.orderBy("send_date", true).where().eq("status", "0")
						.query();
			} else {
				result = mDbMessage.queryForAll();
			}
			return (ArrayList<Messages>) result;
		} catch (SQLException e) {
			log.setExcetion(tag, e);
		}
		return null;
	}

}
