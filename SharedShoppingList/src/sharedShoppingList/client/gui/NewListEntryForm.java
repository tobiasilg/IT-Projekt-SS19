package sharedShoppingList.client.gui;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
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

public class NewListEntryForm extends FlowPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	Group selectedGroup = null;

	ShoppingList selectedShoppingList = null;
	ShoppingListForm shoppingListForm = null;
	ShoppingListForm slf;

	ListEntry selectedListEntry;
	// ListEntry newListEntry;

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
	// private Label unitListLabel = new Label();
	private TextBox amountTextBox = new TextBox();
	private ListBox usersListBox = new ListBox();
	private ListBox storesListBox = new ListBox();

	private Button cancelButton = new Button("Abrechen");
	private Button saveButton = new Button("Neu");

	private Label titel = new Label();
	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	NewListEntryForm() {

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
		// unitListBox.setVisibleItemCount(1);

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

		saveButton.addStyleName("buttonAbfrage");
		cancelButton.addStyleName("buttonAbfrage");
		
		titel.addStyleName("profilTitle");

		// Füge das Grid der Dialogbox hinzu

	}

	/***********************************************************************
	 * onLoad METHODEN
	 ***********************************************************************
	 */

	public void onLoad() {
		
		this.add(titel);
		this.add(grid);

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
					articleOracle.add(a.getName() + ", " + a.getUnit());
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
		
		titel.setText(selectedShoppingList.getName());
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
				
				shoppingListForm.setSelected(selectedGroup);
				RootPanel.get("details").add(shoppingListForm);
			}

		}

	}

	private class SaveClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			selectedShoppingList = gsltvm.getSelectedList();
			selectedGroup = gsltvm.getSelectedGroup();

			

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

			User user = new User();
			// user.setName(usersListBox.getSelectedItemText());
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
				

				elv.createListentry(name, user, article, newAmount, store, selectedShoppingList,
						new CreateListEntryCallback());

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
				
				RootPanel.get("details").clear();

				elv.setNewOne(gsltvm.getSelectedList(), result, new NewOneCallback());
				gsltvm.getSelectedList().setNewOne(result.getId());
				
				shoppingListForm.setSelected(selectedGroup);
				RootPanel.get("details").add(shoppingListForm);
			}
		}

		/***********************************************************************
		 * CALLBACK
		 ***********************************************************************
		 */

		public class NewOneCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Void arg0) {
				// TODO Auto-generated method stub

			}

		}

	}

}