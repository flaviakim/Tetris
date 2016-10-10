package graphics;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.Tetris;

/**
 * Manages the different Panels in the Window.
 * Right now only the GamePanel. Later also Score, nextPiecePanel, 
 **/
public class GameWindow extends JFrame {
	
	GamePanel panel;
	public Tetris getCurrentGame() {
		if (panel != null) {
			return panel.getGame();
		} else {
			return null;
		}
	}
	
	public GameWindow(int width, int height) {
				
		//TODO: Add Game Panel
		//TODO: Add Score Label
		//TODO: Add Start Screen instead of starting immediately (a different Panel than the GamePanel, only create GamePanel when pressed play)
		
		panel = new GamePanel(this, 1, 10, 20);
		panel.setVisible(true);
		this.add(panel);
		
		this.setSize(width, height);
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	
}