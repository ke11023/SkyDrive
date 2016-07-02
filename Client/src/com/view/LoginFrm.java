package com.view;
import javax.swing.*;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
public class LoginFrm  extends JFrame{
    private JLabel lblID=new JLabel("�û���");
    private JLabel lblPwd=new JLabel("����");
    private JTextField txtID=new JTextField();
    private JPasswordField txtPwd=new JPasswordField();
    private JButton btnOK=new JButton("��¼");
    private JButton btnCancel=new JButton("ȡ��");
    public LoginFrm(){
    	JPanel jp=(JPanel)this.getContentPane();
    	jp.setLayout(new GridLayout(3,2));
    	jp.add(lblID);jp.add(txtID);
    	jp.add(lblPwd);jp.add(txtPwd);
    	jp.add(btnOK);jp.add(btnCancel);
    	//btnOK.setIcon(new ImageIcon(JButton.class
            //    .getResource("/images/2-1209221U445-50.png")));// Ϊ��ť����ͼ��
    	btnOK.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			btnOK_Clicked();
    		}
    	});
    }
    public String Login(String uid,String upwd) {
    	String rvalue="";
		// ����Ĭ�ϵ�httpClientʵ��.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// ����httppost  
		HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/user.action");
		// ������������  
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("uid", uid));
		formparams.add(new BasicNameValuePair("upwd", upwd));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					rvalue=EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// �ر�����,�ͷ���Դ  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rvalue;
	}
    public String listFiles(String opttype,String path) {
    	String rvalue="";
		// ����Ĭ�ϵ�httpClientʵ��.  
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// ����httppost  
		HttpPost httppost = new HttpPost("http://localhost:8080/httpclientweb/file.action");
		// ������������  
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		formparams.add(new BasicNameValuePair("opttype", opttype));
		formparams.add(new BasicNameValuePair("path", path));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					rvalue=EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// �ر�����,�ͷ���Դ  
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rvalue;
	}
    private void btnOK_Clicked(){
    	//String uid=txtID.getText().trim();
    	//String upwd=txtPwd.getText().trim();
    	//JOptionPane.showMessageDialog(this, Login(uid,upwd));
    	String rvalue=listFiles("listfiles","D:\\");
    	rvalue=rvalue.substring(0,rvalue.length()-2);
    	System.out.println(rvalue);
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		LoginFrm frm=new LoginFrm();
		frm.setSize(600,200);
		frm.setVisible(true);
	}
}
