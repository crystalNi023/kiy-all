package com.dec.pro.entity.project;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import com.dec.pro.entity.BaseEntity;
/**
 * 项目资料
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Project extends BaseEntity{
	private static final long serialVersionUID = -2001395634791901788L;
	private String comId;// 公司id
	private String cusId;// 客户id
    private String type;// 装修类型 1.基装  2整装
    @DateTimeFormat(pattern="yyyy-MM-dd")	
    private Date planStartTime;// 计划开工时间
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date planEndTime;// 计划完成时间
    private int projectStatus;//项目  1：  2：启动中3：已完成（默认2）
    private List<Procedure> listProcedure;//进度流程集合
    //返回字段
    private String customerName;//客户姓名
    private String customerPhone;//客户电话
    private String address;//工程地址
    private int process;//进度
    private int timeAssess;//动态时间评估
    private String designId;//装修设计师id
    private String design;//装修设计师
    private String designPhone;//装修设计师电话
    private String decorationId;//项目经理id
    private String decoration;//项目经理
    private String decorationPhone;//项目经理电话
    private String spotId;//现场负责人id
    private String spot;//现场负责人
    private String spotPhone;//现场负责人电话  
    private String constructorId;//施工员id
    private String constructor;//施工员
    private String constructorPhone;//施工员电话  
    private int cameraCount;//项目摄像头数量
    //PC新消息返回字段
    private int newsType;// 新消息类型：0：暂无新消息 1:进度流程消息 2：需求变更消息 3：进度流程和需求变更消息 
    private int newsNumber;//新消息条数
    private String userId;//查询带的参数
}
