package com.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class FileUploadTest {

    /**
     * 
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        
        try {
            // 要上传的文件的路径
            String filePath = new String("c:\\actonlinedb.sql");
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(
            		"http://localhost:8080/httpclientweb/servlet/UploadServlet");
             // 把文件转换成流对象FileBody
            File file = new File(filePath);
            FileBody bin = new FileBody(file);  
            StringBody userId = new StringBody(
                    "用户ID", ContentType.create(
                            "text/plain", Consts.UTF_8));
            //以浏览器兼容模式运行，防止文件名乱码。  
            HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName(HTTP.UTF_8)).setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("file1", bin)
                    .addPart("userId", userId).build();

            httpPost.setEntity(reqEntity);

            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            // 发起请求 并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                System.out.println("----------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                // 获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    // 打印响应长度
                    System.out.println("Response content length: "
                            + resEntity.getContentLength());
                    // 打印响应内容
                    System.out.println(EntityUtils.toString(resEntity));
                }
                // 销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

}
