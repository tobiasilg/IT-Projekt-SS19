package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Formular für das Anlegen eines neuen Händlers im Datenstamm
 * 
 * @author moritzhampe
 *
 */

public class StoreForm extends AbstractAdministrationForm {

	FlexTable storeFlexTable;

	ArrayList<String> stores = new ArrayList<>();

	@Override
	protected String nameForm() {

		return "Storeverwaltung";
	}

	@Override
	protected ListBox createUnitListBox() {
		return null;
	}

	@Override
	protected FlexTable createTable() {
		// TODO Auto-generated method stub

		if (storeFlexTable == null) {
			storeFlexTable = new FlexTable();
		}
		storeFlexTable.setText(0, 0, "Store");

		return storeFlexTable;
	}

	// Konstruktor
	public StoreForm() {

		saveButton.addClickHandler(new SaveStoreClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddStoreClickHandler());

	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Store abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get().clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Store nach dem Klicken
	 * des Best�tigungsbutton erstellt.
	 */
	private class SaveStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
//			for (int i = 1; i <= storeFlexTable.getRowCount(); i++) {
//				elv.saveStore(storeFlexTable.getText(i, 0));
//			}
		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Store nach dem Klicken
	 * des addButton erstellt.
	 */
	private class AddStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			final String store = nameTextBox.getValue();

			if (stores.contains(store)) {
				return;
			}

			stores.add(store);
			int rowCount = storeFlexTable.getRowCount();
			storeFlexTable.setText(rowCount, 0, store);

			Button removeButton = new Button("x");

			removeButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					final int removedIndex = stores.indexOf(store);
					stores.remove(removedIndex);
					storeFlexTable.removeRow(removedIndex + 1);
				}

			});

			storeFlexTable.setWidget(rowCount, 1, removeButton);
		}
	}

	/**
	 * Callback wird ben�tigt, um den Store zu erstellen
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
