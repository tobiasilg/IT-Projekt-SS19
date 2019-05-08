package sharedShoppingList.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.CommonSettings;
import sharedShoppingList.shared.EinkaufslistenVerwaltungAsync;
import sharedShoppingList.shared.Einkaufslistenverwaltung;
import sharedShoppingList.shared.ReportGenerator;
import sharedShoppingList.shared.ReportGeneratorAsync;

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

	private static ReportGeneratorAsync reportGenerator = null;

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
	public static ReportGeneratorAsync getReportGenerator() {
		// Gab es bislang noch keine ReportGenerator-Instanz, dann...
		if (reportGenerator == null) {
			// Zunächst instantiieren wir ReportGenerator
			reportGenerator = (ReportGeneratorAsync) GWT.create(ReportGenerator.class);

			final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientsideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientsideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};

			reportGenerator.init(initReportGeneratorCallback);
		}

		// So, nun brauchen wir den ReportGenerator nur noch zurückzugeben.
		return reportGenerator;
	}
}
