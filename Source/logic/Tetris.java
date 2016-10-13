package logic;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import graphics.GamePanel;

/**
 * This is the class that handles the main game logic. It starts immediately when created.
 **/
public class Tetris implements ActionListener, KeyListener {
	
	// PROPERTIES
	
	int rowCountX = 10;	// The number of horizontal rows
	public int getRowCountX() { return rowCountX; }
	int rowCountY = 20;	// The number of vertical rows
	public int getRowCountY() { return rowCountY; }
	public int getRowWidth() { return (int) panel.getSize().getWidth() / rowCountX; }	// The width of one row in pixels.
	public int getRowHeight() { return (int) panel.getSize().getHeight() / rowCountY; }	// The height of one row in pixels.
	
	public int piecesPerShape = 4;
	public int getRowsAboveGameLine() { return piecesPerShape; } // How many lines are only for the new Piece to generate in. TODO: If a piece get's added there in the GameBoard, the Game is over!
	
	Timer updateTimer;	// The timer responsible for dropping the pieces down one row.
	public Timer getTimer() { return updateTimer; }
	float speed = 1;	// How often the current piece drops down one row in drops per second.
	boolean paused = false;	// TODO! Whether the game is currently paused (true) or running (false).
	
	Piece[][] gameBoard; // x and y coordinates for each piece. 0,0 is the top left corner.
	Piece[] currentPieces;
	public Piece[] getCurrentPieces() { return currentPieces; }
	Shape currentShape;
	public Shape getCurrentShape() { return currentShape; }
	Shape nextShape;
	public Shape getNextShape() { return nextShape; }
	
	int currentScore;	// The current score (calculated by speed and lines removed).
	
	GamePanel panel;	// The parent panel in which this GameLogic is displayed in.
	
	
	// INITIALIZING
	
	/**
	 * 
	 **/
	public Tetris (GamePanel panel, float speed, int rowCountX, int rowCountY) {
		this.panel = panel;
		
		this.rowCountX = rowCountX;
		this.rowCountY = rowCountY;
		this.speed = speed;
		
		gameBoard = new Piece[rowCountX][rowCountY];
		
		updateTimer = new Timer((int)(1000/speed), this);
				
		startGame();
		
		System.out.println("GameLogic created! Game started!");
	}
	
	/**
	 * 
	 **/
	public void startGame () {
		
		updateTimer.setInitialDelay(0);
		updateTimer.start();
				
	}
	
	
	// TIMER EVENT
	
	/**
	 * This gets called every time the timer is at 0 and is responsible for the game flow.
	 **/
	public void actionPerformed (ActionEvent e) {
		
		// TODO: Either move the current piece down one row or if it already is down then create a new shape.
		//		 If the creation of the shape isn't possible the game is over.
				
		if (currentPieces == null) {
			deleteFullRows();
			generateNewShape();
		} else if (canDropDownOne()) {
			dropDownOne();
		} else {
			placeCurrentPieces();
		}
		
		//System.out.println("actionPerformed!");
		panel.repaint();
		
	}
	
	
	// GAME UPDATE METHODS
	
	/**
	 * This generates new Pieces according to the next Shape at the top of the board at the middle.
	 **/
	void generateNewShape() {
		
		// TODO: Check if full! => GameOver
		if (nextShape == null) {
			generateNextShape();
		}
		
		currentShape = nextShape;
		currentPieces = new Piece[currentShape.getPositions().length];
		for (int i = 0; i < currentPieces.length; i++) {
			currentPieces[i] = new Piece(currentShape.getPositions()[i], currentShape.color);
			System.out.println("Generated new Piece at (" + currentShape.getPositions()[i].x + "/" + currentShape.getPositions()[i].y + ")");
		}
		
		// Moves the tile into the middle
		for (int i = 0; i < ((rowCountX/2) - (currentShape.getWidth()/2)); i++) {
			tryMoveRight();
		}
		
		generateNextShape();
				
		//System.out.println("generatedNewShape");
		
	}
	
