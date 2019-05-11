package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class Header extends FlowPanel {
	
	/*
	 * Erstellung der Header Panels 
	 */
	private FlowPanel headerLeft = new FlowPanel();
	private FlowPanel headerRight = new FlowPanel();
	
	/*
	 * Erstellung der Header Divs
	 */
	private FlowPanel titleDiv = new FlowPanel();
	
	private FlowPanel articleDiv = new FlowPanel();
	private FlowPanel storeDiv = new FlowPanel();
	private FlowPanel profileDiv = new FlowPanel();
	
	/*
	 * Erstellung der Buttons im Header 
	 */
	private Label title = new Label("KEKBUY");
	private Button articleButton = new Button("Artikel");
	private Button storeButton = new Button("Store");
	
	public void onLoad() {
		
	titleDiv.add(title);
	storeDiv.add(storeButton);
	articleDiv.add(articleButton);
	
	headerLeft.add(titleDiv);
	headerRight.add(articleButton);
	headerRight.add(storeButton);
	
		
	}
	
	
	
	
}
