import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Figur extends JLabel {

    protected int x, y;           // Position des Bildes
    protected JPanel panel;         // Breite und Höhe des Spielfeldes
    protected int xr, yr;         // Bewegung in x- und y-Richtung
    protected ImageIcon imgIcon;   // Breite und Höhe des Bildes
    protected Rectangle[] recs = new Rectangle[1];
    protected boolean hit;

    public Figur(int x, int y, JPanel panel, ImageIcon imgIcon, boolean hit) {
        super();
        this.x = x;
        this.y = y;
        this.imgIcon = imgIcon;
        this.panel = panel;
        this.xr = 0;
        this.yr = 0;
        this.hit = hit;
        this.imgIcon = imgIcon;
        //Image image = this.imgIcon.getImage();
        //Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        //this.imgIcon = new ImageIcon(newimg);
        //this.setBounds(x,y,this.imgIcon.getIconWidth(),this.imgIcon.getIconHeight());

    }

    public Figur() {

    }

    public void move() { }
    public void move(boolean left, boolean right, boolean up, boolean down) { }


    public boolean collides(Figur figur2) {
        ImageIcon icon1 = this.imgIcon;
        ImageIcon icon2 = figur2.getImgIcon();

        BufferedImage img1 = toBufferedImage(icon1.getImage()); //Buffered Image für Zugriff auf Pixel
        BufferedImage img2 = toBufferedImage(icon2.getImage());

        //grobe Überprüfung
        Rectangle bounds1 = new Rectangle(this.x, this.y, img1.getWidth(), img1.getHeight());
        Rectangle bounds2 = new Rectangle(figur2.getX(), figur2.getY(), img2.getWidth(), img2.getHeight());
        if (!bounds1.intersects(bounds2)) {
            return false;
        }
        Rectangle intersection = bounds1.intersection(bounds2);
        int difx2 = 0;
        int difx1 = 0;
        if (intersection.x > this.x){
            difx1 = intersection.x - this.x;
        }else{
            difx2 = intersection.x - figur2.getX();
        }
        int dify2 = 0;
        int dify1 = 0;
        if(intersection.y < this.y){
            dify1 = intersection.y - this.y;
        }else{
            dify2 = intersection.y - figur2.y;
        }




        //Überprüfung pro Pixel
        for (int x = difx1; x < difx1 + intersection.width; x++) {
            for (int y = dify1; y < dify1 + intersection.height; y++) {
                int pixel1 = img1.getRGB(x, y);
                int pixel2 = img2.getRGB(difx2 + x - difx1, dify2 + y - dify1);

                // Check if both pixels are non-transparent
                if (((pixel1 >> 24) & 0xff)!= 0 && ((pixel2 >> 24) & 0xff)!= 0) {
                    return true;
                }

            }
        }
        return false;
    }

    private static BufferedImage toBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }

    public void abprallen(Figur aFigur) { }

    public int getX() {
        return x;
    }

    public void setX(int xNeu) {
        x = xNeu;
    }

    public int getXr() {
        return xr;
    }

    public void setXr(int xrNeu) {
        xr = xrNeu;
    }

    public int getY() {
        return y;
    }

    public void setY(int yNeu) {
        y = yNeu;
    }

    public int getYr() {
        return yr;
    }

    public void setYr(int yrNeu) {
        yr = yrNeu;
    }

    public void setImgIcon(ImageIcon imgIcon){
        this.imgIcon = imgIcon;
        this.setIcon(imgIcon);
    }



    public boolean isHit() {
        return hit;
    }



    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public ImageIcon getImgIcon(){
        return this.imgIcon;
    }

    public void setSpeed(int speed){}

    //public int getWidth(){return imgIcon.getIconWidth();}
    //public int getHeight(){return imgIcon.getIconHeight();}
}
