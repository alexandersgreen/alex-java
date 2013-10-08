import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.comm.*;
import java.util.*;
import java.io.*;

public class ArrowsPanel extends JPanel{
	
	ImageIcon image = new ImageIcon("c:\\arrow.jpg");
	int W = image.getIconWidth();
	int H = image.getIconHeight();
	double angle;
	
	public ArrowsPanel(double ang){
		super();
		this.setMinimumSize(new Dimension(60,60));
		this.setBackground(Color.WHITE);
		angle = ang;
	}
	
	public void paintComponent(Graphics g){
    	super.paintComponent(g);
   		Graphics2D g2 = (Graphics2D)g;
      	g2.rotate (Math.toRadians(angle), W/2, H/2);
      	g2.drawImage(image.getImage(), 0, 0, this);
      	g2.dispose();
   }
   
}
