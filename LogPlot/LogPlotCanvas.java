import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.geom.*;

public class LogPlotCanvas extends Canvas implements MouseListener,MouseMotionListener,MouseWheelListener{
	
	int oldX;
	int oldY;
	double zoom;
	
	Rectangle2DFloat_orig [] readings;
	Line2D.Float[] connections;
	
	Vector joins;
	
	public LogPlotCanvas(Vector input,Vector j){
		zoom=1;
		joins = j;
		readings = new Rectangle2DFloat_orig[input.size()];
		connections = new Line2D.Float[input.size()-1];
		double smallestX = Double.MAX_VALUE;
		double biggestX = Double.NEGATIVE_INFINITY;
		double smallestY = Double.MAX_VALUE;
		double biggestY = Double.NEGATIVE_INFINITY;
		for(int i=0;i<input.size();i++){
			double[] point = (double[]) input.elementAt(i);
			if(point[0]<smallestX){smallestX=point[0];}
			if(point[0]>biggestX){biggestX=point[0];}
			if(point[1]<smallestY){smallestY=point[1];}
			if(point[1]>biggestY){biggestY=point[1];}
		}
		double width = biggestX-smallestX;
		double height = biggestY-smallestY;
		//System.out.println("Width="+width+"\nHeight="+height+"\nX="+smallestX+"to"+biggestX+"\nY="+smallestY+"to"+biggestY);
		
		double scale = width;	
		
		if(height>width){
			scale = height;
		}
		for(int i=0;i<input.size();i++){
			double[] point = (double[]) input.elementAt(i);
			double y = 600-((point[0]-smallestX)*(600/(scale)));
			double x = ((point[1]-smallestY)*(600/scale));
			readings[i]=new Rectangle2DFloat_orig((float)x,(float)y,(float)2,(float)2,point[1],point[0]);
			if(i>0){
				connections[i-1]=new Line2D.Float(readings[i-1].x,readings[i-1].y,(float)x,(float)y);
			}
		}
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}	
		
	public void mouseClicked(MouseEvent e){
		if(e.getButton()!=MouseEvent.BUTTON1){
			slowPaint(this.getGraphics());
		}else{
			for(int i=0;i<readings.length;i++){
				if(readings[i].contains(e.getX(),e.getY())){
					System.out.println("("+readings[i].origX+","+readings[i].origY+")");
				}
			}
		}
	}
	
	
	public void slowPaint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,600,600);
		for(int i=0;i<readings.length;i++){
			if(onScreen(i)){
			//System.out.println(readings[i].x+","+readings[i].y);
			g2.setColor(Color.BLACK);
			g2.fill(readings[i]);
			if(i<connections.length&&notInJoins(i+1)){
				g2.setColor(Color.RED);	
				g2.draw(connections[i]);
			}
			
			try{
				Thread.sleep(1);
			}catch(Exception e){
			}
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
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,600,600);
		for(int i=0;i<readings.length;i++){
			if(onScreen(i)){
			//System.out.println(readings[i].x+","+readings[i].y);
			g2.setColor(Color.BLACK);
			g2.fill(readings[i]);
			if(i<connections.length&&notInJoins(i+1)){
				g2.setColor(Color.RED);	
				g2.draw(connections[i]);
				//System.out.println(i);
			}
			}
		}
	}
	
public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}
public void mouseReleased(MouseEvent e){}

public boolean notInJoins(int find){
	for(int i=0;i<joins.size();i++){
		Integer compare = (Integer) joins.elementAt(i);
		if(compare.intValue()==find){
			return false;
		}
	}
	return true;
}



public void mousePressed(MouseEvent e){
		oldX = e.getX();
		oldY = e.getY();
}
	
public void mouseMoved(MouseEvent e){}
	
public void mouseWheelMoved(MouseWheelEvent e){
	int notches = e.getWheelRotation();
	if(notches<0){
		zoom = (-1.4)*notches;
	}else{
		zoom = (notches/1.4);	
	}
	zoomTo(zoom);
}
	
public void mouseDragged(MouseEvent e){
	int newX = e.getX();
	int newY = e.getY();

	//System.out.println("Mouse Dragged from ("+oldX+","+oldY+") to ("+newX+","+newY+")");
	
	int distanceX = newX-oldX;
	int distanceY = newY-oldY;
	
	moveTo(distanceX,distanceY);
	
	oldX = newX;
	oldY = newY;
}

public void moveTo(int x, int y){
	for(int i=0;i<readings.length;i++){

		readings[i].setRect(
			readings[i].getX()+x,
			readings[i].getY()+y,
			readings[i].getWidth(),
			readings[i].getHeight()
			);

		if(i<readings.length-1){
			connections[i].setLine(
				connections[i].getX1()+x,
				connections[i].getY1()+y,
				connections[i].getX2()+x,
				connections[i].getY2()+y
				);
		}
		
	}
	this.paint(this.getGraphics());
}

public void zoomTo(double input){
	zoom = input;
	for(int i=0;i<readings.length;i++){

		readings[i].setRect(
			((readings[i].getX()-300)*zoom)+300,
			((readings[i].getY()-300)*zoom)+300,
			readings[i].getWidth(),
			readings[i].getHeight()
			);

		if(i<readings.length-1){
			connections[i].setLine(
				((connections[i].getX1()-300)*zoom)+300,
				((connections[i].getY1()-300)*zoom)+300,
				((connections[i].getX2()-300)*zoom)+300,
				((connections[i].getY2()-300)*zoom)+300
				);
		}
		
	}
	this.paint(this.getGraphics());
}

}