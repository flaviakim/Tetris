package logic;

import java.awt.Color;

public enum Shape {
	
	// SHAPES
	
	I_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(0,2), new Vector2(0,3)}, Color.magenta),
	L_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(0,2), new Vector2(1,2)}, Color.red),
	L_REVERSE_SHAPE	(new Vector2[]{new Vector2(1,0), new Vector2(1,1), new Vector2(1,2), new Vector2(0,2)}, Color.yellow),
	S_SHAPE			(new Vector2[]{new Vector2(0,1), new Vector2(1,1), new Vector2(1,0), new Vector2(2,0)}, Color.cyan),
	T_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(0,2), new Vector2(1,1)}, Color.orange),
	Z_SHAPE			(new Vector2[]{new Vector2(0,0), new Vector2(1,0), new Vector2(1,1), new Vector2(2,1)}, Color.green),
	SQUARE_SHAPE	(new Vector2[]{new Vector2(0,0), new Vector2(0,1), new Vector2(1,0), new Vector2(1,1)}, Color.blue);
	
	// PROPERTIES
	
	Vector2[] positions;
	Color color;
	
	// INITIALIZING
	
	Shape(Vector2[] positions, Color color) {
		this.positions = positions;
		this.color = color;
	}
	
}