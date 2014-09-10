package com.yixin.nfyh.cloud.data;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.yixin.nfyh.cloud.model.Clocks;

/**
 * 闹钟
 * 
 * @author MrChenrui
 * 
 */
public class ClockImpl implements IClock
{
	private Dao<Clocks, Long>	dbclock;

	public ClockImpl(Context context) throws SQLException
	{
		super();
		NfyhCloudDataOpenHelp db = NfyhCloudDataBase.getDataOpenHelp(context);
		this.dbclock = db.getClocks();
	}

	@Override
	public List<Clocks> getAll() throws SQLException
	{
		return dbclock.queryBuilder().orderBy("start_date", true).query();
	}

	@Override
	public Clocks addClock(Clocks model) throws SQLException
	{
		if (dbclock.create(model) > 0)
		{
			return getLastClock();
		}
		return model;
	}

	private Clocks getLastClock() throws SQLException
	{
		long id = dbclock.queryRawValue("select max(clockid) from clocks ");
		return dbclock.queryBuilder().where().eq("clockid", id).queryForFirst();
	}

	@Override
	public boolean update(Clocks model) throws SQLException
	{
		return dbclock.update(model) > 0;
	}

	@Override
	public boolean deleteClock(Clocks model) throws SQLException
	{
		return dbclock.delete(model) > 0;
	}

}
