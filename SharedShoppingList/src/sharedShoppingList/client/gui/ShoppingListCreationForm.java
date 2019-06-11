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
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.client.gui.AbstractDialogCreationForm.DynamicTextbox;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.FieldVerifier;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/**
 * Formular für das Anlegen einer neuen ShoppingListe im Datenstamm
 * 
 * 
 */

public class ShoppingListCreationForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	User u = CurrentUser.getUser();

	private Label nameLabel = new Label("Neue Shoppingliste erstellen");
	private DynamicTextbox textBox = new DynamicTextbox();
	private Button saveButton = new Button("speichern");
	private Button cancelButton = new Button("abbrechen");
	private HorizontalPanel hpCancelAndSave = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

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
		hpCancelAndSave.add(saveButton);
		hpCancelAndSave.add(cancelButton);

		// Add them to VerticalPanel
		this.setWidth ("100%");
		this.add(nameLabel);
		this.add(textBox);
		this.add(hpCancelAndSave);

		textBox.getElement().setPropertyString("placeholder", "Namen eingeben... ");

		nameLabel.addStyleName("name_label");
		textBox.addStyleName("addUsersTextBox_textBox");
		cancelButton.addStyleName("cancel_button");
		saveButton.addStyleName("save_button");

		hpCancelAndSave.setSpacing(20);
		cancelButton.setPixelSize(130, 40);
		saveButton.setPixelSize(130, 40);

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
	 * Hiermit wird der Erstellvorgang einer neuen Liste abbgebrochen.
	 */
	private class CancelListCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neue Liste nach dem Klicken
	 * des addButton erstellt.
	 */
	private class SaveListCreationClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.createShoppingList(textBox.getText(), new ListCreationCallback());

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
		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Gruppen konnte nicht erstellt werden");
		}

		@Override
		public void onSuccess(ShoppingList shoppingList) {
			Notification.show("Die Gruppe wurde erfolgreich erstellt");
		}
	}

}
