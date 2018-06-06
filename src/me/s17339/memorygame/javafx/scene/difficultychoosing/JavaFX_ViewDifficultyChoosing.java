package me.s17339.memorygame.javafx.scene.difficultychoosing;

import java.util.function.BiConsumer;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.resinfo.GameImage;
import me.s17339.memorygame.resinfo.LanguageNode;

public class JavaFX_ViewDifficultyChoosing extends JavaFX_AbstractSceneLayer {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_ControllerDifficultyChoosing controller;
	JavaFX_ViewDifficultyChoosing(JavaFX_GameEnvironmentInfo environment, JavaFX_ControllerDifficultyChoosing controller) {
		this.environment = environment;
		this.controller = controller;
	}
	
	@Override
	protected StackPane createContent() {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_CENTER);

		addTitle(vbox);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BASELINE_CENTER);
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("ColorfulButtons"));
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("specified/DifficultyChoosingMenu"));
		vbox.getChildren().add(grid);
		addSection(grid, LanguageNode.DIFFICULTYCHOOSING_LEVEL_ALZHEIMER, LanguageNode.DIFFICULTYCHOOSING_TOOLTIP_LEVEL_ALZHEIMER, "button-first-row", 0,
				GameImage.LEVEL_CHOOSING_ALZHEIMER, JavaFX_ControllerDifficultyChoosing::handleBoardChooseLevelAlzheimer);
		addSection(grid, LanguageNode.DIFFICULTYCHOOSING_LEVEL_EASY, LanguageNode.DIFFICULTYCHOOSING_TOOLTIP_LEVEL_EASY, "button-second-row", 1,
				GameImage.LEVEL_CHOOSING_EASY, JavaFX_ControllerDifficultyChoosing::handleBoardChooseLevelEasy);
		addSection(grid, LanguageNode.DIFFICULTYCHOOSING_LEVEL_MODERATE, LanguageNode.DIFFICULTYCHOOSING_TOOLTIP_LEVEL_MODERATE, "button-third-row",
				2, GameImage.LEVEL_CHOOSING_MODERATE, JavaFX_ControllerDifficultyChoosing::handleBoardChooseLevelModerate);
		addSection(grid, LanguageNode.DIFFICULTYCHOOSING_LEVEL_CHALLENGING, LanguageNode.DIFFICULTYCHOOSING_TOOLTIP_LEVEL_CHALLENGING, "button-fourth-row", 3,
				GameImage.LEVEL_CHOOSING_CHALLENGING, JavaFX_ControllerDifficultyChoosing::handleBoardChooseLevelChallenging);
		
		StackPane content = new StackPane();
		content.setPrefHeight(550);
		content.getChildren().add(vbox);
		return content;
	}
	
	private void addTitle(VBox vbox) {
		Text title = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(LanguageNode.DIFFICULTYCHOOSING_TITLE), true, 80);
		Text subtitle = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(LanguageNode.DIFFICULTYCHOOSING_SUBTITLE), false, 16);

		vbox.getChildren().add(title);
		vbox.getChildren().add(subtitle);
		VBox.setMargin(subtitle, new Insets(0, 0, 30, 0));
	}
	
	private void addSection(GridPane grid, LanguageNode nameNode, LanguageNode tooltipNode, String styleName, int row, GameImage gameImage,
			BiConsumer<JavaFX_ControllerDifficultyChoosing, ActionEvent> onClickAction) {
		ObservableList<Node> children = grid.getChildren();
		Button button = new Button();
		button.textProperty().bind(environment.getModelSettings().getLanguageNode(nameNode));
		Tooltip tooltip = new Tooltip();
		tooltip.setPrefWidth(200);
		tooltip.setWrapText(true);
		tooltip.textProperty().bind(environment.getModelSettings().getLanguageNode(tooltipNode));
		button.setTooltip(tooltip);
		button.getStyleClass().add(styleName);
		button.setOnAction(event -> onClickAction.accept(controller, event));
		JavaFX_GameLayout.adjustButton(button, environment.getModelSettings().getSoundsVolumeProperty());
		children.add(button);
		
		GridPane.setConstraints(button, 0, row);
		ImageView image = new ImageView(JavaFX_GameResources.getImage(gameImage));
		image.setFitHeight(80);
		image.setPreserveRatio(true);
		
		Platform.runLater(() -> image.fitHeightProperty().bind(button.heightProperty()));
		children.add(image);
		GridPane.setConstraints(image, 1, row);
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public void changeController(JavaFX_ControllerDifficultyChoosing newController) {
		this.controller = newController;
	}
}
