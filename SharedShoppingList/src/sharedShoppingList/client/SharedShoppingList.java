package sharedShoppingList.client;

import sharedShoppingList.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SharedShoppingList implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		/* Das ButtonPanel besteht aus drei Buttons, welche dazu dienen, 
		 * Gruppe, Artikel und Händler anzulegen.
		 * Die Buttons sind nebeneinender in einem horizontal Panel angeordnet
		 */
		HorizontalPanel buttonPanel = new HorizontalPanel();
		/*Verweis auf das Selection Div in der HTML page
		 */
		RootPanel.get("selection").add(buttonPanel);
		
		final Button groupButton = new Button("Gruppe anlegen");
		final Button articleButton = new Button("Artikel anlegen"); 
		final Button storeButton = new Button("Händler anlegen");
		
		buttonPanel.add(groupButton);
		buttonPanel.add(articleButton);
		buttonPanel.add(storeButton);
		
		VerticalPanel navigationsPanel = new VerticalPanel();
		
		/*Verweis auf das Navigations Div in der HTML page
		 */
		RootPanel.get("Navigation").add(navigationsPanel);
		
		/*HIER MÜSSEN WIR EINE LÖSUNG FINDEN
		 */
		final Button listButton = new Button("EK anlegen");
		
		navigationsPanel.add(listButton);

	}
}
