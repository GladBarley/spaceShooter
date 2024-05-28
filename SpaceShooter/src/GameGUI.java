

import com.sun.org.apache.bcel.internal.generic.IADD;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;

public class GameGUI {
    private JPanel mainPanel;
    private JPanel gamePanel;

    private int maxAsteroids = 8;
    private int maxBullets = 30;
     // [0] - Raumschiff; [1..max] - Asteroiden
    ArrayList<Asteroid> allAsteroids = new ArrayList<Asteroid>();
    ArrayList<Bullet> allBullets = new ArrayList<Bullet>();


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
                if (allAsteroids.size() < maxFiguren) {
                    int x = (int) (Math.random()*4);

                    int max = gamePanel.getWidth();
                    int min = 1;
                    int range = max - min + 1;

                    // generate random X Coordinates
                    for (int i = 0; i < 10; i++) {
                        int rx = (int)(Math.random() * range) + min;



                    ImageIcon icon1 = new ImageIcon("src/img/Space_Background/Asteroids_Foreground.png");
                    int height = icon1.getIconHeight();
                    int width = icon1.getIconWidth();
                    Asteroid ast1 = new Asteroid();
                    allAsteroids.add(ast1);
                    gamePanel.add(ast1);
                }
            }
        });
        astTimer.setInitialDelay(100);
        astTimer.start();
    }

    public void myTimer_ActionPerformed(ActionEvent evt) {
        // Bewegen von Raumschiff
        allAsteroids.get(0).move(keyLeft,keyRight,keyUp,keyDown);
        // alle Figuren (außer Jake) bewegen
        for (int i=1; i<anzAsteroids; i++){
            allAsteroids.get(i).move();
        }

        // hier muss auf Kollision mit Boden geprüft werden
        for (int i = 1; i < anzAsteroids; i++) {
            if (allAsteroids.get(0).getY() < 10) {
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
