package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;

import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/**
 * Klasse zum Anzeigen einer DialogBox, das einen neuen Listeneintrag generiert.
 * 
 * @author nicolaifischbach
 *
 */

public class NewListEntryForm extends DialogBox {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	Group selectedGroup = null;
	ShoppingList selectedList = null;
	ShoppingListForm slf = null;
	Article a = new Article();

	private MultiWordSuggestOracle articleOracle = new MultiWordSuggestOracle();
	private SuggestBox articleSuggestBox = new SuggestBox(articleOracle);

	Vector<Article> articles = new Vector<Article>();
	Vector<Store> stores = new Vector<Store>();
	Vector<User> users = new Vector<User>();
	String unit = a.getUnit();
	Label articleUnitLabel = new Label ();

	private Grid grid = new Grid(6, 6);

	private TextBox amountTextBox = new TextBox();
	private ListBox usersListBox = new ListBox();
	private ListBox storesListBox = new ListBox();
	
	private Button cancelButton = new Button("Abrechen");
	private Button saveButton = new Button("Neu");

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	NewListEntryForm() {

		cancelButton.addClickHandler(new CancelClickHandler());
		saveButton.addClickHandler(new SaveClickHandler());

		articleUnitLabel.setText(a.getUnit());
	}

//	private class Unit {
//		private Unit unit;
//
//		public void setUnit(Unit unit) {
//			this.unit = unit;
//		}
//
//		public Unit getUnit() {
//			return unit;
//		}
//
//	}

	/***********************************************************************
	 * onLoad METHODEN
	 ***********************************************************************
	 */
	public void onLoad() {
		
		

		/*
		 * Lade alle Artikel aus der Datenbank in das articleOracle
		 */

		elv.getAllArticles(new AsyncCallback<Vector<Article>>() {

			public void onFailure(Throwable caught) {
				Notification.show("1. failure");
			}

			public void onSuccess(Vector<Article> result) {
				for (Article a : result) {
					articles.addElement(a);
					articleOracle.add(a.getName());
				}

			}
		});

		/***********************************************************************
		 * LISTBOXEN
		 ***********************************************************************
		 */

		// UsersListBox
		// Lade alle User aus der Datenbank
		elv.getUsersByGroup(selectedGroup, new AsyncCallback<Vector<User>>() {

			public void onFailure(Throwable caught) {
				Notification.show("2. failure");
			}

			public void onSuccess(Vector<User> result) {
				usersListBox.clear();
				for (User user : result) {
					users.addElement(user);
					usersListBox.addItem(user.getName());
				}

			}
		});

		// StoresListBox
		// Lade alle Stores aus der Datenbank
		elv.getAllStores(new AsyncCallback<Vector<Store>>() {

			public void onFailure(Throwable caught) {
				Notification.show("3. failure");
			}

			public void onSuccess(Vector<Store> result) {
				storesListBox.clear();
				for (Store store : result) {
					stores.addElement(store);
					storesListBox.addItem(store.getName());

				}
			}
		});

//		// UnitListBox
//		// Lade alle Einheit aus der Datenbank
//		// elv.getUnit(new AsyncCallback<Vector<Store>>() {
//
////			public void onFailure(Throwable caught) {
////				Notification.show("failure");
////			}
////
////			public void onSuccess(Vector<Unit> result) {
////				unitsListBox.clear();
////				for (Unit unit : result) {
////					units.addElement(unit);
////					unitsListBox.addItem(unit.getName());
////
////				}
////			}
////		});

		// UnitListBox
		// Lade alle Einheit aus der Datenbank

//		ArrayList<String> units = null;
//
//		if (units == null) {
//			units = new ArrayList<String>();
//			units.addAll(Arrays.asList("kg", "Gramm", "Stück", "Pack", "Liter", "Milliliter"));
//		}
//		if (unitsListBox == null) {
//			unitsListBox = new ListBox();
//		}
//		for (String unit : units) {
//			unitsListBox.addItem(unit);
//		}

		// setting itemcount value to 1 turns listbox into a drop-down list
		// listBoxUnits.setVisibleItemCount(1);

		/***********************************************************************
		 * Building the grid
		 ***********************************************************************
		 */

		grid.setText(0, 0, "Artikel: ");
		grid.setWidget(0, 1, articleSuggestBox);

		grid.setText(1, 0, "Menge: ");
		grid.setWidget(1, 1, amountTextBox);

		grid.setText(2, 0, "Einheit: ");
		grid.setWidget(2, 1, articleUnitLabel);

		grid.setText(3, 0, "Wer?: ");
		grid.setWidget(3, 1, usersListBox);

		grid.setText(4, 0, "Wo?: ");
		grid.setWidget(4, 1, storesListBox);

		grid.setWidget(5, 2, saveButton);
		grid.setWidget(5, 3, cancelButton);

		saveButton.addStyleName("buttonAbfrage");
		cancelButton.addStyleName("buttonAbfrage");

		this.add(grid);

	}

