package com.yixin.nfyh.cloud.w;

/**
 * 用户医生接口
 * 
 * @author ChenRui
 * 
 */
public interface IUserDoctor
{
	/**
	 * 获取用户所在群的所有医生。
	 * 
	 * @param pid
	 *            病人Id
	 */
	void getUserGroupDoctor(String pid, IUserDoctorListener l);
	
	/**
	 * 病人的干预
	 * 
	 * @param uid
	 */
	void getIntervenes(String uid, IGetIntervenesLinstener l);
}
