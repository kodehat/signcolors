/*
 * Copyright (c) 2015 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class PluginFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		return "[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "]" + " [" + record.getLevel() + "]: " + record.getMessage();
	}
}