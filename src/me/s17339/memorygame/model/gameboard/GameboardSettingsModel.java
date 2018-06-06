package me.s17339.memorygame.model.gameboard;

import me.s17339.memorygame.model.AbstractGameModel;
import me.s17339.memorygame.resinfo.ResourcePath;

public class GameboardSettingsModel extends AbstractGameModel<GameboardSettingsObserver> implements GameboardSettingsInterface {
	private static final long serialVersionUID = 6698958581350442106L;
	
	/* ----------------------------------------	*/
	/*					BUILDER					*/
	/* ----------------------------------------	*/
	
	public static class Builder {
		private String path = ResourcePath.SERIALIZATION_GAMEBOARDSETTINGS_FILE.get();
		private boolean unrevealingAllowed = true;
		
		public Builder filePath(String value) {
			path = value;
			return this;
		}
		
		public Builder hideOnClickOnVisible(boolean value) {
			unrevealingAllowed = value;
			return this;
		}
		
		public GameboardSettingsModel build() {
			return new GameboardSettingsModel(path, unrevealingAllowed);
		}
	}
	
	/* ------------------------------------------------	*/
	/*					CONSTRUCTION					*/
	/* ------------------------------------------------	*/
	
	public static GameboardSettingsModel createDefault() {
		return new GameboardSettingsModel.Builder().build();
	}
	
	private boolean unrevealingAllowed = true;
	private boolean hideOnCollect = false;
	public GameboardSettingsModel(String path, boolean unrevealingAllowed) {
		super(path);
		this.unrevealingAllowed = unrevealingAllowed;
	}
	
	/* ----------------------------------------	*/
	/*					LOGIC					*/
	/* ----------------------------------------	*/
	
	@Override
	public boolean isUnrevealingAllowed() {
		return unrevealingAllowed;
	}

	@Override
	public void setUnrevealingAllowed(boolean value) {
		this.unrevealingAllowed = value;
	}

	@Override
	public boolean isHideOnCollect() {
		return hideOnCollect;
	}

	@Override
	public void setHideOnCollect(boolean value) {
		this.hideOnCollect = value;
	}
}
