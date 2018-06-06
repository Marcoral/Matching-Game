package me.s17339.memorygame.javafx.scene.model;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import me.s17339.memorygame.model.settings.GameLanguageInterface;
import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;
import me.s17339.memorygame.resinfo.LanguageNode;
import me.s17339.memorygame.model.settings.GameSettingsInterface;
import me.s17339.memorygame.model.settings.GameSettingsObserver;

public class JavaFX_GameSettingsModelDriver implements GameSettingsObserver {
	private SimpleDoubleProperty soundsVolume;
	private SimpleDoubleProperty songsVolume;
	private Map<LanguageNode, ReadOnlyStringWrapper> currentLanguageNodes;
	
	private GameSettingsInterface nativeModel;
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	public JavaFX_GameSettingsModelDriver(GameSettingsInterface nativeModel) {
		this.nativeModel = nativeModel;
		this.soundsVolume = setupProperty(nativeModel.getScaledSoundsVolume(), nativeModel::setScaledSoundsVolume);
		this.songsVolume = setupProperty(nativeModel.getScaledSongsVolume(), nativeModel::setScaledSongsVolume);
		this.currentLanguageNodes = new EnumMap<>(LanguageNode.class);
		nativeModel.registerObserver(this);
		initializeLanguage();
		notifyLanguageChanged(null);
	}
	
	private SimpleDoubleProperty setupProperty(double initialValue, Consumer<Double> listenerAction) {
		SimpleDoubleProperty property = new SimpleDoubleProperty(initialValue);
		property.addListener((observable, oldValue, newValue) -> listenerAction.accept((Double) newValue));
		return property;
	}
	
	private void initializeLanguage() {
		for(LanguageNode node : LanguageNode.values())
			currentLanguageNodes.put(node, new ReadOnlyStringWrapper());
	}
	
	/* ----------------------------------------	*/
	/*					LOGIC					*/
	/* ----------------------------------------	*/
	
	public SimpleDoubleProperty getSoundsVolumeProperty() {
		return soundsVolume;
	}
	
	public SimpleDoubleProperty getSongsVolumeProperty() {
		return songsVolume;
	}
	
	public ReadOnlyStringProperty getLanguageNode(LanguageNode node) {
		return currentLanguageNodes.get(node).getReadOnlyProperty();
	}
	
	public GameSettingsInterface getNativeModel() {
		return nativeModel;
	}
	
	/* ------------------------------------------------	*/
	/*					MODEL OBSERVING					*/
	/* ------------------------------------------------	*/	
	
	@Override
	public void notifyLanguageChanged(GameLanguageInfo oldValue) {
		GameLanguageInterface languageModel = nativeModel.getLanguage();
		Iterator<Entry<LanguageNode, ReadOnlyStringWrapper>> iterator = currentLanguageNodes.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<LanguageNode, ReadOnlyStringWrapper> entry = iterator.next();
			entry.getValue().set(languageModel.get(entry.getKey()));
		}
	}

	@Override
	public void notifySoundsVolumeChanged(double oldValue) {
		soundsVolume.set(nativeModel.getScaledSoundsVolume());
	}

	@Override
	public void notifySongsVolumeChanged(double oldValue) {
		songsVolume.set(nativeModel.getScaledSongsVolume());
	}
}
