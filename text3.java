// Java Program to create a
// blank label and add text to it.
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
class text3 extends JFrame {
 
    // frame
    static JFrame f;
 
    // label to display text
    static JLabel l;
 
    // default constructor
    text3()
    {
    }
 
    // main class
    public  void main()
    {
        // create a new frame to store text field and button
        f = new JFrame("NEW GAME");
 
        // create a label to display text
        l = new JLabel();
 
        String one = "For player 1, Charmander, use P to jump, L to move left,\n Quotation key to move right, semicolan to block, return for a melee attack, and command for long-range attack. \n";
        String two = " For player 2, Squirtle, use W to jump, A to move left,\n D to move right, S to block, Caps-Lock for melee attack, and space for long-range attack. \n";
        String three = " Each player gets three lives. If you land at the bottom you lose a life,\n and as your damage score gets higher, the percentage at the bottom, your opponent's attack gets stronger.";
        // add text to label
        l.setText(one + two + three);
        // create a panel
        JPanel p = new JPanel();
 
        // add label to panel
        p.add(l);
 
        // add panel to frame
        f.add(p);
 
        // set the size of frame
        f.setSize(300, 300);
 
        f.show();
    }
}