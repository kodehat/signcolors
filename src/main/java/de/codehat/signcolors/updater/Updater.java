/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.updater;

import de.codehat.signcolors.util.HttpRequest;

public class Updater implements Runnable {

    private static final String URL = "https://codehat.de/api/spigot/signcolors/version";
    private String plugin_version = null;
    private UpdateCallback<UpdateResult, String> c;

    /**
     * Constrcutor.
     *
     * @param current_version Current plugin version.
     */
    public Updater(String current_version, UpdateCallback<UpdateResult, String> c) {
        this.plugin_version = current_version;
        this.c = c;
    }

    /**
     * Checks for updates.
     *
     */
    @Override
    public void run() {
        String version = "";
        try {
            version = HttpRequest.sendGet(Updater.URL);
        } catch (Exception e) {
            c.call(UpdateResult.COULD_NOT_CHECK, version);
        }
        if (!plugin_version.equals(version)) {
            c.call(UpdateResult.NEEDED, version);
        } else {
            c.call(UpdateResult.UNNEEDED, version);
        }
    }

    /**
     * Get the plugin download link.
     *
     * @return The plugin download link.
     */
    public static String getDownloadUrl() {
        return "http://www.spigotmc.org/resources/signcolors.6135/";
    }

}