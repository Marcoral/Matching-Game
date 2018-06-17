package me.s17339.memorygame.javafx.utils;

import java.util.EnumMap;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import me.s17339.memorygame.resinfo.GameSong;
import me.s17339.memorygame.resinfo.GameSound;
import me.s17339.memorygame.utils.ResourcesExplorer;

public class JavaFX_GameAudio {	
	/*	Sounds	*/
	private static final String SOUNDS_PATH = "/sounds/";
	private static Map<GameSound, AudioClip> sounds = new EnumMap<>(GameSound.class);
	public static void playSound(GameSound sound, DoubleProperty volumeProperty) {
		AudioClip clip = JavaFX_GameAudio.sounds.get(sound);
		if(clip == null) {
			String path = ResourcesExplorer.class.getResource(SOUNDS_PATH + sound.getPath() + ".wav").toExternalForm();
			clip = new AudioClip(path);
			JavaFX_GameAudio.sounds.put(sound, clip);
		}
		clip.volumeProperty().bind(volumeProperty);
		clip.play();
	}
	
		
	/*	Music	*/
	private static final String SONGS_PATH = "/songs/";
	private static Map<GameSong, Media> songs = new EnumMap<>(GameSong.class);
	private static MediaPlayer currentSong;
	public static void playSong(GameSong song, DoubleProperty volumeProperty) {
		Media media = songs.get(song);
		if(media == null) {
			String path = ResourcesExplorer.class.getResource(SONGS_PATH + song.getPath() + ".mp3").toExternalForm();
			media = new Media(path);
			JavaFX_GameAudio.songs.put(song, media);
		}
		if(JavaFX_GameAudio.currentSong != null) {
			if(JavaFX_GameAudio.currentSong.getMedia() == media)
				return;
			JavaFX_GameAudio.currentSong.stop();
		}
		JavaFX_GameAudio.currentSong = new MediaPlayer(media);
		JavaFX_GameAudio.currentSong.volumeProperty().bindBidirectional(volumeProperty);
		JavaFX_GameAudio.currentSong.setCycleCount(MediaPlayer.INDEFINITE);
		JavaFX_GameAudio.currentSong.play();
	}
}
