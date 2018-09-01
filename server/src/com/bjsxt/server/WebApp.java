package com.bjsxt.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.bjsxt.servlet.Servlet;

public class WebApp {
	private static ServletContext contxt;
	static {

		try {
			// 获取解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 获取解析器
			SAXParser sax = factory.newSAXParser();
			// 指定xml+处理器
			WebHandler web = new WebHandler();
			sax.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("\\WEB_INFO\\web.xml"), web);

			// 将list转成Map
			contxt = new ServletContext();
			Map<String, String> servlet = contxt.getServlet();

			// servlet-name servlet-class
			for (Entity entity : web.getEntityList()) {
				servlet.put(entity.getName(), entity.getClz());
			}

			// url-pattern servlet-name
			Map<String, String> mapping = contxt.getMapping();
			for (Mapping mapp : web.getMappingList()) {
				List<String> urls = mapp.getUrlPattern();
				for (String url : urls) {
					mapping.put(url, mapp.getName());
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public static Servlet getServlet(String url)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if ((null == url) || (url = url.trim()).equals("")) {
			return null;
		}
		// 根据字符串创建对象

		// return contxt.getServlet().get(contxt.getMapping().get(url));
		String name = contxt.getServlet().get(contxt.getMapping().get(url));
		return (Servlet) Class.forName(name).newInstance();

	}
}
