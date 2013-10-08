import java.io.*;
import java.awt.geom.*;

public class Line2DFloat extends Line2D.Float implements Serializable{
	

public Line2DFloat(float x1, float y1, float x2, float y2){
	super(x1,y1,x2,y2);
}	


private void writeObject(java.io.ObjectOutputStream out)
     throws IOException {
    out.writeFloat(x1);
    out.writeFloat(y1);
    out.writeFloat(x2);
    out.writeFloat(y2);
}
 
private void readObject(java.io.ObjectInputStream in)
     throws IOException, ClassNotFoundException {
	x1 = in.readFloat();
	y1 = in.readFloat();
	x2 = in.readFloat();
	y2 = in.readFloat();     	
}
	
	
	
}