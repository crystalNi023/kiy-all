package com.dec.pro.util;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 分页查询实体类
 * @author Administrator
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
	private int pageNo = 1; // 从第几页开始查询
	private int pageSize = 10; // 每页条数
	private int totalPage; // 总页数
	private List<T> result = new ArrayList<T>();// 返回的实体数据
	private String orderBy; // 排序字段
	private int totalItems; // 总条数

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int getPrevPage() {
		return pageNo == 1 ? 1 : pageNo - 1;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int getNextPage() {
		return pageNo == totalPage ? totalPage : pageNo + 1;
	}

	/**
	 * 起始条数
	 * 
	 * @return
	 */
	public int getStartRow() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 最后一条
	 * 
	 * @return
	 */
	public int getEndRow() {
		return pageNo * pageSize >= totalItems ? totalItems : pageNo * pageSize;
	}

	/**
	 * 是否为第一页
	 * 
	 * @return
	 */
	public boolean getIsFirstPage() {
		return pageNo == 1;
	}

	/**
	 * 是否为最后一页
	 * 
	 * @return
	 */
	public boolean getIsLastPage() {
		return pageNo == totalPage;
	}

	/**
	 * 设置总条数和总页数
	 * 
	 * @param totalItems
	 */
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
		this.totalPage = totalItems % pageSize == 0 ? totalItems / pageSize
				: totalItems / pageSize + 1;
	}
	public List<T> getResult(){
		return result;
	}
}
