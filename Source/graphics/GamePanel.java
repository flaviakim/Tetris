package graphics;

import java.awt.Color;
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
					drawSinglePiece(g, x, y, game.getPieceAt(x, y).color);
				}
			}
		}
		
		drawGameOverLine(g);
		
	}
	
	void paintCurrentPiece(Graphics g) {
		Piece c = game.getCurrentPiece();
		drawSinglePiece(g, c.position.x, c.position.y, c.color);
		
	}
	
	void drawSinglePiece(Graphics g, int x, int y, Color c) {
		// Get the PixelPositions for left, right top and bottom
		int firstPixelX = game.getRowWidth() * x;
		int firstPixelY = game.getRowHeight() * y;
		int lastPixelX = firstPixelX + game.getRowWidth() - 1;
		int lastPixelY = firstPixelY + game.getRowHeight() - 1;
		
		// Fill the middle of the square.
		g.setColor(c);
		g.fillRect(firstPixelX, firstPixelY, game.getRowWidth() - 1, game.getRowHeight() - 1);
		// Draw the top and left border with a brighter color.
		g.setColor(c.brighter());
		g.drawLine(firstPixelX, firstPixelY, lastPixelX, firstPixelY); 	// top left to top (right-1)
		g.drawLine(firstPixelX, firstPixelY + 1, firstPixelX, lastPixelY);	// (top-1) left to bottom left
		// Draw the bottom and right border with a darker color.
		g.setColor(c.darker());
		g.drawLine(lastPixelX, firstPixelY + 1, lastPixelX, lastPixelY - 1);	// top right to (bottom-1) right
		g.drawLine(firstPixelX + 1, lastPixelY, lastPixelX, lastPixelY);	// bottom (left+1) to bottom right
		
		//System.out.println("Single Piece drawn at: (" + x + ", " + y + ")");
		
	}
	
	public void drawGameOverLine(Graphics g) {
		int y = game.getRowsAboveGameLine() * game.getRowHeight();
		g.setColor(Color.red);
		g.drawLine(0, y, (int) getSize().getWidth(), y);
		
	}
	
	
	
	
}







