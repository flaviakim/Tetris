package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.*;

/**
 * This class generates the Game Logic class (which handles the game) and displays it.
 **/
public class GamePanel extends JPanel {

    // PROPERTIES

    private GameWindow window;	// The parent window in which this GamePanel is in.
	public GameWindow getWindow() { return window; }

    private Tetris game;		// Where the whole logic for the game is in.
    public Tetris getGame() { return game; }

    JLabel gameOverLabel;
    JLabel pauseLabel;
    
    boolean useDroppedShape = true;

    // INITIALIZING

    public GamePanel (GameWindow window, float speed) {
        this.window = window;

        game = new Tetris(this, speed);

        setPreferredSize(new Dimension((int)window.getSize().getWidth(), game.getGameHeight()));

        addKeyListener(game);
        setFocusable(true);
        setVisible(true);
        //window.getContentPane().add(this);
        requestFocusInWindow();

        System.out.println("GamePanel created!");
    }

    // METHODS

    public void doGameOver () {
        int endScore = game.getCurrentScore();
        // TODO: Display "Game Over" and The endScore and which button to restart.
        gameOverLabel = new JLabel("<html>GAME OVER<br>Score: " + endScore + "<br>Space: Restart<br>esc: Main Menu</html>", javax.swing.SwingConstants.CENTER);
        gameOverLabel.setBackground(new Color (200, 200, 200, 180) );
        gameOverLabel.setVisible(true);
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBounds(0, 60, (int)this.getSize().getWidth(), 80);
        this.add(gameOverLabel);
        repaint();
        System.out.println("Game Over!");
    }

    public void restartGame () {
        this.remove(gameOverLabel);
        gameOverLabel = null;
    }

    public void changePauseLabel () {
        if (game.getPaused()) {
            pauseLabel = new JLabel ("Paused", javax.swing.SwingConstants.CENTER);
            pauseLabel.setBackground(new Color (200, 200, 200, 100) );
            pauseLabel.setVisible(true);
            pauseLabel.setOpaque(true);
            pauseLabel.setBounds(0, 60, (int)this.getSize().getWidth(), 60);
            this.add(pauseLabel);
        } else {
            this.remove(pauseLabel);
            pauseLabel = null;
        }
        repaint();
    }

    // PAINTING

    /**
     * This is used to draw everything in the game.
     **/
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        paintGameBoard(g);
        Shape currentShape = game.getCurrentShape();
        if (currentShape != null) {
            paintCurrentShape(g, currentShape);
            if (useDroppedShape) {
            	paintDroppedShape(g, currentShape, currentShape.numberOfPossibleDrops());
            }
        }
        
    }

    /**
     * Paints all the Pieces saved in the Game Board.
     **/
    void paintGameBoard (Graphics g) {

        for (int x = 0; x < game.getRows(); x++) {
            for (int y = 0; y < game.getColumns(); y++) {
                if (game.isPositionOccupied(x, y)) {
                    // draw a rectangle at the appropriate position.
                    drawSinglePiece(g, x, y, game.getPieceAt(x, y).color, false);
                }
            }
        }

    }

    /**
     * Paints the {@link Piece}s currently falling down (the current Shape);
     **/
    void paintCurrentShape (Graphics g, Shape currentShape) {
        if (currentShape == null) {
            System.out.println("ERROR: GamePanel::paintCurrentShape -- No current Shape to draw!");
            return;
        }

        for (Piece p : currentShape.getPieces()) {
            drawSinglePiece(g, p.position.x, p.position.y, p.color, false);
        }
    }
    
    /**
     * Paints the {@link Piece}s representing the position where the {@link currentShape}
     * would drop down.
     * If the Shape is too close to the ground it doesn't make sense to draw the dropped Shape.
     **/
    void paintDroppedShape (Graphics g, Shape currentShape, int dropps) {
    	if (currentShape == null) {
            System.out.println("ERROR: GamePanel::paintCurrentShape -- No current Shape to draw!");
            return;
        }
        if (dropps <= 1) {
         return;
        }
        
        for (Piece p : currentShape.getPieces()) {
            drawSinglePiece(g, p.position.x, p.position.y + dropps, p.color, true);
        }
    }

    /**
     * Paints one single {@link Piece} at it's position.
     * It's painted with the <code>Piece</code>'s color and with a small 3d effect (brighter and darker edges).
     * If the painted piece is only a visual for where the current Shape would drop, it is
     * drawn with a smaller alpha value.
     *
     * @param g       
     * @param x        The horizontal position on the {@link gameBoard}.
     * @param y        The vertical position on the <code>gameBoard</code>.
     * @param c        The <code>Color</code> of the <code>Piece</code>, according to it's {@link Shape}.
     * @param dropped  Whether the <code>Piece</code> is from the {@link currentShape} or a visual representation of where the <code>Shape</code> would drop.
     **/
    void drawSinglePiece (Graphics g, int x, int y, Color c, boolean dropped) {
        // Get the PixelPositions for left, right top and bottom
        int firstPixelX = game.getPieceSize() * x;
        int firstPixelY = game.getPieceSize() * y;
        int lastPixelX = firstPixelX + game.getPieceSize() - 1;
        int lastPixelY = firstPixelY + game.getPieceSize() - 1;
        
        if (dropped) {
        	c = new Color (c.getRed(), c.getGreen(), c.getBlue(), (c.getAlpha()/5));
        }

        // Fill the middle of the square.
        g.setColor(c);
        g.fillRect(firstPixelX, firstPixelY, game.getPieceSize() - 1, game.getPieceSize() - 1);
        // Draw the top and left border with a brighter color.
        g.setColor(c.brighter());
        g.drawLine(firstPixelX, firstPixelY, lastPixelX, firstPixelY); 	// top left to top (right-1)
        g.drawLine(firstPixelX, firstPixelY + 1, firstPixelX, lastPixelY);	// (top-1) left to bottom left
        // Draw the bottom and right border with a darker color.
        g.setColor(c.darker());
        g.drawLine(lastPixelX, firstPixelY + 1, lastPixelX, lastPixelY - 1);	// top right to (bottom-1) right
        g.drawLine(firstPixelX + 1, lastPixelY, lastPixelX, lastPixelY);	// bottom (left+1) to bottom right

    }
    
    void drawSinglePiece (Graphics g, int x, int y, Color c) {
    	drawSinglePiece  (g, x, y, c, false);
    }
    
    
    
    
    
    // TODO: Draw level information.





}







