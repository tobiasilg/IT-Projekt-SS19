package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
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
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;

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
	private final MultiSelectionModel<ArrayList<Object>> multiSelectionModel = new MultiSelectionModel<ArrayList<Object>>();

	private User u = CurrentUser.getUser();

	Group selectedGroup = null;
	ShoppingList selectedShoppingList = null;

	private ShoppingListForm sf;

	ListEntry listEntry = new ListEntry();
	private CellTable<ArrayList<Object>> table = new CellTable<ArrayList<Object>>();

	private Label infoTitleLabel = new Label(selectedShoppingList.getName());

	private Button saveSlButton = new Button("Änderungen speichern");
	private Button deleteSlButton = new Button("Einkaufsliste löschen");

	private Button createShoppingListButton = new Button("Listeneintrag erstellen");
	private Button deleteRowButton;

	private HorizontalPanel createButtonPanel = new HorizontalPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	private FlowPanel cellTableFlowPanel = new FlowPanel();
	

	private TextBox renameTextBox = new TextBox();

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	public ShoppingListForm() {

		saveSlButton.addClickHandler(new RenameShoppingListClickHandler());
		deleteSlButton.addClickHandler(new DeleteShoppingListClickHanlder());
		createShoppingListButton.addClickHandler(new CreateShoppingListClickHandler());

		// deleteRowButton.addClickHandler(new DeleteRowClickHandler());

	}

	/***********************************************************************
	 * onLoad Methode
	 ***********************************************************************
	 */

	public void onLoad() {

		renameTextBox.getElement().setPropertyString("placeholder", "Einkaufsliste umbenennen...");
		renameTextBox.setWidth("15rem");

		createButtonPanel.add(createShoppingListButton);
		buttonPanel.add(renameTextBox);
		buttonPanel.add(saveSlButton);
		buttonPanel.add(deleteSlButton);
		
		cellTableFlowPanel.add(table);		

		infoTitleLabel.addStyleName("profilTitle");
		createButtonPanel.setCellHorizontalAlignment(createButtonPanel, ALIGN_LEFT);

		this.add(infoTitleLabel);
		this.add(createButtonPanel);
		this.add(table);
		this.add(buttonPanel);

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

	Column<ArrayList<Object>, Boolean> checkBoxColumn = new Column<ArrayList<Object>, Boolean>(
			new CheckboxCell(true,false)){
		
		public Boolean getValue (ArrayList<Object> object) {
			return multiSelectionModel.isSelected(object);
		}
	};
	
	/*
	 * Spalte des Artikelnamens
	 */
	
	Column<ArrayList<Object>, String> articleNameColumn = new Column<ArrayList<Object>, String>(new TextCell()) {

		@Override
		public String getValue(ArrayList<Object> object) {

			return object.get(1).toString();

		}
	};
	
	/* 
	 * Spalte der Mengenanzahl
	 */
	
	Column<ArrayList<Object>, String> amountColumn = new Column<ArrayList<Object>, String>(new TextCell()) {
		@Override
		public String getValue(ArrayList<Object> object) {

			return object.get(1).toString();

		}
	};
	
	/*
	 * Spalte der Einheit
	 */
	
	Column<ArrayList<Object>, String> unitColumn = new Column<ArrayList<Object>, String>(new TextCell()) {
		
		public String getValue(ArrayList<Object> object) {

			return object.get(1).toString();

		}
	};
	
	/*
	 * Spalte der Einzehlhändler
	 */
	
	Column<ArrayList<Object>, String> storeColumn = new Column<ArrayList<Object>, String>(
			new TextCell()) {
		@Override
		public String getValue(ArrayList<Object> object) {

			return object.get(1).toString();

		}
	};
	
	/*
	 * Spalte des zugewiesenen Users
	 */
	
	Column<ArrayList<Object>, String> userColumn = new Column<ArrayList<Object>, String>(
			new TextCell()) {
		@Override
		public String getValue(ArrayList<Object> object) {

			return object.get(1).toString();

		}
	};
	
	table.addColumn(checkBoxColumn,"");
	table.addColumn(articleNameColumn,"Artikel");
	table.addColumn(amountColumn,"Menge");
	table.addColumn(unitColumn,"Einheit");
	table.addColumn(userColumn, "Wer?");
	table.addColumn(storeColumn, "Wo?");
		
	/*
	 * SelectionModel dient zum Markieren der Zellen
	 */
	
	table.setSelectionModel(multiSelectionModel,
			DefaultSelectionEventManager.<ArrayList<Object>>createCheckboxManager());
	
	}
	
	/*
	 * Zusammenfügen der Spalten zu einer CellTable
	 * 
	 */
	
	
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
		
		if(sl != null) {
			
			selectedShoppingList = sl;
			infoTitleLabel.setText("Einkaufsliste: " + selectedShoppingList.getName());
		} else {
			infoTitleLabel.setText("Einkaufsliste: ");
		}
	
		
	}

	/*
	 * Zusammenbau der shoppingListFlexTable
	 */


		

	/***********************************************************************
	 * Abschnitt der CLICKHANDLER
	 ***********************************************************************
	 */
	
	private class RenameShoppingListClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			
			if(renameTextBox.getValue() == "") {
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

	private class AddArticleToShoppingListClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

		}
	}

	private class DeleteRowClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// elv.delete(u, new DeleteRowCallback());
		}
	}

	public class checkBoxClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			boolean checked = ((CheckBox) event.getSource()).getValue();
			Window.alert("It is " + (checked ? "" : "not ") + "checked");
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
