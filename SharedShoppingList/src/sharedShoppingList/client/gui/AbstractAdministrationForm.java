package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.shared.FieldVerifier;

/*
 * Die abstract Class <Code> AbstractAdministrationForm</code> dient dazu, dass die Klassen
 * ArticleForm, StoreForm und ListForm hiervon gemeinsam erben können. 
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
	protected TextBox nTextBox = new TextBox();
	protected TextBox unitTextBox = (createUnitTextBox());
	protected HorizontalPanel hpCreate = new HorizontalPanel();
	protected HorizontalPanel hpCancelandSafe = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameForm();
	protected abstract FlexTable createTable();
	protected abstract TextBox createUnitTextBox();

	// In dieser Methode werden die Widgets der Form hinzugefügt.
	public void onLoad() {

		hpCreate.add(nTextBox);
		hpCreate.add(unitTextBox);
		hpCreate.add(addButton);
		
		
		

		hpCancelandSafe.add(cancelButton);
		hpCancelandSafe.add(saveButton);
		hpCancelandSafe.add(deleteButton);

		this.add(NameLabel);
		this.add(hpCreate);
		this.add(administrationFlexTable);
		this.add(hpCancelandSafe);

		nTextBox.addStyleName("TextBox");
		addButton.addStyleName("Button");
		cancelButton.addStyleName("Button");
		saveButton.addStyleName("Button");
		deleteButton.addStyleName("Button");
		administrationFlexTable.addStyleName("FlexTable");
	}
	

}
