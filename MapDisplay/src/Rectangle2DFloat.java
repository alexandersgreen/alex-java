import java.io.*;
import java.awt.geom.*;

public class Rectangle2DFloat extends Rectangle2D.Float implements Serializable{
	

public Rectangle2DFloat(float x, float y, float w, float h){
	super(x,y,w,h);
}	


private void writeObject(java.io.ObjectOutputStream out)
     throws IOException {
    out.writeFloat(x);
    out.writeFloat(y);
    out.writeFloat(width);
    out.writeFloat(height);
}
 
private void readObject(java.io.ObjectInputStream in)
     throws IOException, ClassNotFoundException {
	x = in.readFloat();
	y = in.readFloat();
	width = in.readFloat();
	height = in.readFloat();     	
	if(x%200==0){
		System.out.print(".");
	}
}
	
}