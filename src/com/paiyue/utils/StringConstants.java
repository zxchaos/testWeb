package com.paiyue.utils;

/**
 * 系统中字符串常量配置
 * @author xin.zhang
 *
 */
public enum StringConstants {
	FILE_FIELD_SEPERATOR("\t"),//文件中各字段的分隔符
	CONFIG_DATASOURCE_FIELD_FLAG("@"),//配置文件中的datasource配置的属性标识符
	CONFIG_FIELD_NAME_VALUE_SEPERATOR(">"),//配置文件中的datasource配置中的实现类属性的名称和值的分隔符
	CONFIG_FIELDS_SEPERATOR(","),//配置文件中的datasource实现类的配置的多个属性之间的分隔符
	;
	private StringConstants(String text){
		this.text = text;
	}
	private String text;
	public String getText(){
		return this.text;
	}
}
