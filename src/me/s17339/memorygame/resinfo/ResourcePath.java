package me.s17339.memorygame.resinfo;

public enum ResourcePath {
	SERIALIZATION_SETTINGS_FILE("Settings_general.ser"),
	SERIALIZATION_GAMEBOARDSETTINGS_FILE("Settings_gameboard.ser"),
	SERIALIZATION_GAMEBOARDS_FILE("Highscores.ser"),
	PICTURES_DIRECTORY("images/tiles/"),
	PICTURES_REVERSE("icon.png"), 
	CSS_DIRECTORY("/css/"),
	FONTS_DIRECTORY("/fonts/"),
	IMAGES_DIRECTORY("/images/");
	
	private String path;
	private ResourcePath(String path) {
		this.path = path;
	}
	
	public String get() {
		return path;
	}
}
