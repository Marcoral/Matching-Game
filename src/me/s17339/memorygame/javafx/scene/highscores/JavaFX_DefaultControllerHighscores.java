package me.s17339.memorygame.javafx.scene.highscores;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_ControllerMainMenu;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_DefaultControllerMainMenu;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;

public class JavaFX_DefaultControllerHighscores implements JavaFX_ControllerHighscores {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_SceneRoot sceneRoot;
	private JavaFX_ViewHighscores view;
	public JavaFX_DefaultControllerHighscores(JavaFX_GameEnvironmentInfo environment, JavaFX_SceneRoot sceneRoot) {
		this.environment = environment;
		this.sceneRoot = sceneRoot;
	}
	
	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/

	@Override
	public JavaFX_ViewHighscores createView(DifficultyLevel initialDifficulty) {
		view = new JavaFX_ViewHighscores(environment, this, sceneRoot, initialDifficulty);
		return view;
	}

	@Override
	public void adjustDifficultySelectionComboBox(ComboBox<JavaFX_DifficultyLevelEntry> comboBox) {
		comboBox.getItems().add(new JavaFX_DifficultyLevelEntry(environment, DifficultyLevel.ALZHEIMER));
		comboBox.getItems().add(new JavaFX_DifficultyLevelEntry(environment, DifficultyLevel.EASY));
		comboBox.getItems().add(new JavaFX_DifficultyLevelEntry(environment, DifficultyLevel.MODERATE));
		comboBox.getItems().add(new JavaFX_DifficultyLevelEntry(environment, DifficultyLevel.CHALLENGING));
		comboBox.valueProperty().addListener(event -> view.refreshScores(comboBox.getValue().level));		
	}

	@Override
	public void handleReturnButtonClick(ActionEvent event) {
		JavaFX_ControllerMainMenu mainMenuController = new JavaFX_DefaultControllerMainMenu(environment, sceneRoot);
		mainMenuController.createView().playMusic();
	}
}
