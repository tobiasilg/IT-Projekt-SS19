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

		saveButton.addClickHandler(new CreateListClickHandler());
		hpButtonPanel.add(saveButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		hpButtonPanel.add(cancelButton);

	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Gruppe abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgefüllt wurde, wird ein neue Gruppe nach dem Klicken
	 * des Bestätigungsbutton erstellt.
	 */
	private class CreateListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Callback wird benötigt, um die Liste zuu erstellen
	 */
	private class ListCreationCallback implements AsyncCallback<Void> {
	
	@Override
	public void onFailure(Throwable caught) {
		Notification.show("Die Liste konnte nicht erstellt werden");
	}
	
	@Override
	public void onSuccess(Void event) {
		Notification.show("Die Liste wurde erfolgreich erstellt");
	}
	}

}