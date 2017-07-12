package com.project.base.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.project.user.entity.User;

@ParentPackage("adminBasePackage")
@Action
public class AdminBaseAction extends BaseAction
{
	protected int page;// 当前页
	protected int rows;// 每页显示记录数
	protected String sort;// 排序字段
	protected String order = "asc";// asc/desc
	protected long id;// 主键
	protected String ids;// 主键集合，逗号分割

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public String getSort()
	{
		return sort;
	}

	public void setSort(String sort)
	{
		this.sort = sort;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}
	
	public User getUser(){
		return (User) getSession().get("user");
	}

}
