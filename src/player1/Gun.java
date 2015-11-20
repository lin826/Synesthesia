package player1;

import processing.core.PApplet;
import processing.core.PImage;

public class Gun {
	
	public static final int STAY = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int FIRE = 3;
	private PApplet parent;
	private PImage image;
	private int x, y, movement;
	
	public Gun(PApplet parent, PImage image, int x, int y){
		this.parent = parent;
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public void display(){
		
		switch(this.movement){
		
		case UP:
			if(this.y > 0)
				this.y -= 10;
			break;
			
		case DOWN:
			if(this.y < this.parent.height - 100)
				this.y += 10;
			break;
			
			
		}
		parent.image(image, x, y);
		
	}
	
	public void setMovement(int m){
		this.movement = m;
	}
	
	public int getX(){
		return x + 100;
	}
	
	public int getY(){
		return y + 12;
	}

}
