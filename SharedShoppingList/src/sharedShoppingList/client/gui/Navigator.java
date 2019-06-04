package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.BusinessObject;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/*
 * Bildet die Navigationsleiste zum anzeigen und selektieren der Einkaufsliste, 
 * welche der Gruppe untergeordnet ist.
 */

public class Navigator extends FlowPanel implements TreeViewModel {

	private User user = CurrentUser.getUser();
	private EinkaufslistenverwaltungAsync einkaufslistenVerwaltung = null;

	private FlowPanel navPanel = new FlowPanel();
	private FlowPanel navImage = new FlowPanel();
	private FlowPanel buttonPanel = new FlowPanel();

	private Button neuButton = new Button("NEU");

	private Label navTitle = new Label("Meine Gruppen");

	// Erstellen des Images für Favorite Article
	Image star = new Image();

	private GroupCreationForm gcf; // Klasse die hinter dem NEU-Button steckt
	private FavoriteArticleForm faf; // Klasse die hinter dem Stern steckt

	private ShoppingListCreationForm listCreationForm; // Klasse die das anlegen einer Liste ermöglicht
	private AdministrationGroupForm groupForm; // Klasse die die Gruppe mit den Gruppenmitgliedern anzeigt
	private ShoppingListForm shoppingListForm; // Klasse die die Einkaufsliste der jeweiligen Gruppe anzeigt

	private ArrayList<Group> groups = new ArrayList<Group>();

	private Group selectedGroup = null;
	private ShoppingList selectedList = null;
	private ListDataProvider<Group> groupDataProvider = null;

	/*
	 * in der Map werden die ListDataProviders für die Einkaufslisten der Gruppen
	 * gemerkt
	 */
	private Map<Group, ListDataProvider<ShoppingList>> shoppingListDataProviders = null;

	/*
	 * Die Nested Class dient zur Abbilding der BusinessObjects als einduetige
	 * Zahlenobjekte. Die Zahlenobkjekte dienen als Schlüssen der Baumknoten
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {

		@Override
		public Object getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			} else {
				return bo.getId(); // id der Gruppe wird zurückgegeben
			}

		}

	};

	private BusinessObjectKeyProvider boKeyProvider = null;

	// SelectionModel wird für die Auswahl innerhalb einer Liste benötigt
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	/*
	 * Die Nested Class dient dafür um auf die Auswahl eines Baumknotens zu
	 * reagieren. Je nach Auswahl wird entweder "selectedGroup" oder "selectedList"
	 * gesetzt.
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			// Wenn die Auswahl instanz einer Gruppe ist, wird sie auf selectedGroup gesetzt
			if (selection instanceof Group) {
				setSelectedGroup((Group) selection);
				// Ansonsten auf selectedList
			} else if (selection instanceof ShoppingList) {
				setSelectedList((ShoppingList) selection);

			}

		}
	}

	// Konstruktor dient zur Initialisierung der lokalen Variablen, sprich ein Wert
	// wird den Variablen zugewiesen
	public Navigator() {
		einkaufslistenVerwaltung = ClientsideSettings.getEinkaufslistenverwaltung();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		shoppingListDataProviders = new HashMap<Group, ListDataProvider<ShoppingList>>();

	}

	/*
	 * Getter und Setter für Selection Model
	 */
	public SingleSelectionModel<BusinessObject> getSelectionModel() {
		return selectionModel;
	}

	public void setSelectionModel(SingleSelectionModel<BusinessObject> selectionModel) {
		this.selectionModel = selectionModel;
	}

	private void setSelectedList(ShoppingList selection) {
		// TODO Auto-generated method stub

	}

	private void setSelectedGroup(Group selection) {
		// TODO Auto-generated method stub

	}

	public void onLoad() {

		star.setUrl("/images/star.png");

		// Vergeben der Stylenamen
		this.addStyleName("nav");

		star.addStyleName("navIcon");
		neuButton.addStyleName("navButton");
		navTitle.addStyleName("navTitle");
		navPanel.addStyleName("navPanel");
		navImage.addStyleName("navImage");
		buttonPanel.addStyleName("buttonPanel");

		navImage.add(star);
		buttonPanel.add(neuButton);

		navPanel.add(buttonPanel);
		navPanel.add(navImage);

		this.add(navPanel);
		this.add(navTitle);

		// Hinzufügen der ClickHandler
		neuButton.addClickHandler(new ShowGroupCreationForm());
		star.addClickHandler(new ShowFavoriteArticleForm());
	}

	/*
	 * Die Klasse ShowGroupCreationForm ermöglicht die Weiterletung zur
	 * GroupCreationForm
	 */
	private class ShowGroupCreationForm implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			RootPanel.get("details").clear();
			gcf = new GroupCreationForm();
			RootPanel.get("details").add(gcf);
		}

	}

	/*
	 * Die Klasse ShowFavoriteArticleForm ermöglicht die Weiterletung zur
	 * FavoriteArticleForm
	 */
	private class ShowFavoriteArticleForm implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			faf = new FavoriteArticleForm();
			RootPanel.get("details").add(faf);

		}

	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		ListDataProvider<String> dataProvider = new ListDataProvider<String>();
		for (int i = 0; i < 2; i++) {
			dataProvider.getList().add(value + " " + String.valueOf(i));
		}
		return new DefaultNodeInfo<String>(dataProvider, new TextCell());
	}

	@Override
	public boolean isLeaf(Object value) {
		// The maximum length of a value is ten characters.
		return value.toString().length() > 10;
	}

}
