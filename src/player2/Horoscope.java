package player2;

import processing.core.PApplet;
import processing.core.PImage;

public class Horoscope {
	
	private PApplet parent;
	private PImage image;
	private int x, y, w, h;
	
	public Horoscope(PApplet parent, PImage image, int x){
		
		this.parent = parent;
		this.image = image;
		this.x = x;
		this.y = 0;
		this.w = image.width;
		this.h = image.height;

	}
	public void display(){
		this.x -= 2;
		this.parent.image(this.image, this.x, this.y, this.w, this.h);
	}
	public int getColor(){
		return parent.get(this.x+this.w/2, this.y+this.h/2);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getW(){
		return this.w;
	}
	
}
