package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

import de.hdm.thies.bankProjekt.shared.bo.Customer;
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

	/*
	 * Klassen auf die im Navigator verwiesen werden
	 */
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
	/*
	 * Getter und Setter für Group ArrayList
	 */

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;

	}
	/*
	 * Setter für die Forms
	 */

	void setGroupForm(AdministrationGroupForm groupForm) {
		this.groupForm = groupForm;
	}

	void setShoppingListForm(ShoppingListForm shoppingListForm) {
		this.shoppingListForm = shoppingListForm;
	}

	/*
	 * Getter und Setter für SelectedList und SelectedGroup
	 */
	ShoppingList getSelectedList() {
		return selectedList;
	}

	void setSelectedList(ShoppingList sl) {
		RootPanel.get("details").clear();
		selectedList = sl;
		// shoppingListForm.setSelected(sl); --> Methode muss noch in ShoppingListForm
		// erstellt werden !
		RootPanel.get("details").add(shoppingListForm);
		
		// Hier muss getShoppingListByGroup hin !!!
		//einkaufslistenVerwaltung.getShoppingListByGroup(sl.getId(), new AsyncCallback<Group>() {

		//	public void onFailure(Throwable caught) {
		//		Notification.show("Der Callback um Gruppe zu finden funktioniert nicht");
		//	}

		//	public void onSuccess(Group group) {
		//		selectedGroup = group;
		//		 shoppingListForm.setSelectedGroup(selectedGroup);
		//	}
		//});
	}

	Group getSelectedGroup() {
		return selectedGroup;
	}

	void setSelectedGroup(Group g) {
		RootPanel.get("derails").clear();
		selectedGroup = g;
		// groupForm.setSelected(g);--> Methode muss noch in GroupForm erstellt werden !
		RootPanel.get("details").add(groupForm);
		selectedList = null;
	}

	/*
	 * Die Methode addGroup ermöglicht das hinzufügen eines neuen Gruppenobjekts als
	 * Knoten in den Tree
	 */
	void addGroup(Group g) {
		List<Group> listofGroups = groupDataProvider.getList();
		listofGroups.add(g);
		this.getGroups().add(g);
		selectionModel.setSelected(g, true);
		groupDataProvider.refresh();

	}

	/*
	 * Die Methode updateGroup dient dazu eine Gruppe mit der selben id zu refreshen
	 */
	void updateGroup(Group group) {
		List<Group> groupList = groupDataProvider.getList();
		int i = 0;
		for (Group g : groupList) {
			if (g.getId() == group.getId()) {
				groupList.set(i, group);
				break;
			} else {
				i++;
			}
		}
		groupDataProvider.refresh();
	}

	void removeGroup(Group group) {
		groupDataProvider.getList().remove(group);
		shoppingListDataProviders.remove(group);
	}

	/*
	 * Die Methode addShoppingListOfGroup dient dem Hinzufügen einer ShoppingList
	 * der entsprechenden Gruppe
	 */
	void addShoppingListOfGroup(ShoppingList list, Group group) {
		// Prüfen, ob die Gruppe schon eine ShoppingList besitzt
		// Falls nicht, wird auch nichts geöffnet
		if (!shoppingListDataProviders.containsKey(group)) {
			return;
		}
		ListDataProvider<ShoppingList> listProvider = shoppingListDataProviders.get(group);
		if (!listProvider.getList().contains(list)) {
			listProvider.getList().add(list);
		}
		selectionModel.setSelected(list, true);
	}

	void removeShoppingListOfGroup(ShoppingList list, Group group) {
		// Prüfen, ob die Gruppe schon eine ShoppingList besitzt
		// Falls nicht, wird auch nichts geöffnet
		if (!shoppingListDataProviders.containsKey(group)) {
			return;
		}
		shoppingListDataProviders.get(group).getList().remove(list);
		selectionModel.setSelected(group, true);
	}

	void updateShoppingList(ShoppingList sl) {
		// Hier muss getGroupByID hin!!!
	//	einkaufslistenVerwaltung.getGroupByUser(sl.getGroupId(), new UpdateShoppingListCallback(sl));
		
	}

	private class UpdateShoppingListCallback implements AsyncCallback<Group> {

		ShoppingList list = null;

		UpdateShoppingListCallback(ShoppingList sl) {
			list = sl;
		}

		@Override
		public void onFailure(Throwable t) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(Group group) {
			List<ShoppingList> shoppingListList = shoppingListDataProviders.get(group).getList();

			for (int i = 0; i < shoppingListList.size(); i++) {
				if (list.getId() == shoppingListList.get(i).getId()) {
					shoppingListList.set(i, list);
					break;
				}
			}
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
	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 * 
	 * Die Klasse onLoad wird für die Buttons und das Nav Label benötigt,
	 * da diese immer aufgerufen werden, der Tree aber nicht !
	 */
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

}
