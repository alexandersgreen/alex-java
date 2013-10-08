package asg01u.components;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.listener.*;

public class Text extends component implements ActionListener {
	
	JTextArea mainArea;
	JPopupMenu popup;
	String fontName;
	int fontAttributes;
	int fontSize;
	Color colour;
	
	public Text(String input, String font, int attrib, int size, Color col){
		mainArea = new JTextArea(input);
		this.add(mainArea);
		fontName = font;
		fontAttributes = attrib;
		fontSize = size;
		colour = col;
		mainArea.setForeground(colour);
		mainArea.setFont(new Font(fontName, fontAttributes, fontSize));
		mainArea.setColumns(30);
		mainArea.setLineWrap(true);
		mainArea.setWrapStyleWord(true);
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
    	mainArea.addMouseListener((MouseListener)popUpListener);
		mainArea.addFocusListener((FocusListener)popUpListener);
		mainArea.setMaximumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-35,1000000));
		mainArea.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-35,50);
		this.setBackground(Color.WHITE);
	}
	
	public static String createString(String[] input){
		String[] escapeChars = {"\\(",
								"\\)",
								"\\=",
								"\\!=",
								"\\<",
								"\\>",
								"\\<=",
								"\\>=",								
								"\\&",								
								"\\|",
								"\\true",
								"\\false",
								"\\+",
								"\\-",
								"\\/",								
								"\\.",
								"\\*",
								"\\^"};
		
		String temp = "";
		for(int i=0;i<input.length;i++){
			if(contains(escapeChars,input[i],0)!=-1){
				input[i]=input[i].substring(1);
			}
			temp = temp + input[i] + " ";
		}
		return temp;
	}
	
	public static int contains(String[] testIn,String testFor,int start){
		for(int i=start;i<testIn.length;i++){
			if(testIn[i].compareTo(testFor)==0){return i;}
		}
		return -1;
		}
		
	public void actionPerformed(ActionEvent e){
		JMenuItem temp = (JMenuItem) e.getSource();
		if(temp.getText().compareTo("Bold")==0){
			fontAttributes = Font.BOLD;
			mainArea.setFont(new Font(fontName, fontAttributes, fontSize));
			}
		else if(temp.getText().compareTo("Italic")==0){
			fontAttributes = Font.ITALIC;
			mainArea.setFont(new Font(fontName, fontAttributes, fontSize));
			}
		else if(temp.getText().compareTo("Plain")==0){
			fontAttributes = Font.PLAIN;
			mainArea.setFont(new Font(fontName, fontAttributes, fontSize));
			}
		else if(temp.getText().compareTo("Black")==0){
			colour = Color.BLACK;
			mainArea.setForeground(colour);
			}
		else if(temp.getText().compareTo("Blue")==0){
			colour = Color.BLUE;
			mainArea.setForeground(colour);
			}
		else if(temp.getText().compareTo("Red")==0){
			colour = Color.RED;
			mainArea.setForeground(colour);
			}
		else if(temp.getText().compareTo("Green")==0){
			colour = new Color(0,200,0);
			mainArea.setForeground(colour);
			}
		else if(temp.getText().compareTo("White")==0){
			colour = Color.WHITE;
			mainArea.setForeground(colour);
			}
		else if(temp.getText().substring(temp.getText().length()-2).compareTo("pt")==0){
				Integer temp2 = new Integer(temp.getText().substring(0,temp.getText().length()-3));	
				fontSize = temp2.intValue();
				mainArea.setFont(new Font(fontName, fontAttributes, fontSize));
			}
		else{
			fontName = temp.getText();
			mainArea.setFont(new Font(fontName, fontAttributes, fontSize));
		}
	}
	
	public String outputString(){
		String output = new String();
		output = "<Text>\n";
		output = output + "  <content>\n";
			output = output + mainArea.getText();
		output = output + "\n  </content>\n";
		output = output + "  <font>\n";
			output = output + fontName;
		output = output + "\n  </font>\n";
		output = output + "  <attrib>\n";
			output = output + fontAttributes;
		output = output + "\n  </attrib>\n";
		output = output + "  <size>\n";
			output = output + fontSize;
		output = output + "\n  </size>\n";
		output = output + "  <colour>\n";
		output = output + "    <red>\n";
			output = output + colour.getRed();
		output = output + "\n    </red>\n";
		output = output + "    <green>\n";
			output = output + colour.getGreen();	
		output = output + "\n    </green>\n";
		output = output + "    <blue>\n";
			output = output + colour.getBlue();
		output = output + "\n    </blue>\n";
		output = output + "  </colour>\n";
		output = output + "</Text>\n";
		return output;
	}
	
}		