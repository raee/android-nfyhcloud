package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.adapter.PhotocategoryAdapter;
import com.yixin.nfyh.cloud.bll.PhotoCategoryControl;
import com.yixin.nfyh.cloud.bll.PhotoCategoryControl.PhotoCategoryListener;
import com.yixin.nfyh.cloud.model.Photocategory;

public class PhotoActivity extends BaseActivity implements OnItemClickListener,
		PhotoCategoryListener
{
	//	private String					filePath;
	private ListView				lvCategories;
	private PhotocategoryAdapter	adapter;
	private PhotoCategoryControl	mContorller;
	
	// private String mCurrentCategory = "0"; //当前分类Id
	// private String mLastCategory = "0"; // 上一级分类Id
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		this.mContorller = new PhotoCategoryControl(this);
		this.mContorller.setPhotoCategoryListener(this);
		findView();
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		// case R.id.btn_photo_graph:
		// NfyhApplication app = (NfyhApplication) getApplication();
		// try
		// {
		// filePath = app.openCamera(this);
		// }
		// catch (Exception e)
		// {
		// TimerToast.makeText(this, e.getMessage(),
		// Toast.LENGTH_SHORT).show();
		// }
		// break;
		//
		// case R.id.btn_photo_from_phone:
		//
		// // startActivity(new Intent(this, PhotoAlbumActivity.class));
		// break;
			case R.id.btn_photo_add_category:
				startActivityForResult(new Intent(this,
						AddCategoryActivity.class), 0);
				break;
			default:
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			showProgressDialog();
			mContorller.getCategories(adapter, "0", true);
		}
		
		// switch (requestCode)
		// {
		// case NfyhApplication.ACTIVITY_RESULT_CAMARA_OK:
		// if (resultCode == 0)
		// {
		// // 点击返回，删除照片
		// File file = new File(filePath);
		// if (file.exists()) file.delete();
		//
		// }
		// else if (filePath == null)
		// {
		// TimerToast.makeText(this, "找不到刚刚拍照的照片，请重试。",
		// Toast.LENGTH_SHORT).show();
		// }
		// else
		// {
		// String[] value = new String[] { filePath };
		// Intent intent = new Intent(this, UploadPhotoActivity.class);
		// intent.putExtra(UploadPhotoActivity.INTENT_EXTRA_DATA,
		// value);
		//
		// startActivity(intent);
		// }
		// break;
		//
		// default:
		// break;
		// }
	}
	
	@Override
	protected void findView()
	{
		findViewById(R.id.btn_photo_add_category).setOnClickListener(this);
		this.lvCategories = (ListView) findViewById(android.R.id.list);
		this.lvCategories.setOnItemClickListener(this);
		
		this.adapter = new PhotocategoryAdapter(this,
				new ArrayList<Photocategory>());
		this.lvCategories.setAdapter(adapter);
		this.mContorller.getCategories(adapter, "0");
		
	}
	
	@Override
	protected String getActivityName()
	{
		return getString(R.string.yhzp);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_webview, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				dismissProgressDialog();
				if (this.getActionBar().getTitle().equals("院后照片"))
				{
					this.finish();
				}
				else
				{
					loadParent();
				}
				break;
			case R.id.menu_refresh: // 刷新
				mContorller.refreshCategory();
				break;
			default:
				break;
		}
		return false;
	}
	
	/**
	 * 返回上一级
	 * 
	 * @return 有上一级则返回真，没有返回假
	 */
	private boolean loadParent()
	{
		Object obj = lvCategories.getTag();
		if (obj == null) return false;
		
		String parentId = obj.toString(); // 上一级的分类
		this.getCategories(parentId); // 加载上一级分类
		
		Photocategory parent = this.mContorller.getCategory(parentId);
		if (parent != null)
		{
			;
			setTitle(parent.getName());
			lvCategories.setTag(parent.getParentid()); // 设置当前分类的上一级分类
			return true;
		}
		else
		{
			setTitle("院后照片");
			return false;
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		Photocategory model = adapter.getDataItem(position);
		lvCategories.setTag(model.getParentid()); // 设置返回的上一级
		this.getCategories(model.getPid()); // 获取该分类下的子分类
		setTitle(model.getName());
	}
	
	private void getCategories(String categoryId)
	{
		showProgressDialog();
		this.mContorller.getCategories(adapter, categoryId); // 加载上一级分类
	}
	
	@Override
	public void onPhotocategoryEmpty(String categoryId)
	{
		dismissProgressDialog();
		// 如果他没有子类则是最后一级
		List<Photocategory> childrens = mContorller.getWebserviceApi()
				.getCategories(categoryId);
		if(categoryId.equals("0")) // 没有根目录
		{
			adapter.setEmptyMessage("第一次使用，新建个分类吧！");
			adapter.showEmptyView();
			return;
		}
		if (childrens != null && childrens.size() < 1)
		{
			loadParent();
			Intent intent = new Intent(this, PhotoViewActivity.class);// 跳转到查看照片
			intent.putExtra(Intent.EXTRA_TEXT, categoryId);
			startActivity(intent);
		}
	}
	
	@Override
	public void onPhotocategoryCallBack(int state, String categoryId)
	{
		dismissProgressDialog();
		Log.i("tt", "服务器调用完毕！");
	}
	
	@Override
	protected void showProgressDialog()
	{
		View v = findViewById(R.id.menu_refresh);
		if (v != null)
		{
			v.startAnimation(AnimationUtils
					.loadAnimation(this, R.anim.spinning));
			Log.i("tt", "开始动画");
		}
	}
	
	@Override
	protected void dismissProgressDialog()
	{
		View v = findViewById(R.id.menu_refresh);
		if (v != null)
		{
			v.clearAnimation();
			Log.i("tt", "清除动画");
		}
	}
	
}
