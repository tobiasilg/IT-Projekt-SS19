package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Store;

/**
 * Formular für das Anlegen eines neuen Händlers im Datenstamm
 * 
 * @author moritzhampe
 *
 */

public class StoreForm extends AbstractAdministrationForm {

	FlexTable storeFlexTable;
	
	EinkaufslistenverwaltungAsync elv= ClientsideSettings.getEinkaufslistenverwaltung();

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
			RootPanel.get("details").clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Store nach dem Klicken
	 * des Best�tigungsbutton erstellt.
	 */
	private class SaveStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			for (int i = 1; i <= storeFlexTable.getRowCount(); i++) {
				
				/*
				 * save und add( create) zu klären
				 */
				
				elv.createStore(storeFlexTable.getText(i, 0), new StoreCreationCallback());
			}
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
	private class StoreCreationCallback implements AsyncCallback<Store> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Store store) {
			Notification.show("Der Store wurde erfolgreich erstellt");
		}

	}

}
