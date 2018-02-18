package com.fo0.robot.utils;

import java.util.concurrent.TimeUnit;

public class Utils {

	public static void sleep(TimeUnit unit, long sleep) {
		try {
			unit.sleep(sleep);
		} catch (Exception e) {
		}
	}

	public static void sleep(long sleepMs) {
		try {
			TimeUnit.MILLISECONDS.sleep(sleepMs);
		} catch (Exception e) {
		}
	}

}
