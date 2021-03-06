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

		nameLabel.addStyleName("profilLabel");
		usernameLabel.addStyleName("profilLabel");
		mailLabel.addStyleName("profilLabel");
		
		nameTextBox.addStyleName("profilTextBox");
		usernameTextBox.addStyleName("profilTextBox");
		mailTextBox.addStyleName("profilTextBox");
		
		nameTextBox.setWidth("20em");
		usernameTextBox.setWidth("20em");
		mailTextBox.setWidth("20em");

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
		
		profilBox.add(nameLabel);
		profilBox.add(nameTextBox);

		profilBox.add(usernameLabel);
		profilBox.add(usernameTextBox);

		profilBox.add(mailLabel);
		profilBox.add(mailTextBox);

		speicherButtonPanel.add(speicherProfilButton);
		profilBox.add(speicherButtonPanel);

//		usernameTextBox.getElement().setPropertyString("placeholder", "Dein Username: " + user.getUserName());
//		nameTextBox.getElement().setPropertyString("placeholder", "Dein Name: " + user.getName());
//		mailTextBox.getElement().setPropertyString("placeholder", "Deine Mailadresse:" + user.getGmail());
		
		if (user == null) {
			nameTextBox.setText("Es gibt noch keinen Namen");
		} else {
			nameTextBox.setText(user.getName());
		}
		
		if (user == null) {
			usernameTextBox.setText("Es gibt noch keinen Username");
		} else {
			usernameTextBox.setText(user.getUsername());
		}
		
		if (user == null) {
			mailTextBox.setText("Es gibt noch keine Mailadresse");
		} else {
			mailTextBox.setText(user.getGmail());
		}

//		usernameTextBox.getElement().setPropertyString("placeholder", "Dein Username: ");
//		nameTextBox.getElement().setPropertyString("placeholder", "Dein Name: ");
//		mailTextBox.getElement().setPropertyString("placeholder", "Deine Mailadresse: ");

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

		private Label sicherheitsFrage = new Label("Bist Du Dir sicher Dein Profil zu löschen?");

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

			user.setLogoutUrl(user.getLogoutUrl());
			Window.open(user.getLogoutUrl(), "_self", "");	
			einkaufslistenverwaltung.delete(user, new DeleteUserCallback());

		}

	}

	private class DeleteUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Dein Profil konnte nicht gelöscht werden!");

		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("Dein Profil wurde erfolgreich gelöscht!");
//			Window.Location.assign(logoutUrl);

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
	/*
	 * Die Klasse SafeProfileClickHandle öffnet eine DialogBox für eine Anfrage zum
	 * speichern der geänderten Daten
	 */

	private class SafeProfileClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			SafeProfileBox safePB = new SafeProfileBox();
			safePB.center();

		}

	}

	private class SafeProfileBox extends DialogBox {

		private VerticalPanel vPanel = new VerticalPanel();
		private HorizontalPanel bPanel = new HorizontalPanel();

		private Label sicherheitsAbfrage = new Label("Willst du die geänderten Daten so übernehmen?");

		private Button yesButton = new Button("Ja");
		private Button noButton = new Button("Nein");

		public SafeProfileBox() {

			sicherheitsAbfrage.addStyleName("Abfrage");
			yesButton.addStyleName("buttonAbfrage");
			noButton.addStyleName("buttonAbfrage");

			bPanel.add(yesButton);
			bPanel.add(noButton);
			vPanel.add(sicherheitsAbfrage);
			vPanel.add(bPanel);

			this.add(vPanel);

			yesButton.addClickHandler(new SafeChangesClickHandler(this));
			noButton.addClickHandler(new CancelChangesClickHandler(this));

		}
	}

	/*
	 * Die Klasse SafeChangesClickHandler dient dazu die geänderten Daten des Users
	 * im System zu speichern
	 */
	private class SafeChangesClickHandler implements ClickHandler {

		private SafeProfileBox safeProfileBox;

		public SafeChangesClickHandler(SafeProfileBox safeProfileBox) {
			this.safeProfileBox = safeProfileBox;

		}

		public void onClick(ClickEvent event) {


			// Den Wert aus den Textboxen ziehen
			String usernameNeu = usernameTextBox.getValue();
			String nameNeu = nameTextBox.getValue();

			// Prüfen, ob die TextBox leer ist
			if (usernameNeu.isEmpty()) {
				Window.alert("Der Username ist leer!");
			} else {
				user.setUsername(usernameNeu);
			}

			if (nameNeu.isEmpty()) {
				Window.alert("Der Name ist leer!");
			} else {
				user.setName(nameNeu);
			}

			einkaufslistenverwaltung.save(user, new UpdateUserCallback());

			safeProfileBox.hide();

		}
	}
	/*
	 * Die Klasse GetUserCallback wird benötigt um den User aufzurufen
	 */

	private class UpdateUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			Window.alert("UpdateUserCallback funktioniert nicht");

		}

		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			Notification.show("Dein User wurde geändert!");

		}

	}

	/*
	 * Die Klasse CancelChangesClickHandler dient dazu den Speichervorgang
	 * abzubrechen
	 */
	private class CancelChangesClickHandler implements ClickHandler {

		private SafeProfileBox safeProfileBox;

		public CancelChangesClickHandler(SafeProfileBox safeProfileBox) {
			this.safeProfileBox = safeProfileBox;
		}

		@Override
		public void onClick(ClickEvent event) {
			safeProfileBox.hide();

		}

	}

	/*
	 * Die Klasse LogoutClickHandler soll das asuloggen des Users ermöglichen und
	 * den User zum Google Konto weiterleiten
	 */
	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			user.setLogoutUrl(user.getLogoutUrl());
			Window.open(user.getLogoutUrl(), "_self", "");

		}
	}

}
