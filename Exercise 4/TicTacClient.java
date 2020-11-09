
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import javax.naming.directory.InvalidAttributeValueException;

/**
 * This class operates the backend for the client, handling the user inputs,
 * displayed messages, and error checking of inputs. This class will also display 
 * the board for the client and manage the backend of the GUI. 
 * 
 * ENSF 607 Lab 6
 * @author Myles Borthwick
 * @author Ken Loughery
 */

public class TicTacClient implements Constants{

	
	private String name = null;
	private Board board;
	private char mark;
	private PrintWriter socketOut;
	private Scanner scan;
	private BufferedReader socketIn;
	private String response; 
	private Socket thisSocket;
	private TicTacGUI userInterface;
	private int buttonCordinates [] [];

	/**
	 * Constructor that receives the server information to create a new socket to establish a connection 
	 * to the server
	 * @param serverName the name of the server to connect to
	 * @param portNumber the port number of the server to connect to
	 */
	public TicTacClient(String serverName, int portNumber) {
		try {
			thisSocket = new Socket(serverName, portNumber);
			scan = new Scanner(System.in);
			socketIn = new BufferedReader(new InputStreamReader(thisSocket.getInputStream()));
			socketOut = new PrintWriter((thisSocket.getOutputStream()), true);
			buttonCordinates = new int [9][2];
			populateButtonCordinates();
	        
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
		}
	}



	private void populateButtonCordinates(){
		try{	
			buttonCordinates[0][0] = 0;
			buttonCordinates[0][1] = 0;

			buttonCordinates[1][0] = 1;
			buttonCordinates[1][1] = 0;	

			buttonCordinates[2][0] = 2;
			buttonCordinates[2][1] = 0;

			buttonCordinates[3][0] = 0;
			buttonCordinates[3][1] = 1;	

			buttonCordinates[4][0] = 1;
			buttonCordinates[4][1] = 1;

			buttonCordinates[5][0] = 2;
			buttonCordinates[5][1] = 2;	

			buttonCordinates[6][0] = 0;
			buttonCordinates[6][1] = 2;	

			buttonCordinates[7][0] = 1;
			buttonCordinates[7][1] = 2;

			buttonCordinates[8][0] = 2;
			buttonCordinates[8][1] = 2;
			
		}catch(Exception e){
			System.out.println("Could not cordinate buttons");
		}
	}


	/**
	 * method that communicates to the server to update the client's copy of the board, receiving the location, and 
	 * character of the other player's mark and placing it on the board. This method will also display the board for
	 * the client, and print out any waiting messages
	 */
	
	private void updateBoard() {

       this.response = null;
		try {
			if(!board.isFull() && !board.xWins() && !board.oWins()) {
				System.out.println("Waiting for opponent to make their move...");
			}
			this.response  = socketIn.readLine();


			if(this.response .contains("THE GAME IS OVER: ")) {
				System.out.println(this.response );
				return;
			}
			try{
				int row = Integer.parseInt(this.response );
				this.response  = socketIn.readLine();
				int col = Integer.parseInt(this.response );	
				this.response  = socketIn.readLine();
				char mar = this.response .charAt(0);
	
				board.addMark(row, col, mar);
			}catch(Exception e){
				System.err.println("Error updating board: bad input.");
			}


		} catch (Exception e) {
			System.err.println("Error updating board: connection issue. ");
		}

        board.display();
		
		
		
	}
	
	/**
	 * method that communicates with the server and user to establish the client's name,
	 * mark, and sets a new board
	 */
	private void setupGame() {
		userInterface = new TicTacGUI();
		this.userInterface.updateMessage("WELCOME TO THE GAME!");
		board = new Board();
		try {
			response = socketIn.readLine();
		} catch (Exception e1) {
			System.out.println("Error receiving the mark character.");
		}
		this.mark = response.charAt(0);
		this.userInterface.updateMark(this.mark);

		System.out.print("\nPlease enter the name of the " + this.mark +" player: ");
		getName();
		
		board.display();
	}
	

	/**
	 * method that will get the client's name, calling itself recursively if presented with bad input
	 */
	private void getName(){
		
	}
	
	/**
	 * method that will run the game for the client, awaiting turns from each player
	 */
	public void playGame()  {
		this.response  = null;
		setupGame();
		socketOut.println(this.name);

		if(this.mark == LETTER_X) {
			System.out.println("Waiting for opponent to connect");
		}
		else {
			updateBoard();
		}
		
		while (true) {
			try {
				if(response.contains("THE GAME IS OVER: ")) {
					return;
				}	
				response = socketIn.readLine();
				System.out.println(response);
				if(response.contains("THE GAME IS OVER: ")) {
					return;
				}
				
				makeMove();
				
				updateBoard();
				
				
			} catch (Exception e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
	}

	

	/**
	 * Method that runs the operation that allows the client to make a move on the board. This method works recursively to 
	 * receive input from the user that is within the boundaries of the board, and not in an occupied space. Once this input is
	 * received, it is sent back to the server thread
	 */
	private void makeMove(){
		//Initialize scanner
		userInterface.setButtonPressed(-1);
		userInterface.setCanUpdate(true);
		System.out.println(this.name+ " please press a button for your next move: ");

		while(userInterface.getButtonPressed() == -1 || userInterface.getButtonPressed() >= 9){
		}
		int button = userInterface.getButtonPressed();
		int row = buttonCordinates[button][0];
		int col = buttonCordinates[button][1];
        //Check for existing marks 
        if(board.getMark(row, col)!=' '){
            System.out.println("Please pick an empty space");
            makeMove();
        }
        //If there is no mark, add mark
        else{
			//add mark and display board
			board.addMark(row, col, this.mark);
			board.display();
			socketOut.println(row);
			socketOut.println(col);	
		}	
		userInterface.setCanUpdate(false);
	}
	
	/**
	 * closes all connections
	 */
	public void disconnect() {
		try {
			scan.close();
			socketIn.close();
			socketOut.close();
			thisSocket.close();
		} catch (Exception e) {
			System.out.println("Closing error: " + e.getMessage());
		}
		
	}
	
	
	/**
	 * main function to run the client
	 */
	public static void main(String[] args)  {
		TicTacClient aClient = new TicTacClient("localhost", 8099);
		aClient.playGame();
		aClient.disconnect();
	}
}