package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Store;

/**
 * Formular für das Anlegen eines neuen Händlers im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class StoreForm extends AbstractAdministrationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	FlexTable storeFlexTable;

	ArrayList<Store> stores;

	// Konstruktor
	public StoreForm() {

		saveButton.addClickHandler(new SaveStoreClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddStoreClickHandler());

	}

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
		elv = ClientsideSettings.getEinkaufslistenverwaltung();

		if (storeFlexTable == null) {
			storeFlexTable = new FlexTable();

			storeFlexTable.setText(0, 0, "Store");
		}

		stores = new ArrayList<Store>();

		// Lade alle Store aus der Datenbank
		elv.getAllStores(new AsyncCallback<Vector<Store>>() {
			@Override
			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			@Override
			public void onSuccess(Vector<Store> result) {
				// Füge alle Elemente der Datenbank in die Liste hinzu
				for (Store store : result) {
					stores.add(store);
					setContentOfStoreFlexTable(store);
				}
				Notification.show("success");

			}
		});

		return storeFlexTable;
	}

	private void setContentOfStoreFlexTable(Store store) {
		// Hole Zeilennummer, die aktuell bearbeitet wird
		int rowCount = storeFlexTable.getRowCount();

		// Erstelle neue Textbox für eigetragenen Store und setze den Namen
		TextBox storeTextBox = new TextBox();
		storeTextBox.setText(store.getName());

		// Erstelle x Button
		Button removeButton = new Button("x");
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//			PROBLEM WEIL STORE NICHT FINAL IST 
//				final int removedIndex = stores.indexOf(store);
//				stores.remove(removedIndex);
//				storeFlexTable.removeRow(removedIndex + 1);
			}
		});

		storeFlexTable.setWidget(rowCount, 0, storeTextBox);
		storeFlexTable.setWidget(rowCount, 3, removeButton);

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
			for (Store store : stores) {
				elv.save(store, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onSuccess(Void result) {
						Notification.show("successfilly saved");

					}
				});
			}
		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Store nach dem Klicken
	 * des addButton erstellt.
	 */
	private class AddStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// Erstelle neues Store Objekt
			Store store = new Store();
			store.setName(nameTextBox.getValue());

			setContentOfStoreFlexTable(store);

			// Persistiere in die Datenbank
			elv.createStore(store.getName(), new StoreCreationCallback());
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
