package logic;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import graphics.*;

/**
 * This is the class that handles the main game logic. It starts immediately when created.
 **/
public class Tetris implements ActionListener, KeyListener {
	
	// PROPERTIES
	
	int rowCountX = 10;	// The number of horizontal rows
	public int getRowCountX() { return rowCountX; }
	int rowCountY = 20;	// The number of vertical rows
	public int getRowCountY() { return rowCountY; }
		
		// The size of the game is defined by the width of it's parent. The Height is calculated with rowCountX and rowCountY.
	public int getGameWidth() { return (int) getWindow().getSize().getWidth(); }	// The width of the Game
	public int getRowSize() { return getGameWidth() / rowCountX; } // The width and height of one row.
	public int getGameHeight() { return getRowSize() * rowCountY; } // The height of the Game (defined by it's width and the row Counts.
		
	Timer updateTimer;	// The timer responsible for dropping the pieces down one row.
	public Timer getTimer() { return updateTimer; }
	float speed = 1;		// How often the current piece drops down one row in drops per second.
	float initialSpeed = 1; // In case the speed changes during the game (not yet implemented) and we wan't to restart it with this.
	float speedPerLevel = 0.5f;	// By how much the speed gets raised per level.
	boolean paused = false;	// Whether the game is currently paused (true) or running (false).
	public boolean getPaused() { return paused; }
	boolean gameOver = false;
	
	Piece[][] gameBoard; // x and y coordinates for each piece. 0,0 is the top left corner.
	Piece[] currentPieces; // The current Pieces falling down. The first one is the center Piece the others rotate around.
	public Piece[] getCurrentPieces() { return currentPieces; }
	Shape currentShape;
	public Shape getCurrentShape() { return currentShape; }
	Shape nextShape;
	public Shape getNextShape() { return nextShape; }
	
	int linesDeleted = 0;	// How many lines were deleted in the current game.
	int level = 1;			// The current level.
	int linesPerLevel = 5;	// How many lines do we have to delete to raise a level
	int currentScore = 0;	// The current score (calculated by speed and lines removed).
	public int getCurrentScore() { return currentScore; }
	
	GamePanel panel;	// The parent panel in which this GameLogic is displayed in.
	GameWindow getWindow() { return panel.getWindow(); } // The window this game('s panel) is displayed in.
	
	
	// INITIALISING
	
	/**
	 * Initialises the Game and sets the speed and the number of rows.
	 **/
	public Tetris (GamePanel panel, float speed, int rowCountX, int rowCountY) {
		this.panel = panel;
		
		this.rowCountX = rowCountX;
		this.rowCountY = rowCountY;
		this.speed = speed;
		this.initialSpeed = speed;
		
		gameBoard = new Piece[rowCountX][rowCountY];
		
		updateTimer = new Timer((int)(1000/speed), this);
		updateTimer.setInitialDelay(0);

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
		gameBoard = new Piece[rowCountX][rowCountY];
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
		getWindow().endGame();
		System.out.println("ENDGAME");
	}
	
	
	// TIMER EVENT
	
	/**
	 * This gets called every time the timer is at 0 and is responsible for the game flow.
	 **/
	public void actionPerformed (ActionEvent e) {
		
		// TODO: Either move the current piece down one row or if it already is down then create a new shape.
		//		 If the creation of the shape isn't possible the game is over.
				
		if (currentPieces == null) {
			recalcSpeed();
			checkFullRows();
			generateNewShape();
		} else if (canDropDownOne()) {
			dropDownOne();
		} else {
			placeCurrentPieces();
		}
		
		//System.out.println("actionPerformed!");
		panel.repaint();
		
	}
	
	/**
	 * Recalculates the speed with the score.
	 */
	void recalcSpeed() {
		speed = ((currentScore / 4) * 0.5f) + initialSpeed;
		updateTimer.setDelay((int)(1000/speed));
	}
	
	
	// GAME UPDATE METHODS
	
