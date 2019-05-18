package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;





/**
 * Formular für das Anlegen eines neuen Händlers im Datenstamm
 * @author moritzhampe
 *
 */

public class StoreForm extends AbstractAdministrationForm{
	
	@Override
	protected String nameForm() {

		return "Storeverwaltung";
	}

	// Konstruktor
	public StoreForm() {

		saveButton.addClickHandler(new CreateStoreClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddStoreClickHandler());

	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Store abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgefüllt wurde, wird ein neuer Store nach
	 * dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Sobald das Textfeld ausgefüllt wurde, wird ein neuer Store nach dem Klicken
	 * des addButton erstellt.
	 */
	private class AddStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Callback wird benötigt, um den Store zu erstellen
	 */
	private class StoreCreationCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Void event) {
			Notification.show("Der Store wurde erfolgreich erstellt");
		}
	}
	
	

}
