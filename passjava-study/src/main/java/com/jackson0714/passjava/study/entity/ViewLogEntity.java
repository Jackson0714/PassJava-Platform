package com.jackson0714.passjava.study.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学习-用户学习浏览记录表
 * 
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-15 17:50:49
 */
@Data
@TableName("sms_view_log")
public class ViewLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 题目id
	 */
	private Long quesId;
	/**
	 * 题目类型id
	 */
	private Long quesType;
	/**
	 * 用户id
	 */
	private Long memberId;
	/**
	 * 删除标记（0-正常，1-删除）
	 */
	private Integer delFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
