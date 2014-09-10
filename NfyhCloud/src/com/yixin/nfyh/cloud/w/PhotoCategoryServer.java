package com.yixin.nfyh.cloud.w;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yixin.nfyh.cloud.R;
import android.content.Context;
import android.text.TextUtils;
import cn.rui.framework.utils.CommonUtil;

import com.yixin.nfyh.cloud.i.IPhotoCategory;
import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.model.Photos;
import com.yixin.nfyh.cloud.model.Users;

/**
 * 照片传输接口
 * 
 * @author MrChenrui
 * 
 */
public class PhotoCategoryServer extends WebserverConnection implements
		IPhotoCategory
{

	private String										cookie;
	private SoapConnectionCallback<List<Photocategory>>	mPhotocategoryListener;
	private SoapConnectionCallback<List<Photos>>		mPhotosListener;
	private SoapConnectionCallback<WebServerException>	mAddCategoryListener;

	public PhotoCategoryServer(Context context)
	{
		super(context);
	}

	@Override
	public List<Photocategory> getCategories(String id)
	{
		if (TextUtils.isEmpty(id))
		{
			id = "0";
		}
		String methodName = context.getString(R.string.soap_method_category);
		NfyhSoapConnection<List<Photocategory>> conn = new NfyhSoapConnection<List<Photocategory>>(
				context);
		conn.setParser(new ParserPhotoCatetgory());
		conn.setParams("cookie", cookie);
		conn.setParams("albumId", id);
		conn.setonSoapConnectionCallback(mPhotocategoryListener);
		conn.request(methodName);
		return null;
	}

	@Override
	public List<Photos> getPhotos(String categoryId)
	{
		String methodName = context.getString(R.string.soap_method_photo_list);
		NfyhSoapConnection<List<Photos>> conn = new NfyhSoapConnection<List<Photos>>(
				context);
		conn.setParser(new ParserPhotos());
		conn.setParams("cookie", cookie);
		conn.setParams("albumId", categoryId);
		conn.setonSoapConnectionCallback(mPhotosListener);
		conn.request(methodName);
		return null;
	}

	/**
	 * 照片分类解析
	 * 
	 * @author MrChenrui
	 * 
	 */
	private class ParserPhotoCatetgory implements
			IWebserverParser<List<Photocategory>>
	{

		@Override
		public List<Photocategory> parser(String json)
		{
			List<Photocategory> result = new ArrayList<Photocategory>();

			try
			{
				JSONArray arr = new JSONArray(json);
				for (int i = 0; i < arr.length(); i++)
				{
					JSONObject obj = arr.getJSONObject(i);
					Photocategory model = new Photocategory();
					int parentId = obj.getInt("Parentaid");
					Users user = new Users();
					user.setUid(obj.getString("Uid"));
					model.setName(obj.getString("Albumname"));
					model.setPid(obj.getInt("Id") + "");
					model.setCreateDate(CommonUtil.getDateFromJson(obj
							.getString("Createdate")));
					model.setUpdateDate(CommonUtil.getDateFromJson(obj
							.getString("Modifydate")));
					model.setParentid(obj.getInt("Parentaid") + "");
					model.setUsers(user);

					if (parentId != 0)
					{
						model.setParentid(parentId + "");
					}
					result.add(model);
				}

				return result;
			}
			catch (JSONException e)
			{
				log.setExcetion(tag, e);
			}

			return null;
		}
	}

	/**
	 * 照片解析
	 * 
	 * @author MrChenrui
	 * 
	 */
	private class ParserPhotos implements IWebserverParser<List<Photos>>
	{

		@Override
		public List<Photos> parser(String json)
		{
			try
			{
				List<Photos> result = new ArrayList<Photos>();
				JSONArray arr = new JSONArray(json);
				for (int i = 0; i < arr.length(); i++)
				{
					JSONObject obj = arr.getJSONObject(i);
					Photos model = new Photos();
					model.setPhotoId(obj.getInt("Id") + "");
					model.setCreateDate(CommonUtil
							.getDateFromJson("Createdate"));
					model.setImagesrc(obj.getString("Photourl"));
					result.add(model);
				}
				return result;
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return null;
		}

	}

	private class AddCategoryParser implements
			IWebserverParser<WebServerException>
	{

		@Override
		public WebServerException parser(String json)
		{
			return new WebServerException(1);
		}

	}

	@Override
	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	@Override
	public void setUserId(String uid)
	{
		// 不用实现
	}

	@Override
	public void addCategory(Photocategory category)
	{
		try
		{
			// [{"Id":"41","Albumname":"测试相册名称","Describe":"描述","Sortcode":"0"}]
			JSONArray arr = new JSONArray();

			JSONObject obj = new JSONObject();
			obj.put("Albumname", category.getName());
			obj.put("Id", "0");
			obj.put("Describe", category.getName());
			arr.put(obj);
			String json = arr.toString();
			NfyhSoapConnection<WebServerException> conn = new NfyhSoapConnection<WebServerException>(
					context);
			conn.setParams("cookie", cookie);
			conn.setParams("json", json);
			conn.setParser(new AddCategoryParser());
			conn.setonSoapConnectionCallback(this.mAddCategoryListener);
			conn.request(context.getString(R.string.soap_method_category_add));
		}
		catch (JSONException e)
		{
			log.setExcetion(tag, e);
			this.mPhotocategoryListener
					.onSoapConnectedFalid(new WebServerException("数据解析错误"));
		}

	}

	@Override
	public void updateCategory(Photocategory category)
	{
		// TODO
	}

	public void setmPhotocategoryListener(
			SoapConnectionCallback<List<Photocategory>> mPhotocategoryListener)
	{
		this.mPhotocategoryListener = mPhotocategoryListener;
	}

	public void setmPhotosListener(
			SoapConnectionCallback<List<Photos>> mPhotosListener)
	{
		this.mPhotosListener = mPhotosListener;
	}

	public void setmAddCategoryListener(
			SoapConnectionCallback<WebServerException> mAddCategoryListener)
	{
		this.mAddCategoryListener = mAddCategoryListener;
	}

	@Override
	public void delCategory(String categoryid)
	{
		// 不用实现

	}

	@Override
	public Photocategory getCategory(String id)
	{
		return null;// 不用实现
	}

}
