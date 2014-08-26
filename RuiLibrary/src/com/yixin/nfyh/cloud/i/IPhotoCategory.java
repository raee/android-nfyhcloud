package com.yixin.nfyh.cloud.i;

import java.util.List;

import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.model.Photos;

/**
 * 院后照片接口
 * 
 * @author MrChenrui
 * 
 */
public interface IPhotoCategory extends IAuthentication
{
	/**
	 * 获取所有分类
	 * 
	 * @param cookie
	 *            身份验证
	 * @param id
	 *            分类ID，该参数为0则获取根目录下的分类
	 * @return 没有返回空
	 */
	List<Photocategory> getCategories(String id);

	/**
	 * 获取分类信息
	 * 
	 * @param id
	 *            分类Id
	 * @return
	 */
	Photocategory getCategory(String id);

	/**
	 * 获取分类下的照片
	 * 
	 * @param cookie
	 * @param categoryId
	 *            分类ID
	 */
	List<Photos> getPhotos(String categoryId);

	/**
	 * 新增分类
	 * 
	 * @param category
	 */
	void addCategory(Photocategory category);

	/**
	 * 更新分类
	 * 
	 * @param category
	 */
	void updateCategory(Photocategory category);

	/**
	 * 删除分类
	 * 
	 * @param categoryid
	 *            分类Id
	 */
	void delCategory(String categoryid);

}
