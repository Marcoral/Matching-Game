package me.s17339.memorygame.model.scoreboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.EnumMap;

import me.s17339.memorygame.model.AbstractGameModel;
import me.s17339.memorygame.resinfo.ResourcePath;

public class ScoreboardSetModel extends AbstractGameModel<ScoreboardObserver> implements ScoreboardObserver {
	private static final long serialVersionUID = 1540111937883204548L;
	
	public static ScoreboardSetModel createDefault() {
		return new ScoreboardSetModel(ResourcePath.SERIALIZATION_GAMEBOARDS_FILE.get());
	}
	
	private EnumMap<DifficultyLevel, Scoreboard> scoreboards = new EnumMap<>(DifficultyLevel.class);
	public ScoreboardSetModel(String path) {
		super(path);
		scoreboards = new EnumMap<>(DifficultyLevel.class);
		for(DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
			Scoreboard scoreboard = new Scoreboard();
			scoreboard.registerObserver(this);
			scoreboards.put(difficultyLevel, scoreboard);
		}
	}
		
	public Scoreboard getScoreboard(DifficultyLevel difficultyLevel) {
		return scoreboards.get(difficultyLevel);
	}

	@Override
	public void notifyBoardChange() {
		save();
		observers.forEach(ScoreboardObserver::notifyBoardChange);
	}
	
	//Deserialization fix
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        scoreboards.values().forEach(scoreboard -> scoreboard.registerObserver(this));
    }
}
