package com.project.base.entity;

import java.io.Serializable;
import java.util.List;

public class DataGrid<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long total;// 总记录数
	private List<T> rows;// 每行记录

	public long getTotal()
	{
		return total;
	}

	public void setTotal(long total)
	{
		this.total = total;
	}

	public List<T> getRows()
	{
		return rows;
	}

	public void setRows(List<T> rows)
	{
		this.rows = rows;
	}

}
