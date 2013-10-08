import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class MapPlot{
	
	Vector joins;
	int count;
	Vector readings;
	Vector readingsConv;
	
	public MapPlot(File logFile,CoordConv coconv) throws Exception{

		readings = new Vector(500);
		readingsConv = new Vector(500);
		joins = new Vector(5);
		CoordConv converter = coconv;
		
		loadFile(logFile.getAbsolutePath());
		
		for(int i=0;i<readings.size();i++){
			double[] orig = (double[]) readings.elementAt(i);
			double[] conv = converter.convert(orig);	
			readingsConv.add(conv);
		}
	
	}
	
	public void loadFile(String file) throws Exception{
				FileInputStream inS = new FileInputStream(file);
				InputStreamReader inR = new InputStreamReader(inS);
				BufferedReader in = new BufferedReader(inR);
				String input = in.readLine();
				while(input != null){
					parseLine(input);
					input = in.readLine();
				}
				joins.add(new Integer(count));
				in.close();
				inR.close();
				inS.close();
	}
	
	
	public void parseLine(String line){
		if(line.length()>0){ //in case of blank line
		if(line.charAt(0)!='$'){
			System.out.println("Invalid NMEA Sentence");
		}else{
			Vector parts = new Vector(20);
			//System.out.println(line);
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
 					double latVal = getLat((String)parts.elementAt(4),(String)parts.elementAt(6));
 					double lonVal = getLon((String)parts.elementAt(8),(String)parts.elementAt(10));
 					//System.out.println(latVal+","+lonVal);
 					if(latVal!=0||lonVal!=0){
 						double[] pos = new double[3];
 						pos[0]=latVal;
 						pos[1]=lonVal;
 						pos[2]=(new Double((String) parts.elementAt(18))).doubleValue();
 						readings.add(pos);
 						count++;
 					}
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
 						//System.out.println(parts.size());
 					}else{ 					
 						double latVal = getLat((String)parts.elementAt(6),(String)parts.elementAt(8));
 						double lonVal = getLon((String)parts.elementAt(10),(String)parts.elementAt(12));
						//System.out.println(latVal+","+lonVal);
						if(latVal!=0||lonVal!=0){
 							double[] pos = new double[2];
 							pos[0]=latVal;
 							pos[1]=lonVal;
 							readings.add(pos);
 							count++;
 						}
					}
 				}else{
 					double latVal = getLat((String)parts.elementAt(6),(String)parts.elementAt(8));
 					double lonVal = getLon((String)parts.elementAt(10),(String)parts.elementAt(12));
					//System.out.println(latVal+","+lonVal);
					if(latVal!=0||lonVal!=0){
 						double[] pos = new double[2];
 						pos[0]=latVal;
 						pos[1]=lonVal;
  						readings.add(pos);
 						count++;
 					}
				}	
			}else if(type.compareTo("$GPGSV")==0){
				// GPS satellites in view
				// id , num_of , num , total_in_view , PRN , Elev , Az , SNR , PRN , Elev , Az , SNR ,PRN , Elev , Az , SNR ,PRN , Elev , Az , SNR , checksum   
			 	if(parts.size()!=39){
 					System.out.println("Invalid $GPGSV sentence");
 					//System.out.println(parts.size());
 				}else{
 					
 		
				}				
			}else if(type.compareTo("$GPVTG")==0){
				if(parts.size()!=19){
 					System.out.println("Invalid $GPVTG sentence");
 					//System.out.println(parts.size());
 				}else{
 					
 		
				}				
			}else{
				System.out.println(type+" sentences not supported");	
			}
		}
	}}
	
          

	public double getLat(String num, String dir){
		Double temp = new Double(num);
		if(dir.compareTo("S")==0){
			temp = temp * -1.0;
		}
		return temp.doubleValue();	
	}
		
	public double getLon(String num, String dir){
		Double temp = new Double(num);
		if(dir.compareTo("W")==0){
			temp = temp * -1.0;
		}
		return temp.doubleValue();
	}
	
	public Vector getVect(){
		return readingsConv;
	}
}