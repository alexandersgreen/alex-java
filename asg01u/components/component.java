package asg01u.components;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.net.*;
import java.io.*;
import asg01u.listener.*;

public abstract class component extends JPanel implements Serializable{
	
	public component(){}
	
	public abstract String outputString();
	
	
}