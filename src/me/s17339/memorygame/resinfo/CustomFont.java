package me.s17339.memorygame.resinfo;

public enum CustomFont {
	ALAMAKOTA("alamakota"),
	SUNRISE("sunrise");
	
	private String path;
	private CustomFont(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
}
