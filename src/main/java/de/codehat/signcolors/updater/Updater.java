/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.updater;

import de.codehat.signcolors.util.HttpRequest;

public class Updater implements Runnable {

    private static final String UPDATE_URL = "https://api.codehat.de/plugin/1";
    private String currentVersion;
    private UpdateCallback callback;

    /**
     * The plugin's update checker.
     *
     * @param currentVersion Current plugin version.
     * @param callback       Callback, which is executed after checking for a newer version.
     */
    public Updater(String currentVersion, UpdateCallback callback) {
        this.currentVersion = currentVersion;
        this.callback = callback;
    }

    @Override
    public void run() {
        String newVersion = "";
        try {
            newVersion = HttpRequest.getNewVersion(Updater.UPDATE_URL);
        } catch (Exception exception) {
            this.callback.call(UpdateResult.COULD_NOT_CHECK, newVersion);
            return;
        }
        if (!this.currentVersion.equals(newVersion)) {
            this.callback.call(UpdateResult.AVAILABLE, newVersion);
        } else {
            this.callback.call(UpdateResult.UNAVAILABLE, newVersion);
        }
    }

    /**
     * Get the plugin's spigot page url.
     *
     * @return The plugin's spigot url.
     */
    public static String getSpigotUrl() {
        return "http://www.spigotmc.org/resources/signcolors.6135/";
    }

}