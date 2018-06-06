package me.s17339.memorygame.javafx.scene.credits;

import javafx.event.ActionEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_ControllerMainMenu;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_DefaultControllerMainMenu;

public class JavaFX_DefaultControllerCredits implements JavaFX_ControllerCredits {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_SceneRoot root;
	public JavaFX_DefaultControllerCredits(JavaFX_GameEnvironmentInfo environment, JavaFX_SceneRoot root) {
		this.environment = environment;
		this.root = root;
	}
	
	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/
	
	@Override
	public JavaFX_ViewCredits createView() {
		return new JavaFX_ViewCredits(this, environment, root);
	}

	@Override
	public void handleReturnButtonClick(ActionEvent event) {
		JavaFX_ControllerMainMenu mainMenuController = new JavaFX_DefaultControllerMainMenu(environment, root);
		mainMenuController.createView();
	}
}
