package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import sharedShoppingList.shared.EinkaufslistenVerwaltungAsync;

public class Header extends FlowPanel {
	
	EinkaufslistenVerwaltungAsync einkaufslistenverwaltung = null;
	
	// Erstellung der Header Divs
	private FlowPanel headerLeft = new FlowPanel();
	private FlowPanel headerRight = new FlowPanel(); 
	private FlowPanel logoutIcon = new FlowPanel();
	
	// Erstellung der Button und Label Divs
	private FlowPanel title = new FlowPanel();
	private FlowPanel article = new FlowPanel(); 
	private FlowPanel store = new FlowPanel(); 
	
	// Erstellung der Buttons und Label 
	private Label titleLabel = new Label("KEKWAY");
	private Button articleButton = new Button("Artikel");
	private Button storeButton = new Button("Store");
	
	//Erstellen des Logout Icons
	Image logout = new Image();
	
	
	public Header() {
		//super();
	}
	
	/*
	 * Die Methode onLoad wird beim Aufrufen der Header Klasse aufgurufen und ausgeführt
	 * In dieser Klasse erfolgt die Zuordnung der Widgets
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	public void onLoad() {

	}
	
	
	
	
}
