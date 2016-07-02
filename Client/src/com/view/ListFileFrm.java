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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;
public class ListFileFrm  extends JFrame{
    
    private JButton btnOK=new JButton("��ȡ�ļ�");
    private JPanel mainpane=new JPanel();
    public ListFileFrm(){
    	this.setTitle("�ҵ�����");
    	JPanel jp=(JPanel)this.getContentPane();
    	jp.setLayout(new BorderLayout());
    	JPanel toppane=new JPanel();
    	toppane.add(btnOK);
    	jp.add(toppane,BorderLayout.NORTH);
    	jp.add(mainpane);
    	btnOK.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			btnOK_Clicked();
    		}
    	});
    }
    private List<String> xmlElements(String xmlDoc) {
    	List<String> filenames=new ArrayList<String>();
        //����һ���µ��ַ���
        StringReader read = new StringReader(xmlDoc);
        //�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
        InputSource source = new InputSource(read);
        //����һ���µ�SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        try {
            //ͨ������Դ����һ��Document
            Document doc = sb.build(source);
            Element root = doc.getRootElement();
            //System.out.println(root.getName());//�����Ԫ�ص����ƣ����ԣ�
            //�õ���Ԫ��������Ԫ�صļ���
            List node = root.getChildren();           
            Element et = null;
            for(int i=0;i<node.size();i++){
                et = (Element) node.get(i);//ѭ�����εõ���Ԫ��
                String type=et.getAttributeValue("type");
                if("file".equals(type))
                	 filenames.add(et.getChild("name").getText());
            }
           
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filenames;
    }
    private JPopupMenu initPopMenu(){
	    JPopupMenu menu = new JPopupMenu(); 
	    menu.add(new JMenuItem("����")); 
        menu.add(new JMenuItem("ճ��"));  
        menu.add(new JMenuItem("ɾ��"));  
        menu.add(new JMenuItem("����")); 
        menu.add(new JMenuItem("������"));  
        return menu;
	}
    private void dispFiles(String rvalue){
    	List<String> filenames=xmlElements(rvalue);
    	mainpane.removeAll();
	    int WIDTH=127;
		int HEIGHT=116;
		for(String fname:filenames){
			
			//String imgpath="/icons/"+fname.substring(fname.lastIndexOf(".")+1).toUpperCase()+".png";
			String imgpath="/icons/PPT.png";
			final JLabel lbl=new JLabel();
			//lbl.setSize(WIDTH,HEIGHT);
			ImageIcon img=new ImageIcon(JLabel.class.getResource(imgpath));
			 img.setImage(img.getImage().getScaledInstance(WIDTH,HEIGHT,Image.SCALE_DEFAULT));
			lbl.setIcon(img);
			lbl.setText(fname);
			lbl.setHorizontalTextPosition(JLabel.CENTER);
			lbl.setVerticalTextPosition(JLabel.BOTTOM);
			final JPopupMenu menu=initPopMenu();
			
			lbl.addMouseListener(new java.awt.event.MouseAdapter() {  
	            public void mouseReleased(MouseEvent e) {  
	                if (e.isPopupTrigger()) {  
	                	JLabel src=(JLabel)(e.getSource());
	                    menu.show(mainpane,src.getX()+50,src.getY()+40);  
	                }  
	            }  
	        });  
			mainpane.add(lbl);
		}
		mainpane.validate();
    }
    public String listFiles(String opttype,String path) {
    	String rvalue="";
		CloseableHttpClient httpclient = HttpClients.createDefault();
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
    	String rvalue=listFiles("listfiles","D:\\eclipse");
    	rvalue=rvalue.substring(0,rvalue.length()-2);
    	dispFiles(rvalue);
    }
	
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ListFileFrm frm=new ListFileFrm();
		frm.setSize(1200,800);
		frm.setVisible(true);
	}
}
