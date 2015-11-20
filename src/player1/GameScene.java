package player1;

import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PImage;

public class GameScene extends PApplet{
	
	private PImage stageImg, gunImg;
	private Gun gun;
	private p1_Game parentFrame;
	private int r, g, b;
	private float x = 400;
	private float y = 100;
	private float plankW = 100;
	private float gX = 200;
	private boolean winFlag = false;
	private boolean loseFlag = false;
	private boolean isShooting;
	
	
	public GameScene(p1_Game parentFrame){
		this.parentFrame = parentFrame;

	}
	
	public void setup(){
		
		stageImg = loadImage(this.getClass().getResource("/res/background/stage1.jpg").getPath());
		gunImg = loadImage(this.getClass().getResource("/res/gun/gun1.gif").getPath());
		size(stageImg.width, stageImg.height);
		this.gun = new Gun(this, gunImg, 0, 0);
		
		
	}
	
	public void draw(){
		
		background(0);
		image(stageImg, 0, 0);
		if(!keyPressed){
			gun.setMovement(Gun.STAY);
		}
		gun.display();
		
		drawBall();
		drawPlank();
		
		if(this.hasLost()){
			this.parentFrame.gameOver();
		}
		else if(this.hasWon()){
			//this.parentFrame.win();
		}
		
	}
	
	/**
	 * drawing the ball.
	 */
	public void drawBall(){
		noStroke();
		fill(r, g, b);
		ellipse(x, y, 50, 50);
	}
	
	/**
	 * drawing the plank.
	 */
	public void drawPlank(){
		noStroke();
		fill(255);
		rect(gX, height - 100, plankW, 20);
	}
	
	/**
	 * handling the keyEvent.
	 */
    public void keyPressed(){
		
		if(keyCode == UP){
			gun.setMovement(Gun.UP);
		}
		else if(keyCode == DOWN){
			gun.setMovement(Gun.DOWN);
		}
		else if(keyCode == KeyEvent.VK_SPACE){
			drawLine(gun.getX(), gun.getY());
			setShooting(true);
		}
	}
    
    /**
     * a method responsible for drawing the ray.
     * @param x
     * @param y
     */
    public void drawLine(int x, int y){
    	    stroke(255, 255, 0);
    	    strokeWeight(4);
    	    line(x, y, width, y);
    }
    
    /**
     * a method to set the value of x, for synchronization with player 2.
     * @param x
     */
    public void setBallX(float x){
    	    this.x = x;
    }
    
    /**
     * a method to set the value of y, for synchronization with player 2.
     * @param y
     */
    public void setBallY(float y){
    	    this.y = y;
    }
    
    /**
     * a method to set the value of gX, for synchronization with player 2.
     * @param x
     */
    public void setPlankX(float x){
    	    this.gX = x;
    }
    
    public void setBallRGB(int r, int g, int b)
    {
    	this.r = r;
    	this.g = g;
    	this.b = b;
    }
	
    /**
     * a method to set the value of gY, for synchronization with player 2.
     * @return
     */
	public int getRayY(){
		setShooting(false);
		return this.gun.getY();
	}
	
	/**
	 * if the player 2 wins the game, set the winFlag true.
	 * @param set
	 */
	public void setWinFlag(boolean set){
		this.winFlag = set;
	}
	
	/**
	 * if the player 2 loses the game, set the loseFlag true.
	 * @param set
	 */
	public void setLoseFlag(boolean set){
		this.loseFlag = set;
	}
	
	public boolean hasWon(){
		return winFlag;
	}
	
	public boolean hasLost(){
		return loseFlag;
	}

	public boolean isShooting() {
		return isShooting;
	}

	public void setShooting(boolean isShooting) {
		this.isShooting = isShooting;
	}

}
