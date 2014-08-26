package com.yixin.nfyh.cloud.i;

import java.io.File;

/**
 * 照片上传接口
 * 
 * @author MrChenrui
 * 
 */
public interface IPhotoUpload
{
	/**
	 * 上传接口
	 * 
	 * @param cookie
	 *            身份验证
	 * @param categoryId
	 *            分类Id
	 * @param file
	 *            图片文件
	 */
	void upload(String cookie, String categoryId, File file);
}
