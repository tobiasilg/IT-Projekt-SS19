package sharedShoppingList.server;

import java.util.logging.Logger;

import sharedShoppingList.shared.CommonSettings;

/*
 * Auslesen des applikationsweiten (Server-seitig!) zentralen Loggers
 * @author Nico Weiler
 */

public class ServersideSettings extends CommonSettings {

	private static final String LOGGER_NAME = "SharedShoppingList Server";
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * Auslesen des applikationsweiten zentralen Loggers.
	 * 
	 * @return Server-Seitige Logger-Instanz
	 */
	public static Logger getLogger() {
		return log;
	}

}
