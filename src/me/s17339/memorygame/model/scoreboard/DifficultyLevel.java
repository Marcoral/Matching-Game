package me.s17339.memorygame.model.scoreboard;

import me.s17339.memorygame.resinfo.GameSong;
import me.s17339.memorygame.resinfo.LanguageNode;

public enum DifficultyLevel {
	ALZHEIMER(LanguageNode.HIGHSCORES_LEVEL_ALZHEIMER, GameSong.INGAME_EASY),
	EASY(LanguageNode.HIGHSCORES_LEVEL_EASY, GameSong.INGAME_EASY),
	MODERATE(LanguageNode.HIGHSCORES_LEVEL_MODERATE, GameSong.INGAME_MODERATE),
	CHALLENGING(LanguageNode.HIGHSCORES_LEVEL_CHALLENGING, GameSong.INGAME_CHALLENGING);
	
	private LanguageNode correspondingLanguageNode;
	private GameSong correspondingSong;
	private DifficultyLevel(LanguageNode correspondingLanguageNode, GameSong correspondingSong) {
		this.correspondingLanguageNode = correspondingLanguageNode;
		this.correspondingSong = correspondingSong;
	}
	
	public LanguageNode getCorrespondingLanguageNode() {
		return correspondingLanguageNode;
	}

	public GameSong getCorrespondingSong() {
		return correspondingSong;
	}
}

