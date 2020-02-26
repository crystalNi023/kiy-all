package com.dec.pro.mapper;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BaseMapper<T extends Serializable> {
	
	/**
	 * 插入实体
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t);
	
	/**
	 * 插入实体(批量)
	 * 
	 * @param t
	 * @return
	 */
	public int addBatch(List<T> list);
	
	/**
	 * 修改数据
	 * 
	 * @param t
	 * @return
	 */
	public int update(T t);
	
	/**
	 * 批量修改
	 * 
	 * @param List<T>
	 * @return
	 */
	public int updateBatch(List<T> list);
	
	/**
	 * 删除单个数据
	 * 
	 * @param t
	 * @return
	 */
	public int delete(@Param("id") String id);
	
	/**
	 * 删除多个数据
	 * 
	 * @param List<Long> ids
	 * @return
	 */
	public int deleteBatch(List<String> ids);
	
	/**
	 * 查询单个
	 * 
	 * @param id
	 * @return
	 */
	public T getOne(@Param("id") String id);
	/**
	 * 列表操作
	 * 
	 * @param map
	 * @return
	 */
	public List<T> getList(Map<String, Object> map);

	/**
	 * 计数
	 * 
	 * @param map
	 * @return
	 */
	public int getCount(Map<String, Object> map);

}
