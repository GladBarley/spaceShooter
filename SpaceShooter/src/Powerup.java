import javax.swing.*;


// Freeze, Minischiffe
public class Powerup extends Figur {
    private int type;
    public Powerup(int x, int y, JPanel panel, ImageIcon imgIcon, boolean hit,int type) {
        super(x, y, panel, imgIcon, hit);
        this.type = type;
        this.setIcon(imgIcon);
        this.setBounds(x, y, imgIcon.getIconWidth(), imgIcon.getIconHeight());
    }

    public void triggerPowerup(){
        if(type==1)
    }


}
