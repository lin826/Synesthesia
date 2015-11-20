package player1;

import player2.Word;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class WinPanel extends PApplet {

	private PImage background;
	private PFont f;
	private String message = "congrats";
	private int[] rgb = { 255, 255, 255 };
	private Word[] words;
	int index = 0;

	WinPanel(String[][] topicdata) { // set Word[]

		words = new Word[topicdata.length];

		for (int i = 0; i < topicdata.length; i++) {
			words[i] = new Word(topicdata[i][0]);
			int[] temp = new int[3];
			if (topicdata[i][1] != null) {
				temp[0] = Integer.parseInt(topicdata[i][1]);
				temp[1] = Integer.parseInt(topicdata[i][2]);
				temp[2] = Integer.parseInt(topicdata[i][3]);
				words[i].setRGB(temp);
			} else {
				words[i].setRGB(rgb);
			}

		}
	}

	public void setup() {
		size(1000, 700);
		background = loadImage(this.getClass()
				.getResource("/res/background/stage2.png").getPath());
		f = createFont("Comic Sans MS", 20, true);
		textFont(f);

	}

	public void draw() {

		image(background, 0, 0);
		int x = 10;
		if (words.length > 0) {
			for (int i = 0; i < words.length; i++) {
				fill(words[i].getR(), words[i].getG(), words[i].getB());
				text(words[i].getWord(), x, height / 2);
				x += textWidth(words[i].getWord());
			}
		}

	}

	public void setMessage(String message, int[] rgb) {

		words[index].setWord(message);
		words[index].setRGB(rgb);
		index++;

	}

}
