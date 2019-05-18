
package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.shared.FieldVerifier;

/**
 * Formular für das Anlegen einer neuen Gruppe
 * 
 *
 */

public class GroupCreationForm extends AbstractDialogCreationForm {

	@Override
	protected String nameDialogForm() {

		return "neue Gruppe";
	}

	// Konstruktor
	public GroupCreationForm() {

		saveButton.addClickHandler(new CreateGroupClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		

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
	 * Sobald das Textfeld ausgefüllt wurde, wird ein neue Gruppe/neuer Liste nach
	 * dem Klicken des Bestätigungsbutton erstellt.
	 */
	private class CreateGroupClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Callback wird benötigt, um die Gruppe zu erstellen
	 */
	private class GroupCreationCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Void event) {
			Notification.show("Die Gruppe wurde erfolgreich erstellt");
		}
	}

}