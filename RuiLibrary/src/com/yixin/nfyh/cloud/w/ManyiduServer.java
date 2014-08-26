package com.yixin.nfyh.cloud.w;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import cn.rui.framework.utils.CommonUtil;

import com.rui.framework.R;
import com.yixin.nfyh.cloud.model.view.MydcViewModel;

public class ManyiduServer extends WebserverConnection implements IManyidu
{

	private SoapConnectionCallback<List<MydcViewModel>>	mListener;

	private String										mCookie;

	public ManyiduServer(Context context)
	{
		super(context);
	}

	@Override
	public List<MydcViewModel> getList()
	{
		NfyhSoapConnection<List<MydcViewModel>> conn = new NfyhSoapConnection<List<MydcViewModel>>(
				context);
		conn.setParams("cookie", mCookie);
		conn.setParser(new ManyiduParser());
		conn.setonSoapConnectionCallback(mListener);
		conn.request(context.getString(R.string.soap_method_myd));
		return null;
	}

	private class ManyiduParser implements
			IWebserverParser<List<MydcViewModel>>
	{

		@Override
		public List<MydcViewModel> parser(String json)
		{
			try
			{
				List<MydcViewModel> mydcList = new ArrayList<MydcViewModel>();
				JSONArray arrs = new JSONArray(json);
				for (int i = 0; i < arrs.length(); i++)
				{
					JSONObject item = arrs.getJSONObject(i);
					if (item == null)
						continue;
					JSONObject m = item.getJSONObject("BaseSmsList");
					if (m == null)
						continue;
					int id = m.getInt("Id");
					String title = m.getString("Title");
					String createDate = CommonUtil.getDateStringFromJson(m
							.getString("Createdate"));
					String collecttime = m.getString("Collecttime");
					int state = item.getInt("Status");
					title += state == 1 ? "(已经调查)" : "";
					// 实体
					MydcViewModel model = new MydcViewModel();
					model.setId(id + "");
					model.setCollecttime(collecttime);
					model.setCreateDate(createDate);
					model.setTitle(title);
					model.setState(state);
					mydcList.add(model);
				}
				if (mydcList.size() <= 0)
					return null; // 没有数据返回空
				return mydcList;
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return null;
		}
	}

	@Override
	public void setOnConnectonCallback(
			SoapConnectionCallback<List<MydcViewModel>> l)
	{
		this.mListener = l;
	}

	@Override
	public void setCookie(String cookie)
	{
		this.mCookie = cookie;
	}

	@Override
	public void setUserId(String uid)
	{
		// 没用
	}
}
