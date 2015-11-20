package player2;

public class Ball {
	
	private float x;
	private float y;
	private float speedX;
	private float speedY;
	
	Ball(){
		initial();
	}
	
	public void initial(){
		this.x=100;
		this.y=100;
		this.speedX = 2;
		this.speedY = 1;
	}
	
	public void setBallX(float x){ 
		
		this.x+=x;
	}
	
	public void setBallY(float y){ 
		
		this.y+=y;
	}
	
	public float getBallX(){ 
		
		return this.x;
	}
	
	public float getBallY(){ 
		
		return this.y;
	}
	
	public void setSpeedX(float speedX){
		this.speedX=speedX;
	}
	
	public void setSpeedY(float speedY){
		this.speedY=speedY;
	}
	
	public float getSpeedX(){
		return this.speedX;
	}
	
	public float getSpeedY(){
		return this.speedY;
	}
	

}
