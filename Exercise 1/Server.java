import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
	
	private Socket theSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private ServerSocket serverSocket; 
	private ExecutorService pool;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is now running.");
			pool = Executors.newFixedThreadPool(2);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void runServer () {

		while (true) {
			connect();
			Palindrome pal = new Palindrome(socketIn, socketOut);
			pool.execute(pal);
		}
		
	}

	private void disconnect() {

		try {
			this.socketIn.close();
			this.socketOut.close();		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connect() {
		try {
			this.theSocket = this.serverSocket.accept();
			this.socketIn = new BufferedReader(new InputStreamReader(this.theSocket.getInputStream()));
			this.socketOut = new PrintWriter(this.theSocket.getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main (String [] args) throws IOException{
	
			Server myServer = new Server(8099); //same port as for client
			myServer.runServer();
			myServer.disconnect();
	}
}
