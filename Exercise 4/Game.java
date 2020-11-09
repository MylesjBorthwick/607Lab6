/**
 * Calls more class methods to start the game, does initializations of class Player.
 * The overall purpose of this class is to organize the Player class, such that the Player
 * Class is able to run the individual moves of the game until it announces a winner.
 * 
 * @author Myles Borthwick, Ken Loughery
 */

import java.io.*;
import java.net.Socket;


public class Game implements Runnable, Constants {
	

	private PrintWriter player1SocketOut;
	private BufferedReader player1SocketIn;
	private PrintWriter player2SocketOut;
	private BufferedReader player2SocketIn;
	private String player1Name;
	private String player2Name;
	private Board board;
	
	
	/**
	 * default constructor that creates a new board for this class to keep track of game progress on,
	 * initializes the two player names, and generates the input and output buffers from the respective player sockets
	 */
	public Game(Socket player1, Socket player2) {
		this.board = new Board();
	
		try {
			player1SocketIn = new BufferedReader(new InputStreamReader(player1.getInputStream()));
			player1SocketOut = new PrintWriter(player1.getOutputStream(),true);
			player2SocketIn = new BufferedReader(new InputStreamReader(player2.getInputStream()));
			player2SocketOut = new PrintWriter(player2.getOutputStream(),true);
			this.player1Name = player1SocketIn.readLine();
			this.player2Name = player2SocketIn.readLine();


		} catch (Exception e) {
			e.printStackTrace();
		}		


	}
	
	/**
	 * Method that co-ordinates the game play, checking for winning conditions and sending out notifications
	 * to the players to regulate turns and end of game messages
	 */
	
	@Override
	public void run() {
				
		while(!board.isFull() && !board.xWins() && !board.oWins() ) {
			
			if(board.isFull()|| board.xWins()||board.oWins()) {
				break;
			}


			player1SocketOut.println(player1Name + " it is your turn to make a move.");
			updateBoard(1);
			
			if(board.isFull()|| board.xWins()||board.oWins()) {
				break;
			}	
			
			
			player2SocketOut.println(player2Name + " it is your turn to make a move.");
			updateBoard(2);

		} //end of loop
		
		if(board.isFull()) {
			player1SocketOut.println("THE GAME IS OVER: it is a tie!");
			player2SocketOut.println("THE GAME IS OVER: it is a tie!");
		}
		
		
		if(board.xWins() ) {
			player1SocketOut.println("THE GAME IS OVER: "+ player1Name + " is the winner!");
			player2SocketOut.println("THE GAME IS OVER: "+ player1Name + " is the winner!");
		}
		
		if(board.oWins() ) {
			player1SocketOut.println("THE GAME IS OVER: "+ player2Name + " is the winner!");
			player2SocketOut.println("THE GAME IS OVER: "+ player2Name + " is the winner!");
		}

	

	}

	
	/**
	 * Method that synchronizes the board class for the referee to the ones of each client, this
	 * method will receive the newest board coordinates from the turn player, identified by playerId as 
	 * 1 or 2, update the referee board, and then send these co-ordinates to the non-turn player
	 */

	private synchronized void updateBoard(int playerId)   {

        int row, col;
        if(playerId == 1) {
			try {
				row = Integer.parseInt(player1SocketIn.readLine());
				col = Integer.parseInt(player1SocketIn.readLine());		
						
				board.addMark(row, col, LETTER_X);


				player2SocketOut.println(row);
				player2SocketOut.println(col);
				player2SocketOut.println(LETTER_X);

			} catch (Exception e) {
				e.printStackTrace();
			}
		
        }
        else {
			try {
				row = Integer.parseInt(player2SocketIn.readLine());
				col = Integer.parseInt(player2SocketIn.readLine());
				board.addMark(row, col, LETTER_O);

				player1SocketOut.println(row);
				player1SocketOut.println(col);
				player1SocketOut.println(LETTER_O);

			} catch (Exception e) {
				e.printStackTrace();
			}

        }

			
	} 

}
