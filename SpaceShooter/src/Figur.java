import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Figur extends JLabel {

    protected int x, y;           // Position des Bildes
    protected JPanel panel;         // Breite und Höhe des Spielfeldes
    protected int xr, yr;         // Bewegung in x- und y-Richtung
    protected ImageIcon imgIcon;      // Breite und Höhe des Bildes

    public Figur(int x, int y, JPanel panel) {
        super();
        this.x = x;
        this.y = y;
        this.imgIcon = imgIcon;
        this.panel = panel;
        this.xr = 0;
        this.yr = 0;
        //Image image = this.imgIcon.getImage();
        //Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        //this.imgIcon = new ImageIcon(newimg);
        //this.setBounds(x,y,this.imgIcon.getIconWidth(),this.imgIcon.getIconHeight());

    }

    public void move() { }
    public void move(boolean left, boolean right, boolean up, boolean down) { }

    // Test auf Kollision
    /*public boolean istKollision(Figur aFigur) {
        if ((x+w) <= aFigur.getX()) return false;
        if ((y+h) <= aFigur.getY()) return false;
        if ((aFigur.getX() + aFigur.getWidth()) <= x) return false;
        if ((aFigur.getY() + aFigur.getHeight()) <= y) return false;
        return true;
    }*/

    public boolean collides(Figur figur2) {
        ImageIcon icon1 = this.imgIcon;
        ImageIcon icon2 = figur2.getImgIcon();

        BufferedImage img1 = toBufferedImage(icon1.getImage());
        BufferedImage img2 = toBufferedImage(icon2.getImage());

        int width1 = img1.getWidth();
        int height1 = img1.getHeight();
        int width2 = img2.getWidth();
        int height2 = img2.getHeight();

        // Check if the images overlap at all
        if (this.getX() + width1 <= figur2.getX() || figur2.getX() + width2 <= this.getX() || this.getY() + height1 <= figur2.getY() || figur2.getY() + height2 <= this.getY()) {
            return false;
        }

        // Check each pixel in the overlap area
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                // Get the pixel from each image
                int color1 = img1.getRGB(x, y);
                int color2 = img2.getRGB(x + this.getX() - figur2.getX(), y + this.getY() - figur2.getY());

                // Only check non-transparent pixels
                if ((color1 & 0xFF000000) != 0 && (color2 & 0xFF000000) != 0) {
                    // If the pixels don't match, the images collide
                    if (color1 != color2) {
                        return true;
                    }
                }
            }
        }

        // If we haven't returned yet, the images don't collide
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
    }

    public ImageIcon getImgIcon(){
        return this.imgIcon;
    }

    //public int getWidth(){return imgIcon.getIconWidth();}
    //public int getHeight(){return imgIcon.getIconHeight();}
}
