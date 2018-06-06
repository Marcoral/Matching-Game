package me.s17339.memorygame.model.settings;

import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;

public interface GameSettingsObserver {
	void notifySoundsVolumeChanged(double oldValue);
	void notifySongsVolumeChanged(double oldValue);
	void notifyLanguageChanged(GameLanguageInfo oldValue);
}