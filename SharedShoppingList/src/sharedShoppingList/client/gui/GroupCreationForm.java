package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;

public class GroupCreationForm extends AbstractDialogCreationForm {

	protected String nameDialogForm() {
		return "Neue Gruppe";
	}

	// Konstruktor
	public GroupCreationForm() {

		saveButton.addClickHandler(new SaveGroupClickHandler());
		cancelButton.addClickHandler(new CancelGroupClickHandler());
	}

}

/**
 * Hiermit wird der Erstellvorgang einer neuen Gruppe abbgebrochen.
 */
class CancelGroupClickHandler implements ClickHandler {

	public void onClick(ClickEvent event) {
		RootPanel.get("Details").clear();
	}
}

/**
 * Sobald das Textfeld ausgefüllt wurde, wird ein neue Gruppe/neuer Liste nach
 * dem Klicken des Bestätigungsbutton erstellt.
 */
class SaveGroupClickHandler implements ClickHandler {

	public void onClick(ClickEvent event) {

	}
}

/**
 * Callback wird benötigt, um die Gruppe zu erstellen
 */
class GroupCreationCallback implements AsyncCallback<Group> {

	public void onFailure(Throwable caught) {
		Notification.show("Die Gruppen konnte nicht erstellt werden");
	}

	public void onSuccess(Group event) {
		Notification.show("Die Gruppe wurde erfolgreich erstellt");
	}
}
