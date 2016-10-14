package graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class StartScreenPanel extends JPanel implements KeyListener {
	
	// PROPERTIES
	
	GameWindow window;	// The parent window in which this GamePanel is in.
	
	
	// INITIALIZING
	
	public StartScreenPanel(GameWindow window) {
		this.window = window;
		setBackground(Color.black);
		// TODO: Add Label with menu text.
	}
	
	
	// KEYEVENTS
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				startGame();
				break;
			default:
				break;
		}
	}
	
	void startGame() {
		window.startGame();
	}
	
	
	
}