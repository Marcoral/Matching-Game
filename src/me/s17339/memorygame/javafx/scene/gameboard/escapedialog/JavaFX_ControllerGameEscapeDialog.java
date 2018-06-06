package me.s17339.memorygame.javafx.scene.gameboard.escapedialog;

import javafx.event.ActionEvent;

public interface JavaFX_ControllerGameEscapeDialog {
	JavaFX_ViewGameEscapeDialog createView();

	void handleSettingsButtonClick(ActionEvent event);
	void handleRagequitButtonClick(ActionEvent event);
	void handleReturnButtonClick(ActionEvent event);
}
