// Import the swing tools for graphics windows
import javax.swing.JPanel;

// Import the timer tool to create game loops
import javax.swing.Timer;

import constants.Constants;

// Import the color tool to style visuals
import java.awt.Color;

// Import the dimension tool to manage screen sizing
import java.awt.Dimension;

// Import the general graphics drawing tools
import java.awt.Graphics;

// Import the advanced 2D drawing engine
import java.awt.Graphics2D;

// Import the rectangle tool for hitboxes
import java.awt.Rectangle;

// Import the font tool to style on-screen text
import java.awt.Font;

// Import the rendering hints tool to smooth pixels
import java.awt.RenderingHints;

// Import the action event tool to read timer updates
import java.awt.event.ActionEvent;

// Import the action listener tool to track timer events
import java.awt.event.ActionListener;

// Import the array list tool to hold game objects
import java.util.ArrayList;

// Import the iterator tool to safely loop through lists
import java.util.Iterator;

// Import the list manager tool to group items
import java.util.List;

// Import the Player class from the subsystems folder
import subsystems.Player;

// Import the Alien class from the subsystems folder
import subsystems.Alien;

// Import the Laser class from the subsystems folder
import subsystems.Laser;

// Create the main game background panel class
public class GamePanel extends JPanel implements ActionListener {
    
    // Set the overall pixel width of the window
    private static final int WIDTH = Constants.GamePanelConstants.kWindowWidth;
    
    // Set the overall pixel height of the window
    private static final int HEIGHT = Constants.GamePanelConstants.kWindowHeight;
    
    // Create a variable to hold our game loop timer
    private Timer gameTimer;       
    
    // Create a variable to hold the controllable player
    private Player player;         
    
    // Create a list variable to track active alien targets
    private List<Alien> aliens;    
    
    // Create a list variable to track active lasers
    private List<Laser> lasers;    
    
    // Create a variable to plug in our input tracker
    private InputHandler input; 
    
    // Track if the gameplay state is currently finished
    private boolean isGameOver = false; 
    
    // Track the player's accumulated game points
    private int score = Constants.GamePanelConstants.kStartingScore;              
    
    // Set horizontal movement direction where 1 means right
    private int alienDirection = Constants.GamePanelConstants.kAlienStartingDirection;     
    
    // Set how many pixels an alien moves each frame
    private final int alienSpeed = Constants.GamePanelConstants.kAlienSpeed;    
    
    // Set how far down aliens drop when hitting a wall
    private final int alienDropDistance = Constants.GamePanelConstants.kAlienDrop; 

    // Create a constructor to set up our panel settings
    public GamePanel() {
        
        // Define the screen sizes using our width and height
        Dimension panelSize = new Dimension(WIDTH, HEIGHT);
        
        // Set this game screen to use those specific sizes
        setPreferredSize(panelSize);
        
        // Paint the background color onto the screen
        setBackground(Constants.GamePanelConstants.kBackgroundColor);
        
        // Allow this panel to listen for key presses
        setFocusable(true);
        
        // Make a new control helper tied to this panel
        InputHandler controller = new InputHandler(this);
        
        // Save the control helper to our field variable
        this.input = controller;
        
        // Register the key listener onto the panel
        addKeyListener(input);
        
        // Call our custom reset method to build the objects
        initGame();

        // Create a timer that fires every 16 milliseconds
        Timer timerSetup = new Timer(Constants.GamePanelConstants.kGameRefreshRate, this);
        
        // Save the setup timer into our main game variable
        gameTimer = timerSetup;
        
        // Activate the timer loop to run the game frames
        gameTimer.start();
    }

    // Give outside classes a way to read the player object
    public Player getPlayer() { 
        
        // Return the current player reference
        return player; 
    }

    // Give outside classes a way to access active lasers
    public List<Laser> getLasers() { 
        
        // Return the master laser collection list
        return lasers; 
    }

    // Give outside classes a way to check if the match ended
    public boolean isGameOver() { 
        
        // Return the current status of the game over flag
        return isGameOver; 
    }

