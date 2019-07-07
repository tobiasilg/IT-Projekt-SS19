package sharedShoppingList.client.gui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

public class FilterShoppingList extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	private final MultiSelectionModel<ListEntry> multiSelectionModel = new MultiSelectionModel<ListEntry>();

	private User selectedUser;
	private Group selectedGroup = null;
	private ShoppingList selectedShoppingList = null;
	private ListEntry selectedListEntry = null;
	private ShoppingListForm selectedShoppingListForm = null;
	private User user = CurrentUser.getUser();
	ShoppingListForm slf;



	private Label infoTitleLabel = new Label("Eigene Eintraege");

	// private Button saveSlButton = new Button("Änderungen speichern");
	// private Button deleteSlButton = new Button("Einkaufsliste löschen");
	// private Button createShoppingListButton = new Button("Listeneintrag
	// erstellen");

	private Button cancelButton = new Button("Alle Eintraege");




	private HorizontalPanel firstRowPanel = new HorizontalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	private VerticalPanel cellTableVP = new VerticalPanel();

	// private TextBox renameTextBox = new TextBox();

	// Create a data provider.
	private ListDataProvider<ListEntry> dataProvider = new ListDataProvider<ListEntry>();
	private List<ListEntry> list = dataProvider.getList();
	private CellTable<ListEntry> cellTable = new CellTable<ListEntry>(KEY_PROVIDER);

	/**
	 * The key provider that allows us to identify Contacts even if a field changes.
	 * We identify contacts by their unique ID.
	 */
	private static final ProvidesKey<ListEntry> KEY_PROVIDER = new ProvidesKey<ListEntry>() {
		public Object getKey(ListEntry le) {
			return le.getId();
		}
	};

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */

	public FilterShoppingList() {

//		saveSlButton.addClickHandler(new RenameShoppingListClickHandler());
//		deleteSlButton.addClickHandler(new DeleteShoppingListClickHandler());
//		createShoppingListButton.addClickHandler(new CreateShoppingListClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());

//		renameTextBox.getElement().setPropertyString("placeholder", "Einkaufsliste umbenennen...");
//		renameTextBox.setWidth("15rem");

		// Panel mit Button zum erzeugen eines neuen Listeneintrags

		// Panel der obersten Ebene
		firstRowPanel.add(infoTitleLabel);

		// Panel der Buttons
		// buttonPanel.add(renameTextBox);
//		buttonPanel.add(saveSlButton);
//		buttonPanel.add(deleteSlButton);

		filterPanel.add(cancelButton);

//		filterPanel.add(filterByStoreListBox);
		cancelButton.addStyleName("createButton");
		infoTitleLabel.addStyleName("profilTitle");
		

		// CellTable
		cellTableVP.add(cellTable);
		cellTableVP.setBorderWidth(1);
		cellTableVP.setWidth("400");

		infoTitleLabel.addStyleName("profilTitle");
		filterPanel.setCellHorizontalAlignment(filterPanel, ALIGN_RIGHT);

		this.add(firstRowPanel);
//		this.add(createShoppingListButton);
		this.add(buttonPanel);
		this.add(filterPanel);
		this.add(cellTable);

//		renameTextBox.addKeyPressHandler(new KeyPressHandler() {
//
//			@Override
//			public void onKeyPress(KeyPressEvent event) {
//				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
//					saveSlButton.click();
//					renameTextBox.setText("");
//				}
//
//			}
//
//		});

		/***********************************************************************
		 * Erstellung der Celltable mit Columns
		 ***********************************************************************
		 */

		/*
		 * Spalte der CheckBox
		 */

		Column<ListEntry, Boolean> checkBoxColumn = new Column<ListEntry, Boolean>(new CheckboxCell(true, false)) {

			public Boolean getValue(ListEntry object) {
				return object.isChecked();
			}
		};

		/*
		 * Spalte der Artikel
		 */
		TextCell articleTextCell = new TextCell();
		Column<ListEntry, String> articleColumn = new Column<ListEntry, String>(articleTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getArticle().getName();
			}
		};

		/*
		 * Spalter der Menge
		 */

		TextCell amountTextCell = new TextCell();
		Column<ListEntry, String> amountColumn = new Column<ListEntry, String>(amountTextCell) {

			public String getValue(ListEntry listEntry) {
				return String.valueOf(listEntry.getAmount());
			}
		};

		/*
		 * Spalte der Einheit
		 */

		TextCell unitTextCell = new TextCell();
		Column<ListEntry, String> unitColumn = new Column<ListEntry, String>(unitTextCell) {

			public String getValue(ListEntry listEntry) {
				return String.valueOf(listEntry.getArticle().getUnit());
			}
		};

		/*
		 * Spalter der Stores
		 */

		TextCell storeTextCell = new TextCell();
		Column<ListEntry, String> storeColumn = new Column<ListEntry, String>(storeTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getStore().getName();

			}
		};

		/*
		 * Spalter der user
		 */

		TextCell userTextCell = new TextCell();
		Column<ListEntry, String> userColumn = new Column<ListEntry, String>(userTextCell) {

			public String getValue(ListEntry listEntry) {

				return listEntry.getUser().getName();
			}
		};
		/*
		 * Spalter des Lösch-Buttons
		 */

		ButtonCell deleteButton = new ButtonCell();
		Column<ListEntry, String> deleteColumn = new Column<ListEntry, String>(deleteButton) {

			public String getValue(ListEntry listEntry) {
				return "x";
			}
		};

		deleteColumn.setFieldUpdater(new FieldUpdater<ListEntry, String>() {

			public void update(int index, ListEntry listEntry, String value) {
				// Value is the button value. Object is the row object.

				dataProvider.getList().remove(listEntry);

				AsyncCallback<Void> deleteCallback = new AsyncCallback<Void>() {

					public void onFailure(Throwable caught) {

					}

					public void onSuccess(Void result) {

					}

				};

				Window.alert("Listeneintrag löschen" + listEntry);
				elv.delete(listEntry, deleteCallback);
			}
		});

		// Die Spalten werden hier der CellTable hinzugefügt
		cellTable.addColumn(checkBoxColumn, "Erledigt?");
		cellTable.addColumn(articleColumn, "Artikel");
		cellTable.addColumn(amountColumn, "Menge");
		cellTable.addColumn(unitColumn, "Einheit");
		cellTable.addColumn(userColumn, "Wer?");
		cellTable.addColumn(storeColumn, "Wo?");
		cellTable.addColumn(deleteColumn, "");

		dataProvider.addDataDisplay(cellTable);

	}

	/***********************************************************************
	 * onLoad-Methode
	 ***********************************************************************
	 */
	public void onLoad() {

		selectedUser = user;

		elv.filterByUser(selectedUser, gsltvm.getSelectedList(), new AsyncCallback<Vector<ListEntry>>() {

			public void onFailure(Throwable caught) {
				Window.alert("");

			}

			public void onSuccess(Vector<ListEntry> listEntry) {

				for (ListEntry le : listEntry) {
					list.add(le);
				}
			}
		});
	}

	private class CreateShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			RootPanel.get("details").clear();
			NewListEntryForm nlef = new NewListEntryForm();
			RootPanel.get("details").add(nlef);

		}

		/***********************************************************************
		 * Setter und Getter
		 ***********************************************************************
		 */

	}

	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;
	}

	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public ShoppingList getSelectedShoppingList() {
		return selectedShoppingList;
	}

	public void setSelectedShoppingList(ShoppingList selectedShoppingList) {
		this.selectedShoppingList = selectedShoppingList;
	}

	public ShoppingListForm getSelectedShoppingListForm() {
		return selectedShoppingListForm;
	}

	public void setSelectedShoppingListForm(ShoppingListForm slf) {
		this.selectedShoppingListForm = slf;
	}

	public void setSelectedUser(User u) {
		selectedUser = u;

	}

	public User getSelectedUser() {
		return selectedUser;
	}

	/***********************************************************************
	 * Clickhandler
	 ***********************************************************************
	 */

	/*
	 * Clickhandler um wieder zurück auf alle Listeneinträge zu springen
	 */
	private class CancelClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			if (selectedShoppingList != null) {
				RootPanel.get("details").clear();

				selectedShoppingListForm.setSelected(selectedGroup);
				
				RootPanel.get("details").add(selectedShoppingListForm);
				
				

			}
		}
	}

