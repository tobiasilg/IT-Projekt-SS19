package sharedShoppingList.client.gui;


import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/*
 * Bildet die Navigationsleiste zum anzeigen und selektieren der Einkaufsliste, 
 * welche der Gruppe untergeordnet ist.
 */

public class Navigator extends FlowPanel {
	
	private Button neuButton = new Button("NEU");
	
	private Label navTitle = new Label("Meine Gruppen");

	//public Navigator() {
	//	super();
	//}
	
	public void onLoad() {
		
		// Vergeben der Stylenamen
		this.addStyleName("nav");
		
		neuButton.addStyleName("navButton");
		navTitle.addStyleName("navTitle");
		
		this.add(neuButton);
		this.add(navTitle);
		
		
	}
	

}
