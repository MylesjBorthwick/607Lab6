import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Palindrome implements Runnable {

    private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	/**
	 * @param socketIn the input socket defined
	 * @param socketOut the output socket defined
	 */
	public Palindrome (BufferedReader in, PrintWriter out) {
		socketIn = in;
		socketOut = out;
	}


    @Override
	public void run() {
			String line = null;
			while(true) {
				try {
					line = socketIn.readLine();
					if(isPalindrome(line)) {
						line = line.concat(" is a Palindrome.");
					}
					else {
						line = line.concat(" is not a Palindrome.");
					}
					socketOut.println(line);
					
				}catch (IOException e) {
					e.printStackTrace();
				}
			}

	}

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
