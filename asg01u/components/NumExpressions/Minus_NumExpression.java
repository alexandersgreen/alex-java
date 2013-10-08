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

public class Minus_NumExpression extends asg01u.components.NumExpression{

	asg01u.components.NumExpression exp1;
	asg01u.components.NumExpression exp2;
	JTextArea minus;
	
	public Minus_NumExpression(String font, int attrib, int size, Color col,asg01u.components.NumExpression in1,asg01u.components.NumExpression in2,boolean root){
			super(font,attrib,size,col,root);
			exp1 = in1;
			exp2 = in2;
			System.out.println("Minus_NumExpression created");
			minus = new JTextArea("-");
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			minus.setAlignmentX(Component.CENTER_ALIGNMENT);
			minus.setAlignmentY(Component.CENTER_ALIGNMENT);
			minus.setEditable(false);
			minus.setMaximumSize(minus.getPreferredSize());
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
			this.add(exp1);
			this.add(minus);
			this.add(exp2);		
			this.setMaximumSize(this.getPreferredSize());
		}
		
		public double getValue(){
			return exp1.getValue() - exp2.getValue();
			}	

		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp1.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			exp2.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			minus.setFont(new Font(fontName,fontAttributes,fontSize));
			minus.setForeground(colour);
			minus.setMaximumSize(minus.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Minus_NumExpression>\n";
		output = output + "  <MinusExpression1>\n";
			output = output + exp1.outputString();
		output = output + "  </MinusExpression1>\n";
		output = output + "  <MinusExpression2>\n";
			output = output + exp2.outputString();
		output = output + "  </MinusExpression2>\n";
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
		output = output + "</Minus_NumExpression>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(exp1);
		this.add(minus);
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
		return new Minus_NumExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(NumExpression) exp1.clone(),(NumExpression) exp2.clone(),true);
	}
}