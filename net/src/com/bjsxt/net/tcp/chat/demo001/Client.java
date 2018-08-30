package com.bjsxt.net.tcp.chat.demo001;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
/**
1、创建客户端   必须指定服务器+端口    此时就在连接
      Socket(String host, int port) 
2、接收数据 +发送数据   
* @author Administrator
*
*/
public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、创建客户端   必须指定服务器+端口    此时就在连接
		Socket client= new Socket("localhost",9999);
		//2、接收数据
		//控制台输入流
		BufferedReader console=new BufferedReader(new InputStreamReader(System.in));
		String info  = console.readLine();
		//输出流
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF(info);
		dos.flush();
		//输入流
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String msg = dis.readUTF();
		System.out.println(msg);
	}

}
