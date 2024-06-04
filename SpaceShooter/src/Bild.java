import javax.swing.*;

public class Bild extends Figur{
    public Bild(int x, int y, JPanel panel, ImageIcon imgIcon) {
        super(x, y, panel, imgIcon, false);
        this.setIcon(imgIcon);
        this.setBounds(x,y,imgIcon.getIconWidth(),imgIcon.getIconHeight());
    }
}
