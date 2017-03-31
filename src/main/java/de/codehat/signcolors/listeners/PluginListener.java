/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.listeners;

import de.codehat.signcolors.SignColors;
import org.bukkit.event.Listener;

public abstract class PluginListener implements Listener {

    private SignColors plugin;

    public PluginListener(SignColors plugin) {
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
