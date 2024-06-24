import javax.swing.*;
import java.awt.*;

public class Helper extends Player{

    public Helper(int x, int y, JPanel panel, ImageIcon imgIcon, int bs, int xr, int yr) {
        super();
        this.x = x;
        this.y = y;
        this.panel = panel;
        this.bs = bs;
        Image scaledRocket = imgIcon.getImage().getScaledInstance(imgIcon.getIconWidth()/2,imgIcon.getIconHeight()/2 , Image.SCALE_SMOOTH);
        this.imgIcon = new ImageIcon(scaledRocket);
        this.setIcon(this.imgIcon);
        this.setBounds(x,y,this.imgIcon.getIconWidth(),this.imgIcon.getIconHeight());
        this.xr = xr;
        this.yr = yr;
    }

    public void move(boolean left,boolean right,boolean up,boolean down, Figur p){
        // Reaktion auf Tastatureingaben; Verzögerung bei Richtungsänderung, Ignorierung der Ränder

        int fw = panel.getWidth();
        int fh = panel.getHeight();
        int h = imgIcon.getIconHeight();
        int w = imgIcon.getIconWidth();

        if(p.getX() == 0){
            left = false; xr = 0;
        }
        if(p.getX() == fw - p.getWidth()){
            right = false; xr = 0;
        }

        if (left) xr--;
        if (right) xr++;
        if (!left && !right && xr!=0)
            xr = (int) Math.signum(xr)*(Math.abs(xr)-1);
        if (xr < -10) xr = -10;
        if (xr > 10) xr = 10;
        x = x + xr;


        if (up) yr--;
        if (down) yr++;
        if (!up && !down && yr!=0)
            yr = (int) Math.signum(yr)*(Math.abs(yr)-1);
        if (yr < -10) yr = -10;
        if (yr > 10) yr = 10;
        y = y + yr;


    }
}
