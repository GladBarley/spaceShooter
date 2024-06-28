import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    private JFrame frame;
    private GameGUI gameGUI;
    boolean keyLeft, keyRight, keyDown, keyUp, Spacebar;

    public Main() {
        frame = new JFrame("Der wilde Space Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        gameGUI = new GameGUI(this);
        frame.setContentPane(gameGUI.getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) keyLeft = true;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) keyRight = true;
                if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) keyUp = true;
                if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) keyDown = true;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = true;
                gameGUI.updateKeys(keyLeft, keyRight, keyDown, keyUp, Spacebar);
            }
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) keyLeft = false;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) keyRight = false;
                if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) keyUp = false;
                if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) keyDown = false;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = false;
                gameGUI.updateKeys(keyLeft, keyRight, keyDown, keyUp, Spacebar);
            }
            @Override
            public void keyTyped(KeyEvent event) { }
        };
        frame.addKeyListener(keyListener);
        frame.setVisible(true);
    }

    public void resetGame() {
        frame = new JFrame("Der wilde Space Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        gameGUI = new GameGUI(this);
        frame.setContentPane(gameGUI.getMainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) keyLeft = true;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) keyRight = true;
                if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) keyUp = true;
                if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) keyDown = true;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = true;
                gameGUI.updateKeys(keyLeft, keyRight, keyDown, keyUp, Spacebar);
            }
            @Override
            public void keyReleased(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_A) keyLeft = false;
                if (event.getKeyCode() == KeyEvent.VK_RIGHT || event.getKeyCode() == KeyEvent.VK_D) keyRight = false;
                if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) keyUp = false;
                if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) keyDown = false;
                if (event.getKeyCode() == KeyEvent.VK_SPACE) Spacebar = false;
                gameGUI.updateKeys(keyLeft, keyRight, keyDown, keyUp, Spacebar);
            }
            @Override
            public void keyTyped(KeyEvent event) { }
        };
        frame.addKeyListener(keyListener);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}