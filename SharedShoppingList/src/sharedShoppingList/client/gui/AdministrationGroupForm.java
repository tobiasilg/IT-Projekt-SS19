package sharedShoppingList.client.gui;

import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import sharedShoppingList.client.ClientsideSettings;
//import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.FieldVerifier;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Einsehen von Gruppenmitglieder, Hinzufügen von Usern, ändern
 * des Gruppennamens, Gruppe löschen
 * 
 * * @author nicolaifischbach + moritzhampe
 * 
 */

public class AdministrationGroupForm extends VerticalPanel {

	private EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
//	private User user = CurrentUser.getUser();
	private User newGroupUser = null;
	private Group selectedGroup = null;

	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();

	private ShoppingListCreationForm shoppingListCreationForm;

	FieldVerifier verifier = new FieldVerifier();

	private Label membersLabel = new Label("Mitgliederverwaltung");
	private Label groupLabel = new Label("Gruppenverwaltung");

	private DynamicTextbox renameTextBox = new DynamicTextbox();

	private MultiWordSuggestOracle userOracle = new MultiWordSuggestOracle();
	private SuggestBox userTextBox = new SuggestBox(userOracle);

	private Button addUserButton = new Button("Hinzufügen");

	private Button deleteGroupButton = new Button("loeschen");
	private Button saveGroupNameButton = new Button("speichern");
	private Button createShoppingListButton = new Button("Shoppingliste erstellen");

	private FlowPanel boxPanel = new FlowPanel();
	private HorizontalPanel memberPanel = new HorizontalPanel();
	private HorizontalPanel hpButtonsPanelGroup = new HorizontalPanel();

	private ListDataProvider<User> dataProvider = new ListDataProvider<User>();
	private List<User> list = dataProvider.getList();
	private Vector<User> users = new Vector<User>();

//	private ScrollPanel scrollPanel = new ScrollPanel();

	private CellTable<User> memberTable = new CellTable<User>(KEY_PROVIDER);

	private static final ProvidesKey<User> KEY_PROVIDER = new ProvidesKey<User>() {
		@Override
		public Object getKey(User user) {
			return user.getId();
		}
	};

	public Group getSelected() {
		return selectedGroup;
	}

