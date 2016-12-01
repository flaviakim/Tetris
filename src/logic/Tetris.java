package logic;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import graphics.*;

/**
 * This is the <code>class</code> that handles the main game logic.
 * The game starts immediately when created (TODO: start via Main class).
 **/
public class Tetris implements ActionListener, KeyListener {
	
	// PROPERTIES
	
	static final int rows = 10;
	public static int getRows() { return rows; }
	
	static final int columns = 20;
	public static int getColumns() { return columns; }
		
	/**
	 * The width of the game. Defined by the size of the window.
	 **/
	public int getGameWidth() { return (int) panel.getWindow().getSize().getWidth(); }
	
	/**
	 * The size of a single square {@link Piece}. It's defined by {@link getGameWidth()}.
	 **/
	public int getPieceSize() { return getGameWidth() / rows; }
	
	/**
	 * The height of the game is defined by the {@link getGameWidth()} and {@link getPieceSize()}
	 **/
	public int getGameHeight() { return getPieceSize() * columns; }
	
	/**
	 * The timer responsible for dropping the pieces down one row.
	 **/
	final Timer updateTimer;
	public Timer getTimer() { return updateTimer; }
	
	/**
	 * How often the {@link currentShape} drops down one row in drops per second.
	 **/
	float speed = 1;
	
	/**
	 * Because the speed can change during the game this is for restarting it.
	 **/
	final float initialSpeed;
	
	final float speedRaisePerLevel = 0.25f;
	
	boolean isPaused = false;
	
	public boolean getPaused() { return isPaused; }
	
	boolean gameOver = false;
	
	/**
	 * The <code>gameBoard</code> keeps track of all the fallen {@link Piece}s.
	 * The array represents the x and y coordinates for each piece.
	 * [0][0] is the top left corner.
	 * <p>
	 * The {@link currentShape} is stored separately.
	 **/
	Piece[][] gameBoard;
	
	/**
	 * The Shape which is currently falling down.
	 * It stores all the falling {@link Piece}s.
	 **/
	Shape currentShape;
	public Shape getCurrentShape() { return currentShape; }
	
	/**
	 * The Shape which will become the {@link currentShape} in the next round.
	 **/
	Shape nextShape;
	public Shape getNextShape() { return nextShape; }
	
	/**
	 * Keeps track of the exact number of lines deleted in the current game.
	 **/
	int linesDeleted = 0;
	
	/**
	 * The current Level that determines the <code>speed</code> and the <code>score</code>
	 * gain per level.
	 **/
	int level = 1;
	public int getLevel() { return level; }
	
	/**
	 * How many lines does the player have to delete to get to the next <code>level</code>.
	 **/
	final int linesPerLevel = 5;
	
	/**
	 * The current score which is calculated by speed and lines removed.
	 **/
	int currentScore = 0;
	public int getCurrentScore() { return currentScore; }
	
	/**
	 * The parent <code>JPanel</code> in which this <code>Tetris</code> is displayed in.
	 * (TODO: The {@link Main} <code>class</code> should know the Game Logic
	 * <code>class</code>es and the Visual <code>class</code>es. They shouldn't know
	 * anything about each other.)
	 **/
	final GamePanel panel; 
	
	
	// INITIALISING
	
	/**
	 * Initialises the Game and sets the speed and the number of rows.
	 * 
	 * @param panel  the GamePanel this game is displayed in (TODO: replace with Main method)
	 * @param speed  the game's {@link speed}
	 * TODO:
	 **/
	public Tetris (GamePanel panel, Difficulty difficulty) {
		this.panel = panel;
		
		this.speed = difficulty.initialSpeed;
		this.initialSpeed = difficulty.initialSpeed;
		
		gameBoard = new Piece[rows][columns];
		
		updateTimer = new Timer((int)(1000/speed), this);

		startGame();
		
		System.out.println("GameLogic created! Game started!");
	}
	
	/**
	 * Starts the Timer and therefore the game.
	 **/
	public void startGame () {
		updateTimer.start();
	}
	
	/**
	 * Restarts the Game with the same initial speed.
	 **/
	public void restartGame() {
		panel.restartGame();
		gameBoard = new Piece[rows][columns];
		currentShape = null;
		nextShape = null;
		currentScore = 0;
		speed = initialSpeed;
		gameOver = false;
		updateTimer.setDelay((int)(1000/speed));
		updateTimer.restart();
	}
	
	/**
	 * Ends the game and goes back to the Start Screen.
	 **/
	public void endGame() {
		updateTimer.stop();
		panel.getWindow().endGame();
		System.out.println("ENDGAME");
	}
	
	
	// TIMER EVENT
	
	/**
	 * Responsible for the game flow.
	 * Gets called every time the timer is at 0. 
	 **/
	public void actionPerformed (ActionEvent e) {
				
		if (currentShape == null) {
			recalcSpeed();
			checkFullRows();
			generateNewShape();
		} else {
			currentShape.dropDownOne();
		}
		
		panel.repaint();
		panel.getWindow().getGameInfoPanel().repaint();
	}
	
	/**
	 * Recalculates the speed with the score.
	 */
	void recalcSpeed() {
		level = (linesDeleted / linesPerLevel) + 1;
		speed = ((level - 1) * speedRaisePerLevel) + initialSpeed;
		//System.out.println("level: " + level + "; speed: " + speed + "; linesDeleted: " + linesDeleted + "; speedRaisePerLevel: " + speedRaisePerLevel + "; linesPerLevel: " + linesPerLevel);
		updateTimer.setDelay((int)(1000/speed));
	}
	
	
	// GAME UPDATE METHODS
	
