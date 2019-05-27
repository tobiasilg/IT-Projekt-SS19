package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * 
 */

public class ListCreationForm extends AbstractDialogCreationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User u= CurrentUser.getUser();

	@Override
	protected String nameDialogForm() {
		return "Neue Liste erstellen";
	}

	// Konstruktor
	public ListCreationForm() {

		saveButton.addClickHandler(new SaveListCreationClickHandler());
		cancelButton.addClickHandler(new CancelListCreationClickHandler());
	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Liste abbgebrochen.
	 */
	private class CancelListCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neue Liste nach dem Klicken
	 * des addButton erstellt.
	 */
	private class SaveListCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.createShoppingList(insertNameTextBox.getText(), new ListCreationCallback());

		}

	}

	/**
	 * Callback wird benötigt, um eine Liste zu erstellen
	 */
	private class ListCreationCallback implements AsyncCallback<ShoppingList> {
		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(ShoppingList shoppingList) {
			Notification.show("Die Gruppe wurde erfolgreich erstellt");
		}
	}

}
