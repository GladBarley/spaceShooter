import javax.swing.*;


public class Bullet extends Figur{
    protected int bs;
    protected int x;
    protected int y;
    public Bullet(int x, int y, ImageIcon img, JPanel panel, int bs){
        super(x,y,panel);
        this.x = x;
        this.y = y;
        this.bs = bs;
        this.setBounds(x,y,img.getIconWidth(),img.getIconHeight());
    }

    public void shoot(){
        y =- bs;
    }
}