	/**
	 * This generates new Pieces according to the next Shape at the top of the board at a random x position.
	 */
	void generateNewShape() {
		
		// TODO: Check if full! => GameOver
		if (nextShape == null) {
			generateNextShape();
		}
		
		// Create a new Shape with the Prototype
		currentShape = nextShape;
		// Set a random position
		int posX = (int)(Math.random() * (rowCountX - currentShape.getWidth() + 1));
		currentPieces = new Piece[currentShape.getPositions().length];
		for (int i = 0; i < currentPieces.length; i++) {
			currentPieces[i] = new Piece(currentShape.getPositions()[i], currentShape.color, currentShape);
			currentPieces[i].position.x += posX;
			if (gameBoard[currentPieces[i].position.x][currentPieces[i].position.y] != null) {
				currentPieces = null;
				doGameOver();
				return;
			}
			//System.out.println("Generated new Piece at (" + currentShape.getPositions()[i].x + "/" + currentShape.getPositions()[i].y + ")");
		}
		
		/*// Moves the tile into the middle -- unused
		for (int i = 0; i < ((rowCountX/2) - (currentShape.getWidth()/2)); i++) {
			tryMoveRight();
		}*/
		
		generateNextShape();
				
		//System.out.println("generatedNewShape");
		
	}
	
	/**
	 * Generates a new Random Shape for the next Shape.
	 **/
	void generateNextShape() {
		nextShape = Shape.getRandomShape();
	}
	
