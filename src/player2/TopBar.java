package player2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TopBar extends JPanel{

	private Stack<JLabel> lives = new Stack<JLabel>();
	
	JLabel word=new JLabel();  
	
	JLabel life;
	static int lifecount;

	int count=0;
	private Image img;
	
	public TopBar(Rectangle bounds, int lifecount)  {
		this.setBounds(bounds);
		//this.setBackground(Color.BLUE);
		 this.setOpaque(true);
		try {
			this.img=new ImageIcon(ImageIO.read(new File(getClass().getResource("/res/OuterSpace.jpg").toURI()))).getImage();
		} catch (IOException | URISyntaxException e) {
			
			e.printStackTrace();
		}
		this.setLayout(null);
		
		this.lifecount=lifecount;
		
		//printWord();
		
		//printLife();
		

	}
	
	private void removeLife(){ //remove life
	
		JLabel removeLive=lives.pop();
		this.remove(removeLive);
		lifecount--;
	}
	
	private void printLife(){ //print life label
		this.removeAll();
		this.revalidate();
		try {
			for(int i=0;i<lifecount;i++){
				life = new JLabel(new ImageIcon(ImageIO.read(new File(getClass().getResource("/res/heart2.png").toURI()))));
				lives.push(life);
				this.add(life);
				life.setBounds(i*50,-5,50,50);
				
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private void printWord(){
	
		this.revalidate();
	
		
		
		//print title
		JLabel text = new JLabel("Topic : ");
		text.setForeground(Color.WHITE);
		
		//Font font1 = new Font(Font.DIALOG, Font.BOLD,20);
		//text.setFont(font1);
		text.setFont(new Font("Bradley Hand ITC", Font.BOLD,30));
		text.setBounds(600,0,700,40); //bound.width=700
		this.add(text);  
		
		
		//print word
		word.setText(p2_Game.getTopic());
		word.setForeground(Color.WHITE);
		//word.setFont(font1);
		word.setFont(new Font("Bradley Hand ITC", Font.BOLD,30));
		word.setBounds(725,0,700,40); //bound.width=700
        
		
		this.add(word); 
		
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		
			if(p2_Game.getrepaintTopbar()){
				System.out.println("Topbar "+lifecount);
				p2_Game.setrepaintTopbar(false);
				printLife();
				printWord();
				
			}
			 g.drawImage(img, 0, 0, null);
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		 //g.drawImage(img, 0, 0, null);
		
	}

	
	
}