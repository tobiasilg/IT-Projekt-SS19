package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/*
 * Klasse enthält alle Elemente zum Anzeigen der Informationen des Users
 */

public class ProfilForm extends FlowPanel {
	
	// Erstellung des Panels indem die Daten liegen
	private FlowPanel profilBox = new FlowPanel();
	private FlowPanel speicherButtonPanel = new FlowPanel();
	
	// Erstellung der Labels für alle Titel
	private Label profilTitle = new Label("Dein Profil");
	private Label usernameLabel = new Label("Username");
	private Label nameLabel = new Label("Name");
	private Label firstnameLabel = new Label("Vorname");
	private Label mailLabel = new Label("Email");
	
	// Erstellung der Text Boxen
	private TextBox usernameTextBox = new TextBox();
	private TextBox nameTextBox = new TextBox();
	private TextBox firstnameTextBox = new TextBox();
	private TextBox mailTextBox = new TextBox();
	
	// Erstellung der Buttons 
	private Button speicherProfilButton = new Button ("Speichern");
	private Button logoutButton = new Button ("Logout");
	
	public void onLoad() {
		
	//Vergeben der Stylenames
	profilBox.addStyleName("profilBox");
	
	profilTitle.addStyleName("profilTitle");
	
	usernameLabel.addStyleName("profilLabel");
	nameLabel.addStyleName("profilLabel");
	firstnameLabel.addStyleName("profilLabel");
	mailLabel.addStyleName("profilLabel");
	
	usernameTextBox.addStyleName("profilTextBox");
	nameTextBox.addStyleName("profilTextBox");
	firstnameTextBox.addStyleName("profilTextBox");
	mailTextBox.addStyleName("profilTextBox");
	
	speicherButtonPanel.addStyleName("profilLabel");
	
	speicherProfilButton.addStyleName("speicherProfilButton");
	logoutButton.addStyleName("logoutButton");
	
	this.add(profilTitle);
	this.add(profilBox);
//	this.add(speicherProfilButton);
	this.add(logoutButton);
	
	profilBox.add(usernameLabel);
	profilBox.add(usernameTextBox);
	
	profilBox.add(nameLabel);
	profilBox.add(nameTextBox);
	
	profilBox.add(firstnameLabel);
	profilBox.add(firstnameTextBox);
	
	profilBox.add(mailLabel);
	profilBox.add(mailTextBox);
	
	speicherButtonPanel.add(speicherProfilButton);
	profilBox.add(speicherButtonPanel);
	
	usernameTextBox.getElement().setPropertyString("placeholder", "Dein Username: ");
	nameTextBox.getElement().setPropertyString("placeholder", "Dein Name: ");
	firstnameTextBox.getElement().setPropertyString("placeholder", "Dein Vorname: ");
	mailTextBox.getElement().setPropertyString("placeholder", "Deine Mailadresse: ");
	
	/*
	 * Noch zu erledigen 
	 * - Daten des Users mit den Methoden aus dem BO User ziehen
	 * - Löschen Button 
	 * - Dialog Box für die Abfrage 
	 */
	
	}
	

}
