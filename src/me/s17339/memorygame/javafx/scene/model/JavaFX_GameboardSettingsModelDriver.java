package me.s17339.memorygame.javafx.scene.model;

import java.util.function.Consumer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import me.s17339.memorygame.model.gameboard.GameboardSettingsInterface;
import me.s17339.memorygame.model.gameboard.GameboardSettingsObserver;

public class JavaFX_GameboardSettingsModelDriver implements GameboardSettingsObserver {
	private SimpleBooleanProperty isUnrevealingAllowed;
	private SimpleBooleanProperty isHideOnCollect;
	
	private GameboardSettingsInterface nativeModel;
	public JavaFX_GameboardSettingsModelDriver(GameboardSettingsInterface nativeModel) {
		this.nativeModel = nativeModel;
		this.isUnrevealingAllowed = setupProperty(nativeModel.isUnrevealingAllowed(), nativeModel::setUnrevealingAllowed);
		this.isHideOnCollect = setupProperty(nativeModel.isHideOnCollect(), nativeModel::setHideOnCollect);
		nativeModel.registerObserver(this);
	}
	
	private SimpleBooleanProperty setupProperty(boolean initialValue, Consumer<Boolean> listenerAction) {
		SimpleBooleanProperty property = new SimpleBooleanProperty(initialValue);
		property.addListener((observable, oldValue, newValue) -> listenerAction.accept(newValue));
		return property;
	}
	
	/* ----------------------------------------	*/
	/*					LOGIC					*/
	/* ----------------------------------------	*/
	
	public BooleanProperty getUnrevealingAllowanceProperty() {
		return isUnrevealingAllowed;
	}
	
	public BooleanProperty getHideOnCollectProperty() {
		return isHideOnCollect;
	}
	
	public GameboardSettingsInterface getNativeModel() {
		return nativeModel;
	}

	/* ------------------------------------------------	*/
	/*					MODEL OBSERVING					*/
	/* ------------------------------------------------	*/	
	
	@Override
	public void notifyUnrevealingAllowedPropertyChanged(boolean oldValue) {
		isUnrevealingAllowed.setValue(nativeModel.isUnrevealingAllowed());
	}

}
