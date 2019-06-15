package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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

	ArrayList<CustomTextBox> textboxes;

	Store newStore;

	public Store getNewStore() {
		return newStore;
	}

	public void setNewStore(Store newStore) {
		this.newStore = newStore;
	}

	// Konstruktor
	public StoreForm() {

		saveButton.addClickHandler(new SaveStoreClickHandler(textboxes));
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

		}
		textboxes = new ArrayList<CustomTextBox>();
		textboxes.clear();
		storeFlexTable.removeAllRows();
		storeFlexTable.setText(0, 0, "Store");

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

		CustomTextBox storeTextBox = new CustomTextBox();
		storeTextBox.setValue(store.getName());
		//
		storeTextBox.setStore(store);
		textboxes.add(storeTextBox);

		// Erstelle x Button
		CustomButton removeButton = new CustomButton();
		removeButton.setStore(store);

		removeButton.addClickHandler(new DeleteStoreClickHandler(removeButton));

		storeFlexTable.setWidget(rowCount, 0, storeTextBox);
		storeFlexTable.setWidget(rowCount, 1, removeButton);

	}

	/**
	 * Hiermit wird der Erstellvorgang eine neuen Stores abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgef?llt wurde, wird ein neuer Store nach dem Klicken
	 * des addButton erstellt.
	 */
	private class AddStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// Persistiere in die Datenbank
			elv.createStore(nameTextBox.getValue(), new StoreCreationCallback());

			// Erstelle neues Store Objekt
			setContentOfStoreFlexTable(newStore);

		}

	}

	/**
	 * Callback wird ben?tigt, um den Store zu erstellen
	 */
	private class StoreCreationCallback implements AsyncCallback<Store> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Store store) {
			Notification.show("Der Store wurde erfolgreich erstellt");

			createTable();

			// Klappt noch nicht
			setNewStore(store);
		}

	}

	private class CustomButton extends Button {
		Store store;

		public Store getStore() {
			return store;
		}

		public void setStore(Store store) {
			this.store = store;
		}
	}

	private class DeleteStoreClickHandler implements ClickHandler {

		private CustomButton cB;

		public DeleteStoreClickHandler(CustomButton cB) {

			this.cB = cB;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (cB != null) {

				elv.delete(cB.getStore(), new DeleteStoreCallback());

			} else {
				Notification.show("Der Store konnte nicht gelöscht werden");
			}
		}
	}

	private class CustomTextBox extends TextBox {
		Store store;

		public Store getStore() {
			return store;
		}

		public void setStore(Store store) {
			this.store = store;
		}
	}

	private class SaveStoreClickHandler implements ClickHandler {

		private ArrayList<CustomTextBox> list;

		public SaveStoreClickHandler(ArrayList<CustomTextBox> list) {

			this.list = list;
		}

		@Override
		public void onClick(ClickEvent event) {

			for (CustomTextBox textbox : textboxes) {

				textbox.getStore().setName(textbox.getValue());

				Window.alert("TextBox Objekt: " + textbox.getStore());
				Window.alert("Store name: " + textbox.getStore().getName());
				Window.alert("Store ID: " + textbox.getStore().getId());
				elv.save(textbox.getStore(), new SaveStoreCallback());
//				Notification.show("Der Store konnte nicht gespeichert werden");

			}
		}
	}

	private class DeleteStoreCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			stores.clear();
			createTable();
		}
	}

	private class SaveStoreCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {
//			Window.alert();

		}

	}

}