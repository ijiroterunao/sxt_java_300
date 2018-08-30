package com.bjsxt.net.tcp.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
  
/**
 必须先启动服务器 后连接
1、创建服务器  指定端口   ServerSocket(int port) 
2、接收客户端连接  
3、发送数据+接收数据
* 
*/
public class MultiServer {
  
       /**
        * @param args
        * @throws IOException 
        */
	public static void main(String[] args) throws IOException {
		// 1、创建服务器 指定端口 ServerSocket(int port)
		ServerSocket server = new ServerSocket(8888);
		// 2、接收客户端连接 阻塞式
		while (true) {
			Socket socket = server.accept();
			System.out.println("一个客户端建立连接");
			// 3、发送数据
			String msg = "欢迎使用";
			// 输出流

			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(msg);
			dos.flush();
			while(true) {
				
			}
		}
		
	}
	
}