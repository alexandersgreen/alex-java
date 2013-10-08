package asg01u.components;

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

public class DeclareBool extends asg01u.components.BoolExpression{

	static HashMap variables;
	JTextArea key;
	JTextArea declare;
	asg01u.components.BoolExpression exp;

	public DeclareBool(String font, int attrib, int size, Color col,String keyString,asg01u.components.BoolExpression in,boolean root,HashMap hm){
			super(font,attrib,size,col,root);
			variables = hm;
			exp = in;
			variables.put(keyString,exp);
			System.out.println("DeclareBool ("+keyString+") created");
			declare = new JTextArea("::=");
			key = new JTextArea(keyString);
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			declare.setAlignmentX(Component.CENTER_ALIGNMENT);
			declare.setAlignmentY(Component.CENTER_ALIGNMENT);
			declare.setEditable(false);
			declare.setMaximumSize(declare.getPreferredSize());
			key.setAlignmentX(Component.CENTER_ALIGNMENT);
			key.setAlignmentY(Component.CENTER_ALIGNMENT);
			key.setEditable(false);
			key.setMaximumSize(key.getPreferredSize());
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
			this.add(key);
			this.add(declare);
			this.add(exp);		
			this.setMaximumSize(this.getPreferredSize());
		}
		
		public boolean getValue(){
			return exp.getValue();
			}	

		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			declare.setFont(new Font(fontName,fontAttributes,fontSize));
			declare.setForeground(colour);
			declare.setMaximumSize(declare.getPreferredSize());
			key.setFont(new Font(fontName,fontAttributes,fontSize));
			key.setForeground(colour);
			key.setMaximumSize(key.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<DeclareBool>\n";
		output = output + "  <DeclaredExpression>\n";
			output = output + exp.outputString();
		output = output + "  </DeclaredExpression>\n";
		output = output + "  <key>\n";
			output = output + key.getText();
		output = output + "  \n</key>\n";
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
		output = output + "</DeclareBool>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(key);
		this.add(declare);
		this.add(exp);		
		this.setMaximumSize(this.getPreferredSize());
		repaint();
		try{
			Expression temp = (Expression) this.getParent();
			temp.recreate(); 
		}catch(Exception ce){
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();	
	}
}