package me.s17339.memorygame.model.settings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

import me.s17339.memorygame.model.AbstractGameModel;
import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;
import me.s17339.memorygame.resinfo.ResourcePath;

public class GameSettingsModel extends AbstractGameModel<GameSettingsObserver> implements GameSettingsInterface {
	private static final long serialVersionUID = -4818831380589876834L;
	
	/* ----------------------------------------	*/
	/*					BUILDER					*/
	/* ----------------------------------------	*/
	
	public static class Builder {
		private static final double DEFAULT_SCALED_SOUNDS_VOLUME = 1.0;
		private static final double DEFAULT_SCALED_SONGS_VOLUME = 1.0;
		
		private String path = ResourcePath.SERIALIZATION_SETTINGS_FILE.get();
		private double scaledSoundsVolume = DEFAULT_SCALED_SOUNDS_VOLUME;
		private double scaledSongsVolume = DEFAULT_SCALED_SONGS_VOLUME;
		private GameLanguageInfo language;
		
		public Builder filePath(String value) {
			path = value;
			return this;
		}
		
		public Builder scaledSoundsVolume(double value) {
			scaledSoundsVolume = value;
			return this;
		}
		
		public Builder scaledSongsVolume(double value) {
			scaledSongsVolume = value;
			return this;
		}
		
		public Builder language(GameLanguageInfo value) {
			language = value;
			return this;
		}
		
		public GameSettingsModel build() {
			return new GameSettingsModel(path, scaledSoundsVolume, scaledSongsVolume, language);
		}
	}
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	public static GameSettingsModel createDefault() {
		return new GameSettingsModel.Builder().build();
	}
	
	private double scaledSoundsVolume;
	private double scaledSongsVolume;
	private GameLanguageModel language;

	public GameSettingsModel(String path, double scaledSoundsVolume, double scaledSongsVolume, GameLanguageInfo language) {
		super(path);
		this.scaledSoundsVolume = scaledSoundsVolume;
		this.scaledSongsVolume = scaledSongsVolume;
		if(language == null)
			this.language = new GameLanguageModel(observers);
		else
			this.language = new GameLanguageModel(language, observers);
	}

	/* ----------------------------------------	*/
	/*					LOGIC					*/
	/* ----------------------------------------	*/

	@Override
	public double getScaledSoundsVolume() {
		return scaledSoundsVolume;
	}

	@Override
	public void setScaledSoundsVolume(double scaledSoundsVolume) {
		double oldValue = this.scaledSoundsVolume;
		if(oldValue == scaledSoundsVolume)
			return;
		this.scaledSoundsVolume = scaledSoundsVolume;
		observers.forEach(observer -> observer.notifySoundsVolumeChanged(oldValue));
	}

	@Override
	public double getScaledSongsVolume() {
		return scaledSongsVolume;
	}

	@Override
	public void setScaledSongsVolume(double scaledSongsVolume) {
		double oldValue = this.scaledSongsVolume;
		if(oldValue == scaledSoundsVolume)
			return;
		this.scaledSongsVolume = scaledSongsVolume;
		observers.forEach(observer -> observer.notifySongsVolumeChanged(oldValue));
	}
	
	@Override
	public GameLanguageInterface getLanguage() {
		return language;
	}
	
	/* --------------------------------------------------------	*/
	/*					CONTROLLERS HANDLING					*/
	/* --------------------------------------------------------	*/
	
	//Deserialization fix
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        observers = new LinkedList<>();
        language.injectObservers(observers);
    }
}
