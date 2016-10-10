package logic;

import java.awt.Color;

public class Piece {
	
	// PROPERTIES
	
	float probability; // UNDONE: How likely is this shape to apear.
	
	public Color color = new Color(204, 102, 102);
	
	public Vector2 position;
	
	// INITIALIZING
	
	public Piece(int x, int y) {
		
		// TODO: Bigger Sizes than only 1 square Tiles. 
		position = new Vector2(x, y);
				
	}
		
}