package me.s17339.memorygame.model.gameboard;

public class OddNumberOfTilesGameException extends RuntimeException {
	private static final long serialVersionUID = 3146111217554082851L;
	private int width, height;

	OddNumberOfTilesGameException(int width, int height) {
		super("Attempted to create board with odd number of tiles! (" + width + "x" + height + ")");
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}