package cn.rui.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 图像处理建造者
 * 
 * @author 陈睿
 * 
 */
public class BitmapBuilder
{
	private Bitmap	bmp;
	private int		srcWidth, srcHeight, width, height;
	private int		color	= Color.WHITE;
	private int		radius	= 0;

	public BitmapBuilder(Bitmap bmp)
	{
		this.bmp = bmp;
		init();
	}

	public BitmapBuilder(Drawable drawable)
	{
		this.bmp = toBitmap(drawable);
		init();
	}

	private void init()
	{

		this.srcWidth = bmp.getWidth();
		this.srcHeight = bmp.getHeight();
		this.width = this.srcWidth;
		this.height = this.srcHeight;
	}

	public BitmapBuilder setColor(int color)
	{
		this.color = color;
		return this;
	}

	/**
	 * 转换为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap toBitmap(Drawable drawable)
	{
		BitmapDrawable bmpdb = (BitmapDrawable) drawable;
		return bmpdb.getBitmap();
	}

	/**
	 * 转换为Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable toDrawable(Bitmap bitmap)
	{
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		w = (int) (w + w * 0.8);
		h = (int) (h + h * 0.8);

		bitmap = zoomDrawable(bitmap, w, h);
		BitmapDrawable dw = new BitmapDrawable(bitmap);

		return dw;
	}

	/**
	 * 缩放图片
	 * 
	 * @param drawable
	 *            资源
	 * @param w
	 *            新的宽度
	 * @param h
	 *            新的高度
	 * @return
	 */
	public static Bitmap zoomDrawable(Bitmap bmp, int w, int h)
	{
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix,
				true);
		return newbmp;
	}

	/**
	 * 圆角
	 * 
	 * @param radius
	 *            圆角半径
	 * @return
	 */
	public BitmapBuilder buildRadius(int radius)
	{
		this.radius = radius;
		// 新建一个图像保持结果的图像
		Bitmap out = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		// 新建一个画布
		Canvas canvas = new Canvas(out); // 画在新建的图像上

		// 新建一个油漆桶
		Paint pen = new Paint();
		pen.setColor(color); // 设置颜色

		// 新建矩形
		Rect rect = new Rect(0, 0, width, height);
		RectF rectf = new RectF(rect);

		// 画一个圆角矩形
		canvas.drawRoundRect(rectf, radius, radius, pen);

		// 设置油漆桶的
		pen.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bmp, rect, rect, pen);

		setBitmap(out);

		return this;
	}

	/**
	 * 描边
	 * 
	 * @param size
	 *            描边大小
	 * @return
	 */
	public BitmapBuilder buildStroke(int size)
	{
		// 加宽加高
		this.width = srcWidth + size;
		this.height = srcHeight + size;

		Bitmap out = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		Canvas canvas = new Canvas(out);

		Paint pen = new Paint();
		pen.setColor(color);

		// 画个矩形
		Rect rect = new Rect(0, 0, width, height);
		RectF rectf = new RectF(rect);

		// 画描边的底色
		canvas.drawRoundRect(rectf, radius, radius, pen);

		// === ===
		Paint tcPen = new Paint();
		tcPen.setColor(Color.RED);

		// 画个填充图像的矩形
		Rect tcRect = new Rect(size, size, srcWidth, srcHeight);

		// 画个填充的图像
		tcPen.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));
		canvas.drawBitmap(bmp, null, tcRect, tcPen);
		setBitmap(out);

		return this;
	}

	/**
	 * 设置当前的图像为新的图像
	 * 
	 * @param out
	 *            新的图像
	 */
	private void setBitmap(Bitmap out)
	{
		this.bmp = out;
	}

	public Bitmap getBitmap()
	{
		return bmp;
	}

	public Drawable getDrawable()
	{
		return toDrawable(bmp);
	}

	// dip转换成px
	public static int dip2px(Context context, float dipValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public void save()
	{

	}

	/**
	 * 图片加圆角
	 * 
	 * @param bitmap
	 * @param pixels
	 *            圆角半径
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels)
	{
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);

		// 新建一个画布
		Canvas canvas = new Canvas(output);

		// 颜色
		final int color = 0xff424242;

		// 颜料
		final Paint paint = new Paint();

		// 颜料设置颜色
		paint.setColor(color);

		// 新建一个矩形
		final Rect rect = new Rect(0, 0, width, height);

		// 转换矩形
		final RectF rectF = new RectF(rect);

		// 圆角大小
		final float roundPx = pixels;

		// 设置抗锯齿
		paint.setAntiAlias(true);

		// 为画布涂上颜色
		canvas.drawARGB(0, 0, 0, 0);

		// 在画布上画个矩形
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}

	/**
	 * 
	 * @param drawable
	 * @param pixels
	 * @return
	 */
	public static Drawable toRoundCorner(Drawable drawable, int pixels)
	{
		Bitmap bmp = drawableToBitmap(drawable);
		bmp = toRoundCorner(bmp, pixels);
		bmp = toStroke(bmp, Color.WHITE, 3);
		bmp = toStroke(bmp, Color.BLACK, 1);
		int w = bmp.getWidth();
		int h = bmp.getHeight();
		return bitmapToDrawable(bmp, w, h);
	}

	/**
	 * 加边框
	 * 
	 * @param bmp
	 * @param color
	 * @param radius
	 * @return
	 */
	public static Bitmap toStroke(Bitmap bmp, int color, int radius)
	{
		int oldW = bmp.getWidth();
		int oldH = bmp.getHeight();
		int w = oldW + radius;
		int h = oldH + radius;

		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		// 新建一个画布
		Canvas canvas = new Canvas(output);

		// 颜料
		final Paint paint = new Paint();
		paint.setColor(color);

		// 设置抗锯齿
		paint.setAntiAlias(true);
		// 新建一个矩形
		final Rect rect = new Rect(0, 0, w, h);

		// 转换矩形
		final RectF rectF = new RectF(rect);

		// 在画布上画个矩形
		canvas.drawRoundRect(rectF, radius * 3, radius * 3, paint);
		//
		// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		//
		Rect mrect = new Rect(radius, radius, oldW, oldH);
		canvas.drawBitmap(bmp, null, mrect, paint);
		return output;

	}

	/**
	 * drawable转成bitmap
	 * 
	 * @param dw
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable)
	{
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}

	/**
	 * bitmap转drawable
	 * 
	 * @param bm
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bmp, float width,
			float height)
	{
		int w = bmp.getWidth();
		int h = bmp.getHeight();
		// w = w + (int) (w * 0.5);
		// h = h + (int) (h * 0.5);

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = width / w;
		float scaleHeight = height / h;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix,
				true);

		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
		return bmd;
	}

	/**
	 * 获取缩略图
	 * 
	 * @param path
	 *            图片所在路径
	 * @param width
	 *            缩略图宽度
	 * @param height
	 *            缩略图高度
	 * @return
	 */
	public static Bitmap getBitmapThumbnail(String path, int width, int height)
	{
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		opts.inSampleSize = Math.max((int) (opts.outHeight / (float) height),
				(int) (opts.outWidth / (float) width));
		opts.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;
	}

	/**
	 * 获取缩略图
	 * 
	 * @param bmp
	 *            图片
	 * @param width
	 *            缩略图宽度
	 * @param height
	 *            缩略图高度
	 * @return
	 */
	public static Bitmap getBitmapThumbnail(Bitmap bmp, int width, int height)
	{
		Bitmap bitmap = null;
		if (bmp != null)
		{
			int bmpWidth = bmp.getWidth();
			int bmpHeight = bmp.getHeight();
			if (width != 0 && height != 0)
			{
				Matrix matrix = new Matrix();
				float scaleWidth = ((float) width / bmpWidth);
				float scaleHeight = ((float) height / bmpHeight);
				matrix.postScale(scaleWidth, scaleHeight);
				bitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,
						matrix, true);
			}
			else
			{
				bitmap = bmp;
			}
		}
		return bitmap;
	}

}
