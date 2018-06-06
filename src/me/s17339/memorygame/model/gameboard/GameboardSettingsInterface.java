package me.s17339.memorygame.model.gameboard;

import me.s17339.memorygame.model.GameModel;

public interface GameboardSettingsInterface extends GameModel<GameboardSettingsObserver> {
	boolean isUnrevealingAllowed();
	boolean isHideOnCollect();
	void setUnrevealingAllowed(boolean value);
	void setHideOnCollect(boolean value);
}