	/**
	 * Drops the current Pieces down one row, if there is nothing below all of them.
	 * Returns true if succeeded, false if the space below is already occupied.
	 * Uses canDropDownOne() to see, if the spaces below are occupied.
	 **/
	boolean dropDownOne() {
		if (canDropDownOne()) {
			for (int i = 0; i < currentPieces.length; i++) {
				currentPieces[i].position.y += 1;
			}
			//System.out.println("droppedDownOne");
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the space below all the current pieces is free, false if at least on of the spaces below is already occupied.
	 **/
	boolean canDropDownOne() {
		for (int i = 0; i < currentPieces.length; i++) {
			if (isPositionOccupied(currentPieces[i].position.x, currentPieces[i].position.y + 1)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This Method gets called, when a Piece can't drop any further.
	 * It puts the Piece at it's current position into the gameBoard.
	 * Afterwards there should always be a call to generate a new Piece!
	 **/
	void placeCurrentPieces() {
		for (int i = 0; i < currentPieces.length; i++) {
			Vector2 v = currentPieces[i].position;
			if (gameBoard[v.x][v.y] != null) {
				//doGameOver();
				System.out.println("Tetris::placeCurrentPieces -- ERROR: There is already a Piece, where the current should be placed!\nProbably the canDropDownOne check went wrong or Game Over didn't work.");
			}
			gameBoard[v.x][v.y] = currentPieces[i];
		}
		currentPieces = null;
		//System.out.println("placedCurrentPieces");
	}
	
	/**
	 * Checks all rows and deletes them if they are full with deleteRow().
	 **/
	void checkFullRows() {
		
		// Check each y row.
		for (int y = 0; y < rowCountY; y++) {
			boolean full = true;
			// Check each x in the y rows.
			for (int x = 0; x < rowCountX; x++) {
				// If one x isn't occupied, the y row isn't full and we can jump to the next y loop
				if (isPositionOccupied(x, y) == false) {
					full = false;
					break; // TOCHECK: Does this end the x loop or the if statement? Probably the x loop. Wouldn't matter anyway, only slightly for performance. Better: continue the y loop with the next y, so we don't have to declare the boolean variable.
				}
			}
			if (full) {
				deleteRow(y);
				y--; // We have to check this line again because all the lines dropped down one.
				currentScore++;
			}
		}
		
		//System.out.println("deletedFullRows");
	}
	
	/**
	 * Deletes the specified y row and drops all the pieces above by one.
	 **/
	void deleteRow(int y) {
		for (int x = 0; x < rowCountX; x++) {
			// Delete every piece in the y row.
			gameBoard[x][y] = null;
			
			// TODO: Animate Line Removal (pause game, send message to panel, wait for panel to finish animation, unpause).
			// or by setting a variable +1 and if the variable is >0 the update Timer drops every piece and sets it -1. (This would just wait with dropping the line(s) one tic instead of animating sth)
			
			// Drop every row above the y row down by one (xrow for xrow)
			for (int i = y; i > 0; i--) {
				gameBoard[x][i] = gameBoard[x][i-1];
			}
		}
	}
	
	
	/**
	 * If a Piece can't be placed anymore this Game Over Method should end the Game.
	 **/
	void doGameOver() {
		updateTimer.stop();
		gameOver = true;
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
		
		if (paused) {
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			endGame();
			return;
		}
		
		if (currentPieces == null) {
			return;
		}
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				tryMoveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				tryMoveRight();
				break;
			case KeyEvent.VK_DOWN:
				//dropDownOne(); // drop down one row
				updateTimer.restart();
				break;
			case KeyEvent.VK_SPACE:
				while(dropDownOne()) {} // drop down to the bottom.
				updateTimer.restart();	// Set the Timer to 0, so the Piece get's added to the board immediately and can't be moved anymore.
				break;
			case KeyEvent.VK_UP:
				if (canRotateClockwise90()) {
					rotateClockwise90();
				}
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
		paused = !paused;
		if (paused) {
			updateTimer.stop();
		} else {
			updateTimer.start();
		}
		panel.changePauseLabel();
	}
	
	/**
	 * Moves all the Pieces of the Shape to the left if the canMoveLeft methods returns true.
	 **/
	boolean tryMoveLeft() {
		if (canMoveLeft()) {
			for (int i = 0; i < currentPieces.length; i++) {
				currentPieces[i].position.x -= 1;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether all the pieces of the Shape can move left.
	 **/
	boolean canMoveLeft() {
		for (int i = 0; i < currentPieces.length; i++) {
			if (isPositionOccupied(currentPieces[i].position.x - 1, currentPieces[i].position.y)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Moves all the Pieces of the Shape to the right if the canMoveRight methods returns true.
	 **/
	boolean tryMoveRight() {
		if (canMoveRight()) {
			for (int i = 0; i < currentPieces.length; i++) {
				currentPieces[i].position.x += 1;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether all the pieces of the Shape can move right.
	 **/
	boolean canMoveRight() {
		for (int i = 0; i < currentPieces.length; i++) {
			if (isPositionOccupied(currentPieces[i].position.x + 1, currentPieces[i].position.y)) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Rotates the currentPieces by 90° around the middle in clockwise direction.
	 **/
	void rotateClockwise90() {
		if (currentShape.isRotateable == false) return;
		
		int middleY = currentPieces[0].position.y;
		int middleX = currentPieces[0].position.x;
		
		for (int i = 1; i < currentPieces.length; i++) {
			int newX = middleX - (currentPieces[i].position.y - middleY);
			int newY = (middleY + currentPieces[i].position.x) - middleX;
			currentPieces[i].position.y = newY;
			currentPieces[i].position.x = newX;
		}
		
	}
	
	/**
	 * Checks to see if the rotation is possible.
	 **/
	boolean canRotateClockwise90() {
		if (currentShape.isRotateable == false) return false;
		
		int middleY = currentPieces[0].position.y;
		int middleX = currentPieces[0].position.x;
		
		for (int i = 1; i < currentPieces.length; i++) {
			int newX = middleX - (currentPieces[i].position.y - middleY);
			int newY = (middleY + currentPieces[i].position.x) - middleX;
			if (isPositionOccupied(newX, newY)) {
				System.out.println("canRotateClockwise90 -- Rotation impossible, position (" + newX + "/" + newY + ") is already occupied!");
				return false;
			}
		}
		return true;
	}
	
	
	// GAME BOARD METHODS
	
	/**
	 * Checks if the position (x, y) is occupied with a static piece (not the falling Pieces).
	 **/
	public boolean isPositionOccupied(int x, int y) {
		if (x >= rowCountX || x < 0 || y >= rowCountY || y < 0) {
			return true; // The borders count like occupied fields for this method
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
	
}







