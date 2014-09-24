// package com.yixin.nfyh.cloud.adapter;
//
// import java.sql.SQLException;
// import java.text.SimpleDateFormat;
// import java.util.Date;
// import java.util.List;
//
// import android.annotation.SuppressLint;
// import android.app.Activity;
// import android.util.Log;
// import android.view.View;
// import android.view.ViewGroup;
//
// import com.yixin.nfyh.cloud.R;
// import com.yixin.nfyh.cloud.model.SignTypes;
// import com.yixin.nfyh.cloud.model.UserSigns;
// import com.yixin.nfyh.cloud.ui.ListItemView;
// import com.yixin.nfyh.cloud.utils.ReflectUtil;
//
// /**
// * 具体的体征类型适配器
// *
// * @author Chenrui
// *
// */
// @SuppressLint("SimpleDateFormat")
// public class SignTypeItemListViewAdapter extends SignTypeItemBaseAdapterView
// {
// public SignTypeItemListViewAdapter(Activity context, List<SignTypes> datas,
// List<UserSigns> defaultValueDatas) throws SQLException {
// super(context);
// setDataList(datas);
// SignTypes dateSignType = new SignTypes();
// dateSignType.setName("测量时间");
// dateSignType.setDefaultValue(new
// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
// dateSignType.setTypeId("-1");
// dateSignType.setIsSign(0);
//
// this.datas.add(0, dateSignType);
// this.defaultValueDatas.add(0, null);
// }
//
// @Override
// public View getView(int location, View v, ViewGroup vg) {
//
// SignTypes m = datas.get(location);
// if (v == null) {
// ListItemView view = new ListItemView(this.context, null,
// R.style.Widget_Light_IOS_ListItem);
// int iconId = ReflectUtil.getDrawableId(R.drawable.class, m.getTypeIcon());
//
// view.setText(m.getName());
// view.setSubText(m.getDefaultValue());
// view.setIcon(iconId);
// view.setNextEnable(true);
// UserSigns usersign = this.defaultValueDatas.get(location);
// view.setTag(usersign);
// v = view;
// viewList.add(v);
// }
// return v;
// }
//
// @Override
// public void setValue(int postion, String value) {
//
// try {
// ListItemView v = (ListItemView) this.viewList.get(postion);
// v.setSubText(value);
// }
// catch (Exception e) {
// Log.e("SignTypeItemBaseAdapterView", "设置体征值失败");
// e.printStackTrace();
// }
// }
//
// @Override
// public void loadDefault() {
// for (View view : this.viewList) {
// view.setTag(null);
// }
// }
//}
