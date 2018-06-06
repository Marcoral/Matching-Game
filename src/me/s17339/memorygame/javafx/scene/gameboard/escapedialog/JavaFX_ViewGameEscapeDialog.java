package me.s17339.memorygame.javafx.scene.gameboard.escapedialog;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.resinfo.LanguageNode;

public class JavaFX_ViewGameEscapeDialog extends JavaFX_AbstractSceneLayer {
	private JavaFX_ControllerGameEscapeDialog controller;
	private JavaFX_GameEnvironmentInfo environment;

	JavaFX_ViewGameEscapeDialog(JavaFX_DefaultControllerGameEscapeDialog controller, JavaFX_GameEnvironmentInfo environment) {
		this.controller = controller;
		this.environment = environment;
	}

	@Override
	protected StackPane createContent() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.BASELINE_CENTER);
		vbox.getStylesheets().add(JavaFX_GameResources.getCssFilePath("ColorfulButtons"));
		addTitle(vbox);
		addButton(vbox, "button-first-row", LanguageNode.MENU_SETTINGS, JavaFX_ControllerGameEscapeDialog::handleSettingsButtonClick);
		addButton(vbox, "button-second-row", LanguageNode.INGAME_RAGEQUIT, JavaFX_ControllerGameEscapeDialog::handleRagequitButtonClick);
		StackPane content = new StackPane();
		content.getChildren().add(vbox);
		return content;
	}
	
	private void addTitle(VBox vbox) {
		Text text = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(LanguageNode.INGAME_PAUSED), true, 80);
		vbox.getChildren().add(text);
	}
	
	private void addButton(VBox vbox, String styleClassName, LanguageNode languageNode, BiConsumer<JavaFX_ControllerGameEscapeDialog, ActionEvent> onClickAction) {
		Button button = new Button();
		button.textProperty().bind(environment.getModelSettings().getLanguageNode(languageNode));
		button.setOnAction(event -> onClickAction.accept(controller, event));
		button.getStyleClass().add(styleClassName);
		vbox.getChildren().add(button);
		JavaFX_GameLayout.adjustButton(button, environment.getModelSettings().getSoundsVolumeProperty());
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public void changeController(JavaFX_ControllerGameEscapeDialog newController) {
		this.controller = newController;
	}
}
