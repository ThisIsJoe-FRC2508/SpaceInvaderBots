//For Teaching Java
//Designed to mimic the robot code in structure



/**
 * Separating your entry point (main) from your actual application logic is a software design
 * best practice.
 * The main method is static and cannot below to an object, therefore in object-oriented programming
 * we are moving to non-static coding.
 * Simple launcher keeps this code clear and understandable, also a dedicated launch allows other tools
 * to launch the app easily.
 * Helps keep clean GUI Thread Management.

*/

import javax.swing.SwingUtilities;

/**
 * The Main class serves as the starting point for the entire game application.
 * It is marked 'final' so that it cannot be extended (inherited) by other classes.
 */
public final class Main {
    
    /**
     * A private constructor prevents other parts of the program from creating 
     * an instance of this Main class, since it only exists to host the main method.
     */
    private Main() {}

    /**
     * The main method is the exact place where the computer starts running the program.
     * 
     * @param args Standard command-line arguments passed as an array of Strings.
     */
    public static void main(String... args) {
        
        /*
         * SwingUtilities.invokeLater ensures that our User Interface (UI) is created 
         * on the Event Dispatch Thread (EDT). The EDT is a special background thread 
         * in Java dedicated solely to handling screen updates and user clicks. 
         * 
         * Running GUI code here prevents visual glitches and freezing.
         * 
         * 'SpaceInvaderBots::new' is a shorthand way (method reference) of telling 
         * Java to launch a new instance of your SpaceInvaderBots window frame.
         */
        SwingUtilities.invokeLater(SpaceInvaderBots::new);
    }
}