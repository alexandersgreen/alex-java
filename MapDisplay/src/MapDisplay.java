import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MapDisplay extends JFrame{
    
    Vector NodeVector;
    Vector LinkVector;
    
    public static void main(String[] args) {
		MapDisplay mainFrame = new MapDisplay();
	}
    
    public MapDisplay(){
    	try{
    		NodeVector = new Vector(9450);
    		LinkVector = new Vector(12090);
    		
    		/*initFromDB("C:\\Nottingham Road Network.mdb");	
    	
    		node nodes[] = new node[9450];
    		link links[] = new link[12090];
    		int[][] nodePos = new int[9450][3];
    		
    		for(int i=0;i<9450;i++){
    			nodes[i] = (node) NodeVector.elementAt(i);	
    		}
    		for(int i=0;i<12090;i++){
    			links[i] = (link) LinkVector.elementAt(i);
    		}*/
    		
    		
    		this.setTitle("Map Display");
			this.setSize(new Dimension(906,670));
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
			this.getContentPane().setLayout(new BorderLayout());
    		
    		MapDisplayCanvas canvas = new MapDisplayCanvas("c:\\NottinghamRoads.rfi");	
    		//MapDisplayCanvas canvas = new MapDisplayCanvas(links,nodes);
    		
    		MapDisplayButtons buttons = new MapDisplayButtons(canvas);
    		this.getContentPane().add(buttons,BorderLayout.NORTH);
    		this.getContentPane().add(canvas,BorderLayout.CENTER);
    	
    		this.setVisible(true);
    		this.toFront();
    	}catch(Exception e){
    		System.out.println("Error: " + e.getMessage());
    		System.exit(-1);
    	}
    }
    
    public void initFromDB(String databaseFile) throws Exception{
    	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" 
    	                   + databaseFile.trim() + 
    	                  ";DriverID=22;READONLY=true}";
    	Connection con = DriverManager.getConnection( database ,"","");
    	Statement s1 = con.createStatement();
    	s1.execute("SELECT * FROM OSRdNd");
    	ResultSet nodes = s1.getResultSet();
    	Statement s2 = con.createStatement();
    	s2.execute("SELECT * FROM OSRdLk");
    	ResultSet links = s2.getResultSet();
    	createResults(nodes,links);
    	s1.close();
    	s2.close();
		con.close(); 
    }
    
    public void createResults(ResultSet nodes, ResultSet links) throws Exception{
    	if(nodes != null){
			System.out.println("Loading Nodes...");
    		int i=0;
    		
    		while ( nodes.next() ){
    			i++;
    			InputStream shape = nodes.getBinaryStream(1);
    			int OID = nodes.getInt(2);
    			String sTOID = nodes.getString(3);
    			//int Version = nodes.getInt(4);
    			//String VerDate = nodes.getString(5);
    			//String Change = nodes.getString(6);
    			//String DescGroup = nodes.getString(7);
    			//String TopoArea = nodes.getString(8);
    			//String Theme = nodes.getString(9);
				Long TOID = new Long(sTOID);
				
			
				byte data[] = new byte[20];
				byte theData[] = new byte[20];
			
				shape.read(data);
				for(int j =0; j<5;j++){
					for(int k=0;k<4;k++){
						theData[(j*4)+(3-k)]=data[(j*4)+k];
					}	
				}
				ByteArrayInputStream byteStream = new ByteArrayInputStream(theData);
				DataInputStream bytes = new DataInputStream(byteStream);
				int ints[] = new int[5];
				for(int k=0;k<5;k++){
					ints[k] = (bytes.readInt());
				}
				
				node thisNode = new node(TOID,OID,(ints[2]-1092300000),(ints[4]-1091800000));
				NodeVector.add(thisNode);
			}
			System.out.println(i+" Nodes Loaded");
        }
        
        
        if(links != null){	
        	System.out.println("Loading Links...");
        	int i=0;
        	
        	while ( links.next() ){
    		i++;
    		//long OID = links.getLong(1);
    		//String shape = links.getString(2);
    		//String TOID = links.getString(3);
    		//int Version = links.getInt(4);
    		//String VerDate = links.getString(5);
    		//String Change = links.getString(6);
    		//String DescGroup = links.getString(7);
    		String DescTerm = links.getString(8);
    		//String Nature = links.getString(9);
    		double LnkLength = links.getDouble(10);
    		String sNode1 = links.getString(11);
    		//String Node1GradeOrientation = links.getString(12);
    		//String Node1GradeSeperation = links.getString(13);
    		String sNode2 = links.getString(14);
    		//String Node2GradeOrientation = links.getString(15);
    		//String Node2GradeSeperation = links.getString(16);
    		//String Theme = links.getString(17);
    		//double shape_Length = links.getDouble(18);
    		Long Node1 = new Long(sNode1);
    		Long Node2 = new Long(sNode2);
    		
    		link thisLink = new link(Node1,Node2,LnkLength,DescTerm);
    		LinkVector.add(thisLink);
    		}
        	
        	System.out.println(i+" Links Loaded");
    	}
    }
    
}
