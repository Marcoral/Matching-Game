package me.s17339.memorygame.javafx.scene.roots;

import javafx.scene.Cursor;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;

public class JavaFX_SceneRootDraggable extends JavaFX_SceneRoot {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private double xAxisDragOffset, yAxisDragOffset;
	private AnchorPane frame;

	public JavaFX_SceneRootDraggable(Stage stage) {
		super(stage);
	}
	
	@Override
	protected void adjustContent(StackPane content) {
		content.getStyleClass().add("layout-of-inside");
		AnchorPane.setTopAnchor(content, 15.0);
		AnchorPane.setLeftAnchor(content, 15.0);
		AnchorPane.setRightAnchor(content, 15.0);
		AnchorPane.setBottomAnchor(content, 15.0);
			
		content.setOnMousePressed(event -> event.consume());
		content.setOnMouseDragged(event -> event.consume());
		content.setOnMouseReleased(event -> event.consume());
		content.setCursor(Cursor.DEFAULT);
		content.setOnDragDetected(null);
	}

	@Override
	protected Pane createFrame() {
		this.frame = new AnchorPane();
		Stage stage = getStage();
		
		frame.getStylesheets().add(JavaFX_GameResources.getCssFilePath("General"));
		frame.getStyleClass().add("layout-of-border");

		frame.setOnMousePressed(event -> {
		    xAxisDragOffset = stage.getX() - event.getScreenX();
		    yAxisDragOffset = stage.getY() - event.getScreenY();
		    frame.setCursor(Cursor.CLOSED_HAND);
		});
		frame.setOnMouseDragged(event -> {
		    stage.setX(event.getScreenX() + xAxisDragOffset);
		    stage.setY(event.getScreenY() + yAxisDragOffset);
		});
		frame.setOnMouseReleased(event -> frame.setCursor(Cursor.OPEN_HAND));
		
		frame.setCursor(Cursor.OPEN_HAND);
		return frame;
	}
}
