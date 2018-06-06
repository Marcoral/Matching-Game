package me.s17339.memorygame.model.settings;

import java.io.Serializable;

import me.s17339.memorygame.model.settings.GameLanguageModel.GameLanguageInfo;
import me.s17339.memorygame.resinfo.LanguageNode;

public interface GameLanguageInterface extends Serializable {
	void restoreDefaultLanguage();
	void switchLanguage(GameLanguageInfo language);
	String get(LanguageNode node);
	GameLanguageInfo getCurrentLanguage();
}
