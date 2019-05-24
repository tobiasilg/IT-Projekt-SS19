package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.shared.bo.ShoppingList;

public class RenameListForm extends AbstractDialogCreationForm {

	protected String nameDialogForm() {
		return "Liste umbenennen";
	}

	// Konstruktor
	public RenameListForm() {

		saveButton.addClickHandler(new SaveRenameListClickhandler());
		cancelButton.addClickHandler(new CancelRenameListClickHandler());
	}

}

/**
 * Hiermit wird der Erstellvorgang einer neuen Gruppe abbgebrochen.
 */
 class CancelRenameListClickHandler implements ClickHandler {
	public void onClick(ClickEvent event) {
		RootPanel.get("Details").clear();
	}
}

/**
 * Sobald das Textfeld ausgefüllt wurde, wird ein neue Gruppe/neuer Liste nach
 * dem Klicken des Bestätigungsbutton erstellt.
 */
 class SaveRenameListClickhandler implements ClickHandler {
	public void onClick(ClickEvent event) {
	}
}

/**
 * Callback wird benötigt, um die Gruppe zu erstellen
 */
 class RenameListCallback implements AsyncCallback<ShoppingList> {

	public void onFailure(Throwable caught) {
		Notification.show("Die Liste konnte nicht umbenannt werden");
	}

	public void onSuccess(ShoppingList event) {
		Notification.show("Die Liste wurde erfolgreich umbenannt");
	}
}
