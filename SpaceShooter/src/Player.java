import javax.swing.*;

public class Player extends Figur{
    protected ImageIcon img;
    protected int bs;
    public Player(int x, int y, JPanel panel, ImageIcon img, int bs) {
        super(x, y, panel);
        this.img = img;
        this.bs = bs;
        this.setIcon(img);
        this.setBounds(x,y,img.getIconWidth(),img.getIconHeight());
    }

    public void move(boolean keyLeft,boolean keyRight,boolean keyUp,boolean keyDown, boolean Spacebar){
        if(keyLeft)x--;
        if(keyRight)x++;
        if(keyDown)y++;
        if(keyUp)y--;


    }
}
