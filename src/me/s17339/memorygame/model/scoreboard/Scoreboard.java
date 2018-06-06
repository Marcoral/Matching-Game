package me.s17339.memorygame.model.scoreboard;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Scoreboard implements Serializable {
	private static final long serialVersionUID = -9015429212403854228L;
	private List<HighscoreEntry> board;
	public Scoreboard() {
		this.board = new ArrayList<>();
	}

	public void submitHighscore(HighscoreEntry info) {
		board.add(info);
		Collections.sort(board, Collections.reverseOrder());
		observers.forEach(ScoreboardObserver::notifyBoardChange);
	}

	public List<HighscoreEntry> getHighscores() {
		return Collections.unmodifiableList(board);
	}
		
	public HighscoreEntry getScoreAtIndex(int index) {
		return getHighscores().get(index);
	}
	public int getRegisteredHighscoresCount() {
		return getHighscores().size();
	}
	

	/* --------------------------------------------------------	*/
	/*					CONTROLLERS HANDLING					*/
	/* --------------------------------------------------------	*/

	private transient List<ScoreboardObserver> observers = new LinkedList<>();
	public void registerObserver(ScoreboardObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(ScoreboardObserver observer) {
		observers.remove(observer);
	}
	
	//Deserialization fix
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        observers = new LinkedList<>();
    }
}