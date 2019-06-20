package sharedShoppingList.client.reportgui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HeaderRepo extends VerticalPanel {

	/**
	 * Erstellung der benötigten Panel
	 */
	private HorizontalPanel hp = new HorizontalPanel();

	/**
	 * Erstellung Widgets
	 */
	private Label label = new Label("KEKWAY-REPORT"); // Label der den Namen im Header anzeigt
	private Label headerSubtitle = new Label("share it - buy it");
	private Button linkToMainBtn = new Button("Zur Startseite");
	private Anchor linkToMain = new Anchor("main");

	/**
	 * Konstruktor der Klasse
	 */
	public HeaderRepo() {
		super();
	}

	/*
	 * Die Methode onLoad wird beim Aufrufen der HeaderRepo Klasse aufgurufen und
	 * ausgeführt In dieser Klasse erfolgt die Zuordnung der Widgets
	 */

	public void onLoad() {

		/*
		 * Vergeben von Stylenamen, um die Divs über CSS ansprechen zu können
		 */
		label.addStyleName("header_title");
		headerSubtitle.addStyleName("header_subtitle");

		// Gesamtes Header Div
		this.addStyleName("header");

		hp.add(label);
		hp.add(headerSubtitle);
		hp.add(linkToMainBtn);

		this.add(hp);

		/*
		 * Clickhandler für den Button um wieder zum Editor zu gelangen
		 */
		linkToMainBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				linkToMain.setHref(GWT.getHostPageBaseURL() + "");
				Window.open(linkToMain.getHref(), "_blank", "");
			}
		});

	}
}
