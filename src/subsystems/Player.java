// Define the package folder name for this code file
package subsystems;

// Import the Graphics2D class to paint pixels
import java.awt.Graphics2D;

// Import the Rectangle class to create hitboxes
import java.awt.Rectangle;

//Imports the constants class
import constants.Constants;

// Define a public class named Player
public class Player {
    
    // Hold the horizontal position of the player ship
    public int x;
    
    // Hold the vertical position of the player ship
    public int y;
    
    // Set the static width of the ship
    public final int width = Constants.PlayerConstants.kPlayerWidth;
    
    // Set the static height of the ship
    public final int height = Constants.PlayerConstants.kPlayerHeight;
    
    // Set how many pixels the player glides per keystroke
    public final int speed = Constants.PlayerConstants.kPlayerSpeed;

    // Create a constructor to set up a brand new player position
    public Player(int startX, int startY) {
        
        // Save the starting horizontal coordinate to our field variable
        this.x = startX;
        
        // Save the starting vertical coordinate to our field variable
        this.y = startY;
    }

    // Slide the player ship to the left edge of the screen
    public void moveLeft() {
        
        // Read the current horizontal position coordinate
        int currentX = x;
        
        // Check if the ship is clear of the left boundary wall
        boolean clearOfLeftWall = (currentX > 0);
        
        // Run code block if left movement space is allowed
        if (clearOfLeftWall) {
            
            // Subtract the speed value away from our horizontal coordinate
            this.x -= speed;
        }
    }

    // Slide the player ship to the right edge of the screen safely
    public void moveRight(int screenWidth) {
        
        // Read the current horizontal position coordinate
        int currentX = x;
        
        // Read the fixed width size parameter of this ship
        int playerWidth = width;
        
        // Calculate the maximum horizontal point the ship can slide to
        int rightBoundaryLimit = screenWidth - playerWidth;
        
        // Check if our coordinate sits behind that maximum safe wall line
        boolean clearOfRightWall = (currentX < rightBoundaryLimit);
        
        // Run code block if right movement space is allowed
        if (clearOfRightWall) {
            
            // Add the speed value forward onto our horizontal coordinate
            this.x += speed;
        }
    }

    // Gather a temporary box shape tracking the current position boundaries
    public Rectangle getBounds() {
        
        // Read the current horizontal position parameter
        int currentX = x;
        
        // Read the current vertical position parameter
        int currentY = y;
        
        // Read the ship width parameter
        int currentWidth = width;
        
        // Read the ship height parameter
        int currentHeight = height;
        
        // Build a fresh rectangle object layout shell matching those sizes
        Rectangle boundsShell = new Rectangle(currentX, currentY, currentWidth, currentHeight);
        
        // Return the constructed rectangle back to the tracking systems
        return boundsShell;
    }

    // Draw the main player ship and its top blaster cannon tip
    public void draw(Graphics2D g2d) {
           
        // Set the active painter tool color brush selector
        g2d.setColor(Constants.PlayerConstants.kPlayerColor);
        
        // Read the current horizontal position variable
        int mainBodyX = x;
        
        // Read the current vertical position variable
        int mainBodyY = y;
        
        // Read the ship width variable
        int mainBodyWidth = width;
        
        // Read the ship height variable
        int mainBodyHeight = height;
        
        // Draw the primary flat horizontal green hull base box
        g2d.fillRect(mainBodyX, mainBodyY, mainBodyWidth, mainBodyHeight);       
        
        // Add twenty pixel spacing forward to locate the turret x center spot
        int turretX = mainBodyX + Constants.PlayerConstants.kTurretXOffset;
        
        // Subtract eight pixels upward to position the turret y elevation peak
        int turretY = mainBodyY - Constants.PlayerConstants.kTurretYOffset;
        
        // Set the distinct structural width sizing for the turret barrel block
        int turretWidth = Constants.PlayerConstants.kTurretWidth;
        
        // Set the distinct structural height sizing for the turret barrel block
        int turretHeight = Constants.PlayerConstants.kTurretHeight;
        
        // Draw the small green block tip representing the laser shooter nose
        g2d.fillRect(turretX, turretY, turretWidth, turretHeight);      
    }
}
