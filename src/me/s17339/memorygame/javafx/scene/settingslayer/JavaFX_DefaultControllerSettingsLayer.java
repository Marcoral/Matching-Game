package me.s17339.memorygame.javafx.scene.settingslayer;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.utils.JavaFX_GameAudio;
import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;
import me.s17339.memorygame.resinfo.GameSound;

public class JavaFX_DefaultControllerSettingsLayer implements JavaFX_ControllerSettingsLayer {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private JavaFX_GameEnvironmentInfo environment;
	public JavaFX_DefaultControllerSettingsLayer(JavaFX_GameEnvironmentInfo environment) {
		this.environment = environment;
	}

	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/
	
	@Override
	public JavaFX_ViewSettingsLayer createView() {
		return new JavaFX_ViewSettingsLayer(environment, this);
	}
	
	@Override
	public void adjustSoundsSlider(Slider slider) {
		slider.valueProperty().bindBidirectional(environment.getModelSettings().getSoundsVolumeProperty());
		slider.setOnMouseReleased(event -> {
			JavaFX_GameAudio.playSound(GameSound.INGAME_PAIR_CORRECT, environment.getModelSettings().getSoundsVolumeProperty());
			environment.getModelSettings().getNativeModel().save();
		});
	}

	@Override
	public void adjustSongsSlider(Slider slider) {
		slider.valueProperty().bindBidirectional(environment.getModelSettings().getSongsVolumeProperty());
		slider.setOnMouseReleased(event -> environment.getModelSettings().getNativeModel().save());
	}

	@Override
	public void adjustHideOnClickVisibleCheckBox(CheckBox hideOnClickVisibleCheckBox) {
		hideOnClickVisibleCheckBox.selectedProperty().bindBidirectional(environment.getModelGameboardSettings().getUnrevealingAllowanceProperty());
		hideOnClickVisibleCheckBox.setOnAction(event -> environment.getModelGameboardSettings().getNativeModel().save());
	}
	
	@Override
	public void adjustHideOnCollectCheckBox(CheckBox hideOnCollectCheckBox) {
		hideOnCollectCheckBox.selectedProperty().bindBidirectional(environment.getModelGameboardSettings().getHideOnCollectProperty());
		hideOnCollectCheckBox.setOnAction(event -> environment.getModelGameboardSettings().getNativeModel().save());
	}
	
	@Override
	public void adjustLanguageComboBox(ComboBox<GameLanguageInfo> comboBox) {
		comboBox.getSelectionModel().selectedItemProperty().addListener(
				(value, oldValue, newValue) -> {
					environment.getModelSettings().getNativeModel().getLanguage().switchLanguage(newValue);
					environment.getModelSettings().getNativeModel().save();
				});
	}
}
