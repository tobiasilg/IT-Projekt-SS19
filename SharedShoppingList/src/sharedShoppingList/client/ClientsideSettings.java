package sharedShoppingList.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.CommonSettings;
import sharedShoppingList.shared.EinkaufslistenVerwaltungAsync;
import sharedShoppingList.shared.Einkaufslistenverwaltung;
import sharedShoppingList.shared.ReportClient;
import sharedShoppingList.shared.ReportClientAsync;

/*
 * Klasse mit allen Eigenschaften und Diensten, welche für alle 
 * Client-Seitigen Klassen relevant sind.  
 */

public class ClientsideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-Seitgen Dienst
	 */

	private static EinkaufslistenVerwaltungAsync einkaufslistenverwaltung = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-Seitgen Dienst
	 */

	private static ReportClientAsync reportClient = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "SharedShoppingList Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */

	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * Auslesen des applikationsweiten client-seitig zentralen Loggers
	 * 
	 * @return Logger-Instanz
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * Anlegen und Auslesen der Einkaufslistenverwaltung
	 */
	public static EinkaufslistenVerwaltungAsync getEinkaufslistenverwaltung() {
		// Gab es bislang noch keine Pinnwandverwaltungs-Instanz, dann...
		if (einkaufslistenverwaltung == null) {
			// Zunächst instantiieren wir Pinnwandverwaltung
			einkaufslistenverwaltung = GWT.create(Einkaufslistenverwaltung.class);
		}

		// So, nun brauchen wir die Pinnwandverwaltung nur noch zurückzugeben.
		return einkaufslistenverwaltung;
	}

	/**
	 * Anlegen und Auslesen des ReportGenerators
	 */
	public static ReportClientAsync getReportGenerator() {
		// Gab es bislang noch keine ReportGenerator-Instanz, dann...
		if (reportClient == null) {
			// Zunächst instantiieren wir ReportGenerator
			reportClient = (ReportClientAsync) GWT.create(ReportClient.class);

			final AsyncCallback<Void> initReportClientCallback = new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};

			reportClient.init(initReportClientCallback);
		}

		// So, nun brauchen wir den ReportGenerator nur noch zurückzugeben.
		return reportClient;
	}
}
