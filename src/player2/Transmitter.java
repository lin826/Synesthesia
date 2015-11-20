package player2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.Random;

import java.util.Queue;

import javax.swing.text.html.HTMLDocument.Iterator;

import processing.core.PApplet;
import processing.core.PImage;


public class Transmitter extends PApplet {
	
	private p2_Game parentFrame;
	
	private Queue<Integer> ball = new ArrayDeque<Integer>();
	Random random = new Random();
	int result[] = new int[]{1,2,3,4,5,6,7}; //mean kind of color  
	  
	int r,g,b;
	int[] rgb=new int[3];//store current playball color
	
	PImage backgroundImg;// 1/6 new add
	
	Transmitter(p2_Game parentFrame){
		this.parentFrame = parentFrame;
		
		//1/6 add
		this.backgroundImg = loadImage(this.getClass().getResource("/res/OuterSpace.jpg").getPath());
		
		 for(int i=0; i < result.length/2; i ++){ //random color order
		     int index = random.nextInt(7);
		     int tmp = result[index];
		     result[index] = result[i];
		     result[i] = tmp;
		   }
		 
		 for(int i=0; i < result.length; i ++){ //random color order
			 ball.add(result[i]);
		 }
		 
		//System.out.println("Transmitter");
	}
	
	public void setup(){
		
		size(60,700);
		smooth();
		
		 /*//for test
		 for(int i=0; i < result.length; i ++){ //random color order
			 //System.out.println(result[i]);
		 }
		 */
		
		   
	}
	
	public void draw(){
		
		background(220,0);
		
		//1/6 add
		image(this.backgroundImg, 0, 0, this.backgroundImg.width, this.backgroundImg.height);
		
		//line(x1, y1, x2, y2);
		//line(frameRate, frameRate, frameRate, frameRate);
		printBall();
	      
	}
	
	public void printBall(){
		java.util.Iterator<Integer> itr = ball.iterator();
		int i=1;
		while(itr.hasNext()) {
	         
			 checkValue(itr.next());
	         i++;
	         stroke(r+10,g+10,b+10); 
	         fill(r,g,b);
	         ellipse(30,height-i*65,50,50);//x,y,r,r;
	       
	    }
		
	}
	
	public int[] getRGBValue(){
		//checkValue(ball.peek());
		/*
		rgb[0]=r;
		rgb[1]=g;
		rgb[2]=b;
		*/
		return rgb;
	}
	
	public void removeBall(){ //remove ball
		
		if(ball.size()!=0){
			
			checkValue(ball.poll());
			rgb[0]=r;
			rgb[1]=g;
			rgb[2]=b;
			//System.out.println(r+"  "+g+"  "+b+"  ");
		}
		
		
	}
	public boolean isBallEmpty(){
		if(this.ball.size()!=0 && this.ball!=null)
			return false;
		return true;
	}
	public void checkValue(int i){
		
		switch(i){
        case 1://orange
      	  r=255; g=125;b=0;
      	  //System.out.println("1");
      	  break;
        case 2://yellow 
      	  r=255; g=225;b=0;
      	  //System.out.println("2");
      	  break;
        case 3://green  
      	  r=0; g=255;b=0;
      	  //System.out.println("3");
      	  break;
        case 4://blue  
      	  r=0; g=0;b=255;
      	  //System.out.println("4");
      	  break;
        case 5://indigo  
      	  r=0; g=255;b=255;
      	  //System.out.println("5");
      	  break;
        case 6://purple  
      	  r=255; g=0;b=255;
      	  //System.out.println("6");
      	  break;
        default:
      	  r=255; g=0;b=0;
      	  //System.out.println("0");
      	  break;
      	  
       }
		
	}
	 
}
/*

 * ¬õ¡G255¡A0¡A0 
¡@¡@¾í: 255,125,0
¡@¡@¶À¡G255¡A255¡A0 
¡@¡@ºñ¡G0¡A255¡A0 
¡@¡@ÂÅ¡G0¡A0¡A255 
¡@¡@ÀQ: 0,255,255
¡@¡@µµ: 255,0,255
 * 
 */


