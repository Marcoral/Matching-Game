package me.s17339.memorygame.javafx.scene.abstractscenelayer;

import javafx.event.ActionEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_Escapeable;

public interface JavaFX_ControllerSceneLayer extends JavaFX_Escapeable {
	void handleReturnButtonClick(ActionEvent event);
}
