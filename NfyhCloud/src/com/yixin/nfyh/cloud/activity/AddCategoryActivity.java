package com.yixin.nfyh.cloud.activity;

import com.yixin.nfyh.cloud.R;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.bll.PhotoCategoryControl;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 添加院后照片分类
 * 
 * @author MrChenrui
 * 
 */
public class AddCategoryActivity extends BaseActivity
{
	
	private PhotoCategoryControl	mControl;
	private EditText				mTextName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_category_add);
		this.mControl = new PhotoCategoryControl(this);
		this.mTextName = (EditText) findViewById(R.id.et_photo_category_add_title);
		findViewById(android.R.id.edit).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case android.R.id.edit:
				addCategory();
				break;
			
			default:
				break;
		}
	}
	
	private void addCategory()
	{
		
		String name = mTextName.getText().toString();
		if (TextUtils.isEmpty(name) && name.length() < 5)
		{
			showMsg("分类名称不能为空，并且长度大于5个！");
			return;
		}
		showProgressDialog("正在连接服务器，请稍候...");
		
		mControl.addCategory(name,
				new SoapConnectionCallback<WebServerException>()
				{
					
					@Override
					public void onSoapConnectedFalid(WebServerException e)
					{
						setResult(RESULT_CANCELED);
						showMsg(e.getMessage());
						dismissProgressDialog();
					}
					
					@Override
					public void onSoapConnectSuccess(WebServerException data)
					{
						setResult(RESULT_OK);
						showMsg("添加分类成功！");
						dismissProgressDialog();
						finish();
					}
				});
		
	}
	
	@Override
	protected String getActivityName()
	{
		return getString(R.string.back);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			setResult(RESULT_CANCELED);
		}
		return super.onOptionsItemSelected(item);
	}
	
}
