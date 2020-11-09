
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class dateTimeClient {
	
	private PrintWriter socketOut;
	private Socket theSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

	public dateTimeClient(String serverName, int portNumber) {
		try {
			theSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					theSocket.getInputStream()));
			socketOut = new PrintWriter((theSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	public void operate()  {

		String line = null;
		String response = null;
		while (true) {
			try {
				System.out.println("Please selection an option (DATE/TIME) ");
				line = stdIn.readLine();
				
				if (line.equals("QUIT")){
					socketOut.println(line);
					System.out.println("Disconnected... ");
					break;
				}
				else{
					socketOut.println(line);
					response = socketIn.readLine();
					System.out.println(response);
				}
			
				
			} catch (IOException e) {
				System.out.println("An error occured while sending: " + e.getMessage());
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("An error occured while closing: " + e.getMessage());
		}

	}

	public static void main(String[] args) throws IOException  {
		dateTimeClient aClient = new dateTimeClient("localhost", 9090);
		aClient.operate();
	}
}


