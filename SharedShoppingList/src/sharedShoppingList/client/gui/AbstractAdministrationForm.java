package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.shared.FieldVerifier;

/*
 * Die abstract Class <Code> AbstractAdministrationForm</code> dient dazu, dass die Klassen
 * ArticleForm, StoreForm und ListForm hiervon gemeinsam erben k�nnen. 
 * Erst wenn alle abstrakten Methoden der Superklasse implementiert worden sind, 
 * kann die Subklasse konkret werden (instanziiert werden).
 * @author patrick
 */

public abstract class AbstractAdministrationForm extends VerticalPanel {

	private Label nameLabel = new Label(nameForm());
	protected TextBox nameTextBox = new TextBox();
	protected ListBox unitListBox = createUnitListBox();
	protected FlexTable administrationFlexTable = createTable();
	protected Button cancelButton = new Button("abbrechen");
	protected Button saveButton = new Button("Änderungen speichern");
	protected Button addButton = new Button("hinzufuegen");

	protected HorizontalPanel hpCreate = new HorizontalPanel();
	protected HorizontalPanel hpCancelandSafe = new HorizontalPanel();

	// Pr�fen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameForm();

	protected abstract FlexTable createTable();

	protected abstract ListBox createUnitListBox();

	// In dieser Methode werden die Widgets der Form hinzugef�gt.
	public void onLoad() {

		this.setWidth("100%");

		nameTextBox.getElement().setPropertyString("placeholder", "Neu... ");

		hpCreate.add(nameTextBox);
		if (unitListBox != null) {
			hpCreate.add(unitListBox);
		}
		hpCreate.add(addButton);

		hpCancelandSafe.add(cancelButton);
		hpCancelandSafe.add(saveButton);

		this.add(nameLabel);
		this.add(hpCreate);
		this.add(administrationFlexTable);
		this.add(hpCancelandSafe);

//		this.setStylePrimaryName("details");

		nameTextBox.addStyleName("TextBox");
		addButton.addStyleName("Button");
		cancelButton.addStyleName("Button");
		saveButton.addStyleName("speicherProfilButton.gwt-Button");
		administrationFlexTable.addStyleName("FlexTable");
		nameLabel.addStyleName("profilTitle");

		nameLabel.setHorizontalAlignment(ALIGN_CENTER);
		nameLabel.setWidth("100%");

		hpCreate.setWidth("50%");
		hpCreate.setCellHorizontalAlignment(nameTextBox, ALIGN_LEFT);
		hpCreate.setCellHorizontalAlignment(addButton, ALIGN_LEFT);
		hpCreate.setCellHorizontalAlignment(unitListBox, ALIGN_LEFT);
		hpCreate.setCellWidth(nameTextBox, "300");

		administrationFlexTable.setWidth("70%");
		administrationFlexTable.setBorderWidth(2);
		administrationFlexTable.setSize("100%", "100%");

		administrationFlexTable.setCellPadding(10);

		nameTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					addButton.click();
					nameTextBox.setText("");
				}

			}
		});
	}

}
