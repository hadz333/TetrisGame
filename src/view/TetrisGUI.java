package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


import model.Block;
import model.Board;
import model.TetrisPiece;

/**
*  GUI for Tetris.
* 
*  @author Dino Hadzic
*  @version 26 November 2016
*/
public class TetrisGUI implements Observer {
    

    /** 
     * The preferred size of the button area panel. 
     */
    private static final Dimension BUTTON_SIZE = new Dimension(180, 150);
    
    /** 
     * The preferred size of the board area panel. 
     */
    private static final Dimension BOARD_SIZE = new Dimension(200, 400);
    
    /** 
     * The preferred size of the board area panel. 
     */
    private static final Dimension NEXT_PIECE_SIZE = new Dimension(120, 100);
    
    /**
     * Lines required to increase level of game.
     */
    private static final int LINES_FOR_LEVEL_UP = 5;
    
    /**
     * Max level game can go to.
     */
    private static final int MAX_LEVEL = 10;
    
    /**
     * Initial delay time for game ticks.
     */
    private static final int INITIAL_DELAY_TIME = MAX_LEVEL * 100;
    
    /**
     * Amount of points a piece freezing gives the player.
     */
    private static final int FREEZE_PTS = 4;
    
    /**
     * Score multiplier for single line clearance.
     */
    private static final int SINGLE_MULTIPLIER = 40;
    
    /**
     * Score multiplier for double line clearance.
     */
    private static final int DOUBLE_MULTIPLIER = 100;
    
    /**
     * Score multiplier for triple line clearance.
     */
    private static final int TRIPLE_MULTIPLIER = 300;
    
    /**
     * Score multiplier for quadruple line clearance.
     */
    private static final int QUAD_MULTIPLIER = 1200;
    
    /**
     * Height of the board.
     */
    private static final int WIDTH = 10;
    /**
     * Width of the board.
     */
    private static final int HEIGHT = 20;
    
    /**
     * Label for next piece. 
     */
    private final JLabel myNext = new JLabel("Next piece: ");
    
    /**
     * Label for controls. 
     */
    private final JLabel myControls = new JLabel("<html>Instructions - controls <br>"
                    + "Press start to begin new game </html>");
    
    /**
     * Instructions for how to play the game.
     */
    private final JLabel myInstructions = new JLabel("<html> Move left: Left arrow key <br>"
                    + "Move right: Right arrow key <br>"
                    + "Move down: down arrow key <br>"
                    + "Rotate CW: up arrow key <br>"
                    + "Rotate CCW: Z button <br>"
                    + "Drop: space <br>"
                    + "Pause/unpause: P <br>"
                    + "</html>");
    
    /**
     * Start button.
     */
    private final JButton myStartButton = new JButton("Start");
    
    /**
     * End button.
     */
    private final JButton myEndButton = new JButton("End game");

    /** The top level window for this application. */
    private final JFrame myFrame = new JFrame("Tetris");
    
    /** A panel for drawing shapes. */
    private final JPanel myNextPiecePanel = new NextPieceArea();
   
    /** A panel for drawing shapes. */
    private final JPanel myButtonPanel = new ButtonArea();
    
    /** A panel for drawing shapes. */
    private final JPanel myBoardPanel = new BoardArea();
     
    /**
     * Board for the Tetris game.
     */
    private Board myBoard = new Board(WIDTH, HEIGHT);
    
    /**
     * Tick timer.
     */
    private final Timer myTimer = new Timer(INITIAL_DELAY_TIME, new TickListener());
    
    /**
    * Score tick timer.
    */
    private final Timer myScoreTimer = new Timer(60, new ScoreTickListener());
    
    /**
     * Next piece (for display).
     */
    private TetrisPiece myNextPiece;
    
    /**
     * Image of the next tetris piece.
     */
    private List<Block[]> myCurrentPieces;
    
    /**
     * The game over state.
     */
    private boolean myGameOver;
    
    /**
     * The paused state of the game.
     */
    private boolean myPaused;
    
    /**
     * The state of the game (started or ended).
     */
    private boolean myPlaying;
    
    /**
     * Number of lines cleared during current game.
     */
    private int myLinesCleared;
    
    /**
     * Difficulty level of the game, based on lines cleared.
     */
    private int myDifficultyLevel = 1;
    
    /**
     * Player's Tetris score.
     */
    private int myScore;
    
    /**
     * Score display.
     */
    private final JLabel myScoreDisplay = new JLabel();
    
    /**
     * Lines cleared display.
     */
    private final JLabel myLinesClearedDisplay = new JLabel();
    
    /**
     * Current level display.
     */
    private final JLabel myLevelDisplay = new JLabel();
    
    /**
     * Lines remaining to next level display.
     */
    private final JLabel myLinesRemainingDisplay = new JLabel();
    
    /**
     * Tells freeze score counter whether or not it is first piece.
     */
    private boolean myFirstPiece = true;
    
