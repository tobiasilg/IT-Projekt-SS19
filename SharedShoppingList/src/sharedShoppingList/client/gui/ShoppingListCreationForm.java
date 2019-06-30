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
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen ShoppingListe im Datenstamm
 * 
 * @author nicolaifischbach
 * 
 *         Info: Anbindung dan Tree fehlt noch
 * 
 */

public class ShoppingListCreationForm extends FlowPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User user = CurrentUser.getUser();

	GroupShoppingListTreeViewModel gsltvm = null;
	private Group selectedGroup = null;
	private ShoppingList selectedShoppingList = null;
	ShoppingListForm showForm = null;
	AdministrationGroupForm groupForm = null;

	private FlowPanel shoppingListPanel = new FlowPanel();
	private FlowPanel buttonPanel = new FlowPanel();

	private Label shoppingListLabel = new Label("Neue Shoppingliste");
	private Label insertLabel = new Label("ShoppingList-Name eingeben:");

	// private DynamicTextbox shoppingListNameTextBox = new DynamicTextbox();
	private TextBox shoppingListNameTextBox = new TextBox();

	private Button saveButton = new Button("Speichern");
	private Button cancelButton = new Button("Abbrechen");

	// Prüfen des Eingabefelds auf richtige Zeichensetzung

	/*
	 * 
	 */
//	private FieldVerifier verifier = new FieldVerifier();

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	public ShoppingListCreationForm() {

		saveButton.addClickHandler(new SaveListCreationClickHandler());
		cancelButton.addClickHandler(new CancelListCreationClickHandler());
	}

	/***********************************************************************
	 * onLoad-Methode
	 ***********************************************************************
	 */

	public void onLoad() {

		shoppingListPanel.addStyleName("profilBox");
		shoppingListLabel.addStyleName("profilTitle");
		insertLabel.addStyleName("profilLabel");
		shoppingListNameTextBox.addStyleName("profilTextBox");

		saveButton.addStyleName("saveNewGrouButton");
		cancelButton.addStyleName("cancelNewGroupButton");

		shoppingListNameTextBox.getElement().setPropertyString("placeholder", "Shoppingliste... ");

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		shoppingListPanel.add(shoppingListLabel);
		shoppingListPanel.add(insertLabel);
		shoppingListPanel.add(shoppingListNameTextBox);
		shoppingListPanel.add(buttonPanel);

		this.add(shoppingListPanel);

		/*
		 * Mit dem Enter-Button kann ebenfalls die Speicherfunktion ausgeführt werden.
		 * Zugleich wird das Eingabefeld geleert.
		 */
		shoppingListNameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveButton.click();
					shoppingListNameTextBox.setText("");
				}

			}
		});

	}

	/***********************************************************************
	 * Methoden
	 ***********************************************************************
	 */

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;

	}

	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;

	}

	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}

	/**
	 * Mit der privaten Klasse <code>DynamicTextbox</code> werden dynamische
	 * Textboxen definiert, die zusätzliche Attribute besitzen, die für den
	 * FieldVerifyer benötigt werden.
	 */
//	private boolean checkTextboxesSaveable() {
//
//		shoppingListNameTextBox.setSaveable(
//				verifier.checkValue(shoppingListNameTextBox.getlabelText(), shoppingListNameTextBox.getText()));
//		if (shoppingListNameTextBox.saveable == false) {
//			return false;
//		}
//		return true;
//	}

	/**
	 * Mit der Klasse <code>DynamicTextbox</code> werden dynamische Textboxen
	 * definiert.
	 */
//	class DynamicTextbox extends TextBox {
//		boolean saveable = true;
//		String labelText;
//
//		public boolean isSaveable() {
//			return saveable;
//		}
//
//		public void setSaveable(boolean saveable) {
//			this.saveable = saveable;
//		}
//
//		public String getlabelText() {
//			return labelText;
//		}
//
//		public void setlabelText(String labelText) {
//			this.labelText = labelText;
//		}
//	}

	/***********************************************************************
	 * ClickHandler
	 ***********************************************************************
	 */

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Liste abbgebrochen.
	 */
	private class CancelListCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			if (gsltvm.getSelectedGroup() != null) {

				RootPanel.get("details").clear();
				groupForm = new AdministrationGroupForm();
				groupForm.setSelected(selectedGroup);
				groupForm.setGsltvm(gsltvm);
				gsltvm.setGroupForm(groupForm);
				RootPanel.get("details").add(groupForm);
			}
		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neue Liste nach dem Klicken
	 * des addButton erstellt.
	 */
	private class SaveListCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			selectedGroup = gsltvm.getSelectedGroup();

			if (shoppingListNameTextBox.getValue() == "") {
				Window.alert("Einkaufsliste muss einen Namen besitzen !");

			} else {
//				 elv.createShoppingList(shoppingListNameTextBox.getValue(), new
//				 ListCreationCallback());

				elv.createShoppingList(shoppingListNameTextBox.getValue(), selectedGroup,
						new ListCreationCallback(selectedGroup));

			}
		}
	}

	/***********************************************************************
	 * AsyncCallbacks
	 ***********************************************************************
	 */

	/**
	 * Callback wird benötigt, um eine Liste zu erstellen
	 */
	private class ListCreationCallback implements AsyncCallback<ShoppingList> {

		Group group = null;

		public ListCreationCallback(Group selectedGroup) {
			group = selectedGroup;
		}

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Shoppingliste konnte nicht erstellt werden");

		}

		@Override
		public void onSuccess(ShoppingList result) {

			Notification.show(String.valueOf(result.getId()));

			RootPanel.get("details").clear();
			selectedShoppingList = result;
			showForm.setSelected(selectedShoppingList);
			showForm.setSelected(gsltvm.getSelectedGroup());
			RootPanel.get("details").add(showForm);

			gsltvm.setSelectedList(selectedShoppingList);
			gsltvm.getSelectionModel().setSelected(selectedShoppingList, true);

			gsltvm.addShoppingListOfGroup(selectedShoppingList, group);

		}

	}
}
