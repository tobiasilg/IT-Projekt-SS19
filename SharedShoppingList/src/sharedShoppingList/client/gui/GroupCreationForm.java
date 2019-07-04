package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen Gruppe im Datenstamm
 * 
 * @author nicolaifischbach + moritzhampe
 * 
 */

public class GroupCreationForm extends FlowPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User user = CurrentUser.getUser();

	GroupShoppingListTreeViewModel gsltvm;

	private FlowPanel groupBox = new FlowPanel();
	private FlowPanel buttonPanel = new FlowPanel();

	private Label groupLabel = new Label("Neue Gruppe");
	private Label insertLabel = new Label("Gruppenname eingeben: ");

	private TextBox groupNameTextBox = new TextBox();

	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");
	

	// Konstruktor
	public GroupCreationForm() {

		saveButton.addClickHandler(new SaveGroupCreationClickHandler());
		cancelButton.addClickHandler(new CancelGroupCreationClickHandler());

	}

	public void onLoad() {
		
		gsltvm = new GroupShoppingListTreeViewModel();

		groupBox.addStyleName("profilBox");
		groupLabel.addStyleName("profilTitle");
		insertLabel.addStyleName("profilLabel");
		groupNameTextBox.addStyleName("profilTextBox");

		buttonPanel.addStyleName("profilPanel");

		saveButton.addStyleName("saveButton");
		cancelButton.addStyleName("deleteButton");

		groupNameTextBox.getElement().setPropertyString("placeholder", "Gruppenname...");

		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);

		groupBox.add(groupLabel);
		groupBox.add(insertLabel);
		groupBox.add(groupNameTextBox);
		groupBox.add(buttonPanel);

		this.add(groupBox);

		groupNameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveButton.click();
					groupNameTextBox.setText("");
				}

			}

		});

	}

	public void setGroupShoppingListTreeViewModel(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}

	public GroupShoppingListTreeViewModel getGroupShoppingListTreeViewModel() {
		return gsltvm;
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
	 * des saveButtons erstellt.
	 */
	class SaveGroupCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// String groupName = groupNameTextBox.getText();

			if (groupNameTextBox.getValue() == "") {

				Window.alert("Die Gruppe muss einen Namen besitzen !");
			} else {

//				groupForm = new AdministrationGroupForm();
	
				elv.createGroup(user, groupNameTextBox.getValue(), new GroupCreationCallback());


			}
		}

	}

	/**
	 * Callback wird benötigt, um die Gruppe zu erstellen
	 */
	class GroupCreationCallback implements AsyncCallback<Group> {
		
		Group group = null;
		
		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(Group result) {

		//	Notification.show(String.valueOf(result.getName()));

			RootPanel.get("details").clear();
			RootPanel.get("navigator").clear();
			Navigator nav = new Navigator();
			RootPanel.get("navigator").add(nav);
			group = result;
			AdministrationGroupForm groupForm = new AdministrationGroupForm();
			groupForm.setSelected(group);
			RootPanel.get("details").add(groupForm);
			
			Notification.show(group.getName());
			
	//		gsltvm.addGroup(group);
			
		}
	}
}
