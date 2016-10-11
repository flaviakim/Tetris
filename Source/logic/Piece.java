package logic;

import java.awt.Color;

// TODO: Enum with different kind of shapes.
// TODO: Static Dictionary inside linking Colors to Shapes.
// TODO: How to arrange Pieces for each Shape.


public class Piece {
	
	// PROPERTIES
	
	public Color color = new Color(204, 102, 102); // TODO: Use color according to the Shape this Piece is part of.
	
	public Vector2 position;
	
	// TODO: The Shape this is part of
	
	
	// INITIALIZING
	
	public Piece(int x, int y) {
		position = new Vector2(x, y);	
	}
	public Piece(Vector2 position) {
		this.position = position;
	}
		
}