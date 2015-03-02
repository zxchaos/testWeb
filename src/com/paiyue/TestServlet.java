package com.paiyue;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet(urlPatterns={"/testServlet"}, loadOnStartup=1)
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]>pMap = request.getParameterMap();
		System.out.println("参数:");
		for (Entry<String, String[]> entry : pMap.entrySet()) {
			System.out.println(entry.getKey()+":"+entry.getValue()[0]);
		}
		response.setContentType("text/html;charset=utf-8");
		Writer writer = response.getWriter();
		writer.write("<h1>你好</h1>");
		writer.close();
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("init...");
//		try {
//			Class dataSource = Class.forName("com.paiyue.impl.FileDataSource");
//			Object ds = dataSource.newInstance();
//			Field field = dataSource.getDeclaredField("dataFilePath");
//			String arg="/home/zxchaos/tempFiles/cat_test/cat_info";
//			String fieldName =  field.getName();
//			String setterName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
//			System.out.println("setter Name:"+setterName);
//			Method method = dataSource.getMethod(setterName, String.class);
//			method.invoke(ds, arg);
//			String getterMethodName = "getDataFilePath";
//			Method getter = dataSource.getMethod(getterMethodName);
//			System.out.println(getter.invoke(ds));
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		} catch (NoSuchFieldException e) {
//			throw new RuntimeException(e);
//		} catch (SecurityException e) {
//			throw new RuntimeException(e);
//		} catch (NoSuchMethodException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalArgumentException e) {
//			throw new RuntimeException(e);
//		} catch (InvocationTargetException e) {
//			throw new RuntimeException(e);
//		} catch (InstantiationException e) {
//			throw new RuntimeException(e);
//		}
	}
	
	

}
