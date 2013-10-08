package asg01u.html.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;

public class viewer extends JFrame implements ActionListener,HyperlinkListener{

    public static JEditorPane mainPane;
    	URL fileURL;
    
    public viewer(String fileName,String title,int width,int height,boolean menu){
	try{
	File file = new File(fileName);
	fileURL = file.toURL();
	mainPane = new JEditorPane(fileURL);
	mainPane.setEditable(false);
	mainPane.setBackground(new Color(200,200,200));
	mainPane.addHyperlinkListener(this);
	if(menu){
		JMenuBar menubar = new JMenuBar();
		JMenu filemenu = new JMenu("File");
		JMenuItem menuitem = new JMenuItem("Contents");
		menuitem.addActionListener(this);
		filemenu.add(menuitem);
		menubar.add(filemenu);
		this.setJMenuBar(menubar);
	}else{
	this.setResizable(false);	
	}
	this.getContentPane().setLayout(new BorderLayout());
	this.getContentPane().add(mainPane, BorderLayout.CENTER);
	this.setTitle(title);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setSize(new Dimension(width,height));
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
	int x = (((int) screenSize.width)-width) / 2;
	int y = (((int) screenSize.height)-height) / 2;
	this.setLocation(x,y);
	this.setVisible(true);
	}catch(Exception e){
		System.out.println(e.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		try{
		mainPane.setPage(fileURL);
		}catch(Exception e){
		System.out.println(e.getMessage());
		}
	}
	
	public void hyperlinkUpdate(HyperlinkEvent e) {
	if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		try{
			mainPane.setPage(e.getURL());
		}catch(IOException ioe){
			JOptionPane.showMessageDialog(this, "Error: Page not Found");
		}
	}
	}

}












