
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * 607 Lab 6
 * Date Client class 
 * @author Ken Loughery
 * @author Myles Borthwick
 */
public class DateClient {
	
	private PrintWriter socketOut;
	private Socket theSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

	/**
	 * Client Constructor. Creates; Socket, Readers, Writers
	 * @param serverName 
	 * @param portNumber
	 */
	public DateClient(String serverName, int portNumber) {
		try {
			//Create Socket, Readers and Writer
			theSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					theSocket.getInputStream()));
			socketOut = new PrintWriter((theSocket.getOutputStream()), true);
			//Error Check
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	/**
	 * Operate function, handles User input and selection. 
	 * Prints prompts
	 */
	public void operate()  {

		String line = null;
		String response = null;
		while (true) {
			try {
				//Selection prompt
				System.out.println("Please selection an option (DATE/TIME) ");
				line = stdIn.readLine();
				
				//Exit checking
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
			
			  //Error Checking	
			} catch (IOException e) {
				System.out.println("An error occured while sending: " + e.getMessage());
			}
		}
		try {
			//Close Readers and writers
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("An error occured while closing: " + e.getMessage());
		}

	}

	/**
	 * Driver function for client
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException  {
		DateClient aClient = new DateClient("localhost", 9090);
		aClient.operate();
	}
}


