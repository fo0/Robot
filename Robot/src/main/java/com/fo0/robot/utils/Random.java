package com.fo0.robot.utils;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

import java.util.UUID;

import org.apache.commons.text.RandomStringGenerator;

public class Random {

	public static Integer numeric(int length) {
		return Integer.parseInt(new RandomStringGenerator.Builder().withinRange('0', '9').build().generate(length));
	}

	public static String alphanumeric(int length) {
		return new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(LETTERS, DIGITS).build()
				.generate(length);
	}

	public static String alphabetic(int length) {
		return new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(LETTERS).build().generate(length);
	}

	public static UUID UUID() {
		return UUID.randomUUID();
	}
}
