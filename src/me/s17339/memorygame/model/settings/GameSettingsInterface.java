package me.s17339.memorygame.model.settings;

import me.s17339.memorygame.model.GameModel;

public interface GameSettingsInterface extends GameModel<GameSettingsObserver> {
	double getScaledSoundsVolume();
	double getScaledSongsVolume();
	GameLanguageInterface getLanguage();
	
	void setScaledSoundsVolume(double scaledSoundsVolume);
	void setScaledSongsVolume(double scaledSongsVolume);
}
