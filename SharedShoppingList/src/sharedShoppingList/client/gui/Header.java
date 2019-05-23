package sharedShoppingList.client.gui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

//import sharedShoppingList.client.ClientsideSettings;
//import sharedShoppingList.shared.EinkaufslistenVerwaltungAsync;

public class Header extends FlowPanel {

	// EinkaufslistenVerwaltungAsync einkaufslistenverwaltung = null;

	// Erstellung der Header Divs
	private FlowPanel headerLeft = new FlowPanel();
	private FlowPanel headerLeftElement = new FlowPanel();
	private FlowPanel headerRight = new FlowPanel();
	private FlowPanel headerEnd = new FlowPanel();
	private FlowPanel headerRightElement = new FlowPanel();

	private FlowPanel headerImage = new FlowPanel();

	// Erstellung Label Divs
	private Label headerTitle = new Label("KEKBUY");
	private Label headerSubtitle = new Label("share it - buy it");

	// Anchors
	private Anchor report = new Anchor("Report");
	private Anchor article = new Anchor("Artikel");
	private Anchor store = new Anchor("Store");

	// Erstellen des Logout Icons
	Image logout = new Image();

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

		// einkaufslistenverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();

		/*
		 * Vergeben von Stylenamen, um die Divs über CSS ansprechen zu können
		 */

		// Gesamtes Header Div
		this.addStyleName("header");

		// Einzelteile des Headers
		headerLeft.addStyleName("header_left");
		headerRight.addStyleName("header_right");
		headerEnd.addStyleName("header_end");
		headerLeftElement.addStyleName("header_left_element");
		headerTitle.addStyleName("header_title");
		headerSubtitle.addStyleName("header_subtitle");
		headerRightElement.addStyleName("header_right_elememt");

		report.addStyleName("header_right_elememt");
		article.addStyleName("header_right_elememt");
		store.addStyleName("header_right_elememt");

		headerImage.addStyleName("header_image");

		// Einfügen des Icons
		logout.setUrl("/images/logoutIcon.png");
		logout.addStyleName("img");

		headerLeft.add(headerLeftElement);
		headerLeftElement.add(headerTitle);
		headerLeftElement.add(headerSubtitle);

		headerRight.add(headerEnd);
		headerEnd.add(report);
		headerEnd.add(article);
		headerEnd.add(store);
		headerEnd.add(headerRightElement);

		headerRightElement.add(headerImage);
		headerImage.add(logout);

		this.add(headerLeft);
		this.add(headerRight);
		
		/*
		 * Hinzufügen der Click-Handler an Anchors, Logo und Icon
		 */
		
		report.addClickHandler(new ShowReportClickHandler());
		article.addClickHandler(new ShowArticleClickHandler());
		store.addClickHandler(new ShowStoreClickHandler());
		headerTitle.addClickHandler(new ShowSartPageClickHandler());
		logout.addClickHandler(new ShowProfilClickHandler());
		

	}	
	
		/*
		 * Die Klasse ShowReportClickHadler ermöglicht die Weiterletung 
		 * zum Report Generator
		 */
		private class ShowReportClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				report.setHref(GWT.getHostPageBaseURL() + "ReportGenerator.html");
				Window.open(report.getHref(), "_blank", ""); //öffnet neunen Tab
			}
		
		}
		/*
		 * Die Klasse ShowArticleClickHandler ermöglicht die Weiterletung 
		 * zur ArticleForm
		 */
		private class ShowArticleClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}
		/*
		 * Die Klasse ShowStoreClickHandler ermöglicht die Weiterletung 
		 * zur StoreForm
		 */
		private class ShowStoreClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}
		/*
		 * Die Klasse ShowStartPageClickHandler ermöglicht die Azeige 
		 * der Startseite
		 */
		private class ShowSartPageClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				
			}
			
		}
		/*
		 * Die Klasse ShowProfilClickHandler ermöglicht die Azeige 
		 * der ProfilForm
		 */
		private class ShowProfilClickHandler implements ClickHandler {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		}

}
