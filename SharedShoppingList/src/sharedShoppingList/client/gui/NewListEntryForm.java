package sharedShoppingList.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
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

public class NewListEntryForm extends DialogBox {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	Group selectedGroup;
	ShoppingList selectedList;
	Article selectedArticle;
	Article newArticle;

	private MultiWordSuggestOracle articleOracle = new MultiWordSuggestOracle();
	private SuggestBox articleSuggestBox = new SuggestBox(articleOracle);

	Vector<Article> articles = new Vector<Article>();
	Vector<Store> stores = new Vector<Store>();
	Vector<User> users = new Vector<User>();
	// Vector<Unit> units = new Vector<Unit>();

	private Grid grid = new Grid(5, 5);

	private TextBox amountTextBox = new TextBox();
	private ListBox unitsListBox = new ListBox();
	private ListBox usersListBox = new ListBox();
	private ListBox storesListBox = new ListBox();

	private Button cancelButton = new Button("abbrechen");
	private Button saveButton = new Button("hinzufügen");

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	NewListEntryForm() {

		cancelButton.addClickHandler(new CancelClickHandler());
		saveButton.addClickHandler(new SaveClickHandler());

	}

	/***********************************************************************
	 * onLoad METHODEN
	 ***********************************************************************
	 */
	public void onLOad() {

		/*
		 * Lade alle Artikel aus der Datenbank in die SuggestBox
		 */

		elv.getAllArticles(new AsyncCallback<Vector<Article>>() {

			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			public void onSuccess(Vector<Article> result) {
				for (Article article : result) {
					articles.addElement(article);
					articleOracle.add(article.getName());
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
				Notification.show("failure");
			}

			public void onSuccess(Vector<User> result) {
				usersListBox.clear();
				for (User user : result) {
					users.addElement(user);
					usersListBox.addItem(user.getName());
					// evtl mit (user.getName() + ", " + user.getLocation());
				}

			}
		});

		// StoresListBox
		// Lade alle Stores aus der Datenbank
		elv.getAllStores(new AsyncCallback<Vector<Store>>() {

			public void onFailure(Throwable caught) {
				Notification.show("failure");
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
//		//elv.getUnit(new AsyncCallback<Vector<Store>>() {
//
//			public void onFailure(Throwable caught) {
//				Notification.show("failure");
//			}
//
//			public void onSuccess(Vector<Unit> result) {
//				unitsListBox.clear();
//				for (Unit unit : result) {
//					units.addElement(unit);
//					unitsListBox.addItem(unit.getName());
//
//				}
//			}
//		});

		/***********************************************************************
		 * Building the grid
		 ***********************************************************************
		 */
		grid.setText(0, 0, "Artikel: ");
	//	grid.setWidget(0, 1, articleSuggestBox);

		grid.setText(1, 0, "Menge: ");
		grid.setWidget(1, 1, amountTextBox);

		grid.setText(2, 0, "Einheit: ");
		grid.setWidget(2, 1, unitsListBox);

		grid.setText(3, 0, "Wer?: ");
		grid.setWidget(3, 1, usersListBox);

		grid.setText(4, 0, "Wo?: ");
		grid.setWidget(4, 1, storesListBox);

		grid.setWidget(5, 2, saveButton);
		grid.setWidget(5, 3, cancelButton);

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
	
	
	
	public Article getNewArticle() {
		return newArticle;
	}

	public void setNewArticle(Article newArticle) {
		this.newArticle = newArticle;
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
			ShoppingListForm shoppingListForm = new ShoppingListForm();
			shoppingListForm.setSelected(selectedList);
			RootPanel.get("details").add(shoppingListForm);

		}

	}

	private class SaveClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			
			ListEntry listEntry = new ListEntry();
			 
				}
			}
			
	public void hideDialog() {
	this.hide();
	
}
}