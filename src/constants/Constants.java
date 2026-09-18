/* The Constants class provides a convenient place for teams to hold constans.
 * This class should not be used for any other purpose
 */

 package constants;

// Import the Color class to choose colors
import java.awt.Color;

public final class Constants {
    //subsystem Alien
    public static final class AlienConstants{
        public static final int kAlienWidth = 40; // Set the width of the alien
        public static final int kAlienHeight = 30; // Set the height of the alien
        // https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html
        public static final Color kAlienColor = Color.RED; // Pick the color red for drawing
    }
        
    public static final class LaserConstants{
        public static final int kLaserWidth = 4; // Set the static width of the beam to four pixels
        public static final int kLaserHeight = 15; // Set the static height of the beam to fifteen pixels
        public static final int kLaserSpeed = 6; // set the static travel speed velocity to 6 pixels per frame
        // https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html
        public static final Color kPlayerLaserColor = Color.CYAN; // Pick the color red for drawing
        public static final Color kAlienLaserColor = Color.ORANGE; // Pick the color red for drawing
    }

    public static final class PlayerConstants{
        public static final int kPlayerWidth = 50; // Set the static width of the ship to fifty pixels
        public static final int kPlayerHeight = 30; // Set the static height of the ship to thirty pixels
        public static final int kPlayerSpeed = 4; // Set how many pixels the player glides per keystroke
        // https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html
        public static final Color kPlayerColor = Color.GREEN; // Pick the color green for drawing
        public static final int kTurretXOffset = 20; // Add twenty pixel spacing forward to locate the turret x center spot
        public static final int kTurretYOffset = 8; /// Subtract eight pixels upward to position the turret y elevation peak
        public static final int kTurretWidth = 10; // Set the distinct structural width sizing for the turret barrel block
        public static final int kTurretHeight = 8; // Set the distinct structural height sizing for the turret barrel block  
    }    
    public static final class GamePanelConstants{
        public static final int kWindowWidth = 800; // Set the overall pixel width of the window
        public static final int kWindowHeight = 600; // Set the overall pixel height of the window
        public static final int kStartingScore = 0; // Track the player's accumulated game points
        public static final int kAlienStartingDirection = 1; //1 is right
        public static final int kAlienSpeed = 2; // Set how many pixels an alien moves each frame
        public static final int kAlienDrop = 15; // Set how far down aliens drop when hitting a wall
        public static final Color kBackgroundColor = Color.BLACK; // Select the color black for our base background
        public static final int kGameRefreshRate = 16; // Create a timer that fires every 16 milliseconds
        public static final int kNumberOfEnemyRows = 5; /// Subtract eight pixels upward to position the turret y elevation peak
        public static final int kNumberOfEnemyColumns = 10; // Set the distinct structural width sizing for the turret barrel block
        public static final double kChanceToFire = 0.02 ; // Set the chance the alien will fire a lasere
        public static final int kPointsPerKill = 10; // Points per kill
    }
}