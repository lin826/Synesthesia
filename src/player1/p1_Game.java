package player1;

import java.awt.Rectangle;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JFrame;

public class p1_Game extends JFrame implements Runnable{
	
	private Rectangle bounds = new Rectangle(1000, 700);
	public StartScene startScene = new StartScene();
	public GameScene gameScene = null;
	private Thread gameThread = null;
	private EndPanel endPanel;
	private WinPanel winPanel;
	public String[][] topicData;
	private final static int DELAY = 20;
	
	
	public p1_Game(){
		super();
		this.setTitle("Synesthesia");
		this.setVisible(true);
		this.setBounds(this.bounds);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(startScene);
		
	}
	
	public void start() {
		if (this.getGameThread() == null) {
            this.setGameThread(new Thread(this));
        }
		this.getGameThread().start();
		this.setVisible(true);
	}
	
	/**
	 * a method invoked when the player 2 loses the game.
	 */
	public void gameOver(){
		this.remove(this.gameScene);
		this.gameScene.destroy();
		this.endPanel = new EndPanel();
		this.endPanel.init();
		this.endPanel.start();
		this.add(this.endPanel);
		this.setVisible(true);
		
	}
	
	public void win(String[][] topicData){		
		this.remove(this.gameScene);
		//this.gameScene.destroy();
		
		if(this.winPanel==null){
			System.out.println("Win");
		this.winPanel = new WinPanel(topicData);
		
		this.winPanel.init();
		this.winPanel.start();
		
		this.add(this.winPanel);
		this.setVisible(true);
		}
	}
	
	
	@Override
	public void run(){
		long t = System.currentTimeMillis();
		while (Thread.currentThread() == this.getGameThread()) {
            try {
            	while(this.startScene.hasSubmitted() && this.winPanel==null){
            		if (this.gameScene == null) {
            			System.out.println("Get a New Game Scene\n");
                        this.gameScene = new GameScene(this);
                        this.remove(startScene);
                		this.removeKeyListener(startScene);
                    }
            		else if(!gameScene.hasWon()&&!gameScene.hasLost()){
            			Thread.currentThread().interrupt();
            			this.gameScene.init();
            			this.gameScene.start();
            			this.add(gameScene);
            			this.repaint();
            			return;
            		}
            		/*else if(this.gameScene.hasWon()){
            			System.out.println("this.gameScene.hasWon()");
            			this.win(topicData);
            			
            			break;
            		}*/
            		else if(this.gameScene.hasLost()){
            			this.gameOver();
            		}
            		
            	}
            
                this.repaint();
                t += DELAY;
                Thread.sleep(Math.max(0, t - System.currentTimeMillis()));
            }
            catch (InterruptedException e) {
            	break;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
        }
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}


}
