package com.dec.pro.entity.company;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import com.dec.pro.entity.BaseEntity;

/**
 * 定位信息
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Position extends BaseEntity{
	
	private static final long serialVersionUID = 2981806229782649806L;
	private String  constructorId			;//施工员id
	private int  type            ;//类型  (1.轨迹 2.上班打卡位置3.下班打卡位置)
	private BigDecimal  longitude   ;//经度
	private BigDecimal  latitude    ;//纬度
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date     positionTime;//定位时间
}
