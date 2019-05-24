package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Formular fÃ¼r das Anlegen eines neuen HÃ¤ndlers im Datenstamm
 * 
 * @author moritzhampe
 *
 */

public class StoreForm extends AbstractAdministrationForm {

	FlexTable storeFlexTable;

	@Override
	protected String nameForm() {

		return "Storeverwaltung";
	}
	
	@Override
	protected TextBox createUnitTextBox() {
		return null;
	}

	

	@Override
	protected FlexTable createTable() {
		// TODO Auto-generated method stub

		if (storeFlexTable == null) {
			storeFlexTable = new FlexTable();
		}
		storeFlexTable.setText(0, 0, "Store");
		storeFlexTable.setText(1, 0, "Lidl");

		return storeFlexTable;
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
	 * Sobald das Textfeld ausgefüllt wurde, wird ein neuer Store nach dem Klicken
	 * des Bestätigungsbutton erstellt.
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
