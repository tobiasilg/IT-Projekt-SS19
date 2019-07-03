package sharedShoppingList.client.gui;

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
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

public class EditListEntryForm extends DialogBox {
	
	
	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	Group selectedGroup = null;

	ShoppingList selectedShoppingList = null;
	ShoppingListForm shoppingListForm = null;
	ShoppingListForm slf;
	ListEntry selectedListEntry;
	Article article;
	String unit;
	NewListEntryForm nlef;
	// private User u = CurrentUser.getUser();

	private MultiWordSuggestOracle articleOracle = new MultiWordSuggestOracle();
	private SuggestBox articleSuggestBox = new SuggestBox(articleOracle);

	Vector<Article> articles = new Vector<Article>();
	Vector<Store> stores = new Vector<Store>();
	Vector<User> users = new Vector<User>();

	private Grid grid = new Grid(5, 5);
	//private Label unitListLabel = new Label();
	private TextBox amountTextBox = new TextBox();
	private ListBox usersListBox = new ListBox();
	private ListBox storesListBox = new ListBox();

	private Label label = new Label (selectedShoppingList.getName()+":" + "Listeneintrag editieren");
	private Button cancelButton = new Button("Abrechen");
	private Button saveButton = new Button("Aktualisieren");

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	 {

		cancelButton.addClickHandler(new CancelClickHandler());
		saveButton.addClickHandler(new SaveClickHandler());

		// UnitListBox

//		unitListBox = new ListBox();
//		unitListBox.addItem("Kg");
//		unitListBox.addItem("Gramm");
//		unitListBox.addItem("Stück");
//		unitListBox.addItem("Pack");
//		unitListBox.addItem("Liter");
//		unitListBox.addItem("Milliliter");

		// setting itemcount value to 1 turns listbox into a drop-down list.
	//	unitListBox.setVisibleItemCount(1);

		// Zusammenbau des Grid

		grid.setText(0, 0, "Artikel:");
		grid.setWidget(0, 1, articleSuggestBox);

		grid.setText(1, 0, "Menge: ");
		grid.setWidget(1, 1, amountTextBox);

//		grid.setText(2, 0, "Einheit: ");
//		grid.setWidget(2, 1, unitListLabel);

		grid.setText(2, 0, "Wer?: ");
		grid.setWidget(2, 1, usersListBox);

		grid.setText(3, 0, "Wo?: ");
		grid.setWidget(3, 1, storesListBox);

		grid.setWidget(4, 2, saveButton);
		grid.setWidget(4, 3, cancelButton);

		label.addStyleName("profilTitle");
		saveButton.addStyleName("saveButton");
		cancelButton.addStyleName("deleteButton");		
		this.add(label);
		this.add(grid);
	}

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
					articleOracle.add(a.getName() + ", "+ a.getUnit() );
				}

			}
		});

//		 UsersListBox
//		 Lade alle User aus der Datenbank
		elv.getUsersByGroup(gsltvm.getSelectedGroup(), new AsyncCallback<Vector<User>>() {

			public void onFailure(Throwable caught) {
				Window.alert("Gruppe:" + selectedGroup.getName());
			}

			public void onSuccess(Vector<User> result) {
				usersListBox.clear();
				for (User user : result) {
					users.addElement(user);
					usersListBox.addItem(user.getName());
					usersListBox.setVisibleItemCount(1);
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
					storesListBox.setVisibleItemCount(1);

				}
			}
		});
		

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

	public void setSelectedGroup(Group g) {
		this.selectedGroup = g;
	}

	public ShoppingList getSelected() {
		return selectedShoppingList;
	}

	public void setSelected(ShoppingList selectedShoppingList) {
		this.selectedShoppingList = selectedShoppingList;
	}

	public ShoppingListForm getShoppinglistForm() {
		return shoppingListForm;
	}

	public void setShoppinglistForm(ShoppingListForm shoppingListForm) {
		this.shoppingListForm = shoppingListForm;
	}

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************
	 */

	// ClickHandler to go back to ShoppingListForm
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (selectedShoppingList != null) {
				RootPanel.get("details").clear();
				slf = new ShoppingListForm();
				slf.setSelected(selectedShoppingList);
				slf.setSelected(selectedGroup);
				RootPanel.get("details").add(slf);
			}

		}

	}

	private class SaveClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			
			 selectedShoppingList = gsltvm.getSelectedList(); 
			 selectedGroup = gsltvm.getSelectedGroup();
			 
			Window.alert("Einkaufslite: " + selectedShoppingList.getName());
			
			String newArticle = articleSuggestBox.getText();

			Article article = new Article();

			for (Article a : articles) {
				if (a.getName() + ", " + a.getUnit() == newArticle) {
					article = a;
					break;
				}
			}
//			Window.alert(article.getName()+"Artikelname");
//			Window.alert(article.getId()+"ArtikelID") ;
			float newAmount = Float.parseFloat(amountTextBox.getText());

			Store store = new Store();
//			store.setName(storesListBox.getSelectedItemText());
			store = stores.get(storesListBox.getSelectedIndex());
			Window.alert("Storename: " + store.getName() );

			User user = new User();
			//user.setName(usersListBox.getSelectedItemText());
			user = users.get(usersListBox.getSelectedIndex());

			String name = new String();
			name = "";

			// Erstellung Listeneintrag
			ListEntry listEntry = new ListEntry();

			listEntry.setName(name);
			listEntry.setArticle(article);
			listEntry.setAmount(newAmount);
			listEntry.setStore(store);
			listEntry.setShoppinglist(selectedShoppingList);
			listEntry.setUser(user);
			Window.alert(user.getId()+"");

			if (amountTextBox == null) {
				Window.alert("Menge eingeben!");
			}

//			if (usersListBox == null) {
//				Window.alert("User auswählen!");
//			}
//
//			if (storesListBox == null) {
//				Window.alert("Einzelhändler auswählen!");

			else {
				
				
				elv.save(listEntry, new UpdateEntryCallback());

			}
		}

		/***********************************************************************
		 * CALLBACK
		 ***********************************************************************
		 */

		public class UpdateEntryCallback implements AsyncCallback<Void> {

			public void onFailure(Throwable caught) {
				Window.alert("Das Erzeugen eines Listeneintrags schlug fehl!");
			}

			public void onSuccess(Void result) {

				if (result != null)
					RootPanel.get("details").clear();
				slf = new ShoppingListForm();
				
				slf.setSelected(selectedShoppingList);
				slf.setSelected(selectedGroup);
				RootPanel.get("details").add(slf);
				Window.alert("Neuer Eintrag für" + selectedShoppingList.getName());
			}
		}

	}
}

