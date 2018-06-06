package me.s17339.memorygame.javafx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import me.s17339.memorygame.resinfo.CustomFont;
import me.s17339.memorygame.resinfo.GameImage;
import me.s17339.memorygame.resinfo.ResourcePath;
import me.s17339.memorygame.utils.ResourcesExplorer;
import me.s17339.memorygame.utils.ResourcesExplorer.JarQueryResult;

public class JavaFX_GameResources {	
	private static Image pictureOfReverse;
	private static List<Image> pictures = new ArrayList<>();
	
	public static void setup() {
		for(GameImage image : GameImage.values())
			JavaFX_GameResources.getImage(image);
		for(CustomFont font : CustomFont.values())
			JavaFX_GameResources.getCustomFont(font, 1);
		loadPictures();
	}
	private static void loadPictures() {
		Consumer<JarQueryResult> jarCase = query -> {
			try(InputStream stream = query.getJar().getInputStream(query.getEntry())) {
				if(query.getShortenedFileName().equals(ResourcePath.PICTURES_REVERSE.get()))
					process(stream, image -> pictureOfReverse = image);
				else
					process(stream, pictures::add);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		Consumer<File> IDECase = file -> {
			try(FileInputStream stream = new FileInputStream(file)) {
				if(file.getName().equals(ResourcePath.PICTURES_REVERSE.get()))
					process(stream, image -> pictureOfReverse = image);
				else
					process(stream, pictures::add);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		ResourcesExplorer.processAllEntriesInDirectory(ResourcePath.PICTURES_DIRECTORY.get(), jarCase, IDECase);
	}
	private static void process(InputStream stream, Consumer<Image> actionOnSuccess) {
		Image image = new Image(stream);
		if(!image.isError())
			actionOnSuccess.accept(image);
	}
	
	public static int getRegisteredPicturesCount() {
		return pictures.size();
	}
	public static Image getPicture(int index) {
		if(index >= pictures.size())
			throw new RuntimeException("Requested picture on index " + index + ", but there is only " + pictures.size() + "! (pictures are indexed from 0)");
		return pictures.get(index);
	}
	public static Image getPictureOfReverse() {
		return pictureOfReverse;
	}
	public static String getCssFilePath(String styleName) {
		return ResourcePath.CSS_DIRECTORY.get() + styleName + ".css";
	}
	
	private static Map<CustomFont, String> fontNames = new EnumMap<>(CustomFont.class);
	public static Font getCustomFont(CustomFont font, int size) {
		String fontName = fontNames.get(font);
		if(fontName != null)
			return Font.font(fontName, size);
		InputStream inputStream = ResourcesExplorer.class.getResourceAsStream(ResourcePath.FONTS_DIRECTORY.get() + font.getPath() + ".ttf");
		Font result = Font.loadFont(inputStream, size);
		fontNames.put(font, result.getName());
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private static Map<GameImage, Image> images = new EnumMap<>(GameImage.class);
	public static Image getImage(GameImage image) {
		Image imageFile = images.get(image);
		if(imageFile == null) {
			imageFile = new Image(ResourcePath.IMAGES_DIRECTORY.get() + image.getPath());
			images.put(image, imageFile);
		}
		return imageFile;
	}
}
