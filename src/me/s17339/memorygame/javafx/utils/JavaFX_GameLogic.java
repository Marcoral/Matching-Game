package me.s17339.memorygame.javafx.utils;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.resinfo.GameSound;
import me.s17339.memorygame.resinfo.LanguageNode;

public class JavaFX_GameLogic {
	public static void performDefaultButtonClickAction(DoubleProperty soundVolumeConstraint) {
		JavaFX_GameAudio.playSound(GameSound.INGAME_REVEAL, soundVolumeConstraint);
	}
	
	public static Button addReturnButton(StackPane content, JavaFX_GameEnvironmentInfo environment) {
		Button button = new Button();
		button.textProperty().bind(environment.getModelSettings().getLanguageNode(LanguageNode.GENERAL_RETURNBUTTON));
		button.getStylesheets().add(JavaFX_GameResources.getCssFilePath("BackableMenu"));
		JavaFX_GameLayout.adjustButton(button, environment.getModelSettings().getSoundsVolumeProperty());
		content.getChildren().add(button);
		StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);
		return button;
	}
}
