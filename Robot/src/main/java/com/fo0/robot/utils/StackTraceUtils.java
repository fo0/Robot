package com.fo0.robot.utils;

public class StackTraceUtils {

	private static final String UNKNOWN = "UnknownClass.Method";

	/**
	 * argument 0 is the current method name
	 *
	 * @param callsBack must be positive
	 * @return
	 */
	public static String methodCaller(int callsBack) {
		if (callsBack < 0) {
			throw new IllegalArgumentException("only positive values are allowed");
		}

		int modifycall = callsBack + 2;

		try {
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			return stack[modifycall].getClassName() + "." + stack[modifycall].getMethodName();
		} catch (Exception e) {
			System.err.println("failed to call stacktrace" + e);
		}

		return UNKNOWN;
	}

	/**
	 * argument 0 is the current method name
	 *
	 * @param callsBack must be positive
	 * @return
	 */
	public static String methodSimpleNameCaller(int callsBack) {
		if (callsBack < 0) {
			throw new IllegalArgumentException("only positive values are allowed");
		}

		int modifycall = callsBack + 2;

		try {
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			return stack[modifycall].getMethodName();
		} catch (Exception e) {
			System.err.println("failed to call stacktrace" + e);
		}

		return UNKNOWN;
	}

}
