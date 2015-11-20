package player2;

import processing.core.PApplet;
import processing.core.PImage;

public class Spaceship  {
	
	private GameStage parent;
	private PImage[] images;
	private int x, y, w, h;

	public Spaceship(GameStage parent){
		
		this.parent = parent;
		this.images = new PImage[5];
		this.images[0] = parent.loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_0.png").getPath());
		this.images[1] = parent.loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_opened.png").getPath());
		this.images[2] = parent.loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_1.png").getPath());
		this.images[3] = parent.loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_2.png").getPath());
		this.images[4] = parent.loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_3.png").getPath());

		
		this.x = 500;
		this.y = 100;
		
	}


	
	
	public void display(){
		/*
		for(int i = 0; i < 5; i++){
			this.parent.image(this.images[0], this.x, this.y, this.w, this.h);		
		}
		*/
		this.parent.image(this.images[0], this.x, this.y, this.w, this.h);		
		System.out.println("test ship");

		
	}
	
}
