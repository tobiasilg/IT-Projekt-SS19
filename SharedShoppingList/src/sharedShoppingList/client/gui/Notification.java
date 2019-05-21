package sharedShoppingList.client.gui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/*
 * Diese Klasse ersetzt störende Window-alerts und zeigt diese im Header an!
 */

public class Notification {

	private static Label notificationLabel = new Label();
	private static boolean isActive = false;

	/**
	 * Konstruktor
	 */
	public Notification() {

	}

	public static void clear() {
		if (isActive == true) {
			RootPanel.get("header").remove(RootPanel.get("header").getWidgetIndex(notificationLabel));

		}
	}

	/**
	 * Die Methode show zeigt die Fehlermeldung im Header an, welche nach 10
	 * Sekunden verschwindet.
	 * 
	 * @param message Die Nachricht, die den Fehler beschreibt, wird übergeben.
	 */
	public static void show(String message) {
		isActive = true;
		notificationLabel.setText(message);
		notificationLabel.setStyleName("errorLabel");
		RootPanel.get("header").add(notificationLabel);

		// timer definiert die Anzeigezeit des Fehlers.
		final Timer timer = new Timer() {
			public void run() {
				RootPanel.get("header").remove(RootPanel.get("header").getWidgetIndex(notificationLabel));
			}
		};
		// Definert die Sekunden, nachdem die Methode aufgerufen wird.
		timer.schedule(5000);
	}
}
