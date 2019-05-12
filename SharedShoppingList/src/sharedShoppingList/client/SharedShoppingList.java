package sharedShoppingList.client;


import sharedShoppingList.client.gui.Header;
import sharedShoppingList.shared.EinkaufslistenVerwaltungAsync;



import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SharedShoppingList implements EntryPoint {
	
	//EinkaufslistenVerwaltungAsync einkaufslistenverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();
	
	//private Header header = null;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
	RootPanel rootPanelHeader = RootPanel.get("header");
		
	Header header = new Header();
	
	rootPanelHeader.add(header);
	
	
	
	

	
	}
}
