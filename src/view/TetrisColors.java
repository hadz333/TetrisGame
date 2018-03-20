package view;

import java.awt.Color;
import java.util.Random;

/**
 * Class for setting a random tetris piece color from a list of colors.
 * @author Dino Hadzic
 * @version 9 December 2016
 *
 */
public class TetrisColors {
    
    /**
     * Turqoise color.
     */
    private final Color myTurqoise = new Color(0, 255, 255);
    
    /**
     * Blue color.
     */
    private final Color myBlue = new Color(0, 0, 255);
    
    /**
     * Orange color.
     */
    private final Color myOrange = new Color(255, 128, 0);
    
    /**
     * Yellow color.
     */
    private final Color myYellow = new Color(255, 255, 50);
    
    /**
     * Green color.
     */
    private final Color myGreen = new Color(128, 255, 0);
    
    /**
     * Purple color.
     */
    private final Color myPurple = new Color(153, 51, 255);
    
    /**
     * Red color.
     */
    private final Color myRed = new Color(255, 0, 0);
    
    /**
     * All colors.
     */
    private final Color[] myColors = {myTurqoise, myBlue, myOrange, 
        myYellow, myGreen, myPurple, myRed};
    
    
    /**
     * Default constructor.
     */
    public TetrisColors() {
        
    }
    
    /**
     * Returns randomly selected color.
     * @return randomly selected color.
     */
    public Color getRandomColor() {
        
        final Random rand = new Random();
        final int color = rand.nextInt(myColors.length);
        return myColors[color];
        
    }
}
