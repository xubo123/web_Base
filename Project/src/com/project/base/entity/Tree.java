package com.project.base.entity;

import java.io.Serializable;
import java.util.List;

public class Tree implements Serializable
{
	private static final long serialVersionUID = 1L;

	private long id;
	private String text;
	private String state = "open";// open,closed
	private boolean checked = false;
	private Object attributes;
	private List<Tree> children;
	private String iconCls;
	private long pid;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getPid()
	{
		return pid;
	}

	public void setPid(long pid)
	{
		this.pid = pid;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public Object getAttributes()
	{
		return attributes;
	}

	public void setAttributes(Object attributes)
	{
		this.attributes = attributes;
	}

	public List<Tree> getChildren()
	{
		return children;
	}

	public void setChildren(List<Tree> children)
	{
		this.children = children;
	}

	public String getIconCls()
	{
		return iconCls;
	}

	public void setIconCls(String iconCls)
	{
		this.iconCls = iconCls;
	}

	@Override
	public String toString()
	{
		return "Tree [id=" + id + ", text=" + text + ", state=" + state + ", checked=" + checked + ", attributes=" + attributes + ", children=" + children + ", iconCls=" + iconCls + ", pid=" + pid + "]";
	}

}
