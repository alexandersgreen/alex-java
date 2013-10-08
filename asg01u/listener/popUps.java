package asg01u.listener;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.*;
import asg01u.components.*;

public class popUps implements MouseListener,FocusListener{
	
	JPopupMenu popup;
	
	public popUps(JPopupMenu input){
	popup = input;
	}

	public void mousePressed(MouseEvent e) {
    if (e.isPopupTrigger()) {
    	popup.show(e.getComponent(), e.getX(), e.getY());
    }
    }
    
    public void mouseEntered(MouseEvent e) {
    	JComponent temp = (JComponent) e.getComponent();
    	temp.setBorder(BorderFactory.createLineBorder(Color.RED)); 
    }
    
    public void mouseExited(MouseEvent e) {
    	JComponent temp = (JComponent) e.getComponent();
    	if(temp.hasFocus()){
    	temp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    	}else{
    	temp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    	}
    }

    public void mouseClicked(MouseEvent e) {
    	JComponent temp = (JComponent) e.getComponent();
    	temp.requestFocus();
    }
    
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    public void focusGained(FocusEvent e){
    	JComponent temp = (JComponent) e.getComponent();
    	temp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    	JRootPane pane = temp.getRootPane();
    	asg01u_main main = (asg01u_main) pane.getParent();
    	int pos = -1;
    	for(int i=0;i<main.components.size();i++){
    		if(temp==main.components.elementAt(i)){
    			pos = i;
    		}else if(temp.getParent()==main.components.elementAt(i)){
    			pos = i;
    	}
    	}
			main.setPosition(pos);
    	}
    
    public void focusLost(FocusEvent e){
    	JComponent temp = (JComponent) e.getComponent();
    	temp.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    	JRootPane pane = temp.getRootPane();
    	asg01u_main main = (asg01u_main) pane.getParent();
    	//main.setPosition(-1);
    	}
}		