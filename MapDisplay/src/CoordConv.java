//Alexander S. Green 01/07/05
//Implementation of Converting WGS84 lattitude and longitude from a GPS unit
// to OSGB36 eastings and northings

import java.util.*;
import java.lang.*;
import java.io.*;

public class CoordConv {
	
	//GRS80 (WGS84) Ellipsoidal constants
	// semi-major axis (m)
	double a = 6378137.000; 
	// smei-minor axis (m)
	double b = 6356752.3141;
	//ecentricity squared
	double eSquared; // initialised on construction
	
	//National Grid Projection Constants
	//Scale Factor on Central Meridian
	double Fzero = 0.9996012717;
	//true origin lat,lon
	double OmegaZero = 49;  //49 degrees north (in degrees) 	N+
	double LamdaZero = -2;  //2 degrees west (in degrees) 	E+
	//map co-ordinates of true origin (m)
	double Ezero = 400000;
	double Nzero = -100000;
	
	//File Input used to get Shift
	boolean fileExists;
	double[][] shifts;
	
	public CoordConv(){
		OmegaZero = degRad(OmegaZero); //changing to radians
		LamdaZero = degRad(LamdaZero); //changing to radians
		eSquared = ((Math.pow(a,2.0))-(Math.pow(b,2.0)))/(Math.pow(a,2.0)); // initialising eSquared
		//System.out.println("e2="+eSquared);
		try{
			System.out.print("Loading Shifts");
			FileInputStream fis = new FileInputStream("C:\\OSconv\\OSTN02_OSGM02_GB.txte");
			InputStreamReader inR = new InputStreamReader(fis);
			BufferedReader in = new BufferedReader(inR);
			String input = in.readLine();
			shifts = new double[876951][7];
			int count = 0;
			while(input != null){
				StringTokenizer tokens = new StringTokenizer(input,",",false);
				//if(tokens.countTokens()!=7){
				//	System.out.println(input);
				//}
				if(count%16000==0){
					System.out.print(".");
				}
				shifts[count][0] = (new Double(tokens.nextToken())).doubleValue();
				shifts[count][1] = (new Double(tokens.nextToken())).doubleValue();
				shifts[count][2] = (new Double(tokens.nextToken())).doubleValue();
				shifts[count][3] = (new Double(tokens.nextToken())).doubleValue();
				shifts[count][4] = (new Double(tokens.nextToken())).doubleValue();
				shifts[count][5] = (new Double(tokens.nextToken())).doubleValue();
				shifts[count][6] = (new Double(tokens.nextToken())).doubleValue();
				count++;
				input = in.readLine();
			}
			fileExists = true;
			System.out.println();
		}catch(Exception e){
			fileExists = false;
			System.out.println();
			System.out.println("Shift File Error, no shift will be added");
			System.out.println(e);
		}
	}	 
	
	//for converting degrees to radians
	public double degRad(double deg){
		double rad = deg * (Math.PI/180.0);
		return rad;
	}
	
	//converts the NMEA standard degrees and decimal minutes to decimal degrees
	public double degminDeg(double degMin){
		String temp = Double.toString(degMin);
		int dotPos = temp.indexOf('.');
		
		String deg = temp.substring(0,dotPos-2);
		String min = temp.substring(dotPos-2,temp.length());
		
		double Deg = (new Double(deg)).doubleValue();
		double Min = (new Double(min)).doubleValue();
		
		if(Deg>=0){
		Deg = Deg + (Min/60.0);
		}else{
		Deg = Deg - (Min/60.0);	
		}
		
		//System.out.println(deg+" "+min+" "+Deg);
		
		return Deg;
	}
	