    // Set up or restart all our core game positions
    public void initGame() {
        
        // Find the horizontal middle index of the screen
        int midWidth = WIDTH / 2;
        
        // Subtract half of the player size to center it perfectly
        int playerStartX = midWidth - 25;
        
        // Calculate the player's bottom screen height placement
        int playerStartY = HEIGHT - 50;
        
        // Build a fresh player instance at those coordinates
        Player newPlayer = new Player(playerStartX, playerStartY);
        
        // Assign the new player instance to our field variable
        player = newPlayer;
        
        // Make an empty array list for the alien horde
        ArrayList<Alien> alienList = new ArrayList<>();
        
        // Assign the empty list to our alien tracker field
        aliens = alienList;
        
        // Make an empty array list for the lasers
        ArrayList<Laser> laserList = new ArrayList<>();
        
        // Assign the empty list to our laser tracker field
        lasers = laserList;
        
        // Reset the score counter back down to zero
        score = Constants.GamePanelConstants.kStartingScore;
        
        // Toggle the game over safety flag back to false
        isGameOver = false;
        
        // Force the initial alien march heading to go right
        alienDirection = Constants.GamePanelConstants.kAlienStartingDirection;

        // Set the amount of horizontal rows of enemies
        int rows = Constants.GamePanelConstants.kNumberOfEnemyRows;
        
        // Set the amount of vertical columns of enemies
        int cols = Constants.GamePanelConstants.kNumberOfEnemyColumns;
        
        // Begin looping through each individual row index
        for (int r = 0; r < rows; r++) {
            
            // Begin looping through each column index within the row
            for (int c = 0; c < cols; c++) {
                
                // Multiply column step by the grid width padding
                int colSpacing = c * 60;
                
                // Add a starting indent margin for the horizontal point
                int alienX = 80 + colSpacing;
                
                // Multiply row step by the grid height padding
                int rowSpacing = r * 40;
                
                // Add a starting top margin for the vertical point
                int alienY = 50 + rowSpacing;
                
                // Create a single enemy target at those grid coordinates
                Alien singleAlien = new Alien(alienX, alienY);
                
                // Add the newly created enemy inside our master tracker list
                aliens.add(singleAlien);
            }
        }
    }

    // Listen for updates pushed by the game loop timer
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Check if the game is currently actively running
        boolean matchRunning = !isGameOver;
        
        // Proceed with updates if the game is active
        if (matchRunning) {
            
            // Recalculate where the player should move
            updatePlayerMovement();
            
            // Recalculate positions for all traveling lasers
            updateLasersMovement();
            
            // Recalculate coordinates for all invading aliens
            updateAliensMovement();
            
            // Check if any lasers collided with other sprites
            checkCollisions();
        }
        
