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
	
	GamePanel panel;
	public Tetris getCurrentGame() {	// Not currently used. But maybe later, when showing different JPanels in this GameWindow which need to know Game Logic stuff (like the score). Maybe make them in the GamePanel. But then the window size has to be resized by the GamePanel. 
		if (panel != null) {
			return panel.getGame();
		} else {
			return null;
		}
	}
	
	public GameWindow(int width, int height) {
				
		//TODO: Add Score Label
		//TODO: Add Start Screen instead of starting immediately (a different Panel than the GamePanel, only create GamePanel when pressed play)
		
		panel = new GamePanel(this, 20, 10, 20); // window, speed, rowCountX, rowCountY
		panel.setVisible(true);
		this.add(panel);
		
		this.setSize(width, height);
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	
}