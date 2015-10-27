package model;

import java.awt.Color;
import java.awt.Point;

public abstract class PaintObj {

	Point p;
	int xsize;
	int ysize;
	Color color;
	String type;
	
	public PaintObj(Point a, int x, int y, Color c, String s){
		p=a;
		xsize=x;
		ysize=y;
		color = c;
		type = s;
	}

	public Point getPoint1() {
		// TODO Auto-generated method stub
		return p;
	}

	public Point getPoint2(){
		Point q = new Point(p.x+xsize, p.y+ysize);
		return q;
	}
	
	public String getType(){
		return(type);
	}
	
	public int getYS(){
		return(ysize);
	}
	public int getXS(){
		return(xsize);
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}
}


