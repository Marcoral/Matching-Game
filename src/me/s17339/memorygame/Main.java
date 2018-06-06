package me.s17339.memorygame;

import java.io.IOException;

import me.s17339.memorygame.javafx.JavaFX_Main;
import me.s17339.memorygame.model.GameObjectSerializer;
import me.s17339.memorygame.model.gameboard.GameboardSettingsModel;
import me.s17339.memorygame.model.scoreboard.ScoreboardSetModel;
import me.s17339.memorygame.model.settings.GameLanguageModel;
import me.s17339.memorygame.model.settings.GameSettingsModel;
import me.s17339.memorygame.resinfo.ResourcePath;

public class Main {	
	public static void main(String[] args) throws IOException {
		GameLanguageModel.reloadLanguageFiles();
		
		//Creation of game models
		GameboardSettingsModel modelGameboard = GameObjectSerializer.load(ResourcePath.SERIALIZATION_GAMEBOARDSETTINGS_FILE.get(), GameboardSettingsModel::createDefault);
		GameSettingsModel modelSettings = GameObjectSerializer.load(ResourcePath.SERIALIZATION_SETTINGS_FILE.get(), GameSettingsModel::createDefault);
		ScoreboardSetModel scoreboards = GameObjectSerializer.load(ResourcePath.SERIALIZATION_GAMEBOARDS_FILE.get(), ScoreboardSetModel::createDefault);
		
		//Initialize JavaFX version of GUI
		JavaFX_Main.initialize(args, modelGameboard, modelSettings, scoreboards);
	}
}
