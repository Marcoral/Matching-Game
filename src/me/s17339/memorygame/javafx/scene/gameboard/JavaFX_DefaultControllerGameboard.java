package me.s17339.memorygame.javafx.scene.gameboard;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import javafx.beans.binding.StringBinding;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import me.s17339.memorygame.javafx.scene.JavaFX_GameEnvironmentInfo;
import me.s17339.memorygame.javafx.scene.JavaFX_SceneRoot;
import me.s17339.memorygame.javafx.scene.abstractscenelayer.JavaFX_AbstractSceneLayer;
import me.s17339.memorygame.javafx.scene.gameboard.escapedialog.JavaFX_ControllerGameEscapeDialog;
import me.s17339.memorygame.javafx.scene.gameboard.escapedialog.JavaFX_DefaultControllerGameEscapeDialog;
import me.s17339.memorygame.javafx.scene.highscores.JavaFX_ControllerHighscores;
import me.s17339.memorygame.javafx.scene.highscores.JavaFX_DefaultControllerHighscores;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_ControllerMainMenu;
import me.s17339.memorygame.javafx.scene.mainmenu.JavaFX_DefaultControllerMainMenu;
import me.s17339.memorygame.javafx.utils.JavaFX_GameAudio;
import me.s17339.memorygame.model.gameboard.GameboardInterface;
import me.s17339.memorygame.model.scoreboard.DifficultyLevel;
import me.s17339.memorygame.model.scoreboard.HighscoreEntry;
import me.s17339.memorygame.resinfo.GameSound;
import me.s17339.memorygame.resinfo.LanguageNode;
import me.s17339.memorygame.utils.Parsers;

public class JavaFX_DefaultControllerGameboard implements JavaFX_ControllerGameboard {
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	private GameboardInterface modelGameboard;
	private JavaFX_GameEnvironmentInfo environment;
	private JavaFX_SceneRoot sceneRoot;
	private DifficultyLevel difficultyLevel;
	private JavaFX_ViewGameboard view;
	public JavaFX_DefaultControllerGameboard(GameboardInterface modelGameboard, JavaFX_GameEnvironmentInfo environment, JavaFX_SceneRoot sceneRoot, DifficultyLevel difficultyLevel) {
		this.modelGameboard = modelGameboard;
		this.environment = environment;
		this.sceneRoot = sceneRoot;
		this.difficultyLevel = difficultyLevel;
	}

	/* ------------------------------------------------	*/
	/*					ACTION HANDLING					*/
	/* ------------------------------------------------	*/
		
	@Override
	public JavaFX_ViewGameboard createView() {
		JavaFX_GameAudio.playSong(difficultyLevel.getCorrespondingSong(), environment.getModelSettings().getSongsVolumeProperty());
		this.view = new JavaFX_ViewGameboard(modelGameboard, environment, this, sceneRoot, modelGameboard.getRows(), modelGameboard.getColumns());
		return view;
	}

	@Override
	public void handleImageClick(int x, int y) {
		modelGameboard.revealTileAt(x, y);
	}

	@Override
	public void playRevealingSound() {
		JavaFX_GameAudio.playSound(GameSound.INGAME_REVEAL, environment.getModelSettings().getSoundsVolumeProperty());
	}

	@Override
	public void playWrongMatchSound() {
		JavaFX_GameAudio.playSound(GameSound.INGAME_PAIR_WRONG, environment.getModelSettings().getSoundsVolumeProperty());		
	}

	@Override
	public void playCorrectMatchSound() {
		JavaFX_GameAudio.playSound(GameSound.INGAME_PAIR_CORRECT, environment.getModelSettings().getSoundsVolumeProperty());		
	}

	@Override
	public void handleGameEnd(long finishTime) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Koniec gry");
		long timePlayed = finishTime - modelGameboard.getStartTime();
		StringBinding contentText = new StringBinding() {
			{
				super.bind(environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_TYPING));
			}
			
			@Override
			protected String computeValue() {
				String separator = System.lineSeparator();
				return environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_TYPING).get()
						.replaceAll("%nextline", separator)
						.replaceAll("%time", Parsers.formatGameTime(timePlayed));
			}
		};
		dialog.contentTextProperty().bind(contentText);
		StringBinding headerText = new StringBinding() {
			{
				super.bind(environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_CHOOSE_LEVEL));
				super.bind(environment.getModelSettings().getLanguageNode(difficultyLevel.getCorrespondingLanguageNode()));
			}

			@Override
			protected String computeValue() {
				return environment.getModelSettings().getLanguageNode(LanguageNode.HIGHSCORES_CHOOSE_LEVEL).get() + ": "
						+ environment.getModelSettings().getLanguageNode(difficultyLevel.getCorrespondingLanguageNode()).get();
			}
		};
		dialog.headerTextProperty().bind(headerText);
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			playCorrectMatchSound();
			HighscoreEntry entry = new HighscoreEntry(result.get(), timePlayed);
			environment.getModelScoreboards().getScoreboard(difficultyLevel).submitHighscore(entry);
			JavaFX_ControllerHighscores controllerHighscores = new JavaFX_DefaultControllerHighscores(environment, sceneRoot);
			controllerHighscores.createView(difficultyLevel);
		} else {
			playWrongMatchSound();
			JavaFX_ControllerMainMenu controllerMainMenu = new JavaFX_DefaultControllerMainMenu(environment, sceneRoot);
			controllerMainMenu.createView().playMusic();
		}
	}

	private Queue<JavaFX_AbstractSceneLayer> attachedLayers = new LinkedList<>();
	@Override
	public void handleEscapeButtonPress(KeyEvent event) {
		if(attachedLayers.size() != 0)
			return;
		JavaFX_ControllerGameEscapeDialog gameEscapeDialog = new JavaFX_DefaultControllerGameEscapeDialog(environment, view);
		gameEscapeDialog.createView().attach(view, environment);
	}

	@Override
	public void notifyLayerAttached(JavaFX_AbstractSceneLayer layer) {
		attachedLayers.offer(layer);
		modelGameboard.setPaused(true);
	}

	@Override
	public void notifyLayerDetached(JavaFX_AbstractSceneLayer layer) {
		attachedLayers.poll();
		if(attachedLayers.size() == 0)
			modelGameboard.setPaused(false);
	}
}
