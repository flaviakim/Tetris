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
	int getRowWidth() { return (int) getSize().getWidth() / rowCountX; }
	int getRowHeight() { return (int) getSize().getHeight() / rowCountY; }
	
	Timer timer;
	float speed;	// How often the current piece drops down one row in drops per second
	boolean paused = false;
	
	int currentScore;
	
	GameWindow window;
	
	
	// INITIALIZING
	
	public GamePanel (GameWindow window, float speed) {
		
		this.window = window;
		
		Timer timer = new Timer((1000/speed), this);
		timer.start();
		
		
		setFocusable(true);
		
	}
	
	
	// TIMER EVENT
			
	public void actionPerformed (ActionEvent e) {
		
		// TODO: Either move the current piece down one row or if it already is down then create a new shape.
		//		 If the creation of the shape isn't possible the game is over.
				
	}
	
	
	
	
}







