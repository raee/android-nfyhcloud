package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

import com.rae.core.image.photoview.PhotoViewPager;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;

/**
 * @author Chenrui 照片预览窗体
 * 
 */
public class PhotoPreviewActivity extends BaseActivity implements
		OnPageChangeListener
{
	private PhotoViewPager	mPhotoViewPager;
	private String[]		mImageUrls;
	private TextView		mTextCount;
	private TextView		mTextCurrent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_photo_preview);
		this.mTextCurrent = (TextView) findViewById(R.id.tv_photoview_current);
		this.mTextCount = (TextView) findViewById(R.id.tv_photoview_count);
		Bundle extra = this.getIntent().getExtras();
		mImageUrls = extra.getStringArray("data");
		if (mImageUrls == null)
		{
			ArrayList<String> uris = extra.getStringArrayList("data");
			if (uris != null)
			{
				mImageUrls = new String[uris.size()];
				uris.toArray(mImageUrls);
			}
		}
		int index = extra.getInt("index");
		this.mTextCount.setText("/" + mImageUrls.length);
		setIndexText(index);
		
		this.mPhotoViewPager = (PhotoViewPager) findViewById(R.id.viewpager);
		this.mPhotoViewPager.setImageUri(mImageUrls);
		this.mPhotoViewPager.setup();
		this.mPhotoViewPager.setCurrentItem(index);
		this.mPhotoViewPager.setOnPageChangeListener(this);
	}
	
	@Override
	protected String getActivityName()
	{
		return "预览照片";
	}
	
	private void setIndexText(int index)
	{
		this.mTextCurrent.setText(index + 1 + "");
	}
	
	@Override
	public void onPageScrollStateChanged(int index)
	{
	}
	
	@Override
	public void onPageScrolled(int index, float arg1, int arg)
	{
	}
	
	@Override
	public void onPageSelected(int index)
	{
		setIndexText(index);
	}
	
}
