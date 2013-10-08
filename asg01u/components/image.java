package asg01u.components;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.listener.*;

public class image extends component implements ActionListener {
	
	ImageIcon mainImage;
	JPopupMenu popup;
	JLabel label;
	String fileName;
	final JFileChooser fc = new JFileChooser();
	
	public image(String image){
		fileName = image;
		mainImage = new ImageIcon(fileName);
		label = new JLabel(mainImage);		
		label.setSize(new Dimension(mainImage.getIconWidth(),mainImage.getIconHeight()));
		this.add(label);
		
		asg01u.other.ExampleFileFilter filter = new asg01u.other.ExampleFileFilter();
    	filter.addExtension("jpg");
    	filter.addExtension("gif");
    	filter.setDescription("JPG & GIF Images");
    	fc.setFileFilter(filter);
		
		popup = new JPopupMenu();
    	
    	JMenuItem imageMenu;
    	imageMenu = new JMenuItem("Select Image");
    	imageMenu.addActionListener(this);
    	popup.add(imageMenu);
    	
    	EventListener popUpListener = new popUps(popup);
    	this.addMouseListener((MouseListener)popUpListener);
		this.addFocusListener((FocusListener)popUpListener);
		this.setBackground(Color.WHITE);
		this.setSize(label.getSize());
		repaint();
	}
	
		public image(ImageIcon image){
		mainImage = image;
		label = new JLabel(mainImage);		
		label.setSize(new Dimension(mainImage.getIconWidth(),mainImage.getIconHeight()));
		this.add(label);
		
		asg01u.other.ExampleFileFilter filter = new asg01u.other.ExampleFileFilter();
    	filter.addExtension("jpg");
    	filter.addExtension("gif");
    	filter.setDescription("JPG & GIF Images");
    	fc.setFileFilter(filter);
		
		popup = new JPopupMenu();
    	
    	JMenuItem imageMenu;
    	imageMenu = new JMenuItem("Select Image");
    	imageMenu.addActionListener(this);
    	popup.add(imageMenu);
    	
    	EventListener popUpListener = new popUps(popup);
    	this.addMouseListener((MouseListener)popUpListener);
		this.addFocusListener((FocusListener)popUpListener);
		this.setBackground(Color.WHITE);
		this.setSize(label.getSize());
		repaint();
	}
	
		
	public void actionPerformed(ActionEvent e){
		//JMenuItem temp = (JMenuItem) e.getSource();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.removeAll();
            File file = fc.getSelectedFile();
         	mainImage = new ImageIcon(file.getAbsolutePath());
         	label = new JLabel(mainImage);
         	label.setSize(new Dimension(mainImage.getIconWidth(),mainImage.getIconHeight()));
			this.add(label);
         	this.setSize(label.getSize());
         	repaint();
         	this.getParent().validate();
         }
	}
	
	public String outputString(){
		String output = new String();
		output = "<image>\n";
		try{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(mainImage);
		oos.close();
		baos.close();
		String temp = baos.toString();
		output = output + temp;
		}catch(Exception e){
				
		}
		output = output + "\n</image>\n";
		return output;
		}

}		