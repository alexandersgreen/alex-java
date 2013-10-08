import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.geom.*;

public class LogMapCanvas extends Canvas{
	
	int oldX;
	int oldY;
	double zoom;
	
	double width;
	
	Rectangle2DFloat_orig [] readings;
	Line2D.Float[] connections;
	
	ImageIcon map = new ImageIcon("GBmap.jpg");
	
	Vector joins;
	
	public LogMapCanvas(Vector input,Vector j){
		width = 411;
		zoom=1;
		joins = j;
		readings = new Rectangle2DFloat_orig[input.size()];
		connections = new Line2D.Float[input.size()-1];
		
		double smallestY = 100000;
		double biggestY =  700000;
		double smallestX = 11000;
		double biggestX =  975000;
		
		double width = biggestX-smallestX;
		double height = biggestY-smallestY;
		//System.out.println("Width="+width+"\nHeight="+height+"\nX="+smallestX+"to"+biggestX+"\nY="+smallestY+"to"+biggestY);
		
		double scale = width;	
		
		if(height>width){
			scale = height;
		}
		for(int i=0;i<input.size();i++){
			double[] point = (double[]) input.elementAt(i);
			double y = 655-((point[0]-smallestX)*(655/(scale)));
			double x = ((point[1]-smallestY)*(655/scale));
			readings[i]=new Rectangle2DFloat_orig((float)x,(float)y,(float)2,(float)2,point[1],point[0]);
			if(i>0){
				connections[i-1]=new Line2D.Float(readings[i-1].x,readings[i-1].y,(float)x,(float)y);
			}
		}
	}	
		
	
	public boolean onScreen(int i){
		if(readings[i].getX()<-50){return false;}
		else if(readings[i].getX()>650){return false;}
		else if(readings[i].getY()<-50){return false;}
		else if(readings[i].getY()>650){return false;}
		else{return true;}
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		//g2.setColor(Color.WHITE);
		//g2.fillRect(0,0,411,655);
		g2.drawImage(map.getImage(),0,0,this);
		for(int i=0;i<readings.length;i++){
			if(onScreen(i)){
			//System.out.println(readings[i].x+","+readings[i].y);
			g2.setColor(Color.BLUE);
			g2.fill(readings[i]);
			if(i<connections.length&&notInJoins(i+1)){
				g2.setColor(Color.MAGENTA);	
				g2.draw(connections[i]);
				//System.out.println(i);
			}
			}
		}
	}
	

public boolean notInJoins(int find){
	for(int i=0;i<joins.size();i++){
		Integer compare = (Integer) joins.elementAt(i);
		if(compare.intValue()==find){
			return false;
		}
	}
	return true;
}

}