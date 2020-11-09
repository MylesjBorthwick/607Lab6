import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacGUI {

    private boolean canUpdate;
    JFrame f=new JFrame("Tic-Tac-Toe");
    JTextField username = new JTextField(20);
    JButton b1=new JButton("");//creating instance of JButton  
    JButton b2=new JButton("");
    JButton b3=new JButton("");
    JButton b4=new JButton("");
    JButton b5=new JButton("");
    JButton b6=new JButton("");
    JButton b7=new JButton("");
    JButton b8=new JButton("");
    JButton b9=new JButton("");

    JLabel character = new JLabel("Player:");
    JLabel name = new JLabel("Username:");
    JLabel status = new JLabel("Game Status");

   //creating instance of JFrame
    JTextArea message = new JTextArea();
    JTextField symbol = new JTextField(2);


    public TicTacGUI(){
         
        status.setBounds(275, 50, 100, 25);
        character.setBounds(50, 225, 100, 50);
        name.setBounds(50, 265, 100, 50);
        username.setBounds(125, 280, 100, 25);
       
        symbol.setBounds(110, 240, 25, 25);
        symbol.setEditable(false);
        
        message.setEditable(false);
        JScrollPane jsp = new JScrollPane(message);
        jsp.setBounds(275, 75, 150, 75);
        
        b1.setBounds(50,50,50, 50);
        b2.setBounds(50,100,50, 50);
        b3.setBounds(50,150,50, 50);
        b4.setBounds(100,50,50, 50);
        b5.setBounds(100,100,50, 50);
        b6.setBounds(100,150,50, 50);
        b7.setBounds(150,50,50, 50);
        b8.setBounds(150,100,50, 50);
        b9.setBounds(150,150,50, 50);

        f.add(b1);//adding button in JFrame  
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.add(b5);
        f.add(b6);
        f.add(b7);
        f.add(b8);
        f.add(b9);
        f.add(name);
        f.add(character);
        f.add(symbol);
        f.add(username);
        f.add(status);
        f.add(jsp);
        f.setSize(500,400);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible
    }
    
    public static void main(String[] args) {  
        JFrame f=new JFrame("Tic-Tac-Toe");//creating instance of JFrame  

        JLabel character = new JLabel("Player:");
        JLabel name = new JLabel("Username:");
        JLabel status = new JLabel("Game Status");
        status.setBounds(275, 50, 100, 25);
        character.setBounds(50, 225, 100, 50);
        name.setBounds(50, 265, 100, 50);

        JTextField username = new JTextField(20);
        username.setBounds(125, 280, 100, 25);
        JTextField symbol = new JTextField(2);
        symbol.setBounds(110, 240, 25, 25);
        symbol.setEditable(false);
        

        JTextArea message = new JTextArea();
        message.setEditable(false);
        JScrollPane jsp = new JScrollPane(message);
        jsp.setBounds(275, 75, 150, 75);
        

        JButton b1=new JButton("");//creating instance of JButton  
        JButton b2=new JButton("");
        JButton b3=new JButton("");
        JButton b4=new JButton("");
        JButton b5=new JButton("");
        JButton b6=new JButton("");
        JButton b7=new JButton("");
        JButton b8=new JButton("");
        JButton b9=new JButton("");
        
        b1.setBounds(50,50,50, 50);
        b2.setBounds(50,100,50, 50);
        b3.setBounds(50,150,50, 50);
        b4.setBounds(100,50,50, 50);
        b5.setBounds(100,100,50, 50);
        b6.setBounds(100,150,50, 50);
        b7.setBounds(150,50,50, 50);
        b8.setBounds(150,100,50, 50);
        b9.setBounds(150,150,50, 50);

        f.add(b1);//adding button in JFrame  
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.add(b5);
        f.add(b6);
        f.add(b7);
        f.add(b8);
        f.add(b9);
        f.add(name);
        f.add(character);
        f.add(symbol);
        f.add(username);
        f.add(status);
        f.add(jsp);
        f.setSize(500,400);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible
        
     
    }  
}