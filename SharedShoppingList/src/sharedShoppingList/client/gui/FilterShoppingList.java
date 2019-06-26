package sharedShoppingList.client.gui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

public class FilterShoppingList extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private GroupShoppingListTreeViewModel gsltvm = new GroupShoppingListTreeViewModel();
	private final MultiSelectionModel<ListEntry> multiSelectionModel = new MultiSelectionModel<ListEntry>();

	private Group selectedGroup = null;
	private ShoppingList selectedShoppingList = null;
	private ListEntry selectedListEntry = null;
	private ShoppingListForm slf = null;

	// private Vector<Vector<Object>> entries = new Vector<Vector<Object>>();

	private Label infoTitleLabel = new Label();

	private Button saveSlButton = new Button("Änderungen speichern");
	private Button deleteSlButton = new Button("Einkaufsliste löschen");
	private Button createShoppingListButton = new Button("Listeneintrag erstellen");
	private Button filterByUserButton = new Button("Filtern nach User");
	private ListBox filterByStoreListBox = new ListBox();

	private HorizontalPanel firstRowPanel = new HorizontalPanel();
	private HorizontalPanel filterPanel = new HorizontalPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	private VerticalPanel cellTableVP = new VerticalPanel();

	private TextBox renameTextBox = new TextBox();

	// Create a data provider.
	private ListDataProvider<ListEntry> dataProvider = new ListDataProvider<ListEntry>();
	private List<ListEntry> list = dataProvider.getList();
	private CellTable<ListEntry> cellTable = new CellTable<ListEntry>(KEY_PROVIDER);

	/**
	 * The key provider that allows us to identify Contacts even if a field changes.
	 * We identify contacts by their unique ID.
	 */
	private static final ProvidesKey<ListEntry> KEY_PROVIDER = new ProvidesKey<ListEntry>() {
		public Object getKey(ListEntry le) {
			return le.getId();
		}
	};

	/***********************************************************************
	 * Konstruktor
	 ***********************************************************************
	 */

	public FilterShoppingList() {

		saveSlButton.addClickHandler(new RenameShoppingListClickHandler());
		deleteSlButton.addClickHandler(new DeleteShoppingListClickHandler());
		createShoppingListButton.addClickHandler(new CreateShoppingListClickHandler());
		// CreateShoppingListClickHandler());
	}

	/***********************************************************************
	 * Clickhandler
	 ***********************************************************************
	 */

	private class CreateShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			RootPanel.get("details").clear();
			NewListEntryForm nlef = new NewListEntryForm();
			RootPanel.get("details").add(nlef);

		}

	}

	private class RenameShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			if (renameTextBox.getValue() == "") {
				Window.alert("Die Einkaufsliste muss einen Namen besitzen!");

			} else {
				selectedShoppingList.setName(renameTextBox.getValue());
				elv.save(selectedShoppingList, new RenameShoppingListCallback());
			}

		}
	}

	private class DeleteShoppingListClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			DeleteShoppingListDialogBox deleteShoppingListDialogBox = new DeleteShoppingListDialogBox();
			deleteShoppingListDialogBox.center();
		}
	}

	private class DeleteShoppingListDialogBox extends DialogBox {
		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label abfrage = new Label("Bist Du Dir sicher die Einkaufsliste zu löschen?");
		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/*
		 * Konstruktor
		 */

		public DeleteShoppingListDialogBox() {
			abfrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(abfrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			jaButton.addClickHandler(new JaDeleteListClickHandler(this));
			neinButton.addClickHandler(new NeinDeleteListClickHandler(this));
		}
	}

	private class JaDeleteListClickHandler implements ClickHandler {

		private DeleteShoppingListDialogBox deleteShoppingListDialogBox;

		public JaDeleteListClickHandler(DeleteShoppingListDialogBox deleteShoppingListDialogBox) {
			this.deleteShoppingListDialogBox = deleteShoppingListDialogBox;

		}

		public void onClick(ClickEvent event) {
			this.deleteShoppingListDialogBox.hide();

			elv.delete(selectedShoppingList, new JaDeleteListCallback());
		}
	}

	private class NeinDeleteListClickHandler implements ClickHandler {

		private DeleteShoppingListDialogBox deleteShoppingListDialogBox;

		public NeinDeleteListClickHandler(DeleteShoppingListDialogBox deleteShoppingListDialogBox) {
			this.deleteShoppingListDialogBox = deleteShoppingListDialogBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.deleteShoppingListDialogBox.hide();

		}

	}

	/***********************************************************************
	 * Callbacks
	 ***********************************************************************
	 */

	private class JaDeleteListCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {

			Notification.show("Die Einkaufsliste konnte nicht gelöscht werden");

		}

		public void onSuccess(Void result) {

			Notification.show("Die Einkaufsliste wurde erfolgreich gelöscht");

			gsltvm.removeShoppingListOfGroup(selectedShoppingList, selectedGroup);

		}
	}

	private class RenameShoppingListCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Einkaufsliste konnte nicht umbenannt werden");

		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Die Einkaufsliste wurde erfolgreich umbenannt");

			gsltvm.updateShoppingList(selectedShoppingList);

		}

	}

}

/***********************************************************************
 * Getter und Setter
 ***********************************************************************
 */
