// Java Program to create a
// blank label and add text to it.
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
class text2 extends JFrame {
 
    // frame
    static JFrame f;
 
    // label to display text
    static JLabel l;
 
    // default constructor
    text2()
    {
    }
 
    // main class
    public  void main()
    {
        // create a new frame to store text field and button
        f = new JFrame("RESULT");
 
        // create a label to display text
        l = new JLabel();
 
        // add text to label
        l.setText("GAME OVER! PLAYER 1 WINS!!!");
 
        // create a panel
        JPanel p = new JPanel();
 
        // add label to panel
        p.add(l);
 
        // add panel to frame
        f.add(p);
 
        // set the size of frame
        f.setSize(300, 50);
 
        f.show();
    }
}