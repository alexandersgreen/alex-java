package asg01u.components.BoolExpressions;

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

public class Equals_BoolExpression extends asg01u.components.BoolExpression{

	asg01u.components.NumExpression exp1;
	public asg01u.components.NumExpression exp2;
	JTextArea equals;
	
	public Equals_BoolExpression(String font, int attrib, int size, Color col,asg01u.components.NumExpression in1,asg01u.components.NumExpression in2,boolean root){
			super(font,attrib,size,col,root);
			exp1 = in1;
			exp2 = in2;
			System.out.println("Equals_BoolExpression created");
			equals = new JTextArea("=");
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			equals.setAlignmentX(Component.CENTER_ALIGNMENT);
			equals.setAlignmentY(Component.CENTER_ALIGNMENT);
			equals.setEditable(false);
			equals.setMaximumSize(equals.getPreferredSize());
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
			this.add(exp1);
			this.add(equals);
			this.add(exp2);		
			this.setMaximumSize(this.getPreferredSize());
		}
		
		public boolean getValue(){
			return exp1.getValue() == exp2.getValue();
			}	

		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp1.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			exp2.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			equals.setFont(new Font(fontName,fontAttributes,fontSize));
			equals.setForeground(colour);
			equals.setMaximumSize(equals.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Equals_BoolExpression>\n";
		output = output + "  <EqualsExpression1>\n";
			output = output + exp1.outputString();
		output = output + "  </EqualsExpression1>\n";
		output = output + "  <EqualsExpression2>\n";
			output = output + exp2.outputString();
		output = output + "  </EqualsExpression2>\n";
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
		output = output + "</Equals_BoolExpression>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(exp1);
		this.add(equals);
		this.add(exp2);		
		this.setMaximumSize(this.getPreferredSize());
		repaint();
		try{
			Expression temp = (Expression) this.getParent();
			temp.recreate(); 
		}catch(Exception ce){
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new Equals_BoolExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(NumExpression) exp1.clone(),(NumExpression) exp2.clone(),true);
	}
}