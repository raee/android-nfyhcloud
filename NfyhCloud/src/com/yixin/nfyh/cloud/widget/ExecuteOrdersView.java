// package com.yixin.nfyh.cloud.widget;
//
// import java.sql.SQLException;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Set;
//
// import android.annotation.SuppressLint;
// import android.content.Context;
// import android.content.DialogInterface;
// import android.content.Intent;
// import android.content.res.Resources.NotFoundException;
// import android.util.Log;
// import android.view.LayoutInflater;
// import android.view.View;
// import android.widget.LinearLayout;
// import cn.rui.framework.ui.RuiDialog;
//
// import com.j256.ormlite.dao.Dao;
// import com.yixin.nfyh.cloud.EditAlarmActivity;
// import com.yixin.nfyh.cloud.R;
// import com.yixin.nfyh.cloud.bll.AlarmManager;
// import com.yixin.nfyh.cloud.bll.Dbhelper;
// import com.yixin.nfyh.cloud.bll.GlobalSetting;
// import com.yixin.nfyh.cloud.entitys.ExeOrderRecord;
// import com.yixin.nfyh.cloud.entitys.UsersOrders;
//
// public class ExecuteOrdersView extends LinearLayout implements
// android.content.DialogInterface.OnClickListener {
// private Context mContext;
//
// private LinearLayout llOrdersList;
//
// HashMap<Integer, List<UsersOrders>> uoHm = new HashMap<Integer,
// List<UsersOrders>>();
//
// public ExecuteOrdersView(Context context) {
// super(context);
// mContext = context;
// LayoutInflater.from(mContext).inflate(R.layout.view_list_remind, this);
// llOrdersList = (LinearLayout) findViewById(R.id.ll_orders_list);
//
// // 开启提醒服务
// Intent mIntent = new Intent(mContext, AlarmManager.class);
// mContext.startService(mIntent);
// setOrdersContent();// 设置医嘱内容
//
// /*AlarmManager am = AlarmManager.getInstence(mContext);
// am.alarm(1, new Date(System.currentTimeMillis() + 30 * 1000),
// "xxxxooooo");
// am.alarm(1, new Date(System.currentTimeMillis() + 10 * 1000),
// "xxxx1111");
// am.alarm(1, new Date(System.currentTimeMillis() + 20 * 1000),
// "xxxx2222");*/
// }
//
// /**
// * 设置医嘱的内容
// */
// @SuppressLint("SimpleDateFormat")
// private void setOrdersContent() {
// Dbhelper dbHelper = Dbhelper.getInstance(mContext, mContext
// .getResources().getString(R.string.dbname));
// Dao<ExeOrderRecord, Integer> orderRecordDao;
// Dao<UsersOrders, Integer> UsersOrdersDao;
// // List<Exeorderrecord> litExeOrderRecord = null;
// List<UsersOrders> litUsersOrders = null;
// try {
// orderRecordDao = dbHelper.getDao(ExeOrderRecord.class);
// UsersOrdersDao = dbHelper.getDao(UsersOrders.class);
// // litExeOrderRecord = orderRecordDao.queryForAll();// 查找出全部医嘱
// String uid = new GlobalSetting(mContext).getUid();
// litUsersOrders = UsersOrdersDao.query(UsersOrdersDao.queryBuilder()
// .where().eq(UsersOrders.PATIENTID, uid).prepare());
// if (litUsersOrders == null) {
// return;
// }
// } catch (SQLException e) {
// e.printStackTrace();
// return;
// }
//
// if (litUsersOrders == null || litUsersOrders.size() == 0) {
// Log.v("cr", "没有医嘱");
//
// return;
// }
// // 记录同一个时段的item
// for (int i = 0; i < litUsersOrders.size(); i++) {
// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
// 小写的mm表示的是分钟
// UsersOrders usersOrderItem = litUsersOrders.get(i);
//
// // 添加提醒
// // 判断医嘱执行的时间和当前时间相距多少天
// try {
// if (sdf.parse(usersOrderItem.getendtime()).getTime() > System
// .currentTimeMillis()) // 未执行医嘱 添加提醒
// {
// //待定
// // this.addAlarm(usersOrderItem.getperform_schedule(),
// // usersOrderItem);
// this.addAlarm("20:00",
// usersOrderItem);
// }
// } catch (ParseException e) {
// e.printStackTrace();
// }
// arrangeOrders(usersOrderItem);
// if (i == (litUsersOrders.size() - 1)) {
// Set keys = uoHm.keySet();
// Iterator<Integer> iterator = keys.iterator();
// while (iterator.hasNext()) {
// Integer key = iterator.next();
// addOrdersItemView(key, uoHm.get(key),
// usersOrderItem.getorderid(),
// usersOrderItem.getcontext());
// }
// }
// }
//
// }
//
// /**
// * 排列医嘱提醒
// */
// @SuppressLint("SimpleDateFormat")
// private void arrangeOrders(UsersOrders usersOrderItem) {
// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
// 小写的mm表示的是分钟
// int fDay = 0;
// try {
// fDay = judgeDayIfEqual(sdf.parse(usersOrderItem.getstarttime()),
// sdf.parse(usersOrderItem.getendtime()));
//
// } catch (Exception e) {
// e.printStackTrace();
// }
// if (uoHm.containsKey(fDay)) {
// List<UsersOrders> litUo = uoHm.get(fDay);
// litUo.add(usersOrderItem);
// uoHm.remove(fDay);
// uoHm.put(fDay, litUo);
// } else {
// List<UsersOrders> litUo = new ArrayList<UsersOrders>();
// litUo.add(usersOrderItem);
// uoHm.put(fDay, litUo);
// }
//
// }
//
// /**
// * 添加提醒功能
// */
// @SuppressLint("SimpleDateFormat")
// private void addAlarm(String ExeTime, UsersOrders UsersOrders) {
// Date exeDate = null;
// SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
// try {
// exeDate = dateFormat.parse(ExeTime);
// } catch (ParseException e) {
// e.printStackTrace();
// }
// if (exeDate.getTime() > System.currentTimeMillis()) {
// AlarmManager am = AlarmManager.getInstence(mContext);
// /*am.alarm(UsersOrders.getOrderid(), exeDate,
// UsersOrders.getContext());*/
// }
//
// }
//
// /**
// * 判断时间是否在同一个时间断
// */
// private int judgeDayIfEqual(Date startDate, Date endDate) {
//
// if (compareCurruteDate(endDate) < 0) {
// return -1;
// } else if (compareCurruteDate(startDate) <= 0
// && compareCurruteDate(endDate) >= 0) {
// return 0;
// } else if (compareCurruteDate(startDate) > 0
// && compareCurruteDate(startDate) < 7) {
// return 1;
// } else if (compareCurruteDate(startDate) > 7
// && compareCurruteDate(startDate) > 30) {
// return 2;
// } else if (compareCurruteDate(startDate) > 30
// && compareCurruteDate(startDate) > 178) {
// return 3;
// } else if (compareCurruteDate(startDate) > 178
// && compareCurruteDate(startDate) > 365) {
// return 4;
// }
// return 5;
// }
//
// /**
// * 和当前时间对比相差天数
// *
// * @return
// */
// private int compareCurruteDate(Date compareDate) {
// if (compareDate == null) {
// return -1;
// }
// int compareDay = (int) ((compareDate.getTime() - System
// .currentTimeMillis()) / (1000 * 60 * 60 * 24));
// return compareDay;
// }
//
// /**
// * 添加到listView
// *
// * @param listEor
// */
// private void addOrdersItemView(int fDay, List<UsersOrders> listEor,
// int ordersExecuteType, String content) {
// String exceedStr = this.getExceedConten(fDay);
// AlarmCategoryTitle act = new AlarmCategoryTitle(mContext, null);
// act.setTvOne(exceedStr);
// act.setTvTwo(String.valueOf(listEor.size()) + "条提醒");
// llOrdersList.addView(act);
// for (int i = (listEor.size() - 1); i > -1; i--) {
// UsersOrders usersorder = listEor.get(i);
// // 增加item
// ViewItemRemind oli = new ViewItemRemind(mContext);
// oli.setTvOrdersContent(usersorder.getcontext());
// oli.setTvOrdersExecuteTime(usersorder.getendtime(),
// usersorder.getexecutetime());
// oli.setTag(usersorder);
// oli.setOrdersType(ordersExecuteType);
// oli.setOnLongClickListener(new olOnLongClickListener());
// oli.setOnClickListener(new olOnClickListener());
// if (fDay < 0) {
// oli.setViewEnable(true);
// }
// llOrdersList.addView(oli);
// }
// }
//
// /**
// * 提醒的点击事件
// *
// * @author Administrator
// *
// */
// private class olOnClickListener implements OnClickListener {
//
// @Override
// public void onClick(View v) {
// UsersOrders usersorder = (UsersOrders) v.getTag();
// Intent mIntent = new Intent();
// mIntent.putExtra("uoID", usersorder.getorderid());
// mIntent.setClass(mContext, EditAlarmActivity.class);
// mContext.startActivity(mIntent);
// }
//
// }
//
// /*
// * 长按删除
// */
// private class olOnLongClickListener implements OnLongClickListener {
//
// @Override
// public boolean onLongClick(View v) {
// Log.i("onLongClick", "item remind LongClick!");
// RuiDialog dialog = new RuiDialog(mContext);
// dialog.setTitle("删除");
// dialog.setLeftButton("是", ExecuteOrdersView.this);
// dialog.setRightButton("否", ExecuteOrdersView.this);
// dialog.setMessage("是否删除提醒?");
// dialog.setTag(v);
// dialog.show();
// return true;
// }
//
// }
//
// /**
// * 取得超过的天数的描述
// *
// * @param fDay
// * @return
// */
// private String getExceedConten(int fDay) {
// String exceedStr = "";
// if (fDay < 0) {
// exceedStr = "错过";
// } else if (fDay == 0) {
// exceedStr = "今天";
// } else if (fDay < 7) {
// exceedStr = "7天内";
// } else if (fDay < 30) {
// exceedStr = "一个月内";
// } else if (fDay < 178) {
// exceedStr = "半年内";
// } else if (fDay < 365) {
// exceedStr = "一年内";
// } else {
// exceedStr = "一年后";
// }
// return exceedStr;
// }
//
// @Override
// public void onClick(DialogInterface dialog, int which) {
// Log.i("dialog which = ", "" + which);
// RuiDialog log = (RuiDialog) dialog;
// ViewItemRemind vir = (ViewItemRemind) log.getTag();
// switch (which) {
// case -2: // 是
// deleteItemView(vir);
// break;
// case -3: // 否
//
// break;
// default:
// break;
// }
// dialog.cancel();
// }
//
// /**
// * 删除子视图
// */
// private void deleteItemView(ViewItemRemind vir) {
// // 删除正在的提醒
// AlarmManager al = AlarmManager.getInstence(mContext);
// ExeOrderRecord exeOrderRecord = (ExeOrderRecord) vir.getTag();
// al.deleteAlarm(exeOrderRecord.getorderid());
// // 在数据库中删除数据
// try {
// Dao<ExeOrderRecord, Integer> erDao = Dbhelper.getInstance(mContext,
// mContext.getResources().getString(R.string.dbname)).getDao(
// ExeOrderRecord.class);
// int deleteResult = erDao.delete(exeOrderRecord);
// } catch (NotFoundException e) {
// e.printStackTrace();
// } catch (SQLException e) {
// e.printStackTrace();
// }
// llOrdersList.removeView(vir);// 删除视图
// }
//
//}
