/**
 * 
 */
package com.yixin.nfyh.cloud.model.view;

/**
 * 满意度调查实体模型
 * 
 * @author Chenrui
 * 
 */
public class MydcViewModel
{
	private String	title, createDate, collecttime, id;
	private int		state;

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	public String getCollecttime()
	{
		return collecttime;
	}

	public void setCollecttime(String collecttime)
	{
		this.collecttime = collecttime;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
