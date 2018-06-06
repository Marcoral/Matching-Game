package me.s17339.memorygame.javafx.scene.mainmenu;

import java.util.function.BiConsumer;

import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneContentRoot;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.resinfo.LanguageNode;

public class JavaFX_ViewMainMenu extends JavaFX_SceneContentRoot {
	private JavaFX_ControllerMainMenu controller;
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_GameEnvironmentInfo environment;
	JavaFX_ViewMainMenu(JavaFX_GameEnvironmentInfo environment, JavaFX_ControllerMainMenu controller, JavaFX_SceneRoot sceneRoot) {
		super(sceneRoot);
		this.environment = environment;
		this.controller = controller;
		createContent();
	}

	private void createContent() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_CENTER);
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("ColorfulButtons"));
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("specified/MainMenu"));
		
		addTitle(grid, 120, new Insets(0, 0, -30, 0), 0, 0, 2, 1, LanguageNode.MENU_TITLE);
		addTitle(grid, 40, new Insets(0, 0, 30, 0), 0, 1, 2, 1, LanguageNode.MENU_SUBTITLE);
		
		addButton(grid, "button-first-row", 0, 2, 1, 1, LanguageNode.MENU_NEWGAME, JavaFX_ControllerMainMenu::handleNewGameButtonClick);
		addButton(grid, "button-first-row", 1, 2, 1, 1, LanguageNode.MENU_CREDITS, JavaFX_ControllerMainMenu::handleCreditsButtonClick);
		addButton(grid, "button-second-row", 0, 3, 1, 1, LanguageNode.MENU_HIGHSCORES, JavaFX_ControllerMainMenu::handleHighscoresButtonClick);
		addButton(grid, "button-second-row", 1, 3, 1, 1, LanguageNode.MENU_SETTINGS, JavaFX_ControllerMainMenu::handleSettingsButtonClick);
		addButton(grid, "button-third-row", 0, 4, 2, 1, LanguageNode.MENU_QUIT, JavaFX_ControllerMainMenu::handleQuitButtonClick);
		
		StackPane content = new StackPane();
		content.getChildren().add(grid);
		setContent(content);
	}
	
	private void addTitle(GridPane grid, int fontSize, Insets insets, int columnIndex, int rowIndex, int columnspan, int rowspan, LanguageNode languageNode) {
		Text text = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(languageNode), true, fontSize);
		grid.add(text, columnIndex, rowIndex, columnspan, rowspan);
		GridPane.setHalignment(text, HPos.CENTER);
		GridPane.setMargin(text, insets);
	}
	
	private void addButton(GridPane grid, String styleClassName, int columnIndex, int rowIndex, int columnspan, int rowspan,
			LanguageNode languageNode, BiConsumer<JavaFX_ControllerMainMenu, ActionEvent> onClickAction) {
		
		Button button = new Button();
		button.textProperty().bind(environment.getModelSettings().getLanguageNode(languageNode));
		button.setOnAction(event -> onClickAction.accept(controller, event));
		button.getStyleClass().add(styleClassName);
		grid.add(button, columnIndex, rowIndex, columnspan, rowspan);
		JavaFX_GameLayout.adjustButton(button, environment.getModelSettings().getSoundsVolumeProperty());
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public void changeController(JavaFX_ControllerMainMenu controller) {
		this.controller = controller;
	}
	
	public void playMusic() {
		controller.handleSongPlayRequest();
	}
}
