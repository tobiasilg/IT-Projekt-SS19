package sharedShoppingList.client;


import sharedShoppingList.client.gui.Header;
import sharedShoppingList.shared.EinkaufslistenVerwaltungAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SharedShoppingList implements EntryPoint {
	
	EinkaufslistenVerwaltungAsync einkaufslistenverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();
	
	//private Header header = null;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
	RootPanel rootPanelHeader = RootPanel.get("header");
		
	Header header = new Header();
	
	//Label label1 = new Label("KEKWAY");
	
	//Button articleButton = new Button("Artikel");
	//Button storeButton = new Button("Einzelhändler");
	
	//articleButton.setStyleName("HeaderButtons");
	//storeButton.setStyleName("HeaderButtons");
	
	//RootPanel.get("KEKWAY").add(label1);
	//RootPanel.get("Artikel").add(articleButton);
	//RootPanel.get("Einzelhändler").add(storeButton);
	
	//headerPanel.add(label1);
	//headerPanel.add(articleButton);
	//headerPanel.add(storeButton);
	
	rootPanelHeader.add(header);
	
	}
}
