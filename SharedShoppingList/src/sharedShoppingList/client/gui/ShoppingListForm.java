package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Favourite;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/**
 * 
 * Die Klasse <code>ShoppingListForm</code> erstellt das Formular zur Anzeige
 * einer Shoppingliste mit Artikel, Artikelanzahl, Unit, wer und wo der Artikel
 * gekauft wird etc..
 * 
 * @author nicolaifischbach
 *
 */

public class ShoppingListForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();

	private User u = CurrentUser.getUser();
	Group selectedGroup;
	ShoppingList selectedList;
	ListEntry listEntry = new ListEntry();

	// +"Gruppenname"
	private Label infoTitleLabel = new Label("Einkaufsliste" + selectedGroup.getName());

	private FlexTable shoppingListFlexTable;

	private Button createShoppingListButton;
	private Button deleteRowButton;

	private Vector<ListEntry> listEntries = new Vector<ListEntry>();
	private Vector<Favourite> favourites = new Vector<Favourite>();

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	public ShoppingListForm() {

		deleteRowButton.addClickHandler(new DeleteRowClickHandler());

	}

	/***********************************************************************
	 * onLoad Methode
	 ***********************************************************************
	 */

	public void onLoad() {

		// Add them to verticalPanel
		this.add(createShoppingListButton);
		this.add(infoTitleLabel);
		this.add(shoppingListFlexTable);

		infoTitleLabel.setHorizontalAlignment(ALIGN_CENTER);
		infoTitleLabel.setWidth("100%");

		shoppingListFlexTable.setWidth("%");
		shoppingListFlexTable.setBorderWidth(0);
		shoppingListFlexTable.setSize("%", "%");
		shoppingListFlexTable.setCellPadding(0);

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

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public ShoppingList getSelectedList() {
		return selectedList;
	}

	public void setSelected(ShoppingList sl) {
		selectedList = sl;
	}

	/*
	 * Zusammenbau der shoppingListFlexTable
	 */

	private FlexTable createTable() {

		// Erstelle eine FlexTable, falls nicht existent

		if (shoppingListFlexTable == null) { 
			shoppingListFlexTable = new FlexTable();

		}

		shoppingListFlexTable.removeAllRows();

		shoppingListFlexTable.setText(0, 0, "Artikel");
		shoppingListFlexTable.setText(0, 1, "Anzahl");
		shoppingListFlexTable.setText(0, 2, "Einheit");
		shoppingListFlexTable.setText(0, 3, "Wer?");
		shoppingListFlexTable.setText(0, 4, "Wo?");
		shoppingListFlexTable.setText(0, 5, "Favorit");

		return shoppingListFlexTable;

	}

	/***********************************************************************
	 * Zusammenbau der ShoppingListFlexTable
	 ***********************************************************************
	 */

	private void setContentOfShoppingListFlexTable(Article article) {
		// Hole Zeilennummer, die aktuell bearbeitet wird
		int rowCount = shoppingListFlexTable.getRowCount();

		// Erstelle neue Textbox für eigetragenen Artikel und setze den Namen
		TextBox articleTextBox = new TextBox();

		TextBox amountTextBox = new TextBox();

		// Erstelle x Button

		deleteRowButton = new Button("x");
		// deleteRowButton.setArticle (article);

		deleteRowButton.addClickHandler(new DeleteRowClickHandler());

		// Füge die TextBox und ListBoxen in die Flextable ein
		CheckBox checkbox = new CheckBox();
		checkbox.setValue(true);
		checkbox.addClickHandler(new checkBoxClickHandler());

		shoppingListFlexTable.setWidget(rowCount, 0, checkbox);
		shoppingListFlexTable.setWidget(rowCount, 1, articleTextBox);
		
	}

	/***********************************************************************
	 * Abschnitt der CLICKHANDLER
	 ***********************************************************************
	 */
	
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

}
