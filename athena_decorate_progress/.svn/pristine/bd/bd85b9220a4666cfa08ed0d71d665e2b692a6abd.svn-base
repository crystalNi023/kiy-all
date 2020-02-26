package com.dec.pro.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 反射取对象的属性和值
 * @author Administrator
 *
 */
public class GetField {
	private static Logger logger=Logger.getLogger(GetField.class);
	//java中遍历实体类，获取属性名和属性值
	/**
	 * 根据对象获取以属性为key，值为value的map
	 * @param model
	 * @throws Exception
	 */
    public static Map<String , Object> getOTM(String msg,Object model) throws Exception{
		Map<String , Object> map = new HashMap<String , Object>();
    	try {
            for (Field field : model.getClass().getDeclaredFields()) {
            	  field.setAccessible(true);
                  String name = field.getName();
                  Object value = field.get(model);
                  if(value != null && !"".equals(value)){//获取值不为空的属性
                  	map.put(name, value);
                  }
            }
		} catch (Exception e) {
			logger.error("异常信息："+e.getMessage());
			throw new RuntimeException("系统异常，稍后重试！");
		}
    	if (msg!=null) {
    		logger.info(model.getClass().getName()+msg+map);
		}else{
        logger.info(model.getClass().getName()+" 查询条件为："+map);
		}
        return map;
    }
    /**
     * 根据传入数据类型返回map
     * @param o
     * @return
     * @throws Exception
     */
    public static Map<String , Object> getMap(Object o) throws Exception{
    	Map<String , Object> map = new HashMap<String , Object>();
    	if(o!=null)
    		map.put(o.toString(), o);
    	return map;
    }
}
