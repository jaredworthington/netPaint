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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

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
public class View extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	String shape;
	NetPaintGUI gui;
	Point points[] = new Point[2];
	private int currPoint = 1;
	private Image image;
	Vector<PaintObj> list = new Vector<PaintObj>();
	Color color;
	String paintShape;
	Point tempPoint;
	MListener ml; 
	
	public View(){
		setBackground(Color.WHITE);
		ml = new MListener();
		addMouseListener(ml);
		addMouseMotionListener(ml);
		color = Color.BLACK;
		shape = "line";
		try {
			image = ImageIO.read(new File("doge.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void callPaintComponent(){
		Graphics g;
		g=getGraphics();
		paintComponent(g);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
	
		for(int i=0; i<list.size(); i=i+1){
			System.out.println("ENTERED PRINT METHOD");
			System.out.println(list.get(i).getType());
			g.setColor(list.get(i).getColor());
			if(list.get(i).getType().equals("line")){ 
				System.out.println("printing line");
				g.drawLine(list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getPoint1().x+list.get(i).getXS(), list.get(i).getPoint1().y+list.get(i).getYS() );
			}
			else if(list.get(i).getType().equals("oval")){ 
				g.fillOval(list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getXS(), list.get(i).getYS() );
			}
			else if(list.get(i).getType().equals("image")){
				ImageObserver observer = null;		
				g.drawImage(image, list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getXS(), list.get(i).getYS() , observer);
			}
			else if(list.get(i).getType().equals("rect")){
				g.fillRect(list.get(i).getPoint1().x, list.get(i).getPoint1().y, list.get(i).getXS(), list.get(i).getYS()  );
			}
		}
		//repeat for the shadow object
		if(currPoint==2){
			g.setColor(color);
			if(shape=="line"){ 
				g.drawLine((int) points[0].getX(), (int) points[0].getY(), (int) points[1].getX(), (int) points[1].getY() );
			}
			else if(shape=="oval"){ 
				g.fillOval((int) points[0].getX(), (int) points[0].getY(), (int)( points[1].getX()-points[0].getX()), (int) (points[1].getY()-points[0].getY()));
			}
			else if(shape=="image"){		
				g.drawImage(image,(int) points[0].getX(), (int) points[0].getY(), (int)( points[1].getX()-points[0].getX()), (int) (points[1].getY()-points[0].getY()), null); 
			}
			else if(shape=="rect"){
				g.fillRect((int) points[0].getX(), (int) points[0].getY(), (int)( points[1].getX()-points[0].getX()), (int) (points[1].getY()-points[0].getY())); 
			}
		}
	}
	
	
	public class MListener extends Observable implements MouseListener, MouseMotionListener{
		
		PaintObj obj;
		@Override
		public void mouseClicked(MouseEvent e) {
		
			points[currPoint-1] = e.getPoint();
			if(currPoint == 2){
				currPoint = 1;
				if(shape=="line"){
					obj = new Line(points[0], (points[1].x - points[0].x), (points[1].y - points[0].y), color, "line");
				}
				else if(shape=="oval"){
					obj = new Oval(points[0], (points[1].x - points[0].x), points[1].y - points[0].y, color, "oval");
				}
				else if(shape=="rect"){
					obj = new Rect(points[0], (points[1].x - points[0].x), points[1].y - points[0].y, color, "rect");
				}
				else if(shape == "image"){
					obj = new Pic(points[0], (points[1].x - points[0].x), points[1].y - points[0].y, color, "image");
				}
				
				this.setChanged();
				notifyObservers(obj);
			}
			else{
				currPoint = currPoint+1;
				points[1] = points[0];
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		
		}
		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		
			if(currPoint == 2){
				points[1] = e.getPoint();
				repaint();
			}
			
		}
		
	}
	
	
}
