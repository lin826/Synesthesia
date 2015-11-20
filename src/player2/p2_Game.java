package player2;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class p2_Game extends JFrame implements Runnable {
	
	private static int STAGE_AMOUNT = 3; //設定關卡數量  依照傳入字數  e.g. I love you.
	private Rectangle bounds = new Rectangle(1000,700);
	private int maxLife = 3; 
	private TopBar topbar = new TopBar(new Rectangle(bounds.width, 40), maxLife);
	private Thread gameThread = null;
	private final static int DELAY = 200;
	
	public GameStage currentStage = null;
	
	public static boolean repaintTopbar=false; //是否要重繪 topbar
	
	private Queue<GameStage> stages= new ArrayDeque<GameStage>();
	public static Queue<String> topic= new ArrayDeque<String>();
	
	private static String[][] topicData;//store word & color
	
	private Transmitter currentTrans;
	private Queue<Transmitter> transmitters= new ArrayDeque<Transmitter>();
	
	private int currentLevel;
	private int currentLive;
	private static String currentTopic;
	
	private EndPanel endPanel;
	private WinPanel winPanel;
	
	private boolean winFlag = false;
	private boolean loseFlag = false;
	
	public p2_Game() { //init
		super();
		/*
		BufferedImage myImage;
		try {
			myImage = ImageIO.read(new File(getClass().getResource("/res/OuterSpace.jpg").toURI()));
			this.setContentPane(new ImagePanel(myImage));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		*/
		
		this.setBounds(this.bounds);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.produceStages(STAGE_AMOUNT);
		//this.produceTrans(STAGE_AMOUNT);
		this.setVisible(true);
		
		//String[] testdata={"I","love","you"};//test data
		//setTopic(testdata);
		
		this.topbar.setBounds(0,10,bounds.width,40); //設定Topbar的 Bounds
		this.add(topbar);

		currentLevel=1; //first Stage
		currentLive=maxLife;
	
		
	}
	
	
	
	
	public void start() {
		
		
		
		/*
		this.transmitter = new Transmitter(this);
		this.transmitter.init();
		this.transmitter.start();
		
		
		this.add(this.transmitter);
		*/
		while(topic.isEmpty());
		this.nextTrans();
		this.nextStage();
		
		
		
		if (this.gameThread == null) {
            this.gameThread = new Thread(this);
        }
		this.gameThread.start(); 
	}
	
	public void setTopic(String data[]){ //set Topic
		topicData=new String[data.length][4];//0 : word , 1:color(RGB)
		for(int i=0;i<data.length;i++){
			topicData[i][0]=data[i];
			System.out.println("topicData["+i+"][0]: "+data[i]);
		}
		shuffleArray(data); //random array
		
		for(int i=0;i<data.length;i++){ //put word in topic
			topic.add(data[i]);
		}
		
		
	}
	
	//shuffle array
	public static void shuffleArray(String[] ar)
	{
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	    
	      String temp = ar[index];
	      ar[index] = ar[i];
	      ar[i] = temp;
	    }
	  }
	
	public static String getTopic(){
		currentTopic=topic.poll();
		return currentTopic;
	}
	
	public void setTopicRGB(int[] rgb){
		
		for(int i=0;i<topicData.length;i++){
			if(topicData[i][0]==this.currentTopic){
				topicData[i][1]=String.valueOf(rgb[0]);
				topicData[i][2]=String.valueOf(rgb[1]);
				topicData[i][3]=String.valueOf(rgb[2]);
				//System.out.println(topicData[i][0]+" "+" "+topicData[i][1]+" "+topicData[i][2]+" "+topicData[i][3]);
			}
			
		}
	}
	
	public void setStageAmount(int num){
		this.STAGE_AMOUNT=num;
	}
	
	//set 是否需要repaintTopbar
	public static void setrepaintTopbar(boolean s){
		repaintTopbar=s;
	}
	
	//get 是否需 repaint Topbar
	public static boolean getrepaintTopbar(){
		return repaintTopbar;
	}


	private void nextTrans() {
			
			if(this.transmitters.size()!=0){
				if(this.currentTrans!=null){
					this.remove(this.currentTrans);
					this.currentTrans.destroy();
				}
				
				this.currentTrans = this.transmitters.poll();//Setting the currentStage
				
				this.currentTrans.init();
				this.currentTrans.start();
				this.add(this.currentTrans);
				
			}
			
			
	}
	//進下一關
	private void nextStage() {
		
		if(this.stages.size()!=0){
			if(this.getCurrentStage() != null){
				this.remove(this.currentStage);
				this.currentStage.destroy();
			}
			
			this.setCurrentStage(this.stages.poll());//Setting the currentStage
			
			this.getCurrentStage().setBounds(60,0,bounds.width,bounds.height);
			//this.revalidate();
			//this.currentStage.requestFocus();
			
			this.getCurrentStage().init();
			this.getCurrentStage().start();
			this.add(this.getCurrentStage());
			this.repaintTopbar=false;
		}
		
		
	}
	
	public void produceTrans(int num) { 
		for(int i=0;i<num;i++){
			transmitters.offer(new Transmitter(this));
		}
	}
	
	public void produceStages(int num) { 
		for(int i=0;i<num;i++){
			stages.offer(new GameStage(this,num,bounds));
		}
	}
	
	public int[] getRGBValue(){//Transmitter
		this.currentTrans.removeBall();
		return this.currentTrans.getRGBValue();
	}
	
	public String[][] getTopicRGB(){
		
		return topicData;
	}
	
	public boolean isBallEmpty() {
		return this.currentTrans.isBallEmpty();
		
	}
	
	
	
	public void gameOver(){
		if(this.endPanel==null){
		this.loseFlag = true;
		this.endPanel = new EndPanel();
		this.endPanel.init();
		this.endPanel.start();
		this.add(this.endPanel);
		this.setVisible(true);
		}
		
	}
	
	public void win(){
		if(this.winPanel==null){
			this.winFlag = true;
			System.out.println("Win");
		this.winPanel = new WinPanel(topicData);
		
		this.winPanel.init();
		this.winPanel.start();
		
		this.add(this.winPanel);
		this.setVisible(true);
		}
	}
	
	@Override
	public void run() {
		
		long t = System.currentTimeMillis();
		while (Thread.currentThread() == this.gameThread) {
			
            try {
            	
            	//Win
            	if(this.getCurrentStage().isWin()&&currentLevel<=STAGE_AMOUNT){
            		
            			//get rgb 
            			int[] rgbOK=this.getCurrentStage().getOKRGB();
            			setTopicRGB(rgbOK);//store user pick color
            			
            		
            			this.nextTrans();
                		this.nextStage();
                		
                		currentLevel++;

                		
                		setrepaintTopbar(true);
                		
                    	this.topbar.revalidate();
                    	this.topbar.repaint();
                    	
                    	
                    	//this.currentStage. //run animation
                    	System.out.println("WinStage");
                    	
                   
            	}else if(this.getCurrentStage().isLose()&&currentLevel<=STAGE_AMOUNT){//Lose
            		
            			this.currentLive--;
            			this.topbar.lifecount--;
            			
            			this.nextTrans();
                		this.nextStage();
                		
                		//for punish  //player's receive width reduce 
                		//plankw-=10;
                		//this.currentStage.setplankW(plankw);
                		
                		currentLevel++;
                		
                		setrepaintTopbar(true);
                		
                    	this.topbar.revalidate();
                    	this.topbar.repaint();
                    	
                    	//this.currentStage. //run animation
                    	System.out.println("LoseStage");
                    	
            	}else if(this.currentLive==0){   //Game over
            		
            		this.remove(this.currentStage);
            		this.remove(this.currentTrans);
            		this.remove(this.topbar);
            		this.currentStage.destroy();
            		this.currentTrans.destroy();
            		
            		this.topbar.removeAll();
                	this.topbar.revalidate();
                	this.topbar.repaint();
                	
                	gameOver();
            		
            	}else if(currentLevel>STAGE_AMOUNT){//Finish
            		
            		this.remove(this.getCurrentStage());
            		this.remove(this.currentTrans);
            		this.remove(this.topbar);
            		this.currentStage.destroy();
            		this.currentTrans.destroy();
            		
            		this.topbar.removeAll();
                	this.topbar.revalidate();
                	this.topbar.repaint();               	
                	
                	win();
            	}
            	
            	this.repaint();
           
                t += DELAY;
                Thread.sleep(Math.max(0, t - System.currentTimeMillis()));
            }
            catch (InterruptedException e) {
            	e.printStackTrace();
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
            
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
       
	}




	public GameStage getCurrentStage() {
		return currentStage;
	}




	public void setCurrentStage(GameStage currentStage) {
		this.currentStage = currentStage;
	}




	public boolean hasWon() {
		// TODO Auto-generated method stub
		return winFlag;
	}




	public boolean hasLost() {
		// TODO Auto-generated method stub
		return loseFlag;
	}
}