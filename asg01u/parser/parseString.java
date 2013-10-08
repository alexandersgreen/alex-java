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

public class parseString {
	
	public static HashMap variables;
	
	public static component main(String input,HashMap var){
		variables = var;
		StringTokenizer tokenizer = new StringTokenizer(input);
		String[] strings = new String[tokenizer.countTokens()];
		int count = 0;
		while(tokenizer.hasMoreTokens()){
			strings[count] = tokenizer.nextToken();
			count++;
		}
		if(strings[0].compareTo("image")==0){
			return parseImage(strings);
			}
		else if(strings[0].compareTo("bool")==0&&strings[2].compareTo("::=")==0){
			return parseDeclareBool(strings);
			}
		else if(strings[0].compareTo("num")==0&&strings[2].compareTo("::=")==0){
			return parseDeclareNum(strings);
			}
		else if(contains(strings,"(",0)!=-1
			||contains(strings,")",0)!=-1
			||contains(strings,"=",0)!=-1
			||contains(strings,"!=",0)!=-1
			||contains(strings,"<",0)!=-1
			||contains(strings,">",0)!=-1
			||contains(strings,"<=",0)!=-1
			||contains(strings,">=",0)!=-1
			||contains(strings,"&",0)!=-1
			||contains(strings,"|",0)!=-1
			||contains(strings,"true",0)!=-1
			||contains(strings,"false",0)!=-1
			||contains(strings,"+",0)!=-1
			||contains(strings,"-",0)!=-1
			||contains(strings,"/",0)!=-1
			||contains(strings,".",0)!=-1
			||contains(strings,"*",0)!=-1
			||contains(strings,"^",0)!=-1
			||contains(strings,"->",0)!=-1
			||contains(strings,"<-",0)!=-1
			||contains(strings,"==",0)!=-1
			){
		return parseExpression(strings);
		}else{
		return parseText(strings);	
		}
		}
		
	public static Expression parseExpression(String[] input){
		if( contains(input,"=",0)!=-1
			||contains(input,"!=",0)!=-1
			||contains(input,"<",0)!=-1
			||contains(input,">",0)!=-1
			||contains(input,"<=",0)!=-1
			||contains(input,">=",0)!=-1
			||contains(input,"&",0)!=-1
			||contains(input,"|",0)!=-1
			||contains(input,"true",0)!=-1
			||contains(input,"false",0)!=-1
			||contains(input,"->",0)!=-1
			||contains(input,"<-",0)!=-1
			||contains(input,"==",0)!=-1
			){
		return parseBoolExpression(input);
		}else{
		return parseNumExpression(input);	
		}
		}
		
