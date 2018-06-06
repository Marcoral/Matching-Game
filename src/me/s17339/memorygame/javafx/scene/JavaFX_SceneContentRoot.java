package me.s17339.memorygame.javafx.scene;

import javafx.scene.layout.StackPane;

public abstract class JavaFX_SceneContentRoot implements JavaFX_SceneElement {
	private JavaFX_SceneRoot sceneRoot;
	private StackPane content;
	protected JavaFX_SceneContentRoot(JavaFX_SceneRoot sceneRoot) {
		this.sceneRoot = sceneRoot;
	}

	protected void setContent(StackPane content) {
		this.content = content;
		sceneRoot.changeContent(content);
	}

	@Override
	public final JavaFX_SceneRoot getRoot() {
		return sceneRoot;
	}

	@Override
	public final StackPane getContent() {
		return content;
	}
}
