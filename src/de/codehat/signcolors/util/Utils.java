package de.codehat.signcolors.util;

/**
 * SignColors
 * @author CodeHat
 */

public class Utils {

	/**
	 * Checks if a String is an Integer.
	 * @param s String to check.
	 * @return true if Integer, false if not.
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}