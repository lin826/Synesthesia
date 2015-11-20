package connection;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import player1.p1_Game;
import player2.p2_Game;

public class PlayerServer extends JFrame{
	private ServerSocket serverSocket;
	private Socket serverToClient;
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	
	private JTextArea textArea;
	private String numOfPlayers = "0";
	private boolean isSetSentence = false;
	
	
	public PlayerServer(int portNum) {
		try {
			this.serverSocket = new ServerSocket(portNum);
			System.out.printf("Server starts listening on port: %d.\n", portNum);
			this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("RobotServer");
			
			// Initialize textArea
			this.textArea = new JTextArea();
			this.textArea.setEditable(false);
			this.textArea.setPreferredSize(new Dimension(500,300));
			Font font = new Font("Verdana", Font.PLAIN, 16);
		    this.textArea.setFont(font);
			
			JScrollPane scrollPane = new JScrollPane(this.textArea);
		    this.add(scrollPane);
		    
		    this.textArea.append("Server starts listening on port: "+portNum+".\n");
		    this.pack();
		    this.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login() {
		System.out.println("Server starts waiting for client.");
		// Create a loop to make server wait for client forever (unless you stop it)
		// Make sure you do create a connectionThread and add it into "connections"
		while(true)
		{
			try {
				int i = Integer.valueOf(numOfPlayers).intValue();
				i= i%2 + 1;
				numOfPlayers = String.valueOf(i); 
				System.out.println("numOfPlayers = "+numOfPlayers);
				serverToClient = this.serverSocket.accept();
				String temp =  "Get connection from client " + 
						serverToClient.getInetAddress() + ": " +
						serverToClient.getPort()+"\n";
				System.out.print(temp);
				ConnectionThread connThread = new ConnectionThread(serverToClient);
				this.connections.add(connThread);
				connThread.start();
				if(numOfPlayers.equals("2")) {
					this.broadcast("Game Ready!!");
					this.broadcast("Game Start!!");
					break;
				}
			} catch (BindException e){
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	class ConnectionThread extends Thread{
		
		private BufferedReader reader;
		private PrintWriter writer;
		//private String stage = "key_in_sentence";
		private int stage;
		String sentence;
		String[] cmd_i;

		public ConnectionThread(Socket socket) {
			try {
				stage = 1;
				this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				this.sendMessage(numOfPlayers);
				System.out.println("this.sendMessage("+numOfPlayers+");");				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(true) {
				try {
					System.out.println("stage = "+stage);
					switch(stage)
					{
					//case "key_in_sentence":
					case 1:
						if(!isSetSentence){
						sentence = this.reader.readLine();
							broadcast("setSentence");
							//sentence = this.reader.readLine();
						broadcast(sentence);
						stage = 2;
						isSetSentence = true;
						}
						break;
					//case "new_stage":
					case 2:
						sentence = this.reader.readLine();
						cmd_i = sentence.split(" ");
						if(cmd_i[0].equals("Shooting"))
						{
							broadcast("setShooting");
							broadcast(cmd_i[1]); // int y place of the ray
						}
						else if(cmd_i[0].equals("PlacingBall"))
						{							
							broadcast("setBall");
							for(String send: cmd_i){
								if(!send.equals("PlacingBall"))
									broadcast(send);
							}								
						}
						else if(cmd_i[0].equals("PlacingPlank"))
						{
							broadcast("setPlank");	
							broadcast(cmd_i[1]); // int x place of the plank
						}
						else if(cmd_i[0].equals("hasWon"))
						{							
							for(String send: cmd_i)
								//if(!send.equals("hasWon"))
									broadcast(send);
						}
						else if(cmd_i[0].equals("hasLost"))
						{
							broadcast(cmd_i[0]);
						}
						
						break;						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	

		public void sendMessage(String message) throws IOException {	
			this.writer.println(message);
			this.writer.flush();
		}
	}
	
	private void broadcast(String message) throws IOException {
		System.out.println("broadcast: "+message);
		for (ConnectionThread connection: connections) {
			connection.sendMessage(message);
		}
	}
	
	
	public static void main(String[] args) {
		
		PlayerServer server = new PlayerServer(8000);
		server.login();
	}

}
