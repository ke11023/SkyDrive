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
            // Ҫ�ϴ����ļ���·��
            String filePath = new String("c:\\actonlinedb.sql");
            // ��һ����ͨ�������ļ��ϴ������������ַ ��һ��servlet
            HttpPost httpPost = new HttpPost(
            		"http://localhost:8080/httpclientweb/servlet/UploadServlet");
             // ���ļ�ת����������FileBody
            File file = new File(filePath);
            FileBody bin = new FileBody(file);  
            StringBody userId = new StringBody(
                    "�û�ID", ContentType.create(
                            "text/plain", Consts.UTF_8));
            //�����������ģʽ���У���ֹ�ļ������롣  
            HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName(HTTP.UTF_8)).setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addPart("file1", bin)
                    .addPart("userId", userId).build();

            httpPost.setEntity(reqEntity);

            System.out.println("���������ҳ���ַ " + httpPost.getRequestLine());
            // �������� �������������Ӧ
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                System.out.println("----------------------------------------");
                // ��ӡ��Ӧ״̬
                System.out.println(response.getStatusLine());
                // ��ȡ��Ӧ����
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    // ��ӡ��Ӧ����
                    System.out.println("Response content length: "
                            + resEntity.getContentLength());
                    // ��ӡ��Ӧ����
                    System.out.println(EntityUtils.toString(resEntity));
                }
                // ����
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

}
