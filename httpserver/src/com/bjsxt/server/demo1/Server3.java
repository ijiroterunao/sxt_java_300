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
public class Server3 {
	private ServerSocket server;
	public static final String CRLF="\r\n";
	public static final String BLANK=" ";
	public static void main(String[] args) {
		Server3 server = new Server3();
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
			
			String requestInfo = new String(data,0,len).trim();
			System.out.println(requestInfo);
			
			StringBuilder responseContext = new StringBuilder();
			responseContext.append("<html><head><title>HTTP响应示例</title>" +
                                  "</head><body>Hello bjsxt!</body></html>");
			StringBuilder response = new StringBuilder();
			//1)  HTTP协议版本、状态代码、描述
			response.append("HTTP/1.1").append(BLANK).append("200").
			append(BLANK).append("OK").append(CRLF);
			//2)  响应头(Response Head)
			response.append("Server:bjsxt Server/0.0.1").append("200");
			response.append("Date:").append(new Date()).append(CRLF);
			response.append("Content-type:text/html;charset=utf-8").append(CRLF);
            //正文长度 ：字节长度
			response.append("Contene-Length:").
			append(responseContext.toString().getBytes().length).
			append(CRLF);
			//3)正文之前
			response.append(CRLF);
			//4)正文
            response.append(responseContext);
            
            System.out.println(responseContext);
            
            //输出流
            BufferedWriter bw = new BufferedWriter(new
            		OutputStreamWriter(client.getOutputStream()));
            bw.write(response.toString());
            bw.flush();
            bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	public void stop() {
		
	}
}
