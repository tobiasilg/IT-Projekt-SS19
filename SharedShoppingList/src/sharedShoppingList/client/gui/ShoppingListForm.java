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
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentGroup;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Favourite;
import sharedShoppingList.shared.bo.Group;
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
	private User u = CurrentUser.getUser();
	Group g = CurrentGroup.getGroup();
	
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();

	// +"Gruppenname"
	private Label infoTitleLabel = new Label("Einkaufsliste" + "");
	private SuggestBox suggestBox;
	private Button addArticleToShoppingListButton = new Button("hinzufuegen");
	private HorizontalPanel hpSuggestBox = new HorizontalPanel();

	private FlexTable shoppingListFlexTable;
	private ListBox usersListBox;
	private ListBox storesListBox;

	private Button deleteRowButton;
	private ArrayList<Article> articles;
	private ArrayList<Favourite> favourites;
	private ArrayList<User> users;
	private ArrayList<Store> stores;

	// Konstruktor
	public ShoppingListForm() {

		deleteRowButton.addClickHandler(new DeleteRowClickHandler());

		hpSuggestBox.add(suggestBox);
		hpSuggestBox.add(addArticleToShoppingListButton);

	}

	/**
	 * In dieser Methode wird das Design der <code>ShoppingListForm</code> und der
	 * Buttons festgelegt. Diese Methode wird aufgerufen, sobald ein Objekt der
	 * Klasse <code>ShoppingListForm</code> instanziert wird.
	 */
	public void onLoad() {

		hpSuggestBox.add(suggestBox);
		hpSuggestBox.add(addArticleToShoppingListButton);

		this.add(infoTitleLabel);
		this.add(hpSuggestBox);
		this.add(shoppingListFlexTable);

		infoTitleLabel.setHorizontalAlignment(ALIGN_CENTER);
		infoTitleLabel.setWidth("100%");

		hpSuggestBox.setCellHorizontalAlignment(suggestBox, ALIGN_LEFT);
		hpSuggestBox.setCellHorizontalAlignment(addArticleToShoppingListButton, ALIGN_LEFT);
		hpSuggestBox.setWidth("50%");

		suggestBox.setWidth("200");

		addArticleToShoppingListButton.setPixelSize(130, 40);
		shoppingListFlexTable.setWidth("%");
		shoppingListFlexTable.setBorderWidth(0);
		shoppingListFlexTable.setSize("%", "%");
		shoppingListFlexTable.setCellPadding(0);

		suggestBox.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {

				if (event.getCharCode() == KeyCodes.KEY_ENTER) {

					addArticleToShoppingListButton.click();
					suggestBox.setText("");
				}

			}
		});
	}

	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;
		
	}
	
	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}
	/***********************************************************************
	 * Abschnitt der METHODEN
	 ***********************************************************************
	 */

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
		shoppingListFlexTable.setText(0, 5, "");

		articles = new ArrayList<Article>();

		/*
		 * Lade alle Favoritenartikel aus der Datenbank in die Shoppingliste
		 */

		// andere Methode: getAllFavByGroupID
		elv.getAllFavourites(new AsyncCallback<Vector<Favourite>>() {
			public void onFailure(Throwable caught) {
				Notification.show("failure");

			}

			public void onSuccess(Vector<Favourite> result) {
				for (Favourite favourite : result) {
					favourites.add(favourite);
					setContentOfShoppingListFlexTable(favourite);
				}
				Notification.show("success");
			}
		});

		return shoppingListFlexTable;

	}

	// SuggestBox

	/*
	 * Default SuggestOracle of SuggestBox is MultiWordSuggestOracle which displays
	 * sorted drop dwon list of matches. List of names comes from
	 * elv.getAllArticles, which returns names of articles
	 */

	private void setNames() {
	//	List<String> list = elv.getAllArticles(new suggestBoxCallback<Vector<Article>>());
		//((MultiWordSuggestOracle) suggestBox.getSuggestOracle()).addAll(list);

	}

	@UiHandler("suggestBox")
	public void handleSelection(SelectionEvent<SuggestOracle.Suggestion> s) {
	//	log.fine("Selection : " + suggestBox.getText());
		suggestBox.setText("");
	}

	/***********************************************************************
	 * Who & Where - ListBoxen
	 ***********************************************************************
	 */

	// WhoListBox
	private ListBox createWhoListBox() {

		// Lade alle User aus der Gruppe
		elv.getUsersByGroup(g, new AsyncCallback<Vector<User>>() {

			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			public void onSuccess(Vector<User> result) {
				for (User user : result) {

					users.add(user);
					// setContentOfShoppingListFlexTable(user);
				}
				Notification.show("success");
			}
		});

		return usersListBox;
	}

	// WhereListBox
	private ListBox createWhereListBox() {

		// Lade alle User aus der Gruppe
		elv.getAllStores(new AsyncCallback<Vector<Store>>() {

			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			public void onSuccess(Vector<Store> result) {
				for (Store store : result) {

					stores.add(store);
					// setContentOfShoppingListFlexTable(store);
				}
			}
		});

		return storesListBox;
	}

	/***********************************************************************
	 * Zusammenbau der ShoppingListFlexTable
	 ***********************************************************************
	 */

	private void setContentOfShoppingListFlexTable(Favourite favourite) {
		// Hole Zeilennummer, die aktuell bearbeitet wird
		int rowCount = shoppingListFlexTable.getRowCount();

		// Erstelle neue Textbox für eigetragenen Artikel und setze den Namen
		TextBox articleTextBox = new TextBox();
		// articleTextBox.setText(favourite.getName());

		// Parameter ???
		TextBox amountTextBox = new TextBox();
		// amountTextBox.setText(favourite.getName());

		usersListBox = new ListBox();
		for (User user : users) {
			// whoListBox.addItem(who);
		}
		usersListBox.setSelectedIndex(Arrays.asList(users).indexOf(u.getUserName()));

		storesListBox = new ListBox();
		for (Store where : stores) {
			// whereListBox.addItem(where);
		}

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
		shoppingListFlexTable.setWidget(rowCount, 2, amountTextBox);
		shoppingListFlexTable.setWidget(rowCount, 3, usersListBox);
		shoppingListFlexTable.setWidget(rowCount, 4, storesListBox);

	}

	/***********************************************************************
	 * Abschnitt der CLICKHANDLER
	 ***********************************************************************
	 */

	/**
	 * Die Nested-Class <code>DeleteRowClickHandler</code> implementiert das
	 * ClickHandler-Interface und startet so die Lösch-Kaskade einer Artikelzeile in
	 * der Shoppingliste.
	 */

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

	/**
	 * Callback wird für die SuggestBox benötigt
	 */
	private class suggestBoxCallback implements AsyncCallback<Vector<Article>> {

		public void onFailure(Throwable caught) {
			Notification.show("");
		} 

		@Override
		public void onSuccess(Vector<Article> result) {
			// TODO Auto-generated method stub
			
		}
	}

	/**
	 * Callback wird benötigt, um die Gruppe umzubenennen
	 */
	private class DeleteRowCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Artikelzeile konnte nicht gelöscht werden");
		}

		public void onSuccess(Void article) {
			Notification.show("Die Artikelzeile wurde erfolgreich gelöscht");
			articles.clear();
			createTable();
		}
	}

}
