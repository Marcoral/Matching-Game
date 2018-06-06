package me.s17339.memorygame.model.settings;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import me.s17339.memorygame.resinfo.LanguageNode;
import me.s17339.memorygame.utils.ResourcesExplorer;
import me.s17339.memorygame.utils.ResourcesExplorer.JarQueryResult;

public class GameLanguageModel implements GameLanguageInterface {
	
	private static final long serialVersionUID = 6877126117520073243L;
	
	/* --------------------------------------------------------	*/
	/*					LANGUAGE FILES LOADING					*/
	/* --------------------------------------------------------	*/
	
	private static final Locale DEFAULT_LOCALE = new Locale("en"); 
	private static final String LANGUAGE_PATH = "languages/";
	private static final String LANGUAGE_BUNDLE_NAME = "Language";
	
	private static GameLanguageInfo defaultLanguage;
	
	
	private static Set<GameLanguageInfo> installedLanguages = new TreeSet<>();
	public static void reloadLanguageFiles() {
		GameLanguageModel.installedLanguages.clear();
		Consumer<JarQueryResult> jarCase = result -> {
			String name = result.getShortenedFileName();
			process(name);
		};
		Consumer<File> IDECase = file -> {
			String splitFileName[] = file.getName().split("/");
			String name = splitFileName[splitFileName.length-1];
			process(name);
		};
		if(defaultLanguage == null)
			defaultLanguage = new GameLanguageInfo(DEFAULT_LOCALE);
		ResourcesExplorer.processAllEntriesInDirectory(LANGUAGE_PATH, jarCase, IDECase);
	}
	
	private static void process(String name) {
		int index = name.indexOf(".properties");
		if(index == -1)
			return;
		String[] splitFileName = name.substring(0, index).split("_");
		if(splitFileName.length >= 2 && splitFileName[0].equals(LANGUAGE_BUNDLE_NAME))
			try {
				String language = splitFileName[1];
				String country = null;
				if(splitFileName.length > 2)
					country = splitFileName[2];
				Locale locale = country == null? new Locale(language) : new Locale(language, country);
				ResourceBundle bundle = ResourceBundle.getBundle(LANGUAGE_PATH + LANGUAGE_BUNDLE_NAME, locale);
				GameLanguageInfo languageInfo = new GameLanguageInfo(locale);
				if(locale.getLanguage().equals(Locale.getDefault().getLanguage()))
					defaultLanguage = languageInfo;
				languageInfo.displayedName = bundle.getString(LanguageNode.GENERAL_LANGUAGENAME.getPath());
				GameLanguageModel.installedLanguages.add(languageInfo);
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	public static Set<GameLanguageInfo> getInstalledLanguages() {
		return Collections.unmodifiableSet(installedLanguages);
	}
	
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	

	private GameLanguageInfo currentLanguage;
	
	GameLanguageModel(List<GameSettingsObserver> observers) {
		this(defaultLanguage, observers);
	}
	GameLanguageModel(GameLanguageInfo initialLanguage, List<GameSettingsObserver> observers) {
		this.observers = observers;
		this.currentLanguage = initialLanguage;
		switchLanguage(initialLanguage);
	}
	
	/* ------------------------------------------------	*/
	/*					DESERIALIZATION					*/
	/* ------------------------------------------------	*/
	
	private transient List<GameSettingsObserver> observers;
	private transient Map<LanguageNode, String> currentNodes;
	
	//Initializing language
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        observers = new LinkedList<>();
        switchLanguage(currentLanguage);
    }
	
	//Used to synchronize observers list with settings model one
	void injectObservers(List<GameSettingsObserver> observers) {
		this.observers = observers;
	}


	/* ----------------------------------------	*/
	/*					LOGIC					*/
	/* ----------------------------------------	*/
	
	@Override
	public void restoreDefaultLanguage() {
		switchLanguage(defaultLanguage);
	}
	
	@Override
	public void switchLanguage(GameLanguageInfo language) {
		try {
			if(currentNodes == null)
				this.currentNodes = new EnumMap<>(LanguageNode.class);
			else if(language.equals(currentLanguage))
				return;
			ResourceBundle bundle = ResourceBundle.getBundle(LANGUAGE_PATH + LANGUAGE_BUNDLE_NAME, language.toLocale());
			for(LanguageNode lnode : LanguageNode.values()) {
				String node = convertString(bundle.getString(lnode.getPath()));
				currentNodes.put(lnode, node);
			}
			GameLanguageInfo oldValue = currentLanguage;
			currentLanguage = language;
			observers.forEach(observer -> observer.notifyLanguageChanged(oldValue));
		} catch (MissingResourceException e) {
			switchLanguage(defaultLanguage);
		}
	}
	
	@Override
	public String get(LanguageNode node) {
		return currentNodes.get(node);
	}
	
	@Override
	public GameLanguageInfo getCurrentLanguage() {
		return currentLanguage;
	}

	
	/* ------------------------------------------------------------	*/
	/*					LANGUAGE ADDITIONAL INFO					*/
	/* ------------------------------------------------------------	*/
	
	private static String convertString(String string) {
		try {
			string = new String(string.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}
	
	public static class GameLanguageInfo implements Serializable, Comparable<GameLanguageInfo> {
		private static final long serialVersionUID = 4756854977445377790L;
		
		private String language;
		private String country;
		private transient String displayedName;

		private GameLanguageInfo() {}
		private GameLanguageInfo(Locale locale) {
			language = locale.getLanguage();
			country = locale.getCountry();
		}
		
		private Locale toLocale() {
			return country == null? new Locale(language) : new Locale(language, country);
		}
		
		@Override
		public String toString() {
			return displayedName;
		}

		@Override
		public int compareTo(GameLanguageInfo second) {
			return displayedName.compareTo(second.displayedName);
		}
		
		@Override
		public boolean equals(Object second) {
			if(this == second)
				return true;
			if(!(second instanceof GameLanguageInfo))
				return false;
			GameLanguageInfo secondInfo = (GameLanguageInfo) second;
			return language.equals(secondInfo.language) && country.equals(secondInfo.country);
		}
	}
}
