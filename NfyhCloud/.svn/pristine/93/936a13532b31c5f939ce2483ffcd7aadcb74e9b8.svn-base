//package com.yixin.nfyh.cloud;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//
//import com.yixin.nfyh.cloud.bll.BitmapHandle;
//import com.yixin.nfyh.cloud.bll.PhotoAlbumAdapter;
//import com.yixin.nfyh.cloud.bll.PhotoAlbumAdapter.MyGridViewHolder;
//
///**
// * @author dmh 院后照片界面
// */
//public class PhotoAlbumActivity extends BaseActivity implements
//		OnScrollListener, OnClickListener, OnItemClickListener
//{
//	private GridView					gridViewImages;
//	private PhotoAlbumAdapter			photoAlbumAdapter;											// 相册适配器
//	private List<String>				mList					= null;
//	private Button						mBtnFinish;												// 完成按钮
//	/**
//	 * 预览按钮
//	 */
//	private Button						btnPreview;
//
//	public static Map<String, Bitmap>	gridviewBitmapCaches	= new HashMap<String, Bitmap>();	// 图片缓存用来保存GridView中每个Item的图片，以便释放
//
//	ArrayList<String>					litPath;													// 存放选择图片的地址
//	private boolean						isResult;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_photo_album);
//
//		Intent intent = getIntent();
//		// Bundle bundle = intent.getExtras();
//		// litPath = bundle.getStringArrayList("litSelectPhoto");
//		if (litPath == null)
//		{
//			litPath = new ArrayList<String>();// 存放选择图片的地址
//		}
//
//		BitmapHandle bh = new BitmapHandle(this);
//		mList = new ArrayList<String>();// 全部图片的地址
//		List<HashMap<String, String>> litbh = bh.getMediaStoreImages();// 取得全部图片
//		for (int i = 0; i < litbh.size(); i++)
//		{
//			mList.add(litbh.get(i).get("imgData"));
//		}
//
//		findView();
//
//		photoAlbumAdapter = new PhotoAlbumAdapter(this, mList, litPath);// 设置适配器
//		gridViewImages.setAdapter(photoAlbumAdapter);
//		gridViewImages.setOnScrollListener(this);
//		gridViewImages.setOnItemClickListener(this);
//
//		// 是否启动为返回结果类型的
//		this.isResult = getIntent().getBooleanExtra(
//				String.valueOf(UploadPhotoActivity.INTENT_RESULT_SELECT_FROM),
//				false);
//
//	}
//
//	/**
//	 * 初始化控件
//	 */
//	@Override
//	protected void findView()
//	{
//		gridViewImages = (GridView) findViewById(R.id.gridView_images);
//		mBtnFinish = (Button) findViewById(R.id.btn_finish);
//		mBtnFinish.setOnClickListener(this);
//		btnPreview = (Button) findViewById(R.id.btn_preview);
//		btnPreview.setOnClickListener(this);
//	}
//
//	/**
//	 * 滚动条 动态释放位图
//	 */
//	@Override
//	public void onScroll(AbsListView view, int firstVisibleItem,
//			int visibleItemCount, int totalItemCount)
//	{
//		recycleBitmapCaches(0, firstVisibleItem);
//		recycleBitmapCaches(firstVisibleItem + visibleItemCount, totalItemCount);
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView view, int scrollState)
//	{
//
//	}
//
//	// 释放图片的函数
//	private void recycleBitmapCaches(int fromPosition, int toPosition)
//	{
//		Bitmap delBitmap = null;
//		for (int del = fromPosition; del < toPosition; del++)
//		{
//			delBitmap = gridviewBitmapCaches.get("" + del);
//			if (delBitmap != null)
//			{
//				// 如果非空则表示有缓存的bitmap，需要清理
//				// 从缓存中移除该del->bitmap的映射
//				gridviewBitmapCaches.remove("" + del);
//				delBitmap.recycle();
//				delBitmap = null;
//				System.gc();
//			}
//		}
//	}
//
//	/**
//	 * 点击事件
//	 */
//	@Override
//	public void onClick(View v)
//	{
//		switch (v.getId())
//		{
//			case R.id.btn_finish:
//				finishSelectPhotos();
//				break;
//			// 预览
//			case R.id.btn_preview:
//				if (checkHaveSelectedPhotos())
//				{
//					Intent intent = new Intent();
//					intent.putExtra("photos", litPath);
//					intent.setClass(this, PhotoPreviewActivity.class);
//
//					startActivity(intent);
//				}
//				break;
//			default:
//				break;
//		}
//	}
//
//	/**
//	 * 完成图片选择
//	 */
//	private void finishSelectPhotos()
//	{
//		if (checkHaveSelectedPhotos())
//		{
//			Bundle bundle = new Bundle();
//			String[] value = new String[litPath.size()];
//			value = litPath.toArray(value);
//			bundle.putStringArray(UploadPhotoActivity.INTENT_EXTRA_DATA, value);
//
//			Intent intent = new Intent(this, UploadPhotoActivity.class);
//			intent.putExtras(bundle);
//			if (isResult)
//			{
//				setResult(UploadPhotoActivity.INTENT_RESULT_SELECT_FROM, intent);
//			}
//			else
//			{
//				startActivity(intent);
//			}
//
//			finish();
//		}
//	}
//
//	/**
//	 * 检测是否已选图片
//	 * 
//	 * @return true 有选择的图片，false 无选择的图片
//	 */
//	private boolean checkHaveSelectedPhotos()
//	{
//		if (litPath.size() <= 0)
//		{
//			// MessagerDefault msg = new MessagerDefault(this);
//			// msg.show(getResources().getString(R.string.no_select_phote));
//			return false;
//		}
//		else
//		{
//			return true;
//		}
//	}
//
//	/*
//	 * (non-Javadoc) gridView item 的选择
//	 * 
//	 * @see
//	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
//	 * .AdapterView, android.view.View, int, long)
//	 */
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
//	{
//		MyGridViewHolder gridHolder = (MyGridViewHolder) arg1.getTag();
//		if (!gridHolder.isSelect)
//		{
//			gridHolder.isSelect = true;
//			litPath.add(gridHolder.imgPath);
//			gridHolder.rl_select_img.setVisibility(View.VISIBLE);
//		}
//		else
//		{
//			gridHolder.isSelect = false;
//			litPath.remove(gridHolder.imgPath);
//			gridHolder.rl_select_img.setVisibility(View.GONE);
//		}
//	}
//
//	@Override
//	protected void setLinsener()
//	{
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected String getActivityName()
//	{
//		return getString(R.string.activity_photo_select);
//	}
//
//}
