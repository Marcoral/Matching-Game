package me.s17339.memorygame.resinfo;

public enum GameSong {
	MENU("menu"),
	INGAME_EASY("level_easy"),
	INGAME_MODERATE("level_moderate"),
	INGAME_CHALLENGING("level_challenging");
	
	private String path;
	private GameSong(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
