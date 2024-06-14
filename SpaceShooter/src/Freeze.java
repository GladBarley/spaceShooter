import javax.swing.*;

public class Freeze extends Powerup{
    public Freeze(int x, int y, JPanel panel, ImageIcon imgIcon, boolean hit) {
        super(x, y, panel, imgIcon, hit);
        this.setIcon(imgIcon);
        this.setBounds(x, y, imgIcon.getIconWidth(), imgIcon.getIconHeight());
    }

    public Freeze() {

    }
}
