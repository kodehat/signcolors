/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.updater;

import de.codehat.signcolors.util.HttpRequest;

public class Updater {

    private static final String URL = "https://codehat.de/api/spigot/signcolors/version";
    private String plugin_version = null;
    private String latest_version = null;

    /**
     * Constrcutor.
     *
     * @param current_version Current plugin version.
     */
    public Updater(String current_version) {
        this.plugin_version = current_version;
    }

    /**
     * Checks for Updates.
     *
     * @return The result of the Updater.
     */
    public UpdateResult checkForUpdate() {
        String version;
        try {
            version = HttpRequest.sendGet(URL);
        } catch (Exception e) {
            return UpdateResult.COULD_NOT_CHECK;
        }
        if (!plugin_version.equals(version)) {
            latest_version = version;
            return UpdateResult.NEEDED;
        } else {
            return UpdateResult.UNNEEDED;
        }
    }

    /**
     * Get the latest version number on UpdateResult.NEEDED.
     *
     * @return The latest version.
     */
    public String getLatestVersion() {
        return latest_version;
    }

    /**
     * Get the plugin download link.
     *
     * @return The plugin download link.
     */
    public String getDownloadUrl() {
        return "http://www.spigotmc.org/resources/signcolors.6135/";
    }
}