package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.client.gui.AdministrationShoppingListForm.RenameShoppingListCallback;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * 
 */

public class AdministrationGroupForm extends AbstractDialogCreationForm {

	protected Button deleteButton = new Button("Loeschen");
	User u = CurrentUser.getUser();

	@Override
	protected String nameDialogForm() {
		return "Gruppe umbenennen";
	}

	// Konstruktor
	public AdministrationGroupForm() {

		hpButtonPanel.add(deleteButton);

		deleteButton.addClickHandler(new DeleteGroupClickhandler());
		saveButton.addClickHandler(new SaveRenameGroupClickhandler());
		cancelButton.addClickHandler(new CancelRenameGroupClickHandler());
	}

	/*
	 * Methode <code> onLoad </code> wird benötigt, sodass im Textfeld bereits der
	 * Gruppenname stteht.
	 */
	public void onLoad() {
//		usernameTextBox.getElement().setPropertyString("placeholder", "Dein Username: " + user.getUserName());
	}

	/*
	 * !!!!!!!!!!!! CLICKHANDLER !!!!!!!!!!!!
	 */

	/**
	 * Hiermit wird der Vorgang, die Gruppe umzubenennen, abgebrochen.
	 */
	class CancelRenameGroupClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	/**
	 * Info: getGroupMethode fehlt noch Sobald das Textfeld ausgef�llt wurde, wird
	 * ein neue Gruppe nach dem Klicken des addButton erstellt.
	 */
	private class SaveRenameGroupClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// elv.renameGroup(insertNameTextBox, new RenameGroupCallback());

		}

	}

	/*
	 * Hiermit wird durch das Drücken des LöschButtons die ShoppingListe gelöscht.
	 */
	private class DeleteGroupClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.delete(group, new RenameShoppingListCallback());

		}

	}

	/*
	 * !!!!!!!!!!!! CALLBACK !!!!!!!!!!!!
	 */

	/**
	 * Callback wird benötigt, um die Gruppe umzubenennen
	 */
	class RenameGroupCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht umbenannt werden");
		}

		public void onSuccess(Void event) {
			Notification.show("Die Gruppe wurde erfolgreich umbenannt");
		}
	}

	/*
	 * Callback wird benötigt, um die Gruppe zu löschen.
	 */
	private class DeleteGroupCallback implements AsyncCallback<ShoppingList> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(ShoppingList shoppinglist) {
			Notification.show("Die Gruppe wurde erfolgreich gelöscht");
		}
	}
}
