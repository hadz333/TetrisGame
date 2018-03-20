/*
 * TCSS 305 - Assignment 6 
 */

package view;

import java.awt.EventQueue;

/**
 * Main class for Tetris.
 * 
 * @author Dino Hadzic
 * @version 10 November 2016
 */
public final class TetrisMain {

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the SnapShop GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
}
