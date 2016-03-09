package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.rae.core.image.loader.ImageLoader;
import com.rae.core.image.photoview.PhotoGridView.ViewHolder;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.PhotoCategoryControl;
import com.yixin.nfyh.cloud.ui.PhotoSelectGridView;

/**
 * 图片选择，从图片库中选择。
 * 
 * @author ChenRui
 * 
 */
public class PhotoSelectorActivity extends BaseActivity implements
		OnItemClickListener {
	private PhotoSelectGridView mGridView;
	private PhotoCategoryControl mControl;
	private String mCategoryId;
	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_select);
		this.mGridView = (PhotoSelectGridView) findViewById(android.R.id.list);
		this.mGridView.setOnItemClickListener(this);
		mControl = new PhotoCategoryControl(this);
		mCategoryId = getIntent().getStringExtra("type");
		ArrayList<String> selectedList = getIntent().getStringArrayListExtra(
				"data");
		if (selectedList != null) {
			for (String uri : selectedList) {
				mGridView.setCheckedUri(uri);
			}
			type = 1;
		}
	}

	@Override
	protected String getActivityName() {
		return "从媒体库中选择照片";
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		boolean selected = !mGridView.isChecked(position);
		ViewHolder holder = (ViewHolder) view.getTag();
		mGridView.setChecked(holder, position, selected);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_save, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_save:
			doSelectPhoto();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 选择图片
	 */
	private void doSelectPhoto() {
		ArrayList<String> checkedUris = (ArrayList<String>) mGridView
				.getCheckedList();
		if (checkedUris.size() <= 0) {
			showMsg("没有选择任何图片");
		} else {
			if (type == 1) {
				Intent intent = getIntent();
				intent.putExtra("data", checkedUris);
				setResult(RESULT_OK, intent);
			} else {
				mControl.gotoUploadActivity(mCategoryId, checkedUris);

			}
			this.finish();

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ImageLoader.getInstance().clearMemoryCache();
	}

	@Override
	protected void onPause() {
		ImageLoader.getInstance().pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		ImageLoader.getInstance().resume();
		super.onResume();
	}

}
