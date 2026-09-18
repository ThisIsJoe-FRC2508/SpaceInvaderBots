// Import the KeyAdapter class to listen to the keyboard
import java.awt.event.KeyAdapter;

// Import the KeyEvent class to read specific keys
import java.awt.event.KeyEvent;

// Import the List tool to manage lists of objects
import java.util.List;

// Import the Player class from the subsystems folder
import subsystems.Player;

// Import the Laser class from the subsystems folder
import subsystems.Laser;

// Create a class to handle keyboard controls for the game
public class InputHandler extends KeyAdapter {
    
    // Track if the player wants to move left
    private boolean moveLeft = false;
    
    // Track if the player wants to move right
    private boolean moveRight = false;
    
    // Hold a reference to the main game screen
    private GamePanel game;

    // Create a constructor to set up the input handler
    public InputHandler(GamePanel game) {
        
        // Save the game panel reference into our variable
        this.game = game;
    }

    // Check if the left movement flag is true
    public boolean isMovingLeft() { 
        
        // Return the left movement status
        return moveLeft; 
    }

    // Check if the right movement flag is true
    public boolean isMovingRight() { 
        
        // Return the right movement status
        return moveRight; 
    }

    // Run this block when a keyboard key is pressed down
    @Override
    public void keyPressed(KeyEvent e) {
        
        // Get the numerical code of the pressed key
        int key = e.getKeyCode();
        
        // Check if the player pressed the Left Arrow key
        boolean pressedLeftArrow = (key == KeyEvent.VK_LEFT);
        
        // Check if the player pressed the A key
        boolean pressedA = (key == KeyEvent.VK_A);
        
        // Combine the left checks to see if either is true
        boolean shouldMoveLeft = pressedLeftArrow || pressedA;
        
        // Check if the left movement condition is met
        if (shouldMoveLeft) {
            
            // Set the left movement flag to true
            moveLeft = true;
        }
        
        // Check if the player pressed the Right Arrow key
        boolean pressedRightArrow = (key == KeyEvent.VK_RIGHT);
        
        // Check if the player pressed the D key
        boolean pressedD = (key == KeyEvent.VK_D);
        
        // Combine the right checks to see if either is true
        boolean shouldMoveRight = pressedRightArrow || pressedD;
        
        // Check if the right movement condition is met
        if (shouldMoveRight) {
            
            // Set the right movement flag to true
            moveRight = true;
        }
        
        // Check if the player pressed the Spacebar
        boolean pressedSpace = (key == KeyEvent.VK_SPACE);
        
        // Check if the game is currently over
        boolean over = game.isGameOver();
        
        // Flip the game over check to see if the game is still active
        boolean active = !over;
        
        // Check if space was pressed while the game is active
        boolean shouldShoot = pressedSpace && active;
        
        // Check if the shooting condition is met
        if (shouldShoot) {
            
            // Get the player object from the game panel
            Player p = game.getPlayer();
            
            // Get the list of active lasers from the game panel
            List<Laser> lasers = game.getLasers();
            
            // Get the player's horizontal position
            int playerX = p.x;
            
            // Get the player's width
            int playerWidth = p.width;
            
            // Divide the player's width by two to find the center
            int halfWidth = playerWidth / 2;
            
            // Add the half-width to the player's X position
            int centerX = playerX + halfWidth;
            
            // Subtract two to center the laser properly
            int laserX = centerX - 2;
            
            // Get the player's vertical position for the laser spawn
            int laserY = p.y;
            
            // Set the flag to make the laser move upward
            boolean movingUp = true;
            
            // Create a brand new Laser object with the calculated values
            Laser newLaser = new Laser(laserX, laserY, movingUp);
            
            // Add the new laser to the game's laser list
            lasers.add(newLaser);
        }
        
        // Check if the player pressed the R key
        boolean pressedR = (key == KeyEvent.VK_R);
        
        // Check if the game is over
        boolean gameOverNow = game.isGameOver();
        
        // Check if R was pressed while the game is over
        boolean shouldReset = pressedR && gameOverNow;
        
        // Check if the reset condition is met
        if (shouldReset) {
            
            // Restart and reset the game variables
            game.initGame();
        }
    }

    // Run this block when a keyboard key is let go
    @Override
    public void keyReleased(KeyEvent e) {
        
        // Get the numerical code of the released key
        int key = e.getKeyCode();
        
        // Check if the released key was the Left Arrow key
        boolean releasedLeftArrow = (key == KeyEvent.VK_LEFT);
        
        // Check if the released key was the A key
        boolean releasedA = (key == KeyEvent.VK_A);
        
        // Combine the left checks to see if either is true
        boolean stoppedLeft = releasedLeftArrow || releasedA;
        
        // Check if the left stop condition is met
        if (stoppedLeft) {
            
            // Set the left movement flag to false
            moveLeft = false;
        }
        
        // Check if the released key was the Right Arrow key
        boolean releasedRightArrow = (key == KeyEvent.VK_RIGHT);
        
        // Check if the released key was the D key
        boolean releasedD = (key == KeyEvent.VK_D);
        
        // Combine the right checks to see if either is true
        boolean stoppedRight = releasedRightArrow || releasedD;
        
        // Check if the right stop condition is met
        if (stoppedRight) {
            
            // Set the right movement flag to false
            moveRight = false;
        }
    }
}