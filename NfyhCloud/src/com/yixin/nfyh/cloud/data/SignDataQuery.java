/**
 * 
 */
package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.model.SignRange;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.model.view.DialogViewModel;

/**
 * @author MrChenrui
 * 
 */
public class SignDataQuery implements ISignDevice {
	private Dao<UserSigns, Long>	usersign;
	private Dao<Devices, String>	dev;
	private Dao<SignTypes, String>	signTypesDao;
	private Dao<SignTips, Long>		signTipsDao;
	private Dao<SignRange, Long>	signRangeDao;
	private String					tag	= "SignDataQuery";
	private IUser					mDbUser;
	
	public SignDataQuery(Context context) throws SQLException {
		NfyhCloudDataOpenHelp db = NfyhCloudDataBase.getDataOpenHelp(context);
		
		this.usersign = db.getUserSigns();
		this.dev = db.getDevices();
		this.signTypesDao = db.getSignTypes();
		this.signTipsDao = db.getSignTips();
		this.signRangeDao = db.getSignRange();
		mDbUser = new DataQuery(context);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yixin.nfyh.cloud.data.ISignDevice#getSignTypes()
	 */
	@Override
	public List<SignTypes> getSignTypes() throws SQLException {
		QueryBuilder<SignTypes, String> builder = signTypesDao.queryBuilder();
		return builder.orderBy("order_id", true).where().isNull("p_typeid").query();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#getUserSignByType(java.lang.String,
	 * com.yixin.nfyh.cloud.model.SignTypes)
	 */
	@Override
	public List<UserSigns> getUserSignByType(String uid, SignTypes types) throws SQLException {
		Where<UserSigns, Long> where = this.usersign.queryBuilder().where();
		where.eq("_SignTypes_typeId", types.getTypeId());
		where.and();
		return where.eq("_Users_uid", uid).query();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yixin.nfyh.cloud.data.ISignDevice#getCurrentDevices()
	 */
	@Override
	public Devices getCurrentDevices() throws SQLException {
		return this.dev.queryBuilder().where().eq("isused", 1).query().get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#updateDevices(com.yixin.nfyh.cloud
	 * .model.Devices)
	 */
	@Override
	public int updateDevices(Devices d) throws SQLException {
		return this.dev.update(d);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#setCurrentDevices(java.lang.String)
	 */
	@Override
	public int setCurrentDevices(String devid) throws SQLException {
		Devices devinfo = this.getCurrentDevices();
		Devices tagdev = this.dev.queryForEq("devid", devid).get(0);
		
		if (devinfo != null && tagdev != null) {
			// 关闭当前的设备
			devinfo.setIsUsed(1);
			tagdev.setIsUsed(1);
			if (this.updateDevices(devinfo) > 0) { return this.updateDevices(tagdev); }
		}
		
		return 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#getSignParames(com.yixin.nfyh.cloud
	 * .model.SignTypes)
	 */
	@Override
	public List<SignTypes> getSignParames(SignTypes m) throws SQLException {
		QueryBuilder<SignTypes, String> builder = signTypesDao.queryBuilder();
		return builder.orderBy("order_id", true).where().eq("p_typeid", m.getTypeId()).and().eq("is_sign", "0").query();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yixin.nfyh.cloud.data.ISignDevice#getSignType(java.lang.String)
	 */
	@Override
	public SignTypes getSignType(String signId) throws SQLException {
		return signTypesDao.queryBuilder().where().eq("typeId", signId).queryForFirst();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#getSignTips(com.yixin.nfyh.cloud
	 * .model.SignTypes)
	 */
	@Override
	public List<SignTips> getSignTips(UserSigns m) throws SQLException {
		SignTypes types = m.getSignTypes();
		if (types.getPTypeid() != 0) { return null; } // 不是体征
		Where<SignTips, Long> where = signTipsDao.queryBuilder().where();
		where.eq("_SignTypes_typeId", types.getTypeId()).query();
		where.and();
		where.raw(m.getSignValue() + " between left_range and right_range");
		return where.query();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#getGroupSignTypes(java.lang.String)
	 */
	@Override
	public List<SignTypes> getGroupSignTypes(SignTypes m) throws SQLException {
		QueryBuilder<SignTypes, String> builder = signTypesDao.queryBuilder();
		return builder.orderBy("order_id", true).where().eq("p_typeid", m.getTypeId()).query();
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#addOrUpdateUserSign(com.yixin.nfyh
	 * .cloud.model.UserSigns)
	 */
	@Override
	public UserSigns addOrUpdateUserSign(UserSigns m) throws SQLException {
		UserSigns result = m;
		int count = 0;
		Date date = m.getRecDate() == null ? new Date() : m.getRecDate();
		m.setRecDate(date);
		if (m.getRecordId() == 0) {
			count = this.usersign.create(m); // 创建
			// 获取新增后的数据
			UserSigns inserted = this.getLastUserSignsByType(m.getUsers().getUid(), m.getSignTypes());
			m.setRecordId(inserted == null ? 0 : inserted.getRecordId());
		}
		else {
			count = this.usersign.update(m);
		}
		if (count < 1) Log.i(tag, "新增或更新用户体征数据失败！");
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.yixin.nfyh.cloud.data.ISignDevice#getLastUserSignsByType(java.lang
	 * .String, com.yixin.nfyh.cloud.model.SignTypes)
	 */
	@Override
	public UserSigns getLastUserSignsByType(String uid, SignTypes types) throws SQLException {
		
		long id = usersign.queryRawValue("select max(recordid) from user_signs where groupid='" + types.getPTypeid() + "' and _signtypes_typeid='" + types.getTypeId() + "' and  rec_date = (select max(rec_date) from user_signs)");
		UserSigns result = usersign.queryForId(id);
		try {
			if (result != null && types.getIsSign() == 0) {
				DialogViewModel dm = new DialogViewModel();
				String jsonString = this.getUserSignRangeArray(types.getTypeId());
				dm.setDatas(jsonString);
				
				String val = result.getSignValue();
				if (val.contains(".")) {
					float value = Float.valueOf(val);
					dm.setCurrentItem((int) value);
				}
				else {
					dm.setCurrentItem(Integer.valueOf(result.getSignValue()));
				}
				
				dm.setCurrentItem(0);
				String value = dm.getDatas().get(dm.getCurrentItem());
				result.setSignValue(value);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public double[] getUserSignRange(String typeId) throws SQLException {
		List<SignRange> datas = signRangeDao.queryBuilder().where().eq("range_type", "0").and().eq("_SignTypes_typeId", typeId).query();
		if (datas.isEmpty() || datas.size() < 1) {
			return new double[] { 0, 1 };
		}
		else {
			SignRange m = datas.get(0);
			double left = m.getLeftRange();
			double right = m.getRightRange();
			return new double[] { left, right };
		}
	}
	
	@Override
	public String getUserSignRangeArray(String typeId) throws SQLException {
		List<SignRange> datas = signRangeDao.queryBuilder().where().eq("range_type", 0).and().eq("_SignTypes_typeId", typeId).query();
		
		if (datas.size() > 0) {
			return datas.get(0).getRangeArr();
		}
		else {
			return "[]";
		}
	}
	
	@Override
	public List<SignRange> getUserSignRange(String typeId, String value) throws SQLException {
		/*
		 * select * from sign_range where typeid=1001 and 120>left_range and
		 * 120<right_range
		 */
		List<SignRange> result = signRangeDao.queryBuilder().where().eq("_SignTypes_typeId", typeId).and().le("left_range", value).and().ge("right_range", value).and().notIn("range_type", 0).query();
		
		return result;
	}
	
	@Override
	public List<SignTips> getSignTipsBySignRangeId(String rangeid) throws SQLException {
		List<SignTips> result = signTipsDao.queryBuilder().where().eq("_signrange_rangeid", rangeid).query();
		
		return result;
	}
	
	@Override
	public List<SignRange> getSignRange(String typeId) throws SQLException {
		List<SignRange> result = signRangeDao.queryBuilder().where().eq("_SignTypes_typeId", typeId).query();
		return result;
	}
	
	@Override
	public List<Devices> getDevices() throws SQLException {
		return dev.queryForAll();
	}
	
	@Override
	public Devices getDevicesByid(String devId) throws SQLException {
		return dev.queryForId(devId);
	}
	
	@Override
	public SignTypes getSignTypeByName(String name) throws SQLException {
		List<SignTypes> signs = signTypesDao.queryBuilder().where().eq("name", name).and().isNotNull("p_typeid").query();
		if (signs == null || signs.size() < 1) // 没有找到
		{
			// 返回第一个
			return this.getSignTypes().get(0);
		}
		else {
			return signs.get(0);
		}
		
	}
	
	@Override
	public List<UserSigns> getUserSignsNotSysnc() {
		try {
			return usersign.queryBuilder().where().eq("isSync", 0).and().not().eq("_Users_uid", "0").query();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<UserSigns> getGuestUserSignsNotSysnc() {
		try {
			return usersign.queryBuilder().where().eq("isSync", 0).and().eq("_Users_uid", "0").query();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void convertGuestData(String uid) {
		try {
			Users user = mDbUser.getUser(uid);
			List<UserSigns> datas = getGuestUserSignsNotSysnc();
			for (UserSigns item : datas) {
				item.setIsSync(0);
				item.setUsers(user);
				this.usersign.update(item);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void uploadUserSign(List<UserSigns> signs) {
		try {
			for (UserSigns m : signs) {
				this.usersign.update(m);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
