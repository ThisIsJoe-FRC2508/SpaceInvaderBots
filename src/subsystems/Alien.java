// Define the package name for this code
package subsystems;

// Import the Graphics2D class to draw shapes
import java.awt.Graphics2D;

// Import the Rectangle class to handle hitboxes
import java.awt.Rectangle;

//Imports the constants class
import constants.Constants;

// Define a public class named Alien
public class Alien {
    
    // Hold the horizontal position of the alien
    public int x;
    
    // Hold the vertical position of the alien
    public int y;
    
    // Set the width of the alien
    public final int width = Constants.AlienConstants.kAlienWidth;

    // Set the height of the alien
    public final int height = Constants.AlienConstants.kAlienHeight;

    // Create a constructor to make a new Alien object
    public Alien(int startX, int startY) {
        
        // Assign the starting X position
        this.x = startX;
        
        // Assign the starting Y position
        this.y = startY;
    }

    // Move the alien left or right
    public void moveHorizontal(int direction, int speed) {
        
        // Multiply direction by speed to get the distance
        int movement = direction * speed;
        
        // Add that distance to the current X position
        this.x += movement;
    }

    // Move the alien down the screen
    public void dropDown(int distance) {
        
        // Add the distance to the current Y position
        this.y += distance;
    }

    // Get a rectangle that matches the alien's size and position
    public Rectangle getBounds() {
        
        // Create a new Rectangle object using the positions and sizes
        Rectangle bounds = new Rectangle(x, y, width, height);
        
        // Return the created rectangle
        return bounds;
    }

    // Draw the alien on the screen
    public void draw(Graphics2D g2d) {
           
        // Set the graphics tool to use the red color
        g2d.setColor(Constants.AlienConstants.kAlienColor);
        
        // Draw a solid rectangle using the alien's position and size
        g2d.fillRect(x, y, width, height);
    }
}