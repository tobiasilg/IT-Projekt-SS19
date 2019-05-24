package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.User;

/*
 * Klasse enthält alle Elemente zum Anzeigen der Informationen des Users
 */

public class ProfilForm extends FlowPanel {
	
	EinkaufslistenverwaltungAsync einkaufsverwaltung = ClientsideSettings.getEinkaufslistenverwaltung();
	
	User user = null; 
	
	// Erstellung des Panels indem die Daten liegen
	private FlowPanel profilBox = new FlowPanel();
	private FlowPanel speicherButtonPanel = new FlowPanel();
	private FlowPanel topPanel = new FlowPanel();
	
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
	private Button speicherProfilButton = new Button ("Speichern");
	private Button logoutButton = new Button ("Logout");
	private Button deleteAccountButton = new Button ("Meinen Account löschen");
	
	
	
	public ProfilForm() {
		
	}
	public void onLoad() {
		
	//Vergeben der Stylenames	
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
	speicherProfilButton.addClickHandler(new SafeProfileClickHandler());
	logoutButton.addClickHandler(new LogoutClickHandler()); 
	deleteAccountButton.addClickHandler(new DeleteAccountClickHandler());
	
	/*
	 * Noch zu erledigen 
	 * - Dialog Box für die Abfrage 
	 */
	
	}
	
	private class SafeProfileClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class LogoutClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/*
	 * Die Klasse DeleteAccountClickHandler öffnet eine DialogBox 
	 */
	private class DeleteAccountClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			DeleteProfileBox pb = new DeleteProfileBox(); 
			pb.center();
	
			
		}
		
	}
	/*
	 * Die Klasse DeleteProfileBox stellt eine Sicherheitsabfrage 
	 * ob das Profil wirklich gelöscht werden soll. 
	 */
	private class DeleteProfileBox extends DialogBox {
		
		private VerticalPanel obenPanel = new VerticalPanel();
		private HorizontalPanel untenPanel = new HorizontalPanel();
		
		private Label sicherheitsFrage = new Label("Sind Sie sich sicher, dass Sie Ihr Profil löschen möchten?");
		
		private Button jaButton = new Button("Ja");
		private Button neinButton = new Button("Nein");
		
		/*
		 * Konstruktor wird benötigt um die DialogBox zusammenzusetzen 
		 */
		public DeleteProfileBox() {
			sicherheitsFrage.addStyleName("Abfrage");
			jaButton.addStyleName("buttonAbfrage rot");
			neinButton.addStyleName("buttonAbfrage");
			
			obenPanel.add(sicherheitsFrage);
			untenPanel.add(jaButton);
			untenPanel.add(neinButton);
			
			this.add(obenPanel);
			this.add(untenPanel);
			
			this.setPopupPosition(getAbsoluteLeft(), getAbsoluteTop());
		} 
		
		
		
	}
	

}
