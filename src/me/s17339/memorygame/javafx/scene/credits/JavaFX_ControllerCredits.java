package me.s17339.memorygame.javafx.scene.credits;

import javafx.event.ActionEvent;

public interface JavaFX_ControllerCredits {
	JavaFX_ViewCredits createView();
	
	void handleReturnButtonClick(ActionEvent event);
}