	/***********************************************************************
	 * METHODEN
	 ***********************************************************************
	 */

	public void onKeyPress(KeyPressEvent event) {
		if (event.getCharCode() == KeyCodes.KEY_ENTER) {
			saveButton.click();
			articleSuggestBox.setText("");
		}

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

	public ShoppingList getSelectedList() {
		return selectedList;
	}

	public void setSelected(ShoppingList sl) {
		selectedList = sl;
	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************
	 */

	// ClickHandler to go back to ShoppingListForm
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			RootPanel.get("details").clear();
			NewListEntryForm nlef = new NewListEntryForm();
			nlef.setSelected(selectedList);
			nlef.setSelectedGroup(selectedGroup);
			slf = new ShoppingListForm();
			RootPanel.get("details").add(slf);

		}

	}

	private class SaveClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			ShoppingList shoppingList = slf.getSelectedList(); // aktuelle Shoppingliste, stimmt das?

			String newArticle = articleSuggestBox.getText();

			Article article = new Article();

			for (Article a : articles) {
				if (a.getName() == newArticle) {
					article = a;
					break;
				}
			}

			//a.getUniit(unit);
			float newAmount = Float.parseFloat(amountTextBox.getText());
		
			Store store = new Store();
			store.setName(storesListBox.getSelectedItemText());

			User user = new User();
			user.setName(usersListBox.getSelectedItemText());

			// Listeneintrag
			ListEntry listEntry = new ListEntry();

			store = stores.get(storesListBox.getSelectedIndex());
			user = users.get(usersListBox.getSelectedIndex());

			
			article.setUnit(unit);
			listEntry.setArticle(article);
			listEntry.setAmount(newAmount);
			listEntry.setStore(store);
			listEntry.setShoppinglist(shoppingList);
			listEntry.setUser(user);

			// ClientsideSettings.getLogger().info("id: " + listEntry.getShoppinglistId());

			if (amountTextBox == null) {
				Window.alert("Menge eingeben!");
			}

			if (usersListBox == null) {
				Window.alert("User auswählen!");
			}

			if (storesListBox == null) {
				Window.alert("Einzelhändler auswählen!");

			} else {

			elv.createListentry("", user, article, newAmount, store,shoppingList,  new CreateListEntryCallback());

			}
		}

		/***********************************************************************
		 * CALLBACK
		 ***********************************************************************
		 */

		public class CreateListEntryCallback implements AsyncCallback<ListEntry> {

			public void onFailure(Throwable caught) {
				Window.alert("Das Erzeugen eines Listeneintrags schlug fehl!");
			}

			public void onSuccess(ListEntry result) {

				if (result != null) {

					RootPanel.get("details").clear();
					slf.setSelected(selectedList);
					slf.setSelected(selectedGroup);
					RootPanel.get("details").add(slf);

				}

			}
		}
	}

}