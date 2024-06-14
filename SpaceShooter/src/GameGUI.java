


import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameGUI {

    private JPanel mainPanel;
    private JPanel gamePanel;
    private JLabel score;



    Background background1;
    Background background2;

    private final int maxFiguren = 8;

    private int anzAsteroiden;
    private final HealthBar healthBar;

    ArrayList<Figur> allFigures = new ArrayList<>();
    ArrayList<Bullet> allBullets = new ArrayList<>();


    private static boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;

    private final Timer myTimer;
    private final Timer astTimer;
    private final Timer bullTimer;
    private final Timer exTimer;
    private Figur delFig;
    private int aktScore;
    private int scale;

    public GameGUI() {
        gamePanel.setBounds(0, 0, 800, 600);
        gamePanel.setLayout(null);
        scale = 2;

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
        ImageIcon imgs = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/spaceship1.png")));
        Player spaceship = new Player(400,300,gamePanel,imgs,2);
        allFigures.add(spaceship);
        gamePanel.add(spaceship);
        System.out.println(imgs.getIconHeight());
        System.out.println(imgs.getIconWidth());

        // Asteroiden Anzahl
        anzAsteroiden = 1;


        // General Timer
        myTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                myTimer_ActionPerformed();
            }
        });
        myTimer.setInitialDelay(100);
        myTimer.start();

        // Healthbar
        ImageIcon healthIC = new ImageIcon(Objects.requireNonNull(getClass().getResource("img/UI/HealthBarFull.png")));
        Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth()*scale,healthIC.getIconHeight()*scale , Image.SCALE_SMOOTH);
        healthIC = new ImageIcon(scaledHealth);

        healthBar = new HealthBar(10,0,gamePanel,healthIC,4);
        gamePanel.add(healthBar);

        // Mond
        ImageIcon mIc = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Surface_Layer1.png")));
        Bild moon = new Bild(0,gamePanel.getHeight()-mIc.getIconHeight()+ 10,gamePanel,mIc);
        gamePanel.add(moon);

        // Bullet Timer
        bullTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                summonBullet();
            }
        });
        bullTimer.setInitialDelay(200);
        bullTimer.start();

        // Hintergrund Timer
        //Hintergrund
        Timer backgroundTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (background1.getY() + 2 == -1 || background1.getY() + 2 == 0) {
                    background2.move(background1.getHeight() * -2 + 2);
                } else {
                    background2.move(2);
                }
                if (background2.getY() + 2 == -1 || background2.getY() + 2 == 0) {
                    background1.move(background1.getHeight() * -2 + 2);
                } else {
                    background1.move(2);
                }
                //Hintergrund
                gamePanel.remove(background1);
                background1 = new Background(background1.getX(), background1.getY(), gamePanel, new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png"))));
                gamePanel.add(background1);
                gamePanel.remove(background2);
                background2 = new Background(background2.getX(), background2.getY(), gamePanel, new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png"))));
                gamePanel.add(background2);
            }
        });
        backgroundTimer.setInitialDelay(100);
        backgroundTimer.start();


        // Asteroid Timer
        astTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (allFigures.size() < maxFiguren) {
                    ImageIcon icon1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Asteroids_Foreground.png")));
                    int width = icon1.getIconWidth();

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

        //Hintergrund
        ImageIcon backgroundimg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png")));
        background1 = new Background(0,0, gamePanel, backgroundimg);
        gamePanel.add(background1);
        background2 = new Background(0,backgroundimg.getIconHeight()*-1, gamePanel, backgroundimg);
        gamePanel.add(background2);
    }


    public boolean checkMoonCollision(ArrayList<Figur> allFigures){
        for(int i=1;i<allFigures.size();i++){
            if(allFigures.get(i).getY()>550&& !allFigures.get(i).isHit()){
                ImageIcon ic = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/explosion.gif")));
                allFigures.get(i).setImgIcon(ic);
                return true;
            }
        }
        return false;
    }

    public void summonBullet(){
        if(Spacebar){
            // get Bullet Image
            Figur spaceship = allFigures.get(0);
            ImageIcon bullImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/Missile1.png")));
            Bullet bull1 = new Bullet(spaceship.getX()+(spaceship.getImgIcon().getIconWidth()/2), spaceship.getY(),bullImg,gamePanel,4);
            allBullets.add(bull1);
            gamePanel.add(bull1);

        }
    }

    public void myTimer_ActionPerformed() {
        // Bewegen von Raumschiff
        allFigures.get(0).move(keyLeft,keyRight,keyUp,keyDown);

        ImageIcon ic = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/explosion.gif")));
        // Kollision Boden
        for(int e=1; e<allFigures.size();e++){
            if(checkMoonCollision(allFigures)){
                allFigures.get(e).setHit(true);
                if(healthBar.getCount()>=4){
                    astTimer.stop();
                    myTimer.stop();
                    bullTimer.stop();
                    exTimer.restart();
                    gamePanel.remove(allFigures.get(e));
                    allFigures.get(e).setImgIcon(ic);
                    gamePanel.add(allFigures.get(e));
                    System.out.println("Kollision!!!");
                    break;
                }
                ImageIcon healthIC = healthBar.delHealth();
                Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth()*scale,healthIC.getIconHeight()*scale , Image.SCALE_SMOOTH);
                healthIC = new ImageIcon(scaledHealth);
                healthBar.setImgIcon(healthIC);
            }
        }

        // alle Figuren (außer Jake) bewegen
        for (int i=1; i<allFigures.size(); i++){
            allFigures.get(i).move();
        }
        if(!allBullets.isEmpty()) {
            for (int i = 0; i < allBullets.size(); i++) {
                Bullet bullet = allBullets.get(i);
                allBullets.get(i).move();
                // Bullet löschen, wenn es aus dem Bildschirm ist
                if (bullet.getY() < 1) {
                    allBullets.remove(i);
                    gamePanel.remove(bullet);

                }
            }
        }

        // Kollision mit Asteroiden
        for (int i = 1; i < allFigures.size(); i++) {
            for(int e=0;e<allBullets.size();e++){
                if(allBullets.get(e).collides(allFigures.get(i))&& !allFigures.get(i).isHit()){


                    aktScore = aktScore + 1;
                    allFigures.get(i).setHit(true);


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

                    delFig = allFigures.get(e);
                    if(!allFigures.get(e).isHit()){
                        allFigures.get(e).setHit(true);
                        if(healthBar.getCount()>= healthBar.getHealth()){
                            astTimer.stop();
                            myTimer.stop();
                            bullTimer.stop();
                            exTimer.restart();
                            gamePanel.remove(allFigures.get(e));
                            allFigures.get(e).setImgIcon(ic);
                            gamePanel.add(allFigures.get(e));
                            System.out.println("Kollision!!!");
                            break;
                        }
                        ImageIcon healthIC = healthBar.delHealth();
                        Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth()*scale,healthIC.getIconHeight()*scale , Image.SCALE_SMOOTH);
                        healthIC = new ImageIcon(scaledHealth);
                        healthBar.setImgIcon(healthIC);


                        exTimer.restart();
                        gamePanel.remove(allFigures.get(e));
                        allFigures.get(e).setImgIcon(ic);
                        gamePanel.add(allFigures.get(e));
                        System.out.println("Kollision!!!");
                    }

                    /*Timer gameOver;
                    gameOver = new Timer(400, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (allFigures.get(0).getImgIcon().equals(new ImageIcon("/img/transparent.png"))) {
                                ImageIcon ic = new ImageIcon("/img/Ships/spaceship1.png");
                                allFigures.get(0).setImgIcon(ic);
                            } else if (allFigures.get(0).getImgIcon().equals(new ImageIcon("/img/Ships/spaceship1.png"))) {
                                ImageIcon ic = new ImageIcon("/img/transparent.png");
                                allFigures.get(0).setImgIcon(ic);
                            }

                        }
                    });
                    gameOver.setInitialDelay(200);
                    gameOver.start();*/

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
        frame.setResizable(false);
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
