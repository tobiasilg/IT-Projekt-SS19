package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * @author nicolaifischbach
 * 
 */

public class GroupCreationForm extends AbstractAdministrationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User u = CurrentUser.getUser();
	
	private Navigator n = null; 
	private AdministrationGroupForm showGroupForm = null;
	private Group groupToDisplay = null;
	

	// Konstruktor
	public GroupCreationForm() {

		saveButton.addClickHandler(new SaveGroupCreationClickHandler());
		cancelButton.addClickHandler(new CancelGroupCreationClickHandler());
		
	}


	@Override
	protected String nameForm() {
		// TODO Auto-generated method stub
		return "Meine Gruppe erstellen";
	}

	@Override
	protected ListBox createUnitListBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FlexTable createTable() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Gruppe abbgebrochen.
	 */
	private class CancelGroupCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();

		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neue Gruppe nach dem Klicken
	 * des addButton erstellt.
	 */
	private class SaveGroupCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		//	elv.createGroup(insertNameTextBox.getText(), new GroupCreationCallback());

		}

	}

	/**
	 * Callback wird benötigt, um die Gruppe zu erstellen
	 */
	private class GroupCreationCallback implements AsyncCallback<Group> {
		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Group event) {
			Notification.show("Die Gruppe wurde erfolgreich erstellt");
		}
	}
	
}


