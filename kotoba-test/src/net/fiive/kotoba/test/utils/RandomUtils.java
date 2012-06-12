package net.fiive.kotoba.test.utils;

import java.util.Random;

public class RandomUtils {

	public static String getRandomQuestionValue() {
		return String.format("Question no. %d", new Random().nextLong());
	}
}
