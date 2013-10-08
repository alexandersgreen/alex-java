import java.util.*;
import java.awt.geom.*;
import java.io.*;

public class node implements Serializable{
	
	public Long node;
	public int id;
	public int posX;
	public int posY;
	
	public short nextNode[];
	
	public Vector links; //Integers
	public Rectangle2DFloat theNode; //careful with this!

	public node(Long n,int i,int x,int y){
		nextNode = new short[9450];
		for(int j=0;j<9450;j++){
			nextNode[j]=-1;
		}
		node = n;
		id = i;
		posX = x;
		posY = y;
		links = new Vector(6);
	}
	
	public void addLink(int newLink){
		links.add(newLink);	
	}
	
}