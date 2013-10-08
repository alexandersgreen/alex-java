package asg01u.parser;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

import asg01u.components.*;
import asg01u.components.NumExpressions.*;
import asg01u.components.BoolExpressions.*;

public class parseFile {
	
	static HashMap hm;
	
	public static Vector main(File readFile, HashMap ihm){
		hm = ihm;
		Vector output = new Vector();
		Vector input = new Vector();
		try{
		FileReader freader = new FileReader(readFile);
		BufferedReader reader = new BufferedReader(freader);
		String line = new String();
		while((line = reader.readLine()) != null){
			Vector temp = new Vector();
			line = line.trim();
			temp.add(line);
			String testline = "</" + line.substring(1);
			String line2 = new String();
			int count = 0;
			if(line.compareTo("<image>")==0){
				reader.mark((int) readFile.length());
				char[] chararray = new char[(int) readFile.length()];
				reader.read(chararray,0,(int) readFile.length());
				reader.reset();
				temp.add(chararray);
				while(((line2 = reader.readLine()).compareTo(testline)!=0)){}
			}else{
				while(((line2 = reader.readLine()).compareTo(testline)!=0)||count>0){
					if(line2.compareTo(line)==0){
						count++;
					}
					if(line2.compareTo(testline)==0){
						count--;
					}
					line2 = line2.trim();
					temp.add(line2);
				}
			}
			input.add(temp);
		}
		for(int i=0;i<input.size();i++){
			Vector temp = (Vector) input.elementAt(i);
			component tempcomponent = parseComponent(temp);
			output.add(tempcomponent);
		}
		}catch(IOException e){
			System.out.println(e.getMessage());
			Text temp = new Text("Error Loading: " + e.getMessage(),"Arial",Font.BOLD,14,Color.RED);
			output = new Vector();
			output.add(temp);
		}
		return output;
		}
		
