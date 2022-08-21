package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools;

/**
 * SystemCheck is the utility for checking the current system type
 * 
 * @author Yuetian Chen
 * @version <b>1.0</b> rev. 0
 */
public class SystemCheck {
	/**
	 * check if the current platform is a version of unix-based max os 
	 * @return true if current system is mac os
	 */
	public static boolean isMacintosh() {
		return System.getProperty("os.name").startsWith("Mac");
	}

	/**
	 * check if the current platform is a version of windows NT
	 * @return true if current system is windows
	 */
	public static boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}
}
