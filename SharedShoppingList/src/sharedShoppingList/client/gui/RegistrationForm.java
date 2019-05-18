package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/*
 * Die Klasse <code>RegistrationForm</code> dient zur Registrierung des Nutzers im System
 */

public class RegistrationForm extends FlowPanel {

	private Label greetingLabel = new Label("Bitte loggen Sie sich über Ihren Google Account ein");
	private Label topLabel = new Label("KEKWAY");
	private Button googleloginButton = new Button();
	private String loginURL;

	// Konstruktur, um den URL für das Einloggen zu setzten
	public RegistrationForm(String loginURL) {
		super();
		this.loginURL = loginURL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 * 
	 * onLoad Methode, um die RegistrationForm zu laden
	 */

	public void onLoad() {

		this.addStyleName("regestrationForm");
		topLabel.addStyleName("welcomeHeadline");
		greetingLabel.addStyleName("greetingLabel");
		googleloginButton.addStyleName("googleloginButton");

		this.add(topLabel);
		this.add(greetingLabel);
		this.add(googleloginButton);

		googleloginButton.addClickHandler(new loginClickHandler());

	}

	/*
	 * Private Klasse loginClickHandler, welche das Interface implementiert Die
	 * Klasse ermöglicht das Einloggen in das System und leitet an das Google
	 * Anmeldeformular weiter
	 */

	private class loginClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			Window.Location.assign(loginURL);

		}

	}

}
