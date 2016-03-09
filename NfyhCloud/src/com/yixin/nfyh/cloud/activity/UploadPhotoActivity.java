package com.yixin.nfyh.cloud.activity;

import java.util.ArrayList;

import org.apache.http.Header;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import cn.rui.framework.ui.RuiDialog;

import com.rae.core.http.async.AsyncHttpResponseHandler;
import com.rae.core.image.photoview.PhotoGridView;
import com.yixin.nfyh.cloud.BaseActivity;
import com.yixin.nfyh.cloud.R;
import com.yixin.nfyh.cloud.bll.PhotoCategoryControl;
import com.yixin.nfyh.cloud.model.Photocategory;
import com.yixin.nfyh.cloud.w.WebServerException;

/**
 * 图片上传视图
 * 
 * @author ChenRui
 * 
 */
public class UploadPhotoActivity extends BaseActivity implements
		OnItemClickListener, OnItemLongClickListener {
	private String mCategoryId;
	private ArrayList<String> mUploadList;
	private PhotoGridView mGridView;
	private PhotoCategoryControl mControl;
	// private BroadcastReceiver mReceiver;
	private Button btnUpload;
	private String mUploadTips = "上传中...";
	private int mUploadCount, mUploadedLength = 0; // 上传的总数量

	// private ServiceConnection mConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_activity);
		this.mControl = new PhotoCategoryControl(this);

		this.mCategoryId = getIntent().getStringExtra("type");
		this.mUploadList = getIntent().getStringArrayListExtra("data");

		this.mGridView = (PhotoGridView) this.findViewById(android.R.id.list);

		this.mGridView.setImageUri(mUploadList);
		this.mGridView.setup(); // 初始化GridView
		this.mGridView.setOnItemClickListener(this);
		this.mGridView.setOnItemLongClickListener(this);

		this.btnUpload = (Button) findViewById(R.id.btn_upload_photo);
		this.btnUpload.setOnClickListener(this);
		findViewById(R.id.btn_upload_add).setOnClickListener(this);

		Photocategory category = this.mControl.getCategory(mCategoryId); // 获取分类
		setTitle("上传图片到：" + category.getName()); // 设置标题

	}

	@Override
	protected void onDestroy() {
		// this.unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	@Override
	protected String getActivityName() {
		return getString(R.string.activity_photo_upload);
	}

	/**
	 * 上传图片
	 */
	public void upload() {
		mUploadCount = this.mUploadList.size();
		mUploadedLength = 0;
		if (this.mUploadList != null && mUploadCount > 0) {
			this.btnUpload.setEnabled(false);
			this.btnUpload.setText(mUploadTips + "0%");
			for (String uri : mUploadList) {
				mControl.uploadPhoto(uri, mCategoryId,
						new AsyncFileHttpResponseHandler(uri));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_upload_photo: // 上传
			upload();
			break;
		case R.id.btn_upload_add: // 添加
			addPhotos();
		default:
			break;
		}
	}

	/**
	 * 添加照片
	 */
	private void addPhotos() {
		Intent intent = new Intent(this, PhotoSelectorActivity.class);
		intent.putStringArrayListExtra("data", this.mUploadList);
		intent.putExtra("type", mCategoryId);
		startActivityForResult(intent, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && data != null) {
			this.mUploadList.clear();
			this.mUploadList = data.getStringArrayListExtra("data");
			this.mGridView.setImageUri(mUploadList);
			this.mGridView.setup(); // 初始化GridView
			refresh();
		}
	}

	/**
	 * 异步回调
	 * 
	 * @author Chenrui
	 * 
	 */
	private class AsyncFileHttpResponseHandler extends AsyncHttpResponseHandler {

		private String mUri;

		public AsyncFileHttpResponseHandler(String uri) {
			this.mUri = uri;
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
			String json = new String(responseBody);
			WebServerException ex = WebServerException.parser(json);
			int code = ex.getCode();
			if (code == 1) {
				Log.i("tag", "上传成功：" + mUri);
				mUploadList.remove(mUri);
				refresh();
				sendUpload();
			}

			else {
				onFailure(statusCode, headers, responseBody,
						new Throwable(ex.getMessage()));
			}
		}

		@Override
		public void onProgress(int bytesWritten, int totalSize) {

			int progress = (int) Math
					.rint(((double) bytesWritten / totalSize) * 100);
			Log.i("tag", "prgress" + progress);
			mGridView.showInProgress(mUri, progress);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable ex) {
			String msg = ex.getMessage();
			showMsg(msg);
			mGridView.showImage(mUri);
			sendUpload();
		}

		/**
		 * 开始上传
		 */
		private void sendUpload() {
			mUploadedLength++;

			if (mUploadedLength >= mUploadCount) // 上传完毕
			{
				// 计算错误个数
				int errorCount = mUploadList.size();
				if (errorCount > 0) {
					btnUpload.setText(errorCount + "张图片错误，重新上传");
				} else {
					btnUpload.setText("上传成功");
				}
				btnUpload.setEnabled(true);
			} else {
				double progress = (double) mUploadedLength / mUploadCount;
				progress = progress * 100;
				int pro = (int) Math.rint(progress);
				btnUpload.setText(mUploadTips + pro + "%");
			}

		}
	}

	/**
	 * 刷新视图
	 */
	private void refresh() {
		BaseAdapter adapter = (BaseAdapter) mGridView.getAdapter();
		adapter.notifyDataSetInvalidated();
		adapter.notifyDataSetChanged();
		mGridView.invalidate();
		if (this.mUploadList.size() <= 0) {
			this.btnUpload.setEnabled(true);
			this.btnUpload.setText("上传");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, final int position,
			long arg3) {
		new RuiDialog.Builder(this).buildTitle("选择操作")
				.buildMessage("请选择操作，点击外面返回。")
				.buildLeftButton("查看", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(UploadPhotoActivity.this,
								PhotoPreviewActivity.class);
						intent.putExtra("data", mUploadList);
						intent.putExtra("index", position);
						startActivity(intent);
						dialog.dismiss();
					}
				}).buildRight("删除", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mUploadList.remove(position);
						refresh();
						dialog.dismiss();
					}
				}).show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			int position, long arg3) {
		return false;
	}

}
