package sharedShoppingList.shared;

import sharedShoppingList.client.gui.Notification;

/**
 * Diese Klasse dient der Übrprüfung von Inhalten dern Textfelder, in die der
 * Nutzer Eingaben trifft.
 */

public class FieldVerifier {

	/**
	 * Konstruktor
	 */
	public FieldVerifier() {
	}

	/**
	 * Überprüft, ob der eingegebene Text zulässig ist.
	 * 
	 * @return true oder false, ob der eingegebene Text zulässig ist
	 */
	public boolean checkValue(String propertyTerm, String text) {

		String identifier = propertyTerm;
		String value = text;

		if (text != null) {

			switch (identifier) {

			// Cases, die überprüft werden
			case "Gruppenname":
				if (value.matches("[a-zA-ZäöüÄÖÜß ]+")) {
					return true;
				} else if (value.matches("^\\s*$")) {
					Notification.show("Name darf nicht leer sein!");
					return false;
				} else {
					Notification.show("Ungültige Zeichen im Namen!");
					return false;
				}
			default:

				return true;
			}
		} else {
			return true;
		}

	}

	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * In this example, we only require that the name is at least three characters.
	 * In your application, you can use more complex checks to ensure that
	 * usernames, passwords, email addresses, URLs, and other fields have the proper
	 * syntax.
	 * 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidNameField(String name) {
		if (name == null) {
			return false;
		}
		return name.length() >= 3;
	}

}