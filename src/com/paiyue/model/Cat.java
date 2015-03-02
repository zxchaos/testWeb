package com.paiyue.model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
/**
 * 猫模型
 * @author xin.zhang
 */
@Data
@ToString(callSuper=true)
public class Cat implements Serializable{

	private static final long serialVersionUID = 8098762789044653737L;
	private Long id;
	//名字
	private String name;
	//品种
	private String type;
	//价格
	private Double price;
	//信息来源
	private String infoSource;
}
