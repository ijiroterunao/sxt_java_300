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
 * @author terunao
 *
 */
public class Server4 {
	private ServerSocket server;
	public static final String CRLF="\r\n";
	public static final String BLANK=" ";
	public static void main(String[] args) {
		Server4 server = new Server4();
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
			byte[] data = new byte[20480];
			int len = client.getInputStream().read(data);
			
			String requestInfo = new String(data,0,len).trim();
			System.out.println(requestInfo);
			
            Response rep = new Response(client.getOutputStream());
            rep.println("<html><head><title>HTTP响应示例</title>");
            rep.println("</head><body>Hello server!</body></html>");
            rep.pushToClient(500);
            
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void stop() {
		
	}
}
