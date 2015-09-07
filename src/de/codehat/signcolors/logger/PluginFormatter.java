package de.codehat.signcolors.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * PluginFormatter
 * @author CodeHat
 */

public class PluginFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		return "[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "]" + " [" + record.getLevel() + "]: " + record.getMessage();
	}
}