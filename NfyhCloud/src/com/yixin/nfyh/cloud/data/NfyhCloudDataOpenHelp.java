package com.yixin.nfyh.cloud.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.model.Dicts;
import com.yixin.nfyh.cloud.model.MarksDetail;
import com.yixin.nfyh.cloud.model.MarksRole;
import com.yixin.nfyh.cloud.model.Messages;
import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.model.Photos;
import com.yixin.nfyh.cloud.model.SignRange;
import com.yixin.nfyh.cloud.model.SignReport;
import com.yixin.nfyh.cloud.model.SignTips;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.model.UserSigns;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

public class NfyhCloudDataOpenHelp extends OrmLiteSqliteOpenHelper {
	
	private static String				databaseName	= "nfyh.db";
	
	private static String				TAG				= "NfyhCloudDataOpenHelp";
	
	private ILog						log				= LogUtil.getLog();
	
	private Context						context;
	
	private Dao<Users, String>			UsersDao;
	
	private Dao<Devices, String>		DevicesDao;
	
	private Dao<Dicts, Long>			DictsDao;
	
	private Dao<MarksRole, Integer>		MarksRoleDao;
	
	private Dao<MarksDetail, Long>		MarksDetailDao;
	
	private Dao<Photocategory, String>	PhotocategoryDao;
	
	private Dao<Photos, String>			PhotosDao;
	
	private Dao<SignTypes, String>		SignTypesDao;
	
	private Dao<SignRange, Long>		SignRangeDao;
	
	private Dao<UserSigns, Long>		UserSignsDao;
	
	private Dao<SignTips, Long>			SignTipsDao;
	
	private Dao<SignReport, String>		SignReportDao;
	
	private Dao<Messages, String>		MessagesDao;
	
	public NfyhCloudDataOpenHelp(Context context) {
		super(context, databaseName, null, 104);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource conn) {
		try {
			// 创建表
			TableUtils.createTable(conn, Users.class);
			TableUtils.createTable(conn, Devices.class);
			TableUtils.createTable(conn, Dicts.class);
			TableUtils.createTable(conn, MarksRole.class);
			TableUtils.createTable(conn, MarksDetail.class);
			TableUtils.createTable(conn, Photocategory.class);
			TableUtils.createTable(conn, Photos.class);
			TableUtils.createTable(conn, SignTypes.class);
			TableUtils.createTable(conn, SignRange.class);
			TableUtils.createTable(conn, UserSigns.class);
			TableUtils.createTable(conn, SignTips.class);
			TableUtils.createTable(conn, SignReport.class);
			TableUtils.createTable(conn, Messages.class);
			initData(db);
		}
		catch (java.sql.SQLException e) {
			log.error(TAG, "数据库创建失败！" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(SQLiteDatabase db) {
		try {
			InputStream inStream = context.getAssets().open("init.sql");
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			String[] sqls = outStream.toString().split(";");
			for (String sql : sqls) {
				//log.info(TAG, "正在执行sql语句：" + sql);
				try {
					if (sql != null && sql.length() > 1) db.execSQL(sql);
				}
				catch (Exception e) {
					log.error(TAG, "执行Sql语句错误：" + sql + ";\r\n" + e.getMessage());
					continue;
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			log.error(TAG, "初始化数据库数据发生错误：" + e.getMessage());
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource conn, int old, int version) {
		// 删除表
		try {
			TableUtils.dropTable(conn, Users.class, false);
			TableUtils.dropTable(conn, Devices.class, false);
			TableUtils.dropTable(conn, Dicts.class, false);
			TableUtils.dropTable(conn, MarksRole.class, false);
			TableUtils.dropTable(conn, MarksDetail.class, false);
			TableUtils.dropTable(conn, Photocategory.class, false);
			TableUtils.dropTable(conn, Photos.class, false);
			TableUtils.dropTable(conn, SignTypes.class, false);
			TableUtils.dropTable(conn, SignRange.class, false);
			TableUtils.dropTable(conn, UserSigns.class, false);
			TableUtils.dropTable(conn, SignTips.class, false);
			TableUtils.dropTable(conn, SignReport.class, false);
			TableUtils.dropTable(conn, Messages.class, false);
			onCreate(db, conn); // 重新创建
		}
		catch (SQLException e) {
			log.error(TAG, "删除表失败失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取users
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<Users, String> getUsers() throws SQLException {
		if (this.UsersDao == null) {
			UsersDao = getDao(Users.class);
		}
		return UsersDao;
	}
	
	/**
	 * 获取devices
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<Devices, String> getDevices() throws SQLException {
		if (this.DevicesDao == null) {
			DevicesDao = getDao(Devices.class);
		}
		return DevicesDao;
	}
	
	/**
	 * 获取dicts
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<Dicts, Long> getDicts() throws SQLException {
		if (this.DictsDao == null) {
			DictsDao = getDao(Dicts.class);
		}
		return DictsDao;
	}
	
	/**
	 * 获取marks_role
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<MarksRole, Integer> getMarksRole() throws SQLException {
		if (this.MarksRoleDao == null) {
			MarksRoleDao = getDao(MarksRole.class);
		}
		return MarksRoleDao;
	}
	
	/**
	 * 获取marks_detail
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<MarksDetail, Long> getMarksDetail() throws SQLException {
		if (this.MarksDetailDao == null) {
			MarksDetailDao = getDao(MarksDetail.class);
		}
		return MarksDetailDao;
	}
	
	/**
	 * 获取Photocategory
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<Photocategory, String> getPhotocategory() throws SQLException {
		if (this.PhotocategoryDao == null) {
			PhotocategoryDao = getDao(Photocategory.class);
		}
		return PhotocategoryDao;
	}
	
	/**
	 * 获取photos
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<Photos, String> getPhotos() throws SQLException {
		if (this.PhotosDao == null) {
			PhotosDao = getDao(Photos.class);
		}
		return PhotosDao;
	}
	
	/**
	 * 获取sign_types
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<SignTypes, String> getSignTypes() throws SQLException {
		if (this.SignTypesDao == null) {
			SignTypesDao = getDao(SignTypes.class);
		}
		return SignTypesDao;
	}
	
	/**
	 * 获取sign_range
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<SignRange, Long> getSignRange() throws SQLException {
		if (this.SignRangeDao == null) {
			SignRangeDao = getDao(SignRange.class);
		}
		return SignRangeDao;
	}
	
	/**
	 * 获取user_signs
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<UserSigns, Long> getUserSigns() throws SQLException {
		if (this.UserSignsDao == null) {
			UserSignsDao = getDao(UserSigns.class);
		}
		return UserSignsDao;
	}
	
	/**
	 * 获取sign_tips
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<SignTips, Long> getSignTips() throws SQLException {
		if (this.SignTipsDao == null) {
			SignTipsDao = getDao(SignTips.class);
		}
		return SignTipsDao;
	}
	
	/**
	 * 获取sign_report
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<SignReport, String> getSignReport() throws SQLException {
		if (this.SignReportDao == null) {
			SignReportDao = getDao(SignReport.class);
		}
		return SignReportDao;
	}
	
	/**
	 * 获取messages
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Dao<Messages, String> getMessages() throws SQLException {
		if (this.MessagesDao == null) {
			MessagesDao = getDao(Messages.class);
		}
		return MessagesDao;
	}
}
