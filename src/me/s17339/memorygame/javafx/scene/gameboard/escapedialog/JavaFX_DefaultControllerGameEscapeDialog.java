package me.s17339.memorygame.javafx.scene.gameboard.escapedialog;

import javafx.event.ActionEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneElement;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_ControllerMainMenu;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_DefaultControllerMainMenu;
import me.s17339.memorygame.javafx.scene.settingslayer.JavaFX_ControllerSettingsLayer;
import me.s17339.memorygame.javafx.scene.settingslayer.JavaFX_DefaultControllerSettingsLayer;

public class JavaFX_DefaultControllerGameEscapeDialog implements JavaFX_ControllerGameEscapeDialog {

	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_SceneElement rootSceneElement;
	public JavaFX_DefaultControllerGameEscapeDialog(JavaFX_GameEnvironmentInfo environment, JavaFX_SceneElement root) {
		this.environment = environment;
		this.rootSceneElement = root;
	}
	
	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_ViewGameEscapeDialog view;
	@Override
	public JavaFX_ViewGameEscapeDialog createView() {
		if(view != null)
			return view;
		view = new JavaFX_ViewGameEscapeDialog(this, environment);
		return view;
	}

	@Override
	public void handleSettingsButtonClick(ActionEvent event) {
		view.detach();
		JavaFX_ControllerSettingsLayer settingsLayerController = new JavaFX_DefaultControllerSettingsLayer(environment);
		settingsLayerController.createView().attach(rootSceneElement, environment);
	}
	
	@Override
	public void handleReturnButtonClick(ActionEvent event) {
		view.detach();
	}

	@Override
	public void handleRagequitButtonClick(ActionEvent event) {
		JavaFX_ControllerMainMenu mainMenuController = new JavaFX_DefaultControllerMainMenu(environment, rootSceneElement.getRoot());
		mainMenuController.createView();
	}
}
