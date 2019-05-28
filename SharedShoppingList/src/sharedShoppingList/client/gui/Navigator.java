package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;

import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Group;

/*
 * Bildet die Navigationsleiste zum anzeigen und selektieren der Einkaufsliste, 
 * welche der Gruppe untergeordnet ist.
 */

public class Navigator extends FlowPanel implements TreeViewModel {

	private FlowPanel navPanel = new FlowPanel();
	private FlowPanel navImage = new FlowPanel();
	private FlowPanel buttonPanel = new FlowPanel();

	private Button neuButton = new Button("NEU");

	private Label navTitle = new Label("Meine Gruppen");

	// Erstellen des Images für Favorite Article
	Image star = new Image();

	private GroupCreationForm gcf; // Klasse die hinter dem NEU-Button steckt
	private FavoriteArticleForm faf; // Klasse die hinter dem Stern steckt

	private ListCreationForm listCreationForm; // Klasse die das anlegen einer Liste ermöglicht
	private AdministrationGroupForm groupForm; // Klasse die die Gruppe mit den Gruppenmitgliedern anzeigt
	private ShoppingListForm shoppingListForm; // Klasse die die Einkaufsliste der jeweiligen Gruppe anzeigt
	
	private EinkaufslistenverwaltungAsync einkaufslistenVerwaltung = null; 
	
	private ListDataProvider<Group> groupDataProvider = null;

	// public Navigator() {
	// super();
	// }

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

}
