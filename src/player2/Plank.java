package player2;

public class Plank {

	private float plankW; //store plank width  
	private float gX; //player2's location
	private float gY;
	
	Plank(float height){
		initial(height);
	}
	
	public void initial(float height){
		this.plankW=100;
		this.gX=50;
		this.gY=height-100;
	}
	
	
	public float getGX(){ //player2's x
		
		return this.gX;
	}
	
	public float getGY(){ //player2's y
		
		return this.gY;
	}
	
	public float getplankW(){
		return this.plankW;
	}
	
	public void setgX(float gX){
		this.gX+=gX;
	}
	
	public void setplankW(float plankW){
		this.plankW=plankW;
	}
}
