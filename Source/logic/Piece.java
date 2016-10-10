package logic;

public class Piece {
	
	// PROPERTIES
	
	float probability; // UNDONE: How likely is this shape to apear.
	
	int color = 0; // 0 = white; 1 = blue; 2 = green;
	
	public Vector2 position;
	
	// INITIALIZING
	
	public Piece(int x, int y) {
		
		// TODO: Bigger Sizes than only 1 square Tiles. 
		position = new Vector2(x, y);
				
	}
		
}