package me.s17339.memorygame.javafx;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_ControllerMainMenu;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_DefaultControllerMainMenu;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_ViewMainMenu;
import me.s17339.memorygame.javafx.scene.model.JavaFX_GameSettingsModelDriver;
import me.s17339.memorygame.javafx.scene.model.JavaFX_GameboardSettingsModelDriver;
import me.s17339.memorygame.javafx.scene.roots.JavaFX_SceneRootDraggable;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.model.gameboard.GameboardSettingsInterface;
import me.s17339.memorygame.model.scoreboard.ScoreboardSetModel;
import me.s17339.memorygame.model.settings.GameSettingsInterface;

public class JavaFX_Main extends Application {
	private static GameboardSettingsInterface modelGameboardSettings;
	private static GameSettingsInterface modelSettings;
	private static ScoreboardSetModel modelScoreboards;
	
	public static void initialize(String[] args, GameboardSettingsInterface modelGameboardSettings, GameSettingsInterface modelSettings, ScoreboardSetModel modelScoreboards) throws IOException {
		JavaFX_Main.modelGameboardSettings = modelGameboardSettings;
		JavaFX_Main.modelSettings = modelSettings;
		JavaFX_Main.modelScoreboards = modelScoreboards;
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//Resources setup
		JavaFX_GameResources.setup();

		//Adjust of stage look and feel
		JavaFX_GameLayout.adjustStage(stage);
		
		//Creation of JavaFX model environment
		JavaFX_GameSettingsModelDriver modelSettingsDriver = new JavaFX_GameSettingsModelDriver(JavaFX_Main.modelSettings);
		JavaFX_GameboardSettingsModelDriver modelGameboardSettingsDriver = new JavaFX_GameboardSettingsModelDriver(JavaFX_Main.modelGameboardSettings);
		JavaFX_GameEnvironmentInfo environment = new JavaFX_GameEnvironmentInfo(modelSettingsDriver, modelGameboardSettingsDriver, modelScoreboards);
		
		//Initialization of main menu window
		JavaFX_SceneRoot sceneRoot = new JavaFX_SceneRootDraggable(stage);
		JavaFX_ControllerMainMenu controller = new JavaFX_DefaultControllerMainMenu(environment, sceneRoot);
		JavaFX_ViewMainMenu mainMenu = controller.createView();
		Scene scene = new Scene(mainMenu.getFrame());
		stage.setScene(scene);
		mainMenu.playMusic();
		
		playInitialAnimation(mainMenu.getFrame());
		stage.show();
	}
	
	private void playInitialAnimation(Parent frame) {
		FadeTransition transition = new FadeTransition(Duration.millis(2000), frame);
		transition.setFromValue(0);
		transition.setInterpolator(Interpolator.EASE_OUT);
		transition.setToValue(1);
		transition.play();
	}
}
