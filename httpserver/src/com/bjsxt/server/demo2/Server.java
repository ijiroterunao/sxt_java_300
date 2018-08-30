package com.bjsxt.server.demo2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.bjsxt.util.CloseUtil;

/**
 * 创建服务器并启动
 * 
 * @author terunao
 *
 */
public class Server {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";

	private boolean isShutDown = false;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	/**
	 * 启动方法
	 */
	public void start() {
		start(9999);
	}

	/**
	 * 指定端口的启动方法
	 */
	public void start(int port) {

		try {
			server = new ServerSocket(port);
			this.receive();
		} catch (IOException e) {
			// e.printStackTrace();
			stop();
		}

	}

	/**
	 * 接受客户端
	 */
	private void receive() {
		try {
			while (!isShutDown) {
				new Thread(new Dispatcher(server.accept())).start();
			}

		} catch (IOException e) {
			// e.printStackTrace();
			stop();
		}
	}

	/**
	 * 停止服务端
	 */
	public void stop() {
		isShutDown = true;
		CloseUtil.closeAll(server);
	}
}
