package graphics;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import logic.Tetris;

/**
 * Manages the different Panels in the Window.
 * Right now only the GamePanel. Later also Score, nextPiecePanel, etc.
 **/
public class GameWindow extends JFrame {

    // PROPERTIES
    
    public static final int gameInfoPanelHeight = 50;
    
    JPanel container;
    GamePanel gamePanel;
    public GamePanel getGamePanel() { return gamePanel; }
    GameInformationPanel gameInfoPanel;
    public GameInformationPanel getGameInfoPanel() { return gameInfoPanel; }
    
    StartScreenPanel startScreenPanel;


    // INITIALIZING

    public GameWindow(int width) {
		
		int height = width / Tetris.getRows() * Tetris.getColumns();
		height += gameInfoPanelHeight;
		
		container = new JPanel();
		container.setSize(width, height);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setVisible(true);
		container.setFocusable(true);
		this.add(container);
		
        this.setSize(width, height);
        setTitle("Tetris");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createStartScreenPanel();
		//startGame(1f);
    }


    // METHODS

    void startGame(float speed) {
        startScreenPanel.setEnabled(false);
        container.remove(startScreenPanel);
        startScreenPanel = null;

        gamePanel = new GamePanel(this, speed);
        gameInfoPanel = new GameInformationPanel(this);
        
        container.add(gamePanel);
        container.add(gameInfoPanel);
             gamePanel.requestFocusInWindow();

        this.pack();

        repaint();
    }

    private void createStartScreenPanel() {
	    try {
		    startScreenPanel = new StartScreenPanel(this);
	    } catch (Exception ex) {
			ex.printStackTrace();
		}
		container.add(startScreenPanel);
		startScreenPanel.requestFocusInWindow();
		
		startScreenPanel.revalidate();
    }
    
    public void endGame() {
    	gamePanel.setEnabled(false);
        container.remove(gamePanel);
        gamePanel = null;
        
        gameInfoPanel.setEnabled(false);
        container.remove(gameInfoPanel);
        gameInfoPanel = null;
        
        createStartScreenPanel();
        
        repaint();
    }


}