package com.yixin.nfyh.cloud.w;

import java.util.List;

import com.yixin.nfyh.cloud.model.Users;

/**
 * 用户医生接口回调
 * 
 * @author ChenRui
 * 
 */
public interface IUserDoctorListener
{
	/**
	 * 获取所有医生成功。
	 * 
	 * @param doctors
	 */
	void onGetUserDoctorSuccess(List<Users> doctors);
	
	/**
	 * 获取医生失败
	 * 
	 * @param code
	 * @param msg
	 */
	void onGetUserDoctorError(int code, String msg);
	
	void onNotDoctor();
}
