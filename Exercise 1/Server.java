import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Server class that allows clients to connect, accepting their sockets and
 *  placing them on their own threads of class palindrome, such that 
 * the client may see if their word is a palindrome.
 * 
 * @author Myles Borthwick
 * @author Ken Loughery
 */


public class Server {
	
	private Socket theSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ServerSocket serverSocket; 
	private ExecutorService pool;

	/**
	 * Constructor that creates a new server socket from the port number, and 
	 * establishes a limited number of connectable client threads (2)
	 * @param port the port number on which to establish a server
	 */
	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is now running.");
			pool = Executors.newFixedThreadPool(2);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * method that will operate the server, accepting new clients and putting them on a 
	 * palindrome checking thread that will handle the input and output
	 */
	public void runServer () {

		while (true) {
			connect();
			Palindrome pal = new Palindrome(socketIn, socketOut);
			pool.execute(pal);
		}
		
	}

	/**
	 * disconnects all server connections
	 */
	private void disconnect() {

		try {
			this.socketIn.close();
			this.socketOut.close();	
			this.serverSocket.close();	

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * a helper method that will accept a new client socket connecting, populating the member variables for 
	 * theSocket, socketIn, and socketOut with the client's information
	 */
	private void connect() {
		try {
			this.theSocket = this.serverSocket.accept();
			this.socketIn = new BufferedReader(new InputStreamReader(this.theSocket.getInputStream()));
			this.socketOut = new PrintWriter(this.theSocket.getOutputStream(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * main method that starts the server
	 */
	public static void main (String [] args) {
	
			Server myServer = new Server(8099);
			myServer.runServer();
			myServer.disconnect();
	}
}
