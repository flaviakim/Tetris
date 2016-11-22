package logic;

import java.awt.Color;

// TODO: Enum with different kind of shapes.
// TODO: Static Dictionary inside linking Colors to Shapes.
// TODO: How to arrange Pieces for each Shape.

/**
 * One single Piece.
 * Every {@link Shape} is made up of <code>Piece</code>s (standard: 4)
 * and the <code>gameBoard</code> in {@link Tetris} is made up of <code>Piece</code>s.
 **/
public class Piece {
	
	// PROPERTIES
	
	public Color color;
	
	/**
	 * The position on the {@link gameBoard}.
	 **/
	public Vector2 position;
	
	// The Shape this Piece is part of. (TODO: is this really necessary?)
	Shape shape;
		
	
	// INITIALIZING
	
	/**
	 * Creates a new <code>Piece</code>.
	 *
	 * @param position  where it should start on the {@link gameBoard}. A new <code>Vectore</code> is created from these values.
	 * @param color     what <code>Color</code> this piece has
	 * @param shape     the Shape instace this piece is part of (TODO: is this really necessary?)
	 **/
	public Piece(Vector2 position, Color color, Shape shape) {
		this.color = color;
		this.position = new Vector2 (position.x, position.y);
		this.shape = shape;
	}
		
}