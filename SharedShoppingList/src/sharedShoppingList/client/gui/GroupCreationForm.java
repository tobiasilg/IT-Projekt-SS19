
package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.shared.FieldVerifier;

/**
 * Formular für das Anlegen einer neuen Gruppe
 * 
 *
 */

public class GroupCreationForm extends AbstractDialogCreationForm {

	@Override
	protected String nameDialogForm() {
		
		return "neue Gruppe";
	}

}