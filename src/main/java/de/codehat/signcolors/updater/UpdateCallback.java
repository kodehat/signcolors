/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.updater;

@FunctionalInterface
public interface UpdateCallback<T, S> {

    /**
     * Callback to the plugin with result and new version.
     *
     * @param result  UpdateResult enum.
     * @param version New plugin version.
     */
    void call(T result, S version);
}
