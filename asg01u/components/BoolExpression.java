package asg01u.components;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;

public abstract class BoolExpression extends Expression implements Cloneable{
	
	public BoolExpression(String font, int attrib, int size, Color col,boolean root){
		super(font,attrib,size,col,root);
		JMenuItem value;
    	value = new JMenuItem("Value");
    	value.addActionListener(this);
    	popup.add(value);
		}
		
	public String getValueString(){
	return ""+getValue();	
	}
	
	public abstract boolean getValue();
	public abstract void setTheFont(String font, int attrib, int size, Color col, int what);
	public abstract Object clone() throws CloneNotSupportedException ;
}