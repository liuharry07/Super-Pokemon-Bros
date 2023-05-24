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
    text3() {}

    // main class
    public void main() {
        // create a new frame to store text field and button
        f = new JFrame("RULES");

        // create a label to display text
        l = new JLabel();

        String one = "<html>For player 1, Charmander, use I to jump, J to move left,<br/> L to move right, space to block, semi-colan for a melee attack, and P for long-range attack. <br/>";
        String two = " For player 2, Squirtle, use W to jump, A to move left,<br/> D to move right, Shift to block, F for melee attack, and R for long-range attack. <br/>";
        String three = " Each player gets three lives. If you land at the bottom you lose a life,<br/> and as your damage score gets higher, the percentage in the scoreboard, your opponent's attack gets stronger.</html>";
        // add text to label
        l.setText(one + two + three);
        // create a panel
        JPanel p = new JPanel();

        // add label to panel
        p.add(l);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(750, 150);

        f.show();
    }
}
