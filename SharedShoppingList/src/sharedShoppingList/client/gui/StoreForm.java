package sharedShoppingList.client.gui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Store;

/**
 * Formular für das Anlegen eines neuen Händlers im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class StoreForm extends VerticalPanel {

	private Label nameLabel = new Label("Storeverwaltung");
	private TextBox nameTextBox = new TextBox();

	private Button cancelButton = new Button("abbrechen");
//	private Button saveButton = new Button("Änderungen speichern");
	private Button addButton = new Button("hinzufügen");

	private HorizontalPanel hpCreate = new HorizontalPanel();

//	private ScrollPanel scrollPanel = new ScrollPanel();

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	// Create a data provider.
	private ListDataProvider<Store> dataProvider = new ListDataProvider<Store>();

	private List<Store> list = dataProvider.getList();

	// create Table
	private CellTable<Store> table = new CellTable<Store>(KEY_PROVIDER);

	/**
	 * The key provider that allows us to identify Contacts even if a field changes.
	 * We identify contacts by their unique ID.
	 */
	private static final ProvidesKey<Store> KEY_PROVIDER = new ProvidesKey<Store>() {
		@Override
		public Object getKey(Store store) {
			return store.getId();
		}
	};

	// Konstruktor
	public StoreForm() {

//		saveButton.addClickHandler(new SaveStoreClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddStoreClickHandler());
//		deleteButton.addClickHandler(new DeleteStoreClickHandler());
	}

	public void onKeyPress(KeyPressEvent event) {
		if (event.getCharCode() == KeyCodes.KEY_ENTER) {
			addButton.click();

		}

	}

	public void onLoad() {

		hpCreate.add(nameTextBox);
		hpCreate.add(addButton);
//		hpCreate.add(saveButton);
		hpCreate.add(cancelButton);
		cancelButton.addStyleName("deleteButton");
		addButton.addStyleName("saveButton");
		nameLabel.addStyleName("profilTitle");

//		scrollPanel.add(table);

//		scrollPanel.setHeight("12");

		this.add(nameLabel);
		this.add(hpCreate);

//
		this.add(table);

		this.setWidth("100%");
		this.setHeight("100");
		this.setHorizontalAlignment(ALIGN_CENTER);
		this.addStyleName("storeForm");
		// this.setCellHorizontalAlignment(hpCreate, ALIGN_CENTER);
		nameLabel.setWidth("100%");

		hpCreate.addStyleName("hpCreateStore");
		hpCreate.setWidth("100%");
		hpCreate.setHorizontalAlignment(ALIGN_CENTER);
		hpCreate.setStyleName("hpCreateStore");
		// nameLabel.addStyleName("storeFormLabel");
		nameTextBox.addStyleName("profilTextBox");
//		saveButton.addStyleName("speicherButton");
		// addButton.addStyleName("storeFormButton");

		// Lade alle Store aus der Datenbank
		elv.getAllStores(new AsyncCallback<Vector<Store>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Vector<Store> result) {

				// Add the data to the data provider, which automatically pushes it to the
				// widget.
				for (Store store : result) {

					list.add(store);

				}

			}
		});

		EditTextCell editTextCell = new EditTextCell();
		Column<Store, String> stringColumn = new Column<Store, String>(editTextCell) {
			@Override
			public String getValue(Store store) {

				return store.getName();
			}
		};

		ButtonCell deleteButton = new ButtonCell();
		Column<Store, String> deleteColumn = new Column<Store, String>(deleteButton) {
			public String getValue(Store object) {
				return "x";
			}

		};

		deleteColumn.setFieldUpdater(new FieldUpdater<Store, String>() {
			public void update(int index, Store store, String value) {
				// Value is the button value. Object is the row object.
//				

				EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

				AsyncCallback<Store> deletecallback = new AsyncCallback<Store>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Store result) {
						if (result == null) {
							Window.alert("Einzelhändler konnte nicht gelöscht werden, da er verwendet wird");

						} else {

							dataProvider.getList().remove(result);
							Notification.show("Händler wurde gelöscht");
							dataProvider.refresh();
						}
					}

				};
				elv.delete(store, deletecallback);
			}

		});

//				dataProvider.getList().remove(store);
//				dataProvider.refresh();
//				table.redraw();

		stringColumn.setFieldUpdater(new FieldUpdater<Store, String>() {
			public void update(int index, Store store, String value) {
				// Value is the textCell value. Object is the row object.
				store.setName(value);
				EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

				AsyncCallback<Void> saveCallback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						Notification.show("Artikel wurde gespeichert");
					}

				};

				elv.save(store, saveCallback);
			}

		});
		// Add the columns.
		table.addColumn(stringColumn, "Stores");
		table.addColumn(deleteColumn, "");
		table.setColumnWidth(stringColumn, 20, Unit.PC);

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(table);
	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des addButton erstellt.
	 */
	private class AddStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

//			// Persistiere in die Datenbank
			elv.createStore(nameTextBox.getValue(), new StoreCreationCallback());

		}

	}

	/**
	 * Callback wird benï¿½tigt, um den Artikel zu erstellen
	 */
	private class StoreCreationCallback implements AsyncCallback<Store> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Store konnte nicht erstellt werden");

		}

		@Override
		public void onSuccess(Store store) {
			Notification.show("Der Store wurde erfolgreich erstellt");

			nameTextBox.setText("");
			dataProvider.getList().add(store);
			dataProvider.refresh();
		}
	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Artikel abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
		}

	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des Bestï¿½tigungsbutton erstellt.
	 */
	private class DeleteStoreClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			Store store = dataProvider.getList().get(table.getKeyboardSelectedRow());

		}

		private class DeleteStoreCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show(caught.toString());
				Window.alert("speichern fehlgeschlagen");

			}

			@Override
			public void onSuccess(Void result) {
				Notification.show("Store wurde entfernt");
//				table.getKeyboardSelectedRow();
//
//				dataProvider.getList().remove(table.getKeyboardSelectedRow());

				dataProvider.refresh();
			}

		}
	}

}