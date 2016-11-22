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
    StartScreenPanel startScreenPanel;


    // INITIALIZING

    public GameWindow(int width, int height) {

        //TODO: Add Score Label
		
        this.setSize(width, height);
        setTitle("Tetris");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createStartScreenPanel();

    }


    // METHODS

    void startGame(float speed) {
        startScreenPanel.setEnabled(false);
        this.remove(startScreenPanel);
        startScreenPanel = null;

        gamePanel = new GamePanel(this, speed, 10, 20); // window, speed, rowCountX, rowCountY TODO: Use variables instead of hardcoding it.

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
    
    public void endGame() {
    	gamePanel.setEnabled(false);
        this.remove(gamePanel);
        gamePanel = null;
        
        createStartScreenPanel();
        
        repaint();
    }


}