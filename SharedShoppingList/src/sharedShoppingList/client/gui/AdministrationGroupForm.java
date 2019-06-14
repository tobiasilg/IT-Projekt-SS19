package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
//import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentGroup;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.FieldVerifier;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Einsehen von Gruppenmitglieder, Hinzufügen von Usern, ändern
 * des Gruppennamens, Gruppe löschen
 * 
 * @author nicolaifischbach 
 * 
 */

public class AdministrationGroupForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User u = CurrentUser.getUser();
	//Group g = CurrentGroup.getGroup();

	Group selectedGroup = null;
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();

	private Label firstNameLabel = new Label("Gruppenverwaltung");
	private Label secondNameLabel = new Label("Mitgliederverwaltung");
	private Label thirdNameLabel = new Label("Gruppenname ändern");

	private DynamicTextbox addUsersTextBox = new DynamicTextbox();
	private DynamicTextbox renameTextBox = new DynamicTextbox();

	private FlexTable viewMembersFlexTable;

	private Button addMembersButton = new Button("hinzufügen");
	private Button deleteGroupButton = new Button("loeschen");
	private Button saveGroupNameButton = new Button("speichern");

	private HorizontalPanel hpButtonsPanelViewMembers = new HorizontalPanel();
	private HorizontalPanel hpButtonsPanelGroup = new HorizontalPanel();
	private ArrayList<User> groupMembers;

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	public AdministrationGroupForm() {

		addMembersButton.addClickHandler(new AddMemberClickHandler());
		deleteGroupButton.addClickHandler(new DeleteGroupClickHandler());
		saveGroupNameButton.addClickHandler(new SaveRenameGroupClickhandler());
	}

	/***********************************************************************
	 * onLoad-Methode
	 ***********************************************************************
	 */

	public void onLoad() {
		hpButtonsPanelViewMembers.add(addUsersTextBox);
		hpButtonsPanelViewMembers.add(addMembersButton);
		hpButtonsPanelGroup.add(saveGroupNameButton);
		hpButtonsPanelGroup.add(deleteGroupButton);

		// Add them to VerticalPanel
		this.setWidth("100%");
		this.add(firstNameLabel);
		this.add(secondNameLabel);
		this.add(viewMembersFlexTable);
		this.add(hpButtonsPanelViewMembers);
		this.add(thirdNameLabel);
		this.add(renameTextBox);
		this.add(hpButtonsPanelGroup);

		firstNameLabel.addStyleName("name_label");
		secondNameLabel.addStyleName("name_label");
		thirdNameLabel.addStyleName("name_label");

		viewMembersFlexTable.setWidth("70%");
		viewMembersFlexTable.setBorderWidth(2);
		viewMembersFlexTable.setSize("100%", "100%");
		viewMembersFlexTable.setCellPadding(10);

		hpButtonsPanelViewMembers.setSpacing(20);
		hpButtonsPanelGroup.setSpacing(20);

		addMembersButton.setPixelSize(130, 40);
		saveGroupNameButton.setPixelSize(130, 40);
		deleteGroupButton.setPixelSize(130, 40);

		// aktueller Name der Gruppe wird in der TextBox angezeigt
		
		//renameTextBox.getElement().setPropertyString(g.getName());

		/*
		 * Mit dem Enter-Button kann ebenfalls die Speicherfunktion ausgeführt werden.
		 * Zugleich wird das Eingabefeld geleert.
		 */
		renameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveGroupNameButton.click();
					renameTextBox.setText("");
				}

			}
		});

		addUsersTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					addMembersButton.click();
					addUsersTextBox.setText("");
				}

			}
		});

	}

	public Group getSelected() {
		return selectedGroup;
	}

	public void setSelected(Group group) {
		selectedGroup = group;

	}
	
	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;
		
	}
	
	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
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

	private boolean checkTextboxesSaveableMember() {

		addUsersTextBox.setSaveable(verifier.checkValue(addUsersTextBox.getlabelText(), addUsersTextBox.getText()));
		if (addUsersTextBox.saveable == false) {
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

	// Erstellt eie Flextable, falls nicht existent
	// @return 3 spaltige Flextable wird zurück gegeben
	protected FlexTable createTable() {

		if (viewMembersFlexTable == null) {
			viewMembersFlexTable = new FlexTable();

			viewMembersFlexTable.setText(0, 0, "Nickname");
			viewMembersFlexTable.setText(0, 1, "");
		}

		groupMembers = new ArrayList<User>();

		// Lädt alle Gruppenmitglieder aus der Datenbank
		elv.getUsersByGroup(selectedGroup, new AsyncCallback<Vector<User>>() {

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
		//
		DynamicTextbox memberTextBox = new DynamicTextbox();
		memberTextBox.setText(user.getName());

		// Erstellt einen Lösch-Button (x)
		CustomButton removeMemberButton = new CustomButton();
		removeMemberButton.setUser(user);

		removeMemberButton.addClickHandler(new RemoveMemberClickhandler(removeMemberButton));

		// Füge die TextBox und die ListBox in die FlexTable ein
		viewMembersFlexTable.setWidget(rowCount, 0, memberTextBox);
		viewMembersFlexTable.setWidget(rowCount, 1, removeMemberButton);
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
	 * Auf Mehtode warten *
	 */

	private class CustomButton extends Button {
		User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}

	private class RemoveMemberClickhandler implements ClickHandler {

		private CustomButton cB;

		public RemoveMemberClickhandler(CustomButton cB) {

			this.cB = cB;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (cB != null) {

				elv.delete(cB.getUser(), new DeleteMemberCallback());

			} else {
				Notification.show("Das Gruppenmitglied konnte nicht gelöscht werden");
			}
		}
	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer User nach dem Klicken
	 * des addButton der Gruppe hinzugefügt.
	 */
	private class AddMemberClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			User user = new User();

			user.setName(addUsersTextBox.getValue());
			setContentOfviewMembersFlexTable(user);

			// Persistiere in die Datenbank
			// elv.save(user.getName(), new AddMemberCallback());
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
			elv.delete(selectedGroup, new DeleteGroupCallback());
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

//	/**
//	 * Hiermit wird der Vorgang, die Gruppe umzubenennen, abgebrochen.
//	 */
//	class CancelRenameGroupClickHandler implements ClickHandler {
//		public void onClick(ClickEvent event) {
//			RootPanel.get("details").clear();
//		}
//	}

	/**
	 * Info: getGroupMethode fehlt noch Sobald das Textfeld ausgef�llt wurde, wird
	 * ein neue Gruppe nach dem Klicken des addButton erstellt.
	 */
	private class SaveRenameGroupClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			if (selectedGroup != null) {

				selectedGroup.setName(renameTextBox.getText());

				elv.save(selectedGroup, new SaveRenameGroupCallback());

			} else {
				Window.alert("Es wurde keine Gruppe ausgewhält");
			}

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

			gsltvm.updateGroup(selectedGroup);

		}
	}

	/*
	 * Callback wird benötigt, um ein Mitglieder aus der Gruppe zu entfernen.
	 */

	private class DeleteMemberCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			groupMembers.clear();
			createTable();
		}
	}

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

}
