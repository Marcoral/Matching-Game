package me.s17339.memorygame.javafx.scene.difficultychoosing;

import javafx.event.ActionEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.gameboard.JavaFX_DefaultControllerGameboard;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.model.gameboard.GameboardGeneratorConstraints;
import me.s17339.memorygame.model.gameboard.GameboardInterface;
import me.s17339.memorygame.model.gameboard.GameboardModel;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;

public class JavaFX_DefaultControlerDifficultyChoosing implements JavaFX_ControllerDifficultyChoosing {	
		
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/

	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_SceneRoot root;
	public JavaFX_DefaultControlerDifficultyChoosing(JavaFX_GameEnvironmentInfo environment, JavaFX_SceneRoot root) {
		this.environment = environment;
		this.root = root;
	}
	
	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/

	@Override
	public JavaFX_ViewDifficultyChoosing createView() {
		return new JavaFX_ViewDifficultyChoosing(environment, this);
	}
	
	@Override
	public void handleBoardChooseLevelAlzheimer(ActionEvent event) {
		startGame(4, DifficultyLevel.ALZHEIMER);
	}

	@Override
	public void handleBoardChooseLevelEasy(ActionEvent event) {
		startGame(6, DifficultyLevel.EASY);
	}

	@Override
	public void handleBoardChooseLevelModerate(ActionEvent event) {
		startGame(8, DifficultyLevel.MODERATE);
	}

	@Override
	public void handleBoardChooseLevelChallenging(ActionEvent event) {
		startGame(10, DifficultyLevel.CHALLENGING);
	}
	
	private void startGame(int size, DifficultyLevel difficultyLevel) {
		GameboardGeneratorConstraints generatorConstraints = new GameboardGeneratorConstraints(size, size, JavaFX_GameResources.getRegisteredPicturesCount());
		GameboardInterface modelGameboard = new GameboardModel(environment.getModelGameboardSettings().getNativeModel(), generatorConstraints);
		JavaFX_DefaultControllerGameboard controller = new JavaFX_DefaultControllerGameboard(modelGameboard, environment, root,	difficultyLevel);
		controller.createView();
	}
}
