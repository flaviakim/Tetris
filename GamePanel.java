import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	
	// PROPERTIES
	
	int rowCountX;	// The number of horizontal rows
	int rowCountY;	// The number of vertical rows
	int getRowWidth() { return (int) getSize().getWidth() / rowCountX; }	// The width of one row in pixels.
	int getRowHeight() { return (int) getSize().getHeight() / rowCountY; }	// The height of one row in pixels.
	
	Timer timer;	// The timer responsible for dropping the pieces down one row.
	float speed;	// How often the current piece drops down one row in drops per second.
	boolean paused = false;	// Whether the game is currently paused (true) or running (false).
	
	int currentScore;	// The current score (calculated by speed and lines removed).
	
	GameWindow window;	// The parent window in which this GamePanel is in.
	
	
	// INITIALIZING
	
	public GamePanel (GameWindow window, float speed) {
		
		this.window = window;
		
		Timer timer = new Timer((1000/speed), this);
		timer.start();
		
		
		setFocusable(true);	// So this class gets the key events.
		
	}
	
	
	// TIMER EVENT
	
	/**
	 * This gets called every time the timer is at 0 and is responsible for the game flow.
	 */
	public void actionPerformed (ActionEvent e) {
		
		// TODO: Either move the current piece down one row or if it already is down then create a new shape.
		//		 If the creation of the shape isn't possible the game is over.
				
	}
	
	
	
	
}







