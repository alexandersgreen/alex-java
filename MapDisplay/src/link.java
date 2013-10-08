import java.awt.geom.*;
import java.io.*;

public class link implements Serializable{
	
	public Long node1;
	public Long node2;
	public double length;
	public String DescTerm;
	public Line2DFloat theLink; //careful with this!
	public boolean onRoute;
	
	public link(Long n1,Long n2,double l, String dt){
		onRoute = false;
		node1 = n1;
		node2 = n2;
		length = l;
		DescTerm = dt;	
	}
	
}