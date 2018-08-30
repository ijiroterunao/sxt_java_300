package com.bjsxt.net.tcp.chat.demo03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private List<MyChannel> all = new ArrayList<MyChannel>();

	public static void main(String[] args) throws IOException {
		new Server().start();
	}

	public void start() throws IOException {
		ServerSocket server = new ServerSocket(9999);
		while (true) {
			Socket client = server.accept();
			MyChannel channel = new MyChannel(client);
			all.add(channel);
			new Thread(channel).start();
		}
	}

	class MyChannel implements Runnable {
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;

		public MyChannel(Socket client) {
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
			} catch (IOException e) {
				// e.printStackTrace();
				CloseUtil.closeAll(dis, dos);
				isRunning = false;
			}
		}

		private String receive() {
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				//e.printStackTrace();
				CloseUtil.closeAll(dis);
				isRunning = false;
				all.remove(this);
			}
			return msg;
		}

		private void send(String msg) {
			if (null == msg || msg.equals("")) {
				return;
			}
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
				CloseUtil.closeAll(dos);
				isRunning = false;
				all.remove(this);
			}
		}

		/**
		 * 发送给其他客户端
		 */
		private void sendOthers() {
			String msg = receive();
			// 遍历容器
			for (MyChannel other : all) {
				if (other == this) {
					continue;
				}
				//发送给其他客户端
				other.send(msg);
			}
		}

		@Override
		public void run() {
			while (isRunning) {
				sendOthers();
			}
		}

	}

}
