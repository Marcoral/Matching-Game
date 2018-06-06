package me.s17339.memorygame.model.gameboard;

public interface GameboardInterface {
	int getPairsLeft();
	int getColumns();
	int getRows();
	GameTileInfo getTileInfo(int x, int y);
	void revealTileAt(int x, int y);
	long getCountedTime();
	boolean isPaused();
	void setPaused(boolean paused);
	Long getStartTime();	//StartTime can be null if game hasn't started yet

	void registerObserver(GameboardObserver observer);
	void removeObserver(GameboardObserver observer);
}
