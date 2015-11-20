package player2;

import processing.core.*;

public class EndPanel extends PApplet {
	
	PFont f;
	String message = "Unfortunately, earthling,"
			+ "you're destined to be a lone wanderer in the universe forever.";
	
	public void setup(){
		size(1000, 700);
		f = createFont("Comic Sans MS", 20, true);
	}
	
	public void draw(){
		
		frameRate(2);
		background(0);
		fill(255, 255, 0);
		textFont(f);
		textSize(60);
		text("YOU LOSE...", width/4, height/2);
		fill(255);
		//textFont(f);
		int x = 10;
		for(int i = 0; i < message.length(); i++){
			textSize(random(12, 36));
			if(i < 25){
				text(message.charAt(i), x + 30, 3*height/4);
			}else{
				text(message.charAt(i), x - 230, 3*height/4 + 50);
			}
			x += textWidth(message.charAt(i));
		}
		//noLoop();
		
	}
	

}
