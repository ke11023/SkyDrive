package com.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.FileDao;

public class FileServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/xml;charset=utf-8");
		PrintWriter out = response.getWriter();
		String opttype=request.getParameter("opttype");
		String path=request.getParameter("path");
		if("listfiles".equals(opttype)){
			List<File> files=FileDao.getFileList(path);
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<files>");
			for(File file : files){
				if(!file.isDirectory()){
					out.println(" <file type=\"file\">");
					out.println("   <path>"+file.getAbsolutePath()+"</path>");
					out.println("   <name>"+file.getName()+"</name>");
					out.println("   <size>"+file.getAbsoluteFile().length()+"</size>");
					out.println("   <ext>"+file.getName().substring(file.getName().lastIndexOf(".")+1)+"</ext>");
					out.println(" </file>");
				}else{
					out.println(" <file type=\"dir\">");
					out.println("   <path>"+file.getAbsolutePath()+"</path>");
					out.println("   <name>"+file.getName()+"</name>");
					out.println(" </file>");
				}
			}
			out.println("</files>");
		}
		out.close();
	}

}
