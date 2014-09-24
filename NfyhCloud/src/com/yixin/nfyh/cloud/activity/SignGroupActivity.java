package com.yixin.nfyh.cloud.activity;

import java.sql.SQLException;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.data.ISignDevice;
import com.yixin.nfyh.cloud.data.NfyhCloudDataFactory;
import com.yixin.nfyh.cloud.model.SignTypes;
import com.yixin.nfyh.cloud.utils.ReflectUtil;

/**
 * 体征测量主要类
 * 
 * @author MrChenrui
 * 
 */
public class SignGroupActivity extends BaseActivity
{
	private ISignDevice		api;
	private List<SignTypes>	signTypes;
	private GridView		gvSignTypes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_core);
		
		// 初始化体征类型
		try
		{
			this.api = NfyhCloudDataFactory.getFactory(this).getSignDevice();
			this.signTypes = api.getSignTypes(); // 获取体征类型
			
			this.gvSignTypes = (GridView) this
					.findViewById(R.id.view_gv_sign_types);
			SignTypeGridViewAdapter signTypeAdapter = new SignTypeGridViewAdapter();
			this.gvSignTypes.setAdapter(signTypeAdapter);
			this.gvSignTypes.setOnItemClickListener(signTypeAdapter);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private class SignTypeGridViewAdapter extends BaseAdapter implements
			OnItemClickListener
	{
		
		@Override
		public View getView(int location, View v, ViewGroup vg)
		{
			TextView tv = null;
			SignTypes m = signTypes.get(location);
			if (v == null)
			{
				
				v = LayoutInflater.from(SignGroupActivity.this).inflate(
						R.layout.ui_sign_type_item, null);
				
			}
			tv = (TextView) v.findViewById(R.id.tv_ui_sign_type_name);
			tv.setText(m.getName());
			int top = ReflectUtil.getDrawableId(R.drawable.class,
					m.getTypeIcon());
			tv.setCompoundDrawablesWithIntrinsicBounds(0, top, 0, 0);
			
			return v;
		}
		
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}
		
		@Override
		public Object getItem(int arg0)
		{
			return signTypes.get(arg0);
		}
		
		@Override
		public int getCount()
		{
			return signTypes.size();
		}
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int location,
				long arg3)
		{
			SignTypes m = signTypes.get(location);
			Intent intent = new Intent(SignGroupActivity.this,
					SignDetailActivity.class);
			intent.putExtra(Intent.EXTRA_TEXT, m.getTypeId());
			startActivity(intent);
			
		}
	}
	
	@Override
	public void onClick(View v)
	{
	}
	
}