		public static component parseComponent(Vector input){
			//System.out.println(input.size());
			try{
			if(((String) input.elementAt(0)).compareTo("<image>")==0){
				System.out.println("Image component found");
				try{
				char[] chars = (char[]) input.elementAt(1);
				String imageString = new String(chars);
				ByteArrayInputStream in = new ByteArrayInputStream(imageString.getBytes());
				ObjectInputStream oin = new ObjectInputStream(in);
				ImageIcon pic =(ImageIcon) oin.readObject();
				oin.close();
				in.close();
				return new image(pic);
				}catch(Exception e){
					//System.out.println(imagestring);
					System.out.println(e);
					return new image("asg01u/images/error.gif");
				}
			}else if(((String) input.elementAt(0)).compareTo("<Text>")==0){
				System.out.println("Text component found");
				return parseText(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Add_NumExpression>")==0){
				System.out.println("Add_NumExpression component found");
				return parseAdd_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Bracket_NumExpression>")==0){
				System.out.println("Bracket_NumExpression component found");
				return parseBracket_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Div_NumExpression>")==0){
				System.out.println("Div_NumExpression component found");
				return parseDiv_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Dot_NumExpression>")==0){
				System.out.println("Dot_NumExpression component found");
				return parseDot_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Int_NumExpression>")==0){
				System.out.println("Int_NumExpression component found");
				return parseInt_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Minus_NumExpression>")==0){
				System.out.println("Minus_NumExpression component found");
				return parseMinus_NumExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<Mult_NumExpression>")==0){
				System.out.println("Mult_NumExpression component found");
				return parseMult_NumExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<Power_NumExpression>")==0){
				System.out.println("Power_NumExpression component found");
				return parsePower_NumExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<And_BoolExpression>")==0){
				System.out.println("And_BoolExpression component found");
				return parseAnd_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Boolean_BoolExpression>")==0){
				System.out.println("Boolean_BoolExpression component found");
				return parseBoolean_BoolExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<Bracket_BoolExpression>")==0){
				System.out.println("Bracket_BoolExpression component found");
				return parseBracket_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Equals_BoolExpression>")==0){
				System.out.println("Equals_BoolExpression component found");
				return parseEquals_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<GreaterEquals_BoolExpression>")==0){
				System.out.println("GreaterEquals_BoolExpression component found");
				return parseGreaterEquals_BoolExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<Greater_BoolExpression>")==0){
				System.out.println("Greater_BoolExpression component found");
				return parseGreater_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<LessEquals_BoolExpression>")==0){
				System.out.println("LessEquals_BoolExpression component found");
				return parseLessEquals_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Less_BoolExpression>")==0){
				System.out.println("Less_BoolExpression component found");
				return parseLess_BoolExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<NotEquals_BoolExpression>")==0){
				System.out.println("NotEquals_BoolExpression component found");
				return parseNotEquals_BoolExpression(input)	;			
			}else if(((String) input.elementAt(0)).compareTo("<Not_BoolExpression>")==0){
				System.out.println("Not_BoolExpression component found");
				return parseNot_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Or_BoolExpression>")==0){
				System.out.println("Or_BoolExpression component found");
				return parseOr_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Equiv_BoolExpression>")==0){
				System.out.println("Equiv_BoolExpression component found");
				return parseEquiv_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<ImpliesR_BoolExpression>")==0){
				System.out.println("ImpliesR_BoolExpression component found");
				return parseImpliesR_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<ImpliesL_BoolExpression>")==0){
				System.out.println("ImpliesL_BoolExpression component found");
				return parseImpliesL_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Pi_NumExpression>")==0){
				System.out.println("Pi_NumExpression component found");
				return parsePi_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Declared_NumExpression>")==0){
				System.out.println("Declared_NumExpression component found");
				return parseDeclared_NumExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<Declared_BoolExpression>")==0){
				System.out.println("Declared_BoolExpression component found");
				return parseDeclared_BoolExpression(input);				
			}else if(((String) input.elementAt(0)).compareTo("<DeclareNum>")==0){
				System.out.println("DeclareNum component found");
				return parseDeclareNum(input);				
			}else if(((String) input.elementAt(0)).compareTo("<DeclareBool>")==0){
				System.out.println("DeclareBool component found");
				return parseDeclareBool(input);				
			}
			System.out.println("Invalid component found: " + (String) input.elementAt(0));
			return new Text("Invalid component found: " + (String) input.elementAt(0),"Arial",Font.BOLD,14,Color.RED);
			}catch(Exception any){
			System.out.println("Invalid or Incorrect component found: " + (String) input.elementAt(0));
			return new Text("Invalid or Incorrect component found: " + (String) input.elementAt(0),"Arial",Font.BOLD,14,Color.RED);
			}
		}
		
	public static String parseFont(Vector input){
		//System.out.println("parseFont called");
		return (String) input.elementAt(1);
		}
	
	public static int parseAttrib(Vector input){
		//System.out.println("parseAttrib called");
		Integer temp = new Integer((String) input.elementAt(1));
		return temp.intValue();
		}
	
	public static int parseSize(Vector input){
		//System.out.println("parseSize called");
		Integer temp = new Integer((String) input.elementAt(1));
		return temp.intValue();
		}
	
	public static Color parseColour(Vector input){
		//System.out.println("parseColour called");
		Integer tempR = new Integer((String) input.elementAt(2));
		Integer tempG = new Integer((String) input.elementAt(5));
		Integer tempB = new Integer((String) input.elementAt(8));
		return new Color(tempR.intValue(),tempG.intValue(),tempB.intValue());
		}
	
		public static Text parseText(Vector input){
			Vector TheText = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int txt = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int tcount=0;

			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<content>")==0){tcount++;}
					
				if(temp.compareTo("</content>")==0&&tcount==1){txt=i;}
				else if(temp.compareTo("</content>")==0){tcount--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=1;i<txt;i++){
			TheText.add(input.elementAt(i));
			}
			for(int i=txt+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			String t = "";
			for(int i=1; i < TheText.size(); i++){
				t = t + (String) TheText.elementAt(i) + "\n";
			}
			t = t.substring(0,t.length()-1);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Text(t,f,a,s,c);
		}
		
		public static Add_NumExpression parseAdd_NumExpression(Vector input){
			Vector AddExpression1 = new Vector();
			Vector AddExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<AddExpression1>")==0){e1count++;}
				if(temp.compareTo("<AddExpression2>")==0){e2count++;}

				if(temp.compareTo("</AddExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</AddExpression1>")==0){e1count--;}
				if(temp.compareTo("</AddExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</AddExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			AddExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			AddExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(AddExpression1);
			NumExpression e2 = (NumExpression) parseComponent(AddExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Add_NumExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Bracket_NumExpression parseBracket_NumExpression(Vector input){
			Vector BracketExpression1 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int ecount = 0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<BracketExpression>")==0){ecount++;}
					
				if(temp.compareTo("</BracketExpression>")==0&&ecount==1){exp1=i;}
				else if(temp.compareTo("</BracketExpression>")==0){ecount--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=2;i<exp1;i++){
			BracketExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(BracketExpression1);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Bracket_NumExpression(f,a,s,c,e1,true);
		}
		
		public static Div_NumExpression parseDiv_NumExpression(Vector input){
			Vector DivExpression1 = new Vector();
			Vector DivExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<DivExpression1>")==0){e1count++;}
				if(temp.compareTo("<DivExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</DivExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</DivExpression1>")==0){e1count--;}
				if(temp.compareTo("</DivExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</DivExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=2;i<exp1;i++){
			DivExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			DivExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(DivExpression1);
			NumExpression e2 = (NumExpression) parseComponent(DivExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Div_NumExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Dot_NumExpression parseDot_NumExpression(Vector input){
			Vector DotExpression1 = new Vector();
			Vector DotExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<DotExpression1>")==0){e1count++;}
				if(temp.compareTo("<DotExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</DotExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</DotExpression1>")==0){e1count--;}
				if(temp.compareTo("</DotExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</DotExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=2;i<exp1;i++){
			DotExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			DotExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(DotExpression1);
			NumExpression e2 = (NumExpression) parseComponent(DotExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Dot_NumExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Int_NumExpression parseInt_NumExpression(Vector input){
			Vector IntExpression = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("</IntValue>")==0){exp1=i;}
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=1;i<exp1;i++){
			IntExpression.add(input.elementAt(i));
			}
			for(int i=exp1+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			Integer v = new Integer((String) IntExpression.elementAt(1));
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Int_NumExpression(f,a,s,c,v.intValue(),true);
		}
		
		public static Pi_NumExpression parsePi_NumExpression(Vector input){
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=0;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Pi_NumExpression(f,a,s,c,true);
		}		

		public static Minus_NumExpression parseMinus_NumExpression(Vector input){
			Vector MinusExpression1 = new Vector();
			Vector MinusExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<MinusExpression1>")==0){e1count++;}
				if(temp.compareTo("<MinusExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</MinusExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</MinusExpression1>")==0){e1count--;}
				if(temp.compareTo("</MinusExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</MinusExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			MinusExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			MinusExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(MinusExpression1);
			NumExpression e2 = (NumExpression) parseComponent(MinusExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Minus_NumExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Mult_NumExpression parseMult_NumExpression(Vector input){
			Vector MultExpression1 = new Vector();
			Vector MultExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<MultExpression1>")==0){e1count++;}
				if(temp.compareTo("<MultExpression2>")==0){e2count++;}

				if(temp.compareTo("</MultExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</MultExpression1>")==0){e1count--;}
				if(temp.compareTo("</MultExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</MultExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			MultExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			MultExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(MultExpression1);
			NumExpression e2 = (NumExpression) parseComponent(MultExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Mult_NumExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Power_NumExpression parsePower_NumExpression(Vector input){
			Vector PowerExpression1 = new Vector();
			Vector PowerExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<PowerExpression1>")==0){e1count++;}
				if(temp.compareTo("<PowerExpression2>")==0){e2count++;}

				if(temp.compareTo("</PowerExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</PowerExpression1>")==0){e1count--;}
				if(temp.compareTo("</PowerExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</PowerExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			PowerExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			PowerExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(PowerExpression1);
			NumExpression e2 = (NumExpression) parseComponent(PowerExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Power_NumExpression(f,a,s,c,e1,e2,true);
		}
		
		public static And_BoolExpression parseAnd_BoolExpression(Vector input){
			Vector AndExpression1 = new Vector();
			Vector AndExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<AndExpression1>")==0){e1count++;}
				if(temp.compareTo("<AndExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</AndExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</AndExpression1>")==0){e1count--;}
				if(temp.compareTo("</AndExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</AndExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			AndExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			AndExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e1 = (BoolExpression) parseComponent(AndExpression1);
			BoolExpression e2 = (BoolExpression) parseComponent(AndExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new And_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Boolean_BoolExpression parseBoolean_BoolExpression(Vector input){
			Vector BoolExpression = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("</BoolValue>")==0){exp1=i;}
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=1;i<exp1;i++){
			BoolExpression.add(input.elementAt(i));
			}
			for(int i=exp1+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			String vs = (String) BoolExpression.elementAt(1);
			boolean v = false;
			if(vs.compareTo("true")==0){
			v = true;
			}
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Boolean_BoolExpression(f,a,s,c,v,true);
		}
		
		public static Bracket_BoolExpression parseBracket_BoolExpression(Vector input){
			Vector BracketExpression1 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int ecount = 0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<BracketExpression>")==0){ecount++;}
					
				if(temp.compareTo("</BracketExpression>")==0&&ecount==1){exp1=i;}
				else if(temp.compareTo("</BracketExpression>")==0){ecount--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=2;i<exp1;i++){
			BracketExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e1 = (BoolExpression) parseComponent(BracketExpression1);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Bracket_BoolExpression(f,a,s,c,e1,true);
		}
		
		public static Equals_BoolExpression parseEquals_BoolExpression(Vector input){
			Vector EqualsExpression1 = new Vector();
			Vector EqualsExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<EqualsExpression1>")==0){e1count++;}
				if(temp.compareTo("<EqualsExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</EqualsExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</EqualsExpression1>")==0){e1count--;}
				if(temp.compareTo("</EqualsExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</EqualsExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			EqualsExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			EqualsExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(EqualsExpression1);
			NumExpression e2 = (NumExpression) parseComponent(EqualsExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Equals_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static GreaterEquals_BoolExpression parseGreaterEquals_BoolExpression(Vector input){
			Vector GreaterEqualsExpression1 = new Vector();
			Vector GreaterEqualsExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<GreaterEqualsExpression1>")==0){e1count++;}
				if(temp.compareTo("<GreaterEqualsExpression2>")==0){e2count++;}

				if(temp.compareTo("</GreaterEqualsExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</GreaterEqualsExpression1>")==0){e1count--;}
				if(temp.compareTo("</GreaterEqualsExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</GreaterEqualsExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			GreaterEqualsExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			GreaterEqualsExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(GreaterEqualsExpression1);
			NumExpression e2 = (NumExpression) parseComponent(GreaterEqualsExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new GreaterEquals_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Greater_BoolExpression parseGreater_BoolExpression(Vector input){
			Vector GreaterExpression1 = new Vector();
			Vector GreaterExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<GreaterExpression1>")==0){e1count++;}
				if(temp.compareTo("<GreaterExpression2>")==0){e2count++;}

				if(temp.compareTo("</GreaterExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</GreaterExpression1>")==0){e1count--;}
				if(temp.compareTo("</GreaterExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</GreaterExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			GreaterExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			GreaterExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(GreaterExpression1);
			NumExpression e2 = (NumExpression) parseComponent(GreaterExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Greater_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static LessEquals_BoolExpression parseLessEquals_BoolExpression(Vector input){
			Vector LessEqualsExpression1 = new Vector();
			Vector LessEqualsExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<LessEqualsExpression1>")==0){e1count++;}
				if(temp.compareTo("<LessEqualsExpression2>")==0){e2count++;}

				if(temp.compareTo("</LessEqualsExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</LessEqualsExpression1>")==0){e1count--;}
				if(temp.compareTo("</LessEqualsExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</LessEqualsExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			LessEqualsExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			LessEqualsExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(LessEqualsExpression1);
			NumExpression e2 = (NumExpression) parseComponent(LessEqualsExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new LessEquals_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Less_BoolExpression parseLess_BoolExpression(Vector input){
			Vector LessExpression1 = new Vector();
			Vector LessExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<LessExpression1>")==0){e1count++;}
				if(temp.compareTo("<LessExpression2>")==0){e2count++;}

				if(temp.compareTo("</LessExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</LessExpression1>")==0){e1count--;}
				if(temp.compareTo("</LessExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</LessExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			LessExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			LessExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(LessExpression1);
			NumExpression e2 = (NumExpression) parseComponent(LessExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Less_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static NotEquals_BoolExpression parseNotEquals_BoolExpression(Vector input){
			Vector NotEqualsExpression1 = new Vector();
			Vector NotEqualsExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<NotEqualsExpression1>")==0){e1count++;}
				if(temp.compareTo("<NotEqualsExpression2>")==0){e2count++;}

				if(temp.compareTo("</NotEqualsExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</NotEqualsExpression1>")==0){e1count--;}
				if(temp.compareTo("</NotEqualsExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</NotEqualsExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			NotEqualsExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			NotEqualsExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e1 = (NumExpression) parseComponent(NotEqualsExpression1);
			NumExpression e2 = (NumExpression) parseComponent(NotEqualsExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new NotEquals_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Not_BoolExpression parseNot_BoolExpression(Vector input){
			Vector NotExpression = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int ecount = 0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<NotExpression>")==0){ecount++;}
				
				if(temp.compareTo("</NotExpression>")==0&&ecount==1){exp1=i;}
				else if(temp.compareTo("</NotExpression>")==0){ecount--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}	
			}
			for(int i=2;i<exp1;i++){
			NotExpression.add(input.elementAt(i));
			}
			for(int i=exp1+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e = (BoolExpression) parseComponent(NotExpression);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Not_BoolExpression(f,a,s,c,e,true);
		}
		
		public static Or_BoolExpression parseOr_BoolExpression(Vector input){
			Vector OrExpression1 = new Vector();
			Vector OrExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<OrExpression1>")==0){e1count++;}
				if(temp.compareTo("<OrExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</OrExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</OrExpression1>")==0){e1count--;}
				if(temp.compareTo("</OrExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</OrExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			OrExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			OrExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e1 = (BoolExpression) parseComponent(OrExpression1);
			BoolExpression e2 = (BoolExpression) parseComponent(OrExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Or_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Equiv_BoolExpression parseEquiv_BoolExpression(Vector input){
			Vector EquivExpression1 = new Vector();
			Vector EquivExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<EquivExpression1>")==0){e1count++;}
				if(temp.compareTo("<EquivExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</EquivExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</EquivExpression1>")==0){e1count--;}
				if(temp.compareTo("</EquivExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</EquivExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			EquivExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			EquivExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e1 = (BoolExpression) parseComponent(EquivExpression1);
			BoolExpression e2 = (BoolExpression) parseComponent(EquivExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Equiv_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static ImpliesR_BoolExpression parseImpliesR_BoolExpression(Vector input){
			Vector ImpliesRExpression1 = new Vector();
			Vector ImpliesRExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<ImpliesRExpression1>")==0){e1count++;}
				if(temp.compareTo("<ImpliesRExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</ImpliesRExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</ImpliesRExpression1>")==0){e1count--;}
				if(temp.compareTo("</ImpliesRExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</ImpliesRExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			ImpliesRExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			ImpliesRExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e1 = (BoolExpression) parseComponent(ImpliesRExpression1);
			BoolExpression e2 = (BoolExpression) parseComponent(ImpliesRExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new ImpliesR_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static ImpliesL_BoolExpression parseImpliesL_BoolExpression(Vector input){
			Vector ImpliesLExpression1 = new Vector();
			Vector ImpliesLExpression2 = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp1 = 0;
			int exp2 = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			int e2count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<ImpliesLExpression1>")==0){e1count++;}
				if(temp.compareTo("<ImpliesLExpression2>")==0){e2count++;}
				
				if(temp.compareTo("</ImpliesLExpression1>")==0&&e1count==1){exp1=i;}
				else if(temp.compareTo("</ImpliesLExpression1>")==0){e1count--;}
				if(temp.compareTo("</ImpliesLExpression2>")==0&&e1count==1){exp2=i;}
				else if(temp.compareTo("</ImpliesLExpression2>")==0){e1count--;}
				
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp1;i++){
			ImpliesLExpression1.add(input.elementAt(i));
			}
			for(int i=exp1+2;i<exp2;i++){
			ImpliesLExpression2.add(input.elementAt(i));
			}
			for(int i=exp2+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e1 = (BoolExpression) parseComponent(ImpliesLExpression1);
			BoolExpression e2 = (BoolExpression) parseComponent(ImpliesLExpression2);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new ImpliesL_BoolExpression(f,a,s,c,e1,e2,true);
		}
		
		public static Declared_NumExpression parseDeclared_NumExpression(Vector input){
			Vector value = new Vector();
			Vector expanded = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp = 0;
			int expnd = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("</DeclaredValue>")==0){exp=i;}
				if(temp.compareTo("</expanded>")==0){expnd=i;}
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp;i++){
			value.add(input.elementAt(i));
			}
			for(int i=exp+1;i<expnd;i++){
			expanded.add(input.elementAt(i));
			}
			for(int i=expnd+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			String v = (String) value.elementAt(0);
			boolean e;
			if(((String)expanded.elementAt(0)).compareTo("true")==0){
			e = true;
			}else{
			e = false;
			}
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Declared_NumExpression(f,a,s,c,v,true,e,hm);
		}
		
		public static Declared_BoolExpression parseDeclared_BoolExpression(Vector input){
			Vector value = new Vector();
			Vector expanded = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp = 0;
			int expnd = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int e1count=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("</DeclaredValue>")==0){exp=i;}
				if(temp.compareTo("</expanded>")==0){expnd=i;}
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp;i++){
			value.add(input.elementAt(i));
			}
			for(int i=exp+1;i<expnd;i++){
			expanded.add(input.elementAt(i));
			}
			for(int i=expnd+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			String v = (String) value.elementAt(0);
			boolean e;
			if(((String)expanded.elementAt(0)).compareTo("true")==0){
			e = true;
			}else{
			e = false;
			}
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new Declared_BoolExpression(f,a,s,c,v,true,e,hm);
		}
		
		public static DeclareBool parseDeclareBool(Vector input){
			Vector Expression = new Vector();
			Vector name = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp = 0;
			int nm = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int ecount=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<DeclaredExpression>")==0){ecount++;}
				
				if(temp.compareTo("</DeclaredExpression>")==0&&ecount==1){exp=i;}
				else if(temp.compareTo("</DeclaredExpression>")==0){ecount--;}
				
				if(temp.compareTo("</key>")==0){nm=i;}
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp;i++){
			Expression.add(input.elementAt(i));
			}
			for(int i=exp+1;i<nm;i++){
			name.add(input.elementAt(i));
			}
			for(int i=nm+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			BoolExpression e = (BoolExpression) parseComponent(Expression);
			String k = (String) name.elementAt(1);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new DeclareBool(f,a,s,c,k,e,true,hm);
		}
		
		public static DeclareNum parseDeclareNum(Vector input){
			Vector Expression = new Vector();
			Vector name = new Vector();
			Vector font = new Vector();
			Vector attrib = new Vector();
			Vector size = new Vector();
			Vector colour = new Vector();
			int exp = 0;
			int nm = 0;
			int fnt = 0;
			int att = 0;
			int sze = 0;
			int clr = 0;
			int ecount=0;
			for(int i = 0;i< input.size();i++){
				String temp = (String) input.elementAt(i);
				if(temp.compareTo("<DeclaredExpression>")==0){ecount++;}
				
				if(temp.compareTo("</DeclaredExpression>")==0&&ecount==1){exp=i;}
				else if(temp.compareTo("</DeclaredExpression>")==0){ecount--;}
				
				if(temp.compareTo("</key>")==0){nm=i;}
				if(temp.compareTo("</font>")==0){fnt=i;}
				if(temp.compareTo("</attrib>")==0){att=i;}
				if(temp.compareTo("</size>")==0){sze=i;}
				if(temp.compareTo("</colour>")==0){clr=i;}
			}
			for(int i=2;i<exp;i++){
			Expression.add(input.elementAt(i));
			}
			for(int i=exp+1;i<nm;i++){
			name.add(input.elementAt(i));
			}
			for(int i=nm+1;i<fnt;i++){
			font.add(input.elementAt(i));
			}
			for(int i=fnt+1;i<att;i++){
			attrib.add(input.elementAt(i));
			}
			for(int i=att+1;i<sze;i++){
			size.add(input.elementAt(i));
			}
			for(int i=sze+1;i<clr;i++){
			colour.add(input.elementAt(i));
			}
			NumExpression e = (NumExpression) parseComponent(Expression);
			String k = (String) name.elementAt(1);
			String f = parseFont(font);
			int a = parseAttrib(attrib);
			int s = parseSize(size);
			Color c = parseColour(colour);
			return new DeclareNum(f,a,s,c,k,e,true,hm);
		}
		
}