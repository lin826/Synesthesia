package connection;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import connection.TxtLabel;

public class Waiting  extends JPanel {
	
	private Rectangle bounds = new Rectangle(1000, 700);
	private static boolean submitFlag = false;
	private BufferedImage background, img1, img2;
	//private JTextField sentenceField;
	private TxtLabel label;
	
	public Waiting(){
		
		this.setBounds(this.bounds);
		this.setLayout(null);
		try{
			background = ImageIO.read(new File("res/background/universe1.jpg"));
			img1 = ImageIO.read(new File("res/rocket/rocket_1a.png"));
			img2 = ImageIO.read(new File("res/rocket/rocket_1b.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		this.labelDrawAnimation();


	}
	
	
	public void setSubmitFlag(boolean set){
		submitFlag = set;
	}
	
	public boolean hasSubmitted(){
		//System.out.println(submitFlag);
		return submitFlag;
	}
	
	/**
	 * an accessor method to get the sentence.
	 * @return sentence
	 */
	
	/*
	 * when the button is pressed, disable the button, set the text field not editable,
	 * and assign the String value inside the text field to the variable sentence.
	 */

	
	
	@Override
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}
	
	/**
	 * a method in charge of the text animation.
	 */
	public void labelDrawAnimation()
	{
	    label = new TxtLabel();
	    label.setBounds(350, 250, 600, 100);
	    label.setVisible(true);
	    this.add(label);	  
	    
	}

}
