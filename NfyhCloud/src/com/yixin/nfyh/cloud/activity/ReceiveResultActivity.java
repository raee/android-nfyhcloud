package com.yixin.nfyh.cloud.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yixin.monitors.sdk.model.SignDataModel;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.NfyhApplication;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.model.Users;

public class ReceiveResultActivity extends BaseActivity {
	private List<SignDataModel>	models;
	private Users				user;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitvity_recdata_result);
		user = ((NfyhApplication) getApplication()).getCurrentUser();
		models = (List<SignDataModel>) this.getIntent().getExtras().getSerializable(Intent.EXTRA_TEXT);
		for (SignDataModel m : models) {
			Log.i("tt", m.getDataName());
		}
	}
	
	@Override
	protected String getActivityName() {
		return user.getName() + "的测量数据";
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
}
