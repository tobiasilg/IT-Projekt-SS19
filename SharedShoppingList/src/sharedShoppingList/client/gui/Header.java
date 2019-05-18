package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;



public class Header extends FlowPanel {

	EinkaufslistenverwaltungAsync einkaufslistenverwaltung = null;

	// Erstellung der Header Divs
	private FlowPanel headerLeft = new FlowPanel(); // Panel indem das Label liegt
	private FlowPanel headerRight = new FlowPanel();// Panel indem die Buttonspanels liegen
	private FlowPanel userIcon = new FlowPanel(); // Panel indem das Logout Icon liegt

	// Erstellung der Button und Label Divs
	private FlowPanel title = new FlowPanel(); // Panel indem das titleLabel liegt
	private FlowPanel report = new FlowPanel(); // Panel indem der reportButton liegt
	private FlowPanel article = new FlowPanel();// Panel indem der articleButton liegt
	private FlowPanel store = new FlowPanel(); // Panel indem der storeButton liegt

	// Erstellung der Buttons und Label
	private Label titleLabel = new Label("KEKWAY");
	private Button reportButton = new Button("Report");
	private Button articleButton = new Button("Artikel");
	private Button storeButton = new Button("Store");

	// Erstellen des Logout Icons
	Image user = new Image();

	public Header() {
		super();
	}

	/*
	 * Die Methode onLoad wird beim Aufrufen der Header Klasse aufgurufen und
	 * ausgeführt In dieser Klasse erfolgt die Zuordnung der Widgets
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	public void onLoad() {

		einkaufslistenverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();

		/*
		 * Vergeben von Stylenamen, um die Divs über CSS ansprechen zu können
		 */

		// Gesamtes Header Div
		this.addStyleName("header");

		// Einzelteile des Headers
		headerLeft.addStyleName("header-left");
		headerRight.addStyleName("header-right");
		
		userIcon.addStyleName("user-icon-element");
		
		title.addStyleName("header-left-element");
		article.addStyleName("header-right-element");
		store.addStyleName("header-right-element");
		report.addStyleName("header-right-element");
		
		titleLabel.addStyleName("header-title");
		articleButton.addStyleName("header-button");
		storeButton.addStyleName("header-button");
		reportButton.addStyleName("report-button");

		// Einfügen des Icons
		user.setUrl("/images/userIcon.png");
		user.addStyleName("user-icon");

		// Zuordnung der Widgets
		title.add(titleLabel);
		report.add(reportButton);
		article.add(articleButton);
		store.add(storeButton);

		// Zuordnung des Icons in das logoutIcon Panel
		userIcon.add(user);

		headerLeft.add(title);
		headerRight.add(report);
		headerRight.add(article);
		headerRight.add(store);
		headerRight.add(userIcon);
		
		
		
		/*
		 * Hinzufügen der Click-Handler an die Buttons
		 */
		reportButton.addClickHandler(new ReportGeneratorClickHandler());
		articleButton.addClickHandler(new CreateArticleClickHandler());
		storeButton.addClickHandler(new CreateStoreClickHandler());
		
		//Hinzufügen eines Clickhandlers an das Image
		
		user.addClickHandler(new UserClickHandler());
		

		this.add(headerLeft);
		this.add(headerRight);

	}
		/*
		 * Klasse ReportGeneratorClickHandler welche den Click-Handler implementiert. 
		 * Die Klasse dient dazu, um auf die Seite des Report Generators zu gelangen. 
		 */
	
		private class ReportGeneratorClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		/*
		 * Klasse CreateArticleClickHandler welche den Click-Handler implementiert. 
		 * Die Klasse dient dazu, um auf die articleForm zu gelangen.   
		 */
		
		private class CreateArticleClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}
		/*
		 * Klasse CreateStoreClickHandler welche den Click-Handler implementiert. 
		 * Die Klasse dient dazu, um auf die storeForm zu gelangen.  
		 */
		
		private class CreateStoreClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}
		/*
		 * Klasse CreateArticleClickHandler welche den Click-Handler implementiert. 
		 * Die Klasse dient dazu, um auf die profileSettingsForm zu gelangen.  
		 */
		
		private class UserClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}

}
