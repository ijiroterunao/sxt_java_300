package com.bjsxt.net.tcp.chat.demo03;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable {
	private BufferedReader console;
	private DataOutputStream dos;
	private boolean isRunning = true;

	public Send() {
		console = new BufferedReader(new InputStreamReader(System.in));
	}

	public Send(Socket client) {
		this();
		try {
			dos = new DataOutputStream(client.getOutputStream());
		} catch (Exception e) {
			// e.printStackTrace();
			isRunning = false;
			CloseUtil.closeAll(dos, console);
		}
	}

	private String getMsgFromConsole() {
		try {
			return console.readLine();
		} catch (IOException e) {
		}
		return "";
	}

	public void send() {
		String msg = getMsgFromConsole();
		try {
			if (null != msg && !msg.equals("")) {
				dos.writeUTF(msg);
				dos.flush();
			}
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.closeAll(dos, console);
		}
	}

	@Override
	public void run() {
		while (isRunning) {
			send();
		}
	}

}
