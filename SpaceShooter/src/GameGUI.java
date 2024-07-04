import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class GameGUI {
    public JFrame frame;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JLabel score;
    private final HealthBar healthBar;
    private Bild moon;

    Background background1;
    Background background2;

    private final int maxFiguren = 8;
    private int anzAsteroiden;

    ArrayList<Figur> allFigures = new ArrayList<>();
    ArrayList<Helper> allHelper = new ArrayList<>();
    ArrayList<Bullet> allBullets = new ArrayList<>();

    public static boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;
  
    private final Timer myTimer;
    private final Timer astTimer;
    private final Timer bullTimer;
    private final Timer exTimer;
    private final Timer helperTimer;
    private Timer resetTimer;
    private Timer powerUpSpawn;
    private Timer backgroundTimer;

    private Figur delFig;
    private int aktScore;
    private int scale;

    private boolean debugimmortality = false;
    public int astDelay;
    private Timer astMove;
    Main main;
    public GameGUI(Main main) {
        this.main = main;
      


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
        Player spaceship = new Player(400, 300, gamePanel, imgs, 2);
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
        Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth() * scale, healthIC.getIconHeight() * scale, Image.SCALE_SMOOTH);
        healthIC = new ImageIcon(scaledHealth);
        healthBar = new HealthBar(10, 0, gamePanel, healthIC, 4);
        gamePanel.add(healthBar);

        // Mond
        ImageIcon mIc = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Surface_Layer1.png")));
        moon = new Bild(0, gamePanel.getHeight() - mIc.getIconHeight() + 10, gamePanel, mIc);
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

        // Timer für Helper
        helperTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < allHelper.size(); i++) {
                    Figur spaceship = allHelper.get(i);
                    ImageIcon bullImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/Missile1.png")));
                    Bullet bull1 = new Bullet(spaceship.getX() + (spaceship.getImgIcon().getIconWidth() / 2), spaceship.getY(), bullImg, gamePanel, 4);
                    allBullets.add(bull1);
                    gamePanel.add(bull1);
                }
            }
        });
        helperTimer.setInitialDelay(2000);
        helperTimer.start();

        // Powerup Timer
        powerUpSpawn = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int randInt = (int) (Math.random() * 10);
                //randInt = 2;
                ImageIcon powerIc;
                int width, max, min, range, rx;
                switch (randInt) {
                    case 1:
                        powerIc = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/freeze.png")));
                        width = powerIc.getIconWidth();
                        max = gamePanel.getWidth() - width;
                        min = 1;
                        range = max - min + 1;
                        rx = (int) (Math.random() * range) + min;
                        Freeze freeze = new Freeze(rx, 0, gamePanel, powerIc, false);
                        gamePanel.add(freeze);
                        allFigures.add(freeze);
                        break;
                    case 2:
                        powerIc = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/helperPowerup.png")));
                        width = powerIc.getIconWidth();
                        max = gamePanel.getWidth() - width;
                        min = 1;
                        range = max - min + 1;
                        rx = (int) (Math.random() * range) + min;
                        HelperPowerup helperPowerup = new HelperPowerup(rx, 0, gamePanel, powerIc, false);
                        gamePanel.add(helperPowerup);
                        allFigures.add(helperPowerup);
                        break;
                }
            }
        });
        powerUpSpawn.setInitialDelay(4000);
        powerUpSpawn.start();

        // Hintergrund Timer
        backgroundTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBackground();
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
                    int max = gamePanel.getWidth() - width;
                    int min = 1;
                    int range = max - min + 1;
                    int geschAst = Math.min(6, (int) Math.ceil(anzAsteroiden / 5.0));
                    int delay = Math.max(1000, 2000 - (anzAsteroiden * 1000 / 30));
                    astTimer.setDelay(delay);
                    int rx = (int) (Math.random() * range) + min;
                    Asteroid ast1 = new Asteroid(rx, 0, gamePanel, icon1, geschAst);
                    allFigures.add(ast1);
                    gamePanel.add(ast1);
                    anzAsteroiden++;
                }
            }
        });
        astTimer.setInitialDelay(100);
        astTimer.start();
        astDelay = 10;
        astMove = new Timer(astDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveFigures();
            }
        });
        astMove.start();

        // Hintergrund
        ImageIcon backgroundimg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png")));
        background1 = new Background(0, 0, gamePanel, backgroundimg);
        gamePanel.add(background1);
        background2 = new Background(0, backgroundimg.getIconHeight() * -1, gamePanel, backgroundimg);
        gamePanel.add(background2);
    }

    private void moveFigures() {
        for (int i = 1; i < allFigures.size(); i++) {
            allFigures.get(i).move();
        }
    }

    private void updateBackground() {
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

        gamePanel.remove(background1);
        background1 = new Background(background1.getX(), background1.getY(), gamePanel, new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png"))));
        gamePanel.add(background1);
        gamePanel.remove(background2);
        background2 = new Background(background2.getX(), background2.getY(), gamePanel, new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Space_Background/Space.png"))));
        gamePanel.add(background2);
    }

    public Figur checkMoonCollision(ArrayList<Figur> allFigures, int i) {
        if (allFigures.get(i).getY() > 550 && !allFigures.get(i).isHit() && allFigures.get(i).getClass() == Asteroid.class) {
            ImageIcon ic = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/explosion.gif")));
            allFigures.get(i).setImgIcon(ic);
            return allFigures.get(i);
        }
        return null;
    }

    public void summonBullet() {
        if (Spacebar) {
            Figur spaceship = allFigures.get(0);
            ImageIcon bullImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/Missile1.png")));
            Bullet bull1 = new Bullet(spaceship.getX() + (spaceship.getImgIcon().getIconWidth() / 2), spaceship.getY(), bullImg, gamePanel, 4);
            allBullets.add(bull1);
            gamePanel.add(bull1);
        }
    }

    public void myTimer_ActionPerformed() {
        allFigures.get(0).move(keyLeft, keyRight, keyUp, keyDown);
        for (int i = 0; i < allHelper.size(); i++) {
            allHelper.get(i).move(keyLeft, keyRight, keyUp, keyDown, allFigures.get(0));
            allHelper.get(i).setY(allFigures.get(0).getY());
        }

        ImageIcon ic = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/explosion.gif")));

        // Kollision Boden

        for (int e = 1; e < allFigures.size(); e++) {
            Figur curAst = checkMoonCollision(allFigures, e);
            if (curAst != null && !debugimmortality) {
                curAst.setHit(true);
                if (healthBar.getCount() >= 4) {
                    gameOver();
                    stopAllTimers();

                    break;
                }
                ImageIcon healthIC = healthBar.delHealth();
                Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth() * scale, healthIC.getIconHeight() * scale, Image.SCALE_SMOOTH);
                healthIC = new ImageIcon(scaledHealth);
                healthBar.setImgIcon(healthIC);
            }
            if (allFigures.get(e).getY() > 600 && allFigures.get(e).getClass() == Asteroid.class) {
                allFigures.remove(e);
                gamePanel.remove(allFigures.get(e));
            }
        }

        powerupCollision();

        if (!allBullets.isEmpty()) {
            for (int i = 0; i < allBullets.size(); i++) {
                Bullet bullet = allBullets.get(i);
                allBullets.get(i).move();
                if (bullet.getY() < 1) {
                    allBullets.remove(i);
                    gamePanel.remove(bullet);
                }
            }
        }
        //Kollision Bullets Asteroid
        for (int i = 1; i < allFigures.size(); i++) {
            for (int e = 0; e < allBullets.size(); e++) {
                if (allBullets.get(e).collides(allFigures.get(i)) && !allFigures.get(i).isHit() && allFigures.get(i).getClass() != HelperPowerup.class && allFigures.get(i).getClass() != Freeze.class) {
                    aktScore++;
                    allFigures.get(i).setHit(true);
                    score.setText(Integer.toString(aktScore));
                    delFig = allFigures.get(i);
                    exTimer.restart();
                    gamePanel.remove(allBullets.get(e));
                    gamePanel.remove(allFigures.get(i));
                    allFigures.get(i).setImgIcon(ic);
                    gamePanel.add(allFigures.get(i));
                    allBullets.remove(e);
                    break;
                }
            }
        }

        int i = 0;
        //Kollision Rakete, Asteroiden
        for (int e = 1; e < allFigures.size(); e++) {
            if (allFigures.get(e).collides(allFigures.get(i)) && !debugimmortality) {
                delFig = allFigures.get(e);
                if (!allFigures.get(e).isHit()) {
                    allFigures.get(e).setHit(true);
                    if (healthBar.getCount() >= healthBar.getHealth()) {
                        gameOver();
                        stopAllTimers();
                        break;
                    }
                    ImageIcon healthIC = healthBar.delHealth();
                    Image scaledHealth = healthIC.getImage().getScaledInstance(healthIC.getIconWidth() * scale, healthIC.getIconHeight() * scale, Image.SCALE_SMOOTH);
                    healthIC = new ImageIcon(scaledHealth);
                    healthBar.setImgIcon(healthIC);

                    exTimer.restart();
                    gamePanel.remove(allFigures.get(e));
                    allFigures.get(e).setImgIcon(ic);
                    gamePanel.add(allFigures.get(e));
                }
                break;
            }
        }
    }
    public void gameOver(){
        Component[] components = gamePanel.getComponents();

        for (Component component : components) {
            if(component.getClass() != Background.class) {
                gamePanel.remove(component);
            }
        }
        ImageIcon overIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Game_Over.png")));
        JLabel overLabel = new JLabel(overIcon);
        overLabel.setBounds(50,50, overIcon.getIconWidth(), overIcon.getIconHeight());
        gamePanel.add(overLabel);

        ImageIcon againIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Play_Again.png")));
        JButton againButton = new JButton(againIcon);
        againButton.setOpaque(false);
        againButton.setContentAreaFilled(false);
        //againButton.setBorderPainted(false);
        againButton.setBounds(250,350, againIcon.getIconWidth(), againIcon.getIconHeight());
        againButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.resetGame();
            }
        });
        gamePanel.add(againButton);

    }

    private void stopAllTimers() {
        astTimer.stop();
        myTimer.stop();
        bullTimer.stop();
        helperTimer.stop();
        powerUpSpawn.stop();
    }

    public void powerupCollision() {
        for (int i = 0; i < allFigures.size(); i++) {
            if (allFigures.get(i).collides(allFigures.get(0)) && (Freeze.class == allFigures.get(i).getClass())) {
                gamePanel.remove(allFigures.get(i));
                allFigures.remove(i);

                for (int e = 0; e < allFigures.size(); e++) {
                    int geschAst = (int) ((Math.min(6, (int) Math.ceil(anzAsteroiden / 5.0))) / 4);
                    allFigures.get(i).setSpeed(geschAst);
                }
                if (resetTimer == null) {
                    resetTimer = new Timer(5000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for (int f = 0; f < allFigures.size(); f++) {
                                int geschAst = (Math.min(6, (int) Math.ceil(anzAsteroiden / 5.0)));
                                allFigures.get(f).setSpeed(geschAst);
                            }
                        }
                    });
                }
                resetTimer.restart();
            } else if (allFigures.get(i).collides(allFigures.get(0)) && (HelperPowerup.class == allFigures.get(i).getClass()) && (!allFigures.get(i).isHit())) {
                gamePanel.remove(allFigures.get(i));
                allFigures.remove(i);
                ImageIcon imgs = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/Ships/spaceship1.png")));
                int anzHelper = allHelper.size();
                if (anzHelper == 0) {
                    Helper helper1 = new Helper(allFigures.get(0).getX() - 50 - allFigures.get(0).getWidth() / 2, allFigures.get(0).getY(), gamePanel, imgs, 2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper1);
                    allHelper.add(helper1);
                } else if (anzHelper == 1) {
                    Helper helper2 = new Helper(allFigures.get(0).getX() + 50 + allFigures.get(0).getWidth(), allFigures.get(0).getY(), gamePanel, imgs, 2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper2);
                    allHelper.add(helper2);
                } else if (anzHelper == 2) {
                    Helper helper3 = new Helper(allHelper.get(0).getX() - 50 - allFigures.get(0).getWidth() / 2, allFigures.get(0).getY(), gamePanel, imgs, 2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper3);
                    allHelper.add(helper3);
                } else if (anzHelper == 3) {
                    Helper helper4 = new Helper(allHelper.get(1).getX() + 50 + allFigures.get(0).getWidth() / 2, allFigures.get(0).getY(), gamePanel, imgs, 2, allFigures.get(0).getXr(), allFigures.get(0).getYr());
                    gamePanel.add(helper4);
                    allHelper.add(helper4);
                }
            }
        }
    }


    public JPanel getMainPanel(){
        return mainPanel;
    }

    public void updateKeys(boolean keyLeft, boolean keyRight, boolean keyDown, boolean keyUp, boolean Spacebar){
        GameGUI.keyLeft = keyLeft;
        GameGUI.keyRight = keyRight;
        GameGUI.keyDown = keyDown;
        GameGUI.keyUp = keyUp;
        GameGUI.Spacebar = Spacebar;
    }

}
