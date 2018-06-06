package me.s17339.memorygame.model.gameboard;

public interface GameboardSettingsObserver {
	void notifyUnrevealingAllowedPropertyChanged(boolean oldValue);
}
