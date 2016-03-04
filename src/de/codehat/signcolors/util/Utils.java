/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.util;

public class Utils {

    /**
     * Checks if a String is an Integer.
     *
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