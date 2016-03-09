package com.yixin.nfyh.cloud.bll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.data.IDict;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.Devices;
import com.yixin.nfyh.cloud.model.Dicts;
import com.yixin.nfyh.cloud.utils.ILog;
import com.yixin.nfyh.cloud.utils.LogUtil;

/**
 * 软件配置服务
 * 
 * @author MrChenrui
 * 
 */
public class ConfigServer {

	public static final String KEY_ENABLE_PULLMSG = "KEY_ENABLE_PULLMSG";// 消息推送
	public static final String KEY_AUTO_UPLOAD = "KEY_AUTO_UPLOAD"; // 自动上传
	public static final String KEY_ENABLE_TIXING = "KEY_ENABLE_TIXING"; // 闹钟提醒
	public static final String KEY_ENABLE_FALL = "KEY_ENABLE_FALL";// 跌倒监测
	public static final String KEY_ENABLE_AUTO_RUN = "KEY_ENABLE_AUTO_RUN"; // 自动开启体征测量服务
	public static final String KEY_ENABLE_DESKTOP = "KEY_ENABLE_DESKTOP";// 界面悬浮框
	public static final String KEY_FALL_BIND_NUMBER = "KEY_FALL_BIND_NUMBER"; // 跌倒绑定
	public static final String KEY_AUTO_TIPS = "KEY_AUTO_TIPS"; // 个性化告警
	public static final String KEY_AUTO_CONNECTED = "KEY_AUTO_CONNECTED"; // 设备连接

	public static final String KEY_DESKTOP_PHONE_LIST = "KEY_DESKTOP_PHONE_LIST"; // 联系人列表
	public static final String KEY_DESKTOP_EVENT_LIST = "KEY_DESKTOP_EVENT_LIST"; // 事件列表
	/**
	 * 客服电话
	 */
	public static final String KEY_PHONE_NUMBER = "KEY_PHONE_NUMBER";

	private IDict api;
	private ISignDevice apiDevice;
	private String tag = "ConfigServer";
	private ILog log = LogUtil.getLog();
	// private DesktopSOS desktopSos;
	private NfyhApplication application;

	// private Context context;

	public ConfigServer(Context context) {
		this.application = (NfyhApplication) context.getApplicationContext();
		api = NfyhCloudDataFactory.getFactory(context).getDict();
		apiDevice = NfyhCloudDataFactory.getFactory(context).getSignDevice();
	}

	/**
	 * 移除配置
	 * 
	 * @param key
	 * @param val
	 */
	public void removeConfig(String key, String val) {
		try {
			Dicts model = api.getDictsByCode(key, key, val);
			if (model != null) {
				api.delDicts(model);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 追加配置，不考虑重复
	 * 
	 * @param key
	 * @param val
	 */
	public void addConfig(String key, String val) {
		try {
			Dicts m = new Dicts();
			m.setCodeName(key);
			m.setDicValue(val);
			m.setName(key);
			api.addDicts(m, true);
		} catch (SQLException e) {
			e.printStackTrace();
			log.setExcetion(tag, e);
		}
	}

	/**
	 * 设置配置，如果有重复则会更新配置项
	 * 
	 * @param key
	 * @param val
	 */
	public void setConfig(String key, String val) {
		try {
			Dicts m = getConfigModel(key);

			if (m == null) {
				// 添加
				addConfig(key, val);
			} else

			{
				// 更新
				m.setCodeName(key);
				m.setDicValue(val);
				m.setName(key);
				api.updateDicts(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.setExcetion(tag, e);
		}
	}

	/**
	 * 获取字段表
	 * 
	 * @param key
	 * @return
	 */
	public Dicts getConfigModel(String key) {
		List<Dicts> results = getListConfigModels(key);
		if (results != null && results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取字符串的配置
	 * 
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		List<String> results = getListConfigs(key);
		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取字符串的配置，如果字段不存在则新建并赋予默认值
	 * 
	 * @param key
	 * @param defalutVal
	 * @return
	 */
	public String getConfig(String key, String defalutVal) {
		List<String> results = getListConfigs(key);
		if (results.size() > 0) {
			return results.get(0);
		} else {
			this.setConfig(key, defalutVal);
			return defalutVal;
		}
	}

	/**
	 * 获取布尔值的配置
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBooleanConfig(String key) {
		String val = getConfig(key);
		boolean result = false;
		try {
			result = Boolean.valueOf(val);
		} catch (Exception e) {
			try {
				result = Integer.valueOf(val) > 0;
			} catch (Exception ex) {
			}
		}
		return result;
	}

	/**
	 * 获取字典列表
	 * 
	 * @param key
	 * @return
	 */
	public List<Dicts> getListConfigModels(String key) {
		try {
			List<Dicts> dicts = api.getDictsByCode(key);
			return dicts;
		} catch (SQLException e) {
			e.printStackTrace();
			log.setExcetion(tag, e);
		}
		return null;
	}

	/**
	 * 获取字典列表 - 并将字典转化为字符串集合
	 * 
	 * @param key
	 * @return
	 */
	public List<String> getListConfigs(String key) {
		List<Dicts> dicts = getListConfigModels(key);
		if (dicts != null) {
			List<String> result = new ArrayList<String>();
			for (Dicts dict : dicts) {
				result.add(dict.getDicValue());
			}
			return result;
		} else {
			return null;
		}
	}

	public void enablePullMsg(boolean val) {
		setConfig(KEY_ENABLE_PULLMSG, val + "");
	}

	public void enableTixing(boolean val) {
		setConfig(KEY_ENABLE_TIXING, val + "");
	}

	public void enableFall(boolean val) {
		setConfig(KEY_ENABLE_FALL, val + "");
	}

	public void enavleAutoconnect(boolean val) {
		setConfig(KEY_AUTO_CONNECTED, val + "");
	}

	/**
	 * 个性化告警
	 * 
	 * @param val
	 */
	public void enableAutoTips(boolean val) {
		setConfig(KEY_AUTO_TIPS, val + "");
	}

	/**
	 * 自动上传
	 * 
	 * @param val
	 */
	public void enableAutoUpload(boolean val) {
		setConfig(KEY_AUTO_UPLOAD, val + "");
	}

	/**
	 * 是否启动悬浮框
	 * 
	 * @param isEnable
	 */
	public void enableDesktop(boolean isEnable) {
		if (isEnable) {
			// 开启
			application.showSOSinDesktop();
		} else {
			// 关闭
			application.removeSOSinDesktop();
		}

		setConfig(KEY_ENABLE_DESKTOP, isEnable + "");
	}

	/**
	 * 设置默认监测设备
	 * 
	 * @param devid
	 */
	public void setDefaultDevice(String devid) {
		try {
			Devices curDev = apiDevice.getCurrentDevices();
			curDev.setIsUsed(0); // 更新当前设备

			Devices dev = apiDevice.getDevicesByid(devid);
			if (dev != null) {
				dev.setIsUsed(1);
			}

			apiDevice.updateDevices(curDev);
			apiDevice.updateDevices(dev);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
