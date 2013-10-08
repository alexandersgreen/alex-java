import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class RouteFindCanvas extends Canvas implements MouseListener,MouseMotionListener {

	Vector linksV;
	Vector donelinksV;
	Vector nodesV;
	
	int currentButton;
	int tempHolder;
	
	boolean editingN;
	boolean editingL;
	boolean linkStart;
	
	int button = -1;
	
	public RouteFindCanvas(){
		currentButton = 2;
		nodesV = new Vector();
		linksV = new Vector();
		donelinksV = new Vector();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setSize(new Dimension(800,650));
		editingN = true;
		editingL = false;
		linkStart = true;
	}
	
	public void paint(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0,0,800,50);
		g.setColor(Color.RED);
		g.fillRect(currentButton*100,0,100,50);
		g.setColor(Color.WHITE);
		g.fillRect(0,50,800,600);
		g.drawRect(000,0,100,50);
		g.drawString("RESET",030,30);
		g.drawRect(100,0,100,50);
		g.drawString("LINKS",130,30);
		g.drawRect(200,0,100,50);
		g.drawString("NODES",230,30);
		g.drawRect(300,0,100,50);
		g.drawRect(400,0,100,50);
		g.drawRect(500,0,100,50);
		g.drawRect(600,0,100,50);
		g.drawRect(700,0,100,50);
		g.drawRect(currentButton*100,0,100,50);
		for(int i=0;i<nodesV.size();i++){
			int[] node = (int[]) nodesV.elementAt(i);
			if(i==0){
				g.setColor(Color.GREEN);
			}else{
				g.setColor(Color.BLUE);
			}
			g.fillRect(node[0],node[1],6,6);
		}
		for(int i=0;i<linksV.size();i++){
			int[] linkinfo = (int[]) linksV.elementAt(i);
			g.setColor(Color.BLUE);
			int[] start = (int[]) nodesV.elementAt(linkinfo[0]);
			int[] finish = (int[]) nodesV.elementAt(linkinfo[1]);
			int[] link = new int[4];
			link[0]=start[0];
			link[1]=start[1];
			link[2]=finish[0];
			link[3]=finish[1];
			g.drawLine(link[0]+3,link[1]+3,link[2]+3,link[3]+3);
		}
		for(int i=0;i<donelinksV.size();i++){
			int[] link = (int[]) donelinksV.elementAt(i);
			g.setColor(Color.RED);
			g.drawLine(link[0]+3,link[1]+3,link[2]+3,link[3]+3);
		}
	}
	
	public void mouseMoved(MouseEvent e){
	}
	
	public void mouseDragged(MouseEvent e){
	}
	
	public void mouseEntered(MouseEvent e){
	}
	
	public void mouseExited(MouseEvent e){
	}
	
	public void mousePressed(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		if(y<=50&&y>=0){
			button = x/100;
			executeButton(button);
		}else{
			button = -1;
		}
	}
	
	public void mouseReleased(MouseEvent e){
		if(button!=-1){
			button = -1;
		}
	}
	
	public void mouseClicked(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		if(editingN){
			if(y>50&&y<=850){
				int[] node = new int[2];
				node[0] = x;
				node[1] = y;		
				nodesV.add(node);
				repaint();
			}
		}else if(editingL){
			int theNode = getNode(x,y);
			if(theNode!=-1){
				if(linkStart){
					Graphics g = this.getGraphics();
					int[] node = (int[]) nodesV.elementAt(theNode);
					g.setColor(Color.RED);
					g.fillRect(node[0],node[1],6,6);
					tempHolder = theNode;
					linkStart = false;
				}else{
					int[] theLink = new int[2];
					theLink[0]=tempHolder;
					theLink[1]=theNode;
					linksV.add(theLink);
					linkStart = true;
					calculateRoutes();
					repaint();
				}
			}	
		}
	}
	
	public int getNode(int x, int y){
		if(y<=50){
			return -1;
		}
		double distance = Double.MAX_VALUE;
		int node = -1;
		for(int i=0;i<nodesV.size();i++){
			int[] theNode = (int[]) nodesV.elementAt(i);
			double dist = Math.sqrt(((theNode[0]-x)*(theNode[0]-x))+((theNode[1]-y)*(theNode[1]-y)));
			if(dist<distance){
				distance = dist;
				node = i;
			}		
		}
		return node;
	}
	
	public void executeButton(int button){
		if(button == 0){
			donelinksV.removeAllElements();
			linksV.removeAllElements();
			nodesV.removeAllElements();
			currentButton = 2;
			repaint();
			editingN = true;
			editingL = false;
		}else if(button == 1){
			editingN = false;
			editingL = true;
			currentButton = 1;
			repaint();
		}else if(button == 2){
			editingN = true;
			editingL = false;
			//calculateRoutes();
			repaint();
			currentButton = 2;
		}else if(button == 3){
			
		}else if(button == 4){
			
		}else if(button == 5){
			
		}else if(button == 6){
			
		}else if(button == 7){
			
		}
	}
	
	public void calculateRoutes(){
		donelinksV.removeAllElements();
		boolean routeFoundTo[] = new boolean[nodesV.size()];
		double distanceTo[] = new double[nodesV.size()];
		// S = all true entries, V-S = all false entries
		short predecessor[] = new short[nodesV.size()];
		//initialise S to empty! and distances to max!
		for(int j=0;j<nodesV.size();j++){
			routeFoundTo[j]=false;
			distanceTo[j]=Double.MAX_VALUE;
			predecessor[j]=-1;
		}
		//routeFoundTo[0]=true;
		distanceTo[0]=0.0;
		boolean vs = getVS(routeFoundTo);
		while(vs){
			int minPos = -1;
			double minVal = Double.MAX_VALUE;
			for(int j=0;j<nodesV.size();j++){
				if(distanceTo[j]<=minVal&&routeFoundTo[j]==false){
						minPos = j;
						minVal = distanceTo[j];
				}
			}
			if(minVal == Double.MAX_VALUE){
				break;
			}
			routeFoundTo[minPos]=true;
					
				
			//at this point, minPos is the node we're working from
			//so any points connected to nodes[minPos] need to be
			//updated to have the value of ditanceTo[minPos] + length
			//of the link! (only if less than current value)
			int[] start = (int[]) nodesV.elementAt(minPos);
			
			for(int j=0;j<nodesV.size();j++){
				if(connected(minPos,j)){
					int[] finish = (int[]) nodesV.elementAt(j);
					double length = Math.sqrt(((finish[0]-start[0])*(finish[0]-start[0]))+((finish[1]-start[1])*(finish[1]-start[1])));
					if((distanceTo[minPos]+length<distanceTo[j])){
						distanceTo[j]=distanceTo[minPos]+length;
						predecessor[j]=(short)minPos;
						int[] link = new int[4];
						link[0]= start[0];
						link[1]= start[1];
						link[2]= finish[0];
						link[3]= finish[1];
						donelinksV.add(link);
					}	
				}
			}
			vs = getVS(routeFoundTo);
		}
	}
	
	public boolean getVS(boolean[] routeFoundTo){
		for(int i=0;i<routeFoundTo.length;i++){
		if(routeFoundTo[i]==false){
			return true;
		}
		}	
		return false;
	}
	
	public boolean connected(int start,int finish){
		for(int i=0;i<linksV.size();i++){
			int[] theLink = (int[]) linksV.elementAt(i);
			if(theLink[0]==start&&theLink[1]==finish){
				return true;
			}else if(theLink[1]==start&&theLink[0]==finish){
				return true;
			}
		}
		return false;
	}

	

}