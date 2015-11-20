package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import org.json.JSONObject;

import player1.p1_Game;
import player2.p2_Game;

public class PlayerClient {
	private String destinationIPAddr;
	private int destinationPortNum;
	private Socket socket;
	private PrintWriter writer;
	private Login login;

	private String serverIP = "";
	private int portNumber;

	private boolean isReady = false;
	public p1_Game game1;
	public p2_Game game2;
	private String playerNumber;

	private String[] stageName = null;
	private String messageToServer;

	private boolean isGettingMessage = false;
	private int SLEEP_TIME = 20;
	private BufferedReader reader;

	public PlayerClient() {
		;
	}

	public static void main(String[] args) {
		PlayerClient client = new PlayerClient();
		client.startLogin();
		client.connect();
	}

	private void startLogin() {
		this.login = new Login();
	}

	public PlayerClient setIPAddress(String IPAddress) {
		this.destinationIPAddr = IPAddress;
		return this;
	}

	public PlayerClient setPort(int portNum) {
		this.destinationPortNum = portNum;
		return this;
	}

	public void connect() {
		GameThread gameThread;
		JSONObject jsonObjectJackyFromString;
		try {
			while (this.serverIP.equals("") || this.portNumber == 0) {
				jsonObjectJackyFromString = this.login.getSource();
				try {
					// System.out.println(jsonObjectJackyFromString);
					System.out.println(jsonObjectJackyFromString
							.get("serverIP"));
					System.out.println(jsonObjectJackyFromString
							.get("portNumber"));
					this.serverIP += jsonObjectJackyFromString.get("serverIP");
					this.portNumber = Integer.valueOf(
							"" + jsonObjectJackyFromString.get("portNumber"))
							.intValue();
				} catch (Exception e) {
				}
			}
			this.setIPAddress(this.serverIP).setPort(this.portNumber);
			System.out.print("connect()\n");
			this.socket = new Socket(this.destinationIPAddr,
					this.destinationPortNum);
			this.writer = new PrintWriter(new OutputStreamWriter(
					this.socket.getOutputStream()));
			this.reader = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
			WriterThread writerThread = new WriterThread(writer);
			ReaderThread readerThread = new ReaderThread(reader);
			gameThread = new GameThread();
			Start();
			readerThread.start();
			gameThread.start();
			writerThread.start();
		} catch (BindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cutSentence(String line2) {
		String[] cmd_i = line2.split(" ");
		this.setStageName(cmd_i);
	}

	private void setPlayerNumber(String readLine) {
		this.playerNumber = readLine;
	}

	private void addLine(final String message) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				PlayerClient.this.login.printToLogin(message + "\n");
			}

		});
	}

	private void Start() {
		if (this.playerNumber.equals("1") && !this.isReady) {
			this.login.printToLogin("Waiting for Player2\n");
		} else if (this.playerNumber.equals("2") && !this.isReady) {
			this.login.printToLogin("Player matched!\n");
		}
	}

	class ReaderThread extends Thread {
		private BufferedReader reader;
		private String line;
		private Waiting Waiting;

		public ReaderThread(BufferedReader reader) {
			this.reader = reader;
			try {
				PlayerClient.this.setPlayerNumber(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (true) {
				try {
					isGettingMessage = false;
					line = reader.readLine();
					System.out.println("read = " + line);
					if (line.equals("Game Ready!!")) {
						begin();
					} else if (line.equals("setSentence") && stageName == null) {
						isGettingMessage = true;
						line = reader.readLine();
						System.out.println("setSentence => " + line);
						cutSentence(line);
						if (playerNumber.equals("2")) {
							int len = stageName.length;
							PlayerClient.this.game2.produceStages(len);
							PlayerClient.this.game2.produceTrans(len);
							PlayerClient.this.game2.setStageAmount(len);
							PlayerClient.this.game2.setTopic(stageName);
							PlayerClient.this.game2.remove(Waiting);
							try {
								sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							PlayerClient.this.game2.repaintTopbar = true;
							PlayerClient.this.game2.start();
						}
					} else if (line.equals("setShooting")) {
						isGettingMessage = true;
						if (playerNumber.equals("2")) {
							line = reader.readLine();
							System.out.println("setShooting => " + line);
							int y = PlayerClient.this.game2.getCurrentStage().getlY();;
							try{
							y = Integer.valueOf(line).intValue();
							}catch(Exception e){}
							PlayerClient.this.game2.getCurrentStage().setlY(y);
							PlayerClient.this.game2.getCurrentStage().setShoot(
									true);

						} else {
							line = reader.readLine();
							System.out.println("eat = " + line);
						}
					} else if (line.equals("setBall")) {
						if (playerNumber.equals("1")) {
							isGettingMessage = true;
							if (PlayerClient.this.game1.getGameThread() == null) {
								PlayerClient.this.game1.start();
							}
							line = reader.readLine();
							System.out.println("setBall => " + line);
							try {
								float x = Float.parseFloat(line);
								line = reader.readLine();
								System.out.println("setBall => " + line);
								float y = Float.parseFloat(line);

								line = reader.readLine();
								System.out.println("setBall => " + line);
								int r = Integer.valueOf(line).intValue();
								line = reader.readLine();
								System.out.println("setBall => " + line);
								int g = Integer.valueOf(line).intValue();
								line = reader.readLine();
								System.out.println("setBall => " + line);
								int b = Integer.valueOf(line).intValue();

								if (PlayerClient.this.game1.gameScene != null) {
									PlayerClient.this.game1.gameScene
											.setBallX(x);
									PlayerClient.this.game1.gameScene
											.setBallY(y);
									PlayerClient.this.game1.gameScene
											.setBallRGB(r, g, b);
								}
							} catch (Exception e) {
							}

						} else
							for (int i = 0; i < 5; i++, line = reader
									.readLine())
								System.out.println("eat = " + line);
					} else if (line.equals("setPlank")) {
						isGettingMessage = true;
						if (playerNumber.equals("1")) {
							if (PlayerClient.this.game1.getGameThread() != null) {
								line = reader.readLine();
								System.out.println("setPlank => " + line);
								try {
									float x = Float.parseFloat(line);

									if (PlayerClient.this.game1.gameScene != null) {
										PlayerClient.this.game1.gameScene
												.setPlankX(x);
									}
								} catch (Exception e) {
								}
							}
						} else
							line = reader.readLine();
					} else if (line.equals("hasWon")) {
						if (playerNumber.equals("1")) {
							if (PlayerClient.this.game1.getGameThread() != null) {
								line = reader.readLine();
								System.out.println("hasWon => " + line);
								int length = stageName.length;
								String[][] topicData = new String[length][4];
								String[] items = line.replace("null","255").split("\\;");
								for (int i = 0; i < length; i++) {
									topicData[i] = items[i].split("\\,");
								}
								PlayerClient.this.game1.topicData = topicData;
								PlayerClient.this.game1.gameScene
										.setWinFlag(true);
								PlayerClient.this.game1.win(topicData);
							}
						}
						break;
					} else if (line.equals("hasLost")
							&& playerNumber.equals("1")) {
						if (PlayerClient.this.game1.getGameThread() != null) {
							PlayerClient.this.game1.gameScene.setLoseFlag(true);

						}
						break;
					} else
						;

					isGettingMessage = false;

				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void begin() {

			PlayerClient.this.isReady = true;
			try {
				line = reader.readLine();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			PlayerClient.this.addLine(line);
			// System.out.println(line.equals("Game Ready!!"));
			if (PlayerClient.this.playerNumber.equals("1")) {
				System.out.print("StartP1\n");
				PlayerClient.this.game1 = new p1_Game();
				PlayerClient.this.game1.setVisible(true);
				System.out.println("Player_1 Start!");
			} else if (PlayerClient.this.playerNumber.equals("2")) {
				PlayerClient.this.game2 = new p2_Game();
				Waiting = new Waiting();
				PlayerClient.this.game2.add(Waiting);
				PlayerClient.this.game2.setVisible(true);
				System.out.println("Player_2 Start!");
			}
			PlayerClient.this.login.setVisible(false);
		}

	}

	private void setStageName(String[] cmd_i) {
		stageName = cmd_i;
	}

	class WriterThread extends Thread {
		private PrintWriter writer;

		public WriterThread(PrintWriter writer) {
			this.writer = writer;
		}

		public void run() {
			while (true) {

				if (messageToServer != null && !isGettingMessage) {
					clientToServer(messageToServer);
				}
				try {
					sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		private void clientToServer(String message) {
			System.out.println("clientToServer=" + message);
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append(message);
			this.writer.println(sBuilder.toString());
			this.writer.flush();
			messageToServer = null;
		}

	}

	class GameThread extends Thread {

		String sentence;
		private float x, y, xOfPlank;
		int[] color;

		public void run() {
			while (true) {
				// System.out.println("Game Thread");
				if (PlayerClient.this.playerNumber.equals("1")
						&& PlayerClient.this.game1 != null) {
					if (PlayerClient.this.game1.gameScene != null) {
						if (PlayerClient.this.game1.gameScene.isShooting()) {
							int y = PlayerClient.this.game1.gameScene.getRayY();
							PlayerClient.this.game1.gameScene
									.setShooting(false);
							while (messageToServer != null || isGettingMessage)
								;
							messageToServer = "Shooting" + " "
									+ String.valueOf(y);
						}
					} else if (PlayerClient.this.game1.startScene != null) {
						if (sentence == null) {
							sentence = PlayerClient.this.game1.startScene
									.getSentence();
							if (sentence != null) {
								System.out.println("sentence=" + sentence);
								messageToServer = sentence;
							}
						}
					}

				} else if (PlayerClient.this.playerNumber.equals("2")) {
					if (PlayerClient.this.game2 != null) {
						if (PlayerClient.this.game2.hasWon()) {
							while (messageToServer != null || isGettingMessage)
								;
							String[][] topic = PlayerClient.this.game2
									.getTopicRGB();
							String array = "";
							for (String[] temp : topic) {
								array = array + " " + Arrays.toString(temp);
							}
							array = array.replaceAll(" ", "")
									.replaceAll("\\[", "")
									.replaceAll("\\]", ";");
							messageToServer = "hasWon" + " " + array;
						} else if (PlayerClient.this.game2.hasLost()) {
							while (messageToServer != null || isGettingMessage)
								;
							messageToServer = "hasLost";
						} else if (PlayerClient.this.game2.getCurrentStage() != null) {
							System.out.println("PlayerClient GameThread");
							x = PlayerClient.this.game2.getCurrentStage().ball
									.getBallX();
							y = PlayerClient.this.game2.getCurrentStage().ball
									.getBallY();
							color = PlayerClient.this.game2.getCurrentStage()
									.getRGB();
							xOfPlank = PlayerClient.this.game2
									.getCurrentStage().getPlank().getGX();

							while (messageToServer != null)
								;
							messageToServer = "PlacingBall "
									+ String.valueOf(x + "") + " "
									+ String.valueOf(y + "") + " " + color[0]
									+ " " + color[1] + " " + color[2];

							try {
								sleep(20);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							messageToServer = "PlacingPlank" + " " + xOfPlank;
						}
					}
				}
				try {
					sleep(SLEEP_TIME + 50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
