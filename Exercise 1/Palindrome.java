import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class compares socket input to itself to determine whether it is a palindorme
 * or not. THen outputs the result to socket out
 * Lab 6 Exercise 1
 * 
 * @author Ken Loughery
 * @author Myles Borthwick
 */

public class Palindrome implements Runnable {

    private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	/**
	 * Constructor for Palindrome class. Initializes socketOut 
	 * and socketIn
	 * @param socketIn the input socket defined
	 * @param socketOut the output socket defined
	 */
	public Palindrome (BufferedReader in, PrintWriter out) {
		socketIn = in;
		socketOut = out;
	}


	@Override
	/**
	 * This method constantly runs so user input can be taken and
	 * checked for palindrome features. After the check, output is created based on results
	 * and printed from socketout.
	 */
	public void run() {
			String line = null;
			while(true) {
				try {
					line = socketIn.readLine();
					if(isPalindrome(line)) {
						line +=(" is a Palindrome.");
					}
					else {
						line += (" is not a Palindrome.");
					}
					socketOut.println(line);
					
				}catch (IOException e) {
					e.printStackTrace();
				}
			}

	}
	/**
	 * This function compares input line's characters from
	 * front to back to check for palindrome
	 * @param line to be checked
	 * @return true or false
	 */
    private boolean isPalindrome(String line) {
        
        int i = 0;
        int j = line.length()-1;
        while(i<j){
            if(line.charAt(i)!=line.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    

}