	public void setSelected(Group g) {

		if (g != null) {

			selectedGroup = g;
			renameTextBox.setText(selectedGroup.getName());

			dataProvider.getList().clear();

		} else {
			renameTextBox.setText("");
		}
	}

	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;

	}

	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	public AdministrationGroupForm() {

		saveGroupNameButton.addClickHandler(new SaveRenameGroupClickhandler());
		deleteGroupButton.addClickHandler(new DeleteGroupClickHandler());
		createShoppingListButton.addClickHandler(new CreateShoppingListClickHandler());

		addUserButton.addClickHandler(new AddUserClickHandler());

		userTextBox.getElement().setPropertyString("placeholder", "Mitglied hinzufügen...");

		TextCell userTextCell = new TextCell();

		Column<User, String> stringColumn = new Column<User, String>(userTextCell) {
			@Override
			public String getValue(User user) {

				return user.getGmail();

			}
		};

		ButtonCell deleteButton = new ButtonCell();

		Column<User, String> deleteColumn = new Column<User, String>(deleteButton) {

			@Override
			public String getValue(User object) {
				// TODO Auto-generated method stub
				return "entfernen";
			}

		};

		deleteColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			public void update(int index, User user, String value) {

				dataProvider.getList().remove(user);

				AsyncCallback<Void> deleteCallback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Notification.show("Das Gruppenmitglied konnte nicht entfernt werden");

					}

					@Override
					public void onSuccess(Void result) {
						Notification.show("Das Gruppenmitglied wurde erfolgreich entfernt");

					}

				};
				elv.removeUserMembership(user, selectedGroup, deleteCallback);
			}
		});

		stringColumn.setFieldUpdater(new FieldUpdater<User, String>() {
			public void update(int index, User user, String value) {
				// Value is the textCell value. Object is the row object.
				// user.setName(value);
				user.setGmail(value);

			}

		});

		memberTable.addColumn(stringColumn, "Users");
		memberTable.addColumn(deleteColumn, "Mitglied entfernen");
		memberTable.setColumnWidth(stringColumn, 20, Unit.PC);

		dataProvider.addDataDisplay(memberTable);

	}

	/***********************************************************************
	 * onLoad-Methode
	 ***********************************************************************
	 */

	public void onLoad() {

		// GroupButtonsPanel
		hpButtonsPanelGroup.add(saveGroupNameButton);
		hpButtonsPanelGroup.add(deleteGroupButton);
		hpButtonsPanelGroup.add(createShoppingListButton);

		memberPanel.add(userTextBox);
		memberPanel.add(addUserButton);

		// scrollPanel.add(memberTable);

		// Add them to VerticalPanel
		boxPanel.add(groupLabel);
		boxPanel.add(renameTextBox);
		boxPanel.add(hpButtonsPanelGroup);

		boxPanel.add(membersLabel);
		boxPanel.add(memberPanel);
		boxPanel.add(memberTable);

		this.add(boxPanel);

		// Styling der Labels
		membersLabel.addStyleName("profilTitle");
		membersLabel.setHorizontalAlignment(ALIGN_LEFT);
		membersLabel.setWidth("100%");
		groupLabel.addStyleName("profilTitle");
		groupLabel.setHorizontalAlignment(ALIGN_LEFT);
		groupLabel.setWidth("100%");

		// Styling der Buttons
//		saveGroupNameButton.setPixelSize(130, 40);
//		deleteGroupButton.setPixelSize(130, 40);
//		createShoppingListButton.setPixelSize(130, 40);

		saveGroupNameButton.addStyleName("saveButton");
		deleteGroupButton.addStyleName("deleteButton");
		createShoppingListButton.addStyleName("createButton");

		addUserButton.addStyleName("createButton");

		// scrollPanel.setHeight("12");

		renameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveGroupNameButton.click();
					renameTextBox.setText("");
				}

			}
		});

		// Alle User einer Gruppe sollen in die CellTable geladen werden
		elv.getUsersByGroup(selectedGroup, new AsyncCallback<Vector<User>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler bei getUsersByGroup" + caught.getMessage());

			}

			@Override
			public void onSuccess(Vector<User> result) {
				// Window.alert("User wird angezeigt");

				for (User user : result) {

					if (list.contains(user)) {

						break;

					} else {
						list.add(user);

					}

				}

			}

		});

		// Alle User die im System vorhanden sind werden geladen
		elv.getAllUsers(new AsyncCallback<Vector<User>>() {

			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			public void onSuccess(Vector<User> result) {
				for (User u : result) {
					users.addElement(u);
					userOracle.add(u.getGmail());
				}

			}
		});

	}

	/***********************************************************************
	 * Methoden
	 ***********************************************************************
	 */

	/**
	 * Mit der privaten Klasse <code>DynamicTextbox</code> werden dynamische
	 * Textboxen definiert, die zusätzliche Attribute besitzen, die für den
	 * FieldVerifyer benötigt werden.
	 */

	private boolean checkTextboxesSaveableRename() {

		renameTextBox.setSaveable(verifier.checkValue(renameTextBox.getlabelText(), renameTextBox.getText()));
		if (renameTextBox.saveable == false) {
			return false;
		}
		return true;
	}

	/**
	 * Mit der Klasse <code>DynamicTextbox</code> werden dynamische Textboxen
	 * definiert.
	 */
	class DynamicTextbox extends TextBox {
		boolean saveable = true;
		String labelText;

		public boolean isSaveable() {
			return saveable;
		}

		public void setSaveable(boolean saveable) {
			this.saveable = saveable;
		}

		public String getlabelText() {
			return labelText;
		}

		public void setlabelText(String labelText) {
			this.labelText = labelText;
		}
	}

	/**
	 * Die Nested-Class <code>DeleteUserDialogBox</code> erstellt eine DialogBox die
	 * den User die Möglichkeit gibt, das Löschen der Gruppe nocheinmal zu
	 * überdenken.
	 */

	private class DeleteGroupDialogBox extends DialogBox {
		private VerticalPanel vPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Bist du dir Sicher, dass du diese Gruppe löschen möchtest?");
		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/**
		 * Der Konstruktor setzt die Stylings und die Gesamtzusammensetzung der
		 * DialogBox
		 */
		public DeleteGroupDialogBox() {
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

	/***********************************************************************
	 * CLICKHANDLER
	 ***********************************************************************/

	private class AddUserClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

//			elv.getUserByName(userTextBox.getValue(), new UserCallback());
//			userTextBox.setText("");
			elv.getUserByMail(userTextBox.getValue(), new UserCallback());
			userTextBox.setText("");

		}

	}

	private class UserCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("UserCallback funktioniert nicht");

		}

		@Override
		public void onSuccess(User result) {

			newGroupUser = result;
			result.getGmail();

			elv.addUser(newGroupUser, selectedGroup, new AddUserCallback());

			dataProvider.getList().add(newGroupUser);
			dataProvider.refresh();

		}

	}

	private class AddUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {

			Notification.show("Das Gruppenmitglied wurde erfolgreich hinzugefügt");

		}

	}

	private class CreateShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			RootPanel.get("details").clear();
			shoppingListCreationForm = new ShoppingListCreationForm();
			shoppingListCreationForm.setGsltvm(gsltvm);
			RootPanel.get("details").add(shoppingListCreationForm);

		}

	}

	/**
	 * Die Nested-Class <code>DeleteGroupClickHandler</code> implementiert das
	 * ClickHandler-Interface, welches eine Interaktion ermöglicht. Hier wird das
	 * Erscheinen der DeleteUserDialogBox ermöglicht.
	 * 
	 * @ center Centers the popup in the browser window and shows it. If the popup
	 * was already showing, then the popup is centered.
	 */
	private class DeleteGroupClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteGroupDialogBox deleteGroupDialogBox = new DeleteGroupDialogBox();
			deleteGroupDialogBox.center();
		}
	}

	/**
	 * Die Nested-Class <code>YesDeleteClickHandler</code> implementiert das
	 * ClickHandler-Interface und startet so die Lösch-Kaskade der Gruppe.
	 */
	private class YesDeleteClickHandler implements ClickHandler {
		private DeleteGroupDialogBox parentDUDB;

		// Konstruktor
		public YesDeleteClickHandler(DeleteGroupDialogBox dudb) {
			this.parentDUDB = dudb;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.parentDUDB.hide();
			// elv.delete(selectedGroup, new DeleteGroupCallback());
			if (selectedGroup == null) {
				Window.alert("Es wurde keine Gruppe ausgewählt");
			} else {

				elv.delete(selectedGroup, new DeleteGroupCallback());
			}

		}
	}

	/**
	 * Die Nested-Class <code>NoClickHandler</code> implementiert das
	 * ClickHandler-Interface, welches den Abbruchvorgang einleitet.
	 * 
	 */
	private class NoDeleteClickHandler implements ClickHandler {
		private DeleteGroupDialogBox parentDUDB;

		public NoDeleteClickHandler(DeleteGroupDialogBox dudb) {
			this.parentDUDB = dudb;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.parentDUDB.hide();
		}

	}

	/**
	 * Info: getGroupMethode fehlt noch Sobald das Textfeld ausgef?llt wurde, wird
	 * ein neue Gruppe nach dem Klicken des addButton erstellt.
	 */
	private class SaveRenameGroupClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			if (selectedGroup != null) {

				selectedGroup.setName(renameTextBox.getValue());

				// elv.save(selectedGroup, new SaveRenameGroupCallback());
				elv.save(selectedGroup, new SaveRenameGroupCallback());

			} else {
				Window.alert("Es wurde keine Gruppe ausgewhält");
			}

		}

	}

	/**
	 * Callback wird benötigt, um die Gruppe umzubenennen
	 */
	class SaveRenameGroupCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht umbenannt werden");
		}

		public void onSuccess(Void result) {
			Notification.show("Die Gruppe wurde erfolgreich umbenannt");

			gsltvm.updateGroup(selectedGroup);

		}
	}

	/*
	 * Callback wird benötigt, um die Gruppe zu löschen.
	 */
	private class DeleteGroupCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Gruppe wurde erfolgreich gelöscht");

			RootPanel.get("details").clear();
			GroupCreationForm gcf = new GroupCreationForm();
			RootPanel.get("details").add(gcf);
			gsltvm.removeGroup(selectedGroup);

		}

	}
}
