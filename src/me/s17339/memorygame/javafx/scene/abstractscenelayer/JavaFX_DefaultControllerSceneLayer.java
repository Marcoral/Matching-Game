package me.s17339.memorygame.javafx.scene.abstractscenelayer;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLogic;

public class JavaFX_DefaultControllerSceneLayer implements JavaFX_ControllerSceneLayer {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_AbstractSceneLayer view;
	private JavaFX_GameEnvironmentInfo environment;
	public JavaFX_DefaultControllerSceneLayer(JavaFX_AbstractSceneLayer view, JavaFX_GameEnvironmentInfo environment) {
		this.view = view;
		this.environment = environment;
	}
	
	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/
	
	@Override
	public void handleEscapeButtonPress(KeyEvent event) {
		view.detach();
	}

	@Override
	public void handleReturnButtonClick(ActionEvent event) {
		JavaFX_GameLogic.performDefaultButtonClickAction(environment.getModelSettings().getSoundsVolumeProperty());
		view.detach();
	}
}
