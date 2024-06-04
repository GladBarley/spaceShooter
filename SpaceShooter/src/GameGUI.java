


import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;

public class GameGUI {

    private JPanel mainPanel;
    private JPanel gamePanel;

    private int maxFiguren = 8;
    private int maxBullets = 30;
     // [0] - Raumschiff; [1..max] - Asteroiden
    ArrayList<Figur> allFigures = new ArrayList<Figur>();
    ArrayList<Bullet> allBullets = new ArrayList<Bullet>();

    ArrayList<Figur> allExplosions = new ArrayList<Figur>();




    private static boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;

    private Timer myTimer;
    private Timer astTimer;
    private Timer bullTimer;
    private Timer exTimer;
    private Figur delFig;

    public GameGUI() {
        gamePanel.setBounds(0, 0, 800, 600);
        gamePanel.setLayout(null);


        exTimer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.remove(delFig);
                allFigures.remove(delFig);
                exTimer.stop();

            }
        });



        // Raumschiff laden
        ImageIcon imgs = new ImageIcon(getClass().getResource("/img/Ships/spaceship1.png") );
        Player spaceship = new Player(400,300,gamePanel,imgs,3);
        allFigures.add(spaceship);
        gamePanel.add(spaceship);
        System.out.println(imgs.getIconHeight());
        System.out.println(imgs.getIconWidth());






        // General Timer
        myTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                myTimer_ActionPerformed(evt);
            }
        });
        myTimer.setInitialDelay(100);
        myTimer.start();


        // Bullet Timer
        bullTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bullTimer_actionPerformed(e);
            }
        });
        bullTimer.setInitialDelay(200);
        bullTimer.start();


        // Asteroid Timer
        astTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (allFigures.size() < maxFiguren) {
                    int x = (int) (Math.random() * 4);

                    int max = gamePanel.getWidth();
                    int min = 1;
                    int range = max - min + 1;

                    // generate random X Coordinates

                int rx = (int) (Math.random() * range) + min;

                ImageIcon icon1 = new ImageIcon(getClass().getResource("/img/Space_Background/Asteroids_Foreground.png"));
                int height = icon1.getIconHeight();
                int width = icon1.getIconWidth();
                Asteroid ast1 = new Asteroid(rx,0,gamePanel,icon1,2);
                allFigures.add(ast1);
                gamePanel.add(ast1);

                }
            }
        });
        astTimer.setInitialDelay(100);
        astTimer.start();
    }


    public void bullTimer_actionPerformed(ActionEvent evt){
        if(Spacebar){
            // get Bullet Image
            Figur spaceship = allFigures.get(0);
            ImageIcon bullImg = new ImageIcon(getClass().getResource("/img/Ships/Missile1.png"));
            Bullet bull1 = new Bullet(spaceship.getX(), spaceship.getY(),bullImg,gamePanel,4);
            allBullets.add(bull1);
            gamePanel.add(bull1);
        }
    }





    public void myTimer_ActionPerformed(ActionEvent evt) {
        // Bewegen von Raumschiff
        allFigures.get(0).move(keyLeft,keyRight,keyUp,keyDown);




        // alle Figuren (außer Jake) bewegen
        for (int i=1; i<allFigures.size(); i++){
            allFigures.get(i).move();
        }

        for(int i=0; i < allBullets.size();i++){
            allBullets.get(i).move();
        }



        // Kollision mit Asteroiden
        for (int i = 1; i < allFigures.size(); i++) {
            for(int e=0;e<allBullets.size();e++){
                if(allBullets.get(e).collides(allFigures.get(i))== true){

                    delFig = allFigures.get(i);
                    // Explosion Timer



                    ImageIcon ic = new ImageIcon(getClass().getResource("/img/explosion.gif"));
                    exTimer.restart();
                    gamePanel.remove(allBullets.get(e));
                    gamePanel.remove(allFigures.get(i));
                    allFigures.get(i).setImgIcon(ic);
                    gamePanel.add(allFigures.get(i));
                    allBullets.remove(e);


                    System.out.println("Kollision!!!");
                    break;
                }
                ;
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
        frame.setVisible(true);
        //frame.setSize(800, 600);
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
