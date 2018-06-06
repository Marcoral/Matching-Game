package me.s17339.memorygame.javafx.scene.credits;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneContentRoot;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLogic;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.resinfo.LanguageNode;

public class JavaFX_ViewCredits extends JavaFX_SceneContentRoot {
	private JavaFX_ControllerCredits controller;
	private JavaFX_GameEnvironmentInfo environment;
	protected JavaFX_ViewCredits(JavaFX_ControllerCredits controller, JavaFX_GameEnvironmentInfo environment, JavaFX_SceneRoot sceneRoot) {
		super(sceneRoot);
		this.controller = controller;
		this.environment = environment;
		createContent();
	}
	
	private void createContent() {
		GridPane grid = new GridPane();
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("specified/Credits"));
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("BackableMenu"));
		grid.setId("content");
		addTitle(grid);
		
		createDevelopersSection(grid);
		createSoundsSection(grid);
		createSongsSection(grid);
		createImagesSection(grid);
		
		StackPane content = new StackPane();
		content.getChildren().add(grid);
		JavaFX_GameLogic.addReturnButton(content, environment).setOnAction(event -> controller.handleReturnButtonClick(event));
		setContent(content);
	}
	
	private void addTitle(GridPane grid) {
		Text text = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(LanguageNode.CREDITS_TITLE), true, 80);
		grid.add(text, 0, 0, 3, 1);
		GridPane.setHalignment(text, HPos.CENTER);
	}
	
	private void createDevelopersSection(GridPane grid) {
		addSectionText(environment.getModelSettings().getLanguageNode(LanguageNode.CREDITS_DEVELOPERS), grid, 1);
		addSubText("- Marcin Polak", grid, 2);
		grid.addRow(3, new Text(""));
	}
	
	private void createSoundsSection(GridPane grid) {
		addSectionText(environment.getModelSettings().getLanguageNode(LanguageNode.CREDITS_SOUNDS), grid, 4);
		addSubText("- freesound.org", grid, 5);
		grid.addRow(6, new Text(""));
	}
	
	private void createSongsSection(GridPane grid) {
		addSectionText(environment.getModelSettings().getLanguageNode(LanguageNode.CREDITS_SONGS), grid, 7);
		addSubText("- bensound.com", grid, 8);
		grid.addRow(9, new Text(""));
	}
	
	private void createImagesSection(GridPane grid) {
		addSectionText(environment.getModelSettings().getLanguageNode(LanguageNode.CREDITS_IMAGES), grid, 10);
		addSubText("- cntraveler.com", grid, 11);
	}

	private void addSectionText(ObservableValue<? extends String> textProperty, GridPane grid, int row) {
		Text textObj = JavaFX_GameLayout.createSubText(textProperty);
		textObj.setUnderline(true);
		grid.add(textObj, 0, row);
		GridPane.setHalignment(textObj, HPos.LEFT);
	}
	
	private void addSubText(String text, GridPane grid, int row) {
		Text textObj = JavaFX_GameLayout.createSubText(new SimpleStringProperty(text));
		StackPane wrapper = new StackPane();
		wrapper.getChildren().add(textObj);
		wrapper.setId("text-wrapper");
		grid.add(wrapper, 0, row);
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public void changeController(JavaFX_ControllerCredits newController) {
		this.controller = newController;
	}
}
