
package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Formular für das Anlegen einer neuen Gruppe
 * @author moritzhampe
 *
 */

public class GroupForm extends AbstractAdministrationForm {


		@Override
		protected String nameForm() {

			return "Meine Lieblingsartikel<3";
		}

		// Konstruktor
		public GroupForm() {

			saveButton.addClickHandler(new CreateStoreHandler());
			cancelButton.addClickHandler(new CancelClickHandler());
			addButton.addClickHandler(new AddFavArticleClickHandler());

		}

		/**
		 * Hiermit wird der Erstellvorgang eines neuen Favoritens abbgebrochen.
		 */
		private class CancelClickHandler implements ClickHandler {

			public void onClick(ClickEvent event) {
				RootPanel.get("Details").clear();

			}

		}

		/**
		 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Favorite nach
		 * dem Klicken des Best�tigungsbutton erstellt.
		 */
		private class CreateStoreHandler implements ClickHandler {

			public void onClick(ClickEvent event) {

			}
		}

		/**
		 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Favorit nach dem Klicken
		 * des addButton erstellt.
		 */
		private class AddFavArticleClickHandler implements ClickHandler {

			public void onClick(ClickEvent event) {

			}
		}

		/**
		 * Callback wird ben�tigt, um den Store zu erstellen
		 */
		private class FavArticleCreationCallback implements AsyncCallback<Void> {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Der Store konnte nicht erstellt werden");
			}

			@Override
			public void onSuccess(Void event) {
				Notification.show("Der Store wurde erfolgreich erstellt");
			}
		}
}

