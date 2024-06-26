import javax.swing.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class HealthBar extends Figur {
    private final ImageIcon healthBar;
    private ImageIcon originalIcon;
    private int count;
    private int health;
    private int originalhealth;
    public void reset(){
        health = originalhealth;
        imgIcon = originalIcon;
        this.setIcon(imgIcon);
        this.setBounds(x, y, imgIcon.getIconWidth(), imgIcon.getIconHeight());
    }
    public HealthBar(int x, int y, JPanel panel, ImageIcon imgIcon, int health) {
        super(x, y, panel, imgIcon, false);
        this.setIcon(imgIcon);
        this.setBounds(x, y, imgIcon.getIconWidth(), imgIcon.getIconHeight());
        this.health = health;
        originalhealth = health;
        originalIcon = imgIcon;
        healthBar = new ImageIcon(Objects.requireNonNull(getClass().getResource("img/UI/HealthBar.png")));
    }

    public ImageIcon delHealth() {
        BufferedImage inputImage = convertToBufferedImage(healthBar);
        count = count +1;
        int width = healthBar.getIconWidth();

        // HealthBar um 27 Pixel kleiner machen
        Rectangle cropArea = new Rectangle(0, 0, (width - (count*(width/health) - 1)), healthBar.getIconHeight());
        BufferedImage croppedImage = cropImage(inputImage, cropArea);

        // leere Bar laden
        ImageIcon emptyBarIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("img/UI/emptyBar.png")));
        BufferedImage emptyBarImage = convertToBufferedImage(emptyBarIcon);

        // HealthBar mit leerer Bar combinieren
        BufferedImage combinedImage = combineImages(croppedImage, emptyBarImage);


        return new ImageIcon(combinedImage);
    }

    public static BufferedImage cropImage(BufferedImage src, Rectangle rect) {
        return src.getSubimage(rect.x, rect.y, rect.width, rect.height);
    }

    public static BufferedImage convertToBufferedImage(ImageIcon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB);
        ImageIcon img = new ImageIcon(icon.getImage());
        img.paintIcon(null, bi.getGraphics(), 0, 0);
        return bi;
    }

    public static BufferedImage combineImages(BufferedImage img1, BufferedImage img2) {
        int width = Math.max(img1.getWidth(), img2.getWidth());
        int height = Math.max(img1.getHeight(), img2.getHeight());
        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        combinedImage.getGraphics().drawImage(img2, 0, 0, null);
        combinedImage.getGraphics().drawImage(img1, 0, 0, null);

        return combinedImage;
    }

    public static void saveImageToFile(BufferedImage image, String filePath) {
        File output = new File(filePath);
        try {
            ImageIO.write(image, "png", output);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing image: " + e.getMessage());
        }
    }

    public int getCount() {
        return count;
    }

    public int getHealth() {
        return health;
    }
}

