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

public class ImpliesL_BoolExpression extends asg01u.components.BoolExpression{

	asg01u.components.BoolExpression exp1;
	asg01u.components.BoolExpression exp2;
	JTextArea impL;
	
	public ImpliesL_BoolExpression(String font, int attrib, int size, Color col,asg01u.components.BoolExpression in1,asg01u.components.BoolExpression in2,boolean root){
			super(font,attrib,size,col,root);
			exp1 = in1;
			exp2 = in2;
			System.out.println("ImpliesL_BoolExpression created");
			impL = new JTextArea("<="/*+'\u21d0'*/);
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			impL.setAlignmentX(Component.CENTER_ALIGNMENT);
			impL.setAlignmentY(Component.CENTER_ALIGNMENT);
			impL.setEditable(false);
			impL.setMaximumSize(impL.getPreferredSize());
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
			this.add(exp1);
			this.add(impL);
			this.add(exp2);		
			this.setMaximumSize(this.getPreferredSize());
		}
		
		public boolean getValue(){
			boolean temp;
			exp2.getValue();
			if(exp1.getValue()){
				if(exp2.getValue()){
				temp = true;
				}else{
				temp = true;
				}
			}else{
				if(exp2.getValue()){
				temp = false;
				}else{
				temp = true;
				}
			}
			return temp;
			}	

		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp1.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			exp2.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			impL.setFont(new Font(fontName,fontAttributes,fontSize));
			impL.setForeground(colour);
			impL.setMaximumSize(impL.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<ImpliesL_BoolExpression>\n";
		output = output + "  <ImpliesLExpression1>\n";
			output = output + exp1.outputString();
		output = output + "  </ImpliesLExpression1>\n";
		output = output + "  <ImpliesLExpression2>\n";
			output = output + exp2.outputString();
		output = output + "  </ImpliesLExpression2>\n";
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
		output = output + "</ImpliesL_BoolExpression>\n";
		return output;	
	}
	
		public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(exp1);
		this.add(impL);
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
		return new ImpliesL_BoolExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(BoolExpression) exp1.clone(),(BoolExpression) exp2.clone(),true);
	}			
}