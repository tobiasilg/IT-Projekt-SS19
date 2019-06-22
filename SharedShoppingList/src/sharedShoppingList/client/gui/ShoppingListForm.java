package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Favourite;
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
	private final MultiSelectionModel<Vector<Object>> multiSelectionModel = new MultiSelectionModel<Vector<Object>>();

	private User u = CurrentUser.getUser();

	private Group selectedGroup = null;
	private ShoppingList selectedShoppingList = null;
	private ListEntry listEntry = null;
	private NewListEntryForm nlef = null;

	private CellTable<Vector<Object>> cellTable = new CellTable<Vector<Object>>();
	private Vector<ListEntry> listEntries = new Vector<ListEntry>();
	private Vector<Vector<Object>> datas = new Vector<Vector<Object>>();

	private Label infoTitleLabel = new Label();

	private Button saveSlButton = new Button("Änderungen speichern");
	private Button deleteSlButton = new Button("Einkaufsliste löschen");

	private Button createShoppingListButton = new Button("Listeneintrag erstellen");
	private Button deleteRowButton;
	
	private ButtonCell removeButton = new ButtonCell();

	private HorizontalPanel createButtonPanel = new HorizontalPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	private VerticalPanel cellTableVP = new VerticalPanel();

	private TextBox renameTextBox = new TextBox();

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */

	public ShoppingListForm() {

		saveSlButton.addClickHandler(new RenameShoppingListClickHandler());
		deleteSlButton.addClickHandler(new DeleteShoppingListClickHanlder());
		createShoppingListButton.addClickHandler(new CreateShoppingListClickHandler());
		
		renameTextBox.getElement().setPropertyString("placeholder", "Einkaufsliste umbenennen...");
		renameTextBox.setWidth("15rem");

		// Panel mit Button zum erzeugen eines neuen Listeneintrags
		
		createButtonPanel.add(createShoppingListButton);
		

		// Panel der Buttons
		buttonPanel.add(renameTextBox);
		buttonPanel.add(saveSlButton);
		buttonPanel.add(deleteSlButton);

		// CellTable
		cellTableVP.add(cellTable);
		cellTableVP.setBorderWidth(1);
		cellTableVP.setWidth("400");
		
		

		infoTitleLabel.addStyleName("profilTitle");
		createButtonPanel.setCellHorizontalAlignment(createButtonPanel, ALIGN_LEFT);

		this.add(infoTitleLabel);
		this.add(buttonPanel);
		this.add(createButtonPanel);
		

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

		Column<Vector<Object>, Boolean> checkBoxColumn = new Column<Vector<Object>, Boolean>(
				new CheckboxCell(true, false)) {

			public Boolean getValue(Vector<Object> object) {
				return multiSelectionModel.isSelected(object);
			}
		};

		/*
		 * Spalte des Artikelnamens
		 */

		Column<Vector<Object>, String> articleColumn = new Column<Vector<Object>, String>(new TextCell()) {

			@Override
			public String getValue(Vector<Object> object) {

				return object.get(0).toString();

			}
		};

		/*
		 * Spalte der Mengenanzahl
		 */

		Column<Vector<Object>, String> amountColumn = new Column<Vector<Object>, String>(new TextCell()) {
			@Override
			public String getValue(Vector<Object> object) {

				return object.get(1).toString();

			}
		};

		/*
		 * Spalte der Einheit
		 */

		Column<Vector<Object>, String> unitColumn = new Column<Vector<Object>, String>(new TextCell()) {

			public String getValue(Vector<Object> object) {

				return object.get(2).toString();

			}
		};

		/*
		 * Spalte der Einzehlhändler
		 */

		Column<Vector<Object>, String> storeColumn = new Column<Vector<Object>, String>(new TextCell()) {
			@Override
			public String getValue(Vector<Object> object) {

				return object.get(3).toString();

			}
		};

		/*
		 * Spalte des zugewiesenen Users
		 */

		Column<Vector<Object>, String> userColumn = new Column<Vector<Object>, String>(new TextCell()) {
			@Override
			public String getValue(Vector<Object> object) {

				return object.get(4).toString();

			}
		};
		
//		ButtonCell removeButton = new ButtonCell();
		
		Column<Vector<Object>, String> deleteColumn = new Column<Vector<Object>, String>(removeButton) {
			
			@Override
			public String getValue(Vector<Object> object) {

				return "x";
	//			return object.get(6).toString();
				
			}

		};

		
	//	cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		// Die Spalten werden hier der CellTable hinzugefügt
		cellTable.addColumn(checkBoxColumn, "Erledigt?");
		cellTable.addColumn(articleColumn, "Artikel");
		cellTable.addColumn(amountColumn, "Menge");
		cellTable.addColumn(unitColumn, "Einheit");
		cellTable.addColumn(userColumn, "Wer?");
		cellTable.addColumn(storeColumn, "Wo?");
		cellTable.addColumn(deleteColumn, "Löschen");
	
		
		checkBoxColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		// Add selection to table
		cellTable.setSelectionModel(multiSelectionModel,
				DefaultSelectionEventManager.<Vector<Object>>createCheckboxManager());
		
		this.add(cellTable);

	}

	/***********************************************************************
	 * onLoad Methode
	 ***********************************************************************
	 */

	public void onLoad() {

		// Füge alle ListenEinträge aus der Datenbank hinzu
//
//		elv.getAllListEntriesByShoppingList(this.getSelectedList(),
//				new AsyncCallback<Map<ListEntry, Vector<String>>>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//
//					}
//
//					@Override
//					public void onSuccess(Map<ListEntry, Vector<String>> result) {
//
//						datas.clear();
//						if (datas.size() == 0) {
//
//							for (ListEntry k : result.keySet()) {
//								Vector<Object> listEntries = new Vector<>();
//
//								listEntries.add(k);
//								listEntries.add(result.get(k).get(0));
//								listEntries.add(result.get(k).get(1));
//								listEntries.add(result.get(k).get(2));
//								listEntries.add(result.get(k).get(3));
//								listEntries.add(result.get(k).get(4));
//
//								datas.add(listEntries);
//							}
//
//							// setze den RowCount
//							cellTable.setRowCount(result.size(), true);
//
//							cellTable.setRowData(0, datas);
//
//						}
//					}
//				});
//
//		this.add(cellTable);

		
		/*
		 * SelectionModel dient zum Markieren der Zellen
		 */
//	// Add a selection model to handle user selection.
//    final SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>();
//    table.setSelectionModel(selectionModel);
//    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
//       public void onSelectionChange(SelectionChangeEvent event) {
//          Object selected = selectionModel.getSelectedObject();
//          if (selected != null) {
//             Window.alert("You selected: " + selected);
//          }
//       }
//    });

	}

	/***********************************************************************
	 * Abschnitt der METHODEN
	 ***********************************************************************
	 */
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