	public static NumExpression parseNumExpression(String[] input){
		Vector temp = new Vector(input.length);
		if( containsF(input,"(",0) !=-1){
		for(int i = 0; i< input.length;i++){
			String temp2 = "";
			if( containsF(input,"(",i) ==i){
				int rlb = i+1;
				int count = 1;
				int error = 0;
				while(count>0){
				error++;
				if(containsF(input,"(",rlb)==rlb){count++;}
				else if(containsF(input,")",rlb)==rlb){count--;}
				rlb++;
				if(error>1000){
				break;
				}
				}
				for(int j = i+1;j< rlb-1;j++ ){
						temp2=temp2 + " " + input[j];
						i++;
					}
				i++;
				temp.add(temp2);
			}else{
				temp.add(input[i]);
			}
		}
		}
		String[] input2;
		if(temp.size()>0){
		input2 = new String[temp.size()];
		for(int i=0;i<temp.size();i++){
			input2[i] = (String) temp.elementAt(i);
			}
		}else{
		input2 = new String[input.length];
		for(int i=0;i<input.length;i++){
			input2[i]=input[i];
			}	
		}
		if(contains(input2,"-",0)!=-1){
			return parseMinus_NumExpression(input2);
			}
		else if(contains(input2,"+",0)!=-1){
			return parseAdd_NumExpression(input2);
			}
		else if(contains(input2,"/",0)!=-1){
			return parseDiv_NumExpression(input2);
			}
		else if(contains(input2,"*",0)!=-1){
			return parseMult_NumExpression(input2);
			}
		else if(contains(input2,".",0)!=-1){
			return parseDot_NumExpression(input2);
			}
		else if(contains(input2,"^",0)!=-1){
			return parsePower_NumExpression(input2);
			}
		else{
			StringTokenizer tokenizer2 = new StringTokenizer(input2[0]);
			String[] strings = new String[tokenizer2.countTokens()];
			int count = 0;
			while(tokenizer2.hasMoreTokens()){
				strings[count] = tokenizer2.nextToken();
				count++;
			}	
			if(strings.length==1){
				try{
					Integer tempInt = new Integer(strings[0]);
					return new Int_NumExpression("arial",Font.PLAIN,20,Color.BLACK,tempInt.intValue(),true);	
				}catch(Exception e){
					if(strings[0].compareTo("pi")==0){
						return new Pi_NumExpression("arial",Font.PLAIN,20,Color.BLACK,true);	
					}else{
						return new Declared_NumExpression("arial",Font.PLAIN,20,Color.BLACK,strings[0],true,false,variables); 
					}
				}
			}else{
				return new Bracket_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(strings),true);	
			}
		} 
		}
		
		public static Power_NumExpression parsePower_NumExpression(String[] input){
			int pos = contains(input,"^",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Power_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Dot_NumExpression parseDot_NumExpression(String[] input){
			int pos = contains(input,".",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Dot_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Mult_NumExpression parseMult_NumExpression(String[] input){
			int pos = contains(input,"*",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Mult_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Div_NumExpression parseDiv_NumExpression(String[] input){
			int pos = contains(input,"/",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Div_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Add_NumExpression parseAdd_NumExpression(String[] input){
			int pos = contains(input,"+",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Add_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Minus_NumExpression parseMinus_NumExpression(String[] input){
			int pos = contains(input,"-",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Minus_NumExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
	public static BoolExpression parseBoolExpression(String[] input){
		Vector temp = new Vector(input.length);
		if( containsF(input,"(",0) !=-1){
		for(int i = 0; i< input.length;i++){
			String temp2 = "";
			if( containsF(input,"(",i) ==i){
				int rlb = i+1;
				int count = 1;
				int error = 0;
				while(count>0){
				if(containsF(input,"(",rlb)==rlb){count++;}
				else if(containsF(input,")",rlb)==rlb){count--;}
				rlb++;
				if(error>1000){
				break;
				}
				}
				for(int j = i+1;j< rlb-1;j++ ){
						temp2=temp2 + " " + input[j];
						i++;
					}
				i++;
				temp.add(temp2);
			}else{
				temp.add(input[i]);
			}
		}
		}
		String[] input2;
		if(temp.size()>0){
		input2 = new String[temp.size()];
		for(int i=0;i<temp.size();i++){
			input2[i] = (String) temp.elementAt(i);
			}
		}else{
		input2 = new String[input.length];
		for(int i=0;i<input.length;i++){
			input2[i]=input[i];
			}	
		}
		if(contains(input2,"!",0)!=-1){
			return parseNot_BoolExpression(input2);
			}
		else if(contains(input2,"==",0)!=-1){
			return parseEquiv_BoolExpression(input2);
			}		
		else if(contains(input2,"->",0)!=-1){
			return parseImpliesR_BoolExpression(input2);
			}
		else if(contains(input2,"<-",0)!=-1){
			return parseImpliesL_BoolExpression(input2);
			}					
		else if(contains(input2,"|",0)!=-1){
			return parseOr_BoolExpression(input2);
			}
		else if(contains(input2,"&",0)!=-1){
			return parseAnd_BoolExpression(input2);
			}
		else if(contains(input2,"<",0)!=-1){
			return parseLess_BoolExpression(input2);
			}
		else if(contains(input2,"<=",0)!=-1){
			return parseLessEquals_BoolExpression(input2);
			}
		else if(contains(input2,">",0)!=-1){
			return parseGreater_BoolExpression(input2);
			}
		else if(contains(input2,">=",0)!=-1){
			return parseGreaterEquals_BoolExpression(input2);
			}
		else if(contains(input2,"=",0)!=-1){
			return parseEquals_BoolExpression(input2);
			}
		else if(contains(input2,"!=",0)!=-1){
			return parseNotEquals_BoolExpression(input2);
			}
		else{
			StringTokenizer tokenizer2 = new StringTokenizer(input2[0]);
			String[] strings = new String[tokenizer2.countTokens()];
			int count = 0;
			while(tokenizer2.hasMoreTokens()){
				strings[count] = tokenizer2.nextToken();
				count++;
			}	
			if(strings.length==1){
				if(strings[0].compareTo("true")==0){
					return new Boolean_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,true,true);
				}else if(strings[0].compareTo("false")==0){
					return new Boolean_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,false,true);	
				}else{
					return new Declared_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,strings[0],true,false,variables);
				}
			}else{
				return new Bracket_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(strings),true);	
			}
		}
		}
		
		public static Not_BoolExpression parseNot_BoolExpression(String[] input){
			int pos = contains(input,"!",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Not_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(exp2),true);
		}
		
		public static Less_BoolExpression parseLess_BoolExpression(String[] input){
			int pos = contains(input,"<",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Less_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static LessEquals_BoolExpression parseLessEquals_BoolExpression(String[] input){
			int pos = contains(input,"<=",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new LessEquals_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Greater_BoolExpression parseGreater_BoolExpression(String[] input){
			int pos = contains(input,">",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Greater_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static GreaterEquals_BoolExpression parseGreaterEquals_BoolExpression(String[] input){
			int pos = contains(input,">=",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new GreaterEquals_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static Equals_BoolExpression parseEquals_BoolExpression(String[] input){
			int pos = contains(input,"=",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			try{
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Equals_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
			}catch(Exception e){
			NumExpression temp1 = parseNumExpression(exp1);
			Int_NumExpression temp2 = new Int_NumExpression("arial",Font.PLAIN,20,Color.BLACK,(int) temp1.getValue(),true);	
			Equals_BoolExpression temp3 = new Equals_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,temp1,temp2,true);
			temp3.exp2.setTheFont("arial",Font.PLAIN,20,Color.BLUE,3);
			return temp3;
			}
		}
		
		public static Equiv_BoolExpression parseEquiv_BoolExpression(String[] input){
			int pos = contains(input,"==",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			try{
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Equiv_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(exp1),parseBoolExpression(exp2),true);
		}catch(Exception e){
			BoolExpression temp1 = parseBoolExpression(exp1);
			Boolean_BoolExpression temp2 = new Boolean_BoolExpression("arial",Font.PLAIN,20,Color.BLACK, temp1.getValue(),true);	
			Equiv_BoolExpression temp3 = new Equiv_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,temp1,temp2,true);
			temp3.exp2.setTheFont("arial",Font.PLAIN,20,Color.BLUE,3);
			return temp3;
		}
		}
		
		public static NotEquals_BoolExpression parseNotEquals_BoolExpression(String[] input){
			int pos = contains(input,"!=",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new NotEquals_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseNumExpression(exp1),parseNumExpression(exp2),true);
		}
		
		public static And_BoolExpression parseAnd_BoolExpression(String[] input){
			int pos = contains(input,"&",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new And_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(exp1),parseBoolExpression(exp2),true);
		}
		
		public static Or_BoolExpression parseOr_BoolExpression(String[] input){
			int pos = contains(input,"|",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new Or_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(exp1),parseBoolExpression(exp2),true);
		}
		
		public static ImpliesR_BoolExpression parseImpliesR_BoolExpression(String[] input){
			int pos = contains(input,"->",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new ImpliesR_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(exp1),parseBoolExpression(exp2),true);
		}
		
		public static ImpliesL_BoolExpression parseImpliesL_BoolExpression(String[] input){
			int pos = contains(input,"<-",0);
			String[] exp1 = new String[pos];
			String[] exp2 = new String[input.length-(pos+1)];
			for(int i = 0;i<pos;i++){
				exp1[i]=input[i];	
			}
			for(int i=0;i<(input.length-(pos+1));i++){
				exp2[i]=input[i+pos+1];	
			}
			return new ImpliesL_BoolExpression("arial",Font.PLAIN,20,Color.BLACK,parseBoolExpression(exp1),parseBoolExpression(exp2),true);
		}
	
	public static Text parseText(String[] input){
			return new Text(Text.createString(input),"arial",Font.BOLD,16,Color.BLACK);
		}
		
	public static int contains(String[] testIn,String testFor,int start){
		for(int i=testIn.length-1;i>=start;i--){
			if(testIn[i].compareTo(testFor)==0){return i;}
		}
		return -1;
		}
		
	public static int containsF(String[] testIn,String testFor,int start){
		for(int i=start;i<testIn.length;i++){
			if(testIn[i].compareTo(testFor)==0){return i;}
		}
		return -1;
		}	
	
	public static image parseImage(String[] input){
		return new image(input[1]);
		}
		
	public static DeclareBool parseDeclareBool(String[] input){
		String[] expression = new String[input.length-3];
		for(int i = 3 ;i < input.length ;i++){
		expression[i-3]=input[i];	
		}
		return new DeclareBool("arial",Font.PLAIN,20,Color.BLACK,input[1],parseBoolExpression(expression),true,variables);
		}
	
	public static DeclareNum parseDeclareNum(String[] input){
		String[] expression = new String[input.length-3];
		for(int i = 3 ;i < input.length ;i++){
		expression[i-3]=input[i];	
		}
		return new DeclareNum("arial",Font.PLAIN,20,Color.BLACK,input[1],parseNumExpression(expression),true,variables);
		}
}
