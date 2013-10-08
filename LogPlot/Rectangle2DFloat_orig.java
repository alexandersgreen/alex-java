import java.io.*;
import java.awt.geom.*;

public class Rectangle2DFloat_orig extends Rectangle2D.Float implements Serializable{
	
public double origX;
public double origY;

public Rectangle2DFloat_orig(float x, float y, float w, float h, double oX , double oY){
	super(x,y,w,h);
	origX = oX;
	origY = oY;
}	


private void writeObject(java.io.ObjectOutputStream out)
     throws IOException {
    out.writeFloat(x);
    out.writeFloat(y);
    out.writeFloat(width);
    out.writeFloat(height);
    out.writeDouble(origX);
    out.writeDouble(origY);
}
 
private void readObject(java.io.ObjectInputStream in)
     throws IOException, ClassNotFoundException {
	x = in.readFloat();
	y = in.readFloat();
	width = in.readFloat();
	height = in.readFloat();
	origX = in.readDouble();
	origY = in.readDouble();     	
}
	
	
	
}