package me.s17339.memorygame.javafx.scene.abstractscenelayer;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneElement;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.utils.JavaFX_GameLayout;
import me.s17339.memorygame.javafx.utils.JavaFX_GameResources;
import me.s17339.memorygame.resinfo.LanguageNode;

public abstract class JavaFX_AbstractSceneLayer implements JavaFX_SceneElement {
	protected abstract StackPane createContent();
	protected void adjustReturnButton(Button button) {}	//Hook
	
	/* ----------------------------------------------------	*/
	/*					ATTACHMENT LOGIC					*/
	/* ----------------------------------------------------	*/
	
	private Map<Node, FormerNodeState> formerStates;
	private EventHandler<? super KeyEvent> formerKeyEventHandler;
	
	private JavaFX_SceneElement parent;
	private JavaFX_ControllerSceneLayer controller;
	private StackPane newContent;
	
	public void attach(JavaFX_SceneElement rootSceneElement, JavaFX_GameEnvironmentInfo environment) {
		JavaFX_DefaultControllerSceneLayer controller = new JavaFX_DefaultControllerSceneLayer(this, environment);
		attach(rootSceneElement, environment, controller);
	}
	
	public void attach(JavaFX_SceneElement parent, JavaFX_GameEnvironmentInfo environment, JavaFX_ControllerSceneLayer controller) {
		this.parent = parent;
		this.controller = controller;

		ObservableList<Node> bottomNodes = parent.getContent().getChildren();
		formerStates = new WeakHashMap<>(bottomNodes.size());
		bottomNodes.forEach(node -> {
			FormerNodeState formerState = new FormerNodeState();
			formerStates.put(node, formerState);
			formerState.effect = node.getEffect();
			formerState.disablationState = node.isDisabled();
			BoxBlur bb = new BoxBlur(5, 5, 3);
			bb.setInput(node.getEffect());
			node.setEffect(bb);
			node.setDisable(true);
			node.setOpacity(0.2 * node.getOpacity());
		});
		newContent = createContent();
		newContent.setFocusTraversable(true);
		
		Scene scene = parent.getFrame().getScene();
		formerKeyEventHandler = scene.getOnKeyPressed();
		scene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ESCAPE)
				this.controller.handleEscapeButtonPress(event);
		});
		parent.notifyLayerAttached(this);
		
		
		attachButton(newContent, environment);
		bottomNodes.add(newContent);
		getRoot().getStage().sizeToScene();
	}

	private void attachButton(StackPane container, JavaFX_GameEnvironmentInfo environment) {
		Button button = new Button();
		button.textProperty().bind(environment.getModelSettings().getLanguageNode(LanguageNode.GENERAL_RETURNBUTTON));
		button.setOnAction(event -> controller.handleReturnButtonClick(event));
		button.getStylesheets().add(JavaFX_GameResources.getCssFilePath("BackableMenu"));
		JavaFX_GameLayout.adjustButton(button, environment.getModelSettings().getSoundsVolumeProperty());
		adjustReturnButton(button);
		container.getChildren().add(button);
		StackPane.setAlignment(button, Pos.BOTTOM_RIGHT);
	}
	
	public void detach() {
		ObservableList<Node> bottomNodes = parent.getContent().getChildren();
		bottomNodes.remove(newContent);
		Iterator<Entry<Node, FormerNodeState>> iterator = formerStates.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<Node, FormerNodeState> entry = iterator.next();
			Node node = entry.getKey();
			FormerNodeState state = entry.getValue();
			node.setEffect(state.effect);
			node.setDisable(state.disablationState);
			node.setOpacity(node.getOpacity() * 5);
			iterator.remove();
		}
		parent.notifyLayerDetached(this);
		getRoot().getStage().getScene().setOnKeyPressed(formerKeyEventHandler);
		getRoot().getStage().sizeToScene();
	}
	
	private static class FormerNodeState {
		private Effect effect;
		private boolean disablationState;
	}

	/* --------------------------------------------	*/
	/*					HANDLING					*/
	/* --------------------------------------------	*/

	@Override
	public StackPane getContent() {
		return parent.getContent();
	}
	
	@Override
	public JavaFX_SceneRoot getRoot() {
		return parent.getRoot();
	}
}
