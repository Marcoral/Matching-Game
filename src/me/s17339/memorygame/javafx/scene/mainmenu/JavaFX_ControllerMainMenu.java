package me.s17339.memorygame.javafx.scene.mainmenu;

import javafx.event.ActionEvent;

public interface JavaFX_ControllerMainMenu {
	JavaFX_ViewMainMenu createView();
	
	void handleNewGameButtonClick(ActionEvent event);
	void handleCreditsButtonClick(ActionEvent event);
	void handleHighscoresButtonClick(ActionEvent event);
	void handleSettingsButtonClick(ActionEvent event);
	void handleQuitButtonClick(ActionEvent event);
	
	void handleSongPlayRequest();
}
