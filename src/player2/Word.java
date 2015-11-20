package player2;

public class Word {
	
	String word;
	int[] rgb = new int[3];
	
	public Word(String word){
		super();
		this.word = word;
	}
	
	public void setWord(String str){
		this.word = str;
	}
	
	public void setRGB(int[] rgb){
		this.rgb = rgb;
	}
	
	public String getWord(){
		return this.word;
	}
	
	public int getR(){
		return this.rgb[0];
	}
	
	public int getG(){
		return this.rgb[1];
	}
	
	public int getB(){
		return this.rgb[2];
	}

}
