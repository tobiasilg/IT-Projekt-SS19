package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.shared.FieldVerifier;

public abstract class AbstractDialogCreationForm extends VerticalPanel{
	private Label groupNameLabel = new Label(nameDialogForm());
	private DynamicTextbox insertNameTextBox = new DynamicTextbox();
	private Button cancelButton = new Button("abbrechen");
	private Button createButton = new Button("erstellen");
	private HorizontalPanel hp = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameDialogForm();

	// Konstruktor
	public AbstractDialogCreationForm() {

		createButton.addClickHandler(new CreateGroupClickHandler());
		hp.add(createButton);

		cancelButton.addClickHandler(new CancelClickHandler());
		hp.add(cancelButton);

	}

	/**
	 * Hiermit wird der Erstellvorgang eines neuen Kontaktes abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}

	}

	// In dieser Methode werden die Widgets der Form hinzugefügt.
	public void onLoad() {

		// Styling

	}

	/**
	 * Sobald das Textfeld ausgefüllt wurde, wird ein neue Gruppe nach dem Klicken
	 * des Bestätigungsbutton erstellt.
	 */
	private class CreateGroupClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * 
	 * Mit der privaten Klasse <code>DynamicTextbox</code> werden dynamische
	 * Textboxen definiert, die zusätzliche Attribute besitzen, die für den
	 * FieldVerifyer benötigt werden.
	 * 
	 */

	private boolean checkTextboxesSaveable() {

		insertNameTextBox
				.setSaveable(verifier.checkValue(insertNameTextBox.getlabelText(), insertNameTextBox.getText()));

		if (insertNameTextBox.saveable == false) {
			return false;
		}

		return true;

	}

	private class DynamicTextbox extends TextBox {

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
