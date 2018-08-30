package com.bjsxt.server.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器并启动
 * @author terunao
 *
 */
public class Server2 {
	private ServerSocket server;
	
	public static void main(String[] args) {
		Server2 server = new Server2();
		server.start();
		
	}

	public void start() {
		
		try {
			server = new ServerSocket(8888);
			this.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void receive() {
		try {
			Socket client = server.accept();
			StringBuilder sb = new StringBuilder();
			String msg =null;
			
			byte[] data = new byte[20480];
			int len = client.getInputStream().read(data);
			/*
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			while((msg=br.readLine()).length()>0) {
				sb.append(msg);
				sb.append("\r\n");
			}
			*/
			String requestInfo = new String(data,0,len).trim();
			System.out.println(requestInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void stop() {
		
	}
}
