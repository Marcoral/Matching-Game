package me.s17339.memorygame.javafx.scene.highscores;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneContentRoot;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLogic;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;
import me.s17339.memorygame.model.scoreboard.HighscoreEntry;
import me.s17339.memorygame.model.scoreboard.Scoreboard;
import me.s17339.memorygame.resinfo.CustomFont;
import me.s17339.memorygame.resinfo.LanguageNode;
import me.s17339.memorygame.utils.Parsers;

public class JavaFX_ViewHighscores extends JavaFX_SceneContentRoot {
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_ControllerHighscores controller;
	
	protected JavaFX_ViewHighscores(JavaFX_GameEnvironmentInfo environment, JavaFX_ControllerHighscores controller, JavaFX_SceneRoot sceneRoot,	DifficultyLevel initialDifficulty) {
		super(sceneRoot);
		this.environment = environment;
		this.controller = controller;
		createContent(initialDifficulty);
	}


	private TableView<HighscoreEntry> entryTable; 
	
	private void createContent(DifficultyLevel initialLevel) {
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setAlignment(Pos.BASELINE_CENTER);
		grid.getStylesheets().add(JavaFX_GameResources.getCssFilePath("BackableMenu"));
		grid.setId("content");
		StackPane content = new StackPane();
		content.getChildren().add(grid);
		
		addTitle(grid);
		addLevelSelectionSection(grid, initialLevel);
		addHighscoresView(grid);
		JavaFX_GameLogic.addReturnButton(content, environment).setOnAction(event -> controller.handleReturnButtonClick(event));
		refreshScores(initialLevel);
		
		setContent(content);
	}

	private void addTitle(GridPane grid) {
		Text title = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_TITLE), true, 80);
		grid.add(title, 0, 0, 2, 1);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setMargin(title, new Insets(0, 0, 20, 0));
	}
	
	private void addLevelSelectionSection(GridPane grid, DifficultyLevel initialLevel) {
		Text text = new Text();
		text.textProperty().bind(environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_CHOOSE_LEVEL));
		Font font = JavaFX_GameResources.getCustomFont(CustomFont.ALAMAKOTA, 20);
		text.setFont(font);
		GridPane.setMargin(text, new Insets(0, 0, 20, 0));
		grid.add(text, 0, 1);
		
		ComboBox<JavaFX_DifficultyLevelEntry> difficultyLevels = new ComboBox<>();
		difficultyLevels.setPromptText(environment.getModelSettings().getLanguageNode(initialLevel.getCorrespondingLanguageNode()).getValue());
		controller.adjustDifficultySelectionComboBox(difficultyLevels);
		grid.add(difficultyLevels, 1, 1);
	}
	
	private void addHighscoresView(GridPane grid) {
		entryTable = new TableView<>();
		entryTable.setEditable(false);
		entryTable.setSelectionModel(null);
		Label placeholder = new Label();
		placeholder.textProperty().bind(environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_NO_DATA));
		entryTable.setPlaceholder(placeholder);
        
        TableColumn<HighscoreEntry, HighscoreEntry> placeColumn = new TableColumn<>();
        placeColumn.textProperty().bind(environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_COLUMN_PLACE));
		placeColumn.setCellValueFactory(cell -> new ReadOnlyObjectWrapper<>(cell.getValue()));
		placeColumn.setCellFactory(column -> 
			new TableCell<HighscoreEntry, HighscoreEntry>() {
				@Override
				protected void updateItem(HighscoreEntry item, boolean empty) {
					super.updateItem(item, empty);
					String text = getTableRow() == null || item == null? "" : String.valueOf(getTableRow().getIndex() + 1); 
					setText(text);
				}
		});

        TableColumn<HighscoreEntry, String> nicknameColumn = new TableColumn<>();
		nicknameColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getNickname()));
		adjustColumn(nicknameColumn, LanguageNode.HIGHSCORES_COLUMN_NICKNAME, 200);

		TableColumn<HighscoreEntry, String> resultColumn = new TableColumn<>();
		resultColumn.setCellValueFactory(cell -> new ReadOnlyStringWrapper(Parsers.formatGameTime(cell.getValue().getTime())));
		adjustColumn(resultColumn, LanguageNode.HIGHSCORES_COLUMN_RESULT, 100);
		
		entryTable.getColumns().add(placeColumn);
		entryTable.getColumns().add(nicknameColumn);
		entryTable.getColumns().add(resultColumn);
		grid.add(entryTable, 0, 2, 2, 5);
	}
	
	private void adjustColumn(TableColumn<?, ?> column, LanguageNode text, int width) {
		column.textProperty().bind(environment.getModelSettings().getLanguageNode(text));
		column.setPrefWidth(width);
	}
	
	void refreshScores(DifficultyLevel level) {
		Scoreboard scoreboard = environment.getModelScoreboards().getScoreboard(level);
		entryTable.getItems().clear();
		entryTable.getItems().addAll(scoreboard.getHighscores());
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public void changeController(JavaFX_ControllerHighscores controller) {
		this.controller = controller;
	}
}
