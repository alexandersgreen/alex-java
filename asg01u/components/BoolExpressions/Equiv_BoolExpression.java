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

public class Equiv_BoolExpression extends asg01u.components.BoolExpression{

	asg01u.components.BoolExpression exp1;
	public asg01u.components.BoolExpression exp2;
	JTextArea equiv;
	
	public Equiv_BoolExpression(String font, int attrib, int size, Color col,asg01u.components.BoolExpression in1,asg01u.components.BoolExpression in2,boolean root){
			super(font,attrib,size,col,root);
			exp1 = in1;
			exp2 = in2;
			System.out.println("Equiv_BoolExpression created");
			equiv = new JTextArea(""+'\u2261');
			for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
			equiv.setAlignmentX(Component.CENTER_ALIGNMENT);
			equiv.setAlignmentY(Component.CENTER_ALIGNMENT);
			equiv.setEditable(false);
			equiv.setMaximumSize(equiv.getPreferredSize());
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
			this.add(exp1);
			this.add(equiv);
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
			equiv.setFont(new Font(fontName,fontAttributes,fontSize));
			equiv.setForeground(colour);
			equiv.setMaximumSize(equiv.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Equiv_BoolExpression>\n";
		output = output + "  <EquivExpression1>\n";
			output = output + exp1.outputString();
		output = output + "  </EquivExpression1>\n";
		output = output + "  <EquivExpression2>\n";
			output = output + exp2.outputString();
		output = output + "  </EquivExpression2>\n";
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
		output = output + "</Equiv_BoolExpression>\n";
		return output;	
	}
	
		public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		this.add(exp1);
		this.add(equiv);
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
		return new Equiv_BoolExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),(BoolExpression) exp1.clone(),(BoolExpression) exp2.clone(),true);
	}			
}