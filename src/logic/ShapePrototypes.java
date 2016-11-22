package logic;

import java.awt.Color;

import java.util.Random;
import java.util.Collections.*;

/** 
 * The Prototype Shapes
 **/
public enum ShapePrototypes {
	
	// SHAPES
	
	I_SHAPE		(new Vector2[]{new Vector2(1,0), new Vector2(0,0), new Vector2(2,0), new Vector2(3,0)}, new Color (204, 102, 102), true,  4),
	L_SHAPE		(new Vector2[]{new Vector2(1,1), new Vector2(0,1), new Vector2(2,1), new Vector2(2,0)}, new Color (102, 204, 102), true,  3),
	J_SHAPE		(new Vector2[]{new Vector2(1,1), new Vector2(0,0), new Vector2(0,1), new Vector2(2,1)}, new Color (102, 102, 204), true,  3),
	S_SHAPE		(new Vector2[]{new Vector2(1,1), new Vector2(0,1), new Vector2(1,0), new Vector2(2,0)}, new Color (204, 204, 102), true,  3),
	T_SHAPE		(new Vector2[]{new Vector2(1,1), new Vector2(0,1), new Vector2(2,1), new Vector2(1,0)}, new Color (204, 102, 204), true,  3),
	Z_SHAPE		(new Vector2[]{new Vector2(1,1), new Vector2(0,0), new Vector2(1,0), new Vector2(2,1)}, new Color (102, 204, 204), true,  3),
	O_SHAPE		(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(1,0), new Vector2(1,1)}, new Color (102, 102, 102), false, 2);
	
	// PROPERTIES
	
	private static final ShapePrototypes[] VALUES = values();
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();
	
	Vector2[] positions; // TODO The first position should be the center piece. So we can rotate around it.
	public Vector2[] getPositions() { return positions; }
	boolean isRotateable;
	public boolean getIsRotateable() { return isRotateable; }
	Color color;
	public Color getColor() { return color; }
	
	final int startWidth;
	public int getStartWidth() { return startWidth; }
	
	// INITIALIZING
	
	ShapePrototypes(Vector2[] positions, Color color, boolean isRotateable, int startWidth) {
		this.positions = new Vector2[positions.length];
		for (int i = 0; i < positions.length; i++) {
			this.positions[i] = new Vector2 (positions[i].x, positions[i].y);
		} 
		this.color = color;
		this.isRotateable = isRotateable;
		this.startWidth = startWidth;
	}
	
	// METODS
	
	public static Shape getRandomShape(Tetris game) {
		return new Shape(VALUES[RANDOM.nextInt(SIZE)], game);
	}
		
}