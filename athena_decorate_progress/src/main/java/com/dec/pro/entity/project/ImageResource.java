package com.dec.pro.entity.project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.dec.pro.entity.BaseEntity;
/**
 * 图片资源
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ImageResource extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -384994918312497850L;
	private String cusId;//客户id
	private String resourceName;//图片名称
    private String resourceUrl;//图片链接
    private double resourceSize;//图片大小（M）--保留1位小数
    private int resourceType;//资源类型   1:合同 2:设计图 3:营业执照 4:需求变更 5:意见反馈

}
