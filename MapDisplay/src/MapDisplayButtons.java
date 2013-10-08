import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class MapDisplayButtons extends JPanel implements ActionListener,ItemListener{
	
	MapDisplayCanvas canvas;
	String buttonName[] = {"Add GPS","Draw Nodes","Zoom In","Zoom Out","Up","Down","Left","Right","Add Log","Clear Logs"};
	CoordConv conv;
	JFileChooser fc;
	ExampleFileFilter filt;
	JCheckBox jcb;

public MapDisplayButtons(MapDisplayCanvas c){
	fc = new JFileChooser();
	filt = new ExampleFileFilter("txt","NMEA log files");	
	fc.setFileFilter(filt);
	conv = new CoordConv();
	canvas = c;
	this.setLayout(new FlowLayout());
	JButton	button[] = new JButton[10];
	for(int i=0;i<10;i++){
		button[i] = new JButton(buttonName[i]);
		button[i].addActionListener(this);
		this.add(button[i]);
	}
	jcb = new JCheckBox("NR",true);
	jcb.addItemListener(this);
	this.add(jcb);
}

public void actionPerformed(ActionEvent e){
	JButton temp = (JButton) e.getSource();
	String tempS = temp.getText();
	tempS = tempS.trim();
	if(tempS.compareTo(buttonName[0])==0){
		NMEA gpsDialog = new NMEA(canvas,conv);
	}else if(tempS.compareTo(buttonName[1])==0){
		canvas.drawNodes(Color.BLACK);
	}else if(tempS.compareTo(buttonName[2])==0){
		canvas.zoomTo(1.2);
	}else if(tempS.compareTo(buttonName[3])==0){
		canvas.zoomTo(1/1.2);
	}else if(tempS.compareTo(buttonName[4])==0){
		canvas.moveTo(0,-10);
	}else if(tempS.compareTo(buttonName[5])==0){
		canvas.moveTo(0,10);
	}else if(tempS.compareTo(buttonName[6])==0){
		canvas.moveTo(-10,0);
	}else if(tempS.compareTo(buttonName[7])==0){
		canvas.moveTo(10,0);
	}else if(tempS.compareTo(buttonName[8])==0){
		//if(canvas.notZoomed){

		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
          	File file = fc.getSelectedFile();
        	try{
				
				MapPlot plot = new MapPlot(file,conv);
				canvas.addLog(plot.getVect());	
        	}catch(Exception ioe){
        				System.out.println(ioe.getMessage());
        	}
        }
	
		//}else{
		//	JOptionPane.showMessageDialog(this,"Please open logs before zooming or moving the map");
		//}
	}else if(tempS.compareTo(buttonName[10])==0){
		canvas.removeLogs();
	}
}

	public void itemStateChanged(ItemEvent e){
		if (e.getStateChange() == ItemEvent.DESELECTED){
			canvas.nr = false;
		}else if(e.getStateChange() == ItemEvent.SELECTED){
			canvas.nr = true;
		}
	}
	
}
