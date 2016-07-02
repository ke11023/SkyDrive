package com.test;

import java.io.File;
import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import com.upload.HttpClientUtil;

public class HttpClientPostDemo {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		post();
		// HttpClientUtil httpclient=new HttpClientUtil();
		// httpclient.get();
	}

	public static void post() throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"http://localhost:8080/httpclientweb/servlet/UploadServlet");

		FileBody fileBody = new FileBody(new File(
				"D:\\myworkspace\\httpclientlib\\测试中文.txt"));
		StringBody userId = new StringBody("用户ID", ContentType.create(
				"text/plain", Consts.UTF_8));
		HttpEntity reqEntity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart("file1", fileBody).addPart("userId", userId)
				.setCharset(CharsetUtils.get("UTF-8")).build();

		post.setEntity(reqEntity);
		HttpResponse response = httpclient.execute(post);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {

			HttpEntity entitys = response.getEntity();
			if (reqEntity != null) {
				// System.out.println(reqEntity.getContentLength());
				System.out.println(EntityUtils.toString(entitys));
			}
		}
		httpclient.getConnectionManager().shutdown();
	}
}