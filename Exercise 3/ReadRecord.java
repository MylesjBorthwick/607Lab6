/** 
 * Started by M. Moussavi
 * March 2015
 * Completed by: Myles Borthwick, Ken Loughery
 */


import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadRecord {
    
    private ObjectInputStream input;
    
    /**
     *  opens an ObjectInputStream using a FileInputStream
     */
    
    private void readObjectsFromFile(String name)
    {
        MusicRecord record ;

        try
        {
            input = new ObjectInputStream(new FileInputStream( name ) );
        }
        catch ( IOException ioException )
        {
            System.err.println( "Error opening file." );
        }
        
        /* The following loop is supposed to use readObject method of
         * ObjectInputSteam to read a MusicRecord object from a binary file that
         * contains several records.
         * Loop should terminate when an EOFException is thrown.
         */

        try
        {
            while ( true )
            {


                // TO BE COMPLETED BY THE STUDENTS

                record =  (MusicRecord) input.readObject();
                System.out.printf("%-10d %-25s %-20s %10.2f\n",record.getYear(),record.getSongName(),record.getSingerName(),record.getPurchasePrice());

            }   // END OF WHILE

            // ADD NECESSARY catch CLAUSES HERE

        } catch(EOFException e) {
        	System.out.println("End of file found. ");
        } catch (ClassNotFoundException e) {
            System.err.println("Can't read the object type in ser file");
        } catch(Exception e){
            System.err.println("An error occurred... ");
        }
        


    }           // END OF METHOD 
    
    
    public static void main(String [] args)
    {
        ReadRecord d = new ReadRecord();
        d.readObjectsFromFile("allSongs.ser");
    }
}