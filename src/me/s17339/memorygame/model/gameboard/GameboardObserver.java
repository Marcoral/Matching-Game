package me.s17339.memorygame.model.gameboard;

public interface GameboardObserver {
	void notifyTileRevealed(int x, int y);
	void notifyTileUnrevealed(int x, int y);
	void notifyWrongMatch(int x1, int y1, int x2, int y2);
	void notifyGoodMatch(int x1, int y1, int x2, int y2);
	void notifyUndo(int x1, int y1);
	void notifyGamePaused();
	void notifyGameUnpaused();
	void notifyCheckingFinished();
	void notifyGameFinished(long finishTime);

	void notifyTimeUpdate(long now);
}