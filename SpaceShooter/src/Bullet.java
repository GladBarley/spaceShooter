import javax.swing.*;


public class Bullet extends Figur{
    protected int speed;
    protected ImageIcon img;
    public Bullet(int x, int y, ImageIcon img, JPanel panel, int speed){
        super(x,y,panel);
        this.speed = speed;
        this.img = img;
        this.setIcon(img);
        this.setBounds(x,y,img.getIconWidth(),img.getIconHeight());
    }

    public void move() {
        y = y - speed;

    }
}
