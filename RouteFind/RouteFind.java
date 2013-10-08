import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class RouteFind extends JFrame {
	

	RouteFindCanvas canvas;
	
	public RouteFind(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("RouteFind");
		this.setSize(820,670);
		this.getContentPane().setLayout(new FlowLayout());
		canvas = new RouteFindCanvas();
		this.getContentPane().add(canvas);
		this.setVisible(true);
	}

	public static void main(String[] args){
		RouteFind main = new RouteFind();	
	}
	
}