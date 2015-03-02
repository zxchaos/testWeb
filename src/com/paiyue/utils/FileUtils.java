package com.paiyue.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.paiyue.api.EachRecord;

public class FileUtils {
	/**
	 * 将文件制定列转换为map
	 * @param file 要转换成map的文件
	 * @param fieldSep 列之间的分隔符
	 * @param fieldNum 作为map中key的列号
	 * @return 转换完成的map
	 * @throws Exception
	 */
	public static Map<String, String> file2Map(File file, String fieldSep, int fieldNum) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		Map<String, String> map = new HashMap<String, String>();
		while ((line = br.readLine()) != null) {
			String[] array = line.split(fieldSep);
			map.put(array[fieldNum - 1], "");
		}
		br.close();
		return map;
	}
	
	/**
	 * 将文件转换成为map: 按分隔符分割后第一列作为key,其余作为value
	 * @param file 要转换成map的文件
	 * @param fieldSep 列之间的分隔符
	 * @return 转换完成的map
	 * @throws Exception
	 */
	public static Map<String, String> file2Map(File file, String fieldSep) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		Map<String, String> map = new HashMap<String, String>();
		while ((line = br.readLine()) != null) {
			if (StringUtils.isBlank(line)) {
				continue;
			}
			String[] array = line.split(fieldSep);
			map.put(array[0], ArrayUtils.toString(ArrayUtils.subarray(array, 1, array.length)).replace("{", "").replace("}", ""));
		}
		br.close();
		return map;
	}

	public static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeFile(File outputFile, String content) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(outputFile);
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 去除文件中的重复行
	 * 
	 * @param file
	 */
	public static StringBuffer wipeOutDupLine(File file) {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer("");
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			Map<String, String> map = new HashMap<String, String>();
			while ((line = br.readLine()) != null) {
				if (map.get(line) == null) {
					map.put(line, "1");
					sb.append(line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeReader(br);
		}
		return sb;
	}

	public static void write2File(StringBuffer resultBuffer, File resultFile) {
		write2File(resultBuffer.toString(), resultFile);
	}
	
	public static void write2File(String content, File resultFile) {
		FileWriter fw = null;
		try {
			
			if (!resultFile.exists()) {
				resultFile.createNewFile();
			}
			fw = new FileWriter(resultFile, true);
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeWriter(fw);
		}
	}

	public static void closeWriter(FileWriter fw) {
		if (fw != null) {
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 操作文件的每一行 操作方法为定制该方法中的代码为通用代码
	 * @param file
	 * @param eachRecord
	 * @throws IOException
	 */
	public static void eachLine(File file, EachRecord eachRecord) throws IOException {
		BufferedReader br = null;
		String line = "";
		br = new BufferedReader(new FileReader(file));
		while ((line = br.readLine()) != null) {
			eachRecord.eachLine(line);
		}
		closeReader(br);
	}
	
	public static void write2file(File file, Collection<String>collection){
		StringBuffer sb = new StringBuffer("");
		for(Iterator<String>iterator=collection.iterator();iterator.hasNext();){
			sb.append(iterator.next()+"\n");
		}
		write2File(sb, file);
	}
	
	public static void write2File(Map<String, String> map,File outputFile) {
		if (null == map) {
			return;
		}
		StringBuffer appender = new StringBuffer("");
		int counter = 0;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			counter++;
			appender.append(entry.getKey() + "\t" + entry.getValue() + "\n");
			if (counter == 100000) {
				write2File(appender,outputFile);
				appender.delete(0, appender.length());
				counter = 0;
			}
		}
		if (counter != 0) {
			write2File(appender,outputFile);
			appender = null;
		}
		map=null;
	}
	
	public static void writeMapCounter2File(Map<String, Integer> map,File outputFile) {
		if (null == map) {
			return;
		}
		StringBuffer appender = new StringBuffer("");
		int counter = 0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			counter++;
			appender.append(entry.getKey() + "\t" + entry.getValue() + "\n");
			if (counter == 100000) {
				write2File(appender,outputFile);
				appender.delete(0, appender.length());
				counter = 0;
			}
		}
		if (counter != 0) {
			write2File(appender,outputFile);
			appender = null;
		}
		map=null;
	}
	
	/**
	 * 获得目录下所有与模式匹配的文件
	 * @param fileDirPath
	 * @param filePattern
	 * @return
	 */
	public static File[] fileList(String fileDirPath, final String filePattern) {
		File fileDir = new File(fileDirPath);
		File [] files = fileDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
					Pattern pattern = Pattern.compile(filePattern);
					return pattern.matcher(name).matches();
			}
		});
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				String f1Name = o1.getName();
				String f2Name = o2.getName();
				return f1Name.compareTo(f2Name);
			}
		});
		return files;
	}
	
	/**
	 * 获取配置文件properties
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static Properties getConfig() throws FileNotFoundException, IOException {
		String configPath = FileUtils.class.getResource("/").getPath()+"config.properties";
		Properties config = new Properties();
		config.load(new FileReader(new File(configPath)));
		return config;
	}
}
