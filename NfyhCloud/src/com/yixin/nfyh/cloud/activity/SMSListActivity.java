package com.yixin.nfyh.cloud.activity;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.rui.framework.ui.RuiDialog;
import cn.rui.framework.ui.WebViewerActivity;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ApiController;
import com.yixin.nfyh.cloud.model.Users;
import com.yixin.nfyh.cloud.model.view.MydcViewModel;
import com.yixin.nfyh.cloud.ui.ListItemView;
import com.yixin.nfyh.cloud.ui.TimerProgressDialog;
import com.yixin.nfyh.cloud.w.IManyidu;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 满意度调查界面
 * 
 * @author ChenRui
 * 
 */
public class SMSListActivity extends BaseActivity implements
		OnItemClickListener, SoapConnectionCallback<List<MydcViewModel>> {

	private ListView lv;

	private Users user;

	private List<MydcViewModel> dataList;

	private ListAdapter adapter;

	private TimerProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_list);
		findView();
		user = ((NfyhApplication) getApplication()).getCurrentUser();
		IManyidu mydApi = NfyhWebserviceFactory.getFactory(this).getManyidu();
		mydApi.setOnConnectonCallback(this);
		mydApi.setCookie(user.getCookie());
		mydApi.getList();
		dialog = new TimerProgressDialog(this);
		dialog.setMessage("正在获取数据..");
		dialog.show();

		String type = getIntent().getStringExtra("type");
		String id = getIntent().getStringExtra("id");
		if ("view".equals(type) && id != null) {
			gotoSmsView(id); // 前去调查
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	protected void findView() {
		this.lv = (ListView) this.findViewById(R.id.lv_sms_list);
		TextView tv = new TextView(this);
		tv.setText("您没有满意度调查");
		this.lv.setEmptyView(tv);
		this.adapter = new ListAdapter();
		this.lv.setOnItemClickListener(this);
	}

	@Override
	protected String getActivityName() {
		return "满意度调查";
	}

	@Override
	protected void setLinsener() {
	}

	private class ListAdapter extends BaseAdapter {

		@Override
		public View getView(int location, View v, ViewGroup arg2) {
			MydcViewModel m = dataList.get(location);
			ListItemView itemView = null;
			if (v == null) {
				itemView = new ListItemView(SMSListActivity.this, null,
						R.style.setting_item);
			} else {
				itemView = (ListItemView) v;
			}
			itemView.setText(m.getTitle());
			itemView.setSubText(m.getCreateDate());
			if (m.getState() == 0) {
				itemView.setNextEnable(true);
			}
			return itemView;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public Object getItem(int location) {
			return dataList.get(location);
		}

		@Override
		public int getCount() {
			return dataList.size();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> vg, View v, int location, long arg3) {
		MydcViewModel m = dataList.get(location);
		if (m.getState() == 1) {
			new RuiDialog.Builder(this).buildTitle("感谢您的调查")
					.buildMessage("该调查您已经做完，感谢您的参与").buildButton("返回", null)
					.show();
			return;
		}
		gotoSmsView(m.getId());
	}

	// 前往调查
	private void gotoSmsView(String id) {
		Intent intent = new Intent(SMSListActivity.this,
				WebViewerActivity.class);
		Uri uri = Uri.parse(ApiController.get().getSmsUrl() + "?lid=" + id
				+ "&pid=" + user.getUid());
		intent.putExtra(WebViewerActivity.EXTRA_COOKIE, user.getCookie());
		intent.setData(uri);
		startActivity(intent);
		finish();
	}

	@Override
	public void onSoapConnectSuccess(List<MydcViewModel> data) {
		this.dataList = data;
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		dialog.dismiss();
	}

	@Override
	public void onSoapConnectedFalid(WebServerException e) {
		dialog.dismiss();
		showMsg("获取到数据！");
	}
}
