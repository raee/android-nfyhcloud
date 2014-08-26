package cn.rui.framework.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class TabView extends LinearLayout
{

	// private Intent intent;
	// private int contentViewId;
	//
	// private int hoverIconId;
	// private int hoverBackroundImageId;
	// private int iconId;

	private int			iconId, backgoundId, hoverIconId, hoverBackgroundId;
	private String		title;

	private Intent		intent;
	private int			contentViewId;

	private ImageView	imgIcon;
	private TextView	tvTitle;

	public TabView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		loadLayout(context);
		this.imgIcon = getIconView();
		this.tvTitle = getTitleView();
	}

	public abstract void loadLayout(Context context);

	public abstract ImageView getIconView();

	public abstract TextView getTitleView();

	/**
	 * @return the iconId
	 */
	public int getIconId()
	{
		return iconId;
	}

	/**
	 * @param iconId
	 *            the iconId to set
	 */
	public void setIconId(int iconId)
	{
		this.iconId = iconId;
		this.imgIcon.setImageResource(iconId);
	}

	/**
	 * @return the backgoundId
	 */
	public int getBackgoundId()
	{
		return backgoundId;
	}

	/**
	 * @param backgoundId
	 *            the backgoundId to set
	 */
	public void setBackgoundId(int backgoundId)
	{
		this.backgoundId = backgoundId;
		this.setBackgroundResource(backgoundId);
	}

	/**
	 * @return the hoverIconId
	 */
	public int getHoverIconId()
	{
		return hoverIconId;
	}

	/**
	 * @param hoverIconId
	 *            the hoverIconId to set
	 */
	public void setHoverIconId(int hoverIconId)
	{
		this.hoverIconId = hoverIconId;
	}

	/**
	 * @return the hoverBackgroundId
	 */
	public int getHoverBackgroundId()
	{
		return hoverBackgroundId;
	}

	/**
	 * @param hoverBackgroundId
	 *            the hoverBackgroundId to set
	 */
	public void setHoverBackgroundId(int hoverBackgroundId)
	{
		this.hoverBackgroundId = hoverBackgroundId;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
		this.tvTitle.setText(title);
	}

	/**
	 * @return the intent
	 */
	public Intent getIntent()
	{
		return intent;
	}

	/**
	 * @param intent
	 *            the intent to set
	 */
	public void setIntent(Intent intent)
	{
		this.intent = intent;
	}

	/**
	 * @return the contentViewId
	 */
	public int getContentViewId()
	{
		return contentViewId;
	}

	/**
	 * @param contentViewId
	 *            the contentViewId to set
	 */
	public void setContentViewId(int contentViewId)
	{
		this.contentViewId = contentViewId;
	}

	//
	// // 1、标题 2、背景图片3、图标
	//
	// public abstract TextView getTitle();
	//
	// public abstract ImageView getIcon();
	//
	// public abstract Drawable getBackgroundImage();
	//
	// /**
	// * @return the intent
	// */
	// public Intent getIntent()
	// {
	// return intent;
	// }
	//
	// /**
	// * @param intent
	// * the intent to set
	// */
	// public void setIntent(Intent intent)
	// {
	// this.intent = intent;
	// }
	//
	// /**
	// * @return the contentViewId
	// */
	// public int getContentViewId()
	// {
	// return contentViewId;
	// }
	//
	// /**
	// * @param contentViewId
	// * the contentViewId to set
	// */
	// public void setContentViewId(int contentViewId)
	// {
	// this.contentViewId = contentViewId;
	// }
	//
	// /**
	// * @return the hoverIconId
	// */
	// public int getHoverIconId()
	// {
	// return hoverIconId;
	// }
	//
	// /**
	// * @param hoverIconId
	// * the hoverIconId to set
	// */
	// public void setHoverIconId(int hoverIconId)
	// {
	// this.hoverIconId = hoverIconId;
	// }
	//
	// /**
	// * @return the hoverBackroundImageId
	// */
	// public abstract View getHoverBackroundImageView();
	//
	// /**
	// * @return the hoverBackroundImageId
	// */
	// public int getHoverBackroundImageId()
	// {
	// return hoverBackroundImageId;
	// }
	//
	// /**
	// * @param hoverBackroundImageId
	// * the hoverBackroundImageId to set
	// */
	// public void setHoverBackroundImageId(int hoverBackroundImageId)
	// {
	// this.hoverBackroundImageId = hoverBackroundImageId;
	// }
	//
	// /**
	// * @return the iconId
	// */
	// public int getIconId()
	// {
	// return iconId;
	// }
	//
	// public void setIconId(int iconid)
	// {
	// this.iconId = iconid;
	// }
	//
	// public abstract void setIcon(int resId);

}
