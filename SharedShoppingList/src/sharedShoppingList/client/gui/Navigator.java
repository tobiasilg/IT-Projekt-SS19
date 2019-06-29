package sharedShoppingList.client.gui;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.User;

/*
 * Bildet die Navigationsleiste zum anzeigen und selektieren der Einkaufsliste, 
 * welche der Gruppe untergeordnet ist.
 */

public class Navigator extends FlowPanel {
	

	private User user = CurrentUser.getUser();
	private EinkaufslistenverwaltungAsync einkaufslistenVerwaltung = ClientsideSettings.getEinkaufslistenverwaltung();

	private FlowPanel navPanel = new FlowPanel();
	private FlowPanel navImage = new FlowPanel();
	private FlowPanel buttonPanel = new FlowPanel();
	private FlowPanel refreshPanel = new FlowPanel();

	private Button neuButton = new Button("NEU");

	private Label navTitle = new Label("Meine Gruppen");

	// Erstellen des Images für Favorite Article
	Image star = new Image();

	/*
	 * Klassen auf die im Navigator verwiesen werden
	 */
	private GroupCreationForm gcf; // Klasse die hinter dem NEU-Button steckt
	private FavoriteArticleForm faf; // Klasse die hinter dem Stern steckt
	
	private AdministrationGroupForm agf;
	private ShoppingListForm sf;

	private Group selectedGroup = null;
	private ShoppingList selectedList = null;
	
	private GroupShoppingListTreeViewModel gsltvm;
	
	private CellTree tree;
	
	private Label refreshLabel = new Label();
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 * 
	 * Die Klasse onLoad wird für die Buttons und das Nav Label benötigt, da diese
	 * immer aufgerufen werden, der Tree aber nicht !
	 */
	public void onLoad() {
		
		final Timer timer = new Timer() {

			@Override
			public void run() {
				Navigator.this.refreshInfo();
				schedule(10000);
				
			}
			
		};
		// solange soll abgewartet werden, bis der Timer abläuft
		timer.schedule(10000);
		
		agf = new AdministrationGroupForm();
		sf = new ShoppingListForm();
		
		gsltvm = new GroupShoppingListTreeViewModel();
		
		tree = new CellTree(gsltvm, "Root");
		
		// Zusammenführen der Forms für den Tree
		gsltvm.setGroupForm(agf);
		agf.setGsltvm(gsltvm);
		
		gsltvm.setShoppingListForm(sf);
		sf.setGsltvm(gsltvm);
		
		tree.setAnimationEnabled(true);

		star.setUrl("/images/star.png");

		// Vergeben der Stylenamen
		this.addStyleName("nav");

		star.addStyleName("navIcon");
		neuButton.addStyleName("navButton");
		navTitle.addStyleName("navTitle");
		navPanel.addStyleName("navPanel");
		navImage.addStyleName("navImage");
		buttonPanel.addStyleName("buttonPanel");
		refreshLabel.addStyleName("refreshLabel");
		refreshPanel.addStyleName("refreshPanel");
		tree.addStyleName("navTree");

		navImage.add(star);
		buttonPanel.add(neuButton);
		refreshPanel.add(refreshLabel);

		navPanel.add(buttonPanel);
		navPanel.add(navImage);

		this.add(navPanel);
		this.add(refreshPanel);
		this.add(navTitle);
		
		this.add(tree);

		// Hinzufügen der ClickHandler
		neuButton.addClickHandler(new ShowGroupCreationForm());
		star.addClickHandler(new ShowFavoriteArticleForm());

	}
	
	public void setGsltvm(GroupShoppingListTreeViewModel gsltvm) {
		this.gsltvm = gsltvm;
	}
	
	public GroupShoppingListTreeViewModel getGsltvm() {
		return gsltvm;
		
	}
	
	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	public Group getSelectedGroup() {
		return selectedGroup;
		
	}
	
	public void setSelectedList(ShoppingList selectedList) {
		this.selectedList = selectedList;
	}
	
	public ShoppingList getSelectedList() {
		return selectedList;
	}
	protected void refreshInfo() {
		
		einkaufslistenVerwaltung.changed(this.getGsltvm().getSelectedGroup(), user, new RefreshGroupCallback());
		
	}

	private class RefreshGroupCallback implements AsyncCallback<Boolean>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Refresh Methode greift noch nicht");
			
		}

		@Override
		public void onSuccess(Boolean result) {
			// TODO Auto-generated method stub
			if (result == true ) {
				refreshLabel.setText("Die Anwendung wurde aktualisiert");
			}
			else {
				refreshLabel.setText("Es gibt zurzeit keine Änderungen");
			}
		}
		
	}
	/*
	 * Die Klasse ShowGroupCreationForm ermöglicht die Weiterletung zur
	 * GroupCreationForm
	 */
	private class ShowGroupCreationForm implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			gcf = new GroupCreationForm();
			gcf.setGroupShoppingListTreeViewModel(gsltvm);
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
