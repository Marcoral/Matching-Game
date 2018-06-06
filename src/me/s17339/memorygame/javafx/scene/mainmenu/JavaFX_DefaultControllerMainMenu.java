package me.s17339.memorygame.javafx.scene.mainmenu;

import javafx.event.ActionEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.credits.JavaFX_ControllerCredits;
import me.s17339.memorygame.javafx.scene.credits.JavaFX_DefaultControllerCredits;
import me.s17339.memorygame.javafx.scene.difficultychoosing.JavaFX_ControllerDifficultyChoosing;
import me.s17339.memorygame.javafx.scene.difficultychoosing.JavaFX_DefaultControlerDifficultyChoosing;
import me.s17339.memorygame.javafx.scene.difficultychoosing.JavaFX_ViewDifficultyChoosing;
import me.s17339.memorygame.javafx.scene.highscores.JavaFX_ControllerHighscores;
import me.s17339.memorygame.javafx.scene.highscores.JavaFX_DefaultControllerHighscores;
import me.s17339.memorygame.javafx.scene.settingslayer.JavaFX_ControllerSettingsLayer;
import me.s17339.memorygame.javafx.scene.settingslayer.JavaFX_DefaultControllerSettingsLayer;
import me.s17339.memorygame.javafx.scene.settingslayer.JavaFX_ViewSettingsLayer;
import me.s17339.memorygame.javafx.utils.JavaFX_GameAudio;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLogic;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;
import me.s17339.memorygame.resinfo.GameSong;

public class JavaFX_DefaultControllerMainMenu implements JavaFX_ControllerMainMenu {
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_ViewMainMenu view;
	private JavaFX_SceneRoot sceneRoot;
		
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	public JavaFX_DefaultControllerMainMenu(JavaFX_GameEnvironmentInfo environment, JavaFX_SceneRoot sceneRoot) {
		this.environment = environment;
		this.sceneRoot = sceneRoot;
	}

	@Override
	public JavaFX_ViewMainMenu createView() {
		if(view != null)
			throw new RuntimeException("Attempted to bind the same instance of DefaultMainMenuController to more than one view!");
		view = new JavaFX_ViewMainMenu(environment, this, sceneRoot);
		return view;
	}
	
	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/

	@Override
	public void handleNewGameButtonClick(ActionEvent event) {
		validateViewNotNull();
		JavaFX_GameLogic.performDefaultButtonClickAction(environment.getModelSettings().getSoundsVolumeProperty());
		JavaFX_ControllerDifficultyChoosing controllerDifficultyChoosing = new JavaFX_DefaultControlerDifficultyChoosing(environment, sceneRoot);
		JavaFX_ViewDifficultyChoosing difficultyChoosing = controllerDifficultyChoosing.createView();
		difficultyChoosing.attach(view, environment);
	}

	@Override
	public void handleCreditsButtonClick(ActionEvent event) {
		JavaFX_GameLogic.performDefaultButtonClickAction(environment.getModelSettings().getSoundsVolumeProperty());
		JavaFX_ControllerCredits controllerCredits = new JavaFX_DefaultControllerCredits(environment, view.getRoot());
		controllerCredits.createView();
	}

	@Override
	public void handleHighscoresButtonClick(ActionEvent event) {
		JavaFX_GameLogic.performDefaultButtonClickAction(environment.getModelSettings().getSoundsVolumeProperty());
		JavaFX_ControllerHighscores controllerHighscores = new JavaFX_DefaultControllerHighscores(environment, sceneRoot);
		controllerHighscores.createView(DifficultyLevel.ALZHEIMER);
	}

	@Override
	public void handleSettingsButtonClick(ActionEvent event) {
		validateViewNotNull();
		JavaFX_GameLogic.performDefaultButtonClickAction(environment.getModelSettings().getSoundsVolumeProperty());
		JavaFX_ControllerSettingsLayer controllerSettings = new JavaFX_DefaultControllerSettingsLayer(environment);
		JavaFX_ViewSettingsLayer settings = controllerSettings.createView();
		settings.attach(view, environment);
	}

	@Override
	public void handleQuitButtonClick(ActionEvent event) {
		validateViewNotNull();
		if(view.getRoot().getStage() == null)
			return;
		else
			view.getRoot().getStage().close();
	}

	@Override
	public void handleSongPlayRequest() {
		JavaFX_GameAudio.playSong(GameSong.MENU, environment.getModelSettings().getSongsVolumeProperty());
	}
	
	private void validateViewNotNull() {
		if(view == null)
			throw new RuntimeException("Attempted to access view method of non-binded to view instance of DefaultMainMenuController!");
	}
}