	/**
	 * Generates a new Random Shape for the next Shape.
	 **/
	void generateNextShape() {
		// TODO: Random Generation of all Shapes!
		
		
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
				System.out.println("Tetris::placeCurrentPieces -- ERROR: There is already a Piece, where the current should be placed!\nProbably the canDropDownOne check went wrong or Game Over didn't work.");
			}
			// TODO: If Peace get's placed over the line, call GameOver().
			gameBoard[v.x][v.y] = currentPieces[i];
		}
		currentPieces = null;
		
		//System.out.println("placedCurrentPieces");
	}
	
	/**
	 * Checks all rows and deletes them if they are full with deleteRow().
	 **/
	void deleteFullRows() {
		
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
	
	
	// KEY EVENT METHODS
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * 
	 **/
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_P) {
			pauseGame();
			return;
		}
		
		if (paused) {
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
				dropDownOne(); // drop down one row
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
	 * 
	 **/
	void pauseGame() {
		paused = !paused;
		if (paused) {
			updateTimer.stop();
		} else {
			updateTimer.start();
		}
	}
	
	/**
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * Rotates the currentPieces by 90° around the middle in clockwise direction
	 **/
	void rotateClockwise90() {
		int middleX = (Shape.getWidth(currentPieces)-1) / 2;
		int middleY = (Shape.getHeight(currentPieces)-1) / 2;
		int leftmostX = Shape.getLeftmostPiece(currentPieces).position.x;
		int highestY = Shape.getHighestPiece(currentPieces).position.y;
		System.out.println("width: " + Shape.getWidth(currentPieces) + ", height: " + Shape.getHeight(currentPieces));
		System.out.println("middleX: " + middleX + ", middleY: " + middleY);
		for (Piece p : currentPieces) {
			int currX = p.position.x - middleX - leftmostX; // The current x position of the piece relative to the middle Piece
			int currY = p.position.y - middleY - highestY; // The current y position of the piece relative to the middle Piece
			int newX = -currY;
			int newY = currX;
			System.out.println("newX relative to middle Piece: " + newX + "; newY relative to middle Piece: " + newY);
			newX += middleX + leftmostX;
			newY += middleY + highestY;
			System.out.println("newX in GameWorld: " + newX + "; newY in GameWorld: " + newY);
			p.position.x = newX;
			p.position.y = newY;
		}
	}
	
	/**
	 *
	 **/
	boolean canRotateClockwise90() {
		int middleX = (Shape.getWidth(currentPieces)-1) / 2;
		int middleY = (Shape.getHeight(currentPieces)-1) / 2;
		int leftmostX = Shape.getLeftmostPiece(currentPieces).position.x;
		int highestY = Shape.getHighestPiece(currentPieces).position.y;
		//System.out.println("width: " + Shape.getWidth(currentPieces) + ", height: " + Shape.getHeight(currentPieces));
		//System.out.println("middleX: " + middleX + ", middleY: " + middleY);
		for (Piece p : currentPieces) {
			int currX = p.position.x - middleX - leftmostX; // The current x position of the piece relative to the middle Piece
			int currY = p.position.y - middleY - highestY; // The current y position of the piece relative to the middle Piece
			int newX = -currY;
			int newY = currX;
			//System.out.println("newX relative to middle Piece: " + newX + "; newY relative to middle Piece: " + newY);
			newX += middleX + leftmostX;
			newY += middleY + highestY;
			//System.out.println("newX in GameWorld: " + newX + "; newY in GameWorld: " + newY);
			if (isPositionOccupied(newX, newY)) {
				System.out.println("canRotateClockwise90 -- Rotation impossible, position (" + newX + "/" + newY + ") is already occupied!");
				return false;
			}
		}
		return true;
	}
	
	
	// GAME BOARD METHODS
	
	/**
	 * 
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
	 * 
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







