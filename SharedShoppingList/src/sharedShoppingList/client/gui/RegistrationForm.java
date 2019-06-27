package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.User;

public class RegistrationForm extends FlowPanel {

	private EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	private User user;

	private FlowPanel registrationBox = new FlowPanel();

//	private HorizontalPanel titlePanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	private Label welcomeLabel = new Label("Wilkommen bei Kekbuy");
	private Label infoLabel = new Label("Bitte registrieren Sie sich: ");
	private Label nameLabel = new Label("Name");
	private Label usernameLabel = new Label("Username");

	private DynamicTextBox nameTextbox = new DynamicTextBox();
	private DynamicTextBox usernameTextbox = new DynamicTextBox();

	private Button registrationButton = new Button("Registrieren");
	private Button cancelButton = new Button("Abbrechen");

	private Grid registrationGrid = new Grid(2, 2);

	// dient der Weiterleitung
	private Anchor destinationUrl = new Anchor();

	public RegistrationForm(Anchor destinationUrl, User user) {
		this.destinationUrl = destinationUrl;
		this.user = user;

		registrationButton.addClickHandler(new RegistrationClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
	}

//	public RegistrationForm() {
//		
//		registrationButton.addClickHandler(new RegistrationClickHandler());
//		cancelButton.addClickHandler(new CancelClickHandler());
//		
//	}

	private class RegistrationClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

//		elv.createUser(nameTextbox.getValue(), user.getGmail(), usernameTextbox.getValue(), new NewUserCallback());

			String name = nameTextbox.getText();
			String uName = usernameTextbox.getText();
			user.setName(name);
			user.setUsername(uName);
			Window.alert(user.getName() + user.getId() + user.getGmail() + user.getUsername());

			elv.save(user, new SaveUserCallback());
		}

	}

	private class CancelClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
			Window.open(user.getLogoutUrl(), "_self", "");

		}

	}

	public void onLoad() {

		registrationBox.addStyleName("profilBox");
		welcomeLabel.addStyleName("profilTitle");
		infoLabel.addStyleName("profilLabel");
		registrationGrid.addStyleName("profilTextBox");
		buttonPanel.addStyleName("profilLabel");

		buttonPanel.setSpacing(20);
		registrationGrid.setCellSpacing(15);

		nameTextbox.getElement().setPropertyString("placeholder", "Dein Name...");
		usernameTextbox.getElement().setPropertyString("placeholder", "Dein Username...");

//		titlePanel.setHeight("10em");
//		titlePanel.setWidth("100%");
//		titlePanel.setCellVerticalAlignment(welcomeLabel, ALIGN_BOTTOM);
//		
//		registrationButton.setPixelSize(130, 40);
//		cancelButton.setPixelSize(130, 40);

		registrationGrid.setWidget(0, 0, nameLabel);
		registrationGrid.setWidget(0, 1, nameTextbox);
		registrationGrid.setWidget(1, 0, usernameLabel);
		registrationGrid.setWidget(1, 1, usernameTextbox);

		// titlePanel.add(welcomeLabel);
		buttonPanel.add(cancelButton);
		buttonPanel.add(registrationButton);

		registrationBox.add(welcomeLabel);
		registrationBox.add(infoLabel);
		registrationBox.add(registrationGrid);
		registrationBox.add(buttonPanel);

		this.add(registrationBox);

	}

	private class DynamicTextBox extends TextBox {

	}

	/*
	 * Callback
	 */

	private class NewUserCallback implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(User result) {
			Window.open(destinationUrl.getHref(), "_self", "");

		}

	}

	/*
	 * Callback
	 */

	private class SaveUserCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {
			Notification.show("User wurde upgedated");
			Window.open(destinationUrl.getHref(), "_self", "");
		}

	}

}
