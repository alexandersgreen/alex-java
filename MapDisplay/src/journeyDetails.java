import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class journeyDetails extends JFrame{
	
	public Vector arrows;
	public Vector theRoute;
	public String[] details;
	double oldBearing;
	
	public journeyDetails(Vector route){
		oldBearing = 0.0;
		theRoute = route;
		this.setTitle("Journey Details");
		this.setSize(new Dimension(870,675));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	this.getContentPane().setLayout(new GridLayout(0,2));
    	
    	details = new String[(route.size())+1];
    	getDetails();
    	
    	String display = new String();
    	for(int i=0;i<details.length;i++){
    		display = display + details[i];
    	}
    	JTextArea text = new JTextArea(display,40,40);
    	text.setEditable(false);
    	text.setLineWrap(true);
    	text.setWrapStyleWord(true);
    	JScrollPane scroll = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scroll.setMaximumSize(new Dimension(600,600));
    	scroll.setMinimumSize(new Dimension(600,600));
    	this.getContentPane().add(scroll);
    	
    	Panel panel = new Panel();
    	panel.setMinimumSize(new Dimension(140,600));
    	panel.setMaximumSize(new Dimension(140,600));
    	panel.setLayout(new GridLayout(0,2));
    	panel.setBackground(Color.WHITE);
    	
    	for(int i=0;i<arrows.size();i++){
    		double[] angles = (double[]) arrows.elementAt(i);
    		ArrowsPanel arrowpan1 = new ArrowsPanel(angles[0]);
    		panel.add(arrowpan1);
    		ArrowsPanel arrowpan2 = new ArrowsPanel(angles[1]);
    		panel.add(arrowpan2);
    	}
    	   		
   		JScrollPane scroll2 = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	scroll2.setMinimumSize(new Dimension(140,600));
    	scroll2.setMaximumSize(new Dimension(140,600));
    	this.getContentPane().add(scroll2);
    	this.setVisible(true);
    	this.toFront();
	}

	public void getDetails(){
		
		arrows = new Vector();
		
		details[0]="Start"+"\n";

		if(details.length>2){
			for(int i=1;i<(details.length-1);i++){
				node start = (node) theRoute.elementAt(i-1);
				node finish = (node) theRoute.elementAt(i);
				
				int sxpos = ((start.posX+1092299700)/4)-272629775;
				int sypos = ((start.posY+1091800300)/4)-272629775;
				int fxpos = ((finish.posX+1092299700)/4)-272629775;
				int fypos = ((finish.posY+1091800300)/4)-272629775;
				
				double bearing = getBearing(sxpos,sypos,fxpos,fypos);
				double turn = bearing - oldBearing;
				
				double[] pair = new double[2];
				pair[0] = bearing;
				pair[1] = turn;
				arrows.add(pair);
				
				details[i]="From "+sxpos+","+sypos+" To "+fxpos+","+fypos+"\n";
				details[i]=details[i]+"  Bearing of "+bearing+": Turn "+turn+" from current bearing\n";
				oldBearing = bearing;
			}
		}
		
		details[(details.length-1)]="Finish"+"\n";
		
	}

	public double getBearing(int x1, int y1, int x2, int y2){
		double result = 0.0;
		if(x2>x1&&y2>y1){
			double tantheta = (x2-x1)/(y2-y1);
			result = Math.toDegrees(Math.atan(tantheta));
		}
		else if(x2>x1&&y2<y1){
			double tantheta = (x2-x1)/(y1-y2);
			result = 180 - Math.toDegrees(Math.atan(tantheta));
		}
		else if(x2<x1&&y2<y1){
			double tantheta = (x1-x2)/(y1-y2);
			result = 180 + Math.toDegrees(Math.atan(tantheta));
		}
		else if(x2<x1&&y2>y1){
			double tantheta = (x1-x2)/(y2-y1);
			result = 360 - Math.toDegrees(Math.atan(tantheta));
		}
		else if(x2==x1){
			if(y2>y1){
				result = 0.0;
			}else if(y2<y1){
				result = 180.0;
			}else{
				result = 0.0;
			}
		}
		else if(y2==y1){
			if(x2>x1){
				result = 90.0;
			}else if(x2<x1){
				result = 270.0;
			}
		}
		return result;
	}
}