/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.List;

import com.yixin.nfyh.cloud.model.Dicts;

/**
 * 字典相关接口
 * 
 * @author MrChenrui
 * 
 */
public interface IDict
{

	/**
	 * 添加一个字典
	 * 
	 * @param m
	 * @throws SQLException
	 */
	int addDicts(Dicts m) throws SQLException;

	/**
	 * 更新一条字典
	 * 
	 * @param m
	 * @throws SQLException
	 */
	int updateDicts(Dicts m) throws SQLException;

	/**
	 * 删除一条字典
	 * 
	 * @param m
	 * @throws SQLException
	 */
	int delDicts(Dicts m) throws SQLException;

	/**
	 * 通过字典代码获取所有数据
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	List<Dicts> getDictsByCode(String code) throws SQLException;

	/**
	 * 通过字典代码获取单条数据
	 * 
	 * @param code
	 * @param key
	 *            键，为空则不查询
	 * @param value
	 *            值，为空则不查询
	 * @return
	 * @throws SQLException
	 */
	Dicts getDictsByCode(String code, String key, String value)
			throws SQLException;
}
