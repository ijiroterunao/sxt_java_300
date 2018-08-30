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
public class Server6 {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";

	public static void main(String[] args) {
		Server6 server = new Server6();
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
			Servlet serv = new Servlet();
			Request req = new Request(client.getInputStream());
			Response rep = new Response(client.getOutputStream());
			serv.service(req, rep);
			rep.pushToClient(200);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {

	}
}
