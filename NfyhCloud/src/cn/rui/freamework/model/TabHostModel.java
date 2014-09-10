//package cn.rui.freamework.model;
//
//import android.content.Intent;
//import android.view.View;
//
//public class TabHostModel
//{
//	private String	name;
//
//	private int		iconId;
//	private int		hoverIconId;
//
//	private int		backgroundId;
//	private int		hoverBackgroundId;
//
//	private int		imageId;			// 自定义tabview视图中的Icon的R文件的id
//
//	private View	tabView;
//
//	private int		contentViewId;
//	private Intent	contentIntent;
//
//	/**
//	 * @return the name
//	 */
//	public String getName()
//	{
//		return name;
//	}
//
//	/**
//	 * @param name
//	 *            the name to set
//	 */
//	public void setName(String name)
//	{
//		this.name = name;
//	}
//
//	public TabHostModel()
//	{
//	}
//
//	public TabHostModel(String name, int iconId, int backgroundId,
//			int hoverIconId, int hoverBackgroundId, View tabView)
//	{
//		super();
//		this.name = name;
//		this.iconId = iconId;
//		this.backgroundId = backgroundId;
//		this.hoverIconId = hoverIconId;
//		this.hoverBackgroundId = hoverBackgroundId;
//		this.tabView = tabView;
//	}
//
//	/**
//	 * @return the iconId
//	 */
//	public int getIconId()
//	{
//		return iconId;
//	}
//
//	/**
//	 * @param iconId
//	 *            the iconId to set
//	 */
//	public void setIconId(int iconId)
//	{
//		this.iconId = iconId;
//	}
//
//	/**
//	 * @return the backgroundId
//	 */
//	public int getBackgroundId()
//	{
//		return backgroundId;
//	}
//
//	/**
//	 * @param backgroundId
//	 *            the backgroundId to set
//	 */
//	public void setBackgroundId(int backgroundId)
//	{
//		this.backgroundId = backgroundId;
//	}
//
//	/**
//	 * @return the hoverIconId
//	 */
//	public int getHoverIconId()
//	{
//		return hoverIconId;
//	}
//
//	/**
//	 * @param hoverIconId
//	 *            the hoverIconId to set
//	 */
//	public void setHoverIconId(int hoverIconId)
//	{
//		this.hoverIconId = hoverIconId;
//	}
//
//	/**
//	 * @return the hoverBackgroundId
//	 */
//	public int getHoverBackgroundId()
//	{
//		return hoverBackgroundId;
//	}
//
//	/**
//	 * @param hoverBackgroundId
//	 *            the hoverBackgroundId to set
//	 */
//	public void setHoverBackgroundId(int hoverBackgroundId)
//	{
//		this.hoverBackgroundId = hoverBackgroundId;
//	}
//
//	/**
//	 * @return the tabView
//	 */
//	public View getTabView()
//	{
//		return tabView;
//	}
//
//	/**
//	 * @param tabView
//	 *            the tabView to set
//	 */
//	public void setTabView(View tabView)
//	{
//		this.tabView = tabView;
//		this.tabView.setTag(this);
//	}
//
//	/**
//	 * @return the contentIntent
//	 */
//	public Intent getContentIntent()
//	{
//		return contentIntent;
//	}
//
//	/**
//	 * @param contentIntent
//	 *            the contentIntent to set
//	 */
//	public void setContentIntent(Intent contentIntent)
//	{
//		this.contentIntent = contentIntent;
//	}
//
//	/**
//	 * @return the contentViewId
//	 */
//	public int getContentViewId()
//	{
//		return contentViewId;
//	}
//
//	/**
//	 * @param contentViewId
//	 *            the contentViewId to set
//	 */
//	public void setContentViewId(int contentViewId)
//	{
//		this.contentViewId = contentViewId;
//	}
//
//	/**
//	 * @return the imageId
//	 */
//	public int getImageId()
//	{
//		return imageId;
//	}
//
//	/**
//	 * @param imageId
//	 *            the imageId to set
//	 */
//	public void setImageId(int imageId)
//	{
//		this.imageId = imageId;
//	}
//
// }
