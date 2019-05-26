package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.User;

/*
 * Klasse enthält alle Elemente zum Anzeigen der Informationen des Users
 */

public class ProfilForm extends FlowPanel {

	EinkaufslistenverwaltungAsync einkaufslistenverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();

	User user = CurrentUser.getUser();
	String logoutUrl;

	// Erstellung des Panels indem die Daten liegen
	private FlowPanel profilBox = new FlowPanel();
	private FlowPanel speicherButtonPanel = new FlowPanel();
	private HorizontalPanel topPanel = new HorizontalPanel();

	// Erstellung der Labels für alle Titel
	private Label profilTitle = new Label("Dein Profil");
	private Label usernameLabel = new Label("Username");
	private Label nameLabel = new Label("Name");
	private Label mailLabel = new Label("Email");

	// Erstellung der Text Boxen
	private TextBox usernameTextBox = new TextBox();
	private TextBox nameTextBox = new TextBox();
	private TextBox mailTextBox = new TextBox();

	// Erstellung der Buttons
	private Button speicherProfilButton = new Button("Speichern");
	private Button logoutButton = new Button("Logout");
	private Button deleteAccountButton = new Button("Meinen Account löschen");

	public ProfilForm() {

	}

	public void onLoad() {

		// Vergeben der Stylenames
		profilBox.addStyleName("profilBox");

		profilTitle.addStyleName("profilTitle");

		usernameLabel.addStyleName("profilLabel");
		nameLabel.addStyleName("profilLabel");
		mailLabel.addStyleName("profilLabel");

		usernameTextBox.addStyleName("profilTextBox");
		nameTextBox.addStyleName("profilTextBox");
		mailTextBox.addStyleName("profilTextBox");

		topPanel.addStyleName("topPanel");
		speicherButtonPanel.addStyleName("profilLabel");

		speicherProfilButton.addStyleName("speicherProfilButton");
		logoutButton.addStyleName("logoutButton");
		deleteAccountButton.addStyleName("deleteAccountButton");

		this.add(topPanel);
		this.add(profilTitle);
		this.add(profilBox);

		topPanel.add(deleteAccountButton);
		topPanel.add(logoutButton);

		profilBox.add(usernameLabel);
		profilBox.add(usernameTextBox);

		profilBox.add(nameLabel);
		profilBox.add(nameTextBox);

		profilBox.add(mailLabel);
		profilBox.add(mailTextBox);

		speicherButtonPanel.add(speicherProfilButton);
		profilBox.add(speicherButtonPanel);

//	usernameTextBox.getElement().setPropertyString("placeholder", "Dein Username: " + user.getUserName());
//	nameTextBox.getElement().setPropertyString("placeholder", "Dein Name: " + user.getName());
//	mailTextBox.getElement().setPropertyString("placeholder", "Deine Mailadresse: " + user.getGmail());

		usernameTextBox.getElement().setPropertyString("placeholder", "Dein Username: ");
		nameTextBox.getElement().setPropertyString("placeholder", "Dein Name: ");
		mailTextBox.getElement().setPropertyString("placeholder", "Deine Mailadresse: ");

		/*
		 * Hinzufügen der Click-Handler an Anchors, Logo und Icon
		 */
		deleteAccountButton.addClickHandler(new DeleteAccountClickHandler());
		speicherProfilButton.addClickHandler(new SafeProfileClickHandler());
		logoutButton.addClickHandler(new LogoutClickHandler());

	}

	/*
	 * Die Klasse DeleteAccountClickHandler öffnet eine DialogBox
	 */
	private class DeleteAccountClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteProfileBox deletePB = new DeleteProfileBox();
			deletePB.center();
		}

	}

	/*
	 * Die Klasse DeleteProfileBox stellt eine Sicherheitsabfrage ob das Profil
	 * wirklich gelöscht werden soll.
	 */
	private class DeleteProfileBox extends DialogBox {

		private VerticalPanel verticalPanel = new VerticalPanel();
		private HorizontalPanel buttonPanel = new HorizontalPanel();

		private Label sicherheitsFrage = new Label("Sind Sie sich sicher, dass Sie Ihr Profil löschen möchten?");

		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");

		/*
		 * Konstruktor wird benötigt um die DialogBox zusammenzusetzen
		 */
		public DeleteProfileBox() {
			sicherheitsFrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage");
			neinButton.addStyleName("buttonAbfrage");

			buttonPanel.add(jaButton);
			buttonPanel.add(neinButton);
			verticalPanel.add(sicherheitsFrage);
			verticalPanel.add(buttonPanel);

			this.add(verticalPanel);

			jaButton.addClickHandler(new FinalDeleteClickHanlder(this));
			neinButton.addClickHandler(new CancelDeleteClickHandler(this));

		}

	}

	/*
	 * Die Klasse FinalDeleteClickHanlder dient dazu, um den User aus dem System zu
	 * löschen
	 */
	private class FinalDeleteClickHanlder implements ClickHandler {

		private DeleteProfileBox deleteProfileBox;

		public FinalDeleteClickHanlder(DeleteProfileBox deleteProfileBox) {
			this.deleteProfileBox = deleteProfileBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			this.deleteProfileBox.hide();
			
			// Hier muss noch der zugriff auf die Methode deletUser erfolgen !
			// user.setLogoutUrl(user.getLogoutUrl());
			// einkaufslistenverwaltung.delete(user, new DeleteUserCallback());
		}

	}

	private class DeleteUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Dein Profil wurde nicht gelöscht!");
			
		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Dein Profil wurde erfolgreich gelöscht!");
			Window.Location.assign(logoutUrl);
			
		}

	 }

	/*
	 * Die Klasse CancelDeleteClickHandler dient dazu, um den Löschvorgang des Users
	 * abzubrechen
	 */
	private class CancelDeleteClickHandler implements ClickHandler {

		private DeleteProfileBox deleteProfileBox;

		public CancelDeleteClickHandler(DeleteProfileBox deleteProfileBox) {
			this.deleteProfileBox = deleteProfileBox;

		}

		@Override
		public void onClick(ClickEvent event) {
			this.deleteProfileBox.hide();
		}

	}

	private class SafeProfileClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

		}

	}
	/*
	 * Die Klasse LogoutClickHandler soll das asuloggen des Users ermöglichen 
	 * und den User zum Google Konto weiterleiten
	 */
	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			Window.Location.assign(logoutUrl);

		}

	}



		

	}