        // Request the windowing system to redraw the screen graphics
        repaint();
    }

    // Process movement changes specifically for the player ship
    private void updatePlayerMovement() {
        
        // Read the controller to see if the left key is held
        boolean goingLeft = input.isMovingLeft();
        
        // Run code block if left movement is registered
        if (goingLeft) {
            
            // Trigger the internal move left logic on the player
            player.moveLeft();
        }
        
        // Read the controller to see if the right key is held
        boolean goingRight = input.isMovingRight();
        
        // Run code block if right movement is registered
        if (goingRight) {
            
            // Trigger the player move right logic with screen boundaries
            player.moveRight(WIDTH);
        }
    }

    // Process positional shifts for all projectiles in flight
    private void updateLasersMovement() {
        
        // Pull out a safety iterator loop handler from our list
        Iterator<Laser> it = lasers.iterator();
        
        // Keep looping as long as there is an unread item ahead
        while (it.hasNext()) {
            
            // Pick out the current single laser container object
            Laser laser = it.next();
            
            // Advance the laser's coordinates forward based on velocity
            laser.update();
            
            // Check if the laser moved completely past screen borders
            boolean vanished = laser.isOutOfBounds(HEIGHT);
            
            // Process removal if the laser left the visible space
            if (vanished) {
                
                // Safely extract the laser out of our master list tracking
                it.remove();
            }
        }
    }

    // Process shifts, screen boundary bounces, and shots for invaders
    private void updateAliensMovement() {
        
        // Track whether any alien bumped into an outer wall boundary
        boolean shiftDown = false;

        // Loop through every invader instance currently in our list
        for (Alien alien : aliens) {
            
            // Slide the alien sideways based on direction and speed metrics
            alien.moveHorizontal(alienDirection, alienSpeed);
            
            // Read the individual alien's horizontal left coordinate
            int ax = alien.x;
            
            // Check if the alien crossed past the left screen border
            boolean hitLeftWall = (ax <= 0);
            
            // Read the invader's width attribute
            int aw = alien.width;
            
            // Calculate where the right edge of the invader sits
            int rightEdge = WIDTH - aw;
            
            // Check if the alien crossed past the right screen border
            boolean hitRightWall = (ax >= rightEdge);
            
            // Determine if either wall boundary was triggered
            boolean hitWall = hitLeftWall || hitRightWall;
            
            // Run logic adjustment if an outer boundary was touched
            if (hitWall) {
                
                // Flag that the complete row must slide downward next
                shiftDown = true;
            }
        }

        // Process grid adjustment drops if a wall bounce happened
        if (shiftDown) {
            
            // Reverse the horizontal direction factor using multiplication
            alienDirection *= -1;
            
            // Loop through all the aliens to push them downward
            for (Alien alien : aliens) {
                
                // Apply the dropping position offset onto the alien
                alien.dropDown(alienDropDistance);
                
                // Read the alien's vertical height placement coordinate
                int ay = alien.y;
                
                // Read the physical size height value of the alien
                int ah = alien.height;
                
                // Calculate where the bottom edge of the alien sits
                int alienBottom = ay + ah;
                
                // Read the vertical line placement of the player ship
                int playerY = player.y;
                
                // Check if the alien descended past the player line
                boolean breached = (alienBottom >= playerY);
                
                // Trigger terminal state if an enemy bypassed defenses
                if (breached) {
                    
                    // Mark the state variable to end the active game
                    isGameOver = true;
                }
            }
        }

        // Generate a random double calculation step
        double roll = Math.random();
        
        // Check if the probability roll falls beneath two percent
        boolean logicRoll = (roll < Constants.GamePanelConstants.kChanceToFire);
        
        // Check if there are active invaders remaining to execute shots
        boolean targetExists = !aliens.isEmpty();
        
        // Combine the rate roll and list availability verification states
        boolean alienShouldShoot = logicRoll && targetExists;
        
        // Execute an enemy laser attack if requirements pass
        if (alienShouldShoot) {
            
            // Generate a random double up to the total list count size
            double indexRoll = Math.random();
            
            // Read total size count of the remaining alien list
            int listSize = aliens.size();
            
            // Multiply roll by list size to pick a position index
            double targetIndexRaw = indexRoll * listSize;
            
            // Cast the raw calculation into a whole number integer index
            int targetIndex = (int) targetIndexRaw;
            
            // Pull the chosen alien shooter reference out of our list
            Alien randomAlien = aliens.get(targetIndex);
            
            // Read the chosen shooter's horizontal coordinate placement
            int rx = randomAlien.x;
            
            // Read the chosen shooter's component width dimension
            int rw = randomAlien.width;
            
            // Divide width value by two to discover the midpoint offset
            int halfAlienWidth = rw / 2;
            
            // Add horizontal offset to discover the projectile center spot
            int shotX = rx + halfAlienWidth;
            
            // Read the chosen shooter's vertical position value
            int ry = randomAlien.y;
            
            // Read the chosen shooter's component height dimension
            int rh = randomAlien.height;
            
            // Add height value to locate the bottom lip of the alien
            int shotY = ry + rh;
            
            // Define that this laser is fired by enemies and travels down
            boolean playerOwned = false;
            
            // Build the physical enemy laser projectile instance node
            Laser enemyLaser = new Laser(shotX, shotY, playerOwned);
            
            // Append the new weapon projectile directly into our list
            lasers.add(enemyLaser);
        }

        // Check if all the targets inside our list are deleted
        boolean noAliensLeft = aliens.isEmpty();
        
        // Terminate the active match loop if all enemies are dead
        if (noAliensLeft) {
            
            // Set the match state flag to finish the session
            isGameOver = true;
        }
    }

    // Detect overlapping bounds intersections across all objects
    private void checkCollisions() {
        
        // Pull out a primary iterator loop handler for active lasers
        Iterator<Laser> laserIt = lasers.iterator();
        
        // Continue parsing active projectiles while they remain available
        while (laserIt.hasNext()) {
            
            // Store the single target laser instance handle currently read
            Laser l = laserIt.next();
            
            // Extract the rectangular boundary hitbox from that laser
            Rectangle laserBounds = l.getBounds();

            // Check if the current laser element belongs to the player
            boolean humanLaser = l.isPlayerLaser;
            
            // Handle player hits vs enemy hits in distinct blocks
            if (humanLaser) {
                
                // Pull out a separate nested iterator loop handler for aliens
                Iterator<Alien> alienIt = aliens.iterator();
                
                // Continue reading alien entries within our inner cycle loop
                while (alienIt.hasNext()) {
                    
                    // Store the single target alien instance handle being checked
                    Alien a = alienIt.next();
                    
                    // Extract the bounding hitbox area framework from that alien
                    Rectangle alienBounds = a.getBounds();
                    
                    // Test if the laser box intersects the invader box area
                    boolean hitAlien = laserBounds.intersects(alienBounds);
                    
                    // Process removal scoring details if a hit evaluates true
                    if (hitAlien) {
                        
                        // Erase the hit alien out from the master list registry
                        alienIt.remove();
                        
                        // Erase the used laser out from the master list registry
                        laserIt.remove();
                        
                        // Increment our point tracker total metric upward by ten
                        score += Constants.GamePanelConstants.kPointsPerKill;
                        
                        // Terminate this nested loop to jump to the next laser
                        break; 
                    }
                }
            } else {
                
                // Extract the active safety hitbox layer of the player ship
                Rectangle playerBounds = player.getBounds();
                
                // Test if the enemy laser overlaps our player hitbox boundary
                boolean hitPlayer = laserBounds.intersects(playerBounds);
                
                // End game operations if an enemy projectile strikes player
                if (hitPlayer) {
                    
                    // Adjust state flag variable to trigger game termination
                    isGameOver = true;
                    
                    // Wipe out that striking laser from our active list collection
                    laserIt.remove();
                }
            }
        }
    }

    // Draw all our game objects onto the window view screen
    @Override
    protected void paintComponent(Graphics g) {
        
        // Invoke initial base drawing processes from core JPanel
        super.paintComponent(g);
        
        // Cast the general graphics processor handle to modern 2D engine
        Graphics2D g2d = (Graphics2D) g;
        
        // Store an anti-aliasing key constant label into a variable
        RenderingHints.Key antiAliasKey = RenderingHints.KEY_ANTIALIASING;
        
        // Store an anti-aliasing activation value label into a variable
        Object antiAliasOn = RenderingHints.VALUE_ANTIALIAS_ON;
        
        // Inject pixel smoothing options inside our graphics settings engine
        g2d.setRenderingHint(antiAliasKey, antiAliasOn);

        // Instruct the player object to draw its sprite image on screen
        player.draw(g2d);
        
        // Cycle through our remaining list collection layout of enemies
        for (Alien alien : aliens) {
            
            // Instruct the individual invader to paint itself on screen
            alien.draw(g2d);
        }
        
        // Cycle through our active tracking list collection of lasers
        for (Laser laser : lasers) {
            
            // Instruct the individual projectile to paint itself on screen
            laser.draw(g2d);
        }

        // Pick out the color white to use for overlay text fonts
        Color whiteTextColor = Color.WHITE;
        
        // Set our active drawing tool color selector to white
        g2d.setColor(whiteTextColor);
        
        // Create an Arial bold typography configuration setting object
        Font scoreFont = new Font("Arial", Font.BOLD, 18);
        
        // Apply our fresh score font sizing options onto the drawing engine
        g2d.setFont(scoreFont);
        
        // Merge a scoreboard label string along with our actual points variable
        String scoreMessage = "SCORE: " + score;
        
        // Draw our score tracker character sequence at screen coordinate padding
        g2d.drawString(scoreMessage, 20, 30);

        // Build end overlay graphics layouts if game session concludes
        if (isGameOver) {
            
            // Build a transparent dark backdrop layer color wrapper frame
            Color shadowOverlay = new Color(0, 0, 0, 180);
            
            // Apply the dark overlay color to the active engine brush
            g2d.setColor(shadowOverlay);
            
            // Paint a solid dark shade mask block stretching across screen sizes
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            
            // Select our standard solid white color value item node again
            Color textWhite = Color.WHITE;
            
            // Apply white color onto the font engine layout context brush
            g2d.setColor(textWhite);
            
            // Establish a thick headline typography design font properties asset
            Font endHeadlineFont = new Font("Arial", Font.BOLD, 36);
            
            // Mount the thick headline typography item to our drawing engine
            g2d.setFont(endHeadlineFont);
            
            // Verify if the active list collection of aliens is vacant
            boolean playerWon = aliens.isEmpty();
            
            // Pick a winning quote string message if list is vacant
            String winMsg = "VICTORY EARTHLING!";
            
            // Pick a losing quote string message if list holds items
            String loseMsg = "GAME OVER MAN!";
            
            // Choose the proper string message content based on win state
            String message = playerWon ? winMsg : loseMsg;
            
            // Compute a horizontal layout alignment index anchor coordinate point
            int alertX = WIDTH / 2 - 150;
            
            // Compute a vertical layout alignment index anchor coordinate point
            int alertY = HEIGHT / 2 - 20;
            
            // Print out the proper game status notification string block message
            g2d.drawString(message, alertX, alertY);
            
            // Establish a small plain instructions font text object setting
            Font subTextFont = new Font("Arial", Font.PLAIN, 18);
            
            // Apply the instructions font setting onto our drawing engine
            g2d.setFont(subTextFont);
            
            // Define an instruction prompt text label string for resetting
            String restartNotice = "Press 'R' to Restart your mission";
            
            // Compute a horizontal layout spot for the instructions text
            int promptX = WIDTH / 2 - 130;
            
            // Compute a vertical layout spot for the instructions text
            int promptY = HEIGHT / 2 + 20;
            
            // Draw out the final instruction string message line at those layout points
            g2d.drawString(restartNotice, promptX, promptY);
        }
    }
}