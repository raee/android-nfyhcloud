/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.List;

import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.model.SignRange;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;

/**
 * 设备体征监测相关接口
 * 
 * @author MrChenrui
 * 
 */
public interface ISignDevice
{

	// ============ 体征相关接口 ================//

	/**
	 * 添加或更新体征记录,如果体征记录不存在则新增否则更新数据
	 * 
	 * @param m
	 * @throws SQLException
	 * @return 返回更新后的数据
	 */
	UserSigns addOrUpdateUserSign(UserSigns m) throws SQLException;

	/**
	 * 根据体征类型获取用户的体征数据
	 * 
	 * @param types
	 *            体征类型
	 * @param uid
	 *            用户ID
	 * @return
	 */
	List<UserSigns> getUserSignByType(String uid, SignTypes types)
			throws SQLException;

	/**
	 * 获取最近的用户体征
	 * 
	 * @param uid
	 * @param types
	 * @return
	 * @throws SQLException
	 */
	UserSigns getLastUserSignsByType(String uid, SignTypes types)
			throws SQLException;

	/**
	 * 获取所有的体征测量类型,已经分组的。
	 * 
	 * @return
	 */
	List<SignTypes> getSignTypes() throws SQLException;

	/**
	 * 获取分组下所有的体征类型，主要！
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<SignTypes> getGroupSignTypes(SignTypes m) throws SQLException;

	/**
	 * 根据ID获取一条体征类型
	 * 
	 * @param signId
	 *            类型ID
	 * @return
	 * @throws SQLException
	 */
	SignTypes getSignType(String signId) throws SQLException;

	/**
	 * 获取体征提示，根据体征类型
	 * 
	 * @param m
	 * @return
	 * @throws SQLException
	 */
	List<SignTips> getSignTips(UserSigns m) throws SQLException;

	/**
	 * 获取用户体征的选择范围
	 * 
	 * @return 第一个为最小值，第二个为最大值
	 * @throws SQLException
	 */
	double[] getUserSignRange(String typeId) throws SQLException;

	/**
	 * 获取体征类型的体征范围
	 * 
	 * @param typeId
	 * @return
	 * @throws SQLException
	 */
	List<SignRange> getSignRange(String typeId) throws SQLException;

	/**
	 * 获取用户体征的选择范围
	 * 
	 * @return 第一个为最小值，第二个为最大值
	 * @throws SQLException
	 */
	String getUserSignRangeArray(String typeId) throws SQLException;

	/**
	 * 获取体征的额外参数，如测试时间段
	 * 
	 * @param m
	 *            体征类型
	 * @return
	 * @throws SQLException
	 */
	List<SignTypes> getSignParames(SignTypes m) throws SQLException;

	// ============ 设备相关接口 ================//

	/**
	 * 获取当前正在监测的设备
	 * 
	 * @return
	 */
	Devices getCurrentDevices() throws SQLException;

	Devices getDevicesByid(String devId) throws SQLException;

	/**
	 * 更新正在使用的设备
	 * 
	 * @param d
	 */
	int updateDevices(Devices d) throws SQLException;

	/**
	 * 设置新的当前监测设备
	 * 
	 * @param devid
	 *            设备Id
	 * @return
	 * @throws SQLException
	 */
	int setCurrentDevices(String devid) throws SQLException;

	List<Devices> getDevices() throws SQLException;

	/**
	 * 获取体征范围
	 * 
	 * @param typeId
	 *            体征类型
	 * @param type
	 *            范围类型
	 * @return
	 * @throws SQLException
	 */
	List<SignRange> getUserSignRange(String typeId, String value)
			throws SQLException;

	/**
	 * 获取体征类型，根据类型名称
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	SignTypes getSignTypeByName(String name) throws SQLException;

	/**
	 * 获取体征提示，根据范围ID
	 * 
	 * @param rangeid范围ID
	 * @return
	 * @throws SQLException
	 */
	List<SignTips> getSignTipsBySignRangeId(String rangeid) throws SQLException;

	/**
	 * 获取所有没有同步的数据
	 * 
	 * @return
	 */
	List<UserSigns> getUserSignsNotSysnc();

	/**
	 * 更新体征数据
	 * 
	 * @param signs
	 */
	void uploadUserSign(List<UserSigns> signs);
}
