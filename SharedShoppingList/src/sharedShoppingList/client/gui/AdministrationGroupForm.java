package sharedShoppingList.client.gui;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.client.gui.AdministrationShoppingListForm.RenameShoppingListCallback;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * 
 */

public class AdministrationGroupForm extends AbstractDialogCreationForm {

	SuggestBox suggestUser;
	FlexTable viewMembersFlexTable;
	Button deleteMembersButton;
	Button addMembersButton;
	HorizontalPanel createHpFirstButtonPanel;
	ArrayList<String> member = new ArrayList<>();
	User u = CurrentUser.getUser();

	protected Button deleteButton = new Button("Loeschen");

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

	protected SuggestBox suggestUser() {
		return suggestUser;
	}

	protected String nameThirdDialogForm() {
		return "Gruppenname ändern";
	}

	// @return 3 spaltige Flextable wird zurück gegeben
	protected FlexTable createTable() {

		if (viewMembersFlexTable == null) {
			viewMembersFlexTable = new FlexTable();

			viewMembersFlexTable.setText(0, 0, "Nickname");

		}
		return viewMembersFlexTable;
	}

	protected Button deleteButton() {
		return deleteMembersButton;
	}

	protected Button addButton() {
		return addMembersButton;
	}

	// Konstruktor
	public AdministrationGroupForm() {

		deleteButton.addClickHandler(new DeleteGroupClickHandler());
		saveButton.addClickHandler(new SaveRenameGroupClickhandler());
		cancelButton.addClickHandler(new CancelRenameGroupClickHandler());

	}

	/*
	 * Methode <code> onLoad </code> wird benötigt, sodass im Textfeld bereits der
	 * Gruppenname stteht.
	 */

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
	 * Die Nested-Class <code>DeleteUserClickHandler</code> implementiert das
	 * ClickHandler-Interface, welches eine Interaktion ermöglicht. Hier wird das
	 * Erscheinen der DeleteUserDialogBox ermöglicht.
	 */
	private class DeleteGroupClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteGroupDialogBox deleteGroupDB = new DeleteGroupDialogBox();
			deleteGroupDB.center();
		}
	}

	/**
	 * Die Nested-Class <code>YesDeleteClickHandler</code> implementiert das
	 * ClickHandler-Interface und startet so die Lösch-Kaskade der Gruppe.
	 */
	private class YesDeleteClickHandler implements ClickHandler {
		private DeleteGroupDialogBox parentDUDB;

		public YesDeleteClickHandler(DeleteGroupDialogBox dudb) {
			this.parentDUDB = dudb;
		}

		// Info: setGroupId Methode fehlt noch
		@Override
		public void onClick(ClickEvent event) {
			this.parentDUDB.hide();
			Group deletableGroup = new Group();
			deletableGroup.setGroupId(Integer.parseInt(Cookies.getCookie("groupId")));

			elv.delete(deletableGroup, new DeleteGroupCallback());
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
			Group group = new Group();

			elv.save(insertNameTextBox.getText(), new SaveRenameGroupCallback());

		}

	}

	/***********************************************************************
	 * CALLBACK
	 ***********************************************************************/

	/**
	 * Callback wird benötigt, um die Gruppe umzubenennen
	 */
	class SaveRenameGroupCallback implements AsyncCallback<Group> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht umbenannt werden");
		}

		public void onSuccess(Group Group) {
			Notification.show("Die Gruppe wurde erfolgreich umbenannt");
		}
	}

	/*
	 * Callback wird benötigt, um die Gruppe zu löschen.
	 */
	private class DeleteGroupCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppe konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Group group) {
			Notification.show("Die Gruppe wurde erfolgreich gelöscht");
		}
	}

	/*
	 * Callback wird benötigt, um ein Gruppenmitglied der Gruppe zu löschen.
	 */
	private class AddMemberCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppenmitlgied konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Group group) {
			Notification.show("Ein neues Gruppenmitglied wurde erfolgreich hinzugefügt");
		}
	}

	/*
	 * Callback wird benötigt, um ein Gruppenmitglieder aus der Gruppe zu entfernen.
	 */
	private class DeleteMemberCallback implements AsyncCallback<Group> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Das Gruppenmitglied konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Group group) {
			Notification.show("Das Gruppenmitglied wurde erfolgreich gelöscht");
		}
	}
}
