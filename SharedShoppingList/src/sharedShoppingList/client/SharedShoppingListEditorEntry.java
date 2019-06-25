package sharedShoppingList.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.gui.Header;
import sharedShoppingList.client.gui.Navigator;
import sharedShoppingList.client.gui.RegistrationForm;
import sharedShoppingList.shared.LoginServiceAsync;
import sharedShoppingList.shared.bo.User;

/**
 * /** Entry-Point-Klasse des Projekts <b>SharedShoppingList</b>.
 *
 * Login aktuell noch auskommentiert, damit die GUI immer getestet werden kann
 */
public class SharedShoppingListEditorEntry implements EntryPoint {
	
	Header header = new Header();
	RootPanel rootPanelHeader = RootPanel.get("header");

	Navigator navigator = new Navigator();
	RootPanel rootPanelNavigator = RootPanel.get("navigator");

	// Objekt, das die Anmeldeinformation des Benutzerdienstes enthält //privat

//	private LoginServiceAsync loginService = null;
//
//	private VerticalPanel loginPanel = new VerticalPanel();
//
//	private Button loginButton = new Button("Einloggen");
//
//	private Anchor signInLink = new Anchor("Einloggen");
//
//	private Label loginLabel = new Label(
//			"Melden Sie sich in Ihrem Google-Konto an, um auf die SharedShoppingList-Anwendung zuzugreifen.");

	/**
	 * Da diese Klasse das Interface <code>EntryPoint</code> implementiert, wird die
	 * Methode <code>public void onModuleLoad()</code> benötigt. Die
	 * <code>onModuleLoad()</> implementiert die "Main"-Methode des Systems. Diese
	 * Methode wird zu Beginn des Seitenaufrufs abgerufen.
	 */

	public void onModuleLoad() {
		
		rootPanelHeader.add(header);
		rootPanelNavigator.add(navigator);
		

//		loginService = ClientsideSettings.getLoginService();
//
//		loginService.login(GWT.getHostPageBaseURL() + "SharedShoppingList.html", new loginServiceCallback());

	}

//	private class loginServiceCallback implements AsyncCallback<User> {
//
//		@Override
//		public void onFailure(Throwable caught) {
//		//	Notification.show(caught.toString());
//			Window.alert("Test: "+ caught.toString());
//			
//		}
//
//		@Override
//		public void onSuccess(User u) {
//
//			CurrentUser.setUser(u);
//
//			if (u.isLoggedIn()) {
//				if (u.getName() == null) {
//					Anchor shoppingListEditorLink = new Anchor();
//					shoppingListEditorLink.setHref(GWT.getHostPageBaseURL() + "SharedShoppingList.html");
//					RootPanel.get("navigator").setVisible(false);
//					RootPanel.get("header").setVisible(false);
//					RootPanel.get("footer").setVisible(false);
//					RootPanel.get("details").add(new RegistrationForm(shoppingListEditorLink, u));
//
//				} else {
//
//					Header header = new Header();
//					RootPanel rootPanelHeader = RootPanel.get("header");
//
//					Navigator navigator = new Navigator();
//					RootPanel rootPanelNavigator = RootPanel.get("navigator");
//
//					rootPanelHeader.add(header);
//					rootPanelNavigator.add(navigator);
//				}
//			} else {
//				loadLogin();
//			}
//
//		}
//
//	}
//	
//	/**
//	 * Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt ist
//	 * In dieser wird die Google LoginMaske über den Button
//	 * <code>loginButton </code> aufgerufen
//	 */
//	private void loadLogin() {
//		
//		RootPanel.get("details").add(loginPanel);
//		RootPanel.get("header").setVisible(false);
//		RootPanel.get("navigation").setVisible(false);
//		//RootPanel.get("details").setVisible(false);
//		RootPanel.get("footer").setVisible(false);
//		
//		loginLabel.setStylePrimaryName("loginLabel");
//		loginButton.setStylePrimaryName("loginButton");
//
//		loginPanel.setSpacing(10);
//		loginPanel.setWidth("100vw");
//		loginPanel.add(loginLabel);
//		loginPanel.add(loginButton);
//		loginPanel.setCellHorizontalAlignment(loginLabel,HasHorizontalAlignment.ALIGN_CENTER);
//		loginPanel.setCellHorizontalAlignment(loginButton,HasHorizontalAlignment.ALIGN_CENTER);
//		signInLink.setHref(CurrentUser.getUser().getLoginUrl());
//
//		/**
//		 * Durch einen Klick auf den <code>loginButton</code> wird der User auf die
//		 * Google LoginMaske weitergeleitet
//		 */
//		loginButton.addClickHandler(new LoginClickHandler());
//	}
//	
//
//	private class LoginClickHandler  implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//		
//		Window.open(signInLink.getHref(), "_self", "");
//		
//		}
//	}

	/**
	 * Die Klasse <code>CurrentUser</code> repräsentiert den aktuell am System
	 * angemeldeten User. Da weitere GUI-Klassen das angemeldetet User-Objekt
	 * verwenden, muss diese jederzeit aufrufbar sein.
	 */
	public static class CurrentUser {

		private static User u = null;

		public static User getUser() {
			return u;
		}

		public static void setUser(User u) {
			CurrentUser.u = u;
		}
	}

	public static class Unit {

	}
}
