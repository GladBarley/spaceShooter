import javax.swing.*;

public class Background extends JLabel {
    int x;

    int y;
    JPanel panel;
    ImageIcon imgIcon;
    public Background(int x, int y, JPanel panel, ImageIcon imgIcon){
        this.x = x;
        this.y = y;
        this.panel = panel;
        this.imgIcon = imgIcon;
        this.setIcon(imgIcon);
        this.setBounds(x,y, imgIcon.getIconWidth(), imgIcon.getIconHeight());

    }
    public void setY(int y) {
        this.y = y;
    }


}
