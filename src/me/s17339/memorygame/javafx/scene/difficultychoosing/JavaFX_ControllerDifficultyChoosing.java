package me.s17339.memorygame.javafx.scene.difficultychoosing;

import javafx.event.ActionEvent;

public interface JavaFX_ControllerDifficultyChoosing {
	JavaFX_ViewDifficultyChoosing createView();
	
	void handleBoardChooseLevelAlzheimer(ActionEvent event);
	void handleBoardChooseLevelEasy(ActionEvent event);
	void handleBoardChooseLevelModerate(ActionEvent event);
	void handleBoardChooseLevelChallenging(ActionEvent event);
}
