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

public class Dot_NumExpression extends asg01u.components.NumExpression{

	asg01u.components.NumExpression exp1;
	asg01u.components.NumExpression exp2;
	JTextArea dot;
	
	public Dot_NumExpression(String font, int attrib, int size, Color col,asg01u.components.NumExpression in1,asg01u.components.NumExpression in2,boolean root){
			super(font,attrib,size,col,root);
			exp1 = in1;
			exp2 = in2;
			System.out.println("Dot_NumExpression created");
			dot = new JTextArea(""+'\u2219');
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			dot.setAlignmentX(Component.CENTER_ALIGNMENT);
			dot.setAlignmentY(Component.CENTER_ALIGNMENT);
			dot.setEditable(false);
			dot.setMaximumSize(dot.getPreferredSize());
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
			this.add(exp1);
			this.add(dot);
			this.add(exp2);		
			this.setMaximumSize(this.getPreferredSize());
		}
		
		public double getValue(){
			return exp1.getValue() * exp2.getValue();
			}	
		
		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			exp1.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			exp2.setTheFont(fontName,fontAttributes,fontSize,colour,what);
			dot.setFont(new Font(fontName,fontAttributes,fontSize));
			dot.setForeground(colour);
			dot.setMaximumSize(dot.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Dot_NumExpression>\n";
		output = output + "  <DotExpression1>\n";
			output = output + exp1.outputString();
		output = output + "  </DotExpression1>\n";
		output = output + "  <DotExpression2>\n";
			output = output + exp2.outputString();
		output = output + "  </DotExpression2>\n";
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
		output = output + "</Dot_NumExpression>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(exp1);
		this.add(dot);
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
		return new Dot_NumExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(NumExpression) exp1.clone(),(NumExpression) exp2.clone(),true);
	}			
}