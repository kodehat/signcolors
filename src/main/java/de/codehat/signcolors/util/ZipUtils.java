/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

/*
 * Copied and modified from:
 * http://www.roseindia.net/java/example/java/io/ZipFolderExample.shtml
 */

package de.codehat.signcolors.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void zipFolder(String sourceFolder, String outputZip) {
        try {
            File inFolder = new File(sourceFolder);
            File outFolder = new File(outputZip);
            ZipOutputStream out = new ZipOutputStream(new
                    BufferedOutputStream(new FileOutputStream(outFolder)));
            BufferedInputStream in = null;
            byte[] data = new byte[1000];
            String files[] = inFolder.list();
            for (int i = 0; i < files.length; i++) {
                in = new BufferedInputStream(new FileInputStream
                        (inFolder.getPath() + File.separator + files[i]), 1000);
                out.putNextEntry(new ZipEntry(files[i]));
                int count;
                while ((count = in.read(data, 0, 1000)) != -1) {
                    out.write(data, 0, count);
                }
                out.closeEntry();
                in.close();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}