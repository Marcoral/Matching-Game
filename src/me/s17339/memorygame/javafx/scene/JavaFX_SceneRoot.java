package me.s17339.memorygame.javafx.scene;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class JavaFX_SceneRoot {
	private final Insets DEFAULT_PADDING = new Insets(30);
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private StackPane content;
	private Pane frame;
	private Stage stage;
	public JavaFX_SceneRoot(Stage stage) {
		this.content = new StackPane();
		this.stage = stage;
		adjustContent(content);
		this.frame = createFrame();
		frame.getChildren().add(content);
		setDefaultPadding();
	}
	
	protected abstract Pane createFrame();
	protected void adjustContent(StackPane frame) {}	//Hook
	
	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/
	
	public final Stage getStage() {
		return stage;
	}
	
	public final Pane getContent() {
		return frame;
	}
	
	public final void setPadding(Insets insets) {
		content.setPadding(insets);
	}
	
	public final void setDefaultPadding() {
		setPadding(DEFAULT_PADDING);
	}
	
	public final void changeContent(StackPane content) {
		ObservableList<Node> children = this.content.getChildren();
		children.clear();
		children.add(content);
		stage.sizeToScene();
		double width = Screen.getPrimary().getVisualBounds().getWidth();
		double height = Screen.getPrimary().getVisualBounds().getHeight();
		stage.setX((width - stage.getWidth())/2);
		stage.setY((height - stage.getHeight())/2);
	}
}
