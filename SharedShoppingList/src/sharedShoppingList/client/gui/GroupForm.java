package sharedShoppingList.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;



/**
 * Formular für das Anlegen einer neuen Gruppe
 * @author moritzhampe
 *
 */

public class GroupForm extends VerticalPanel{

	Label groupNameLabel = new Label("Name der Gruppe: ");
	Label enterUserNameLabel = new Label("Benutzername eingeben:");
	Label foundUserLabel = new Label ("Gefundene Benutzer");
	Label addUserLabel = new Label ("Hinzugefügter User:");
	TextBox nameTextBox = new TextBox();
	
	Button addGroupButton = new Button("Gruppe mit genannten Usern anlegen");
}
