package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
//import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentGroup;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.client.gui.ShoppingListCreationForm.DynamicTextbox;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.FieldVerifier;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Löschen und Umbenennen einer ShoppingListe im Datenstamm
 * 
 * 
 */

public class AdministrationShoppingListForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User u = CurrentUser.getUser();
	ShoppingList l = new ShoppingList();

	private Label nameLabel = new Label("Shoppingliste verwalten");
	private DynamicTextbox textBox = new DynamicTextbox();
	private Button saveButton = new Button("speichern");
	private Button deleteButton = new Button("ShoppingListe loeschen");
	private Button cancelButton = new Button("abbrechen");
	private HorizontalPanel hpButtons = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */
	public AdministrationShoppingListForm() {

		deleteButton.addClickHandler(new DeleteShoppingListClickhandler());
		saveButton.addClickHandler(new SaveClickhandler());
		cancelButton.addClickHandler(new CancelClickHandler());

	}

	/***********************************************************************
	 * onLoad-Methode
	 ***********************************************************************
	 */

	public void onLoad() {
		hpButtons.add(saveButton);
		hpButtons.add(cancelButton);
		hpButtons.add(deleteButton);

		// Add them to VerticalPanel
		this.setWidth("100%");
		this.add(nameLabel);
		this.add(textBox);
		this.add(hpButtons);

		nameLabel.addStyleName("name_label");
		textBox.addStyleName("addUsersTextBox_textBox");
		cancelButton.addStyleName("cancel_button");
		saveButton.addStyleName("save_button");

		hpButtons.setSpacing(20);
		cancelButton.setPixelSize(130, 40);
		saveButton.setPixelSize(130, 40);

		// aktueller Name der Shoppingliste wird in der TextBox angezeigt
		 //textBox.getElement().setPropertyString(l.getShoppingListName());

		/*
		 * Mit dem Enter-Button kann ebenfalls die Speicherfunktion ausgeführt werden.
		 * Zugleich wird das Eingabefeld geleert.
		 */
		textBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveButton.click();
					textBox.setText("");
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
	private boolean checkTextboxesSaveable() {

		textBox.setSaveable(verifier.checkValue(textBox.getlabelText(), textBox.getText()));
		if (textBox.saveable == false) {
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

	/***********************************************************************
	 * ClickHandler
	 ***********************************************************************
	 */

	/**
	 * Hiermit wird der Vorgang, die Gruppe umzubenennen, abgebrochen.
	 */
	class CancelClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();
		}
	}

	/**
	 * 
	 * *
	 */
	private class SaveClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.save(l, new RenameShoppingListCallback());
		}
	}

	/*
	 * Hiermit wird durch das Drücken des LöschButtons die ShoppingListe gelöscht.
	 */
	private class DeleteShoppingListClickhandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.delete(l, new DeleteShoppingListCallback());

		}

	}

	/***********************************************************************
	 * AsyncCallbacks
	 ***********************************************************************
	 */

	/**
	 * Callback wird benötigt, um die Shoppingliste umzubenennen
	 */
	class RenameShoppingListCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Shoppingliste konnte nicht umbenannt werden");
		}

		public void onSuccess(Void shoppinglist) {
			Notification.show("Die Shoppingliste wurde erfolgreich umbenannt");
		}
	}

	/*
	 * Callback wird benötigt, um die Shoppingliste zu löschen.
	 */
	private class DeleteShoppingListCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Shoppingliste konnte nicht gelöscht werden");
		}

		@Override
		public void onSuccess(Void shoppinglist) {
			Notification.show("Die Shoppingliste wurde erfolgreich gelöscht");
		}
	}

}
