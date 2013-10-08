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

public class Div_NumExpression extends asg01u.components.NumExpression{

	asg01u.components.NumExpression exp1;
	asg01u.components.NumExpression exp2;
	JTextArea div;
	
	public Div_NumExpression(String font, int attrib, int size, Color col,asg01u.components.NumExpression in1,asg01u.components.NumExpression in2,boolean root){
			super(font,attrib,size,col,root);
			exp1 = in1;
			exp2 = in2;
			System.out.println("Div_NumExpression created");
			String divLine = "";
			for(int i =-1; (i<exp1.getPreferredSize().width/10)||(i<exp2.getPreferredSize().width/10);i++){
				divLine = divLine + '\u2500';
			}
			div = new JTextArea(divLine);
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			div.setAlignmentX(Component.CENTER_ALIGNMENT);
			div.setAlignmentY(Component.CENTER_ALIGNMENT);
			div.setMaximumSize(div.getPreferredSize());
			div.setEditable(false);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));			
			this.add(exp1);
			this.add(div);
			this.add(exp2);		
			this.setMaximumSize(this.getPreferredSize());
		}
		
		public double getValue(){
			return exp1.getValue() / exp2.getValue();
			}	
		
		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp1.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			exp2.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			div.setFont(new Font(fontName,fontAttributes,fontSize));
			div.setForeground(colour);
			div.setMaximumSize(div.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Div_NumExpression>\n";
		output = output + "  <DivExpression1>\n";
			output = output + exp1.outputString();
		output = output + "  </DivExpression1>\n";
		output = output + "  <DivExpression2>\n";
			output = output + exp2.outputString();
		output = output + "  </DivExpression2>\n";
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
		output = output + "</Div_NumExpression>\n";
		return output;	
	}			

	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));			
		this.add(exp1);
		this.add(div);
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
		return new Div_NumExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(NumExpression) exp1.clone(),(NumExpression) exp2.clone(),true);
	}
}