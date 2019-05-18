package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarePraktikum.client.gui.Header.HomeClickHandler;
import de.hdm.softwarePraktikum.client.gui.Header.LogoutClickHandler;
import de.hdm.softwarePraktikum.client.gui.Header.ReportGeneratorClickHandler;
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
	protected Button safeButton = new Button("speichern");
	protected Button addButton = new Button("Hinzufügen");
	protected SuggestBox suggestBox = new SuggestBox();
	protected HorizontalPanel hpCreate = new HorizontalPanel();
	protected HorizontalPanel hpCancelandSafe = new HorizontalPanel();

	// Prüfen des Eingabefelds auf richtige Zeichensetzung
	private FieldVerifier verifier = new FieldVerifier();

	protected abstract String nameDialogForm();

	// In dieser Methode werden die Widgets der Form hinzugefügt.
	public void onLoad() {

			hpCreate.add(suggestBox);
			hpCreate.add(addButton);
			
			hpCancelandSafe.add(cancelButton);
			hpCancelandSafe.add(safeButton);
			
			this.add(hpCreate);
			this.add(administrationFlexTable);
			this.add(hpCancelandSafe);
			
			NameLabel.setStylePrimaryName("Namelabel");
		
		
			
			
	}
	
	logoutButton.addClickHandler(new LogoutClickHandler());
	editorButton.addClickHandler(new HomeClickHandler());
	reportGeneratorButton.addClickHandler(new ReportGeneratorClickHandler() );
}
