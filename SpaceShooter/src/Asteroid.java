import javax.swing.*;
import java.awt.*;

public class Asteroid extends Figur{
    protected int speed;
    public Asteroid(int x, int y, JPanel panel, ImageIcon imgIcon, int speed) {
        super(x, y, panel, imgIcon);
        this.speed = speed;
        this.setIcon(imgIcon);
        this.setBounds(x,y,imgIcon.getIconWidth(),imgIcon.getIconHeight());

    }

    public void move(){
        y = y + speed;
    }
}
