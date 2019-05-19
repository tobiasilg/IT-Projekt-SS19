package sharedShoppingList.client;

import java.util.logging.Logger;

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
import sharedShoppingList.shared.LoginInfo;

/**
 * /** Entry-Point-Klasse des Projekts <b>SharedShoppingList</b>.
 */
public class SharedShoppingListEditorEntry implements EntryPoint {

	// Objekt, das die Anmeldeinformation des Benutzerdienstes enthält
	private LoginInfo loginInfo = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Button loginButton = new Button("Einloggen");

	private Anchor signInLink = new Anchor("Einloggen");

	private Label loginLabel = new Label(
			"Melden Sie sich in Ihrem Google-Konto an, um auf die SharedShoppingList-Anwendung zuzugreifen.");

	/**
	 * Da diese Klasse das Interface <code>EntryPoint</code> implementiert, wird die
	 * Methode <code>public void onModuleLoad()</code> benötigt. Die
	 * <code>onModuleLoad()</> implementiert die "Main"-Methode des Systems. Diese
	 * Methode wird zu Beginn des Seitenaufrufs abgerufen.
	 */

	public void onModuleLoad() {

		loadEditor();

		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL() + "SharedShoppingList.html", new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
				Button loginButton = new Button("Einloggen");
				RootPanel.get("header").add(loginButton);
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadEditor();
				} else {
					loadLogin();
				}
			}
		});
	}

	/*
	 * Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt ist.
	 * Ueber den Button <code>loginButton</code> wird die Google-LoginMaske
	 * aufgerufen
	 */
	private void loadLogin() {
		// assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		loginPanel.add(loginButton);
		RootPanel.get("header").setVisible(false);
		RootPanel.get("navigator").setVisible(false);
		RootPanel.get("details").setVisible(false);
		RootPanel.get("footer").setVisible(false);
		RootPanel.get("container").add(loginPanel);

		loginLabel.setStylePrimaryName("loginLabel");
		loginButton.setStylePrimaryName("loginButton");

		loginPanel.setSpacing(8);
		loginPanel.setWidth("100vw");
		loginPanel.setCellHorizontalAlignment(loginLabel, HasHorizontalAlignment.ALIGN_CENTER);
		loginPanel.setCellHorizontalAlignment(loginButton, HasHorizontalAlignment.ALIGN_CENTER);

		loginButton.addClickHandler((ClickHandler) new LoginClickHandler());

	}

	/*
	 * Die nested Class <code> LoginClickHandler</code> implementiert das
	 * ClickHandler-Interface Durch einen Klick auf den loginButton wird der User
	 * auf die Google-LoginMaske weitergeleitet
	 */
	private class LoginClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {
			Window.open(signInLink.getHref(), "_self", "");
		}
	}

	// Methode wird erst nach erfolgreichem Login geladen
	private void loadEditor() {
		// RootPanels
		RootPanel rootPanelHeader = RootPanel.get("header");
		RootPanel rootPanelNav = RootPanel.get("navigator");
		RootPanel rootPanelDetails = RootPanel.get("details");
		RootPanel rootPanelFooter = RootPanel.get("footer");

		Header header = new Header();

		rootPanelHeader.add(header);

	}
}
