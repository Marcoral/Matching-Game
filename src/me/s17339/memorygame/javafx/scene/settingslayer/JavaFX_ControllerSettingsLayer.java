package me.s17339.memorygame.javafx.scene.settingslayer;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;

public interface JavaFX_ControllerSettingsLayer {
	JavaFX_ViewSettingsLayer createView();
	
	void adjustSoundsSlider(Slider slider);
	void adjustSongsSlider(Slider slider);
	void adjustHideOnClickVisibleCheckBox(CheckBox hideOnClickVisibleCheckBox);
	void adjustHideOnCollectCheckBox(CheckBox hideOnCollectCheckBox);
	void adjustLanguageComboBox(ComboBox<GameLanguageInfo> comboBox);
}
