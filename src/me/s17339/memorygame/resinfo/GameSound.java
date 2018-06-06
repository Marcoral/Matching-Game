package me.s17339.memorygame.resinfo;

public enum GameSound {
	GENERIC_HOVER("generic_hover"),
	INGAME_PAIR_CORRECT("ingame_pair-collected"),
	INGAME_PAIR_WRONG("ingame_pair-wrong"),
	INGAME_REVEAL("ingame_reveal"),
	LOBBY_JOINED("lobby_joined"),
	LOBBY_QUIT("lobby_quit");

	private String path;
	private GameSound(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
