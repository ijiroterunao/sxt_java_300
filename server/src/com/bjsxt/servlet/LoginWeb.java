package com.bjsxt.servlet;

import com.bjsxt.server.Request;
import com.bjsxt.server.Response;

public class LoginWeb extends Servlet {

	@Override
	protected void doGet(Request req, Response rep) throws Exception {
		rep.println("aaaaa");
	}

	@Override
	protected void doPost(Request req, Response rep) throws Exception {
		rep.println("aaaaa");
	}

}
