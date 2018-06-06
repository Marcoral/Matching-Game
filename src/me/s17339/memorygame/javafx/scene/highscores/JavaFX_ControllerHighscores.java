package me.s17339.memorygame.javafx.scene.highscores;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;

public interface JavaFX_ControllerHighscores {
	JavaFX_ViewHighscores createView(DifficultyLevel initialDifficulty);
	
	void handleReturnButtonClick(ActionEvent event);
	void adjustDifficultySelectionComboBox(ComboBox<JavaFX_DifficultyLevelEntry> comboBox);
}
