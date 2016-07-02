package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gbk");
		PrintWriter out = response.getWriter();
		String uid=request.getParameter("uid");
		String upwd=request.getParameter("upwd");
		if("admin".equals(uid)&&"1234".equals(upwd))
			out.println("用户登录成功!");
		else
		   out.println("User Login Failure!");
		out.flush();
		out.close();
	}

}
