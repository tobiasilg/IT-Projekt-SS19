package sharedShoppingList.client.reportgui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class HeaderRepo extends VerticalPanel {

//	 EinkaufslistenVerwaltungAsync einkaufslistenverwaltung = null;

	// Erstellung der Header Divs
	private Label label = new Label("KEKWAY-REPORT"); // Label der den Namen im Header anzeigt
	private Label headerSubtitle = new Label("share it - buy it");
	private HorizontalPanel hp = new HorizontalPanel();
	private Button testButton = new Button("drück mich");

	public HeaderRepo() {
		super();
	}

	/*
	 * Die Methode onLoad wird beim Aufrufen der HeaderRepo Klasse aufgurufen und
	 * ausgeführt In dieser Klasse erfolgt die Zuordnung der Widgets
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	public void onLoad() {

		// einkaufslistenverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();

		/*
		 * Vergeben von Stylenamen, um die Divs über CSS ansprechen zu können
		 */
		label.addStyleName("header_title");
		headerSubtitle.addStyleName("header_subtitle");
		// Gesamtes Header Div
		this.addStyleName("header");

		hp.add(label);
		hp.add(headerSubtitle);
		hp.add(testButton);
		this.add(hp);

	}
}
