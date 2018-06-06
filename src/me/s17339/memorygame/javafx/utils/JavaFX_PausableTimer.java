package me.s17339.memorygame.javafx.utils;

import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

public class JavaFX_PausableTimer {
	private Consumer<Long> timeDifferenceConsumer;
	private AnimationTimer task;
	
	private long lastKnownTime;
	
	public JavaFX_PausableTimer(Consumer<Long> timeDifferenceConsumer) {
		this.timeDifferenceConsumer = timeDifferenceConsumer;
	}
	
	public void play() {
		if(task != null)
			return;
		lastKnownTime = System.nanoTime();
		this.task = new AnimationTimer() {
			@Override
			public void handle(long now) {
				Long difference = now - lastKnownTime;
				lastKnownTime = now;
				timeDifferenceConsumer.accept(difference);
			}
		};
		task.start();
	}
	
	public void pause() {
		if(task != null) {
			task.stop();
			task = null;
		}
	}
}
