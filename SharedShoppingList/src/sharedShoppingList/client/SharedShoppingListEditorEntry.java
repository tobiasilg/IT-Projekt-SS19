package sharedShoppingList.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.gui.Header;
import sharedShoppingList.shared.LoginInfo;



/**
 * /**
 * Entry-Point-Klasse des Projekts <b>SharedShoppingList</b>.
 */
public class SharedShoppingListEditorEntry implements EntryPoint {

	private LoginInfo loginInfo = null;
	
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
		LoginServiceAsync loginService = GWT.create(LoginService.class);
	    loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
	      public void onFailure(Throwable error) {
	      }

	    }
}
}
	