package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import model.PaintObj;

/*
 * Author: Jared Worthington
 * cs 335
 * NetPaint Project
 * SL: Daniel
 * 
 * This class runs the user interface and all of the listeners for user interaction
 */
public class NetPaintGUI extends Observable{

	private View canvas = new View();
	private JScrollPane scrollWindow;
	private JPanel buttonPanel = new JPanel(new FlowLayout());
	private JButton colorButton;
	private JRadioButton lineButton;
	private JRadioButton rectButton;
	private JRadioButton imageButton;
	private JRadioButton ovalButton;
	private ButtonGroup buttGroup = new ButtonGroup();
	private Color color = new Color(111);
	JFrame window;
	private String shape;
	Image doge;
	
	public NetPaintGUI(){
	// Basics
		window = new JFrame();
	   window.setTitle("NetPaint");
	   window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   window.setSize(screenSize);
	   window.setLayout(new FlowLayout());
	   window.setVisible(true);
	   
	   
	   
	   setupGUI();
	   setupListeners();
	}
	
	public static void main(String args[]){
		new NetPaintGUI();
		
	}
	
	private void setupGUI(){
	
		//scroll window
		window.setLayout(null);
		canvas.setPreferredSize(new Dimension(2048, 1024));
		canvas.setLocation(10,10);
		canvas.setBackground(Color.WHITE);
		scrollWindow = new JScrollPane(canvas);
		scrollWindow.setSize(1550,650);
		scrollWindow.setLocation(30, 30);
		window.add(scrollWindow);
		
		//button Group
		lineButton = new JRadioButton("Line");
		lineButton.setSelected(true);
		shape = "line";
		rectButton = new JRadioButton("Rectangle");
		imageButton = new JRadioButton("Image");
		ovalButton = new JRadioButton("Oval");

		buttGroup.add(lineButton);
		buttGroup.add(rectButton);
		buttGroup.add(ovalButton);
		buttGroup.add(imageButton);
	
		buttonPanel.setSize(1550, 100);
		buttonPanel.setLocation(30, 700);
		//button panel
		colorButton = new JButton();
		colorButton.setText("Choose Color");
		colorButton.setSize(50,100);
		colorButton.setLocation(50,50);
	
		buttonPanel.add(colorButton);
		buttonPanel.add(lineButton);
		buttonPanel.add(rectButton);
		buttonPanel.add(ovalButton);
		buttonPanel.add(imageButton);
	
		window.add(buttonPanel);
	}  

	
	public Color getColor(){
		return color;
	}
	
	public View getCanvas(){
		return canvas;
	}
	private void setupListeners(){
		colorButton.addActionListener(new BListener());
		lineButton.addActionListener(new ShapeListener());
		rectButton.addActionListener(new ShapeListener());
		ovalButton.addActionListener(new ShapeListener());
		imageButton.addActionListener(new ShapeListener());
	}

	
	private class ShapeListener implements ActionListener{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
		
			if(lineButton.isSelected()){
				shape = "line";
			}
			else if(rectButton.isSelected()){
				shape = "rect";
			}
			else if(ovalButton.isSelected()){
				shape = "oval";
			}
			else if(imageButton.isSelected()){
				shape = "image";
			}
			
			
			canvas.shape = shape;
		}
		
	}
	
	private class BListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

		 canvas.color = JColorChooser.showDialog(null, "Choose a Color", color);
		}
		
	}
}