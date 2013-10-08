package asg01u.components;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.listener.*;
import asg01u.components.*;
import asg01u.components.BoolExpressions.*;
import asg01u.components.NumExpressions.*;

public abstract class Expression extends component implements ActionListener{
	
	protected JPopupMenu popup;
	public String fontName;
	public int fontAttributes;
	public int fontSize;
	public Color colour;
	
	public Expression(String font, int attrib, int size, Color col,boolean root){
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		this.setBackground(Color.WHITE);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setAlignmentY(Component.CENTER_ALIGNMENT);
		//this.setMaximumSize(this.getPreferredSize());
		fontName = font;
		fontAttributes = attrib;
		fontSize = 70;//size;
		colour = col;
				
		popup = new JPopupMenu();
    	
		int[] availableSizes = {4,8,10,12,14,16,20,24,30,40,60};
    	JMenu fontSizeMenu;
    	fontSizeMenu = new JMenu("Size");
    	JMenuItem[] sizes = new JMenuItem[availableSizes.length];
    	for(int i=0;i<availableSizes.length;i++){
    		sizes[i] = new JMenuItem(availableSizes[i]+" pt");
    		sizes[i].addActionListener(this);
    		fontSizeMenu.add(sizes[i]);
    	}
    	popup.add(fontSizeMenu);
    	
    	JMenu fontAttributesMenu;
    	fontAttributesMenu = new JMenu("Attributes");
    	JMenuItem fontAttributesBold;
    	JMenuItem fontAttributesItalic;
    	JMenuItem fontAttributesPlain;
    	fontAttributesBold = new JMenuItem("Bold");
    	fontAttributesItalic = new JMenuItem("Italic");
    	fontAttributesPlain = new JMenuItem("Plain");
    	fontAttributesBold.addActionListener(this);
    	fontAttributesItalic.addActionListener(this);
    	fontAttributesPlain.addActionListener(this);
    	fontAttributesMenu.add(fontAttributesBold);
    	fontAttributesMenu.add(fontAttributesItalic);
    	fontAttributesMenu.add(fontAttributesPlain);
    	popup.add(fontAttributesMenu);
    	
		JMenu fontColourMenu;
    	fontColourMenu = new JMenu("Colour");
    	JMenuItem fontColourBlack;
    	JMenuItem fontColourBlue;
    	JMenuItem fontColourRed;
    	JMenuItem fontColourGreen;
    	JMenuItem fontColourWhite;
    	fontColourBlack = new JMenuItem("Black");
    	fontColourBlue = new JMenuItem("Blue");
    	fontColourRed = new JMenuItem("Red");
    	fontColourGreen = new JMenuItem("Green");
    	fontColourWhite = new JMenuItem("White");
    	fontColourBlack.addActionListener(this);
    	fontColourBlue.addActionListener(this);
    	fontColourRed.addActionListener(this);
    	fontColourGreen.addActionListener(this);
    	fontColourWhite.addActionListener(this);
    	fontColourMenu.add(fontColourBlack);
    	fontColourMenu.add(fontColourBlue);
    	fontColourMenu.add(fontColourRed);
    	fontColourMenu.add(fontColourGreen);
    	fontColourMenu.add(fontColourWhite);
    	popup.add(fontColourMenu);
    	
    	String[] availableFonts = {"Times New Roman","Arial","Courier New","Symbol"};
    	JMenu fontNameMenu;
    	fontNameMenu = new JMenu("Font");
    	JMenuItem[] fonts = new JMenuItem[availableSizes.length];
    	for(int i=0;i<availableFonts.length;i++){
    		fonts[i] = new JMenuItem(availableFonts[i]);
    		fonts[i].addActionListener(this);
    		fontNameMenu.add(fonts[i]);
    	}
    	popup.add(fontNameMenu);

    	EventListener popUpListener = new popUps(popup);
    	this.addMouseListener((MouseListener) popUpListener);
		if(root){
		this.setFocusable(true);
		this.addFocusListener((FocusListener) popUpListener);
		}else{
		this.setFocusable(false);
		}
		}
		
		public void actionPerformed(ActionEvent e){
		JMenuItem temp = (JMenuItem) e.getSource();
		if(temp.getText().compareTo("Bold")==0){
			fontAttributes = Font.BOLD;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,1);
			}
		else if(temp.getText().compareTo("Italic")==0){
			fontAttributes = Font.ITALIC;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,1);
			}
		else if(temp.getText().compareTo("Plain")==0){
			fontAttributes = Font.PLAIN;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,1);
			}
		else if(temp.getText().compareTo("Black")==0){
			colour = Color.BLACK;
			System.out.println("Black");
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Blue")==0){
			colour = Color.BLUE;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Red")==0){
			colour = Color.RED;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("White")==0){
			colour = Color.WHITE;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Green")==0){
			colour = new Color(0,200,0);
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Value")==0){
			JOptionPane.showMessageDialog(this, getValueString());
			}
		else if(temp.getText().substring(temp.getText().length()-2).compareTo("pt")==0){
				Integer temp2 = new Integer(temp.getText().substring(0,temp.getText().length()-3));	
				fontSize = temp2.intValue();
				this.setTheFont(fontName, fontAttributes, fontSize, colour,2);
			}
		else{
			fontName = temp.getText();
			this.setTheFont(fontName, fontAttributes, fontSize, colour,0);
		}
	}
	
	public abstract void setTheFont(String font, int attrib, int size, Color col, int what);
	// what gives the change, eg 0 for font, 1 for attrib, 2 for size, 3 for col
	public abstract String getValueString();
	
	public abstract void recreate();

}