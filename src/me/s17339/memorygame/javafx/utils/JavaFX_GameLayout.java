package me.s17339.memorygame.javafx.utils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.s17339.memorygame.resinfo.CustomFont;
import me.s17339.memorygame.resinfo.GameImage;
import me.s17339.memorygame.resinfo.GameSound;

public class JavaFX_GameLayout {
	public static void adjustStage(Stage stage) {
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(JavaFX_GameResources.getImage(GameImage.ICON));
		stage.initStyle(StageStyle.TRANSPARENT);
	}
	
	public static void adjustButton(Button button, DoubleProperty volumeProperty, String... style) {
		button.setOnMouseEntered(event -> JavaFX_GameAudio.playSound(GameSound.GENERIC_HOVER, volumeProperty));
		Font font = JavaFX_GameResources.getCustomFont(CustomFont.ALAMAKOTA, 1);
		StringBuilder builder = new StringBuilder("-fx-font-family: '" + font.getName() + "'");
		for(String styleNode : style) {
			builder.append(";");
			builder.append(styleNode);
		}
		button.setStyle(builder.toString());
	}
	
	public static Text createTitleText(ObservableValue<? extends String> textProperty, boolean isTitle, int fontSize) {
		Text text = new Text();
		text.textProperty().bind(textProperty);
		Font font = JavaFX_GameResources.getCustomFont(isTitle? CustomFont.SUNRISE : CustomFont.ALAMAKOTA, fontSize);
		
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(3.0f);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		text.setEffect(shadow);
		text.setFont(font);
		return text;
	}
	
	public static Text createSubText(ObservableValue<? extends String> textProperty) {		
		Text text = new Text();
		text.textProperty().bind(textProperty);
		Font font = JavaFX_GameResources.getCustomFont(CustomFont.ALAMAKOTA, 20);
		text.setFont(font);
		return text;
	}
}
