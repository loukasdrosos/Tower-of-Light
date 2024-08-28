package main;

import javax.swing.JFrame;

public class Main {

    public static void main (String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close window when 'X' is pressed
        window.setResizable(false);                             // Window can't be resized
        window.setTitle("Tower of Light");                      // Window name

        // Add GamePanel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();                                          // Size window to preferred size

        window.setLocationRelativeTo(null);                     // Window displayed at screen center
        window.setVisible(true);                                // Window is visible

        gamePanel.launchGame();                                 // Launch the Game
    }
}
