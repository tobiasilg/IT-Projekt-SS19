package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;

/**
 * Formular für das Anlegen eines neuen Artikels im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class FavoriteArticleForm extends AbstractAdministrationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	FlexTable favoriteArticleFlexTable;

	ArrayList<String> favoriteArticles = new ArrayList<>();

	@Override
	protected String nameForm() {

		return "Meine Lieblingsartikel<3";
	}

	@Override
	protected ListBox createUnitListBox() {
		return null;
	}

	@Override
	protected FlexTable createTable() {
		// TODO Auto-generated method stub
		return null;
	}

	// Konstruktor
	public FavoriteArticleForm() {

		saveButton.addClickHandler(new CreateFavArticleClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddFavArticleClickHandler());

	}

	/**
	 * Hiermit wird der Erstellvorgang eines neuen Favoritens abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Favorite nach dem
	 * Klicken des Best�tigungsbutton erstellt.
	 */
	private class CreateFavArticleClickHandler implements ClickHandler {

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