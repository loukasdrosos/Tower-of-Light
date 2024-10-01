package main;

import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static void main (String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);  // Window can't be resized
        window.setTitle("Tower of Light");  // Set window title

        // Set custom icon for the window
        Image icon = Toolkit.getDefaultToolkit().getImage("res/Icon/Icon.png");
        window.setIconImage(icon);

        // Add GamePanel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // Add the GamePanel instance to the JFrame window
        window.pack();  // Size window to preferred size

        window.setLocationRelativeTo(null);  // Window displayed at screen center
        window.setVisible(true);   // Window is visible on screen

        gamePanel.setupGame();
        gamePanel.launchGame(); // Launch the Game
    }
}
