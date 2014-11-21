/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;
import com.yixin.nfyh.cloud.model.Dicts;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * @author MrChenrui
 * 
 */
public class DataQuery implements IUser, IDict {

	private static final String	TAG	= "DataQuery";
	private Dao<Users, String>	user;
	private Dao<Dicts, Long>	dicts;

	private ILog				log	= LogUtil.getLog();

	public DataQuery(Context context) throws SQLException {
		NfyhCloudDataOpenHelp db = NfyhCloudDataBase.getDataOpenHelp(context);

		this.user = db.getUsers();
		this.dicts = db.getDicts();
	}

	// 用户接口

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#getUser(java.lang.String)
	 */
	@Override
	public Users getUser(String uid) throws SQLException {
		return user.queryForId(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#getUserFriends(java.lang.String)
	 */
	@Override
	public List<Users> getUserFriends(String uid) throws SQLException {
		// 获取好友字段的用户为Uid的所有
		return user.queryBuilder().orderBy("marks", true).where().eq("friendid", uid).query();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yixin.nfyh.cloud.data.IUser#createUser(com.yixin.nfyh.cloud.model
	 * .Users)
	 */
	@Override
	public int createUser(Users user) throws SQLException {
		if (exitUser(user.getUsername())) { // 已经存在，则更新
			return updateUser(user);
		}
		user.setLoginDate(new Date());
		user.setHeadImage("");
		user.setHospital("南方医院");
		user.setLoginTime(0);
		user.setMarks(0);
		user.setRecTime(0);
		return this.user.create(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#delUser(java.lang.String)
	 */
	@Override
	public int delUser(String uid) throws SQLException {
		return this.user.deleteById(uid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#exitUser(java.lang.String)
	 */
	@Override
	public boolean exitUser(String username) throws SQLException {
		return this.user.queryBuilder().where().eq("username", username).countOf() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#addUserLoginTime()
	 */
	@Override
	public void addUserLoginTime(String uid) throws SQLException {
		Users m = getUser(uid);
		int count = m.getLoginTime() + 1;
		m.setLoginTime(count);
		this.updateUser(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#addUserRecordTime()
	 */
	@Override
	public void addUserRecordTime(String uid) throws SQLException {

		Users m = getUser(uid);
		int count = m.getRecTime() + 1;
		m.setRecTime(count);
		this.updateUser(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#updateLoginDate(java.util.Date)
	 */
	@Override
	public void updateLoginDate(String uid, Date date) {

		Users m;
		try {
			m = getUser(uid);
			m.setLoginDate(date);
			this.updateUser(m);
		}
		catch (SQLException e) {
			e.printStackTrace();
			log.warn(TAG, "更新登录时间失败！" + e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IUser#getUserRanking(java.lang.String)
	 */
	@Override
	public List<Users> getUserRanking(String uid) throws SQLException {
		// 获取已排序的好友列表
		List<Users> friends = this.getUserFriends(uid);

		// 把自己加到好友列表中
		Users curUser = this.getUser(uid);

		// 根据字段Marks进行冒泡排序
		for (int i = 0; i < friends.size(); i++) {
			Users friend = friends.get(i);
			if (friend != null && curUser.getMarks() > friend.getMarks()) {
				// 插入到当前位置
				friends.add(i, curUser);
				break;
			}
		}

		return friends;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yixin.nfyh.cloud.data.IUser#updateUser(com.yixin.nfyh.cloud.model
	 * .Users)
	 */
	@Override
	public int updateUser(Users m) throws SQLException {
		return user.update(m);
	}

	// 字典接口

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yixin.nfyh.cloud.data.IDict#addDicts(com.yixin.nfyh.cloud.model.Dicts
	 * )
	 */
	@Override
	public int addDicts(Dicts m) throws SQLException {
		// 检查重复
		Dicts dict = getDictsByCode(m.getCodeName(), null, null);
		if (dict != null) {
			dict.setDicValue(m.getDicValue());
			return updateDicts(dict); // 更新字典
		}

		return this.dicts.create(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yixin.nfyh.cloud.data.IDict#updateDicts(com.yixin.nfyh.cloud.model
	 * .Dicts)
	 */
	@Override
	public int updateDicts(Dicts m) throws SQLException {
		return this.dicts.update(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yixin.nfyh.cloud.data.IDict#delDicts(com.yixin.nfyh.cloud.model.Dicts
	 * )
	 */
	@Override
	public int delDicts(Dicts m) throws SQLException {
		return this.dicts.delete(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yixin.nfyh.cloud.data.IDict#getDictsByCode(java.lang.String)
	 */
	@Override
	public List<Dicts> getDictsByCode(String code) throws SQLException {
		return this.dicts.queryForEq("code_name", code);
	}

	@Override
	public Dicts getDictsByCode(String code, String key, String value) throws SQLException {
		Where<Dicts, Long> query = this.dicts.queryBuilder().where().eq("code_name", code);
		if (key != null) {
			query = query.and().eq("name", key);
		}
		if (value != null) {
			query = query.and().eq("dic_value", value);
		}

		return query.queryForFirst();
	}

	@Override
	public boolean login(String username, String pwd) {
		return true; // 离线登录返回真
		// try
		// {
		// List<Users> results = this.user.queryBuilder().where()
		// .eq("username", username).and().eq("pwd", pwd).query();
		// return results.size() > 0;
		// }
		// catch (SQLException e)
		// {
		// e.printStackTrace();
		// }
		// return false;
	}

}
