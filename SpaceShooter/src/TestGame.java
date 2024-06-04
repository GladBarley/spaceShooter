import javax.swing.*;
import java.awt.*;

public class TestGame {

    private JPanel mainPanel;
    private JPanel gamePanel;

    public TestGame() {

        gamePanel.setBounds(0,0,800,600);
        gamePanel.setLayout(null);

        // Set preferred size for gamePanel
        //gamePanel.setPreferredSize(new Dimension(800, 600)); // Set the desired width and height

        // Add gamePanel to mainPanel or set mainPanel layout and add other components if needed

    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Der wilde Space Shooter");

        frame.setContentPane(new TestGame().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
