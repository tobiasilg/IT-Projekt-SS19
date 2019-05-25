package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.shared.FieldVerifier;

/* Die abstract Class <CodeAbstractDialogCreationForm</code> dient dazu, dass die Klassen
 * GroupCreationForm und ListCrationForm hiervon gemeinsam erben können. 
 * Erst wenn alle abstrakten Methoden der Superklasse implementiert worden sind,
 * kann die Subklasse konkret instanziiert werden. 
*/

public abstract class AbstractDialogCreationForm extends VerticalPanel {
	
	

	protected Label nameLabel = new Label(nameDialogForm());
	protected DynamicTextbox insertNameTextBox = new DynamicTextbox();
	// protected TextBox insertNameTextBox = new TextBox();
	protected Button cancelButton = new Button("abbrechen");
	protected Button saveButton = new Button("speichern");
	protected HorizontalPanel hpButtonPanel = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameDialogForm();

	// In dieser Methode werden die Widgets der Form hinzugefügt sowie das Styling
	// durchgeführt
	public void onLoad() {
		hpButtonPanel.add(saveButton);
		hpButtonPanel.add(cancelButton);

		// Add them to VerticalPanel
		this.add(nameLabel);
		this.add(insertNameTextBox);
		this.add(hpButtonPanel);

		nameLabel.addStyleName("name_label");
		cancelButton.addStyleName("cancel_button");
		saveButton.addStyleName("save_button");
		hpButtonPanel.setSpacing(20);
		cancelButton.setPixelSize(130, 40);
		saveButton.setPixelSize(130, 40);
		
//		hpButtonPanel.setCellHorizontalAlignment(saveButton, ALIGN_CENTER);
//		hpButtonPanel.setCellHorizontalAlignment(cancelButton, ALIGN_CENTER);
		
	
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