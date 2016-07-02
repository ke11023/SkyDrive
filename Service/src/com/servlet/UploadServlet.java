package com.servlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

public class UploadServlet extends HttpServlet {

    
    public UploadServlet() {
        super();
    }

    public void destroy() {
        super.destroy(); 
    }

   
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setCharacterEncoding("UTF-8");
        response.getWriter().println("请以POST方式上传文件");
    }

    
    @SuppressWarnings({ "unchecked", "deprecation" })
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File file1 = null,file2=null;
        String description1 = null;
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        DiskFileUpload diskFileUpload = new DiskFileUpload();
        try{
            List<FileItem> list = diskFileUpload.parseRequest(request);
            
            out.println("遍历所有的FileItem...<br/>");
            for(FileItem fileItem : list){
            	 if(fileItem.isFormField()){
                    if("userId".equals(fileItem.getFieldName())){
                        out.println("遍历到description1 ... <br/>");
                        description1 = new String(fileItem.getString());
                        out.println(description1);
                    }
                    
                }else{
                    if("file1".equals(fileItem.getFieldName())){
                        File remoteFile = new File(new String(fileItem.getName().getBytes(),"ISO-8859-1"));
                        out.println("遍历到file1...<br/>");
                        out.println("客户端文件位置："+remoteFile.getAbsolutePath()+"<br/>");
                        
                        file1 = new File(this.getServletContext().getRealPath("attachment"),remoteFile.getName());
                        file1.getParentFile().mkdirs();
                        file1.createNewFile();
                        
                        InputStream ins = fileItem.getInputStream();
                        OutputStream ous = new FileOutputStream(file1);
                        
                        try{
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            while((len = ins.read(buffer)) > -1)
                                ous.write(buffer,0,len);
                            out.println("以保存文件"+file1.getAbsolutePath()+"<br/>");
                        }finally{
                            ous.close();
                            ins.close();
                        }
                    }
                 }
                out.println("Request 解析完毕<br/><br/>");
            }
        }catch(FileUploadException e){}
        
        out.flush();
        out.close();
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
