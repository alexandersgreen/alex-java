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

public class Not_BoolExpression extends asg01u.components.BoolExpression{
	
	asg01u.components.BoolExpression exp;	
	JTextArea area1;
	
	public Not_BoolExpression(String font, int attrib, int size, Color col,asg01u.components.BoolExpression in,boolean root){
			super(font,attrib,size,col,root);
			exp = in;
			area1 = new JTextArea("!");
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			area1.setEditable(false);
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.add(area1);
			this.add(exp);
		this.setMaximumSize(this.getPreferredSize());
	}
	
	public boolean getValue(){
		return !exp.getValue();
		}	

		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			area1.setFont(new Font(fontName,fontAttributes,fontSize));
			area1.setForeground(colour);
			area1.setMaximumSize(area1.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Not_BoolExpression>\n";
		output = output + "  <NotExpression>\n";
			output = output + exp.outputString();
		output = output + "  </NotExpression>\n";
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
		output = output + "</Not_BoolExpression>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(area1);
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
		return new Not_BoolExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(BoolExpression) exp.clone(),true);
	}	
}