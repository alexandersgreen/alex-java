import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class MapDisplayCanvas extends Canvas implements MouseListener,MouseMotionListener,MouseWheelListener {

	link[] links; // 12090
	node[] nodes; // 9450
	
	Vector logPoints;
	Vector logLines;
	Vector logRoads;
	
	Rectangle2DFloat oldPos;
	Rectangle2DFloat newPos;
	
	public boolean nr = true;
	
	int[][] nodePos; // 9450 2
	
	double[][] translation; //Stores the current translation
	
	double zoom;
	int zoomposX;
	int zoomposY;
	
	int oldX;
	int oldY;
	
	boolean notZoomed = true;
	
	boolean posSelected;
	int[] selectedNodes; // 2
	double distance;
	
	public MapDisplayCanvas(link[] l,node[] n){
		oldPos = new Rectangle2DFloat((float)-1,(float)-1,(float)1,(float)1);
		newPos = new Rectangle2DFloat((float)-1,(float)-1,(float)1,(float)1);
		
		logPoints = new Vector();
		logLines = new Vector();
		logRoads = new Vector();
		
		posSelected = false;
		selectedNodes = new int[2];

		links = l;
		nodes = n;
		nodePos = new int[9450][2];
		
		translation = new double[3][3];
		translation[0][0]=1.0;
		translation[0][1]=0.0;
		translation[0][2]=0.0;
		translation[1][0]=0.0;
		translation[1][1]=1.0;
		translation[1][2]=0.0;
		translation[2][0]=0.0;
		translation[2][1]=0.0;
		translation[2][2]=1.0;
		
		zoom = 1;
		zoomposX = 0;
		zoomposY = 0;
		
		oldX = 0;
		oldY = 0; 
		
		System.out.println("Calculating Posistions...");
		for(int i=0;i<9450;i++){
			double x = (nodes[i].posX-15636)*(600/41312.0);
			double y = (nodes[i].posY-52614)*(600/41510.0);
			nodePos[i][0]= (int) x + 140;
			nodePos[i][1]= (int) (600-y) ;
		}
		System.out.println("Positions Calculated");
		System.out.println("Creating Links...                        |");
		for(int i=0;i<12090;i++){
				if(i%300==0){
					System.out.print(".");
				}
				Long node1 = links[i].node1;			
				Long node2 = links[i].node2;
				int node1pos = -1;
				int node2pos = -1;
				for(int j=0;j<9450;j++){
					if(nodes[j].node.longValue()==node1.longValue()){
						node1pos = j;
						nodes[j].addLink(new Integer(i));
					}
					if(nodes[j].node.longValue()==node2.longValue()){
						node2pos = j;
						nodes[j].addLink(new Integer(i));
						}
				}
				if(node1pos!=-1&&node2pos!=-1){
					links[i].theLink = new Line2DFloat((float)((nodePos[node1pos][0])),(float)((nodePos[node1pos][1])),
			    	       	   (float)((nodePos[node2pos][0])),(float)((nodePos[node2pos][1])));

				}else if(node1pos!=-1){
				}else if(node2pos!=-1){
				}else{
				}
		}
		System.out.println("|");
		System.out.println("Links Created");
		System.out.println("Creating Nodes...");
		for(int i=0;i<9450;i++){
			nodes[i].theNode = new Rectangle2DFloat((float)((nodePos[i][0])),(float)((nodePos[i][1])),2,2);		
		}
		System.out.println("Nodes Created");
		System.out.println("Saving...");
		try{
		FileOutputStream fos = new FileOutputStream("c:\\NottinghamRoads.rfi");
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(links);
		os.writeObject(nodes);
		os.writeObject(nodePos);
		os.close();
		}catch(Exception e){
			System.out.println(e);
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		System.out.println("Saved");
		
		this.setSize(new Dimension(900,600));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}
	
	public MapDisplayCanvas(String filename){
		oldPos = new Rectangle2DFloat((float)-1,(float)-1,(float)1,(float)1);
		newPos = new Rectangle2DFloat((float)-1,(float)-1,(float)1,(float)1);
		
		translation = new double[3][3];
		translation[0][0]=1.0;
		translation[0][1]=0.0;
		translation[0][2]=0.0;
		translation[1][0]=0.0;
		translation[1][1]=1.0;
		translation[1][2]=0.0;
		translation[2][0]=0.0;
		translation[2][1]=0.0;
		translation[2][2]=1.0;
		
		logPoints = new Vector();
		logLines = new Vector();
		logRoads = new Vector();
		
		System.out.print("Loading The Map.........");
		try{
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream is = new ObjectInputStream(fis);
		links = (link[]) is.readObject();
		System.out.print(".");
		nodes = (node[]) is.readObject();
		System.out.print(".");
		nodePos = (int[][]) is.readObject();
		System.out.print(".");
		is.close();	
		}catch(Exception e){
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		System.out.println();
		//System.out.println("Loaded");
		
		posSelected = false;
		selectedNodes = new int[2];
		
		zoom = 1;
		zoomposX = 0;
		zoomposY = 0;
		
		//calculateRoutes();
		
		for(int i=0;i<links.length;i++){
			links[i].onRoute=false;
		}
		
		int count = 0;
		for(int i=0;i<9450;i++){
			for(int j=0;j<9450;j++){	
				short next = nodes[i].nextNode[j];
				if(next!=-1){
					count++;
				}
			}
		}
		System.out.println(count + " routes out of a possible "+ (9450*9450));
		
		/*System.out.println("Saving...");
		try{
		FileOutputStream fos = new FileOutputStream("c:\\NottinghamRoads.rfi");
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(links);
		os.writeObject(nodes);
		os.writeObject(nodePos);
		os.close();
		}catch(Exception e){
			System.out.println(e);
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		System.out.println("Saved");*/
		
		this.setSize(new Dimension(900,600));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);	
	}
	
	public void paint(Graphics g){
		this.createBufferStrategy(2);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,900,700);
		drawLinks();
		drawLog();
	}	
	
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	
	public void mouseClicked(MouseEvent e){
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		for(int i=0;i< 9450;i++){
			if(nodes[i].theNode.contains(mouseX,mouseY)){
				Graphics2D g = (Graphics2D) this.getGraphics();
				if(posSelected){
					selectedNodes[1]=i;
					posSelected = false;
					g.setColor(Color.BLUE);
					g.fill(nodes[selectedNodes[1]].theNode);
					g.drawString(("To:   "+selectedNodes[1]),10,570);
					findRoute(true);	
				}else{
					clearScreen(false);
					selectedNodes[0]=i;
					posSelected = true;
					for(int j=0;j<12090;j++){
						links[j].onRoute = false;
					}
					drawLinks();
					g.setColor(Color.RED);
					g.fill(nodes[selectedNodes[0]].theNode);
					g.drawString(("From: "+selectedNodes[0]),10,550);
				}
				break;
			}	
		}
		
	}
	
	public void findRoute(boolean details){
		Vector route = new Vector();
		int currentNode = selectedNodes[0];
		//selectedNodes[1]=0;
		distance = 0.0;
		int count = 0;
		//System.out.println();
		//System.out.println("firstNode = "+currentNode);
		route.add(nodes[currentNode]);
		while(currentNode != selectedNodes[1]){
			count++;
			short nextNode = nodes[currentNode].nextNode[selectedNodes[1]]; 
			//System.out.println("nextNode = "+nextNode);
			if(nextNode==-1){
				//no route available
				JOptionPane.showMessageDialog(this, "No Route Available");
				break;
			}
			
			route.add(nodes[nextNode]);
			Integer linkNum = new Integer(-1);
			
			for(int i=0;i<nodes[currentNode].links.size();i++){
				Integer linkpos = (Integer) nodes[currentNode].links.elementAt(i);
				if(links[linkpos.intValue()].node1.longValue()
				     ==nodes[currentNode].node.longValue()
				  &&links[linkpos.intValue()].node2.longValue()
				     ==nodes[nextNode].node.longValue()){
					linkNum = linkpos;	
				}
				
				if(links[linkpos.intValue()].node2.longValue()
				     ==nodes[currentNode].node.longValue()
				  &&links[linkpos.intValue()].node1.longValue()
				     ==nodes[nextNode].node.longValue()){
					linkNum = linkpos;
				}
			}
			
			if(linkNum.intValue()==-1){
				//no route available
				JOptionPane.showMessageDialog(this, "No Route Available");
				break;
			}
			
			
			links[linkNum.intValue()].onRoute = true;
			Graphics2D g = (Graphics2D) this.getGraphics();
			g.setColor(Color.WHITE);
			g.drawString(""+(distance/1609),750,580);
			distance = distance + links[linkNum.intValue()].length;
			g.setColor(Color.RED);
			g.drawString(""+(distance/1609),750,580);
			g.setColor(Color.BLUE);
			g.fill(nodes[selectedNodes[1]].theNode);
			
			if(links[linkNum.intValue()].theLink!=null){
			g.setColor(Color.RED);
			g.draw(links[linkNum.intValue()].theLink);
			}
			
			currentNode = nextNode;
		}
		drawLinks();
		if(details){
			journeyDetails theDetails = new journeyDetails(route);
			theDetails.toFront();
		}
	}
	
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){
		oldX = e.getX();
		oldY = e.getY();
	}
	
	public void mouseMoved(MouseEvent e){}
	
	public void mouseWheelMoved(MouseWheelEvent e){
		int notches = e.getWheelRotation();
		if(notches<0){
			zoom = (-1.2)*notches;
		}else{
			zoom = (notches/1.2);	
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
	
	public void clearScreen(boolean map){
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		if(map){
		g2.fillRect(150,0,600,700);
		}else{
		g2.fillRect(0,0,150,700);
		g2.fillRect(750,0,150,700);	
		}
	}
		
	public void drawNodes(Color colour){
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		for(int i=0;i<9450;i++){
			g2.setColor(colour);
			if(nodes[0].nextNode[i]!=-1){
				g2.setColor(Color.RED);
				//System.out.println(i+" : "+nodes[0].nextNode[i]);
			}
			g2.fill(nodes[i].theNode);
		}	
	}
	
	public void drawLinks(){
		clearScreen(true);
		clearScreen(false);
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		
		for(int i=0;i<12090;i++){
			
			if(links[i].onRoute){
			g2.setStroke(new BasicStroke((float)3.0));
			g2.setColor(/*getColour(links[i].DescTerm)*/Color.GREEN);
			}else{
			g2.setStroke(new BasicStroke((float)1.0));
			g2.setColor(getColour(links[i].DescTerm));
			}
			
			if(links[i].theLink!=null){
			g2.draw(links[i].theLink);
			}
		}
	}
	
	public Color getColour(String term){
		if(term.compareTo("A Road")==0){
			return Color.BLUE;
		}
		else if(term.compareTo("B Road")==0){
			return new Color(0,0,150); //DARKBLUE
		}
		else if(term.compareTo("Local Street")==0){
			return new Color(150,150,150); //GREY
		}
		else if(term.compareTo("Minor Road")==0){
			return Color.BLACK;
		}
		else{
			return new Color(176,100,100); //MAROON(ISH)
		}
	}

	public void zoomTo(double input){
		zoom = input;
		
		double[][] trans = new double[3][3];
		//move centre to origin
		trans[0][0]=1.0;
		trans[0][1]=0.0;
		trans[0][2]=-440.0;
		trans[1][0]=0.0;
		trans[1][1]=1.0;
		trans[1][2]=-300.0;
		trans[2][0]=0.0;
		trans[2][1]=0.0;
		trans[2][2]=1.0;
		translationMult(trans);
		//zoom
		trans[0][0]=zoom;
		trans[0][1]=0.0;
		trans[0][2]=0.0;
		trans[1][0]=0.0;
		trans[1][1]=zoom;
		trans[1][2]=0.0;
		trans[2][0]=0.0;
		trans[2][1]=0.0;
		trans[2][2]=1.0;
		translationMult(trans);
		//move origin back to centre
		trans[0][0]=1.0;
		trans[0][1]=0.0;
		trans[0][2]=440.0;
		trans[1][0]=0.0;
		trans[1][1]=1.0;
		trans[1][2]=300.0;
		trans[2][0]=0.0;
		trans[2][1]=0.0;
		trans[2][2]=1.0;
		translationMult(trans);
		
		notZoomed = false;
		
		for(int i=0;i<12090;i++){
			if(i<9450){
				nodes[i].theNode.setRect(
					((nodes[i].theNode.getX()-440)*zoom)+440,
					((nodes[i].theNode.getY()-300)*zoom)+300,
					nodes[i].theNode.getWidth(),
					nodes[i].theNode.getHeight()
					);
			}
			if(links[i].theLink!=null){
			links[i].theLink.setLine(
				((links[i].theLink.getX1()-440)*zoom)+440,
				((links[i].theLink.getY1()-300)*zoom)+300,
				((links[i].theLink.getX2()-440)*zoom)+440,
				((links[i].theLink.getY2()-300)*zoom)+300
				);
			
			}
		}
		drawLinks();
		for(int j=0;j<logPoints.size();j++){
			Rectangle2DFloat[] thislogPoints = (Rectangle2DFloat[]) logPoints.elementAt(j);
			Line2DFloat[] thislogLines = (Line2DFloat[]) logLines.elementAt(j);
			for(int i=0;i<thislogPoints.length;i++){
				thislogPoints[i].setRect(
					((thislogPoints[i].getX()-440)*zoom)+440,	
					((thislogPoints[i].getY()-300)*zoom)+300,	
					thislogPoints[i].getWidth(),	
					thislogPoints[i].getHeight()	
				);
				if(i<thislogLines.length){
					thislogLines[i].setLine(
						((thislogLines[i].getX1()-440)*zoom)+440,
						((thislogLines[i].getY1()-300)*zoom)+300,
						((thislogLines[i].getX2()-440)*zoom)+440,
						((thislogLines[i].getY2()-300)*zoom)+300
					);	
				}
			}
		}
		/*for(int j=0;j<logRoads.size();j++){
			Line2DFloat thisLine = (Line2DFloat) logRoads.elementAt(j);
			thisLine.setLine(
						((thisLine.getX1()-440)*zoom)+440,
						((thisLine.getY1()-300)*zoom)+300,
						((thisLine.getX2()-440)*zoom)+440,
						((thisLine.getY2()-300)*zoom)+300
					);
		}*/
		drawLog();
	}
	
	public void moveTo(int x, int y){
		double[][] trans = new double[3][3];
		//move
		trans[0][0]=1.0;
		trans[0][1]=0.0;
		trans[0][2]=(double)x;
		trans[1][0]=0.0;
		trans[1][1]=1.0;
		trans[1][2]=(double)y;
		trans[2][0]=0.0;
		trans[2][1]=0.0;
		trans[2][2]=1.0;
		translationMult(trans);
		
		notZoomed = false;
		for(int i=0;i<12090;i++){
			if(i<9450){
				nodes[i].theNode.setRect(
					nodes[i].theNode.getX()+x,
					nodes[i].theNode.getY()+y,
					nodes[i].theNode.getWidth(),
					nodes[i].theNode.getHeight()
					);
			}
			if(links[i].theLink!=null){
			links[i].theLink.setLine(
				links[i].theLink.getX1()+x,
				links[i].theLink.getY1()+y,
				links[i].theLink.getX2()+x,
				links[i].theLink.getY2()+y
				);
			
			}
		}
		drawLinks();
		for(int j=0;j<logPoints.size();j++){
			Rectangle2DFloat[] thislogPoints = (Rectangle2DFloat[]) logPoints.elementAt(j);
			Line2DFloat[] thislogLines = (Line2DFloat[]) logLines.elementAt(j);
			for(int i=0;i<thislogPoints.length;i++){
				thislogPoints[i].setRect(
					thislogPoints[i].getX()+x,	
					thislogPoints[i].getY()+y,	
					thislogPoints[i].getWidth(),	
					thislogPoints[i].getHeight()	
				);
				if(i<thislogLines.length){
					thislogLines[i].setLine(
						thislogLines[i].getX1()+x,
						thislogLines[i].getY1()+y,
						thislogLines[i].getX2()+x,
						thislogLines[i].getY2()+y
					);	
				}
			}
		}
		/*for(int j=0;j<logRoads.size();j++){
			Line2DFloat thisLine = (Line2DFloat) logRoads.elementAt(j);
			thisLine.setLine(
						thisLine.getX1()+x,
						thisLine.getY1()+y,
						thisLine.getX2()+x,
						thisLine.getY2()+y
					);
		}*/
		drawLog();
	}
	
	public void calculateRoutes(){
		long startTime = System.currentTimeMillis();
		int number2 = 0;
		for(int i=0000;i<9450;i++){
			long time = System.currentTimeMillis();
			int number = 0;
			try{
				
				//temp load
				FileInputStream fis = new FileInputStream("c:\\nodes\\node"+i+".node");
				System.out.println("Loading "+i+" from Temp");
				ObjectInputStream is = new ObjectInputStream(fis);
				short predecessor[] = (short[]) is.readObject();
				is.close();
				for(int j=0;j<9450;j++){
				nodes[j].nextNode[i]=predecessor[j];
				}
			}catch(Exception fileNotfound){
			System.out.print("Calculating Routes for nodes["+i+"]");
			boolean routeFoundTo[] = new boolean[9450];
			double distanceTo[] = new double[9450];
			// S = all true entries, V-S = all false entries
			Vector routeTo[] = new Vector[9450];
			short predecessor[] = new short[9450];
			//initialise S to empty! and distances to max!
			for(int j=0;j<9450;j++){
				routeFoundTo[j]=false;
				distanceTo[j]=Double.MAX_VALUE;
				predecessor[j]=-1;
				}
			//routeFoundTo[i]=true;
			distanceTo[i]=0.0;
			boolean vs = getVS(routeFoundTo);
			while(vs){
				int minPos = -1;
				double minVal = Double.MAX_VALUE;
				for(int j=0;j<9450;j++){
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
				
				for(int j=0;j<nodes[minPos].links.size();j++){
					Integer index = (Integer) nodes[minPos].links.elementAt(j);
					links[index].onRoute = true;
					Long n1 = links[index].node1;
					Long n2 = links[index].node2;
					double length = links[index].length;
					Long nodeTo;
					if(n1.longValue()==nodes[minPos].node.longValue()){
						nodeTo = n2;
					}else{
						nodeTo = n1;
					}
					int ind = -1;
					for(int k=0;k<9450;k++){
						if(nodes[k].node.longValue()==nodeTo.longValue()){
							ind = k;
						}
					}
					if(ind==-1){
						break;
					}
					if((distanceTo[minPos]+length<distanceTo[ind])){
						distanceTo[ind]=distanceTo[minPos]+length;
						
						predecessor[ind]=(short)minPos;
						nodes[ind].nextNode[i]=(short)minPos;	
							
					}	
				}
				
				vs = getVS(routeFoundTo);
				number = getNumber(routeFoundTo);
				if(number%945==0){
					System.out.print(".");
				}	
			
			}	
			
			number2 = number2 + number;
			System.out.println();
			System.out.println("  "+ number +" routes for nodes["+i+"] found! in "+(System.currentTimeMillis()-time)+"ms");
			System.out.println("  "+ number2 +" routes for nodes[0 to "+ i +"] found! in "+(System.currentTimeMillis()-startTime)+"ms");
			
			try{
				System.out.println("Temp Save");
				FileOutputStream fos = new FileOutputStream("c:\\nodes\\node"+i+".node");
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(predecessor);
				os.close();
			}catch(Exception e){
				System.out.println("Error on "+i+":"+e);
			}
		}}
	}

	public boolean getVS(boolean[] routeFoundTo){
		for(int i=0;i<9450;i++){
		if(routeFoundTo[i]==false){
			return true;
		}
		}	
		return false;
	}
	
	public int getNumber(boolean[] routeFoundTo){
		int j=0;
		for(int i=0;i<9450;i++){
		if(routeFoundTo[i]==true){
			j++;
		}
		}	
		return j;
	}
	
	public void addLog(Vector input){
		drawLinks();
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		Rectangle2DFloat[] newlogPoints = new Rectangle2DFloat[input.size()];
		Line2DFloat[] newlogLines = new Line2DFloat[input.size()-1];
	
		for(int i=0;i<input.size();i++){
			double[] point = (double[]) input.elementAt(i);
			double y = 600-(((((point[0]+272629775)*4)-1091800300)-52614)*(600/41510.0));
			double x = (((((point[1]+272629775)*4)-1092299700)-15636)*(600/41312.0))+140;
			
			x=(translation[0][0]*x)+(translation[0][1]*y)+(translation[0][2]*1.0);
			y=(translation[1][0]*x)+(translation[1][1]*y)+(translation[1][2]*1.0);
			
			
				
			newlogPoints[i]=new Rectangle2DFloat((float)x,(float)y,(float)1,(float)1);
			g2.setColor(Color.YELLOW);
			g2.setStroke(new BasicStroke((float)1.0));
			g2.fill(newlogPoints[i]);
			
			if(i>0){
				newlogLines[i-1]=new Line2DFloat(newlogPoints[i-1].x,newlogPoints[i-1].y,(float)x,(float)y);
				g2.setColor(Color.YELLOW);
				g2.setStroke(new BasicStroke((float)1.0));
				g2.draw(newlogLines[i-1]);
			}
		}
		logPoints.add(newlogPoints);
		logLines.add(newlogLines);
		if(nr){
			getNearestRoads(newlogPoints);
		}
		drawLog();
	}
	
	public void getNearestRoads(Rectangle2DFloat[] newlogPoints){
		Graphics g = (Graphics) this.getGraphics();
		int[] nearest = new int[newlogPoints.length];
		int percent = newlogPoints.length/100;
		for(int j=0;j<newlogPoints.length;j++){
			if(j%percent==0){
				g.setColor(Color.WHITE);
				g.fillRect(50,50,50,50);
				g.setColor(Color.RED);
				g.drawString(((j/percent)+"%"),55,80);	
			}
			double distanceToNearest = Double.MAX_VALUE;
			for(int i=0;i<links.length;i++){
				if(links[i].theLink!=null){
					double dist = links[i].theLink.ptSegDist(newlogPoints[j].getX(),newlogPoints[j].getY());
					if(dist<distanceToNearest){
						distanceToNearest = dist;	
						nearest[j] = i;
					}
				}
			}
		}
		int small = Integer.MAX_VALUE;
		int big = Integer.MIN_VALUE;
		for(int i=0;i<nearest.length;i++){
			if(nearest[i]<small){
				small = nearest[i];
			}
			if(nearest[i]>big){
				big = nearest[i];
			}	
		}
		int number = big - small;
		//System.out.println(number + " = " + big + " - " + small);
		int[] counts = new int[number+1];
		for(int i=0;i<(number+1);i++){
			counts[i]=0;
		}
		for(int i=0;i<nearest.length;i++){
			//System.out.println((nearest[i]-small));
			counts[(nearest[i]-small)]++;
		}
		for(int i=0;i<(number+1);i++){
			if(counts[i]>10){
				//System.out.println(i);
				logRoads.add(new Integer(i+small));
			}
		}
	}
	
	
	public void drawLog(){
		Graphics g = this.getGraphics();
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.YELLOW);
		for(int j=0;j<logPoints.size();j++){
			Rectangle2DFloat[] thislogPoints = (Rectangle2DFloat[]) logPoints.elementAt(j);
			Line2DFloat[] thislogLines = (Line2DFloat[]) logLines.elementAt(j);
			
			for(int i=0;i<thislogPoints.length;i++){
				g2.setStroke(new BasicStroke((float)1.0));
				g2.fill(thislogPoints[i]);
				if(i<thislogLines.length){
					g2.setStroke(new BasicStroke((float)1.0));
					g2.draw(thislogLines[i]);
				}
			}
		}
		for(int i=0;i<logRoads.size();i++){
			Integer temp = (Integer) logRoads.elementAt(i);
			g2.setStroke(new BasicStroke((float)3.0));
			g2.setColor(Color.RED);
			g2.draw(links[temp.intValue()].theLink);
		}
	}
	
	public void removeLogs(){
		logPoints.removeAllElements();
		logLines.removeAllElements();
		logRoads.removeAllElements();
		drawLinks();
	}
	
	public void translationMult(double[][] newTrans){
		//structure of matrix:
		//	00 01 02
		//	10 11 12
		// 	20 21 22
		
		double j = translation[0][0];	double a = newTrans[0][0];
		double k = translation[0][1];	double b = newTrans[0][1];
		double l = translation[0][2];	double c = newTrans[0][2];
		double m = translation[1][0];	double d = newTrans[1][0];
		double n = translation[1][1];	double e = newTrans[1][1];
		double o = translation[1][2];	double f = newTrans[1][2];
		double p = translation[2][0];	double g = newTrans[2][0];
		double q = translation[2][1];	double h = newTrans[2][1];
		double r = translation[2][2];	double i = newTrans[2][2];
		
		//  a b c 	j k l	aj+bm+cp ak+bn+cq al+bo+cr
		//  d e f * m n o =	dj+em+fp dk+en+fq dl+eo+fr
		//  g h i	p q r	gj+hm+ip gk+hn+iq gl+ho+ir
		
		translation[0][0]=(a*j)+(b*m)+(c*p);
		translation[0][1]=(a*k)+(b*n)+(c*q);
		translation[0][2]=(a*l)+(b*o)+(c*r);
		
		translation[1][0]=(d*j)+(e*m)+(f*p);
		translation[1][1]=(d*k)+(e*n)+(f*q);
		translation[1][2]=(d*l)+(e*o)+(f*r);
		
		translation[2][0]=(g*j)+(h*m)+(i*p);
		translation[2][1]=(g*k)+(h*n)+(i*q);
		translation[2][2]=(g*l)+(h*o)+(i*r);
	
		//System.out.println("["+translation[0][0]+","+translation[0][1]+","+translation[0][2]+"]");
		//System.out.println("["+translation[1][0]+","+translation[1][1]+","+translation[1][2]+"]");
		//System.out.println("["+translation[2][0]+","+translation[2][1]+","+translation[2][2]+"]");
		//System.out.println();
	
	}
	
	public void changePos(double oldX,double oldY,double newX,double newY){
		double theX = (translation[0][0]*newX)+(translation[0][1]*newY)+(translation[0][2]*1.0);
		double theY = (translation[1][0]*newX)+(translation[1][1]*newY)+(translation[1][2]*1.0);
		double theoX = (translation[0][0]*oldX)+(translation[0][1]*oldY)+(translation[0][2]*1.0);
		double theoY = (translation[1][0]*oldX)+(translation[1][1]*oldY)+(translation[1][2]*1.0);
		
		oldPos = new Rectangle2DFloat((float)theoX,(float)theoY,(float)3,(float)3);;
		newPos = new Rectangle2DFloat((float)theX,(float)theY,(float)3,(float)3);
		
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fill(oldPos);
		g2.setColor(Color.YELLOW);
		g2.fill(newPos);
	}
	

}