package asg01u;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import asg01u.html.viewer.*;
import asg01u.components.*;

public class asg01u_main extends JFrame implements KeyListener, ActionListener{

public JScrollPane scrollPane;
public JPanel mainPane;
public Vector components;
public static HashMap variables = new HashMap<String,component>();

public ImageIcon logoImage;
final JFileChooser fc = new JFileChooser();

public static void main(String args[]){
	asg01u_main main = new asg01u_main();
	}
	
public asg01u_main(){
	JMenuBar menuBar = new JMenuBar();
	logoImage = new ImageIcon("asg01u/images/logo.gif");
	JMenu fileMenu = new JMenu("File");
		JMenuItem fileNew = new JMenuItem("New");
		fileNew.addActionListener(this);
		fileMenu.add(fileNew);
		fileMenu.addSeparator();
		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.addActionListener(this);
		fileMenu.add(fileOpen);
		JMenuItem fileSave = new JMenuItem("Save");
		fileSave.addActionListener(this);
		fileMenu.add(fileSave);
		fileMenu.addSeparator();
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(this);
		fileMenu.add(fileExit);
	menuBar.add(fileMenu);
	
	asg01u.other.ExampleFileFilter filter = new asg01u.other.ExampleFileFilter();
    filter.addExtension("xml");
    filter.addExtension("pres");
    filter.setDescription("XML and Presentation Files");
    fc.setFileFilter(filter);
	
	JMenu editMenu = new JMenu("Edit");
		JMenu editInsert = new JMenu("Insert Space");
			JMenuItem editInsertAbove = new JMenuItem("Above");
			editInsertAbove.addActionListener(this);
			editInsert.add(editInsertAbove);
			JMenuItem editInsertBelow = new JMenuItem("Below");
			editInsertBelow.addActionListener(this);
			editInsert.add(editInsertBelow);
		editMenu.add(editInsert);
		JMenuItem editDeleteComponent = new JMenuItem("Delete Component");
		editDeleteComponent.addActionListener(this);
		editMenu.add(editDeleteComponent);
	menuBar.add(editMenu);	
	menuBar.add(Box.createHorizontalGlue());
	
	JMenu helpMenu = new JMenu("Help");
		JMenuItem helpHelp = new JMenuItem("Help");
		helpHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		helpHelp.addActionListener(this);
		helpMenu.add(helpHelp);
		JMenuItem logo = new JMenuItem("About",logoImage);
		logo.addActionListener(this);
		logo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.ALT_MASK));
		helpMenu.add(logo);
	menuBar.add(helpMenu);
	

	this.setJMenuBar(menuBar);
	components = new Vector();
	mainPane = new JPanel();	
  	mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
	mainPane.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),50);
	scrollPane = new JScrollPane(mainPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JTextArea component1 = new JTextArea();
	component1.setColumns(50);
	component1.setLineWrap(true);
	component1.addKeyListener(this);
	components.add(component1);
	mainPane.add((JTextArea) components.elementAt(components.size()-1));
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	this.getContentPane().add(scrollPane);
	mainPane.setBackground(Color.WHITE);
	this.setTitle("asg01u - Presentation Editor");
	this.setVisible(true);
	}

public void keyPressed(KeyEvent e){
	if(e.getKeyCode()==KeyEvent.VK_ENTER){
		JTextArea source = (JTextArea) e.getSource();
		String temp = source.getText();
		temp = temp.trim();
		if(temp.length()>0){
			char tempChar = temp.charAt(temp.length()-1);
			if(tempChar==';'){
				int index = components.indexOf(source);
				try{
				Component swap = update(temp.substring(0,temp.length()-1));
				components.remove(index);
				components.add(index,swap);
				if(index==components.size()-1){
				JTextArea nextComponent = new JTextArea();
				nextComponent.addKeyListener(this);
				components.add(index+1,nextComponent);
				}
				update();
				}catch(Exception se){
					System.out.println(se.getMessage());
					JOptionPane.showMessageDialog(this, "Error: Please Check Command Line, for Help press F1");
					JTextArea nextComponent = new JTextArea(source.getText());
					components.remove(index);
					nextComponent.addKeyListener(this);
					components.add(index,nextComponent);
					update();
				}
			}
		}
		}
	}

public Component update(String input){
		return asg01u.parser.parseString.main(input,variables);
		}

public void update(){
		mainPane.removeAll();
		for(int i =0;i<components.size();i++){
			Component temp = (Component) components.elementAt(i);
			mainPane.add(temp);	
		}
		Component temp = (Component) components.elementAt(components.size()-1);
		temp.requestFocus();
		mainPane.validate();
		int size = components.size()*10;
		if(size<800){
		mainPane.add(Box.createRigidArea(new Dimension(0,800-size)));
		}
		mainPane.repaint();
	}

