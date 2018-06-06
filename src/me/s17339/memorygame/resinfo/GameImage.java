package me.s17339.memorygame.resinfo;

public enum GameImage {
	ICON("icon.png"),
	
	LEVEL_CHOOSING_ALZHEIMER("level_alzheimer.png"),
	LEVEL_CHOOSING_EASY("level_warmup.png"),
	LEVEL_CHOOSING_MODERATE("level_moderate.png"),
	LEVEL_CHOOSING_CHALLENGING("level_challenging.png");
	
	private String path;
	private GameImage(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
