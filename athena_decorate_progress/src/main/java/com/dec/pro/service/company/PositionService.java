package com.dec.pro.service.company;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dec.pro.entity.company.Position;
import com.dec.pro.mapper.company.PositionMapper;
import com.dec.pro.service.BaseService;
import com.dec.pro.util.Check;
import com.dec.pro.util.GetField;
import com.dec.pro.util.Page;

@Service
public class PositionService extends BaseService<Position>{
	@Autowired
	private PositionMapper positionMapper;
	/**
	 * 添加定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public Position addPosition(Position position) throws Exception{
		if(Check.isEmpty(position.getConstructorId())) throw new RuntimeException("请关联施工员！");
		int count = this.add(position);
		if(count!=1) throw new RuntimeException("定位信息添加失败，稍后重试！");
		return position;
	}
	/**
	 * 批量添加定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public int addBatchPosition(List<Position> listPosition) throws Exception{	
		int count = this.addBatch(listPosition);
		return count;
	}
	/**
	 * 删除定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public int deletePosition(String id) throws Exception{
		int count = this.delete(id);
		if(count > 1) throw new RuntimeException("数据误操作，请联系管理员！");
		if(count != 1) throw new RuntimeException("定位信息移除失败，稍后重试！");
		return count;
	}
	/**
	 * 修改定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public int updatePosition(Position position) throws Exception{
		if(Check.isEmpty(position.getId())) throw new RuntimeException("id不能为空，请联系管理员！");
		int count = this.update(position);
		if(count != 1) throw new RuntimeException("定位信息修改失败，稍后重试！");
		return count;
	}
	/**
	 * 分页查询定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public Page<Position> getPageListPosition(Position position) throws Exception{
		pages.setPageNo(position.getPageNo());
		pages.setPageSize(position.getPageSize());
		Page<Position> resultPage;
		resultPage = getPage(pages, GetField.getOTM(" 分页查询定位信息:",position));//按条件查询
		return resultPage;
	}
	/**
	 * 获取单个定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public Position getOnePosition(String id) throws Exception{
		return this.getOne(id);
	}
	/**
	 * 根据时间，员工id获取定位信息
	 * @param position
	 * @return
	 * @throws Exception
	 */
	public List<Position> getConstructorIdOneDayPosition(String constructorId,Date date) throws Exception{
		return positionMapper.getConstructorIdOneDayPosition(constructorId,date);
	}
}
