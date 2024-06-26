import javax.swing.*;
import java.awt.*;

public class Asteroid extends Figur{
    protected int speed;
    protected boolean hit;
    public Asteroid(int x, int y, JPanel panel, ImageIcon imgIcon, int speed) {
        super(x, y, panel, imgIcon,false);
        this.speed = speed;

        this.setIcon(imgIcon);
        this.setBounds(x,y,imgIcon.getIconWidth(),imgIcon.getIconHeight());

    }


    public Asteroid(){
        super();
    }

    public void move(){
        y = y + speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
