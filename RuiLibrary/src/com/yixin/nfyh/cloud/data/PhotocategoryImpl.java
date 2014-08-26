package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.yixin.nfyh.cloud.i.IAuthentication;
import com.yixin.nfyh.cloud.i.IPhotoCategory;
import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.model.Photos;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 院后照片
 * 
 * @author MrChenrui
 * 
 */
public class PhotocategoryImpl implements IPhotoCategory, IAuthentication
{

	private Dao<Photocategory, String>	category;
	private Dao<Photos, String>			photo;
	private String						uid;
	private ILog						log	= LogUtil.getLog();
	private String						tag	= "PhotocategoryImpl";

	public PhotocategoryImpl(Context context)
	{
		try
		{
			NfyhCloudDataOpenHelp db = NfyhCloudDataBase
					.getDataOpenHelp(context);
			this.category = db.getPhotocategory();
		}
		catch (SQLException e)
		{
			log.setExcetion(tag, e);
		}
	}

	@Override
	public List<Photocategory> getCategories(String id)
	{
		if (noUid())
		{
			return null;
		}

		Where<Photocategory, String> where = this.category.queryBuilder()
				.where();
		List<Photocategory> datas = null;
		try
		{

			datas = where.eq("_Users_uid", this.uid).and().eq("parentid", id)
					.query(); // 找子类

			return datas;
		}
		catch (SQLException e)
		{
			log.setExcetion(id, e);
			return null;
		}

	}

	@Override
	public List<Photos> getPhotos(String categoryId)
	{
		if (noUid())
			return null;
		try
		{
			return this.photo.queryBuilder().where().eq("_Users_uid", this.uid)
					.and().eq("categoryid", categoryId).query();
		}
		catch (SQLException e)
		{
			log.setExcetion(categoryId, e);
			return null;
		}
	}

	@Override
	public void addCategory(Photocategory category)
	{
		try
		{
			// 如果存在则更新
			if (this.category.queryForId(category.getPid()) != null)
			{
				this.category.update(category);
			}
			else
			{
				this.category.create(category);
			}
		}
		catch (SQLException e)
		{
			log.setExcetion(tag, e);
		}
	}

	@Override
	public void updateCategory(Photocategory category)
	{
		try
		{
			this.category.update(category);
		}
		catch (SQLException e)
		{
			log.setExcetion(tag, e);
		}
	}

	@Override
	public void setCookie(String cookie)
	{
		// 不用实现
	}

	@Override
	public void setUserId(String uid)
	{
		if (TextUtils.isEmpty(uid))
		{
			return;
		}
		this.uid = uid;
	}

	/**
	 * 监测用户ID
	 * 
	 * @return
	 */
	private boolean noUid()
	{
		boolean result = TextUtils.isEmpty(uid);
		if (result)
		{
			log.error(tag, "getCategories 没有传入用户Id！");
		}
		return result;
	}

	@Override
	public void delCategory(String categoryid)
	{
		try
		{
			this.category.deleteBuilder().delete();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Photocategory getCategory(String id)
	{
		try
		{
			return this.category.queryForId(id);
		}
		catch (SQLException e)
		{
			log.setExcetion(tag, e);
		}
		return null;
	}
}
