package sharedShoppingList.client.gui;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class GroupShoppingListTreeViewModel implements TreeViewModel{
	
	private User user = CurrentUser.getUser();
	private EinkaufslistenverwaltungAsync einkaufslistenVerwaltung = null;

	private AdministrationGroupForm groupForm; // Klasse die die Gruppe mit den Gruppenmitgliedern anzeigt
	private ShoppingListForm shoppingListForm; // Klasse die die Einkaufsliste der jeweiligen Gruppe anzeigt

//	private ArrayList<Group> groups = new ArrayList<Group>();

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
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
				
			} if (bo instanceof Group) {
				return new Integer(bo.getId());
			} else {
				return new Integer(-bo.getId());
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
	public GroupShoppingListTreeViewModel() {
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

//	public ArrayList<Group> getGroups() {
//		return groups;
//	}

//	public void setGroups(ArrayList<Group> groups) {
//		this.groups = groups;

//	}
	/*
	 * Setter für die Forms
	 */

	void setGroupForm(AdministrationGroupForm groupForm) {
		this.groupForm = groupForm;
	}

	void setShoppingListForm(ShoppingListForm shoppingListForm) {
		this.shoppingListForm = shoppingListForm;
	}
	
	Group getSelectedGroup() {
		return selectedGroup;
	}

	void setSelectedGroup(Group g) {
		RootPanel.get("details").clear();
		selectedGroup = g;
		groupForm.setSelected(g);
		selectedList = null;
		RootPanel.get("details").add(groupForm);
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
		shoppingListForm.setSelected(sl); 
		RootPanel.get("details").add(shoppingListForm);

		// Funktioniert das so...?
		if (sl != null) {

			einkaufslistenVerwaltung.getGroupById(sl.getGroupId(), new AsyncCallback<Group>() {

				public void onFailure(Throwable caught) {
					Notification.show("Der Callback um die Gruppe zu finden funktioniert nicht");
				}

				public void onSuccess(Group group) {
					selectedGroup = group;
					shoppingListForm.setSelectedGroup(selectedGroup);
				}
			});
		}
	}


	/*
	 * Die Methode addGroup ermöglicht das hinzufügen eines neuen Gruppenobjekts als
	 * Knoten in den Tree
	 */
	void addGroup(Group group) {
		//List<Group> listofGroups = groupDataProvider.getList();
		//listofGroups.add(g);
		//this.getGroups().add(g);
		//selectionModel.setSelected(g, true);
		//groupDataProvider.refresh();
		
		groupDataProvider.getList().add(group);
		selectionModel.setSelected(group, true);

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

		einkaufslistenVerwaltung.getGroupById(sl.getId(), new UpdateShoppingListCallback(sl));

	}

	private class UpdateShoppingListCallback implements AsyncCallback<Group> {

		ShoppingList list = null;

		UpdateShoppingListCallback(ShoppingList sl) {
			list = sl;
		}

		@Override
		public void onFailure(Throwable t) {
			Notification.show("Folgender Fehler: \n" + t.toString());

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

	/*
	 * Die Methode getNodeInfo ermöglicht die Rückgabe des jeweiligen
	 * untergeordneten Wertes.
	 */

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {
			
			groupDataProvider = new ListDataProvider<Group>();

			einkaufslistenVerwaltung.getGroupByUser(user, new AsyncCallback<Group>() {

				@Override
				public void onFailure(Throwable t) {
					Notification.show("Folgender Fehler: \n" + t.toString());

				}

				@Override
				public void onSuccess(Group group) {
					//GroupShoppingListTreeViewModel.this.getGroups().add(result);
					groupDataProvider.getList().add(group);
			
				}

			});
			// Zurückgeben des Knotenpunktes, wobei die Daten der Zelle gepaart werden
			return new DefaultNodeInfo<Group>(groupDataProvider, new GroupCell(), selectionModel, null);
		}

		if (value instanceof Group) {
			// Erzeugen eines ListDataProviders für die ShoppingList Daten
			final ListDataProvider<ShoppingList> listProvider = new ListDataProvider<ShoppingList>();
			shoppingListDataProviders.put((Group) value, listProvider);
			
			einkaufslistenVerwaltung.getAllByGroup((Group) value, new AsyncCallback<Vector<ShoppingList>>() {

				@Override
				public void onFailure(Throwable t) {
					Notification.show("Folgender Fehler: \n" + t.toString());

				}

				@Override
				public void onSuccess(Vector<ShoppingList> shoppingLists) {
					for (ShoppingList s : shoppingLists) {
						listProvider.getList().add(s);

					}

				}

			});
			return new DefaultNodeInfo<ShoppingList>(listProvider, new ShoppingListCell(), selectionModel, null);
		}

		return null;
	}

	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return (value instanceof ShoppingList);
	}

}
