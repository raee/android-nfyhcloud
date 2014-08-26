//package com.yixin.nfyh.cloud.service;
//
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.util.Log;
//import cn.rui.framework.utils.SoapCallback;
//
//import com.j256.ormlite.dao.Dao;
//import com.yixin.nfyh.cloud.R;
//import com.yixin.nfyh.cloud.bll.AlarmDataHandle;
//import com.yixin.nfyh.cloud.bll.Dbhelper;
//import com.yixin.nfyh.cloud.entitys.UsersOrders;
//
//public class ExeOrderRecordWebService implements SoapCallback {
//
//	/**
//	 * 回调给界面接口 1表示更新成功
//	 * 
//	 * @author zhulin
//	 * 
//	 */
//	public interface cellRemindService {
//		public void updateCellResult(int result, String msg);
//	}
//
//	private Context mContext;
//	private String dbName;
//	private cellRemindService cell;
//	int methodType = 0;
//
//	public ExeOrderRecordWebService(Context context,cellRemindService cell) {
//		this.mContext = context;
//		this.cell = cell;
//		dbName = mContext.getResources().getString(R.string.dbname);
//	}
//
//	/**
//	 * 用用户id查询出用户的医嘱信息
//	 */
//	public void GetUsersOrdersListByUid(String Uid) {
//		synchronized (this) {
//			methodType = 1;// 当前方法的标识
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("uid", Uid);
//			SoapService soapService = new SoapService(mContext);
//			soapService.setParams(params);
//			soapService.setCallbackListener(this);
//			soapService.call(mContext.getResources().getString(
//					R.string.GetUsersOrdersListByUid));
//		}
//
//	}
//	/**     
//	 * 修改医嘱内容
//	 */
//	public void EditUsersOrdersContent(String content){
//		synchronized (this) {
//			methodType = 2;// 当前方法的标识
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("ordersJson", content);
//			SoapService soapService = new SoapService(mContext);
//			soapService.setParams(params);
//			soapService.setCallbackListener(this);
//			soapService.call(mContext.getResources().getString(
//					R.string.editUserOrders));
//		}
//	}
//	/**
//	 * 添加一条医嘱
//	 * @param json
//	 */
//	public void AddUsersOrders(String json){
//		synchronized (this) {
//			methodType = 3;// 当前方法的标识
//			Map<String, Object> params = new HashMap<String, Object>();
//			SoapService soapService = new SoapService(mContext);
//			params.put("addJson", json);
//			soapService.setParams(params);
//			soapService.setCallbackListener(this);
//			soapService.call(mContext.getResources().getString(
//					R.string.addUserOrders));
//		}
//	}
//	
//	/**
//	 * soap回调数据
//	 */
//	@Override
//	public void onSoapResponse(Object response) {
//		GetUsersOrdersByService(response);
////		switch (methodType) {
////		case 1:
////			GetUsersOrdersByService(response);
////			break;
////		case 2:
////			cell.updateCellResult(1, "");
////			break;
////		case 3:
////			cell.updateCellResult(1, "");
////			break;
////		default:
////			break;
////		}
//	}
//
//	/**
//	 * soap请求错误
//	 */
//	@Override
//	public void onSoapError(int code, String msg) {
//		cell.updateCellResult(code, msg);// webService更新出错
//	}
//
//	/**
//	 * 获得医嘱信息
//	 * 
//	 * @param response
//	 */
//	private void GetUsersOrdersByService(Object response) {
//		String respondStr = (String) response;
//		Log.v("zl", respondStr);
//		if ("".equals(respondStr)) {
//			return;
//		}
//		try {
//			boolean bool = false;
//			JSONArray jsonArray = new JSONArray(respondStr);
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				if (resolveUsersOrders(jsonObject)) {
//					bool = true;
//				}
//			}
//			if (bool) {
//				Log.i("onSoapResponse", "" + bool);
//				cell.updateCellResult(1, "");// webService更新成功
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 解析医嘱记录和保存数据
//	 */
//	@SuppressLint("SimpleDateFormat")
//	private boolean resolveUsersOrders(JSONObject jsonObject) {
//		AlarmDataHandle handle = new AlarmDataHandle();
//		boolean bool = false;
//		try {
//			UsersOrders usersOrders = handle.resolveUserOrder(jsonObject);
//			// 保存数据
//			boolean saveUO = saveUsersOrders(usersOrders);
//			if (saveUO) {
//				bool = true;
//			} else {
//				bool = false;
//			}
//			Log.i("resolveExeOrdersRecord", "" + bool);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return bool;
//	}
//
//
//	/**
//	 * 保存医嘱信息
//	 */
//	private boolean saveUsersOrders(UsersOrders uo) {
//
//		try {
//			Dao<UsersOrders, Integer> UsersordersDao = Dbhelper.getInstance(
//					mContext, dbName).getDao(UsersOrders.class);
//			if (!UsersordersDao.idExists(uo.getorderid())) {
//				UsersordersDao.create(uo);// 插入实体数据
//				return true;
//			}else{
//				UsersordersDao.update(uo);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//
//}
