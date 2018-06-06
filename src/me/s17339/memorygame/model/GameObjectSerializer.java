package me.s17339.memorygame.model;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.Supplier;

import me.s17339.memorygame.utils.ResourcesExplorer;

public class GameObjectSerializer {
	@SuppressWarnings("unchecked")
	public static <T> T load(String path, Supplier<T> onFailureObject) {
		File file = ResourcesExplorer.getFile(path);
		if(file != null)
			try(FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
				return (T) ois.readObject();
		} catch(EOFException e1) {
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return onFailureObject.get();
	}
	
	public static void save(String path, Object object) {
		File file = ResourcesExplorer.forceGetFile(path);
		try(FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
