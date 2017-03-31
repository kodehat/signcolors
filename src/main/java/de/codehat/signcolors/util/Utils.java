/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Contains some useful methods.
 */
public class Utils {

    /**
     * Checks if a string is an integer.
     *
     * @param string String to check.
     * @return       true if Integer, false if not.
     */
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    /**
     * Checks wheather a string is a double or not.
     *
     * @param string String to check.
     * @return       true if double, false if not.
     */
    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    /**
     * Helper to extract files from the plugin's .jar file.
     *
     * @param in   Resource via getResource("file-in-jar.ending").
     * @param file Location where the file should be put to.
     */
    public static void extractFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a directory and all its contents.
     *
     * @param directory Directory to delete.
     * @return          true if directory was deleted.
     */
    public static boolean deleteDirectory(File directory) {
        if (directory.isDirectory() && directory.listFiles().length > 0) {
            for (File f : directory.listFiles()) {
                f.delete();
            }
        }
        return directory.delete();
    }
}