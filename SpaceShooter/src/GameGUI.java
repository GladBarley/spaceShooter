package FlummisJakeJaeger;

import com.sun.org.apache.bcel.internal.generic.IADD;

import javax.swing.*;
import java.awt.event.*;

public class GameGUI {
    private JPanel mainPanel;
    private JPanel gamePanel;

    private int anzFiguren = 0;
    private int maxFiguren = 8;
    private Figur[] alleFiguren = new aFigur[maxFiguren]; // [0] - Jake; [1..max] - Flummis
    private static boolean keyLeft, keyRight, keyDown, keyUp;

    private Timer myTimer;
    private Timer flummiTimer;

    public GameGUI() {
        gamePanel.setBounds(0,0,800,600);
        gamePanel.setLayout(null);

        // Jake laden
        ImageIcon JakeR = new ImageIcon("src/FlummisJakeJaeger/images/runR.gif");
        ImageIcon JakeL = new ImageIcon("src/FlummisJakeJaeger/images/runL.gif");
        Jake jake = new Jake(gamePanel.getWidth()/2,gamePanel.getHeight()/2,JakeL.getIconWidth(),JakeR.getIconHeight(),gamePanel.getWidth(),gamePanel.getHeight(),JakeR,JakeL);
        alleFiguren[0] = jake;
        anzFiguren++;
        gamePanel.add(jake);
        // Jaeger laden
        ImageIcon JaegerI = new ImageIcon("src/FlummisJakeJaeger/images/smile4.png");
        Jaeger jaeger = new Jaeger(0,0,JaegerI.getIconWidth(), JaegerI.getIconHeight(),gamePanel.getWidth(), gamePanel.getHeight(),JaegerI,jake);
        alleFiguren[anzFiguren] = jaeger;
        anzFiguren++;
        gamePanel.add(jaeger);


        // Timer
        myTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                myTimer_ActionPerformed(evt);
            }
        });
        myTimer.setInitialDelay(100);
        myTimer.start();
        flummiTimer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (anzFiguren < maxFiguren) {
                    int z = (int) (Math.random()*4);
                    ImageIcon icon1 = new ImageIcon("src/FlummisJakeJaeger/images/smile" + z + ".png");
                    int height = icon1.getIconHeight();
                    int width = icon1.getIconWidth();
                    Figur aFlummi = new Flummi(10, 10, width, height, gamePanel.getWidth(), gamePanel.getHeight(), icon1);
                    alleFiguren[anzFiguren] = aFlummi;
                    gamePanel.add(aFlummi);
                    anzFiguren++;
                }
            }
        });
        flummiTimer.setInitialDelay(100);
        flummiTimer.start();
    }

    public void myTimer_ActionPerformed(ActionEvent evt) {
        // Bewegen von Jake
        alleFiguren[0].move(keyLeft,keyRight,keyUp,keyDown);
        // alle Figuren (außer Jake) bewegen
        for (int i=1; i<anzFiguren; i++){
            alleFiguren[i].move();
        }
        // Test auf Kollisionen
        // Jake ... darf nicht
        for (int i = 1; i < anzFiguren; i++) {
            if (alleFiguren[0].istKollision(alleFiguren[i])) {
                myTimer.stop();
                flummiTimer.stop();
            }
        }
        // Kollision der Flummis untereinander?
        for (int i = 1; i < anzFiguren-1; i++) {
            if (alleFiguren[i] instanceof Flummi) {
                for (int j = i + 1; j < anzFiguren; j++) {
                    if (alleFiguren[i].istKollision(alleFiguren[j])) {
                        if (alleFiguren[j] instanceof Flummi) {
                            alleFiguren[i].abprallen(alleFiguren[j]);
                        } else {
                            alleFiguren[j].abprallen(alleFiguren[i]);
                        }
                    }
                }
            }
        }
        gamePanel.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ein Flummi ... und seine Freunde");
        frame.setContentPane(new FlummisJakeJaegerGUI().mainPanel);
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
            }
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT) keyLeft = false;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT) keyRight = false;
                if (event.getKeyCode() == KeyEvent.VK_UP) keyUp = false;
                if (event.getKeyCode() == KeyEvent.VK_DOWN) keyDown = false;
            }
            @Override
            public void keyTyped(KeyEvent event) { }
        });
        frame.setVisible(true);
    }
}
