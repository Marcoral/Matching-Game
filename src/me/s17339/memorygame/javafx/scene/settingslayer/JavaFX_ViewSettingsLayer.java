package me.s17339.memorygame.javafx.scene.settingslayer;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.model.settings.GameLanguageModel;
import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;
import me.s17339.memorygame.resinfo.LanguageNode;

public class JavaFX_ViewSettingsLayer extends JavaFX_AbstractSceneLayer {
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_ControllerSettingsLayer controller;
	JavaFX_ViewSettingsLayer(JavaFX_GameEnvironmentInfo environment, JavaFX_ControllerSettingsLayer controller) {
		this.environment = environment;
		this.controller = controller;
	}
	
	private Slider soundsVolumeSlider;
	private Slider songsVolumeSlider;
	private CheckBox unrevealingPropertyCheckBox;
	private CheckBox hideOnCollectCheckBox;
	private ComboBox<GameLanguageInfo> languageComboBox;
	
	@Override
	protected StackPane createContent() {
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setAlignment(Pos.BASELINE_CENTER);

		addTitle(grid);
		addSoundsVolumeSection(grid);
		addSongsVolumeSection(grid);
		addUnrevealingPropertyCheckBox(grid);
		addHideOnCollectPropertyCheckBox(grid);
		addLanguageChoiceBox(grid);
		
		updateControllsDependingOnController();
		StackPane content = new StackPane();
		content.getChildren().add(grid);
		return content;
	}
	
	private void addTitle(GridPane grid) {
		Text text = JavaFX_GameLayout.createTitleText(environment.getModelSettings().getLanguageNode(LanguageNode.SETTINGS_TITLE), true, 80);
		grid.add(text, 0, 0, 2, 1);
		GridPane.setHalignment(text, HPos.CENTER);
	}
	
	private void addSoundsVolumeSection(GridPane grid) {
		soundsVolumeSlider = new Slider(0, 1, 1);
				
		Text text = JavaFX_GameLayout.createSubText(environment.getModelSettings().getLanguageNode(LanguageNode.SETTINGS_SOUNDSVOLUME));
		grid.add(text, 0, 1);
		grid.add(soundsVolumeSlider, 1, 1);
	}
	
	private void addSongsVolumeSection(GridPane grid) {
		songsVolumeSlider = new Slider(0, 1, 1);
				
		Text text = JavaFX_GameLayout.createSubText(environment.getModelSettings().getLanguageNode(LanguageNode.SETTINGS_SONGSVOLUME));
		grid.add(text, 0, 2);
		grid.add(songsVolumeSlider, 1, 2);
		GridPane.setMargin(text, new Insets(0, 0, 20, 0));
		GridPane.setMargin(songsVolumeSlider, new Insets(0, 0, 20, 0));
	}
	
	private void addUnrevealingPropertyCheckBox(GridPane grid) {
		unrevealingPropertyCheckBox = new CheckBox();
		
		Text text = JavaFX_GameLayout.createSubText(environment.getModelSettings().getLanguageNode(LanguageNode.SETTINGS_UNREVEALINGALLOWED));
		grid.add(text, 0, 3);
		grid.add(unrevealingPropertyCheckBox, 1, 3);
	}

	private void addHideOnCollectPropertyCheckBox(GridPane grid) {
		hideOnCollectCheckBox = new CheckBox();
		
		Text text = JavaFX_GameLayout.createSubText(environment.getModelSettings().getLanguageNode(LanguageNode.SETTINGS_HIDEONCOLLECT));
		grid.add(text, 0, 4);
		grid.add(hideOnCollectCheckBox, 1, 4);
		GridPane.setMargin(text, new Insets(0, 0, 20, 0));
		GridPane.setMargin(hideOnCollectCheckBox, new Insets(0, 0, 20, 0));
	}
	
	private void addLanguageChoiceBox(GridPane grid) {
		languageComboBox = new ComboBox<>();
		languageComboBox.getItems().addAll(GameLanguageModel.getInstalledLanguages());
		languageComboBox.promptTextProperty().bind(environment.getModelSettings().getLanguageNode(LanguageNode.GENERAL_LANGUAGENAME));
		
		grid.add(JavaFX_GameLayout.createSubText(environment.getModelSettings().getLanguageNode(LanguageNode.SETTINGS_LANGUAGE)), 0, 5);
		grid.add(languageComboBox, 1, 5);
	}
	
	private void updateControllsDependingOnController() {
		controller.adjustSoundsSlider(soundsVolumeSlider);
		controller.adjustSongsSlider(songsVolumeSlider);
		controller.adjustHideOnClickVisibleCheckBox(unrevealingPropertyCheckBox);
		controller.adjustHideOnCollectCheckBox(hideOnCollectCheckBox);
		controller.adjustLanguageComboBox(languageComboBox);
	}
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public void changeController(JavaFX_ControllerSettingsLayer newController) {
		this.controller = newController;
		updateControllsDependingOnController();
	}
}