	/**
	 * This generates new Pieces according to the next Shape at the top of the board at a random x position.
	 */
	void generateNewShape() {
	
		if (nextShape == null) {
			generateNextShape();
		}
		currentShape = nextShape;
		
		if (currentShape.checkGameOver()) {
			doGameOver();
			return;
		}
		
		generateNextShape();	
		
		//System.out.println("generatedNewShape");
		
	}
	
	/**
	 * Generates a new Random Shape for the next Shape.
	 **/
	void generateNextShape() {
		nextShape = ShapePrototypes.getRandomShape(this);
	}
	
	
	
	/**
	 * Checks all rows and deletes them if they are full with deleteRow().
	 **/
	void checkFullRows() {
		int deletedRows = 0;
		// Check each y row.
		for (int y = 0; y < columns; y++) {
			boolean full = true;
			// Check each piece in the row.
			for (int x = 0; x < rows; x++) {
				// If one x isn't occupied, the y row isn't full and we can jump to the next y loop
				if (isPositionOccupied(x, y) == false) {
					full = false;
					break;
				}
			}
			if (full) {
				deleteRow(y);
				linesDeleted++;
				deletedRows++;
			}
		}
		if (deletedRows > 0) dropDownEmptyRows(deletedRows); // TODO(maybe): remove the rows in the next update for a better visualisation.
		increaseScoreForDeletedRows (deletedRows);
		//System.out.println("deletedFullRows");
	}
	
	/**
	 * Deletes the specified row.
	 **/
	void deleteRow(int y) {
		for (int x = 0; x < rows; x++) {
			// Delete every piece in the row.
			gameBoard[x][y] = null;
			
			// TODO(maybe): Animate Line Removal (pause game, send message to panel, wait for panel to finish animation, unpause).
			
		}
	}
	
	/**
	 * Dropps down all the empty rows.
	 *
	 * @param count  How many rows were deleted this turn, so we don't check all the empty lines above.
	 **/
	void dropDownEmptyRows (int count) {
		for (int y = columns-1; y >= 0; y--) {
			boolean empty = true;
			for (int x = 0; x < rows; x++) {
				if (isPositionOccupied(x, y)) {
					empty = false;
					break;
				}
			}
			if (empty) {
				count--;
				// Drop every row above the empty row down by one (row for row)
				for (int i = y; i > 0; i--) {
					for (int x = 0; x < rows; x++) {
						gameBoard[x][i] = gameBoard[x][i-1];
					}
				}
				y++; // We have to check the row again because it's been deleted.
			}
			if (count <= 0) return;
		}
	}
	
	/**
	 * Increases the score according to the level and how many rows were deleted with one Shape.
	 * The number of deleted Rows is squared and then multiplied with the level.
	 **/
	void increaseScoreForDeletedRows (int deletedRows) {
		currentScore += deletedRows * deletedRows * level;
	}
	
	/**
	 * If a Piece can't be placed anymore this Game Over Method should end the Game.
	 **/
	void doGameOver() {
		updateTimer.stop();
		gameOver = true;
		currentShape = null;
		nextShape = null;
		panel.doGameOver();
	}
	
	
	// KEY EVENT METHODS
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * Handles the Key Pressed Event.
	 **/
	public void keyPressed(KeyEvent e) {
		
		if (gameOver) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					restartGame();
					break;
				case KeyEvent.VK_ESCAPE:
					endGame();
					break;
				default:
					break;
			}
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_P) {
			pauseGame();
			return;
		}
		
		if (isPaused) {
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			endGame();
			return;
		}
		
		if (currentShape == null) {
			return;
		}
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				currentShape.tryMoveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				currentShape.tryMoveRight();
				break;
			case KeyEvent.VK_DOWN:
				currentShape.dropDownOne(); // drop down one row
				updateTimer.restart();
				break;
			case KeyEvent.VK_SPACE:
				while(currentShape.canDropDownOne()) {currentShape.dropDownOne();} // drop down to the bottom.
				updateTimer.restart();
				break;
			case KeyEvent.VK_UP:
				currentShape.rotate();
				break;
			default:
				// Non-relevant Key Pressed. Ignore.
				break;
		}
		
		panel.repaint();
		//System.out.println("Key Pressed!");
	}
	
	/**
	 * Pauses the game.
	 **/
	void pauseGame() {
		isPaused = !isPaused;
		if (isPaused) {
			updateTimer.stop();
		} else {
			updateTimer.start();
		}
		panel.changePauseLabel();
	}
	
	
	
	// GAME BOARD METHODS
	
	/**
	 * Checks if the position (x, y) is occupied
	 * Only static pieces count, not the falling Pieces from the current Shape.
	 * All the positions outside the game Board count as occupied.
	 **/
	public boolean isPositionOccupied(int x, int y) {
		if (x >= rows || x < 0 || y >= columns || y < 0) {
			return true;
		}
		if (gameBoard[x][y] != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the piece at the (x, y) position or null if there is none.
	 * Should first be checked with isPositionOccupied(x, y).
	 **/
	public Piece getPieceAt(int x, int y) {
		if (isPositionOccupied(x, y) == false) {
			System.out.println("Tetris::getPieceAt -- ERROR: Trying to get a piece at an empty position.");
			return null;
		} else {
			return gameBoard[x][y];
		}
	}
	
	// TODO: Check which (protected?) allows only access inside same package.
	boolean setPieceAt(int x, int y, Piece p) {
		if (isPositionOccupied(x, y)) {
			System.out.println("Tetris::getPieceAt -- ERROR: Trying to set a piece where one already exists.");
			return false;
		} else {
			gameBoard[x][y] = p;
			return true;
		}
	}
	
	/**
	 * Sets the current Shape to null.
	 * In the next Timer update the next Shape will be set to the current Shape.
	 **/
	public void resetCurrentShape() {
		currentShape = null;
	}
	
}







