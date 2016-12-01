package graphics;

public enum Difficulty {
	
	EASY	(1f, true),
	MEDIUM	(2f, true),
	HARD	(4f, false);
	
	public final float initialSpeed;
	public final boolean droppedShapePreview;
	
	Difficulty (float initialSpeed, boolean droppedShapePreview) {
		this.initialSpeed = initialSpeed;
		this.droppedShapePreview = droppedShapePreview;
	}
	
}

