package asg01u.components.NumExpressions;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.*;
import asg01u.components.*;
import asg01u.components.BoolExpressions.*;
import asg01u.components.NumExpressions.*;

public class Declared_NumExpression extends asg01u.components.NumExpression{
	
	String value;
	JTextArea area;
	NumExpression var;
	boolean expanded;
	static HashMap variables;
	
	public Declared_NumExpression(String font, int attrib, int size, Color col,String input,boolean root,boolean show,HashMap hm){
		super(font,attrib,size,col,root);
		variables = hm;
		expanded = show;
		value = input;
		System.out.println("Declared_NumExpression(\""+input+"\") called");
		
		if(variables.containsKey(value)){
		NumExpression temp = (NumExpression) variables.get(value);
		try{
		var = (NumExpression) temp.clone();
		}catch(Exception e){
		System.out.println(e.getMessage());
		var = null;	
		}
		}else{
		var = null;
		}
		
		area = new JTextArea(value);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		for(int i=0;i<4;i++){
			this.setTheFont(font,attrib,size,col,i);
			}
		area.setEditable(false);
		if(expanded&&var!=null){
			this.add(var);
		}else{
			this.add(area);
		}
		this.setMaximumSize(this.getPreferredSize());
		
		JMenuItem expand = new JMenuItem("(Un)Expand");
		expand.addActionListener(this);
		popup.add(expand);
	}
	
	public double getValue(){
		if(var!=null){
		return var.getValue();	
		}else{
		return 0.0;
		}
		}	

		public void setTheFont(String font, int attrib, int size, Color col, int what){
			if(what==0){fontName=font;}
			else if(what==1){fontAttributes=attrib;}
			else if(what==2){fontSize=size;}
			else if(what==3){colour=col;}
			if(var!=null){
				var.setTheFont(fontName,fontAttributes,fontSize,colour,what);	
			}
			area.setFont(new Font(fontName,fontAttributes,fontSize));
			area.setForeground(colour);
			area.setMaximumSize(area.getPreferredSize());
			recreate();
			}
			
	public String outputString(){
		String output = new String();
		output = "<Declared_NumExpression>\n";
		output = output + "  <DeclaredValue>\n";
			output = output + value;
		output = output + "\n  </DeclaredValue>\n";
		output = output + "  <expanded>\n";
			output = output + expanded;
		output = output + "\n  </expanded>\n";
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
		output = output + "</Declared_NumExpression>\n";
		return output;	
	}
	
	public void recreate(){
		this.removeAll();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));			
		if(expanded&&var!=null){
			var.setMaximumSize(var.getPreferredSize());
			var.validate();
			this.add(var);
		}else{
			area.setMaximumSize(area.getPreferredSize());
			area.validate();
			this.add(area);
		}
		this.setMaximumSize(this.getPreferredSize());
		repaint();
		validate();
		try{
			this.getRootPane().validate();
			Expression temp = (Expression) this.getParent();
			temp.validate();
			temp.recreate(); 
		}catch(Exception ce){
		}
	}
	
		public void actionPerformed(ActionEvent e){
		JMenuItem temp = (JMenuItem) e.getSource();
		if(temp.getText().compareTo("Bold")==0){
			fontAttributes = Font.BOLD;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,1);
			}
		else if(temp.getText().compareTo("Italic")==0){
			fontAttributes = Font.ITALIC;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,1);
			}
		else if(temp.getText().compareTo("Plain")==0){
			fontAttributes = Font.PLAIN;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,1);
			}
		else if(temp.getText().compareTo("Black")==0){
			colour = Color.BLACK;
			System.out.println("Black");
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Blue")==0){
			colour = Color.BLUE;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Red")==0){
			colour = Color.RED;
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("Green")==0){
			colour = new Color(0,200,0);
			this.setTheFont(fontName, fontAttributes, fontSize, colour,3);
			}
		else if(temp.getText().compareTo("(Un)Expand")==0){
			expanded = !expanded;
			recreate();
			validate();
			}
		else if(temp.getText().compareTo("Value")==0){
			JOptionPane.showMessageDialog(this, getValueString());
			}
		else if(temp.getText().substring(temp.getText().length()-2).compareTo("pt")==0){
				Integer temp2 = new Integer(temp.getText().substring(0,temp.getText().length()-3));	
				fontSize = temp2.intValue();
				this.setTheFont(fontName, fontAttributes, fontSize, colour,2);
			}
		else{
			fontName = temp.getText();
			this.setTheFont(fontName, fontAttributes, fontSize, colour,0);
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		return new Declared_NumExpression(new String(fontName),fontAttributes,fontSize,new Color(colour.getRed(),colour.getGreen(),colour.getBlue()),new String(value),true,expanded,variables);
	}
}