//	private class RenameShoppingListClickHandler implements ClickHandler {
//
//		public void onClick(ClickEvent event) {
//			if (renameTextBox.getValue() == "") {
//				Window.alert("Die Einkaufsliste muss einen Namen besitzen!");
//
//			} else {
//				selectedShoppingList.setName(renameTextBox.getValue());
//				elv.save(selectedShoppingList, new RenameShoppingListCallback());
//			}
//
//		}
//	}

	private class DeleteShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			DeleteShoppingListDialogBox deleteShoppingListDialogBox = new DeleteShoppingListDialogBox();
			deleteShoppingListDialogBox.center();
		}
	}

	private class DeleteShoppingListDialogBox extends DialogBox {
		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Bist Du Dir sicher die Einkaufsliste zu löschen?");
		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/*
		 * Konstruktor
		 */

		public DeleteShoppingListDialogBox() {
			abfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			jaButton.addClickHandler(new JaDeleteListClickHandler(this));
			neinButton.addClickHandler(new NeinDeleteListClickHandler(this));
		}
	}

	private class JaDeleteListClickHandler implements ClickHandler {

		private DeleteShoppingListDialogBox deleteShoppingListDialogBox;

		public JaDeleteListClickHandler(DeleteShoppingListDialogBox deleteShoppingListDialogBox) {
			this.deleteShoppingListDialogBox = deleteShoppingListDialogBox;

		}

		public void onClick(ClickEvent event) {
			this.deleteShoppingListDialogBox.hide();

			elv.delete(selectedShoppingList, new JaDeleteListCallback());
		}
	}

	private class NeinDeleteListClickHandler implements ClickHandler {

		private DeleteShoppingListDialogBox deleteShoppingListDialogBox;

		public NeinDeleteListClickHandler(DeleteShoppingListDialogBox deleteShoppingListDialogBox) {
			this.deleteShoppingListDialogBox = deleteShoppingListDialogBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.deleteShoppingListDialogBox.hide();

		}

	}

	/***********************************************************************
	 * Callbacks
	 ***********************************************************************
	 */

	private class JaDeleteListCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {

			Notification.show("Die Einkaufsliste konnte nicht gelöscht werden");

		}

		public void onSuccess(Void result) {

			Notification.show("Die Einkaufsliste wurde erfolgreich gelöscht");

			gsltvm.removeShoppingListOfGroup(selectedShoppingList, selectedGroup);

		}
	}

	private class RenameShoppingListCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Einkaufsliste konnte nicht umbenannt werden");

		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Einkaufsliste wurde erfolgreich umbenannt");

			gsltvm.updateShoppingList(selectedShoppingList);

		}

	}

}
