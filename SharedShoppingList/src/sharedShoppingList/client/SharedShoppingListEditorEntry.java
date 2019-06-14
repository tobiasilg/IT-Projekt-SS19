package sharedShoppingList.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.TreeViewModel;

import sharedShoppingList.client.gui.Header;
import sharedShoppingList.client.gui.Navigator;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.User;


/**
 * /** Entry-Point-Klasse des Projekts <b>SharedShoppingList</b>.
 * 
 * Login aktuell noch auskommentiert, damit die GUI immer getestet werden kann
 */
public class SharedShoppingListEditorEntry implements EntryPoint {

	Header header = new Header();
	RootPanel rootPanelHeader = RootPanel.get("header");
	
	TreeViewModel nav = new Navigator();
	
	/**
	 * @TODO Navigator nav = new Navigator(); -> CellTree Methode hinzufügen
	 * */
	
	CellTree tree = new CellTree(nav,"Item 1");

	Navigator navigator = new Navigator();
	RootPanel rootPanelNavigator = RootPanel.get("navigator");
	

	/*
	 * 
	 * 
	 * //Objekt, das die Anmeldeinformation des Benutzerdienstes enthält //private
	 * LoginInfo loginInfo = null;
	 * 
	 * private VerticalPanel loginPanel = new VerticalPanel();
	 * 
	 * private Button loginButton = new Button ("Einloggen");
	 * 
	 * private Anchor signInLink = new Anchor ("Einloggen");
	 * 
	 * private Label loginLabel = new Label
	 * ("Melden Sie sich in Ihrem Google-Konto an, um auf die SharedShoppingList-Anwendung zuzugreifen."
	 * );
	 * 
	 * /** Da diese Klasse das Interface <code>EntryPoint</code> implementiert, wird
	 * die Methode <code>public void onModuleLoad()</code> benötigt. Die
	 * <code>onModuleLoad()</> implementiert die "Main"-Methode des Systems. Diese
	 * Methode wird zu Beginn des Seitenaufrufs abgerufen.
	 */

	public void onModuleLoad() {

		rootPanelHeader.add(header);
		rootPanelNavigator.add(navigator);
		rootPanelNavigator.add(tree);

	}
	
	/*
	 * // Check login status using login service. LoginServiceAsync loginService =
	 * GWT.create(LoginService.class); loginService.login(GWT.getHostPageBaseURL()
	 * +"SharedShoppingList.html", new AsyncCallback<LoginInfo>() { public void
	 * onFailure(Throwable error) { }
	 * 
	 * public void onSuccess (LoginInfo result) { loginInfo = result;
	 * if(loginInfo.isLoggedIn()) { loadEditor(); }else { loadLogin(); } } }); }
	 * 
	 * /* Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt
	 * ist. Ueber den Button <code>loginButton</code> wird die Google-LoginMaske
	 * aufgerufen
	 */

	/*
	 * private void loadLogin() { // assemble login panel.
	 * signInLink.setHref(loginInfo.getLoginUrl()); loginPanel.add(loginLabel);
	 * loginPanel.add(signInLink); loginPanel.add(loginButton);
	 * RootPanel.get("header").setVisible(false);
	 * RootPanel.get("navigator").setVisible(false);
	 * RootPanel.get("details").setVisible(false);
	 * RootPanel.get("footer").setVisible(false);
	 * RootPanel.get("container").add(loginPanel);
	 * 
	 * 
	 * loginLabel.setStylePrimaryName("loginLabel");
	 * loginButton.setStylePrimaryName("loginButton");
	 * 
	 * loginPanel.setSpacing(8); loginPanel.setWidth("100vw");
	 * loginPanel.setCellHorizontalAlignment(loginLabel,HasHorizontalAlignment.
	 * ALIGN_CENTER);
	 * loginPanel.setCellHorizontalAlignment(loginButton,HasHorizontalAlignment.
	 * ALIGN_CENTER);
	 * 
	 * loginButton.addClickHandler((ClickHandler)new LoginClickHandler());
	 * 
	 * } /* Die nested Class <code> LoginClickHandler</code> implementiert das
	 * ClickHandler-Interface Durch einen Klick auf den loginButton wird der User
	 * auf die Google-LoginMaske weitergeleitet
	 */

	/*
	 * private class LoginClickHandler implements ClickHandler { public void
	 * onClick(ClickEvent event) { Window.open(signInLink.getHref(),"_self", ""); }
	 * } // Methode wird erst nach erfolgreichem Login geladen
	 * 
	 */

	// private void loadEditor (){
	// RootPanels
	// RootPanel rootPanelHeader = RootPanel.get("header");
	// RootPanel rootPanelNav= RootPanel.get("navigator");
	// RootPanel rootPanelDetails = RootPanel.get("details");
	// RootPanel rootPanelFooter = RootPanel.get("footer");

	/*
	 * 
	 * rootPanelHeader.add(); rootPanelNav.add(); rootPanelDetails.add();
	 * rootPanelFooter.add();
	 */
	
	/**
	 * Die Klasse <code>CurrentUser</code> repräsentiert den aktuell am System angemeldeten User.
	 * Da weitere GUI-Klassen das angemeldetet User-Objekt verwenden, muss diese jederzeit aufrufbar sein.
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
  	
public static class CurrentGroup {
		
		private static Group g = null;

		public static Group getGroup() {
			return g;
		}

		public static void setGroup(Group g) {
			CurrentGroup.g = g;
		}
  	}
}
