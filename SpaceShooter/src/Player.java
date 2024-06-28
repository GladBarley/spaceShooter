import javax.swing.*;

public class Player extends Figur{

    protected int bs;
    protected int xr, yr;
    public Player(int x, int y, JPanel panel, ImageIcon imgIcon, int bs) {
        super(x, y, panel, imgIcon,false);
        this.bs = bs;
        this.setIcon(imgIcon);
        this.setBounds(x,y,imgIcon.getIconWidth(),imgIcon.getIconHeight());
        xr = 0;
        yr = 0;

    }

    public Player() {
        super();
    }

    public void move(boolean left,boolean right,boolean up,boolean down){
        // Reaktion auf Tastatureingaben; Verzögerung bei Richtungsänderung

        int fw = panel.getWidth();
        int fh = panel.getHeight();
        int h = imgIcon.getIconHeight();
        int w = imgIcon.getIconWidth();

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

        // rechter oder linker Spielfeldrand?
        if (x <= 0) { x = 0; xr = 0; }
        if ((x+w) >= fw) { x = fw-w; xr = 0; }
        if (y <= 0) { y = 0; yr = 0; }
        if ((y+h) >= fh) { y = fh-h; yr = 0; }


    }




}
