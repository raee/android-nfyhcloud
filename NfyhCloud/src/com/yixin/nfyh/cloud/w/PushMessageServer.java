package com.yixin.nfyh.cloud.w;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.rui.framework.utils.CommonUtil;

import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.IPushMessage;
import com.yixin.nfyh.cloud.i.IPushMessageCallback;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.model.view.MessageManager;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 消息推送接口
 * 
 * @author Chenrui
 * 
 */
public class PushMessageServer extends WebserverConnection implements IPushMessage, SoapConnectionCallback<List<Messages>>, IWebserverParser<List<Messages>>
{
	
	private NfyhSoapConnection<List<Messages>>	mConn;
	
	private IPushMessageCallback				mListener;
	
	private ILog								log	= LogUtil.getLog();
	
	private IPushMessage						mDbPushMessage;
	
	public PushMessageServer(Context context)
	{
		super(context);
		this.mConn = new NfyhSoapConnection<List<Messages>>(context);
		
		this.mConn.setonSoapConnectionCallback(this);
		this.mConn.setParser(this);
		mDbPushMessage = NfyhCloudDataFactory.getFactory(context).getPushMessage();
	}
	
	@Override
	public void setCookie(String cookie)
	{
		super.setCookie(cookie);
		this.mConn.setParams("cookie", cookie);
	}
	
	@Override
	public boolean hasNewMessage()
	{
		return false;
	}
	
	@Override
	public int getNewMessageCount()
	{
		return 0;
	}
	
	@Override
	public void setPushMessageListener(IPushMessageCallback l)
	{
		this.mListener = l;
	}
	
	@Override
	public void check()
	{
		String methodName = context.getString(R.string.soap_method_message);
		this.mConn.request(methodName);
	}
	
	@Override
	public void save(Messages model)
	{
	}
	
	@Override
	public void delete(Messages model)
	{
	}
	
	@Override
	public ArrayList<Messages> getMessage(boolean isReader)
	{
		return null;
	}
	
	@Override
	public void onSoapConnectSuccess(List<Messages> data)
	{
		for (int index = 0; index < data.size(); index++)
		{
			Messages item = data.get(index);
			
			mListener.onPushNewMessage(data.size(), index, item, MessageManager.getIntent(context, item));
			mDbPushMessage.save(item); // 保存到数据库中
		}
	}
	
	@Override
	public void onSoapConnectedFalid(WebServerException e)
	{
		if (e.getCode() == WebServerException.CODE_NULL_DATA)
		{
			log.debug("PushMessageServer", e.getMessage());
			return; //空数据不处理
		}
		log.error("PushMessageServer", e.getMessage());
	}
	
	@Override
	public List<Messages> parser(String json)
	{
		List<Messages> result = new ArrayList<Messages>();
		try
		{
			JSONArray arr = new JSONArray(json);
			if (arr.length() <= 0) return null; // 没有数据
			for (int i = 0; i < arr.length(); i++)
			{
				JSONObject obj = arr.getJSONObject(i);
				Messages item = new Messages();
				item.setMsgId(obj.getString("Id"));
				item.setTypeCode(obj.getString("Type"));
				item.setTitle(obj.getString("Title"));
				item.setContent(obj.getString("Content"));
				item.setSummary(obj.getString("Summary"));
				item.setStatus(0);
				item.setSendDate(CommonUtil.getDateString(obj.getString("Senddate")));
				// Intent
				item.setIntentName(obj.getString("Intentclassname"));
				item.setIntentCategroy(obj.getString("Intentcategory"));
				item.setIntentDate(obj.getString("Intentdata"));
				item.setIntentExtra(obj.getString("Intentextra"));
				item.setIntentFlag(obj.getString("Intentflags"));
				item.setIntentAction(obj.getString("Intentaction"));
				result.add(item);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
