/**
 * Created by Eugeny on 16.09.2016.
 */

import javax.swing.*;
import java.applet.*;
import java.awt.*;

public class FirstApplet extends Applet {
    Image img;
    String myGroup;

    public void init() {
        img = getImage(getCodeBase(), "ja.jpg");
        myGroup = getParameter("GROUP");
        setBackground(Color.darkGray);
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
        g.drawImage(img, 10, 100, this);
        g.drawString("Honorable students of group " + myGroup + "!", 350, 40);
        g.drawString("Congratulate you on the beggining of the course", 325, 60);
        g.drawString("\"Programming in Java\"", 400, 80);
    }
}
