package me.s17339.memorygame.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGameModel<T> implements GameModel<T> {
	private static final long serialVersionUID = -7435450481346349300L;
	
	
	private String path;
	public AbstractGameModel(String path) {
		this.path = path;
	}
	
	@Override
	public void save() {
		GameObjectSerializer.save(path, this);
	}
	
	@Override
	public void save(String path) {
		GameObjectSerializer.save(path, this);
	}
	
	/* --------------------------------------------------------	*/
	/*					CONTROLLERS HANDLING					*/
	/* --------------------------------------------------------	*/
	
	//Deserialization fix
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        observers = new LinkedList<>();
    }
	
	protected transient List<T> observers = new LinkedList<>();
	@Override
	public void registerObserver(T observer) {
		observers.add(observer);
	}
	
	@Override
	public void removeObserver(T observer) {
		observers.remove(observer);
	}
}
