package com.paiyue.api;

import java.util.List;

import com.paiyue.model.Cat;
/**
 * 存放猫对象的数据源接口
 * @author xin.zhang
 *
 */
public interface DataSource {
	//获得所有猫数据集合
	List<Cat> getAllCats();
	
	//根据猫的id获得对应的猫对象
	Cat getCatById(Long id);
	
	//根据猫的id删除对应的猫对象记录
	void deleteById(Long id);
}
