package me.s17339.memorygame.model.gameboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import me.s17339.memorygame.javafx.utils.JavaFX_PausableTimer;

public class GameboardModel implements GameboardInterface {
	private GameboardSettingsInterface modelSettings;
	
	private int pairsLeft;
	private GameTileInfo[][] tiles;
	private Long startTime;
	private long countedTime;
	private JavaFX_PausableTimer timer;
	private boolean isPaused;
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/

	public GameboardModel(GameboardSettingsInterface modelSettings, GameboardGeneratorConstraints generatorConstraints) {
		validateConstraints(generatorConstraints);
		int rows = generatorConstraints.getRows();
		int columns = generatorConstraints.getColumns();
		this.modelSettings = modelSettings;
		this.pairsLeft = columns * rows / 2;
		this.tiles = new GameTileInfo[rows][columns];
		registerTiles(generatorConstraints);
	}
	
	private void validateConstraints(GameboardGeneratorConstraints generatorConstraints) {
		int rows = generatorConstraints.getRows();
		int columns = generatorConstraints.getColumns();
		if(columns <= 0 || rows <= 0)
			throw new IllegalArgumentException("Gameboard columns and rows must be greater than 0!");
		if(columns * rows % 2 == 1)
			throw new OddNumberOfTilesGameException(columns, rows);
		if(generatorConstraints.getAllowedValues() == null) {
			if(generatorConstraints.getValuesFromInclusive() >= generatorConstraints.getValuesToExclusive())
				throw new IllegalArgumentException("Values-index-from must not be greater or equal to values-index-to");
		} else
			if(generatorConstraints.getAllowedValues().size() == 0)
				throw new IllegalArgumentException("Allowed-index-values list must not be null!");
	}
	
	private void registerTiles(GameboardGeneratorConstraints generatorConstraints) {
		List<Integer> allowedValues = generatorConstraints.getAllowedValues();
		if(allowedValues == null) {
			int from = generatorConstraints.getValuesFromInclusive();
			int to = generatorConstraints.getValuesToExclusive();
			allowedValues = new ArrayList<>(to-from-1);
			for(; from < to; from++)
				allowedValues.add(from);
		}
		Collections.shuffle(allowedValues);
		int rows = generatorConstraints.getRows();
		int columns = generatorConstraints.getColumns();
		int tilesCount = rows * columns;
		int pairsCount = tilesCount / 2;
		List<Integer> tiles = new ArrayList<>(tilesCount);
		Random random = new Random();
		for(int i = 0; i < pairsCount; i++) {
			int randomNumber = allowedValues.get(random.nextInt(allowedValues.size()));
			tiles.add(randomNumber);
			tiles.add(randomNumber);
		}
		Collections.shuffle(tiles);
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				this.tiles[i][j] = new GameTileInfo(tiles.get(i * columns + j));
	}
	
	/* ----------------------------------------	*/
	/*					LOGIC					*/
	/* ----------------------------------------	*/
	
