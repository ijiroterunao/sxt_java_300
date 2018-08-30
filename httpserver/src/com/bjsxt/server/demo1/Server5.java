package com.bjsxt.server.demo1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 创建服务器并启动
 * 
 * @author terunao
 *
 */
public class Server5 {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";

	public static void main(String[] args) {
		Server5 server = new Server5();
		server.start();
	}

	public void start() {

		try {
			server = new ServerSocket(9999);
			this.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void receive() {
		try {
			Socket client = server.accept();
			// 请求
			Request req = new Request(client.getInputStream());
			// 响应
			Response rep = new Response(client.getOutputStream());
			
			rep.pushToClient(200);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {

	}
}
