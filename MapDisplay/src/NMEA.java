import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.comm.*;
import java.util.*;
import java.io.*;

public class NMEA extends JFrame implements SerialPortEventListener,WindowListener,ActionListener{
	
	SerialPort serialPort1;
	InputStream in1;
	InputStreamReader is1;
	OutputStream out1;
	CommPortIdentifier[] ports;
	String[] portName;
	JComboBox choosePort;
	JButton open;
	static JTextField currentLine;
	static JTextField lat;
	static JTextField lon;
	static JTextField alt;
	static JTextField sat;
	static JTextField time;
	
	boolean notopen;
	
	double latVal;
	double lonVal;
	double oldLat;
	double oldLon;
	double newX;
	double newY;
	
	int satVal;
	
	MapDisplayCanvas canvas;
	CoordConv coconv;
	
	public NMEA(MapDisplayCanvas cnvs,CoordConv cnv){
		notopen = true;
		canvas = cnvs;
		coconv = cnv;
		newX = -1;
		newY = -1;
		latVal = 0.0;
		lonVal = 0.0;
		oldLat = 0.0;
		oldLon = 0.0;
		satVal = 0;
		Enumeration portsE = CommPortIdentifier.getPortIdentifiers();
		Vector temp = new Vector(4);
		int count = 0;
		while(portsE.hasMoreElements()){
			count++;
			CommPortIdentifier cpi = (CommPortIdentifier) portsE.nextElement();
			temp.add(cpi);
		}
		//System.out.println(count+" ports found");
		ports = new CommPortIdentifier[temp.size()];
		portName = new String[temp.size()];
		for(int i=0;i<temp.size();i++){
			String tempString = new String();
			ports[i] = (CommPortIdentifier) temp.elementAt(i);
			//System.out.println(ports[i].getName());
			portName[i] = ports[i].getName();
		}

		choosePort = new JComboBox(portName);
		open = new JButton("Open");
		currentLine = new JTextField(50);
		lat = new JTextField(50);
		lon = new JTextField(50);
		alt = new JTextField(50);
		sat = new JTextField(50);
		time = new JTextField(50);
		currentLine.setEditable(false);
		lat.setEditable(false);
		lon.setEditable(false);
		alt.setEditable(false);
		sat.setEditable(false);
		time.setEditable(false);
		
		open.addActionListener(this);
		
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(choosePort);		
		this.getContentPane().add(open);
		this.getContentPane().add(currentLine);
		this.getContentPane().add(lat);
		this.getContentPane().add(lon);
		this.getContentPane().add(alt);
		this.getContentPane().add(sat);
		this.getContentPane().add(time);
		
		this.setSize(new Dimension(600,215));
		this.addWindowListener(this);
		this.setVisible(true);		

	}
	
