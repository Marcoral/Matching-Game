package me.s17339.memorygame.javafx.scene;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;

public interface JavaFX_SceneElement {
	StackPane getContent();
	JavaFX_SceneRoot getRoot();
	default Parent getFrame() {
		return getRoot().getContent();
	}
	default void notifyLayerAttached(JavaFX_AbstractSceneLayer layer) {}; //Hook
	default void notifyLayerDetached(JavaFX_AbstractSceneLayer layer) {}; //Hook
}