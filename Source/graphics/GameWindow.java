package graphics;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.Tetris;

/**
 * Manages the different Panels in the Window.
 * Right now only the GamePanel. Later also Score, nextPiecePanel, etc.
 **/
public class GameWindow extends JFrame {

    // PROPERTIES

    GamePanel gamePanel;
    public Tetris getCurrentGame() {	// Not currently used. But maybe later, when showing different JPanels in this GameWindow which need to know Game Logic stuff (like the score). Maybe make them in the GamePanel. But then the window size has to be resized by the GamePanel.
        if (gamePanel != null) {
            return gamePanel.getGame();
        } else {
            return null;
        }
    }

    StartScreenPanel startScreenPanel;


    // INITIALIZING

    public GameWindow(int width, int height) {

        //TODO: Add Score Label
        //TODO: Add Start Screen instead of starting immediately (a different Panel than the GamePanel, only create GamePanel when pressed play)

        this.setSize(width, height);
        setTitle("Tetris");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createStartScreenPanel();

    }


    // METHODS

    void startGame() {
        startScreenPanel.setEnabled(false);
        this.remove(startScreenPanel);
        startScreenPanel = null;

        gamePanel = new GamePanel(this, 3, 10, 20); // window, speed, rowCountX, rowCountY TODO: Use variables instead of hardcoding it.

        this.pack();

        repaint();
    }

    private void createStartScreenPanel() {
	    try {
		    startScreenPanel = new StartScreenPanel(this);
	    } catch (Exception ex) {
			ex.printStackTrace();
		}
    }


}