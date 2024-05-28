import javax.swing.*;


public class Bullet extends Figur{
    protected int speed;
    public Bullet(int x, int y, ImageIcon imgIcon, JPanel panel, int speed){
        super(x,y,panel, imgIcon);
        this.speed = speed;
        this.setIcon(imgIcon);
        this.setBounds(x,y,imgIcon.getIconWidth(),imgIcon.getIconHeight());
    }

    public void move() {
        y = y - speed;

    }
}
