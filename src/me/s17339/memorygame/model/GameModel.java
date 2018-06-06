package me.s17339.memorygame.model;

import java.io.Serializable;

public interface GameModel<T> extends Serializable {
	void save();
	void save(String path);
	
	void registerObserver(T observer);
	void removeObserver(T observer);
}
