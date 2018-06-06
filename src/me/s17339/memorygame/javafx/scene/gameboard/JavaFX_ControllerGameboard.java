package me.s17339.memorygame.javafx.scene.gameboard;

import me.s17339.memorygame.javafx.scene.JavaFX_Escapeable;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;

public interface JavaFX_ControllerGameboard extends JavaFX_Escapeable {
	JavaFX_ViewGameboard createView();

	void handleImageClick(int x, int y);

	void playRevealingSound();
	void playWrongMatchSound();
	void playCorrectMatchSound();
	
	void handleGameEnd(long finishTime);

	void notifyLayerAttached(JavaFX_AbstractSceneLayer layer);
	void notifyLayerDetached(JavaFX_AbstractSceneLayer layer);
}
