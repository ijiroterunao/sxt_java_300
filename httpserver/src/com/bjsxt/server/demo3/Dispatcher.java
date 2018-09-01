package com.bjsxt.server.demo3;

import java.io.IOException;
import java.net.Socket;

import com.bjsxt.util.CloseUtil;

/**
 * 一个请求与响应 就一个对象
 * 
 * @author terunao
 *
 */
public class Dispatcher implements Runnable {
	private Socket client;
	private Request req;
	private Response rep;
	private int code = 200;

	public Dispatcher(Socket client) {
		this.client = client;
		try {
			req = new Request(client.getInputStream());
			rep = new Response(client.getOutputStream());
		} catch (IOException e) {
			// e.printStackTrace();
			code = 500;
			return;
		}

	}

	@Override
	public void run() {
		try {
			Servlet serv = WebApp.getServlet(req.getUrl());
			if(null==serv) {
				this.code=404; //找不到对应的处理
			}else {
				serv.service(req, rep);
			}	
			rep.pushToClient(code);
		} catch (Exception e) {
			e.printStackTrace();
			this.code=500;
		}
		
		try {
			rep.pushToClient(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		CloseUtil.closeAll(client);
	}

}
