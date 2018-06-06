package me.s17339.memorygame.model.scoreboard;

import java.io.Serializable;

public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry> {
	private static final long serialVersionUID = 1203569633730705489L;

	private String nickname;
	private long time;
	
	public HighscoreEntry(String nickname, long time) {
		this.nickname = nickname;
		this.time = time;
	}
	
	public String getNickname() {
		return nickname;
	}

	public long getTime() {
		return time;
	}

	@Override
	public int compareTo(HighscoreEntry second) {
		if(second.time == time)
			return 0;
		else
			return second.time > time? 1 : -1;	//Shorter time is better ("greater")
	}
}
