package com.jackson0714.passjava.study.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学习-用户学习时常表
 * 
 * @author jackson0714
 * @email jackson0585@163.com
 * @date 2020-04-15 17:50:49
 */
@Data
@TableName("sms_study_time")
public class StudyTimeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 题目类型id
	 */
	private Long quesTypeId;
	/**
	 * 用户id
	 */
	private Long memberId;
	/**
	 * 学习时常（分
	 */
	private Integer totalTime;
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
