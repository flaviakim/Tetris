package logic;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import graphics.GamePanel;

/**
 *
 **/
public class Tetris implements ActionListener, KeyListener{
	
	int rowCountX = 10;	// The number of horizontal rows
	public int getRowCountX() { return rowCountX; }
	int rowCountY = 20;	// The number of vertical rows
	public int getRowCountY() { return rowCountY; }
	public int getRowWidth() { return (int) panel.getSize().getWidth() / rowCountX; }	// The width of one row in pixels.
	public int getRowHeight() { return (int) panel.getSize().getHeight() / rowCountY; }	// The height of one row in pixels.
	
	public int piecesPerShape = 4;
	public int rowsAboveGameLine = piecesPerShape;
	
	Timer updateTimer;	// The timer responsible for dropping the pieces down one row.
	public Timer getTimer() { return updateTimer; }
	float speed = 1;	// How often the current piece drops down one row in drops per second.
	boolean paused = false;	// Whether the game is currently paused (true) or running (false).
	
	Piece[][] gameBoard; // x and y coordinates for each piece. 0,0 is the top left corner.
	Piece currentPiece;
	public Piece getCurrentPiece() { return currentPiece; }
	
	int currentScore;	// The current score (calculated by speed and lines removed).
	
	GamePanel panel;	// The parent panel in which this GameLogic is displayed in.
	
	
	// INITIALIZING
	
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
	
	public void startGame () {
		
		updateTimer.start();
		generateNewPiece();
		
		panel.repaint();
		
	}
	
	
	// TIMER EVENT
	
	/**
	 * This gets called every time the timer is at 0 and is responsible for the game flow.
	 **/
	public void actionPerformed (ActionEvent e) {
		
		// TODO: Either move the current piece down one row or if it already is down then create a new shape.
		//		 If the creation of the shape isn't possible the game is over.
				
		if (currentPiece == null) {
			System.out.println("GameLogic::actionPerformed -- ERROR: current Piece == null.\nThere should always be a current piece!");
			return;
		}
		
		if (canDropDownOne()) {
			dropDownOne();
		} else {
			placePiece();
			deleteFullRows();
			generateNewPiece();
		}
		
		System.out.println("actionPerformed!");
		panel.repaint();
		
	}
	
	
	// GAME UPDATE METHODS
	
	/**
	 * This generates a new single Piece at the top of the board at a random position.
	 **/
	void generateNewPiece () {
		
		// TODO: Shapes from different Pieces. Use an Array of currentPieces instead and set the positions according to a random generated shape.
		// TODO: Check if full!
		
		// Choose a random starting location
		int startX = (int) Math.random() * rowCountX;
		
		currentPiece = new Piece(startX, 0);
	}
	
	/**
	 * Drops the current Piece down one row, if there is nothing below it.
	 * Returns true if succeeded, false if the space below is already occupied.
	 * Uses canDropDownOne() to see, if the space below is occupied.
	 **/
	boolean dropDownOne() {
		if (canDropDownOne()) {
			currentPiece.position.y += 1;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the space below the current piece is free, false if the space below is already occupied.
	 **/
	boolean canDropDownOne() {
		if (isPositionOccupied(currentPiece.position.x, currentPiece.position.y + 1)) {
			return false;
		}
		return true;
	}
	
	/**
	 * This Method gets called, when a Piece can't drop any further.
	 * It puts the Piece at it's current position into the gameBoard.
	 * Afterwards there should always be a call to generate a new Piece!
	 **/
	void placePiece() {
		Vector2 v = currentPiece.position;
		
		if (gameBoard[v.x][v.y] != null) {
			System.out.println("Tetris::placePiece -- ERROR: There is already a Piece, where the current should be placed!\nProbably the canDropDownOne check went wrong.");
		}
		
		gameBoard[v.x][v.y] = currentPiece;
		currentPiece = null;
	}
	
	
	void deleteFullRows() {
		
		for (int y = 0; y < rowCountY; y++) {
			boolean full = true;
			for (int x = 0; x < rowCountX; x++) {
				if (isPositionOccupied(x, y) == false) {
					full = false;
				}
			}
			if (full) {
				deleteRow(y);
				y--; // we have to check this line again because all the lines dropped down one
				currentScore++;
			}
		}
		
	}
	
	void deleteRow(int y) {
		for (int x = 0; x < rowCountX; x++) {
			// Delete every piece in the y row.
			gameBoard[x][y] = null;
			
			// TODO: Animate Line Removal (pause game, send message to panel, wait for panel to finish animation, unpause).
			
			// Drop every row above the y row down by one (xrow for xrow)
			for (int i = y; i > 0; i--) {
				gameBoard[x][i] = gameBoard[x][i-1];
			}
		}
		
	}
	
	
	// KEY EVENT METHODS
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			tryMoveLeft();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			tryMoveRight();
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dropDownOne(); // drop down one row
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			while(dropDownOne()) {} // drop down to the bottom.
		}
		
		panel.repaint();
		System.out.println("Key Pressed!");
	}
	
	boolean tryMoveLeft() {
		if (canMoveLeft()) {
			currentPiece.position.x -= 1;
			return true;
		} else {
			return false;
		}
	}
	
	boolean canMoveLeft() {
		if (isPositionOccupied(currentPiece.position.x - 1, currentPiece.position.y)) {
			return false;
		}
		return true;
	}
	
	boolean tryMoveRight() {
		if (canMoveRight()) {
			currentPiece.position.x += 1;
			return true;
		} else {
			return false;
		}
	}
	
	boolean canMoveRight() {
		if (isPositionOccupied(currentPiece.position.x + 1, currentPiece.position.y)) {
			return false;
		}
		return true;
	}
	
	
	
	// ACCESSOR METHODS
	
	public boolean isPositionOccupied(int x, int y) {
		if (x >= rowCountX || x < 0 || y >= rowCountY || y < 0) {
			return true; // The borders count like occupied fields for this method
		}
		if (gameBoard[x][y] != null) {
			return true;
		}
		return false;
	}
	
	public Piece getPieceAt(int x, int y) {
		if (isPositionOccupied(x, y) == false) {
			System.out.println("Tetris::getPieceAt -- ERROR: Trying to get a piece at an empty position.");
			return null;
		} else {
			return gameBoard[x][y];
		}
	}
	
}







