package com.bjsxt.net.tcp.chat.demo04;

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
		private String name;

		public MyChannel(Socket client) {
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());

				this.name = dis.readUTF();

				this.send("欢迎进入聊天室");
				sendOthers(this.name + "进入了聊天室", true);
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
				// e.printStackTrace();
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
		private void sendOthers(String msg, boolean sys) {
			// 是否为私聊
			if (msg.startsWith("@") && msg.indexOf(":") > -1) {
				// get name
				String name = msg.substring(1, msg.indexOf(":"));
				String content = msg.substring(msg.indexOf(":") + 1);
				for (MyChannel other : all) {
					if (other.name.equals(name)) {
						other.send(this.name + "对你悄悄地说：" + content);
					}
				}

			} else {
				// 遍历容器
				for (MyChannel other : all) {
					if (other == this) {
						continue;
					}
					if (sys == true) {
						other.send("系统信息:" + msg);
					} else {
						// 发送给其他客户端
						other.send(this.name + "对所有人说：" + msg);
					}
				}
			}
		}

		@Override
		public void run() {
			while (isRunning) {
				sendOthers(receive(), false);
			}
		}

	}

}
