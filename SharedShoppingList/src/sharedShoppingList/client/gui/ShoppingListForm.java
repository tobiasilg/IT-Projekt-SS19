package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * 
 * Die Klasse <code>ShoppingListForm</code> erstellt das Formular zur Anzeige
 * einer Shoppingliste mit Artikel, Artikelanzahl, Unit, wer und wo der Artikel
 * gekauft wird etc..
 * 
 * @author nicolaifischbach & Moritz Hampe
 *
 */

public class ShoppingListForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	private final MultiSelectionModel<ListEntry> multiSelectionModel = new MultiSelectionModel<ListEntry>();

	private Group selectedGroup = null;
	private ShoppingList selectedShoppingList = null;
	private ListEntry selectedListEntry = null;
	private ShoppingListForm slf = null;

	// private Vector<Vector<Object>> entries = new Vector<Vector<Object>>();

	private Label infoTitleLabel = new Label();

	private Button saveSlButton = new Button("Änderungen speichern");
	private Button deleteSlButton = new Button("Einkaufsliste löschen");
	private Button createShoppingListButton = new Button("Listeneintrag erstellen");
	private Button filterByUserButton = new Button("Filtern nach User");
	private ListBox filterByStoreListBox = new ListBox ();

	private HorizontalPanel firstRowPanel = new HorizontalPanel ();
	private HorizontalPanel filterPanel =  new HorizontalPanel ();
	private FlowPanel buttonPanel = new FlowPanel();
	private VerticalPanel cellTableVP = new VerticalPanel();

	private TextBox renameTextBox = new TextBox();

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

	public ShoppingListForm() {

		saveSlButton.addClickHandler(new RenameShoppingListClickHandler());
		deleteSlButton.addClickHandler(new DeleteShoppingListClickHanlder());
		createShoppingListButton.addClickHandler(new CreateShoppingListClickHandler());
		filterByUserButton.addClickHandler(new FilterByUserClickHandler());
		//filterByStore.addClickHandler(new FilterByStoreClickHandler());

		renameTextBox.getElement().setPropertyString("placeholder", "Einkaufsliste umbenennen...");
		renameTextBox.setWidth("15rem");

		// Panel mit Button zum erzeugen eines neuen Listeneintrags

		
		// Panel der obersten Ebene
		firstRowPanel.add(infoTitleLabel);
			
		// Panel der Buttons
		buttonPanel.add(renameTextBox);
		buttonPanel.add(saveSlButton);
		buttonPanel.add(deleteSlButton);
		
		filterPanel.add(filterByUserButton);
		filterPanel.add(filterByStoreListBox);

		// CellTable
		cellTableVP.add(cellTable);
		cellTableVP.setBorderWidth(1);
		cellTableVP.setWidth("400");

		infoTitleLabel.addStyleName("profilTitle");
		filterPanel.setCellHorizontalAlignment(filterPanel, ALIGN_RIGHT);
		

		this.add(firstRowPanel);
		this.add(createShoppingListButton);
		this.add(buttonPanel);
		this.add(filterPanel);
		this.add(cellTable);
		

		renameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveSlButton.click();
					renameTextBox.setText("");
				}

			}

		});

		/***********************************************************************
		 * Erstellung Celltable
		 ***********************************************************************
		 */

		/*
		 * Spalte der CheckBox
		 */

		Column<ListEntry, Boolean> checkBoxColumn = new Column<ListEntry, Boolean>(new CheckboxCell(true, false)) {

			public Boolean getValue(ListEntry object) {
				return multiSelectionModel.isSelected(object);
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
		 * Spalte der Einheit
		 */

		TextCell unitTextCell = new TextCell();
		Column<ListEntry, String> unitColumn = new Column<ListEntry, String>(unitTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getArticle().getUnit();
			}
		};

		/*
		 * Spalte der Menge
		 */

		TextCell amountTextCell = new TextCell();
		Column<ListEntry, String> amountColumn = new Column<ListEntry, String>(amountTextCell) {

			public String getValue(ListEntry listEntry) {
				return String.valueOf(listEntry.getAmount());
			}
		};

		/*
		 * Spalte der Stores
		 */

		TextCell storesTextCell = new TextCell();
		Column<ListEntry, String> storeColumn = new Column<ListEntry, String>(storesTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getStore().getName();
			}
		};

		/*
		 * Spalte der User
		 */

		TextCell userTextCell = new TextCell();
		Column<ListEntry, String> userColumn = new Column<ListEntry, String>(userTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getUser().getName();
			}
		};
	
		/*
		 * Spalte des Lösch-Buttons
		 */
		ButtonCell deleteButton = new ButtonCell();
			Column<ListEntry, String> deleteColumn = new Column <ListEntry, String>(deleteButton){
			
			public String getValue (ListEntry listEntry) {
				return "x";
			}
			
		};
		
		deleteColumn.setFieldUpdater (new FieldUpdater <ListEntry, String>(){
			
			public void update (int index, ListEntry listEntry, String value) {
				// Value is the button value. Object is the row object.

				dataProvider.getList().remove(listEntry);
				
				AsyncCallback <Void> deleteCallback = new AsyncCallback<Void>() {
					
					public void onFailure (Throwable caught) {
						
					}
					
					public void onSuccess (Void result) {
						
					}
					
				};
				
				Window.alert ("Listeneintrag löschen" + listEntry);
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
//		
//
//		checkBoxColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
////
////		// Add selection to table
////		cellTable.setSelectionModel(multiSelectionModel,
//				DefaultSelectionEventManager.<Vector<Object>>createCheckboxManager());

	}

	/***********************************************************************
	 * onLoad Methode
	 ***********************************************************************
	 */

	public void onLoad() {

		elv.getAllListEntriesByShoppingList(selectedShoppingList, new AsyncCallback<Vector<ListEntry>>() {

			public void onFailure(Throwable caught) {
				Window.alert("");
			}

			public void onSuccess(Vector<ListEntry> listEntry) {

				for (ListEntry le : listEntry) {
					list.add(le);
				}

			}
		});
		
		/***********************************************************************
		 * Erstellung Celltable
		 ***********************************************************************
		 */

		/*
		 * Spalte der CheckBox
		 */

		Column<ListEntry, Boolean> checkBoxColumn = new Column<ListEntry, Boolean>(new CheckboxCell(true, false)) {

			public Boolean getValue(ListEntry object) {
				return multiSelectionModel.isSelected(object);
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
		 * Spalte der Einheit
		 */

		TextCell unitTextCell = new TextCell();
		Column<ListEntry, String> unitColumn = new Column<ListEntry, String>(unitTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getArticle().getUnit();
			}
		};

		/*
		 * Spalte der Menge
		 */

		TextCell amountTextCell = new TextCell();
		Column<ListEntry, String> amountColumn = new Column<ListEntry, String>(amountTextCell) {

			public String getValue(ListEntry listEntry) {
				return String.valueOf(listEntry.getAmount());
			}
		};

		/*
		 * Spalte der Stores
		 */

		TextCell storesTextCell = new TextCell();
		Column<ListEntry, String> storeColumn = new Column<ListEntry, String>(storesTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getStore().getName();
			}
		};

		/*
		 * Spalte der User
		 */

		TextCell userTextCell = new TextCell();
		Column<ListEntry, String> userColumn = new Column<ListEntry, String>(userTextCell) {

			public String getValue(ListEntry listEntry) {
				return listEntry.getUser().getName();
			}
		};



		this.add(cellTable);


	}

	/***********************************************************************
	 * Abschnitt der METHODEN
	 ***********************************************************************
	 */

	/***********************************************************************
	 * Delete DialogBox
	 ***********************************************************************
	 */

	private class DeleteListEntryDialogBox extends DialogBox {
		private VerticalPanel vPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Bist du dir Sicher, dass du diesen Eintrag löschen möchtest?");
		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/**
		 * Der Konstruktor setzt die Stylings und die Gesamtzusammensetzung der
		 * DialogBox
		 */
		public DeleteListEntryDialogBox() {
			abfrage.addStyleName("Abfrage");
			jaButton.addStyleName("button is-danger");
			neinButton.addStyleName("button bg-primary has-text-white");

			jaButton.addClickHandler(new YesDeleteClickHandler(this));
			neinButton.addClickHandler(new NoDeleteClickHandler(this));

			vPanel.add(abfrage);
			vPanel.add(buttonPanel);
			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);

			this.add(vPanel);
			this.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop());
		}
	}

	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;

	}

	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}

	public Group getSelected() {
		return selectedGroup;
	}

	public void setSelected(Group g) {
		selectedGroup = g;

	}

	public ShoppingList getSelectedList() {
		return selectedShoppingList;
	}

	public void setSelected(ShoppingList sl) {

		if (sl != null) {

			selectedShoppingList = sl;
			infoTitleLabel.setText("Einkaufsliste: " + selectedShoppingList.getName());
		} else {
			infoTitleLabel.setText("Einkaufsliste: ");
		}

	}

	/***********************************************************************
	 * Abschnitt der CLICKHANDLER
	 ***********************************************************************
	 */
	
	private class FilterByUserClickHandler implements ClickHandler {
		
		public void onClick (ClickEvent event) {
			
			RootPanel.get("details").clear();
			ShoppingListFilterForm slff = new ShoppingListFilterForm();
			
			
			RootPanel.get("details").add(slff);
			
		}
	}
	
	

	/*
	 * Die Nested-Class <code>DeleteListEntryClickHandler</code> implementiert das
	 * ClickHandler-Interface, welches eine Interaktion ermöglicht. Hier wird das
	 * Erscheinen der DeleteListEntryDialogBox ermöglicht.
	 * 
	 * @ center Centers the popup in the browser window and shows it. If the popup
	 * was already showing, then the popup is centered.
	 */

	private class DeleteListEntryClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteListEntryDialogBox deleteListEntryDialogBox = new DeleteListEntryDialogBox();
			deleteListEntryDialogBox.center();
		}
	}

	/**
	 * Die Nested-Class <code>YesDeleteClickHandler</code> implementiert das
	 * ClickHandler-Interface und startet so die Lösch-Kaskade des Listeneintrags.
	 */
	private class YesDeleteClickHandler implements ClickHandler {
		private DeleteListEntryDialogBox parentDUDB;

		// Konstruktor
		public YesDeleteClickHandler(DeleteListEntryDialogBox dudb) {
			this.parentDUDB = dudb;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.parentDUDB.hide();

			if (selectedListEntry == null) {
				Window.alert("Es wurde kein Listeneintrag ausgewählt");
			} else {
				elv.delete(selectedListEntry, new DeleteListEntryCallback());

			}

		}
	}

	/**
	 * Die Nested-Class <code>NoClickHandler</code> implementiert das
	 * ClickHandler-Interface, welches den Abbruchvorgang einleitet.
	 * 
	 */
	private class NoDeleteClickHandler implements ClickHandler {
		private DeleteListEntryDialogBox parentDUDB;

		public NoDeleteClickHandler(DeleteListEntryDialogBox dudb) {
			this.parentDUDB = dudb;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.parentDUDB.hide();
		}

	}

	private class RenameShoppingListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (renameTextBox.getValue() == "") {
				Window.alert("Die Einkaufsliste muss einen Namen besitzen!");

			} else {
				selectedShoppingList.setName(renameTextBox.getValue());
				elv.save(selectedShoppingList, new RenameShoppingListCallback());
			}

		}

	}

	private class DeleteShoppingListClickHanlder implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteShoppingListDialogBox deleteShoppingListDialogBox = new DeleteShoppingListDialogBox();
			deleteShoppingListDialogBox.center();
		}

	}

	private class DeleteShoppingListDialogBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label sicherheitsFrage = new Label("Bist Du Dir sicher die Einkaufsliste zu löschen?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/*
		 * Konstruktor
		 */

		public DeleteShoppingListDialogBox() {

			sicherheitsFrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(sicherheitsFrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			jaButton.addClickHandler(new FinalDeletListClickHandler(this));
			neinButton.addClickHandler(new CancelDeleteClickHandler(this));
		}

	}

	private class FinalDeletListClickHandler implements ClickHandler {

		private DeleteShoppingListDialogBox deleteShoppingListDialogBox;

		public FinalDeletListClickHandler(DeleteShoppingListDialogBox deleteShoppingListDialogBox) {
			this.deleteShoppingListDialogBox = deleteShoppingListDialogBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.deleteShoppingListDialogBox.hide();

			elv.delete(selectedShoppingList, new FinalDeleteListCallback());

		}

	}

	private class CancelDeleteClickHandler implements ClickHandler {

		private DeleteShoppingListDialogBox deleteShoppingListDialogBox;

		public CancelDeleteClickHandler(DeleteShoppingListDialogBox deleteShoppingListDialogBox) {
			this.deleteShoppingListDialogBox = deleteShoppingListDialogBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.deleteShoppingListDialogBox.hide();

		}

	}

	private class CreateShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			RootPanel.get("details").clear();
			NewListEntryForm nlef = new NewListEntryForm();
			RootPanel.get("details").add(nlef);
			
			

		}

	}

	/***********************************************************************
	 * Abschnitt der CALLBACKS
	 ***********************************************************************
	 */

	/*
	 * Callback wird benötigt, um den Listeneintrag zu löschen.
	 */
	private class DeleteListEntryCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Listeneintrag konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Gruppe wurde erfolgreich gelöscht");

			slf.setSelected(selectedShoppingList);
			slf.setSelected(selectedGroup);
			RootPanel.get("details").add(slf);

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

	private class FinalDeleteListCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Einkaufsliste konnte nicht gelöscht werden");

		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Einkaufsliste wurde erfolgreich gelöscht");

			gsltvm.removeShoppingListOfGroup(selectedShoppingList, selectedGroup);

		}

	}

}
