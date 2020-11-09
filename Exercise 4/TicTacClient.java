
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TicTacClient implements Constants{

	
	private String name = null;
	private Board board;
	private char mark;
	private PrintWriter socketOut;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	private String response; 
	private Socket thisSocket;

	/**
	 * constructor that establishes a connection to the server and sets all the required communication channels
	 */
	public TicTacClient(String serverName, int portNumber) {
		try {
			thisSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(thisSocket.getInputStream()));
			socketOut = new PrintWriter((thisSocket.getOutputStream()), true);
	        
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}


	/**
	 * method that communicates to the server to update the client's copy of the board, receiving the location, and 
	 * character of the other player's mark and placing it on the board. 
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
		this.response  = "Message: WELCOME TO THE GAME. ";
		board = new Board();
		System.out.println(this.response );
		try {
			response = socketIn.readLine();
		} catch (IOException e1) {
			System.out.println("Error receiving the mark character.");
		}
		this.mark = response.charAt(0);
		

		System.out.print("\nPlease enter the name of the " + this.mark +" player: ");
		try {
			this.name = stdIn.readLine();
			while (this.name == null) {
				System.out.print("Please try again: ");
				name = stdIn.readLine();
			}
		} catch (IOException e1) {
			System.err.println(e1.getStackTrace());
		}
		board.display();
	}
	
	
	
	/**
	 * method that operates the game from the client side, waiting for turns 
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
				
				
			} catch (IOException e) {
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
		int row = 0, col = 0;
		Scanner scan = new Scanner(System.in);
		try{
			//Row prompt + input
			System.out.println(this.name+ " please enter row number for your move: ");
			row = scan.nextInt();
			//Check for valid row input
			if(row<0 || row>2){
				throw new NumberFormatException("Number is out of scope");
			}
		}catch(Exception e){
			System.out.println("Please enter integer 0,1 or 2");
			makeMove();
			return;
		}

		try{
        	//Column prompt + input
			System.out.println(this.name+ " please enter column number for your move: ");
			col = scan.nextInt();
			//Check for valid row input
			if(col<0 || col>2){
				throw new NumberFormatException("Number is out of scope");
			}
		}catch(Exception e){
			System.out.println("Please enter integer 0,1 or 2");
			makeMove();
			return;
		}
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

	}
	
	
	
	/**
	 * closes all connections
	 */
	public void disconnect() {
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
			thisSocket.close();
		} catch (IOException e) {
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