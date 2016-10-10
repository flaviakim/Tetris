package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.*;

/**
 * This class generates the Game Logic class (which handles the game) and displays it.
 **/
public class GamePanel extends JPanel {
	
	GameWindow window;	// The parent window in which this GamePanel is in.
	public GameWindow getWindow() { return window; }
	
	Tetris game;		// Where the whole logic for the game is in.
	public Tetris getGame() { return game; }
	
	
	// INITIALIZING
	
	public GamePanel (GameWindow window, float speed, int rowCountX, int rowCountY) {
		this.window = window;
		
		game = new Tetris(this, speed, rowCountX, rowCountY);
		
		setFocusable(true);	// So this class gets the key events.
		setVisible(true);
		
		addKeyListener(game);
		
		System.out.println("GamePanel created!");
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		paintGameBoard(g);
		if (game.getCurrentPiece() != null) {
			paintCurrentPiece(g);
		}
				
	} 
	
	void paintGameBoard(Graphics g) {
		
		for (int x = 0; x < game.getRowCountX(); x++) {
			for (int y = 0; y < game.getRowCountY(); y++) {
				if (game.isPositionOccupied(x, y)) {
					// draw a rectangle at the appropriate position.
					drawSinglePiece(g, x, y, game.getPieceAt(x, y));
				}
			}
		}
		
	}
	
	void paintCurrentPiece(Graphics g) {
		Piece c = game.getCurrentPiece();
		drawSinglePiece(g, c.position.x, c.position.y, c);
		
	}
	
	void drawSinglePiece(Graphics g, int x, int y, Piece p) {
		
		int firstPixelX = game.getRowWidth() * x;
		int firstPixelY = game.getRowHeight() * y;
		
		g.setColor(Color.blue);
		g.fillRect(firstPixelX, firstPixelY, game.getRowWidth(), game.getRowHeight());
		
		System.out.println("Single Piece drawn at: (" + x + ", " + y + ")");
		
	}
	
	
	
	
	
}







