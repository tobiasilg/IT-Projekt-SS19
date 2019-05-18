package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import sharedShoppingList.shared.FieldVerifier;

/*
 * Die abstract Class <Code> AbstractAdministrationForm</code> dient dazu, dass die Klassen
 * ArticleForm, StoreForm und ListForm hiervon gemeinsam erben können. 
 * Erst wenn alle abstrakten Methoden der Superklasse implementiert worden sind, 
 * kann die Subklasse konkret werden (instanziiert werden).
 */

public class AbstractAdministrationForm extends VerticalPanel {

	private Label NameLabel = new Label(nameDialogForm());
	protected FlexTable administrationFlexTable = new FlexTable();
	protected Button cancelButton = new Button("abbrechen");
	protected Button saveButton = new Button("speichern");
	protected Button addButton = new Button("Hinzufügen");
	protected SuggestBox nameSuggestBox = new SuggestBox();
	protected HorizontalPanel hpCreate = new HorizontalPanel();
	protected HorizontalPanel hpCancelandSafe = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameDialogForm();

	// In dieser Methode werden die Widgets der Form hinzugefügt.
	public void onLoad() {

		hpCreate.add(nameSuggestBox);
		hpCreate.add(addButton);

		hpCancelandSafe.add(cancelButton);
		hpCancelandSafe.add(saveButton);

		this.add(hpCreate);
		this.add(administrationFlexTable);
		this.add(hpCancelandSafe);

		NameLabel.setStylePrimaryName("NameLabel");
		administrationFlexTable.setStylePrimaryName("FlexTable");
		cancelButton.setStylePrimaryName("CancelButton");
		saveButton.setStylePrimaryName("SaveButton");
		addButton.setStylePrimaryName("AddButton");
		nameSuggestBox.setStylePrimaryName("SuggestBox");

		hpCreate.setCellHorizontalAlignment(nameSuggestBox, ALIGN_LEFT);
		hpCreate.setCellHorizontalAlignment(addButton, ALIGN_RIGHT);
		hpCancelandSafe.setCellHorizontalAlignment(cancelButton, ALIGN_CENTER);
		hpCancelandSafe.setCellHorizontalAlignment(saveButton, ALIGN_CENTER);
		this.setCellHorizontalAlignment(hpCreate, ALIGN_CENTER);
		this.setCellHorizontalAlignment(hpCancelandSafe, ALIGN_CENTER);
		

	}
	
	

}



