package com.paiyue.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.paiyue.api.DataSource;
import com.paiyue.api.EachRecord;
import com.paiyue.model.Cat;
import com.paiyue.utils.FileUtils;
import com.paiyue.utils.StringConstants;

/**
 * 存放猫信息的数据文件
 * @author xin.zhang
 *
 */
@Data
public class FileDataSource implements DataSource {

	private String dataFilePath;
	@Override
	public List<Cat> getAllCats() {
		List<Cat> list = new ArrayList<>();
		readAllLinesAsList(list);
		return list;
	}

	@Override
	public Cat getCatById(Long id) {
		// 待实现
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// 待实现

	}
	
	/**
	 * 将数据文件中的所有行读取出来放入list中
	 * @param list
	 */
	private void readAllLinesAsList(final List<Cat> list){
		try {
			System.out.println("数据文件路径:"+dataFilePath);
			File dataFile = new File(dataFilePath);
			FileUtils.eachLine(dataFile, new EachRecord() {
				
				@Override
				public void eachLine(String line) {
					String [] fieldParts = line.split(StringConstants.FILE_FIELD_SEPERATOR.getText());
					Cat cat = new Cat();
					cat.setId(Long.valueOf(fieldParts[0]));
					cat.setName(fieldParts[1]);
					cat.setPrice(Double.valueOf(fieldParts[2]));
					cat.setType(fieldParts[3]);
					cat.setInfoSource(fieldParts[4]);
					list.add(cat);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("文件读取出错",e);
		}
	} 

}
