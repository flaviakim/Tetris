package logic;

import java.awt.Color;

import java.util.Random;
import java.util.Collections.*;

public class Shape {
	
	// PROPERTIES
	
	ShapePrototypes prototype;
	public ShapePrototypes getPrototype() { return prototype; }
	Tetris game;
	
	Piece[] pieces;
	public Piece[] getPieces() { return pieces; }
	
	boolean isRotateable;
	
	
	// INITIALIZING
	
	/**
	 * Generates a new Shape in the middle of the {@link gameBoard}.
	 **/
	public Shape (ShapePrototypes prototype, Tetris game) {
		this.prototype = prototype;
		this.game = game;
		
		pieces = new Piece[prototype.getPositions().length];
		for (int i = 0; i < pieces.length; i++) {
			Vector2 position = new Vector2 (prototype.getPositions()[i].x, prototype.getPositions()[i].y);
			position.x += game.getRows()/2 - prototype.getStartWidth()/2;
			pieces[i] = new Piece(position, prototype.getColor(), this);
		}
				
		isRotateable = prototype.getIsRotateable();
				
	}
	
	
	// METODS
	
	/**
	 * Rotates this Shape by 90° around the middle in clockwise direction.
	 **/
	public boolean rotate () {
		if (isRotateable == false) return false;
		if (canRotate() == false) return false;
		
		int middleY = pieces[0].position.y;
		int middleX = pieces[0].position.x;
		
		for (int i = 1; i < pieces.length; i++) {
			int newX = middleX - (pieces[i].position.y - middleY);
			int newY = (middleY + pieces[i].position.x) - middleX;
			pieces[i].position.y = newY;
			pieces[i].position.x = newX;
		}
		return true;
	}
	
	/**
	 * Checks to see if the rotation of this Shape is possible.
	 **/
	boolean canRotate() {
		if (isRotateable == false) return false;
		
		int middleY = pieces[0].position.y;
		int middleX = pieces[0].position.x;
		
		for (int i = 1; i < pieces.length; i++) {
			int newX = middleX - (pieces[i].position.y - middleY);
			int newY = (middleY + pieces[i].position.x) - middleX;
			if (game.isPositionOccupied(newX, newY)) {
				System.out.println("canRotate -- Rotation impossible, position (" + newX + "/" + newY + ") is already occupied!");
				return false;
			}
		}
		return true;
	}
	
	/** Drops the current Shape by one or places it on the Game Board.
	 * Drops the current Pieces down one row, if there is nothing below all of them.
	 * Returns true if succeeded, false if the space below is already occupied.
	 * Uses canDropDownOne() to see, if the spaces below are occupied.
	 * If canDropDownOne() failed the Shape gets placed.
	 **/
	public boolean dropDownOne() {
		if (canDropDownOne() == false) {
			placeShape();
			return false;
		}
		
		for (int i = 0; i < pieces.length; i++) {
			pieces[i].position.y += 1;
		}
		//System.out.println("droppedDownOne");
		return true;
	}
	
	/**
	 * Returns true if the space below all the current pieces is free,
	 * false if at least on of the spaces below is already occupied.
	 **/
	boolean canDropDownOne() {
		for (int i = 0; i < pieces.length; i++) {
			if (game.isPositionOccupied(pieces[i].position.x, pieces[i].position.y + 1)) {
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
	void placeShape() {
		for (int i = 0; i < pieces.length; i++) {
			Vector2 v = pieces[i].position;
			if (game.isPositionOccupied(v.x, v.y)) {
				System.out.println("Tetris::placeCurrentPieces -- ERROR: There is already a Piece, where the current should be placed!\nProbably the canDropDownOne check went wrong or Game Over didn't work.");
			}
			game.setPieceAt(v.x, v.y, pieces[i]);
		}
		pieces = null;
		game.resetCurrentShape();
		game.checkFullRows();
		//System.out.println("placedCurrentPieces");
	}
	
	/**
	 * Moves all the Pieces of the Shape to the right if the canMoveRight methods returns true.
	 **/
	public boolean tryMoveRight() {
		if (canMoveRight() == false) return false;
		for (int i = 0; i < pieces.length; i++) {
			pieces[i].position.x += 1;
		}
		return true;
	
	}
	
	/**
	 * Checks whether all the pieces of the Shape can move right.
	 **/
	boolean canMoveRight() {
		for (int i = 0; i < pieces.length; i++) {
			if (game.isPositionOccupied(pieces[i].position.x + 1, pieces[i].position.y)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Moves all the Pieces of the Shape to the right if the canMoveRight methods returns true.
	 **/
	public boolean tryMoveLeft() {
		if (canMoveLeft() == false) return false;
		for (int i = 0; i < pieces.length; i++) {
			pieces[i].position.x -= 1;
		}
		return true;
	
	}
	
	/**
	 * Checks whether all the pieces of the Shape can move right.
	 **/
	boolean canMoveLeft() {
		for (int i = 0; i < pieces.length; i++) {
			if (game.isPositionOccupied(pieces[i].position.x - 1, pieces[i].position.y)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether the game is over.
	 * The game is over when this <code>Shape</code>'s position is already occupied.
	 * Should only be called when the Shape is created.
	 * 
	 * @return returns <code>true</code> if the game is over, <code>false</code> otherwise.
	 **/
	public boolean checkGameOver() {
		for (Piece p : pieces) {
			if (game.isPositionOccupied(p.position.x, p.position.y)) return true;
		}
		return false;
	}
	
	
	
}












