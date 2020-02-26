package com.dec.pro.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.mapper.BaseMapper;
import com.dec.pro.util.Page;


@Service
public abstract class BaseService<T extends Serializable> {
	private static Logger logger=Logger.getLogger(BaseService.class);
	
	@Autowired
	protected BaseMapper<T> mapper;
	
	public Page<T> pages=new  Page<T>(); 
	/**
	 * 插入实体
	 * 
	 * @param t
	 * @return
	 */
	public int add(T t) throws Exception{
		int count;
		try {
			count = mapper.add(t);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}

	/**
	 * 插入实体（批量）
	 * 
	 * @param t
	 * @return
	 */
	public int addBatch(List<T> list) throws Exception{
		int count;
		try {
			count = mapper.addBatch(list);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}

	/**
	 * 修改数据（实体）
	 * 
	 * @param map
	 * @return
	 */
	public int update(T t) throws Exception{
		int count;
		try {
			count = mapper.update(t);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}

	/**
	 * 批量修改数据（实体）
	 * 
	 * @param map
	 * @return
	 */
	public int updateBatch(List<T> list) throws Exception{
		int count;
		try {
			count = mapper.updateBatch(list);
		} catch (Exception e) {
			logger.error("数据库异常信息："+e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}
	
	/**
	 * 删除单个数据（实体）
	 * 
	 * @param id
	 * @return
	 */
	public int delete(String id) throws Exception{
		int count;
		try {
			count = mapper.delete(id);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}

	/**
	 * 批量删除数据（实体）
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteBatch(List<String> ids) throws Exception{
		int count;
		try {
			count = mapper.deleteBatch(ids);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return count;
	}

	/**
	 * 获取单个实体
	 * 
	 * @param id
	 * @return
	 */
	public T getOne(String id) throws Exception{
		T one;
		try {
			one = mapper.getOne(id);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return one;
	}
	/**
	 * 获取多个实体
	 * 
	 * @param id
	 * @return
	 */
	public List<T> getList(Map<String,Object> map) throws Exception{
		List<T> list;
		try {
			list = mapper.getList(map);
		} catch (Exception e) {
			logger.error("数据库异常信息：" + e.getMessage());
			throw new RuntimeException("数据异常，请联系管理员！");
		}
		return list;
	}
	 /**
     * 分页方法
     *
     * @param page
     * @param map
     * @return
     */
    public Page<T> getPage(Page<T> page, Map<String, Object> map) throws Exception{
        map.put("startRow", page.getStartRow());
        map.put("pageSize", page.getPageSize());
        map.put("orderBy", page.getOrderBy());
        Number totalItems = (Number) mapper.getCount(map);
        if (totalItems != null) {
            page.setTotalItems(totalItems.intValue());
            List<T> list;
            try {
            	list = mapper.getList(map);
    		} catch (Exception e) {
    			logger.error("数据库异常信息：" + e.getMessage());
    			throw new RuntimeException("数据异常，请联系管理员！");
    		}
            page.setResult(list);
        }
        return page;
    }
}
