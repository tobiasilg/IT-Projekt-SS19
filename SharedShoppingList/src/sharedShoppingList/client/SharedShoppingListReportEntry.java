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

import sharedShoppingList.client.gui.RegistrationForm;
import sharedShoppingList.client.reportgui.HeaderRepo;
import sharedShoppingList.client.reportgui.MainPanelReport;
import sharedShoppingList.shared.LoginServiceAsync;
import sharedShoppingList.shared.bo.User;

public class SharedShoppingListReportEntry implements EntryPoint {

	private LoginServiceAsync loginService = null;

	private VerticalPanel loginPanel = new VerticalPanel();

	private Button loginButton = new Button("Einloggen");

	private Anchor signInLink = new Anchor("Einloggen");

	private Label loginLabel = new Label(
			"Melden Sie sich in Ihrem Google-Konto an, um auf die SharedShoppingList-Anwendung zuzugreifen.");

	public void onModuleLoad() {

		loginService = ClientsideSettings.getLoginService();

		loginService.login(GWT.getHostPageBaseURL() + "ReportGenerator.html", new LoginServiceCallback());

	}

	private class LoginServiceCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			// Notification.show(caught.toString());
			Window.alert("Test: " + caught.toString());

		}

		@Override
		public void onSuccess(User u) {

			CurrentUser.setUser(u);

			if (u.isLoggedIn()) {
				if (u.getName() == null) {
					Anchor shoppingListEditorLink = new Anchor();
					shoppingListEditorLink.setHref(GWT.getHostPageBaseURL() + "ReportGenerator.html");
//					RootPanel.get("navigator").setVisible(false);
//					RootPanel.get("header").setVisible(false);
//					RootPanel.get("footer").setVisible(false);
					RootPanel.get("navigation").add(new RegistrationForm(shoppingListEditorLink, u));

				} else {

					Window.alert("else");
					HeaderRepo headerReport = new HeaderRepo();
					RootPanel rootPanelHeaderReport = RootPanel.get("header");

					MainPanelReport mainPanelReport = new MainPanelReport();
					RootPanel rootPanelMainPanelReport = RootPanel.get("navigation");

					rootPanelHeaderReport.add(headerReport);
					rootPanelMainPanelReport.add(mainPanelReport);

				}
			} else {

				loadLogin();
			}
		}
	}

	/**
	 * Diese Methode wird aufgerufen, falls der User nicht am System eingeloggt ist
	 * In dieser wird die Google LoginMaske über den Button
	 * <code>loginButton </code> aufgerufen
	 */
	private void loadLogin() {

		loginPanel.setSpacing(10);
//				loginPanel.setWidth("100vw");
		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		loginPanel.setCellHorizontalAlignment(loginLabel, HasHorizontalAlignment.ALIGN_CENTER);
		loginPanel.setCellHorizontalAlignment(loginButton, HasHorizontalAlignment.ALIGN_CENTER);
		signInLink.setHref(CurrentUser.getUser().getLoginUrl());

		RootPanel.get("header").setVisible(false);
//				RootPanel.get("navigation").setVisible(false);
		RootPanel.get("footer").setVisible(false);
		RootPanel.get("navigation").add(loginPanel);

		loginLabel.setStylePrimaryName("loginLabel");
		loginButton.setStylePrimaryName("speicherProfilButton");

		/**
		 * Durch einen Klick auf den <code>loginButton</code> wird der User auf die
		 * Google LoginMaske weitergeleitet
		 */
		loginButton.addClickHandler(new LoginClickHandler());
	}

	private class LoginClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			Window.open(signInLink.getHref(), "_self", "");

		}
	}

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

}
