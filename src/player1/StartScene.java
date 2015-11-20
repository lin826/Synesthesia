package player1;

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

public class StartScene extends JPanel implements ActionListener, KeyListener{
	
	private Rectangle bounds = new Rectangle(1000, 700);
	private static boolean submitFlag = false;
	private BufferedImage background, img1, img2;
	private ImageIcon buttonIcon;
	private ImageIcon submitIcon;
	private JTextField sentenceField;
	private TextLabel label;
	private JButton submitButton;
	private String sentence;
	
	public StartScene(){
		
		this.setBounds(this.bounds);
		this.setLayout(null);
		try{
			background = ImageIO.read(new File("res/background/universe1.jpg"));
			img1 = ImageIO.read(new File("res/rocket/rocket_1a.png"));
			img2 = ImageIO.read(new File("res/rocket/rocket_1b.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		this.initTextBox();
		this.initSubmitButton();
		this.labelDrawAnimation();


	}
	
	public void initTextBox(){
		
		sentenceField = new JTextField();
		sentenceField.setBounds(250, 300, 500, 50);
		sentenceField.setFocusable(true);
		sentenceField.setEditable(true);
		Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
		sentenceField.setCursor(cursor);
		this.add(sentenceField);
		sentenceField.addKeyListener(this);
		
	}
	
	public void initSubmitButton(){
		
		//initialize the submit button.
		this.submitButton = new JButton();
		this.submitButton.setBounds(420, 400, 150, 150);
		//set the button to be transparent.
		this.submitButton.setOpaque(false);
		this.submitButton.setContentAreaFilled(false);
		this.submitButton.setBorderPainted(false);
		//resize images to get them fit into the button.
		Image newImg1 = img1.getScaledInstance(this.submitButton.getWidth(), this.submitButton.getHeight(), Image.SCALE_SMOOTH);
		Image newImg2 = img2.getScaledInstance(this.submitButton.getWidth(), this.submitButton.getHeight(), Image.SCALE_SMOOTH);
		this.buttonIcon = new ImageIcon(newImg1);
		this.submitIcon = new ImageIcon(newImg2);
		this.submitButton.setIcon(buttonIcon);
		//when the mouse hovers over the button and when the button is pressed and disabled, change the icon.
		this.submitButton.setRolloverIcon(submitIcon);
		this.submitButton.setPressedIcon(submitIcon);
		this.submitButton.setDisabledIcon(null);
		//setting a cursor when the mouse hovers over the button.
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		this.submitButton.setCursor(cursor);
		this.submitButton.addActionListener(this);
		this.add(this.submitButton);
		
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
	public String getSentence(){
		return sentence;
	}
	
	/*
	 * when the button is pressed, disable the button, set the text field not editable,
	 * and assign the String value inside the text field to the variable sentence.
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		System.out.println("the buttom was pressed");
		this.setSubmitFlag(true);
		this.submitButton.setEnabled(false);
		this.label.stopTimer();//when the sentence is submitted, stop the timer to end animation.
		this.sentenceField.setEditable(false);
		this.sentence = sentenceField.getText();
		
	}
	
	
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
	    label = new TextLabel();
	    label.setBounds(350, 250, 600, 100);
	    label.setVisible(true);
	    this.add(label);
	    
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()!=0){
			this.label.stopTimer();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()!=0){
			this.label.resume();
		}
		
	}
	
	

}
