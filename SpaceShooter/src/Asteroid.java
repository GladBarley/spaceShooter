import javax.swing.*;
import java.awt.*;

public class Asteroid extends Figur{
    protected int speed;
    protected ImageIcon img;
    public Asteroid(int x, int y, JPanel panel, ImageIcon img, int speed) {
        super(x, y, panel);
        this.speed = speed;
        this.img = img;
        this.setIcon(img);
        this.setBounds(x,y,img.getIconWidth(),img.getIconHeight());

    }

    public void move(){
        y = y + speed;
    }
}
