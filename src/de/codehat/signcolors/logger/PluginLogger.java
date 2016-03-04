/*
 * Copyright (c) 2016 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.logger;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class PluginLogger {

    private final Logger debugLogger = Logger.getLogger("PluginLogger");
    private Logger log;
    private FileHandler debugFileHandler;

    /**
     * Constrcutor.
     *
     * @param plugin Plugin instance.
     */
    public PluginLogger(Plugin plugin) {

        log = plugin.getLogger();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
        try {
            File log_dir = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "logs" + File.separator);
            if (!log_dir.exists()) log_dir.mkdir();
            debugFileHandler = new FileHandler(plugin.getDataFolder().getAbsolutePath() + File.separator + "logs" + File.separator + plugin.getName() + "-" + timeStamp + ".log", true);
            PluginFormatter formatter = new PluginFormatter();
            debugLogger.addHandler(debugFileHandler);
            debugFileHandler.setFormatter(formatter);
            debugLogger.setUseParentHandlers(false);

        } catch (IOException | SecurityException e) {
            e.printStackTrace();
            warn(e.getMessage(), true);
        }
    }

    /**
     * Logs with INFO level.
     *
     * @param msg    Message to log.
     * @param toFile Log it to file?
     */
    public void info(String msg, boolean toFile) {
        if (toFile) {
            if (debugLogger != null) {
                debugLogger.info(msg);
            }
        } else {
            log.info(msg);
        }
    }

    /**
     * Logs with WARNING level.
     *
     * @param msg    Message to log.
     * @param toFile Log it to file?
     */
    public void warn(String msg, boolean toFile) {
        if (toFile) {
            if (debugLogger != null) {
                debugLogger.warning(msg);
            }
        } else {
            log.warning(msg);
        }
    }

    /**
     * Close all Logger files.
     */
    public void close() {
        debugFileHandler.close();
    }
}