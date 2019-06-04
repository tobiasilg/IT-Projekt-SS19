package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.FieldVerifier;

/* Die abstract Class <CodeAbstractDialogCreationForm</code> dient dazu, dass die Klassen
 * GroupCreationForm und ListCrationForm hiervon gemeinsam erben können. 
 * Erst wenn alle abstrakten Methoden der Superklasse implementiert worden sind,
 * kann die Subklasse konkret instanziiert werden. 
*/

public abstract class AbstractDialogCreationForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	protected Label nameLabel = new Label(nameDialogForm());
	protected Label nameSecondLabel = new Label(nameSecondDialogForm());
	protected Label nameThirdLabel = new Label(nameThirdDialogForm());
	protected TextBox addUsersTextBox = (addUsersTextBox());
 
	protected Button addButton = (addButton());
	protected Button deleteButton = (deleteButton());
	protected HorizontalPanel hpfirstButtonPanel = (createHpFirstButtonPanel());
	protected FlexTable flexTable = (createTable());

	protected DynamicTextbox insertNameTextBox = new DynamicTextbox();
	protected Button cancelButton = new Button("abbrechen");
	protected Button saveButton = new Button("speichern");
	protected HorizontalPanel hpButtonPanel = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameDialogForm();

	protected abstract String nameSecondDialogForm();

	protected abstract String nameThirdDialogForm();

	protected abstract FlexTable createTable();

	protected abstract Button addButton();

	protected abstract Button deleteButton();

	protected abstract HorizontalPanel createHpFirstButtonPanel();
	
	protected abstract TextBox addUsersTextBox();

	// In dieser Methode werden die Widgets der Form hinzugefügt sowie das Styling
	// durchgeführt
	public void onLoad() {
		hpfirstButtonPanel.add(addButton);
		hpfirstButtonPanel.add(deleteButton);
		hpButtonPanel.add(saveButton);
		hpButtonPanel.add(cancelButton);

		// Add them to VerticalPanel
		this.add(nameLabel);
		this.add(nameSecondLabel);
		this.add(flexTable);
		
		this.add(hpfirstButtonPanel);
		this.add(nameThirdLabel);
		this.add(insertNameTextBox);
		this.add(hpButtonPanel);
		
		addUsersTextBox.getElement().setPropertyString("placeholder", "User eingeben ");
		insertNameTextBox.getElement().setPropertyString("placeholder", "User eingeben ");

		
		nameLabel.addStyleName("name_label");
		nameSecondLabel.addStyleName("nameSecond_label");
		nameThirdLabel.addStyleName("nameThird_labe");
		addUsersTextBox.addStyleName("addUsersTextBox_textBox");
		addButton.addStyleName("add_Button");
		deleteButton.addStyleName("delte_button");
		cancelButton.addStyleName("cancel_button");
		saveButton.addStyleName("save_button");

		hpfirstButtonPanel.setSpacing(20);
		hpButtonPanel.setSpacing(20);
		cancelButton.setPixelSize(130, 40);
		saveButton.setPixelSize(130, 40);
		addButton.setPixelSize(130, 40);
		deleteButton.setPixelSize(130, 40);

//		hpButtonPanel.setCellHorizontalAlignment(saveButton, ALIGN_CENTER);
//		hpButtonPanel.setCellHorizontalAlignment(cancelButton, ALIGN_CENTER);

		/*
		 * Mit dem Enter-Button kann ebenfalls die Speicherfunktion ausgeführt werden.
		 * Zugleich wird das Eingabefeld geleert.
		 */
		insertNameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					saveButton.click();
					insertNameTextBox.setText("");
				}

			}
		});
		
		addUsersTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					addButton.click();
					insertNameTextBox.setText("");
				}

			}
		});

	}

	
	
	

	/**
	 * Mit der privaten Klasse <code>DynamicTextbox</code> werden dynamische
	 * Textboxen definiert, die zusätzliche Attribute besitzen, die für den
	 * FieldVerifyer benötigt werden.
	 */
	private boolean checkTextboxesSaveable() {

		insertNameTextBox
				.setSaveable(verifier.checkValue(insertNameTextBox.getlabelText(), insertNameTextBox.getText()));
		if (insertNameTextBox.saveable == false) {
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
}