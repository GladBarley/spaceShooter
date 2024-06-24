import javax.swing.*;

public class HelperPowerup extends Powerup{
    public HelperPowerup(int x, int y, JPanel panel, ImageIcon imgIcon, boolean hit) {
        super(x, y, panel, imgIcon, hit);
        this.setIcon(imgIcon);
        this.setBounds(x, y, imgIcon.getIconWidth(), imgIcon.getIconHeight());
    }

    public HelperPowerup() {

    }

}
