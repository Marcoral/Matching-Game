package me.s17339.memorygame.utils;

public class Parsers {
	public static String formatGameTime(long time) {
		long milisInSecond = 1000000000L;
		long milisInMinute = 60 * milisInSecond;
		long rounder = 1000000;
		return String.format("%02d:%02d.%03d", time / milisInMinute, (time % milisInMinute) / milisInSecond, (time % milisInSecond) / rounder);
	}
}
