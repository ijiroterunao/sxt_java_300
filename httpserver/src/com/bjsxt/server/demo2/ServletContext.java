package com.bjsxt.server.demo2;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文
 * @author terunao
 *
 */
public class ServletContext {
	//为每一个servlet取个别名
	// login -->LoginServlet
	private Map<String,Servlet> servlet;
	private Map<String,String> mapping;
	//url -->login
	//  /log -->login
	//  /login -->login
	ServletContext(){
		servlet = new HashMap<String,Servlet>();
		mapping =new HashMap<String,String>();
	}
	
	public Map<String, Servlet> getServlet() {
		return servlet;
	}
	public void setServlet(Map<String, Servlet> servlet) {
		this.servlet = servlet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
}
