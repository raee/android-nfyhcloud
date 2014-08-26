//package com.yixin.nfyh.cloud;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.DisplayMetrics;
//import android.util.Log;
//import android.util.Pair;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.GridView;
//import android.widget.Toast;
//import cn.rui.framework.ui.RuiDialog;
//
//import com.yixin.nfyh.cloud.bll.BitmapHandle;
//import com.yixin.nfyh.cloud.bll.UploadPhoto;
//import com.yixin.nfyh.cloud.bll.UploadPhoto.UploadEntity;
//import com.yixin.nfyh.cloud.bll.UploadPhoto.UploadPhotoCallback;
//import com.yixin.nfyh.cloud.entitys.Albums;
//import com.yixin.nfyh.cloud.ui.TimerProgressDialog;
//import com.yixin.nfyh.cloud.ui.TimerToast;
//import com.yixin.nfyh.cloud.ui.UploadImageItem;
//import com.yixin.nfyh.cloud.widget.ActionBar;
//import com.yixin.nfyh.cloud.widget.SubMenu;
//
///**
// * 图片上传
// * 
// * @author 睿
// * 
// */
//public class UploadPhotoActivity extends BaseActivity implements
//		UploadPhotoCallback
//{
//	public static final String			INTENT_EXTRA_DATA			= "EXTRA_DATA";
//	protected static final int			INTENT_RESULT_SELECT_FROM	= 990;
//	private GridView					gvPhotolist;
//	private ActionBar					actionbar;
//	private Button						btnRight;
//	// private List<Pair<String, Drawable>> imagelist;
//
//	private static String				filePath;
//	private PhotoListAdapter			adapter;
//	private UploadPhoto					upload;
//	private SubMenu						album;
//	private EditText					etSaySomethink;
//	private UIHandler					handler;
//	private static UploadEntity			uploadEntity;
//
//	private NfyhApplication				app;
//	private List<Pair<String, String>>	albumList;
//	private TimerProgressDialog			albumProgress;
//	private int							width;										// 图片的宽度
//	private boolean						isuploading					= false;		// 是否在上传
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_upload_photo);
//
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//		int swidth = dm.widthPixels;
//
//		width = swidth / 3 - 50;
//
//		app = (NfyhApplication) getApplication();
//		this.upload = new UploadPhoto(this);
//
//		if (uploadEntity == null)
//			uploadEntity = new UploadEntity();
//		if (uploadEntity.size() <= 0)
//		{
//			Drawable add = getResources().getDrawable(
//					R.drawable.upload_photo_add);
//			uploadEntity.setEntity("", add);
//		}
//		this.upload.setUploadList(uploadEntity);
//		upload.setUploadListener(this);
//		handler = new UIHandler();
//
//		findView();
//		setLinsener();
//		recData(getIntent());
//
//	}
//
//	public void recData(Intent intent)
//	{
//		try
//		{
//			Bundle data = intent.getExtras();
//			if (data == null)
//				return;
//			else if (!data.containsKey(INTENT_EXTRA_DATA))
//				return;
//
//			String[] filePaths = (String[]) data.get(INTENT_EXTRA_DATA);
//
//			for (CharSequence item : filePaths)
//			{
//				if (item == null || item.equals(""))
//					continue;
//
//				Drawable img = loadPhotoImage(item.toString());
//				uploadEntity.setEntity(item.toString(), img);
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			Log.e("test", "获取图片失败" + e.getMessage());
//			showMsg("获取图片失败。");
//		}
//
//	}
//
//	@Override
//	public void onClick(View v)
//	{
//		switch (v.getId())
//		{
//			case R.id.btn_right_one:
//				if (isuploading && uploadEntity.size() > 1)
//				{
//					showMsg("正在上传，请等待上传完。");
//					return;
//				}
//				else if (uploadEntity.size() > 1)
//				{
//					// 上传到云端
//					upload.upload(this.etSaySomethink.getText().toString(),
//							this.album.getTag().toString());
//					isuploading = true;
//				}
//				else
//				{
//					showMsg("请添加要上传的图片");	
//				}
//				break;
//			case R.id.submenu_album:
//				// 选择照片
//				if (albumList == null)
//				{
//					albumProgress = TimerProgressDialog.show(this,
//							"正在从服务器获取数据...");
//					upload.getServerAlbum(handler);
//				}
//				else
//					showAlbumList();
//				break;
//
//			default:
//				break;
//		}
//	}
//
//	private void showAlbumList()
//	{
//		RuiDialog dialog = new RuiDialog(this);
//		dialog.setTitle("请选择相册");
//		String[] items = new String[albumList.size()];
//		int i = 0;
//		for (Pair<String, String> item : albumList)
//		{
//			items[i] = item.second;
//			i++;
//		}
//
//		dialog.setItems(items);
//		dialog.setOnItemSelectListener(new DialogInterface.OnClickListener()
//		{
//
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				Pair<String, String> m = albumList.get(which);
//				album.setTag(m.first); // 相册Id
//				album.setRightTitle(m.second);
//			}
//		});
//		dialog.show();
//	}
//
//	/**
//	 * 添加新的图片
//	 */
//	private void addNewPhoto()
//	{
//
//		RuiDialog dialog = new RuiDialog(this);
//		dialog.setTitle("请选择操作");
//		if (uploadEntity.size() > 20)
//		{
//			dialog.setTitle("上传上限");
//			dialog.setMessage("一次只能上传20张图片，请先上传后再添加。");
//			dialog.setLeftButton("返回", null);
//		}
//		else
//		{
//			dialog.setItems("拍照", "从媒体库中选择");
//			dialog.setOnItemSelectListener(new DialogInterface.OnClickListener()
//			{
//
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//					if (which == 0)
//					{
//						// 打开照相机
//						try
//						{
//							filePath = app.openCamera(UploadPhotoActivity.this);
//						}
//						catch (Exception e)
//						{
//							e.printStackTrace();
//							showMsg("打开系统相机失败，请尝试从本地媒体库中选择。");
//						}
//					}
//					else
//					{
//						Intent intent = new Intent(UploadPhotoActivity.this,
//								PhotoAlbumActivity.class);
//						intent.putExtra(
//								String.valueOf(INTENT_RESULT_SELECT_FROM), true);
//						startActivityForResult(intent,
//								INTENT_RESULT_SELECT_FROM);
//					}
//				}
//			});
//		}
//
//		dialog.show();
//	}
//
//	private class UIHandler extends Handler
//	{
//		@Override
//		public void handleMessage(Message msg)
//		{
//			switch (msg.what)
//			{
//				case 0:
//					uploadEntity.remove(msg.arg1);
//					break;
//				case 1:
//					if (uploadEntity.size() == 1)
//					{
//						TimerToast.makeText(UploadPhotoActivity.this, "上传成功",
//								Toast.LENGTH_SHORT).show();
//						isuploading = false;
//					}
//					else
//						Log.i("test", "上传成功");
//					break;
//				case UploadPhoto.HANDLER_WAHT_ALBUM:
//					albumProgress.dismiss();
//					if (msg.obj == null)
//					{
//						showMsg("服务器没有相册列表");
//						return;
//					}
//
//					albumList = new ArrayList<Pair<String, String>>();
//
//					List<Albums> albums = (List<Albums>) msg.obj;
//
//					if (albums.size() <= 0)
//					{
//						albums.add(new Albums("默认相册", "true", "0", "0"));
//					}
//					for (Albums item : albums)
//					{
//						albumList.add(new Pair<String, String>(item
//								.getAlbumId(), item.getName()));
//					}
//					showAlbumList();
//					break;
//				default:
//					break;
//			}
//
//			adapter.notifyDataSetChanged();
//			gvPhotolist.invalidate();
//		}
//	}
//
//	@Override
//	protected void findView()
//	{
//		this.gvPhotolist = (GridView) findViewById(R.id.gv_photolist);
//		adapter = new PhotoListAdapter();
//		this.gvPhotolist.setAdapter(adapter);
//		this.gvPhotolist.setOnItemClickListener(adapter);
//
//		this.actionbar = (ActionBar) findViewById(R.id.actionbar);
//		this.etSaySomethink = (EditText) findViewById(R.id.et_saysomethink);
//		this.album = (SubMenu) findViewById(R.id.submenu_album);
//		this.album.setRightTitle("默认相册");
//
//		btnRight = this.actionbar.getRightButton1();
//		btnRight.setText(R.string.upload_photo);
//		btnRight.setBackgroundResource(R.drawable.btn_top_green);
//		btnRight.setVisibility(View.VISIBLE);
//		btnRight.setTextColor(Color.WHITE);
//		btnRight.setPadding(30, 20, 30, 20);
//
//	}
//
//	@Override
//	protected void setLinsener()
//	{
//		btnRight.setOnClickListener(this);
//		album.setOnClickListener(this);
//	}
//
//	private void add(String filePath, Drawable img)
//	{
//		this.uploadEntity.setEntity(filePath, img);
//		this.adapter.notifyDataSetChanged();
//		this.gvPhotolist.invalidate();
//	}
//
//	private void remove(int index)
//	{
//		Message.obtain(handler, 0, index, 0).sendToTarget();
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		switch (requestCode)
//		{
//			case NfyhApplication.ACTIVITY_RESULT_CAMARA_OK:
//				try
//				{
//					if (resultCode == 0)
//					{
//						// 点击返回，删除照片
//						File file = new File(app.getCurrentCameraPath());
//						if (file.exists())
//							file.delete();
//						return;
//					}
//
//					Drawable resultImage = null; 
////					loadPhotoImage(app
////							.getCurrentCameraPath());
//					if (resultImage == null)
//					{
//						showMsg("无法找到刚刚拍照的照片！");
//						return;
//					}
//					add(filePath, resultImage);
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//					showMsg("程序发生一点小问题，请重试。");
//				}
//				break;
//			case INTENT_RESULT_SELECT_FROM:
//				recData(data);
//				this.adapter.notifyDataSetChanged();
//				this.gvPhotolist.invalidate();
//				break;
//
//			default:
//				break;
//		}
//	}
//
////	private Drawable loadPhotoImage(String path)
////	{
////
////		// Bitmap bmp = ImageUtils.getBitmapThumbnail(path, width, height);
////		Bitmap bmp = BitmapHandle.reduceImg(path, width, width);
////		return ImageUtils.bitmapToDrawable(bmp, width, width);
////	}
//
//	private class PhotoListAdapter extends BaseAdapter implements
//			OnItemClickListener
//	{
//		@Override
//		public int getCount()
//		{
//			return uploadEntity.size();
//		}
//
//		@Override
//		public Object getItem(int position)
//		{
//			return uploadEntity.getEntity(position);
//		}
//
//		@Override
//		public long getItemId(int position)
//		{
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent)
//		{
//			if (convertView == null)
//			{
//				convertView = new UploadImageItem(UploadPhotoActivity.this);
//			}
//
//			UploadImageItem v = (UploadImageItem) convertView;
//			v.hideProgress();
//			Pair<String, Drawable> m = uploadEntity.getEntity(position);
//			v.setImage(m.second);
//			v.setTag(m.first);
//			v.setLayoutParams(new AbsListView.LayoutParams(width, width));
//
//			return v;
//		}
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id)
//		{
//			showDel(position);
//		}
//
//		/**
//		 * 显示删除图片对话框
//		 * 
//		 * @param index
//		 */
//		private void showDel(final int index)
//		{
//			if (index == 0)
//			{
//				addNewPhoto();
//				return;
//			}
//
//			new RuiDialog.Builder(UploadPhotoActivity.this).buildTitle("删除照片")
//					.buildMessage("是否删除该照片？").buildLeftButton("返回", null)
//					.buildRight("删除", new DialogInterface.OnClickListener()
//					{
//
//						@Override
//						public void onClick(DialogInterface dialog, int which)
//						{
//							if (index > 0)
//							{
//								remove(index);
//							}
//							dialog.dismiss();
//						}
//					}).show();
//		}
//
//	}
//
//	@Override
//	public void onUploadSuccess(String path, String msg)
//	{
//		// 上传成功
//		Message.obtain(handler, 1).sendToTarget();
//	}
//
//	@Override
//	public void onUploadError(int index, String msg)
//	{
//		showMsg("上传第" + index + "张图片错误，" + msg);
//		this.isuploading = false;
//	}
//
//	@Override
//	protected void onDestroy()
//	{
//		super.onDestroy();
//		uploadEntity.clear();
//	}
//
//	@Override
//	public void onUploadProcess(String imgPath, float progress)
//	{
//		for (int i = 0; i < gvPhotolist.getChildCount(); i++)
//		{
//			UploadImageItem item = (UploadImageItem) this.gvPhotolist
//					.getChildAt(i);
//			if (item == null)
//				continue;
//			if (item.getTag().toString().equals(imgPath))
//			{
//				item.setProgress(progress);
//				Log.i("test", "进度：" + progress);
//				break;
//			}
//		}
//	}
//
//	@Override
//	protected String getActivityName()
//	{
//		return "照片上传";
//	}
//
//}
