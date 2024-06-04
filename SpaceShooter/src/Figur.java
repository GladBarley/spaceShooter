import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Figur extends JLabel {

    protected int x, y;           // Position des Bildes
    protected JPanel panel;         // Breite und Höhe des Spielfeldes
    protected int xr, yr;         // Bewegung in x- und y-Richtung
    protected ImageIcon imgIcon;   // Breite und Höhe des Bildes
    protected Rectangle[] recs = new Rectangle[1];

    public Figur(int x, int y, JPanel panel, ImageIcon imgIcon) {
        super();
        this.x = x;
        this.y = y;
        this.imgIcon = imgIcon;
        this.panel = panel;
        this.xr = 0;
        this.yr = 0;
        this.imgIcon = imgIcon;
        //Image image = this.imgIcon.getImage();
        //Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        //this.imgIcon = new ImageIcon(newimg);
        //this.setBounds(x,y,this.imgIcon.getIconWidth(),this.imgIcon.getIconHeight());

    }

    public void move() { }
    public void move(boolean left, boolean right, boolean up, boolean down) { }


    public boolean collides(Figur figur2) {
        ImageIcon icon1 = this.imgIcon;
        ImageIcon icon2 = figur2.getImgIcon();

        BufferedImage img1 = toBufferedImage(icon1.getImage()); //Buffered Image für Zugriff auf Pixel
        BufferedImage img2 = toBufferedImage(icon2.getImage());

        //grobe Überprüfung
        Rectangle bounds1 = new Rectangle(0, 0, img1.getWidth(), img1.getHeight());
        Rectangle bounds2 = new Rectangle(0, 0, img2.getWidth(), img2.getHeight());
        if (!bounds1.intersects(bounds2)) {
            return false;
        }

        //Überprüfung pro Pixel
        for (int x = 0; x < img1.getWidth(); x++) {
            for (int y = 0; y < img1.getHeight(); y++) {
                int pixel1 = img1.getRGB(x, y);

                // Calculate the corresponding coordinates in img2
                int x2 = x - (this.getX() - figur2.getX());
                int y2 = y - (this.getY() - figur2.getY());

                if (x2 >= 0 && x2 < img2.getWidth() && y2 >= 0 && y2 < img2.getHeight()) {
                    int pixel2 = img2.getRGB(x2, y2);

                    // Check if both pixels are non-transparent
                    if (((pixel1 >> 24) & 0xff)!= 0 && ((pixel2 >> 24) & 0xff)!= 0) {
                        return true;
                    }
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

    public ImageIcon getImgIcon(){
        return this.imgIcon;
    }

    //public int getWidth(){return imgIcon.getIconWidth();}
    //public int getHeight(){return imgIcon.getIconHeight();}
}
