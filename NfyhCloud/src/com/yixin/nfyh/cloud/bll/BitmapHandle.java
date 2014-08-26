//package com.yixin.nfyh.cloud.bll;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.ContentResolver;
//import android.content.Context;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.media.ThumbnailUtils;
//import android.net.Uri;
//import android.provider.BaseColumns;
//import android.provider.MediaStore;
//import android.provider.MediaStore.MediaColumns;
//
///**
// * 位图的处理者
// * 
// * @author zhulin
// * 
// */
//public class BitmapHandle {
//
//	// public final static int BITMAP_WIGHT = 200;
//	// public final static int BITMAP_HEIGHT = 200;
//
//	private Context mContext;
//
//	public BitmapHandle(Context context) {
//		this.mContext = context;
//	}
//
//	/**
//	 * 获得全部的图片
//	 * 
//	 * @return
//	 */
//	public List<HashMap<String, String>> getMediaStoreImages() {
//		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;// uri
//		ContentResolver contentResolver = mContext.getContentResolver();
//		String[] projection = { BaseColumns._ID,
//				MediaColumns.DISPLAY_NAME,
//				MediaColumns.DATA, MediaColumns.SIZE };
//		String selection = MediaColumns.MIME_TYPE + "=?";
//		String[] selectionArgs = { "image/jpeg" };
//		String sortOrder = MediaColumns.DATE_MODIFIED + " desc";
//
//		Cursor cursor = contentResolver.query(uri, projection, selection,
//				selectionArgs, sortOrder);
//		List<HashMap<String, String>> litImg = new ArrayList<HashMap<String, String>>();
//		if (cursor != null) {
//			HashMap<String, String> imgMap = null;
//			cursor.moveToFirst();
//			while (cursor.moveToNext()) {
//				imgMap = new HashMap<String, String>();
//				imgMap.put("imgID",
//						cursor.getString(cursor.getColumnIndex(projection[0])));
//				imgMap.put("imgName",
//						cursor.getString(cursor.getColumnIndex(projection[1])));
//				imgMap.put("imgData",
//						cursor.getString(cursor.getColumnIndex(projection[2])));
//				imgMap.put("imgInfo",
//						cursor.getString(cursor.getColumnIndex(projection[3])));
//				if (isImage(imgMap.get("imgData"))) {
//					litImg.add(imgMap);
//				}
//			}
//			cursor.close();
//		}
//		return litImg;
//	}
//
//	/**
//	 * 通过读取文件并获取其width及height的方式，来判断判断当前文件是否图片，这是一种非常简单的方式。
//	 * 
//	 * @param imageFile
//	 * @return
//	 */
//	public static boolean isImage(String imagePath) {
//		File imgFile = new File(imagePath);
//		FileInputStream fis = null;
//		try {
//			if (imgFile == null) {
//				return false;
//			}
//			fis = new FileInputStream(imgFile);
//			if (fis == null) {
//				return false;
//			}
//			int length = fis.available();
//
//			if (length < 1024 * 150) {
//				return false;
//			}
//			return true;
//		} catch (Exception e) {
//			return false;
//		} finally {
//			if (fis != null) {
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * 
//	 * @param bitmap
//	 * @param wight
//	 * @param height
//	 * @return
//	 */
//	public static Bitmap zoomBitmap(Bitmap bitmap, int wight, int height) {
//		int sWight = bitmap.getWidth();
//		int sHeight = bitmap.getHeight();
//		Matrix matrix = new Matrix();
//		float scaleWight = (float) wight / sWight;
//		float scaleHight = (float) height / sHeight;
//		matrix.postScale(scaleWight, scaleHight);
//		Bitmap tempBitmap = Bitmap.createBitmap(bitmap, 0, 0, sWight, sHeight,
//				matrix, false);
//		return tempBitmap;
//	}
//
//	/**
//	 * 用路径取得位图
//	 * 
//	 * @param name
//	 * @return
//	 */
//	public static Bitmap reduceImg(String name, int width, int height) {
//		Bitmap bitmap = null;
//		try {
//			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inJustDecodeBounds = true;
//			bitmap = BitmapFactory.decodeFile(name, options);
//			int be = calculateInSampleSize(options, width, height);// 取得压缩率
//			options.inSampleSize = be;
//			options.inJustDecodeBounds = false;// 在次打开位图 就可以用了
//			bitmap = BitmapFactory.decodeFile(name, options);// 路径取得位图
//
//			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);// 裁剪图片
//		} catch (OutOfMemoryError e) {
//			e.printStackTrace();
//		}
//
//		return bitmap;
//	}
//
//	/**
//	 * 获取采样率
//	 * 
//	 * @param options
//	 * @param reqWidth
//	 * @param reqHeight
//	 * @return
//	 */
//	private static int calculateInSampleSize(BitmapFactory.Options options,
//			int reqWidth, int reqHeight) {
//		// 从BitmapFactory.Options中获取图片的原始高度和宽度
//		final int height = options.outHeight;
//		final int width = options.outWidth;
//		int inSampleSize = 1;
//
//		if (height > reqHeight || width > reqWidth) {
//
//			// 计算原始尺寸与显示尺寸的比例
//			final int heightRatio = Math.round((float) height
//					/ (float) reqHeight);
//			final int widthRatio = Math.round((float) width / (float) reqWidth);
//
//			// 使用两个比例中较小的作为采样比，可以保证图片在两个方向上都满足需求
//			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//
//			// 为了防止一些纵横比异常（很宽或者很长）的图片仍然会占用较大内存，增加
//			// 对显示像素的限制
//			final float totalPixels = width * height;
//
//			// 如果以前面计算到的采样比采样得到的bitmap像素大于请求的像素的两倍，继续
//			// 提高采样比
//			final float totalReqPixelsCap = reqWidth * reqHeight * 2;
//
//			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
//				inSampleSize++;
//			}
//		}
//		return inSampleSize;
//	}
//}
