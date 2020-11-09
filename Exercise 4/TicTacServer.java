
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TicTacServer implements Constants{
	
	private Socket theSocket;
	private ServerSocket serverSocket; 

	private Socket player1;
	private Socket player2;
	
	private ExecutorService playGame;

	
	/**
	 * constructor that runs the server, initializing the server ports and thread pools
	 */
	public TicTacServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is running...");
			playGame = Executors.newFixedThreadPool(10);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * method that runs until it has stored two different client's sockets (player1 and player2)
	 * and assigns each player either the X marker or O marker based off of the order connected
	 */
	public void getTwoPlayers() {
		
		int playerCount = 0;
		while (playerCount < 2) {
			connect();

			try {
				PrintWriter socketOut  = new PrintWriter(this.theSocket.getOutputStream(),true);

				if(playerCount ==0) {
					socketOut.println(LETTER_X);
					player1 = theSocket;
				   	playerCount++;
				}
				else {
					socketOut.println(LETTER_O);
					player2 = theSocket;
					playerCount++;					
				}
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
		
	
	/**
	 * method to run the server, initializing the two players and then starting them on a thread from the threadpool 
	 */
	public void runServer () {

		
		while (true) {
			getTwoPlayers();
			
			playGame.execute(new Game(player1, player2));
	        	        
		}
		
	}

	
	/**
	 * method that sets up theSocket to a new client, once a new client has connected
	 */
	private void connect() {
		
		try {
			this.theSocket = this.serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * closes all connections
	 */
	private void disconnect() {

		try {
			this.theSocket.close();
			this.player1.close();		
			this.player2.close();		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void main (String [] args) throws IOException {
	
			TicTacServer myServer = new TicTacServer(8099); //same port as for client
			myServer.runServer();
			myServer.disconnect();
	}
}
