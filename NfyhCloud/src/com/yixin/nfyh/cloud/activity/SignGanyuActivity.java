package com.yixin.nfyh.cloud.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.AdapterViewHelper;
import com.yixin.nfyh.cloud.adapter.GanyuListViewAdapter;
import com.yixin.nfyh.cloud.model.GanyuInfo;
import com.yixin.nfyh.cloud.w.IGetIntervenesLinstener;
import com.yixin.nfyh.cloud.w.NfyhWebserviceFactory;

/**
 * 体征干预
 * 
 * @author ChenRui
 * 
 */
public class SignGanyuActivity extends BaseActivity implements IGetIntervenesLinstener
{
	private ListView				mListView;
	private GanyuListViewAdapter	mListAdater;
	private AdapterViewHelper		mHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		showHomeAsUp();
		
		setContentView(R.layout.activity_sign_ganyu);
		
		mListView = (ListView) findViewById(android.R.id.list);
		mListAdater = new GanyuListViewAdapter(this, null);
		mListView.setAdapter(mListAdater);
		mListView.setOnItemClickListener(mListAdater);
		mHelper = new AdapterViewHelper(this);
		
		loadData();
		
	}
	
	private void loadData()
	{
		mHelper.showLoaddingView();
		NfyhWebserviceFactory.getUserDoctorApi(this).getIntervenes(getUser().getUid(), this);
	}
	
	@Override
	protected String getActivityName()
	{
		return getString(R.string.tzgy);
	}
	
	@Override
	public void onGetIntervenesSuccess(List<GanyuInfo> datas)
	{
		mHelper.dismissLoaddingView();
		mListAdater.setData(datas);
		mListAdater.notifyDataSetChanged();
	}
	
	@Override
	public void onGetIntervenesError(int code, String msg)
	{
		mHelper.dismissLoaddingView();
		mHelper.showEmptyView(msg);
		
	}
	
	@Override
	public void onNotIntervenes()
	{
		mHelper.dismissLoaddingView();
		mHelper.showEmptyView("没有医生对您的体征干预");
	}
}
