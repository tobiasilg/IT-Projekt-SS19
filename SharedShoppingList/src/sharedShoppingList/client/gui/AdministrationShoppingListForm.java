package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * 
 */

public class AdministrationShoppingListForm extends AbstractDialogCreationForm {

	User u = CurrentUser.getUser();
	protected Button deleteButton = new Button("Loeschen");

	@Override
	protected String nameDialogForm() {
		return "Shoppingliste verwalten";
	}

	protected String nameSecondDialogForm() {
		return null;
	}

	protected String nameThirdDialogForm() {
		return null;
	}

	protected FlexTable createTable() {
		return null;
	}

	protected Button addButton() {
		return null;
	}

	protected Button deleteButton() {
		return null;
	}

	protected HorizontalPanel createHpFirstButtonPanel() {
		return null;
	}

	protected TextBox addUsersTextBox() {
		return null;
	}

	// Konstruktor
	public AdministrationShoppingListForm() {

		hpButtonPanel.add(deleteButton);

		deleteButton.addClickHandler(new DeleteShoppingListClickhandler());
		saveButton.addClickHandler(new SaveAdministrationShoppingListClickhandler());
		cancelButton.addClickHandler(new CancelAdministrationShoppingListClickHandler());

	}

	/*
	 * Methode <code>onLoad</code> wird benötigt, sodass im Textfeld bereits der
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
	class CancelAdministrationShoppingListClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	/**
	 * Info: getGroupMethode fehlt noch Sobald das Textfeld ausgef�llt wurde, wird
	 * ein neue Gruppe nach dem Klicken des addButton erstellt.
	 */
	private class SaveAdministrationShoppingListClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			//elv.save(insertNameTextBox, new RenameShoppingListCallback());
		}
	}

	/*
	 * Hiermit wird durch das Drücken des LöschButtons die ShoppingListe gelöscht.
	 */
	private class DeleteShoppingListClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		//	elv.delete(group, new RenameShoppingListCallback());

		}

	}

	/*
	 * !!!!!!!!!!!! CALLBACK !!!!!!!!!!!!
	 */

	/**
	 * Callback wird benötigt, um die Shoppingliste umzubenennen
	 */
	class RenameShoppingListCallback implements AsyncCallback<ShoppingList> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Shoppingliste konnte nicht umbenannt werden");
		}

		public void onSuccess(ShoppingList shoppinglist) {
			Notification.show("Die Shoppingliste wurde erfolgreich umbenannt");
		}
	}

	/*
	 * Callback wird benötigt, um die Shoppingliste zu löschen.
	 */
	private class DeleteShoppingListCallback implements AsyncCallback<ShoppingList> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Shoppingliste konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(ShoppingList shoppinglist) {
			Notification.show("Die Shoppingliste wurde erfolgreich gelöscht");
		}
	}

	@Override
	protected SuggestBox suggestUser() {
		// TODO Auto-generated method stub
		return null;
	}
}
