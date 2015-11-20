package player2;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Queue;


import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class GameStage extends PApplet {

	private boolean winFlag=false;

	private int currentLevel;
	private p2_Game parentFrame;
	
	private boolean point=false; //若不想接那顏色的球就可不要接 
	private int ballCount=8; //fix ball count
	
	private int r,g,b;
	private int[] okRGB; 
	
	private boolean flagAnim=false;//animation
	private int xA=350;//animation x
	
	private PImage[] testImgs;
	private Queue<Horoscope> backgrounds;
	
	public Ball ball;
	private Plank plank;
	private int rHNumber;
	
	private PImage[] images;
	
	private float spaceshipX=750;
	private float spaceshipY=100;
	private int sNum=-1;
	
	private boolean isShoot = false;
	private int lY;
	
	GameStage(p2_Game parentFrame,int level,Rectangle bound){
	
		this.currentLevel=level;
		this.width=bound.width;
		this.height=bound.height-300;
		
		this.parentFrame=parentFrame;
		okRGB=new int[3];
		
		//random picNumber
		Random random = new Random();
		this.rHNumber = random.nextInt(12);//random.nextInt(12);
	
		this.testImgs = new PImage[2];
		checkPic(this.rHNumber);//set this.testImgs
		
		ball = new Ball();
		setPlank(new Plank(height));
		
	}
	
	

	public void setRGB(){//get new ball's color
		int[] rgb=this.parentFrame.getRGBValue();
		r=rgb[0];
		g=rgb[1];
		b=rgb[2];
	}
	
	public void setup(){
		
		size(this.width,this.height);
		smooth();
		setRGB();
		
		ballCount--;
		
		this.backgrounds = new LinkedList<Horoscope>();
		
		/*
		Random random = new Random();
		this.rHNumber = random.nextInt(12);
		*/
		this.backgrounds.offer(new Horoscope(this, this.testImgs[0], 0));
		for(int i = 1; i < 30; i++ ){
			this.backgrounds.offer(new Horoscope(this, this.testImgs[1], i * this.testImgs[0].width ));
		}
		
		this.images = new PImage[5];
		this.images[0] = loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_0.png").getPath());
		this.images[1] = loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_opened.png").getPath());
		this.images[2] = loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_1.png").getPath());
		this.images[3] = loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_2.png").getPath());
		this.images[4] = loadImage(this.getClass().getResource("/res/SpaceshipImgs/spaceship_3.png").getPath());
	}
	
	
	public void draw(){
		/*
		int time = millis();
		if(time % 100 == 0)
			this.sNum += 1;
		*/
		
		
		if(!flagAnim){//animation
			doAnimation();
		}
		else{
			delay(35); //delay time
		
			if(keyPressed && keyCode == LEFT){
				getPlank().setgX(-5);//plank
			}
			if(keyPressed && keyCode==RIGHT){
				getPlank().setgX(5);//plank
			}
	
			if(!winFlag){
			
				//12/30 更動 width 變成 width-250
				if(ball.getBallX() > width-250){//origin width //if over width-250 then consider isEnter the spaceship 
					float distance = abs(750 - ball.getBallX());//assume spaceship's position x at 750
				
					if (distance < 50){ //enter spaceship //origin 255
						winFlag=true;
						okRGB[0]=r; //set okRGB
						okRGB[1]=g;
						okRGB[2]=b;
					
					
					}
				}
				if(ball.getBallY() > height-120 && ballCount!=0){ //over player2's height then check isGetBall
					float distance = abs((getPlank().getGX()+getPlank().getplankW()/2) - ball.getBallX());//gX+50 is  player2's position x center
					if (distance < 50){//1/8 change
						ball.setSpeedY(-ball.getSpeedY());
						point=true; //mean get ball at least once
					
					}else{
					
						ballCount--; 
				
						setRGB();//get New color & set ball color
					
						//reset value
						ball.initial();
					}
				
				}
				else{ 
					ball.setSpeedY(ball.getSpeedY()+1);//for show parabola 
					 
				}
			
				//ball move 
				ball.setBallX(ball.getSpeedX());
				ball.setBallY(ball.getSpeedY());
				
			
			}
			
		
		
			  
			//background(255,600);
			
			for(Horoscope background: this.backgrounds)
				background.display();
			
			if(this.ball.getBallX()>580){//show spaceship 
				if(sNum==-1){
					sNum++;
				}
				else{
					if(this.ball.getBallX()%40==0)
					sNum=(sNum+1)%5;
				}
					
				System.out.println(sNum);
				image(this.images[sNum],spaceshipX,spaceshipY, 200, 200);
				
				
			}
			if(this.isShoot() ){
				float distance = abs(this.getlY() - this.ball.getBallY());
				if(distance < 20){	
					this.setShoot(false);
					Random random = new Random();
					this.ball.setBallX(random.nextInt((int)(this.width - this.spaceshipX) +60));
				}
				drawLine(this.getlY());
				this.setShoot(false);
				
			}
		 
			
			//ball
			fill(r,g,b); 
			stroke(r,g,b);
			ellipse(ball.getBallX(), ball.getBallY(), 50, 50);
		
			//player2
			fill(0);
			stroke(255);
			rect(getPlank().getGX(),height-100,getPlank().getplankW(),20);
		}
		
	}
	
    public void drawLine(int y){
	    stroke(255, 255, 0);
	    strokeWeight(4);
	    line(this.ball.getBallX() -10, y, width, y);
}
	
	private void doAnimation() {
		background(255,600);
		//1/6 add test
		for(Horoscope background: this.backgrounds)
			background.display();
		
		
		textSize(100);
		textAlign(CENTER);
		fill(0, 0, 255);
		text("Ready Go!!!",xA,400);
		xA++;
		if(xA>400){
			flagAnim=true;
		}
		
	}

	public void delay(int delay)
	{
	  int time = millis();
	  while(millis() - time <= delay);
	}
	
	public boolean isWin(){
		
		return winFlag;
		
	}
	
	public boolean isLose() {
		if(ballCount==0 && winFlag==false){
			return true; 
		}
		return false;
	}

	
	
	public int getCurrentLevel(){
		return this.currentLevel;
	}
	
	public int[] getRGB(){ //get current ball's color
		//System.out.println("RGB OK "+r+" "+g+" "+b);
		int[] rgb=new int[3];
		rgb[0]=r;
		rgb[1]=g;
		rgb[2]=b;
		return rgb;
	}
	
	public int[] getOKRGB(){//get Win ball's color
		return okRGB;
		
	}
	

	
	private void checkPic(int num) {
		if(num==0){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius_1.png").getPath());
			
		}else if(num==1){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/pisces.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/pisces_1.png").getPath());
			
		}else if(num==2){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aries.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aries_1.png").getPath());
			
		}else if(num==3){//test
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius_1.png").getPath());
			
		}else if(num==4){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/gemini.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/gemini_1.png").getPath());
			
		}else if(num==5){//test
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius_1.png").getPath());
			
		}else if(num==6){//test
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/aquarius_1.png").getPath());
			
		}else if(num==7){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/virgo.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/virgo_1.png").getPath());
			
		}else if(num==8){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/libra.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/libra_1.png").getPath());
			
		}else if(num==9){
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/scorpio.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/scorpio_1.png").getPath());
			
			
		}else if(num==10){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/pisces.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/pisces_1.png").getPath());
			
		}else if(num==11){
			
			this.testImgs[0] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/capricon.png").getPath());
			this.testImgs[1] = loadImage(this.getClass().getResource("/res/HoroscopeImgs/capricon_1.png").getPath());
			
		}
		
	}



	public boolean isShoot() {
		return isShoot;
	}



	public void setShoot(boolean isShoot) {
		this.isShoot = isShoot;
	}



	public int getlY() {
		return lY;
	}



	public void setlY(int lY) {
		this.lY = lY;
	}


	public Plank getPlank() {
		return plank;
	}



	public void setPlank(Plank plank) {
		this.plank = plank;
	}
	
}
