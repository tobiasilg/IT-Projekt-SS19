package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Formular für das Anlegen eines neuen Händlers im Datenstamm
 * 
 * @author moritzhampe
 *
 */

public class StoreForm extends VerticalPanel {

	// Erstellung der Panels
	private HorizontalPanel newStore = new HorizontalPanel();
	private HorizontalPanel deleteStore = new HorizontalPanel();

	// Erstellung der Widgets
	private Label storeLabel = new Label("STORES");
	private Label storeInFlexTableLabel = new Label("fehlt!!!");
	private FlexTable storeFlexTable = new FlexTable();
	private TextBox newStoreNameTextBox = new TextBox();
	private Button addArticleButton = new Button("+");
	private Button deleteStoreButton = new Button("-");
	private Button saveNewStoreButton = new Button("speichern");

	public void onLoad() {
		this.addStyleName("Forms");

		// Styling der Widgets
		storeLabel.addStyleName("formularHeading");
		newStoreNameTextBox.addStyleName("addTextbox");
		addArticleButton.addStyleName("addButton");
		deleteStoreButton.addStyleName("deleteButton");
		saveNewStoreButton.addStyleName("saveButton");
		storeFlexTable.addStyleName("FlexTable");

		// hinzufügen der Widgets zum newStore HorizontalPanel
		newStore.add(newStoreNameTextBox);
		newStore.add(addArticleButton);

		// hinzufügen der Widgets zum deleteStore HorizontalPanel
		deleteStore.add(storeInFlexTableLabel);
		deleteStore.add(deleteStoreButton);

		// hinzufügen der vorhandenen Stores und deleteButtons Zeile zur Flextable
		storeFlexTable.add(deleteStore);

		this.add(storeLabel);
		this.add(newStore);
		this.add(storeFlexTable);
		this.add(saveNewStoreButton);

	}

}
