package com.bjsxt.server.demo2;

public class LoginServlet extends Servlet {

	@Override
	public void doGet(Request req, Response rep) throws Exception {
		rep.println("<html><head><title>欢迎回来</title>");
		rep.println("</head><body>");
		rep.println("欢迎：").println(req.getParameter("uname")).println("回来");
		rep.println("</body></html>");
	}

	@Override
	public void doPost(Request req, Response rep) throws Exception {
	}

	
	
}
