package com.yixin.nfyh.cloud.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.rae.core.image.photoview.PhotoGridView;
import com.rae.core.image.utils.CameraUtils;
import com.rae.core.image.utils.CameraUtils.CameraCallbackListener;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.PhotoCategoryControl;
import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.model.Photos;
import com.yixin.nfyh.cloud.ui.EmpteyView;
import com.yixin.nfyh.cloud.w.SoapConnectionCallback;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 查看照片
 * 
 * @author ChenRui
 * 
 */
public class PhotoViewActivity extends BaseActivity implements
		OnItemClickListener
{
	private PhotoCategoryControl	mControl;
	private PhotoGridView			mGridView;
	private Photocategory			mCurrentCategory;
	
	private String[]				mImageUrls;
	private EmpteyView				emptView;
	private CameraUtils				utils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);
		showProgressDialog();
		this.mGridView = (PhotoGridView) findViewById(android.R.id.list);
		utils = new CameraUtils(this);
		this.mGridView.setOnItemClickListener(this);
		
		findViewById(R.id.btn_photo_graph).setOnClickListener(this);
		findViewById(R.id.btn_photo_from_phone).setOnClickListener(this);
		
		utils.setCameraListener(new CameraCallbackListener()
		{
			
			@Override
			public void onTakePhotoSuccess(String filePath)
			{
				ArrayList<String> uris = new ArrayList<String>();
				uris.add(Uri.fromFile(new File(filePath)).toString());
				mControl.gotoUploadActivity(mCurrentCategory.getPid(), uris);
			}
			
			@Override
			public void onCameraFaild(int code, String msg)
			{
				showMsg(msg);
			}
		});
		
		loadPhotos();
		
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		loadPhotos();
	}
	
	private void loadPhotos()
	{
		// 获取照片列表
		mControl.getPhotos(mCurrentCategory.getPid(),
				new SoapConnectionCallback<List<Photos>>()
				{
					
					@Override
					public void onSoapConnectedFalid(WebServerException e)
					{
						showMsg(e.getMessage());
						dismissProgressDialog();
						showEmptyView("服务器获取数据失败..");
					}
					
					@Override
					public void onSoapConnectSuccess(List<Photos> data)
					{
						dismissProgressDialog();
						if (data.size() < 1)
						{
							showEmptyView("没有照片，赶紧上传吧！");
							return;
						}
						mImageUrls = new String[data.size()];
						int i = 0;
						for (Photos photo : data)
						{
							Photocategory category = mControl
									.getCategory(mCurrentCategory.getPid());
							photo.setPhotocategory(category);
							String url = getURI(photo.getPhotoId());
							mImageUrls[i] = url;
							i++;
						}
						mGridView.setImageUri(mImageUrls);
						mGridView.setup();
						
						hideEmptyView();
					}
				});
	}
	
	/**
	 * 获取路径
	 * 
	 * @param id
	 * @return
	 */
	private String getURI(String id)
	{
		return getString(R.string.url_method_photo_list) + "&cookie="
				+ getUser().getCookie() + "&photoid=" + id;
	}
	
	@Override
	protected String getActivityName()
	{
		
		if (mCurrentCategory == null)
		{
			String categoryid = getIntent().getStringExtra(Intent.EXTRA_TEXT);
			mControl = new PhotoCategoryControl(this);
			mCurrentCategory = mControl.getCategory(categoryid);
			if (mCurrentCategory != null) return mCurrentCategory.getName();
		}
		
		return "查看照片";
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3)
	{
		Intent intent = new Intent(this,
				com.yixin.nfyh.cloud.activity.PhotoPreviewActivity.class);
		intent.putExtra("data", mImageUrls);
		intent.putExtra("index", index);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent();
		switch (v.getId())
		{
			case R.id.btn_photo_graph: // 拍照
				utils.takePhoto();
				break;
			case R.id.btn_photo_from_phone: // 媒体库
				intent.setClass(this, PhotoSelectorActivity.class);
				intent.putExtra("type", this.mCurrentCategory.getPid());
				startActivity(intent);
				break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		utils.onTakePhotoActivityResult(requestCode, resultCode, data);
	}
	
	private void showEmptyView(String msg)
	{
		if (emptView == null)
		{
			ViewGroup view = (ViewGroup) ((Activity) this)
					.findViewById(android.R.id.empty);
			view.setVisibility(View.VISIBLE);
			emptView = new EmpteyView(this, view);
		}
		
		emptView.setText(msg);
		emptView.setVisibility(View.VISIBLE);
		emptView.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.fade_in));
	}
	
	private void hideEmptyView()
	{
		if (emptView != null)
		{
			emptView.setVisibility(View.GONE);
			emptView.clearAnimation();
		}
	}
}
