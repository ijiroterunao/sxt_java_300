package com.bjsxt.net.tcp.chat.demo03;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
	public static void closeAll(Closeable... io) {
		for(Closeable temp:io) {
			try {
				if(null!=temp) {
					temp.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
