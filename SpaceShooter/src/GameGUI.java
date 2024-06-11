


import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameGUI {

    private JPanel mainPanel;
    private JPanel gamePanel;
    private JLabel score;
    Bild healthBar;
    Bild emptyBar;


    private final int maxFiguren = 8;

    private int anzAsteroiden;
    private int geschAst;

     // [0] - Raumschiff; [1..max] - Asteroiden
    ArrayList<Figur> allFigures = new ArrayList<Figur>();
    ArrayList<Bullet> allBullets = new ArrayList<Bullet>();

    ArrayList<Figur> allExplosions = new ArrayList<Figur>();




    private static boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;

    private final Timer myTimer;
    private final Timer astTimer;
    private final Timer bullTimer;
    private final Timer exTimer;
    private Figur delFig;
    private int aktScore;

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


        score.setText("0");
        // Raumschiff laden
        ImageIcon imgs = new ImageIcon(getClass().getResource("/img/Ships/spaceship1.png") );
        Player spaceship = new Player(400,300,gamePanel,imgs,2);
        allFigures.add(spaceship);
        gamePanel.add(spaceship);
        System.out.println(imgs.getIconHeight());
        System.out.println(imgs.getIconWidth());

        // Asteroiden Anzahl
        anzAsteroiden = 0;


        // General Timer
        myTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                myTimer_ActionPerformed(evt);
            }
        });
        myTimer.setInitialDelay(100);
        myTimer.start();

        // Health Bar
        ImageIcon health = new ImageIcon(getClass().getResource("img/UI/HealthBar.png"));
        Bild healthBar = new Bild(650,0,gamePanel,health);
        gamePanel.add(healthBar);

        // Empty Bar
        ImageIcon bar = new ImageIcon(getClass().getResource("img/UI/emptyBar.png"));
        Bild emptyBar = new Bild(650,0,gamePanel,bar);
        gamePanel.add(emptyBar);




        // Mond
        ImageIcon mIc = new ImageIcon(getClass().getResource("/img/Surface_Layer1.png"));
        Bild moon = new Bild(0,gamePanel.getHeight()-mIc.getIconHeight(),gamePanel,mIc);
        gamePanel.add(moon);

        // Bullet Timer
        bullTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                summonBullet(e);
            }
        });
        bullTimer.setInitialDelay(200);
        bullTimer.start();


        // Asteroid Timer
        astTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (allFigures.size() < maxFiguren) {
                    ImageIcon icon1 = new ImageIcon(getClass().getResource("/img/Space_Background/Asteroids_Foreground.png"));
                    int height = icon1.getIconHeight();
                    int width = icon1.getIconWidth();
                    int x = (int) (Math.random() * 4);

                    int max = gamePanel.getWidth()-width;
                    int min = 1;
                    int range = max - min + 1;

                    int geschAst = Math.min(6, (int) Math.ceil(anzAsteroiden / 5.0));

                    int delay = Math.max(1000, 2000 - (anzAsteroiden * 1000 / 30));
                    astTimer.setDelay(delay);

                    // generate random X Coordinates

                int rx = (int) (Math.random() * range) + min;



                    Asteroid ast1 = new Asteroid(rx, 0, gamePanel, icon1, geschAst);
                allFigures.add(ast1);
                gamePanel.add(ast1);
                anzAsteroiden = anzAsteroiden +1;

                }
            }
        });
        astTimer.setInitialDelay(100);
        astTimer.start();
    }


    public boolean checkMoonCollision(ArrayList<Figur> allFigures){
        for(int i=1;i<allFigures.size();i++){
            if(allFigures.get(i).getY()>550&& !allFigures.get(i).isHit()){
                ImageIcon ic = new ImageIcon(getClass().getResource("/img/explosion.gif"));
                allFigures.get(i).setImgIcon(ic);
                return true;
            }
        }
        return false;
    }

    public void summonBullet(ActionEvent evt){
        if(Spacebar){
            // get Bullet Image
            Figur spaceship = allFigures.get(0);
            ImageIcon bullImg = new ImageIcon(getClass().getResource("/img/Ships/Missile1.png"));
            Bullet bull1 = new Bullet(spaceship.getX()+(spaceship.getImgIcon().getIconWidth()/2), spaceship.getY(),bullImg,gamePanel,4);
            allBullets.add(bull1);
            gamePanel.add(bull1);
        }
    }

    public void myTimer_ActionPerformed(ActionEvent evt) {
        // Bewegen von Raumschiff
        allFigures.get(0).move(keyLeft,keyRight,keyUp,keyDown);

        // Health Bar bewegen
        int x = allFigures.get(0).getX();
        int y = allFigures.get(0).getY();
        //healthBar.setY(y);
        //healthBar.setX(x);
        //emptyBar.setX(x);
        //emptyBar.setY(y);


        // Kollision Boden
        for(int i=1; i<allFigures.size();i++){
            if(checkMoonCollision(allFigures)){
                astTimer.stop();
                exTimer.stop();
                myTimer.stop();
            }
        }



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
                if(allBullets.get(e).collides(allFigures.get(i))){
                    ImageIcon ic = new ImageIcon(getClass().getResource("/img/explosion.gif"));

                    if(allFigures.get(i).isHit() == false){
                        aktScore = aktScore + 1;
                        allFigures.get(i).setHit(true);
                    }

                    // Score
                    score.setText(Integer.toString(aktScore));

                    delFig = allFigures.get(i);




                    // Asteroiden Bild ändern und Bullet löschen
                    exTimer.restart();
                    gamePanel.remove(allBullets.get(e));
                    gamePanel.remove(allFigures.get(i));
                    allFigures.get(i).setImgIcon(ic);
                    gamePanel.add(allFigures.get(i));
                    allBullets.remove(e);


                    System.out.println("Kollision!!!");
                    break;
                }
            }

        }
        int i = 0;
        for(int e=1;e<allFigures.size();e++){
                if(allFigures.get(e).collides(allFigures.get(i))){
                    ImageIcon ic = new ImageIcon(getClass().getResource("/img/explosion.gif"));

                    if(allFigures.get(e).isHit() == false){
                        allFigures.get(e).setHit(true);
                        myTimer.stop();
                        exTimer.stop();
                        astTimer.stop();
                    }


                    Timer gameOver;
                    gameOver = new Timer(400, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(allFigures.get(0).getImgIcon() == new ImageIcon("/img/transparent.png")){
                                ImageIcon ic = new ImageIcon("/img/Ships/spaceship1.png");
                                allFigures.get(0).setImgIcon(ic);
                            } else if (allFigures.get(0).getImgIcon() == new ImageIcon("/img/Ships/spaceship1.png")) {
                                ImageIcon ic = new ImageIcon("/img/transparent.png");
                                allFigures.get(0).setImgIcon(ic);
                            }


                        }
                    });
                    gameOver.setInitialDelay(200);
                    gameOver.start();


                    break;
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
                if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) keyLeft = true;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) keyRight = true;
                if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) keyUp = true;
                if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) keyDown = true;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = true;
            }
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) keyLeft = false;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) keyRight = false;
                if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) keyUp = false;
                if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) keyDown = false;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = false;

            }
            @Override
            public void keyTyped(KeyEvent event) { }
        });
        frame.setVisible(true);
    }
}
