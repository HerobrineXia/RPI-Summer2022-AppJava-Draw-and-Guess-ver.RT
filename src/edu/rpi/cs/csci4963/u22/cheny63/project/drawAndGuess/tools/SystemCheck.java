package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools;

public class SystemCheck {
	public static boolean isMacintosh() {
		return System.getProperty("os.name").startsWith("Mac");
	}

	public static boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}
}
