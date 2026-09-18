import javax.swing.JFrame;

/**
 * The SpaceInvaderBots class creates the main window (frame) for the game.
 * It inherits from JFrame, which is a standard window in Java's Swing library.
 */
// https://docs.oracle.com/javase/8/docs/api/javax/swing/JPanel.html
public class SpaceInvaderBots extends JFrame {

    /**
     * Constructor for the SpaceInvaderBots class.
     * This sets up the window properties and displays the game.
     */
    public SpaceInvaderBots() {
        // Sets the text that appears in the title bar at the top of the window
        setTitle("Space Invader Bots");

        // Tells the program to completely close and stop running when the user clicks the 'X' button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevents the user from resizing the window so the game layout stays perfectly aligned
        setResizable(false);

        // Creates a new instance of the GamePanel, which holds the actual game logic and graphics
        GamePanel gamePanel = new GamePanel();

        // Adds the game panel inside this window frame so players can see it
        add(gamePanel);

        // Automatically sizes the window so that it fits the preferred size of the GamePanel perfectly
        pack();

        // Centers the window perfectly in the middle of the user's computer screen
        setLocationRelativeTo(null);

        // Makes the window visible on the screen so the user can start playing
        setVisible(true);
    }
}