	public GameTileInfo getTileInfo(int x, int y) throws IndexOutOfBoundsException {
		try {
			return tiles[y][x];
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Attempted to access tile at (" + x + ", " + y + "),"
					+ " but board is of size " + getColumns() + " x " + getRows() + " (remember about indexing from 0!)");
		}
	}
	
	private GameRevealedTileInfo revealedFirst = new GameRevealedTileInfo();
	private GameRevealedTileInfo revealedSecond = new GameRevealedTileInfo();
	
	@Override
	public Long getStartTime() {
		return startTime;
	}
	
	@Override
	public long getCountedTime() {
		return countedTime;
	}
	
	@Override
	public boolean isPaused() {
		return isPaused();
	}
	
	@Override
	public void setPaused(boolean paused) {
		if(isPaused == paused)
			return;
		isPaused = paused;
		if(paused) {
			if(timer != null) {
				timer.pause();
				if(revealerTask != null)
					revealerTask.pause();
			}
			observers.forEach(GameboardObserver::notifyGamePaused);
		} else {
			if(timer != null) {
				timer.play();
				if(revealerTask != null)
					revealerTask.play();
			}
			observers.forEach(GameboardObserver::notifyGameUnpaused);
		}
	}
	
	@Override
	public void revealTileAt(int x, int y) {
		if(timer == null) {
			timer = new JavaFX_PausableTimer(difference -> {
				countedTime += difference;
				observers.forEach(observer -> observer.notifyTimeUpdate(difference));	
			});
			startTime = System.nanoTime();
			timer.play();
		}
		GameTileInfo requestedTile = getTileInfo(x, y);
		if(!requestedTile.isCollected()) {
			if(revealedFirst.tileInfo != null) {
				if(revealedSecond.tileInfo != null)
					performRevealerAction();
				else if(revealedFirst.tileInfo == requestedTile) {
					if(modelSettings.isUnrevealingAllowed()) {
						hide(revealedFirst);
						observers.forEach(observer -> observer.notifyUndo(x, y));
						observers.forEach(GameboardObserver::notifyCheckingFinished);
						return;
					} else {
						observers.forEach(GameboardObserver::notifyCheckingFinished);
						return;
					}
				}
			}			
			
			if(revealedFirst.tileInfo == requestedTile || revealedSecond.tileInfo == requestedTile) {
				observers.forEach(GameboardObserver::notifyCheckingFinished);
				return;
			}
			if(revealedFirst.tileInfo == null)
				reveal(revealedFirst, requestedTile, x, y);
			else {
				reveal(revealedSecond, requestedTile, x, y);
				int number = revealedSecond.tileInfo.getNumber();
				if(number == revealedFirst.tileInfo.getNumber()) {
					long now = System.nanoTime();
					collected();
					if(pairsLeft == 0) {
						timer.pause();
						observers.forEach(GameboardObserver::notifyCheckingFinished);
						observers.forEach(observer -> observer.notifyTimeUpdate(now));
						observers.forEach(observer -> observer.notifyGameFinished(now));
						return;
					}
				} else 
					wrongMatch();
			}
		}
		observers.forEach(GameboardObserver::notifyCheckingFinished);
	}
	
	private void reveal(GameRevealedTileInfo revealedTileInfo, GameTileInfo tileInfo, int x, int y) {
		observers.forEach(observer -> observer.notifyTileRevealed(x, y));
		revealedTileInfo.tileInfo = tileInfo;
		tileInfo.setRevealed(true);
		revealedTileInfo.x = x;
		revealedTileInfo.y = y;
	}
	
	private void hide(GameRevealedTileInfo tileInfo) {
		observers.forEach(observer -> observer.notifyTileUnrevealed(tileInfo.x, tileInfo.y));
		tileInfo.tileInfo.setRevealed(false);
		tileInfo.tileInfo = null;
	}
	
	private void collected() {
		int x1 = revealedFirst.x;
		int x2 = revealedSecond.x;
		int y1 = revealedFirst.y;
		int y2 = revealedSecond.y;
		revealedSecond.tileInfo.setCollected(true);
		revealedFirst.tileInfo.setCollected(true);
		revealedFirst.tileInfo = null;
		revealedSecond.tileInfo = null;
		observers.forEach(observer -> observer.notifyGoodMatch(x1, y1, x2, y2));
		pairsLeft--;
	}
	
	private JavaFX_PausableTimer revealerTask;
	private long timeLeft;
	private void wrongMatch() {
		int x1 = revealedFirst.x;
		int x2 = revealedSecond.x;
		int y1 = revealedFirst.y;
		int y2 = revealedSecond.y;
		observers.forEach(observer -> observer.notifyWrongMatch(x1, y1, x2, y2));
		
		timeLeft = 2000000000L;
		revealerTask = new JavaFX_PausableTimer(difference -> {
			timeLeft -= difference;
			if(timeLeft <= 0)
				performRevealerAction();
		});
		revealerTask.play();
	}
	
	private void performRevealerAction() {
		hide(revealedFirst);
		hide(revealedSecond);
		revealerTask.pause();
		//Prevents from unpausing task.
		//Once tiles are unrevealed, this task won't be useful anymore
		revealerTask = null;
	}
	
	private class GameRevealedTileInfo {
		private GameTileInfo tileInfo;
		private int x, y;
	}

	/* --------------------------------------------------------	*/
	/*					CONTROLLERS HANDLING					*/
	/* --------------------------------------------------------	*/

	@Override
	public int getPairsLeft() {
		return pairsLeft;
	}
	
	@Override
	public int getColumns() {
		return tiles[0].length;
	}
	
	@Override
	public int getRows() {
		return tiles.length;
	}
	
	private List<GameboardObserver> observers = new LinkedList<>();
	@Override
	public void registerObserver(GameboardObserver observer) {
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(GameboardObserver observer) {
		observers.remove(observer);
	}
}
