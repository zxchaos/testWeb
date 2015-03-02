package com.paiyue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;

import com.paiyue.api.DataSource;
import com.paiyue.model.Cat;
import com.paiyue.utils.FileUtils;
import com.paiyue.utils.StringConstants;

/**
 * Servlet implementation class CatServlet
 */
@Data
@WebServlet(urlPatterns={"/CatServlet"}, loadOnStartup=5)
public class CatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Properties config = FileUtils.getConfig();
			String dataSourceConfig = config.getProperty("dataSource");
			String dataSourceImpl = "";
			if (dataSourceConfig.contains(StringConstants.CONFIG_DATASOURCE_FIELD_FLAG.getText())) {
				String[] dataSourceParts = dataSourceConfig.split(StringConstants.CONFIG_DATASOURCE_FIELD_FLAG.getText());
				String fields = dataSourceParts[0];
				dataSourceImpl = dataSourceParts[1];
				Class dataSourceClass = Class.forName(dataSourceImpl);
				dataSource = (DataSource)dataSourceClass.newInstance();
				if (fields.contains(StringConstants.CONFIG_FIELDS_SEPERATOR.getText())) {//当datasource有多个属性配置时
					String [] fieldsParts = fields.split(StringConstants.CONFIG_FIELDS_SEPERATOR.getText());
					for (int i = 0; i < fieldsParts.length; i++) {
						setFieldValue(fieldsParts[i], dataSourceClass);
					}
				}else {
					setFieldValue(fields, dataSourceClass);
				}
				
				List<Cat> list = dataSource.getAllCats();
				System.out.println("集合大小:"+list.size());
				System.out.println("初始化完毕");
			}else {
				dataSourceImpl=dataSourceConfig;
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("配置文件未找到",e);
		} catch (IOException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (InstantiationException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (SecurityException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("初始化失败",e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("初始化失败",e);
		}
	}

	/**
	 * 设置dataSource实例中的属性的值
	 * @param field
	 * @param dataSourceClass
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void setFieldValue(String field, Class dataSourceClass) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		String [] fieldParts = field.split(StringConstants.CONFIG_FIELD_NAME_VALUE_SEPERATOR.getText());
		String fieldName = fieldParts[0];
		String setterName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
		Method setter = dataSourceClass.getMethod(setterName, String.class);
		setter.invoke(dataSource, fieldParts[1]);
	}
	
	

}
