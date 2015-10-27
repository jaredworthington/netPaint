package model;

import java.awt.Color;
import java.awt.Point;

public class Pic extends PaintObj{

	String type;
	public Pic(Point a, int x, int y, Color c, String s) {
		super(a, x, y, c, s);
		type = s;
		// TODO Auto-generated constructor stub
	}
	
	public String getType(){
		return type;
	}
}