public void keyTyped(KeyEvent e){}
public void keyReleased(KeyEvent e){}

public void insertSpace(boolean above){
	try{
		JTextArea nextComponent = new JTextArea();
		nextComponent.addKeyListener(this);
		if(above){
		components.add(position,nextComponent);
		}else{
		components.add(position+1,nextComponent);	
		}
		update();	
	}catch(Exception e){
		JOptionPane.showMessageDialog(this, "Error: Please Select a Component");
	}
	}
	
public void deleteComponent(){
	try{
	Object[] options = {"Yes","No"};
	int n = JOptionPane.showOptionDialog(this,"Delete component at "+position,
    				"Delete Component",JOptionPane.YES_NO_CANCEL_OPTION,
    					JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
	if(n==0){
		components.removeElementAt(position);
		update();	
	}
	}catch(Exception e){
	JOptionPane.showMessageDialog(this, "Error: Please Select a Component");
	}
	}

public boolean saveChanges(boolean prompt){
	int n=0;
	if(prompt){
		Object[] options = {"Yes","No","Cancel"};
		n = JOptionPane.showOptionDialog(this,"Would You Like To Save First",
    				"Save Changes",JOptionPane.YES_NO_CANCEL_OPTION,
    					JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
	}
	if(n==0){
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
           	File file = fc.getSelectedFile();
           	//JOptionPane.showMessageDialog(this, "Saving: " + file.getName());
           	//System.out.println("Saving: " + file.getName());
        	try{
        		FileWriter fwdel = new FileWriter(file,false);
        		fwdel.write("");
        		fwdel.close();
        		FileWriter fw = new FileWriter(file,true);
        		for(int i=0;i<components.size()-1;i++){
        			try{
        			component tempComp = (component) components.elementAt(i);
        			fw.write(tempComp.outputString());	
        			}catch(Exception ce){
        			System.out.println("Non-Component Found at "+i+", skipping...");
        			}
        		}
        		fw.close();
        	}catch(Exception ioe){
        		System.out.println(ioe.getMessage());
        	}
        
        }
    }
    if(n==2){return false;}
    else{return true;}
}

public void actionPerformed(ActionEvent e){
		JMenuItem temp = (JMenuItem) e.getSource();
		if(temp.getText().compareTo("About")==0){
				viewer about = new viewer("asg01u/html/about.html","About...",490,390,false);
			}
		else if(temp.getText().compareTo("Help")==0){
				viewer help = new viewer("asg01u/html/help_main.html","Help",800,600,true);
			}
		else if(temp.getText().compareTo("Above")==0){
				insertSpace(true);
			}
		else if(temp.getText().compareTo("Below")==0){
				insertSpace(false);
			}
		else if(temp.getText().compareTo("Delete Component")==0){
				deleteComponent();
			}
		else if(temp.getText().compareTo("New")==0){
			if(saveChanges(true)){
			variables.clear();
			components.clear();
			JTextArea nextComponent = new JTextArea();
			nextComponent.addKeyListener(this);
			components.add(nextComponent);
			update();}
			}
		else if(temp.getText().compareTo("Open")==0){
			if(saveChanges(true)){
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
            		File file = fc.getSelectedFile();
               		//JOptionPane.showMessageDialog(this, "Opening: " + file.getName());
               		//System.out.println("Opening: " + file.getName());
        			try{
        				components = asg01u.parser.parseFile.main(file,variables);
        				JTextArea nextComponent = new JTextArea();
						nextComponent.addKeyListener(this);
						components.add(nextComponent);
						update();
        			}catch(Exception ioe){
        				System.out.println(ioe.getMessage());
        			}
        		}
        	}
        }
		else if(temp.getText().compareTo("Save")==0){
				saveChanges(false);
        	}
		else if(temp.getText().compareTo("Exit")==0){
				if(saveChanges(true)){
				System.exit(0);
				}
			}
	}

    public static String readFile(String fileName){
		try {
            BufferedReader input  = new BufferedReader(new FileReader(fileName));
            String fileContents = new String();
            String line;
            while((line = input.readLine())!= null){
				fileContents = fileContents + line;
				}
			input.close();
			return fileContents;
		}catch(IOException e){
            System.out.println("Error reading file");
            System.out.println(e.getMessage());
            return null;
		}
    }
    
    int position = -1;
    public void setPosition(int pos){
    	position = pos;
    	System.out.println("Position: "+position);
    	}

} // end class asg01u_main












