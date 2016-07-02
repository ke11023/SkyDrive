package com.view;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseEvent;
public class MainFrm extends JFrame{
	private JMenu m1=new JMenu("ϵͳ����");
	private JTree tree=null;
	private JPanel mainpane=new JPanel();
	private void initTree(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("�ҵ�����");
		DefaultMutableTreeNode m1 = new DefaultMutableTreeNode("�ҵ�ͼƬ");
		
		DefaultMutableTreeNode m2 = new DefaultMutableTreeNode("�ҵ���Ƶ");
		DefaultMutableTreeNode m3 = new DefaultMutableTreeNode("����վ");
		m1.add(new DefaultMutableTreeNode("��ҳ"));
		m2.add(new DefaultMutableTreeNode("��Ƶ����"));
		m3.add(new DefaultMutableTreeNode("�鿴����վ"));
		root.add(m1);root.add(m2);root.add(m3);
		tree=new JTree(root);
		tree.addTreeSelectionListener(new TreeSelectionListener(){
           @Override
			public void valueChanged(TreeSelectionEvent e) {
				tree_clicked(e);
			}
			
		});
	}
	private JPopupMenu initPopMenu(){
	    JPopupMenu menu = new JPopupMenu(); 
	    //menu.add
        menu.add(new JMenuItem("����")); 
        menu.add(new JMenuItem("ճ��"));  
        menu.add(new JMenuItem("ɾ��"));  
        menu.add(new JMenuItem("����")); 
        menu.add(new JMenuItem("������"));  
        return menu;
	}
	private void tree_clicked(TreeSelectionEvent e){
		mainpane.removeAll();
	    int WIDTH=127;
		int HEIGHT=116;
		//System.out.println(e.getPath().getLastPathComponent());
		String[] files={"my.ppt","my.txt","my.png","my.xls","my.WMA","my.ppt","my.txt","my.png","my.xls","my.WMA","my.txt","my.png","my.xls","my.WMA","my.ppt"};
		for(String fname:files){
			
			String imgpath="/icons/"+fname.substring(fname.lastIndexOf(".")+1).toUpperCase()+".png";
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
	private void initMenu(){
		m1.add(new JMenuItem("�޸�����"));
		m1.add(new JMenuItem("�˳�ϵͳ"));
		mainpane.setLayout(new FlowLayout(FlowLayout.LEFT));
		JMenuBar bar=new JMenuBar();
		bar.add(m1);
		this.setJMenuBar(bar);
		
	}
	public MainFrm(){
		this.setTitle("�ҵ�����ϵͳ");
		initMenu();
		initTree();
		JSplitPane jsplit=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				   new JScrollPane(tree),mainpane);
		jsplit.setDividerLocation(300);
		jsplit.setDividerSize(2);
		this.getContentPane().add(jsplit);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MainFrm frm=new MainFrm();
		frm.setSize(1200,600);
		frm.setVisible(true);

	}
}
