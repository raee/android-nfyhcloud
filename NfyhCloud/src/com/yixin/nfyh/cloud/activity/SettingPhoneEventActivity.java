package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.rui.framework.ui.RuiDialog;
import cn.rui.framework.utils.InputUtils;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.ConfigServer;
import com.yixin.nfyh.cloud.ui.EmpteyView;

/**
 * 
 * 设置-手机号码和事件绑定
 * 
 * @author MrChenrui
 * 
 */
public class SettingPhoneEventActivity extends BaseActivity {

	private String			type;

	private EditText		etValue;

	private ListView		lvValues;

	private Button			btnAdd;

	private List<String>	valList	= new ArrayList<String>();

	private ConfigServer	config;

	private ValueAdateper	adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_setting_phone_event);
		config = new ConfigServer(this);
		type = getIntent().getStringExtra(Intent.EXTRA_TEXT);
		initView();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		this.etValue = (EditText) this.findViewById(R.id.et_setting_phone_event);
		this.btnAdd = (Button) findViewById(R.id.btn_setting_phone_event);
		this.btnAdd.setOnClickListener(this);
		this.lvValues = (ListView) findViewById(R.id.lv_setting_phone_event);
		if (type.equals(ConfigServer.KEY_DESKTOP_PHONE_LIST)) {
			etValue.setHint("输入手机号码，客服手机号码前面加*号。");
		}
		else {
			etValue.setHint("如:我跌倒在家里了！");
		}
		btnAdd.setEnabled(false);
		this.etValue.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String value = s.toString().trim();
				// 电话号码校验
				if (value.startsWith("*") && type.equals(ConfigServer.KEY_DESKTOP_PHONE_LIST)) {
					btnAdd.setEnabled(true);
				}
				else if (InputUtils.isPhoneNumber(value) && type.equals(ConfigServer.KEY_DESKTOP_PHONE_LIST)) {
					btnAdd.setEnabled(true);
				}
				else if (InputUtils.isChinese(value) && value.length() > 1 && type.equals(ConfigServer.KEY_DESKTOP_EVENT_LIST)) {
					btnAdd.setEnabled(true);
				}
				else {
					btnAdd.setEnabled(false);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		// 获取数据
		this.valList = config.getListConfigs(type);
		EmpteyView emptyView = new EmpteyView(this, (ViewGroup) lvValues.getParent());
		emptyView.setText("这里空空如也，赶紧添加一条吧");
		this.lvValues.setEmptyView(emptyView);
		adapter = new ValueAdateper();
		this.lvValues.setAdapter(adapter);
		this.lvValues.setOnItemClickListener(adapter);
	}

	@Override
	public void onClick(View arg0) {
		String value = etValue.getText().toString().trim();
		if (value.length() < 5) {
			showMsg("长度不得小于5");
			return;
		}
		// 重复检查
		if (valList.contains(value)) {
			showMsg("已经存在：" + value);
			return;
		}
		try {
			// 客服手机号码
			if (value.startsWith("*")) {
				config.setConfig(ConfigServer.KEY_PHONE_NUMBER, value.replace("*", "")); // 添加客服
				Toast.makeText(this, "添加客服号码(" + value + ")成功！", Toast.LENGTH_SHORT).show();
				etValue.setText("");
				return;
			}

			this.valList.add(0, value);
			config.addConfig(type, value);
			adapter.notifyDataSetChanged();
			etValue.setText("");
		}
		catch (Exception e) {
			e.printStackTrace();
			showMsg("添加失败");
		}
	}

	private class ValueAdateper extends BaseAdapter implements OnItemClickListener {

		@Override
		public int getCount() {
			return valList.size();
		}

		@Override
		public Object getItem(int position) {
			return valList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.view_setting_phone_event_item, null);
			}
			TextView tv = (TextView) convertView;
			tv.setText(getItem(position).toString());
			return convertView;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final String val = getItem(position).toString();
			RuiDialog dialog = new RuiDialog(SettingPhoneEventActivity.this);
			dialog.setMessage("是否真的删除：" + val);
			dialog.setTitle("删除");
			dialog.setLeftButton("返回", null);
			dialog.setRightButton("删除", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					valList.remove(val);
					config.removeConfig(type, val);
					notifyDataSetChanged();
					dialog.dismiss();
				}
			});
			dialog.show();
		}
	}

	@Override
	protected String getActivityName() {
		if (type.equals(ConfigServer.KEY_DESKTOP_PHONE_LIST)) {
			return getString(R.string.setting_phone);
		}
		else {
			return getString(R.string.setting_event);
		}
	}
}
