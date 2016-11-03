package logic;

import java.awt.Color;

// TODO: Enum with different kind of shapes.
// TODO: Static Dictionary inside linking Colors to Shapes.
// TODO: How to arrange Pieces for each Shape.


public class Piece {
	
	// PROPERTIES
	
	public Color color = new Color(254, 254, 254); // The color of this Piece (default color shouldn't actually be used)
	
	public Vector2 position;
	
	Shape shape;
	
	boolean center;
	
	// TODO: The Shape this is part of
	
	
	// INITIALIZING
	
	public Piece(int x, int y) {
		position = new Vector2(x, y);	
		System.out.println("ERROR: Wrong Piece Constructor used");
	}
	public Piece(Vector2 position) {
		this.position = new Vector2 (position.x, position.y);
		System.out.println("ERROR: Wrong Piece Constructor used");
	}
	public Piece(int x, int y, Color color) {
		this.color = color;
		position = new Vector2(x, y);	
	}
	public Piece(Vector2 position, Color color) {
		this.color = color;
		this.position = new Vector2 (position.x, position.y);
	}
		
}