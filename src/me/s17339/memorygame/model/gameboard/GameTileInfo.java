package me.s17339.memorygame.model.gameboard;

public class GameTileInfo {
	private int number;
	private boolean isCollected;
	private boolean isRevealed;
	
	public GameTileInfo(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	public boolean isCollected() {
		return isCollected;
	}
	
	public boolean isRevealed() {
		return isRevealed;
	}
	
	
	
	void setCollected(boolean value) {
		this.isCollected = value;
	}
	
	void setRevealed(boolean value) {
		this.isRevealed = value;
	}
}