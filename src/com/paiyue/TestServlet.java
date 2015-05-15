package com.paiyue;

import java.io.IOException;
import java.io.Writer;
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
@WebServlet(urlPatterns = { "/testServlet" }, loadOnStartup = 1)
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 4155088923460265573L;
	private Double rand = Math.random();
	private Integer consume = 70;

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
		//测试-------reset注释
		Map<String, String[]> pMap = request.getParameterMap();
		System.out.println("---rand---" + rand);
		System.out.println("参数:");
		
		for (Entry<String, String[]> entry : pMap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue()[0]);
		}
		if (consume>0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			consume--;
		}
		
		response.setContentType("text/html;charset=utf-8");
		System.out.println(Thread.currentThread().getName()+"---consume---"+consume);
		Writer writer = response.getWriter();
		writer.write("<h1>你好</h1>" + rand);
		writer.close();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("init...");
	}

}
