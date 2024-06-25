


import java.awt.*;
import java.sql.Time;
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
    ArrayList<Helper> allHelper = new ArrayList<>();
    ArrayList<Bullet> allBullets = new ArrayList<>();


    private static boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;

    private final Timer myTimer;
    private final Timer astTimer;
    private final Timer bullTimer;
    private final Timer exTimer;
    private final Timer helperTimer;
    private Figur delFig;
    private int aktScore;
    private int scale;

    public int astDelay;
    private Timer astMove;
    private Timer resetTimer;
    private Timer powerUpSpawn;
    private Timer backgroundTimer;
    private boolean debugimmortality = false;


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


        //Timer für Helper
        helperTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < allHelper.size(); i++){
                    Figur spaceship = allHelper.get(i);
                    ImageIcon bullImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/Missile1.png")));
                    Bullet bull1 = new Bullet(spaceship.getX()+(spaceship.getImgIcon().getIconWidth()/2), spaceship.getY(),bullImg,gamePanel,4);
                    allBullets.add(bull1);
                    gamePanel.add(bull1);
                }
            }
        });
        helperTimer.setInitialDelay(2000);
        helperTimer.start();

        //Powerup Timer
        powerUpSpawn = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int randInt = (int)(Math.random()*10);

                randInt = 2;

                //Variablen für Powerups
                ImageIcon powerIc;
                int width, max, min, range, rx;
                switch(randInt){
                    //Freeze Powerup
                    case 1:
                        powerIc = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/freeze.png")));
                        width = powerIc.getIconWidth();
                        max = gamePanel.getWidth()-width;
                        min = 1;
                        range = max - min + 1;
                        rx = (int) (Math.random() * range) + min;
                        Freeze freeze = new Freeze(rx,0,gamePanel,powerIc,false);
                        gamePanel.add(freeze);
                        allFigures.add(freeze);
                    //Helper Powerup
                    case 2:
                        System.out.println("Helper Powerup gespawnt");
                        powerIc = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/helperPowerup.png")));
                        width = powerIc.getIconWidth();
                        max = gamePanel.getWidth()-width;
                        min = 1;
                        range = max - min + 1;
                        rx = (int) (Math.random() * range) + min;
                        HelperPowerup helperPowerup = new HelperPowerup(rx,0,gamePanel,powerIc,false);
                        gamePanel.add(helperPowerup);
                        allFigures.add(helperPowerup);
                }
            }
        });
        powerUpSpawn.setInitialDelay(6000);
        powerUpSpawn.start();


        // Hintergrund Timer
        //Hintergrund
        backgroundTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Hintergrund
                gamePanel.remove(background1);
                background1 = new Background(background1.getX(), background1.getY(), gamePanel, new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png"))));
                gamePanel.add(background1);
                gamePanel.remove(background2);
                background2 = new Background(background2.getX(), background2.getY(), gamePanel, new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png"))));
                gamePanel.add(background2);

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
        astDelay = 10;
        // Asteroid move() Timer
        Timer astMove = new Timer(astDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // alle Figuren (außer Jake) bewegen
                for (int i=1; i<allFigures.size(); i++){
                    allFigures.get(i).move();
                }
            }
        });
        astMove.start();

        //Hintergrund
        ImageIcon backgroundimg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png")));
        background1 = new Background(0,0, gamePanel, backgroundimg);
        gamePanel.add(background1);
        background2 = new Background(0,backgroundimg.getIconHeight()*-1, gamePanel, backgroundimg);
        gamePanel.add(background2);
    }

    public boolean checkMoonCollision(ArrayList<Figur> allFigures){
        for(int i=1;i<allFigures.size();i++){
            if(allFigures.get(i).getY()>550 && !allFigures.get(i).isHit() && allFigures.get(i).getClass() == Asteroid.class){
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

        //Bewegen von  Helper
        for (int i = 0; i < allHelper.size(); i++){
            allHelper.get(i).move(keyLeft,keyRight,keyUp,keyDown, allFigures.get(0));
            allHelper.get(i).setY(allFigures.get(0).getY());
        }

        ImageIcon ic = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/explosion.gif")));
        // Kollision Boden
        for(int e=1; e<allFigures.size();e++){
            if(checkMoonCollision(allFigures) && !debugimmortality){
                allFigures.get(e).setHit(true);
                if(healthBar.getCount()>=4){
                    gameOver();
                    astTimer.stop();
                    myTimer.stop();
                    bullTimer.stop();
                    exTimer.restart();
                    helperTimer.stop();
                    astTimer.stop();
                    powerUpSpawn.stop();
                    exTimer.restart();
                    break;
                }
                ImageIcon healthIC = healthBar.delHealth();
                Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth()*scale,healthIC.getIconHeight()*scale , Image.SCALE_SMOOTH);
                healthIC = new ImageIcon(scaledHealth);
                healthBar.setImgIcon(healthIC);
            }
            if(allFigures.get(e).getY()>600 && allFigures.get(e).getClass() == Asteroid.class){
                allFigures.remove(e);
                gamePanel.remove(e);
            }
        }

        // Kollision Powerup
        powerupCollision();
        // Bullets löschen
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
                if(allFigures.get(e).collides(allFigures.get(i)) && !debugimmortality){

                    delFig = allFigures.get(e);
                    if(!allFigures.get(e).isHit()){
                        allFigures.get(e).setHit(true);
                        if(healthBar.getCount()>= healthBar.getHealth()){
                            gameOver();
                            astTimer.stop();
                            myTimer.stop();
                            bullTimer.stop();
                            astTimer.stop();
                            exTimer.restart();
                            helperTimer.stop();
                            powerUpSpawn.stop();
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

                    break;
                }
            }

        gamePanel.repaint();
    }

    public void gameOver(){
        allFigures.clear();
        allHelper.clear();
        allBullets.clear();
        //gamePanel.removeAll();
        Component[] components = gamePanel.getComponents();

        for (Component component : components) {
            if(component.getClass() != Background.class)
                gamePanel.remove(component);
        }
        ImageIcon overIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Game_Over.png")));
        JLabel overLabel = new JLabel(overIcon);
        overLabel.setBounds(50,50, overIcon.getIconWidth(), overIcon.getIconHeight());
        gamePanel.add(overLabel);

        ImageIcon againIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Play_Again.png")));
        JButton againButton = new JButton(againIcon);
        againButton.setBounds(250,350, againIcon.getIconWidth(), againIcon.getIconHeight());
        gamePanel.add(againButton);
    }

    public void setAstDelay(int astDelay) {
        this.astDelay = astDelay;
        astMove.setDelay(astDelay);
    }

    public void powerupCollision(){
        for(int i=0;i<allFigures.size();i++){
            if (allFigures.get(i).collides(allFigures.get(0)) && (new Freeze().getClass() == allFigures.get(i).getClass())) {

                // PowerUp löschen
                gamePanel.remove(allFigures.get(i));
                allFigures.remove(i);


                for (int e = 0; e<allFigures.size();e++){
                    int geschAst = (int)((Math.min(6, (int) Math.ceil(anzAsteroiden / 5.0)))/4);
                    allFigures.get(i).setSpeed(geschAst);

                }
                if(resetTimer == null) {
                    resetTimer = new Timer(5000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (int f = 0; f<allFigures.size();f++){
                                int geschAst = (Math.min(6, (int) Math.ceil(anzAsteroiden / 5.0)));
                                allFigures.get(f).setSpeed(geschAst);

                            }
                        }
                    });
                }
                resetTimer.restart();
            } else if (allFigures.get(i).collides(allFigures.get(0)) && (HelperPowerup.class == allFigures.get(i).getClass()) && (!allFigures.get(i).isHit())) {

                // PowerUp löschen
                gamePanel.remove(allFigures.get(i));
                allFigures.remove(i);

                //Helfer laden
                ImageIcon imgs = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/spaceship1.png")));
                int anzHelper = allHelper.size();
                if (anzHelper == 0){
                    Helper helper1 = new Helper(allFigures.get(0).getX() - 50 - allFigures.get(0).getWidth()/2, allFigures.get(0).getY(),gamePanel,imgs,2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper1);
                    allHelper.add(helper1);
                }else if(anzHelper == 1){
                    Helper helper2 = new Helper(allFigures.get(0).getX() + 50 + allFigures.get(0).getWidth(), allFigures.get(0).getY(),gamePanel,imgs,2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper2);
                    allHelper.add(helper2);
                } else if (anzHelper == 2) {
                    Helper helper3 = new Helper(allHelper.get(0).getX() - 50 - allFigures.get(0).getWidth()/2, allFigures.get(0).getY(),gamePanel,imgs,2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper3);
                    allHelper.add(helper3);
                } else if (anzHelper == 3) {
                    Helper helper4 = new Helper(allHelper.get(1).getX() + 50 + allFigures.get(0).getWidth()/2, allFigures.get(0).getY(),gamePanel,imgs,2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper4);
                    allHelper.add(helper4);
                }

            }
        }
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
