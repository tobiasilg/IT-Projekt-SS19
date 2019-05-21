package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class ListCreationForm extends AbstractDialogCreationForm {

	@Override
	protected String nameDialogForm() {

		return "Neue Liste";
	}

	// Konstruktor
	public ListCreationForm() {

		saveButton.addClickHandler(new SaveListClickHandler());
		cancelButton.addClickHandler(new CancelListClickHandler());
	}

}

/**
 * Hiermit wird der Erstellvorgang einer neuen Gruppe abbgebrochen.
 * TODO: Clickhandler private setzen -> Fehler!
 */
class CancelListClickHandler implements ClickHandler {
	public void onClick(ClickEvent event) {
		RootPanel.get("Details").clear();
	}
}

/**
 * Sobald das Textfeld ausgefüllt wurde, wird ein neue Gruppe/neuer Liste nach
 * dem Klicken des Bestätigungsbutton erstellt.
 */
class SaveListClickHandler implements ClickHandler {
	public void onClick(ClickEvent event) {
	}
}

/**
 * Callback wird benötigt, um die Gruppe zu erstellen
 */
class ListCreationCallback implements AsyncCallback<Void> {

	public void onFailure(Throwable caught) {
		Notification.show("Die Gruppen konnte nicht erstellt werden");
	}

	public void onSuccess(Void event) {
		Notification.show("Die Gruppe wurde erfolgreich erstellt");
	}
}
