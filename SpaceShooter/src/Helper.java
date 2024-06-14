import javax.swing.*;
import java.awt.*;

public class Helper extends Player{

    public Helper(int x, int y, JPanel panel, ImageIcon imgIcon, int bs) {
        super();
        this.x = x;
        this.y = y;
        this.panel = panel;
        this.bs = bs;
        Image scaledRocket = imgIcon.getImage().getScaledInstance(imgIcon.getIconWidth()/2,imgIcon.getIconHeight()/2 , Image.SCALE_SMOOTH);
        this.imgIcon = new ImageIcon(scaledRocket);
        this.setIcon(this.imgIcon);
        this.setBounds(x,y,this.imgIcon.getIconWidth(),this.imgIcon.getIconHeight());
        this.xr = 0;
        this.yr = 0;
    }
}
