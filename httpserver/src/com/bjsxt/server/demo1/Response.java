package com.bjsxt.server.demo1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import com.bjsxt.util.CloseUtil;

public class Response {
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";

	private BufferedWriter bw;
	private StringBuilder content;
	private StringBuilder headInfo;

	private int len = 0;

	public Response() {
		headInfo = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}

	public Response(Socket client) {
		this();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			// e.printStackTrace();
			headInfo = null;
		}
	}

	public Response(OutputStream os) {
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	public Response print(String info) {
		content.append(info);
		len += info.getBytes().length;
		return this;
	}

	public Response println(String info) {
		content.append(info).append(CRLF);
		len += (info + CRLF).getBytes().length;
		return this;
	}

	private void createHeadInfo(int code) {
		// 1) HTTP协议版本、状态代码、描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUND");
			break;
		case 500:
			headInfo.append("SERER ERROR");
			break;
		}
		headInfo.append(CRLF);
		// 2) 响应头(Response Head)
		headInfo.append("Server:bjsxt Server/0.0.1").append("200");
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-type:text/html;charset=utf-8").append(CRLF);
		// 正文长度 ：字节长度
		headInfo.append("Contene-Length:").append(len).append(CRLF);
		headInfo.append(CRLF);
	}

	void pushToClient(int code) throws IOException {
		if (null == headInfo) {
			code = 500;
		}
		createHeadInfo(code);
		bw.append(headInfo.toString());
		bw.append(content.toString());
		bw.flush();
	}

	public void close() {
		CloseUtil.closeAll(bw);
	}
}