	public void openPort(int port){
		try{
			serialPort1 = (SerialPort) ports[port].open("NMEA",1000);
			serialPort1.setSerialPortParams(4800,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			
			serialPort1.notifyOnDataAvailable(true);
			System.out.println(serialPort1.getName()+" opened");
			in1 = serialPort1.getInputStream();
			is1 = new InputStreamReader(in1,"cp1252");
			out1 = serialPort1.getOutputStream();
			serialPort1.addEventListener(this);
		}catch(Exception e){
			JOptionPane.showMessageDialog(this, "Can't Open "+ports[port].getName()+" as a Serial port");
		}
	}

	
	public void parseLine(String line){
		if(line.charAt(0)!='$'){
			System.out.println("Invalid NMEA Sentence");
		}else{
			Vector parts = new Vector(20);
			//System.out.println(line);
			currentLine.setText(line);
			StringTokenizer tokenizer = new StringTokenizer(line,",",true);
			while(tokenizer.hasMoreElements()){
				String part = tokenizer.nextToken();
				parts.add(part);
			}
			String type = (String) parts.elementAt(0);
			String tempLast = (String) parts.elementAt(parts.size()-1);
			if(tempLast.compareTo(",")==0){
				parts.add("null");
			}
			for(int i=1;i<parts.size();i++){
				String one = (String) parts.elementAt(i-1);
				String two = (String) parts.elementAt(i);
				if(one.compareTo(",")==0&two.compareTo(",")==0){
					parts.insertElementAt("null",i);
				}		
			}
			//System.out.println(line);
			if(type.compareTo("$GPGGA")==0){
				// Global Positioning System Fix Data
				// id , time , lat , dir , lon , dir , qual , num_sats , rel_acc_horiz , alt , mes , geoid , mes , irrel , Checksum
 				if(parts.size()!=29){
 					System.out.println("Invalid $GPGGA sentence");
 				}else{
 					lat.setText("Latitude: "+parts.elementAt(4)+" "+parts.elementAt(6));		
 					oldLat = latVal;
 					latVal = getLat((String)parts.elementAt(4),(String)parts.elementAt(6));
 					lon.setText("Longitude: "+parts.elementAt(8)+" "+parts.elementAt(10));
 					oldLon = lonVal;
 					lonVal = getLon((String)parts.elementAt(8),(String)parts.elementAt(10));
 					alt.setText("Altitude: "+parts.elementAt(18)+" "+parts.elementAt(20));
 					//altVal
 					sat.setText("Number of Satellites: "+parts.elementAt(14));
 					satVal = (new Integer((String) parts.elementAt(14))).intValue();
					time.setText("Time: "+parts.elementAt(2));
				}
					
			}else if(type.compareTo("$GPGSA")==0){
				// GPS DOP and active satellites
				// id , mode , act_mode , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , 11 , 12 , 13 , 14 , PDOP , HDOP , VDOP
			 	if(parts.size()!=35){
 					System.out.println("Invalid $GPGSA sentence");
 				}else{
 					
 		
				}
			}else if(type.compareTo("$GPRMC")==0){
				// Recommended minimum specific GPS/Transit data
				// id , time , data_status , lat , dir , lon , dir , speed_knots , course , date , var , checksum
			 	if(parts.size()!=23){
 					if(parts.size()!=25){
 						System.out.println("Invalid $GPRMC sentence");
 						System.out.println(parts.size());
 					}else{ 					
 						lat.setText("Latitude: "+parts.elementAt(6)+" "+parts.elementAt(8));		
 						oldLat = latVal;
 						latVal = getLat((String)parts.elementAt(6),(String)parts.elementAt(8));
 						lon.setText("Longitude: "+parts.elementAt(10)+" "+parts.elementAt(12));
 						oldLon = lonVal;
 						lonVal = getLon((String)parts.elementAt(10),(String)parts.elementAt(12));
						time.setText("Time: "+parts.elementAt(2));
 					}
 				}else{
 					lat.setText("Latitude: "+parts.elementAt(6)+" "+parts.elementAt(8));		
 					oldLat = latVal;
 					latVal = getLat((String)parts.elementAt(6),(String)parts.elementAt(8));
 					lon.setText("Longitude: "+parts.elementAt(10)+" "+parts.elementAt(12));
 					oldLon = lonVal;
 					lonVal = getLon((String)parts.elementAt(10),(String)parts.elementAt(12));
					time.setText("Time: "+parts.elementAt(2));
				}	
			}else if(type.compareTo("$GPGSV")==0){
				// GPS satellites in view
				// id , num_of , num , total_in_view , PRN , Elev , Az , SNR , PRN , Elev , Az , SNR ,PRN , Elev , Az , SNR ,PRN , Elev , Az , SNR , checksum   
			 	if(parts.size()!=15){
 					System.out.println("Invalid $GPRMC sentence");
 				}else{
 					
 		
				}				
			}else if(type.compareTo("$GPVTG")==0){
				if(parts.size()!=19){
 					System.out.println("Invalid $GPVTG sentence");
 					System.out.println(parts.size());
 				}else{
 					
 		
				}				
			}else{
				System.out.println(type+" sentences not supported");	
			}
			this.setTitle(latVal+","+lonVal+" from "+satVal+" satellites");
		}
	}
	
	public void serialEvent(SerialPortEvent e){
		try{
			String theLine = "";
			char theChar = (char) is1.read();
			while(theChar!='\n'){
				theLine = theLine+theChar;
				theChar = (char) is1.read();
			}
			parseLine(theLine);
			double oldX = newX;
			double oldY = newY;
			double[] orig = new double[2];
			orig[0]=latVal;
			orig[1]=lonVal;
			if(latVal!=0||lonVal!=0){
				orig = coconv.convert(orig);
				newY = 600-(((((orig[0]+272629775)*4)-1091800300)-52614)*(600/41510.0));
				newX = (((((orig[1]+272629775)*4)-1092299700)-15636)*(600/41312.0))+140;
				canvas.changePos(oldX,oldY,newX,newY);
			}else{
				oldX = 70.0;
				oldY = 70.0;
				canvas.changePos(70.0,70.0,70.0,70.0);
			}
		}catch(Exception ex){
			System.out.println(e.getEventType());
			System.out.println(ex.getMessage());	
		}
	}
	
	public void windowActivated(WindowEvent e){} 

	public void windowClosed(WindowEvent e){} 
          
	public void windowClosing(WindowEvent e){
		try{
			serialPort1.close();
			System.out.println(serialPort1.getName()+" closed");
		}catch(Exception ex){
		}
		this.dispose();
	} 
          
	public void windowDeactivated(WindowEvent e){} 
          
	public void windowDeiconified(WindowEvent e){} 
	
	public void windowIconified(WindowEvent e){} 
	
	public void windowOpened(WindowEvent e){}  

	public void actionPerformed(ActionEvent e){
		if(notopen){
			int thePort = choosePort.getSelectedIndex();
			if(thePort!=-1){
				try{
					serialPort1.close();
					System.out.println(serialPort1.getName()+" closed");
				}catch(Exception ex){
				}
				openPort(thePort);
				open.setText("Close");
				notopen = false;
			}
		}else{
			try{
			serialPort1.close();
			System.out.println(serialPort1.getName()+" closed");
			}catch(Exception ex){
			}
			open.setText("Open");
			notopen = true;
		}
	}
	
	public double getLat(String num, String dir){
		Double temp = new Double(num);
		if(dir.compareTo("S")==0){
			temp = temp * -1;
		}
		return temp.doubleValue();	
	}
		
	public double getLon(String num, String dir){
		Double temp = new Double(num);
		if(dir.compareTo("W")==0){
			temp = temp * -1;
		}
		return temp.doubleValue();
	}
}