package com.bjsxt.server.demo1;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author terunao
 *
 */
public class Request {
	private String method;
	private String url;
	private Map<String, List<String>> parameterMapValues;
	public static final String CRLF = "\r\n";
	private InputStream is;
	private String requestInfo;

	public Request() {
		method = "";
		url = "";
		parameterMapValues = new HashMap<String, List<String>>();
	}

	public Request(InputStream is) {
		this();
		this.is = is;
		try {
			byte[] data = new byte[20480];
			int len = is.read(data);
			requestInfo = new String(data, 0, len);
		} catch (Exception e) {
			return;
		}
		// 分析请求信息
		parseRequestInfo();
	}

	private void parseRequestInfo() {
		if (null == requestInfo || (requestInfo = requestInfo.trim()).equals("")) {
			return;
		}
		/**
		 * ===================================== 从信息的首行分解出 :请求方式 请求路径 请求参数(get可能存在)
		 * 如:GET /index.html?name=123&pwd=5456 HTTP/1.1
		 * 
		 * 如果为post方式，请求参数可能在 最后正文中
		 * 
		 * 思路: 1)请求方式 :找出第一个/ 截取即可 2)请求资源:找出第一个/ HTTP/
		 * =====================================
		 */
		String paramString = ""; // 接收请求参数

		// 1、获取请求方式
		String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));
		int idx = requestInfo.indexOf("/");
		this.method = firstLine.substring(0, idx).trim();
		String urlStr = firstLine.substring(idx, firstLine.indexOf("HTTP/")).trim();
		if (this.method.equalsIgnoreCase("post")) {
			this.url = urlStr;
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
		} else if (this.method.equalsIgnoreCase("get")) {
			if (urlStr.contains("?")) { // 是否存在参数
				String[] urlArray = urlStr.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1];// 接收请求参数
			} else {
				this.url = urlStr;
			}
		}

		// 2、将请求参数封装到Map中
		// 不存在请求参数
		if (paramString.equals("")) {
			return;
		}
		parseParams(paramString);
	}

	private void parseParams(String paramString) {
		// 分割 将字符串转换为数组
		StringTokenizer token = new StringTokenizer(paramString, "&");
		while (token.hasMoreTokens()) {
			String keyValue = token.nextToken();
			String[] keyValues = keyValue.split("=");
			if (keyValues.length == 1) {
				keyValues = Arrays.copyOf(keyValues, 2);// 如果为空，就添加一个内容为"null"的字符串
				keyValues[1] = null;
			}

			String key = keyValues[0].trim();
			String value = null == keyValues[1] ? null : decode(keyValues[1].trim(),"utf-8");
			// 转换为Map
			if (!parameterMapValues.containsKey(key)) {
				parameterMapValues.put(key, new ArrayList<String>());
			}

			List<String> values = parameterMapValues.get(key);
			values.add(value);
		}
	}

	private String decode(String value,String code) {
		try {
			return java.net.URLDecoder.decode(value, code);
		}catch(UnsupportedEncodingException e ){
			
		}
		return null;
	}
	
	/**
	 * 根据页面的name获取对应的多个值
	 * 
	 * @param args
	 */
	public String[] getParameterValues(String name) {
		List<String> values = null;
		if ((values = parameterMapValues.get(name)) == null) {
			return null;
		} else {
			return values.toArray(new String[0]);
		}
	}

	/**
	 * 根据页面的name获取对应的值
	 * 
	 * @param args
	 */
	public String getParameter(String name) {
		String[] values = getParameterValues(name);
		if (null == values) {
			return null;
		}
		return values[0];
	}

	public String getUrl() {
		return url;
	}

	
}
