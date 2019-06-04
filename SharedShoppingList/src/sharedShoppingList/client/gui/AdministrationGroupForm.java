package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentGroup;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.client.gui.AdministrationShoppingListForm.RenameShoppingListCallback;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * @author nicolaifischbach
 * 
 */

public class AdministrationGroupForm extends AbstractDialogCreationForm {

	TextBox addUsersTextBox;
	FlexTable viewMembersFlexTable;
	Button deleteMembersButton = new Button("x");
	Button addMembersButton = new Button("speichern");
	HorizontalPanel createHpFirstButtonPanel;
	ArrayList<User> groupMembers;

	User u = CurrentUser.getUser();
	Group g = CurrentGroup.getGroup();

	protected HorizontalPanel createHpFirstButtonPanel() {
		return createHpFirstButtonPanel;
	}

	@Override
	protected String nameDialogForm() {
		return "Gruppenverwaltung";
	}

	protected String nameSecondDialogForm() {
		return "Mitgliederverwaltung";
	}

	protected TextBox addUsersTextBox() {
		return addUsersTextBox;
	}

	protected Button deleteButton() {
		return deleteMembersButton;
	}

	protected Button addButton() {
		return addMembersButton;
	}

	protected String nameThirdDialogForm() {
		return "Gruppenname ändern";
	}

	// Erstellt eie Flextable, falls nicht existent
	// @return 3 spaltige Flextable wird zurück gegeben
	protected FlexTable createTable() {

		if (viewMembersFlexTable == null) {
			viewMembersFlexTable = new FlexTable();

			viewMembersFlexTable.setText(0, 0, "Nickname");
		}

		groupMembers = new ArrayList<User>();

		// Lädt alle Gruppenmitglieder aus der Datenbank
		elv.getUsersByGroup(g, new AsyncCallback<Vector<User>>() {

			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			public void onSuccess(Vector<User> result) {
				// Füge alle Elemente der Datenbank in die Liste hinzu
				for (User user : result) {
					groupMembers.add(user);
					setContentOfviewMembersFlexTable(user);
				}
				Notification.show("success");

			}
		});

		return viewMembersFlexTable;
	}

	private void setContentOfviewMembersFlexTable(User user) {
		// Hole die Zeilennummer, die aktuell bearbeitet wird
		int rowCount = viewMembersFlexTable.getRowCount();

		// Erstellt eine neue Textbox für eigetragene User und setze den Namen
		// iF-Abfrage?
		addUsersTextBox = new TextBox();
		addUsersTextBox.setText(user.getName());

		// Füge die TextBox und die ListBox in die FlexTable ein
		viewMembersFlexTable.setWidget(rowCount, 0, addUsersTextBox);
		viewMembersFlexTable.setWidget(rowCount, 1, deleteMembersButton);
	}

	// Konstruktor
	public AdministrationGroupForm() {

		deleteMembersButton.addClickHandler(new deleteMembersClickHandler());
		addMembersButton.addClickHandler(new AddMemberClickHandler());
		deleteButton.addClickHandler(new DeleteGroupClickHandler());
		saveButton.addClickHandler(new SaveRenameGroupClickhandler());
		cancelButton.addClickHandler(new CancelRenameGroupClickHandler());

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
			abfrage.addStyleName("label has-text-primary content_margin");
			jaButton.addStyleName("button is-danger");
			neinButton.addStyleName("button bg-primary has-text-white");

			jaButton.addClickHandler(new YesDeleteClickHandler(this));
			neinButton.addClickHandler(new CancelDeleteClickHandler(this));

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

	
	/**
	 * Auf Mehtode warten
	 * 	 *
	 */
	private class deleteMembersClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des addButton erstellt.
	 */
	private class AddMemberClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			u.setName(addUsersTextBox.getValue());
			setContentOfviewMembersFlexTable(u);

			// Persistiere in die Datenbank
			// auf Methode warten
			//elv.save(addUsersTextBox.getName(), new AddMemberCallback());
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
			elv.delete(g, new DeleteGroupCallback());
		}
	}

	/**
	 * Die Nested-Class <code>NoClickHandler</code> implementiert das
	 * ClickHandler-Interface, welches den Abbruchvorgang einleitet.
	 * 
	 */
	private class CancelDeleteClickHandler implements ClickHandler {
		private DeleteGroupDialogBox parentDUDB;

		public CancelDeleteClickHandler(DeleteGroupDialogBox dudb) {
			this.parentDUDB = dudb;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.parentDUDB.hide();
		}

	}

	/**
	 * Die Nested-Class <code>DeleteUserCallbac</code> implementiert einen
	 * AsyncCallback und ermöglicht das Löschen des Users in der DB.
	 */

	/**
	 * Hiermit wird der Vorgang, die Gruppe umzubenennen, abgebrochen.
	 */
	class CancelRenameGroupClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
		}
	}

	/**
	 * Info: getGroupMethode fehlt noch Sobald das Textfeld ausgef�llt wurde, wird
	 * ein neue Gruppe nach dem Klicken des addButton erstellt.
	 */
	private class SaveRenameGroupClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			g.setName(insertNameTextBox.getText());

			elv.save(g, new SaveRenameGroupCallback());

		}

	}

	/***********************************************************************
	 * CALLBACK
	 ***********************************************************************/

	/**
	 * Callback wird benötigt, um die Gruppe umzubenennen
	 */
	class SaveRenameGroupCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht umbenannt werden");
		}

		public void onSuccess(Void Group) {
			Notification.show("Die Gruppe wurde erfolgreich umbenannt");
		}
	}

	/*
	 * Callback wird benötigt, um ein User der Gruppe hinzuzufügen
	 */
	private class AddMemberCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der User konnte nicht hinzugefuegt werden");
		}

		@Override
		public void onSuccess(Void user) {
			Notification.show("Der User wurde erfolgreich hinzugefuegt");
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
		public void onSuccess(Void group) {
			Notification.show("Die Gruppe wurde erfolgreich gelöscht");
		}
	}

	/*
	 * Callback wird benötigt, um ein Gruppenmitglied aus der Gruppe zu entfernen.
	 */
	private class DeleteMemberCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Gruppenmitglied konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void user) {
			Notification.show("Das Gruppenmitglied wurde erfolgreich gelöscht");
		}
	}

	

}
