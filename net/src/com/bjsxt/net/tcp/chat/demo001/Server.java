package com.bjsxt.net.tcp.chat.demo001;

import java.io.DataInputStream;
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
public class Server {

	public static void main(String[] args) throws IOException {
		// 1、创建服务器 指定端口 ServerSocket(int port)
		ServerSocket server = new ServerSocket(9999);
		// 2、接收客户端连接 阻塞式
		Socket client = server.accept();
		// 3、发送数据
		// 输入流
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String msg = dis.readUTF();
		// 输出流
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF("服务器-->" + msg);
		dos.flush();
	}

}
