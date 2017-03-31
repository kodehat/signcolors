/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.managers;

import de.codehat.signcolors.SignColors;

public abstract class Manager {

    private SignColors plugin;

    Manager(SignColors plugin) {
        this.plugin = plugin;
    }

    /**
     * Get the plugin instance.
     *
     * @return The plugin instance.
     */
    public SignColors getPlugin() {
        return this.plugin;
    }

}
