/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.yixin.nfyh.cloud.model.Users;

/**
 * 用户相关接口
 * 
 * @author MrChenrui
 * 
 */
public interface IUser
{

	/**
	 * 获取单个用户
	 * 
	 * @param uid
	 *            用户ID
	 * @return
	 * @throws SQLException
	 */
	Users getUser(String uid) throws SQLException;

	/**
	 * 本地登录
	 * 
	 * @param username
	 * @param pwd
	 * @return
	 */
	boolean login(String username, String pwd);

	/**
	 * 更新用户信息
	 * 
	 * @param m
	 * @return
	 * @throws SQLException
	 */
	int updateUser(Users m) throws SQLException;

	/**
	 * 获取用户的好友,排序为总积分由高到低
	 * 
	 * @param uid
	 *            用户Id
	 * @return
	 * @throws SQLException
	 */
	List<Users> getUserFriends(String uid) throws SQLException;

	/**
	 * 创建一个用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	int createUser(Users user) throws SQLException;

	/**
	 * 删除一个用户
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	int delUser(String uid) throws SQLException;

	/**
	 * 用户是否存在
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	boolean exitUser(String uid) throws SQLException;

	/**
	 * 用户今日登录次数自增
	 * 
	 * @throws SQLException
	 */
	void addUserLoginTime(String uid) throws SQLException;

	/**
	 * 添加用户今日测量次数
	 * 
	 * @throws SQLException
	 */
	void addUserRecordTime(String uid) throws SQLException;

	/**
	 * 更新用户登录时间
	 * 
	 * @param date
	 */
	void updateLoginDate(String uid, Date date);

	/**
	 * 获取用户排名,根据由高到低进行排序
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	List<Users> getUserRanking(String uid) throws SQLException;
}
