package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * A menu bar for the tetris game.
 * @author Dino Hadzic
 * @version 9 December 2016
 *
 */
public class ScoreMenu extends JMenuBar {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Menu item for scoring details.
     */
    private final JMenuItem myAboutMenuItem = new JMenuItem("Scoring details...");
    
    /**
     * Default constructor.
     */
    public ScoreMenu() {
        super();
        final JMenu menu;
        menu = new JMenu("Help");
        add(menu);
        myAboutMenuItem.addActionListener(new MenuListener());
        menu.add(myAboutMenuItem);
    }

    /**
     * A listener for the scoring rules menu item.
     * @author Dino Hadzic
     * @version 9 December 2016
     *
     */
    private class MenuListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            JOptionPane.showMessageDialog(null, "Scoring rules \n"
                                + "Freezing piece: 4 pts \n"
                    + "Lines cleared:   1 line         2 lines        3 lines        4 lines\n"
                    + "Level 1:                40               100               "
                    + "300              1200\n"
                    + "Level 2:                80               200               "
                    + "600              2400\n"
                    + "Level 3:                120             300               "
                    + "900              3600\n"
                    + "...\n"
                    + "Level 10:              400             1000             "
                    + "3000            12000\n",
                    "TCSS 305 Tetris rules", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}