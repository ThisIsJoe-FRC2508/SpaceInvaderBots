// Define the package folder name for this code file
package subsystems;

// Import the Graphics2D class to render pixels
import java.awt.Graphics2D;

// Import the Rectangle class to compute hitboxes
import java.awt.Rectangle;

//Imports the constants class
import constants.Constants;

// Define a public class named Laser
public class Laser {
    
    // Hold the horizontal position of the traveling laser beam
    public int x;
    
    // Hold the vertical position of the traveling laser beam
    public int y;
    
    // Set the static width of the beam
    public final int width = Constants.LaserConstants.kLaserWidth;
    
    // Set the static height of the beam
    public final int height = Constants.LaserConstants.kLaserHeight;
    
    // Set the static travel speed velocity to pixels per frame
    public final int speed = Constants.LaserConstants.kLaserSpeed;
    
    // Track if this beam came from the player or an alien invader
    public boolean isPlayerLaser;

    // Create a constructor to set up a brand new projectile instance
    public Laser(int startX, int startY, boolean isPlayerLaser) {
        
        // Save the starting horizontal coordinate to our field variable
        this.x = startX;
        
        // Save the starting vertical coordinate to our field variable
        this.y = startY;
        
        // Save the ownership flag status to our field variable
        this.isPlayerLaser = isPlayerLaser;
    }

    // Advance the vertical position of the projectile across the board
    public void update() {
        
        // Read the ownership flag state of the laser beam
        boolean friendly = this.isPlayerLaser;
        
        // Run proper movement calculation based on ownership state
        if (friendly) {
            
            // Subtract speed to move the laser upward toward enemies
            this.y -= speed; 
        } else {
            
            // Add speed to move the laser downward toward the player
            this.y += speed; 
        }
    }

    // Check if the laser has flown completely past the visible screen borders
    public boolean isOutOfBounds(int screenHeight) {
        
        // Read the current vertical position metric of the laser
        int currentY = this.y;
        
        // Check if the laser went past the absolute top of the screen
        boolean pastTopEdge = (currentY < 0);
        
        // Check if the laser went past the absolute bottom screen border height
        boolean pastBottomEdge = (currentY > screenHeight);
        
        // Combine both boundary tests to see if either condition is true
        boolean out = pastTopEdge || pastBottomEdge;
        
        // Return the final out of bounds verification boolean state flag
        return out;
    }

    // Gather a temporary box shape tracking the current position boundaries
    public Rectangle getBounds() {
        
        // Read the current horizontal position parameter
        int currentX = this.x;
        
        // Read the current vertical position parameter
        int currentY = this.y;
        
        // Read the fixed width size attribute
        int currentWidth = this.width;
        
        // Read the fixed height size attribute
        int currentHeight = this.height;
        
        // Build a fresh rectangle object layout shell matching those sizes
        Rectangle boundsShell = new Rectangle(currentX, currentY, currentWidth, currentHeight);
        
        // Return the constructed rectangle back to the collision checking engine
        return boundsShell;
    }

    // Draw the laser beam with a custom color based on who fired it
    public void draw(Graphics2D g2d) {
        
        // Read the ownership flag state of the laser beam
        boolean friendly = this.isPlayerLaser;
        
        // Route correct visual color properties based on ownership state
        if (friendly) {
              
            // Apply color onto the active drawing engine context brush
            g2d.setColor(Constants.LaserConstants.kPlayerLaserColor);
        } else {
            
            // Apply color onto the active drawing engine context brush
            g2d.setColor(Constants.LaserConstants.kAlienLaserColor);
        }
        
        // Read the horizontal placement layout coordinate variable
        int drawX = this.x;
        
        // Read the vertical placement layout coordinate variable
        int drawY = this.y;
        
        // Read the width dimension specification variable
        int drawWidth = this.width;
        
        // Read the height dimension specification variable
        int drawHeight = this.height;
        
        // Paint a solid colored rectangle beam stripe down onto the panel screen
        g2d.fillRect(drawX, drawY, drawWidth, drawHeight);
    }
}