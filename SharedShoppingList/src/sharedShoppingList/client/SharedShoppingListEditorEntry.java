package sharedShoppingList.client;

import sharedShoppingList.client.gui.Header;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * /**
 * Entry-Point-Klasse des Projekts <b>SharedShoppingList</b>.
 */
public class SharedShoppingListEditorEntry implements EntryPoint {

	private LoginInfo logInfo = null;

	private Button loginButton = new Button("Einloggen");

	private VerticalPanel loginPanel = new VerticalPanel();

	private Anchor singnInLink = new Anchor ("Einloggen");		

	private Label loginLabel = new Label ("Melden Sie sich in Ihrem Google-Konto an um auf die SharedShoppingList-Anwendung zuzugreifen.");			



	/**
	 * Da diese Klasse das Interface <code>EntryPoint</code> implementiert,
	 * wird die Methode <code>public void onModuleLoad()</code> benötigt. 
	 * Die <code>onModuleLoad()</> implementiert die "Main"-Methode des Systems. Diese
	 * Methode wird zu Beginn des Seitenaufrufs abgerufen.
	 */
	public void onModuleLoad() {

		/*
		 * Login-Status mit Login-Service prüfen.
		 */


		loginServiceAsync loginService = GWT.create (LoginService.class);

		loginService.login (GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {

			/*
			 * Bei fehlerhaftem RPC Callback wird eine Fehlermeldung geworfen
			 */

			public void onFailure (Throwable caught) {

			/*
			 * hier muss noch eine Notification Klasse erstellen werden.
			 * "Notification.zeige(caught.toString());"
			 */
			}

		/*
			public void onSuccess (LoginInfo result) {
				loginInfo = result;
				
				if (loginInfo.isLoggedIn()) {
					loadEditor();	
					}else {
					loadLogin;
					}
			}
			}
		}
		/*
	/*
	 * Login Panel anzeigen
	 */
	private void loadLogin () {	

		/*
		 * Zuweisung des Labels sowie des Buttons dem loginPanel
		 */
		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
	}

	/*
	 * wird nach erfolgreichem Login geladen
	 *
	 */

	private void loadEditor() {

/*
 * Die Methode setzt die Seite korrekt zusammen
 * 
 */
	 RootPanel rootPanelHeader =  RootPanel.get("header");
	 Header header = new Header();

	rootPanelHeader.add(header);
	}
		}
	}
}
	