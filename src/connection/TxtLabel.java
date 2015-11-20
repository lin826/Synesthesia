package connection;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/*
 * 
 */

public class TxtLabel extends JLabel{
	
	private String s = " ";
	private String welcome;
	private String[] welcomeArray;
	private int index = 0;//index of the array
	private int x = 10;
	private int delay = 200;
	private Timer timer;
	
	public TxtLabel(){
		
		welcome = "waiting.......";
		
		welcomeArray = welcome.split("(?!^)");
		ActionListener counter = new ActionListener() {
			public void actionPerformed(ActionEvent evt) 
			{ 
			      repaint();
			      //index++;
			}};
		 timer = new Timer(delay, counter);
		 timer.start();
		
	}
	
	public void paintComponent(Graphics g)
    {
		
      if(index < welcomeArray.length)
      {
    	      g.setFont(new Font("Comic Sans MS", Font.LAYOUT_LEFT_TO_RIGHT, 20));
    	      g.setColor(Color.BLACK);
	      g.drawString(s + welcomeArray[index],x,20);
	      s = s + welcomeArray[index];
	      index++;
      }else{
    	      restart();
      }
    }
	
	public void restart(){
		index = 0;
		s = "";
	}
	
	public void stopTimer(){
		timer.stop();
	}
	
	public void resume(){
		restart();
		timer.restart();
	}


}

