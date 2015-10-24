package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Line;
import model.Oval;
import model.PaintObj;
import model.Pic;
import model.Rect;

/*
 * Author: Jared Worthington
 * cs 335
 * NetPaint Project
 * SL: Daniel
 * 
 * This class serves as the model for the drawing windows and scroll pane
 * 
 * 
 */
public class View extends JPanel{

	
	String shape;
	NetPaintGUI gui;
	Point points[] = new Point[2];
	private int currPoint = 1;
	private Image doge;
	private ArrayList<PaintObj> list = new ArrayList<PaintObj>();
	Color color;
	String paintShape;
	Point tempPoint;
	
	public View(){
		setBackground(Color.WHITE);
		addMouseListener(new MListener());
		color = Color.BLACK;
		shape = "line";
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		

		for(int i=0; i<list.size(); i=i+1){
			g.setColor(list.get(i).getColor());
			if(list.get(i).getType()=="line"){ 
				g.drawLine(list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getPoint1().x+list.get(i).getXS(), list.get(i).getPoint1().y+list.get(i).getYS() );
			}
			else if(list.get(i).getType()=="oval"){ 
				g.fillOval(list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getXS(), list.get(i).getYS() );
			}
			else if(list.get(i).getType()=="image"){
				ImageObserver observer = null;		
				g.drawImage(doge, list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getXS(), list.get(i).getYS() , observer);
			}
			else if(list.get(i).getType()=="rect"){
				g.fillRect(list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getXS(), list.get(i).getYS()  );
			}
		}
	}
	
	private class MListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
		
			System.out.println(color);
			points[currPoint-1] = e.getPoint();
			if(currPoint == 2){
				currPoint = 1;
				if(shape=="line"){
					list.add(new Line(points[0], (points[1].x - points[0].x), (points[1].y - points[0].y), color, "line"));
				}
				else if(shape=="oval"){
					list.add(new Oval(points[0], (points[1].x - points[0].x), points[1].y - points[0].y, color, "oval"));
				}
				else if(shape=="rect"){
					list.add(new Rect(points[0], (points[1].x - points[0].x), points[1].y - points[0].y, color, "rect"));
				}
				else if(shape == "image"){
					list.add(new Pic(points[0], (points[1].x - points[0].x), points[1].y - points[0].y, color, "image"));
				}
				
			}
			else{
				currPoint = currPoint+1;
				
			}
			
			repaint();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
	
	
}
