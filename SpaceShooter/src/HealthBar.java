import javax.swing.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;



public class HealthBar extends Figur{
        public HealthBar(int x, int y, JPanel panel,ImageIcon imgIcon) {
            super(x, y, panel, imgIcon, false);
            this.setIcon(imgIcon);
            this.setBounds(x,y,imgIcon.getIconWidth(),imgIcon.getIconHeight());
            ImageIcon healthBar = new ImageIcon("img/UI/HealthBar.png")
        }

    public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        // Bild zuschneiden
        BufferedImage croppedImage = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
        return new BufferedImage(rect.width, rect.height, src.getType());
    }

    public void delHealth() {

        File inpFile = 
        BufferedImage inputImage = ImageIO.read(inpFile);
        int width = inputImage.getWidth();
        // Crop Bereich
        Rectangle cropArea = new Rectangle(112, 11, width-10, 11);
        BufferedImage croppedImage = cropImage(inputImage, cropArea);

        // BufferedImage to ImageIcon
        ImageIcon healthBar = new ImageIcon(croppedImage);

        ImageIcon icon1 = healthBar;
        ImageIcon icon2 = new ImageIcon("/img/UI/emptyBar.png");

        // Convert ImageIcon to BufferedImage
        BufferedImage img1 = convertToBufferedImage(icon1);
        BufferedImage img2 = convertToBufferedImage(icon2);

        // Combine the images
        BufferedImage combinedImage = combineImages(img1, img2);

        // Convert the combined BufferedImage back to ImageIcon
        ImageIcon combinedIcon = new ImageIcon(combinedImage);

    }


    public static BufferedImage convertToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        return bufferedImage;
    }

    // Method to combine two BufferedImages
    public static BufferedImage combineImages(BufferedImage img1, BufferedImage img2) {
        int width = Math.max(img1.getWidth(), img2.getWidth());
        int height = Math.max(img1.getHeight(), img2.getHeight());
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        return combinedImage;
    }

}

