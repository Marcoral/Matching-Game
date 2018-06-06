package me.s17339.memorygame.javafx.scene;

import me.s17339.memorygame.javafx.scene.model.JavaFX_GameSettingsModelDriver;
import me.s17339.memorygame.javafx.scene.model.JavaFX_GameboardSettingsModelDriver;
import me.s17339.memorygame.model.scoreboard.ScoreboardSetModel;

public class JavaFX_GameEnvironmentInfo {
	private JavaFX_GameboardSettingsModelDriver modelGameboardSettings;
	private JavaFX_GameSettingsModelDriver modelSettings;
	private ScoreboardSetModel modelScoreboards;
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	public JavaFX_GameEnvironmentInfo(JavaFX_GameSettingsModelDriver modelSettings, JavaFX_GameboardSettingsModelDriver modelGameboardSettings, ScoreboardSetModel modelScoreboards) {
		this.modelSettings = modelSettings;
		this.modelGameboardSettings = modelGameboardSettings;
		this.modelScoreboards = modelScoreboards;
	}

	/* ----------------------------------------	*/
	/*					GETTERS					*/
	/* ----------------------------------------	*/
	
	public JavaFX_GameSettingsModelDriver getModelSettings() {
		return modelSettings;
	}
	public JavaFX_GameboardSettingsModelDriver getModelGameboardSettings() {
		return modelGameboardSettings;
	}
	public ScoreboardSetModel getModelScoreboards() {
		return modelScoreboards;
	}
}
