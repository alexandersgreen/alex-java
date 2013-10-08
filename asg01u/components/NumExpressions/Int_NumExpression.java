package asg01u.components.NumExpressions;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.components.*;
import asg01u.components.BoolExpressions.*;
import asg01u.components.NumExpressions.*;

public class Int_NumExpression extends asg01u.components.NumExpression{
	
	int value;
	JTextArea area;
	
	public Int_NumExpression(String font, int attrib, int size, Color col,int input,boolean root){
		super(font,attrib,size,col,root);
		value = input;
		System.out.println("Int_NumExpression(\""+input+"\") called");
		area = new JTextArea(""+value);
		for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
		area.setEditable(false);
		area.setFocusable(false);
		this.add(area);
		this.setMaximumSize(this.getPreferredSize());
	}
	
	public double getValue(){
		return (double) value;
		}	
	
		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			area.setFont(new Font(fontName,fontAttributes,fontSize));
			area.setForeground(colour);
			this.setMaximumSize(this.getPreferredSize());
			this.setSize(this.getMaximumSize());
			recreate();
		}
			
	public String outputString(){
		String output = new String();
		output = "<Int_NumExpression>\n";
		output = output + "  <IntValue>\n";
			output = output + value;
		output = output + "\n  </IntValue>\n";
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
		output = output + "</Int_NumExpression>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(area);
		this.setMaximumSize(this.getPreferredSize());
		repaint();
		try{
			Expression temp = (Expression) this.getParent();
			temp.recreate(); 
		}catch(Exception ce){
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new Int_NumExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),value,true);
	}		
}