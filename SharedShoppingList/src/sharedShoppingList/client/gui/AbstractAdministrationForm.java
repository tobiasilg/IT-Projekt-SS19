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
 */

public abstract class AbstractAdministrationForm extends VerticalPanel {

	private Label NameLabel = new Label(nameForm());
	protected FlexTable administrationFlexTable = (createTable());
	protected Button cancelButton = new Button("abbrechen");
	protected Button saveButton = new Button("speichern");
	protected Button addButton = new Button("hinzufuegen");
	protected Button deleteButton = new Button("loeschen");
	protected TextBox nameTextBox = new TextBox();
	protected ListBox unitListBox = (createUnitListBox());
	protected HorizontalPanel hpCreate = new HorizontalPanel();
	protected HorizontalPanel hpCancelandSafe = new HorizontalPanel();

	// Pr�fen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameForm();

	protected abstract FlexTable createTable();

	protected abstract ListBox createUnitListBox();

	// In dieser Methode werden die Widgets der Form hinzugef�gt.
	public void onLoad() {

		hpCreate.add(nameTextBox);
		if (unitListBox != null) {
			hpCreate.add(unitListBox);
		}
		hpCreate.add(addButton);

		hpCancelandSafe.add(cancelButton);
		hpCancelandSafe.add(saveButton);
		hpCancelandSafe.add(deleteButton);

		this.add(NameLabel);
		this.add(hpCreate);
		this.add(administrationFlexTable);
		this.add(hpCancelandSafe);

		nameTextBox.addStyleName("TextBox");
		addButton.addStyleName("Button");
		cancelButton.addStyleName("Button");
		saveButton.addStyleName("Button");
		deleteButton.addStyleName("Button");
		administrationFlexTable.addStyleName("FlexTable");

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
