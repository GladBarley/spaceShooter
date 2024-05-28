

import com.sun.org.apache.bcel.internal.generic.IADD;

import javax.swing.*;
import java.awt.event.*;

public class GameGUI {
    private JPanel mainPanel;
    private JPanel gamePanel;

    private int maxAsteroids = 8;
    private int maxBullets = 30;
    private Asteroid[] allAsteroids = new asteroids[maxFiguren]; // [0] - Raumschiff; [1..max] - Asteroiden
    private Bullet[] allBullets = new Bullet[maxBullets];

    int anzAsteroids = 0;
    int anzBullets = 0;

    private static boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;

    private Timer myTimer;
    private Timer astTimer;

    public GameGUI() {
        gamePanel.setBounds(0,0,800,600);
        gamePanel.setLayout(null);

        // Raumschiff laden

        // Timer
        myTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                myTimer_ActionPerformed(evt);
            }
        });
        myTimer.setInitialDelay(100);
        myTimer.start();
        astTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (anzFiguren < maxFiguren) {
                    int z = (int) (Math.random()*4);
                    ImageIcon icon1 = new ImageIcon("src/FlummisJakeJaeger/images/smile" + z + ".png");
                    int height = icon1.getIconHeight();
                    int width = icon1.getIconWidth();
                    Asteroid ast1 = new Asteroid(//hier Constructor);
                    allAsteroids[anzAsteroids] = ast1;
                    gamePanel.add(ast1);
                    anzAsteroids++;
                }
            }
        });
        astTimer.setInitialDelay(100);
        astTimer.start();
    }

    public void myTimer_ActionPerformed(ActionEvent evt) {
        // Bewegen von Raumschiff
        allAsteroids[0].move(keyLeft,keyRight,keyUp,keyDown);
        // alle Figuren (außer Jake) bewegen
        for (int i=1; i<anzAsteroids; i++){
            allAsteroids[i].move();
        }

        // hier muss auf Kollision mit Boden geprüft werden
        for (int i = 1; i < anzAsteroids; i++) {
            if (allAsteroids[i].getY() < 10) {
                myTimer.stop();
                astTimer.stop();
            }
        }



        gamePanel.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Der wilde Space Shooter");
        frame.setContentPane(new GameGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT) keyLeft = true;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT) keyRight = true;
                if (event.getKeyCode() == KeyEvent.VK_UP) keyUp = true;
                if (event.getKeyCode() == KeyEvent.VK_DOWN) keyDown = true;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = true;
            }
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT) keyLeft = false;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT) keyRight = false;
                if (event.getKeyCode() == KeyEvent.VK_UP) keyUp = false;
                if (event.getKeyCode() == KeyEvent.VK_DOWN) keyDown = false;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = false;

            }
            @Override
            public void keyTyped(KeyEvent event) { }
        });
        frame.setVisible(true);
    }
}
