package logic;

import java.awt.Color;

import java.util.Random;
import java.util.Collections.*;

public enum Shape {
	
	// SHAPES
	
	I_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(0,2), new Vector2(0,3)}, new Color (204, 102, 102)),
	L_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(0,2), new Vector2(1,2)}, new Color (102, 204, 102)),
	L_REVERSE_SHAPE	(new Vector2[]{new Vector2(1,0), new Vector2(1,1), new Vector2(1,2), new Vector2(0,2)}, new Color (102, 102, 204)),
	S_SHAPE			(new Vector2[]{new Vector2(0,1), new Vector2(1,1), new Vector2(1,0), new Vector2(2,0)}, new Color (204, 204, 102)),
	T_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(0,2), new Vector2(1,1)}, new Color (204, 102, 204)),
	Z_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(1,0), new Vector2(1,1), new Vector2(2,1)}, new Color (102, 204, 204)),
	SQUARE_SHAPE	(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(1,0), new Vector2(1,1)}, new Color (102, 102, 102));
	
	// PROPERTIES
	
	private static final Shape[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();
	
	Vector2[] positions; // TODO The first position should be the center piece. So we can rotate around it.
	public Vector2[] getPositions() { return positions; }
	int turns = 0;
	Color color;
	
	// INITIALIZING
	
	Shape(Vector2[] positions, Color color) {
		this.positions = positions;
		this.color = color;
	}
	
	// METODS
	
	public static Shape getRandomShape() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
	
	/**
	 * The Width in Pieces of this Shape
	 **/
	public int getWidth() {
		int rightmostX = 0;
		for (int i = 0; i < positions.length; i++) {
			if (positions[i].x > rightmostX) {
				rightmostX = positions[i].x;
			}
		}
		//System.out.println("rightmostX: " + rightmostX);
		return rightmostX + 1;
	}
	
	/**
	 * The Height in Pieces of this Shape
	 **/
	public int getHeight() {
		int lowestY = 0;
		for (int i = 0; i < positions.length; i++) {
			if (positions[i].y > lowestY) {
				lowestY = positions[i].y;
			}
		}
		//System.out.println("lowestY: " + lowestY);
		return lowestY + 1;
	}
	
	/**
	 * The Width in Pieces of the given array of Pieces.
	 **/
	public static int getWidth(Piece[] pieces) {
		
		int leftmostX = getLeftmostPiece(pieces).position.x;
		int rightmostX = getRightmostPiece(pieces).position.x;
		
		return rightmostX - leftmostX + 1;
	}
	
	/**
	 * The Height in Pieces of the given array of Pieces.
	 **/
	public static int getHeight(Piece[] pieces) {
		
		int highestY = getHighestPiece(pieces).position.y;
		int lowestY = getLowestPiece(pieces).position.y;
		
		return lowestY - highestY + 1;
	}
	
	public static Piece getHighestPiece(Piece[] pieces) {
		Piece highestPiece = pieces[0];
		for (int i = 1; i < pieces.length; i++) {
			if (pieces[i].position.y < highestPiece.position.y) {
				highestPiece = pieces[i];
			}
		}
		return highestPiece;
	}
	
	public static Piece getLowestPiece(Piece[] pieces) {
		Piece lowestPiece = pieces[0];
		for (int i = 1; i < pieces.length; i++) {
			if (pieces[i].position.y > lowestPiece.position.y) {
				lowestPiece = pieces[i];
			}
		}
		return lowestPiece;
	}
	
	public static Piece getLeftmostPiece(Piece[] pieces) {
		Piece leftmostPiece = pieces[0];
		for (int i = 1; i < pieces.length; i++) {
			if (pieces[i].position.x < leftmostPiece.position.x) {
				leftmostPiece = pieces[i];
			}
		}
		return leftmostPiece;
	}
	
	public static Piece getRightmostPiece(Piece[] pieces) {
		Piece rightmostPiece = pieces[0];
		for (int i = 1; i < pieces.length; i++) {
			if (pieces[i].position.x > rightmostPiece.position.x) {
				rightmostPiece = pieces[i];
			}
		}
		return rightmostPiece;
	}	
}