package com.yixin.nfyh.cloud.bll;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import com.rae.core.http.async.AsyncHttpClient;
import com.rae.core.http.async.AsyncHttpResponseHandler;
import com.rae.core.http.async.RequestParams;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.activity.UploadPhotoActivity;
import com.yixin.nfyh.cloud.adapter.PhotocategoryAdapter;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.i.IPhotoCategory;
import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.model.Photos;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.PhotoCategoryServer;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 院后照片逻辑处理
 * 
 * @author MrChenrui
 * 
 */
public class PhotoCategoryControl implements
		SoapConnectionCallback<List<Photocategory>>
{
	public static final String		ACTION_UPLOAD_PHOTO_ASYNC_SUCCESS		= "ACTION_UPLOAD_PHOTO_ASYNC_SUCCESS";
	public static final String		ACTION_UPLOAD_PHOTO_ASYNC_ERROR			= "ACTION_UPLOAD_PHOTO_ASYNC_ERROR";
	public static final String		ACTION_UPLOAD_PHOTO_ASYNC_PROGRESSING	= "ACTION_UPLOAD_PHOTO_ASYNC_PROGRESSING";

	private PhotocategoryAdapter	mAdapter;
	private Context					mContext;
	private PhotoCategoryServer		mPhotocategoryApi;
	private Users					mUser;
	private IPhotoCategory			mDbPhotocategoryApi;
	private ILog					log										= LogUtil
																					.getLog();
	private String					tag										= "PhotoCategoryControl";
	private PhotoCategoryListener	mPhotoCategoryEmptyListener;
	private String					mCurrentCategoryId						= "0";
	private AsyncHttpClient			mSyncClient								= new AsyncHttpClient();

	/**
	 * 没有数据时调用的接口
	 * 
	 * @author ChenRui
	 * 
	 */
	public interface PhotoCategoryListener
	{
		void onPhotocategoryEmpty(String categoryId);

		/**
		 * 回调
		 * 
		 * @param state
		 *            成功为1，否则为0
		 * @param categoryId
		 */
		void onPhotocategoryCallBack(int state, String categoryId);
	}

	public PhotoCategoryControl(Context context)
	{
		this.mContext = context;
		this.mPhotocategoryApi = NfyhWebserviceFactory.getFactory(context)
				.getPhotoCategory(); // webservice 接口
		this.mUser = new GlobalSetting(context).getUser(); // 当前用户
		this.mPhotocategoryApi.setCookie(this.mUser.getCookie()); // 设置身份验证
		this.mPhotocategoryApi.setUserId(this.mUser.getUid());
		this.mPhotocategoryApi.setmPhotocategoryListener(this); // 设置获取分类回调监听
		this.mDbPhotocategoryApi = NfyhCloudDataFactory.getFactory(context)
				.getPhotocategory(); // 数据库 接口
		this.mDbPhotocategoryApi.setUserId(mUser.getUid());
		this.mDbPhotocategoryApi.setCookie(mUser.getCookie());
	}

	/**
	 * 设置当获取分类为空时候的接口
	 * 
	 * @param l
	 */
	public void setPhotoCategoryListener(PhotoCategoryListener l)
	{
		this.mPhotoCategoryEmptyListener = l;
	}

	/**
	 * 获取webservice 接口
	 * 
	 * @return
	 */
	public IPhotoCategory getWebserviceApi()
	{
		return this.mDbPhotocategoryApi;
	}

	/**
	 * 获取分类
	 * 
	 * @param adapter
	 * @param categoryid
	 *            分类ID
	 * @param isFromNet
	 *            是否从云端下载
	 */
	public void getCategories(PhotocategoryAdapter adapter, String categoryid,
			boolean isFromNet)
	{
		this.mAdapter = adapter;
		this.mCurrentCategoryId = categoryid;

		// 服务器分类数据
		if (isFromNet)
		{
			mPhotocategoryApi.getCategories(categoryid); // 获取网络数据
			return;
		}

		// 本地分类数据
		List<Photocategory> data = mDbPhotocategoryApi
				.getCategories(categoryid);
		if (data != null && data.size() > 0)
		{
			this.mPhotoCategoryEmptyListener.onPhotocategoryCallBack(1,
					categoryid);
			refreshListView(data);
			Log.i("tt", "从本地加载分类");
			return;
		}
		else
		{
			mPhotocategoryApi.getCategories(categoryid); // 获取网络数据
		}

	}

	/**
	 * 获取分类，数据库不存在则从云端下载
	 * 
	 * @param adapter
	 * @param categoryId
	 */
	public void getCategories(PhotocategoryAdapter adapter, String categoryId)
	{
		getCategories(adapter, categoryId, false);
	}

	/**
	 * 获取数据库分类信息
	 * 
	 * @param categoryId
	 * @return
	 */
	public Photocategory getCategory(String categoryId)
	{
		return this.mDbPhotocategoryApi.getCategory(categoryId);
	}

	/**
	 * 更新Listview
	 * 
	 * @param data
	 */
	private void refreshListView(List<Photocategory> data)
	{
		this.mAdapter.dataList = data;
		this.mAdapter.notifyDataSetInvalidated();
		this.mAdapter.notifyDataSetChanged();
	}

	/**
	 * 添加分类
	 * 
	 * @param name
	 *            分类名称
	 * @param l
	 *            操作回调
	 */
	public void addCategory(String name,
			SoapConnectionCallback<WebServerException> l)
	{
		final Photocategory m = new Photocategory();
		m.setName(name);
		m.setCreateDate(new Date());
		m.setParentid("0"); // 根目录
		m.setUpdateDate(new Date());
		m.setUpdateTime(0);
		m.setUsers(mUser);
		mPhotocategoryApi.setmAddCategoryListener(l);
		mPhotocategoryApi.addCategory(m);
	}

	public void getPhotos(String categoryId,
			SoapConnectionCallback<List<Photos>> l)
	{
		mPhotocategoryApi.setmPhotosListener(l);
		mPhotocategoryApi.getPhotos(categoryId);
	}

	@Override
	public void onSoapConnectSuccess(List<Photocategory> data)
	{
		if (data.size() <= 0)
		{
			mPhotoCategoryEmptyListener
					.onPhotocategoryEmpty(this.mCurrentCategoryId);
			return;
		}
		for (Photocategory photocategory : data)
		{
			mDbPhotocategoryApi.addCategory(photocategory); // 再把服务器的数据添加到数据库中
		}
		refreshListView(data);
		mPhotoCategoryEmptyListener.onPhotocategoryCallBack(1,
				mCurrentCategoryId);
	}

	@Override
	public void onSoapConnectedFalid(WebServerException e)
	{
		this.mPhotoCategoryEmptyListener
				.onPhotocategoryEmpty(mCurrentCategoryId);
		refreshListView(new ArrayList<Photocategory>());
		log.error(tag, "调用失败：" + e.getMessage());
		mPhotoCategoryEmptyListener.onPhotocategoryCallBack(0,
				mCurrentCategoryId);
	}

	/**
	 * 跳转到上传的视图
	 * 
	 * @param uris
	 *            需要上传的图片路径
	 * @param categoryId
	 *            所在分类Id
	 */
	public void gotoUploadActivity(String categoryId, ArrayList<String> uris)
	{
		Intent intent = new Intent(this.mContext, UploadPhotoActivity.class);
		intent.putExtra("type", categoryId);
		intent.putStringArrayListExtra("data", uris);
		mContext.startActivity(intent);
	}

	private String getUploadUrl(String categoryId, String uri)
	{
		File file = new File(URI.create(uri));
		String fileName = DateFormat.format("yyyyMMddHHmmss",
				new Date(file.lastModified())).toString()
				+ "的照片";
		String url = mContext.getString(R.string.url_method_photo_upload)
				+ "&cookie=" + this.mUser.getCookie() + "&albumId="
				+ categoryId + "&Photodescribe=照片描述&Photoname" + fileName;
		return url;
	}

	public void uploadPhoto(String uri, String categoryId,
			AsyncHttpResponseHandler handler)
	{
		// AsyncFileHttpResponseHandler handler = new
		// AsyncFileHttpResponseHandler(
		// uri);
		try
		{
			String url = getUploadUrl(categoryId, uri);
			mSyncClient.setTimeout(60 * 1000);
			RequestParams params = new RequestParams();
			File file = new File(URI.create(uri));
			params.put("imgFile", file);
			mSyncClient.post(url, params, handler);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			handler.onFailure(0, null, null, e);
		}

	}

	/**
	 * 刷新分类数据
	 */
	public void refreshCategory()
	{
		// 删除分类数据
		this.mDbPhotocategoryApi.delCategory("0");

		// 重新获取服务器数据
		this.getCategories(mAdapter, "0");
	}

}
