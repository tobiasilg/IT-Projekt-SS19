package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * 
 */

public class GroupCreationForm extends AbstractDialogCreationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	 User u= CurrentUser.getUser();

	@Override
	protected String nameDialogForm() {
		return "Neue Gruppe erstellen";
	}

	// Konstruktor
	public GroupCreationForm() {

		saveButton.addClickHandler(new SaveGroupCreationClickHandler());
		cancelButton.addClickHandler(new CancelGroupCreationClickHandler());
	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Gruppe abbgebrochen.
	 */
	private class CancelGroupCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neue Gruppe nach dem Klicken
	 * des addButton erstellt.
	 */
	private class SaveGroupCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.createGroup(insertNameTextBox.getText(), new GroupCreationCallback());

		}

	}

	/**
	 * Callback wird benötigt, um die Gruppe zu erstellen
	 */
	private class GroupCreationCallback implements AsyncCallback<Group> {
		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Group event) {
			Notification.show("Die Gruppe wurde erfolgreich erstellt");
		}
	}

}
