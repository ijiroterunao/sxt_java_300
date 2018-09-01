package com.bjsxt.reflect;

import com.bjsxt.server.demo3.Servlet;

/**
 * 创建实例
 * 
 * @author terunao 2
 */
public class Demo02 {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> clz = Class.forName("com.bjsxt.server.demo03.LoginServlet");
		//
		Servlet ser = (Servlet) clz.newInstance();
		// return ser;
	}

}