package me.s17339.memorygame.javafx.scene.highscores;

import javafx.beans.value.ObservableValue;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;

public class JavaFX_DifficultyLevelEntry {
	ObservableValue<? extends String> text;
	DifficultyLevel level;
	
	JavaFX_DifficultyLevelEntry(JavaFX_GameEnvironmentInfo environment, DifficultyLevel level) {
		this.text = environment.getModelSettings().getLanguageNode(level.getCorrespondingLanguageNode());
		this.level = level;
	}

	@Override
	public String toString() {
		return text.getValue();
	}
}