	//actual conversion method
	public double[] convert(double[] lonLat){
		//initialise output
		double[] northEast = new double[2];
		
		//get the input and convert to radians
		double Lamda = lonLat[1];
		double Omega = lonLat[0];
		Lamda = degminDeg(Lamda);	
		Omega = degminDeg(Omega);
		Lamda = degRad(Lamda);
		Omega = degRad(Omega);
		
		//stages of transformtaion
		double n = (a-b)/(a+b);
			//System.out.println("n="+n);
		double v = a*Fzero*Math.pow((1-((eSquared)*(Math.pow(Math.sin(Omega),2.0)))),-0.5); 
			//System.out.println("v="+v);
		double roe = a*Fzero*(1-eSquared)*Math.pow((1-((eSquared)*(Math.pow(Math.sin(Omega),2.0)))),-1.5);
			//System.out.println("roe="+roe);
		double nuSquared = (v/roe)-1;
			//System.out.println("nu2="+nuSquared);
		double M = b * Fzero * (
				((1+n+((5/4)*Math.pow(n,2.0))+((5/4)*Math.pow(n,3.0)))*(Omega-OmegaZero))
			   -(((3*n)+(3*(Math.pow((n),2.0)))+((21/8)*(Math.pow(n,3.0))))*(Math.sin(Omega-OmegaZero))*(Math.cos(Omega+OmegaZero)))
			   +((((15/8)*(Math.pow(n,2.0)))+((15/8)*(Math.pow(n,3.0))))*(Math.sin(2*(Omega-OmegaZero)))*(Math.cos(2*(Omega+OmegaZero))))
			   -((35/24)*(Math.pow(n,3.0))*(Math.sin(3*(Omega-OmegaZero)))*(Math.cos(3*(Omega+OmegaZero))))
			);
			//System.out.println("M="+M);
		double I = M + Nzero;
			//System.out.println("I="+I);
		double II = (v/2)*(Math.sin(Omega))*(Math.cos(Omega));
			//System.out.println("II="+II);
		double III = (v/24)*(Math.sin(Omega))*(Math.pow((Math.cos(Omega)),3.0))*(5-(Math.pow((Math.tan(Omega)),2.0))+(9*nuSquared));
			//System.out.println("III="+III);
		double IIIA =(v/720)*(Math.sin(Omega))*(Math.pow((Math.cos(Omega)),5.0))*(61-(58*(Math.pow((Math.tan(Omega)),2.0)))+(Math.pow((Math.tan(Omega)),4.0)));
			//System.out.println("IIIA="+IIIA);
		double IV = v * Math.cos(Omega);
			//System.out.println("IV="+IV);
		double V = (v/6)*(Math.pow((Math.cos(Omega)),3.0))*((v/roe)-(Math.pow((Math.tan(Omega)),2.0)));
			//System.out.println("V="+V);
		double VI = (v/120)*(Math.pow((Math.cos(Omega)),5.0))*(5-(18*(Math.pow(Math.tan(Omega),2.0)))+(Math.pow((Math.tan(Omega)),4.0))+(14*nuSquared)-(58*(Math.pow((Math.tan(Omega)),2.0))*nuSquared));
			//System.out.println("VI="+VI);
			
		//the results...
		double N = I + (II*(Math.pow((Lamda-LamdaZero),2.0))) + (III*(Math.pow((Lamda-LamdaZero),4.0))) + (IIIA*(Math.pow((Lamda-LamdaZero),6.0)));	
			//System.out.println("N="+N);
		double E = Ezero + (IV*(Lamda-LamdaZero)) + (V*(Math.pow((Lamda-LamdaZero),3.0))) + (VI*(Math.pow((Lamda-LamdaZero),5.0)));
			//System.out.println("E="+E);
			
		northEast[1]=E;
		northEast[0]=N;
		
		
		if(fileExists){
			northEast = shiftOSGB36(northEast);
		}
		
		return northEast;
	}

	public double[] shiftOSGB36(double[] ETRS89){
		//typical record:
		//  220065 ,651000,313000,102.775,-78.244,44.252,1
		//record_no,  x0  ,  yo  , se    ,   sn  , sg   , ?
		double[] OSGB36 = new double[2];
		
		int east_index = (int) (ETRS89[1]/1000.0);
		int north_index = (int) (ETRS89[0]/1000.0);
		
		int record_number0 = (east_index) + ((north_index)*701) + 1;
		double[] zero = getRecord(record_number0);
		int record_number1 = (east_index+1) + ((north_index)*701) + 1;
		double[] one = getRecord(record_number1);
		int record_number2 = (east_index+1) + ((north_index+1)*701) + 1;
		double[] two = getRecord(record_number2);
		int record_number3 = (east_index) + ((north_index+1)*701) + 1;
		double[] three = getRecord(record_number3);
		
		double x0 = zero[1];
		double y0 = zero[2];
		
		double se0 =zero[3];
		double se1 =one[3];
		double se2 =two[3];
		double se3 =three[3];
		
		double sn0 =zero[4];
		double sn1 =one[4];
		double sn2 =two[4];
		double sn3 =three[4];
		
		
		double dx = ETRS89[1] - x0;
		double dy = ETRS89[0] - y0;
		
		double t = dx/1000.0;
		double u = dy/1000.0;
		
		double se = ((1-t)*(1-u)*(se0))+((t)*(1-u)*(se1))+((t)*(t)*(se2))+((1-t)*(u)*(se3));
		double sn = ((1-t)*(1-u)*(sn0))+((t)*(1-u)*(sn1))+((t)*(t)*(sn2))+((1-t)*(u)*(sn3));
		
		
		OSGB36[0]=ETRS89[0] + sn;
		OSGB36[1]=ETRS89[1] + se;
		return OSGB36;	
	}
	
	double[] getRecord(int index){
		return shifts[index];
	}
}