package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JLabel;

import logic.Tetris;
import logic.ShapePrototypes;
import logic.Vector2;


public class GameInformationPanel extends JPanel {
	
	// PROPERTIES
	
	final GameWindow window;
	JLabel scoreLabel;
	
	
	// INITIALISATION
	
	public GameInformationPanel (GameWindow window) {
		this.window = window;
		
		setPreferredSize(new Dimension((int)window.getSize().getWidth(), window.gameInfoPanelHeight));
        
        setVisible(true);
		
		addScoreLabel();
		
		System.out.println("GameInformationPanel created!");
	}
	
	
	// METHODS
	
	void addScoreLabel () {
    	scoreLabel = new JLabel ("Score: 0", javax.swing.SwingConstants.LEFT);
        //scoreLabel.setBackground(new Color (200, 200, 200, 100) );
        scoreLabel.setVisible(true);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(0, 10, (int)this.getSize().getWidth(), 40);
        this.add(scoreLabel);
    }
    
    
    // PAINTING
	
	public void paintComponent (Graphics g) {
        super.paintComponent(g);
		drawTopLine(g);
		updateScoreLabel(g);
		drawNextShape(g);
    }
    
    void drawTopLine (Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, (int)this.getSize().getWidth(), 2);
    }
    
    void updateScoreLabel (Graphics g) {
    	scoreLabel.setText("Score: " + window.getGamePanel().getGame().getCurrentScore() + 
    			 	     "\nLevel: " + window.getGamePanel().getGame().getLevel());
    }
    
    void drawNextShape(Graphics g) {
    	ShapePrototypes nextShape = window.getGamePanel().getGame().getNextShape().getPrototype();
    	for (Vector2 pos : nextShape.getPositions()) {
    		drawSinglePiece (g, pos.x, pos.y, nextShape.getColor());
    	}
    }
    
    
    void drawSinglePiece (Graphics g, int x, int y, Color c) {
        // Get the PixelPositions for left, right top and bottom
        Tetris game = window.getGamePanel().getGame();
        int pieceSize = game.getPieceSize() / 2;
        int firstPixelX = pieceSize * x + 10;
        int firstPixelY = pieceSize * y + 10;
        int lastPixelX = firstPixelX + pieceSize - 1;
        int lastPixelY = firstPixelY + pieceSize - 1;

        // Fill the middle of the square.
        g.setColor(c);
        g.fillRect(firstPixelX, firstPixelY, pieceSize - 1, pieceSize - 1);
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

    
    
	
}