    /**
     * Menu item for score details.
     */
    private final JMenuBar myMenuBar = new ScoreMenu();
    
    /**
     * List of possible colors for tetris pieces.
     */
    private final TetrisColors myColors = new TetrisColors();
    
    //private final MenuListener myMenu = new MenuListener();

    
   /** Sets up the elements of the GUI. */ 
    public void start() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        
        myBoard.addObserver(new ObserverClass());
        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(myButtonPanel, BorderLayout.EAST);
        myFrame.add(myBoardPanel, BorderLayout.WEST);
        myFrame.add(myNextPiecePanel, BorderLayout.SOUTH);
        myFrame.add(myMenuBar, BorderLayout.NORTH);
        myFrame.pack();
        myFrame.addKeyListener(new PieceListener());
        myFrame.requestFocus();
        myFrame.setVisible(true);
    }
    
    /**
     * Sets up score labels.
     */
    private void setUpScores() {
        myScoreDisplay.setText("Score: " + myScore);
        myButtonPanel.add(myScoreDisplay);
        myLinesClearedDisplay.setText("Lines cleared: " + myLinesCleared);
        myButtonPanel.add(myLinesClearedDisplay);
        myLevelDisplay.setText("Current level: " + myDifficultyLevel);
        myButtonPanel.add(myLevelDisplay);
        myLinesRemainingDisplay.setText("Lines to next level: " 
                      + (LINES_FOR_LEVEL_UP - myLinesCleared % LINES_FOR_LEVEL_UP));
        myButtonPanel.add(myLinesRemainingDisplay);
    }
    
    /**
     * Updates current level (difficulty of game).
     */
    private void updateLevel() {
        myDifficultyLevel = myLinesCleared / LINES_FOR_LEVEL_UP + 1;
        final int speedChangeRatio = 100;
        final int levelForCalc = myDifficultyLevel - 1;
        if (myDifficultyLevel <= MAX_LEVEL) {
            myTimer.setDelay(INITIAL_DELAY_TIME - (speedChangeRatio * levelForCalc));
        }
    }
    
    /**
     * Updates current score when line(s) are cleared.
     * @param theNumLines number of lines.
     * @param theFreezeScore true if freeze scored.
     */
    private void updateScore(final int theNumLines, final boolean theFreezeScore) {
        final int single = 1;
        final int d = 2;
        final int triple = 3;
        final int quad = 4;
        
        if (theFreezeScore) {
            if (myFirstPiece) {
                myFirstPiece = false;
            } else {
                myScore += FREEZE_PTS;
            }
        } else {
            if (theNumLines == single) {
                myScore += SINGLE_MULTIPLIER * myDifficultyLevel;
            }
            if (theNumLines == d) {
                myScore += DOUBLE_MULTIPLIER * myDifficultyLevel;
            }
            if (theNumLines == triple) {
                myScore += TRIPLE_MULTIPLIER * myDifficultyLevel;
            }
            if (theNumLines == quad) {
                myScore += QUAD_MULTIPLIER * myDifficultyLevel;
            }
        }
    }
    
    /**
     * Called when game is over.
     */
    private void gameOver() {
        myGameOver = true;
        myPlaying = false;
        JOptionPane.showMessageDialog(null, "GAME OVER",
                        "Game over", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void update(final Observable theObservable, final Object theOptionalArg) {
        
        
    }

    /**
     * Represents the panel where the game board will be.
     * @author Dino Hadzic
     * @version 27 November 2016
     *
     */
    private class BoardArea extends JPanel {
        
        /**
         * A generated serial version ID. 
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * Constructor. 
         */
        BoardArea() {
            super();
            setPreferredSize(BOARD_SIZE);
            setBackground(Color.BLACK);
        }
        @Override
        public void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);
            final Graphics2D g2D = (Graphics2D) theGraphics;
            
            
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (myCurrentPieces != null) {
                final int arrSize = myCurrentPieces.size();
                for (int i = arrSize - 1; i >= 0; i--) {
                    final Block[] row = myCurrentPieces.get(i);
                    if (row != null) {
                        for (int j = 0; j < myBoard.getWidth(); j++) {
                            if (row[j] != null) {
                                final int pieceSize = 20;
                                g2D.setColor(Color.BLACK);
                                g2D.drawRect(j * pieceSize, 
                                             (myBoard.getHeight() - i - 1)  * pieceSize, 
                                         pieceSize, pieceSize);
                                g2D.setColor(myColors.getRandomColor());
                                g2D.fillRect(j * pieceSize + 1, 
                                             (myBoard.getHeight() - i - 1)  * pieceSize + 1, 
                                         pieceSize - 1, pieceSize - 1);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Represents the panel where the game buttons and instructions will be.
     * @author Dino Hadzic
     * @version 27 November 2016
     *
     */
    private class ButtonArea extends JPanel {
        
        /**
         * A generated serial version ID. 
         */
        private static final long serialVersionUID = 1L;
        /**
         * Constructor. 
         */
        ButtonArea() {
            super();
            add(myControls);
            add(myInstructions);
            myStartButton.addActionListener(new ButtonListener());
            myEndButton.addActionListener(new ButtonListener());
            add(myStartButton);
            add(myEndButton);
            
            setPreferredSize(BUTTON_SIZE);
            setBackground(Color.GRAY);
        }

    }
    
    /**
     * Represents the panel where the next piece will be.
     * @author Dino Hadzic
     * @version 2 December 2016
     *
     */
    private class NextPieceArea extends JPanel {
        
        /**
         * A generated serial version ID. 
         */
        private static final long serialVersionUID = 1L;
        /**
         * Constructor. 
         */
        NextPieceArea() {
            super();
            setLayout(new BorderLayout());

            add(myNext, BorderLayout.CENTER);
            
            setSize(NEXT_PIECE_SIZE);
            setPreferredSize(NEXT_PIECE_SIZE);
            setBackground(Color.GRAY);
        }
        @Override
        public void paintComponent(final Graphics theGraphics) {
            super.paintComponent(theGraphics);
            final Graphics2D g2D = (Graphics2D) theGraphics;
            
            // setting location of next piece to be center of panel.
            final int xDisplacement = getWidth() / 2 - 30;
            final int yDisplacement = getHeight() / 2 + 20;
            g2D.translate(xDisplacement, yDisplacement);
            
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(Color.BLACK);
            
            if (myNextPiece != null) {
                final int arrSize = myNextPiece.getPoints().length;
                for (int i = 0; i < arrSize; i++) {
                    final model.Point currPt = myNextPiece.getPoints()[i];
                    final int pieceSize = 20;
                    g2D.drawRect(currPt.x() * pieceSize, -currPt.y() * pieceSize, 
                                 pieceSize, pieceSize);                    
                }
            }
            g2D.translate(-xDisplacement, -yDisplacement);
        }
    }
    
    
    /**
     * Listener for the start button.
     * @author Dino Hadzic
     * @version 26 November 2016
     *
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            // start game
            if (theEvent.getSource() == myStartButton) {
                if (!myPlaying) {
                    myGameOver = false;
                    myBoard.newGame();
                    myTimer.start();
                    myScoreTimer.start();
                    myPlaying = true;
                    myDifficultyLevel = 1;
                    myLinesCleared = 0;
                    myScore = 0;
                    myTimer.setDelay(INITIAL_DELAY_TIME);
                }
                myFrame.requestFocus();
            } else if (theEvent.getSource() == myEndButton) {
                gameOver();
            }
            
        }   
    }
    
    /**
     * Listener for the game ticks.
     * @author Dino Hadzic
     * @version 26 November 2016
     *
     */
    private class TickListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myGameOver) {
                myBoard.step();
            }
            
        }   
    }
    
    /**
     * Listener for the score update ticks.
     * @author Dino Hadzic
     * @version 26 November 2016
     *
     */
    private class ScoreTickListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (!myGameOver) {
                setUpScores();
            }
            
        }   
    }
    
    
    
    /**
     * Listener for movement of current piece.
     * @author Dino Hadzic
     * @version 30 November 2016
     *
     */
    public class PieceListener implements KeyListener {
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            if (!myPaused) {
                if (theEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                    myBoard.left();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                    myBoard.right();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_UP) {
                    myBoard.rotateCW();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_Z) {
                    myBoard.rotateCCW();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                    myBoard.down();
                }
                if (theEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    myBoard.drop();
                }     
            }
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                if (myPaused) {
                    myTimer.start();
                    myPaused = false;
                } else {
                    myTimer.stop();
                    myPaused = true;
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent arg0) {
        }

        @Override
        public void keyTyped(final KeyEvent arg0) {
        }

    }
    
    
    
    /**
     * Inner class that implements Observer.
     * @author Dino Hadzic
     * @version 30 November 2016
     *
     */
    private class ObserverClass implements Observer {
        @SuppressWarnings("unchecked")
        @Override
        public void update(final Observable theObservable, final Object theArg) {
            myBoard = (Board) theObservable;
            if (theArg instanceof TetrisPiece) {
                myNextPiece = (TetrisPiece) theArg;
                updateScore(0, true);
            }
            if (theArg instanceof Boolean) {
                myGameOver = (Boolean) theArg;
                gameOver();
            }
            if (theArg instanceof List) {
                myCurrentPieces = (List<Block[]>) theArg;
                myButtonPanel.repaint();
                myBoardPanel.repaint();
                myNextPiecePanel.repaint();
            }
            if (theArg instanceof Integer[]) {
                final Integer[] arr = (Integer[]) theArg;
                myLinesCleared += arr.length;
                updateScore(arr.length, false);
                updateLevel();
            }
        }
    }
}
