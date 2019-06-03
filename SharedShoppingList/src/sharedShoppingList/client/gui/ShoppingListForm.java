package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListEditorEntry.CurrentUser;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.User;

/**
 * 
 * Die Klasse <code>ShoppingListForm</code> erstellt das Formular zur Anzeige
 * einer Shoppingliste mit Artikel, Artikelanzahl, Unit, wer und wo der Artikel
 * gekauft wird etc..
 * 
 * @author nicolaifischbach
 *
 */

public class ShoppingListForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
	private User u = CurrentUser.getUser();

	// +"Gruppenname"
	private Label infoTitleLabel = new Label("Einkaufsliste" + "");
	private SuggestBox addArticleSuggestBox = new SuggestBox();
	private Button addArticleToShoppingList = new Button("hinzufuegen");
	private HorizontalPanel hpSuggestBox = new HorizontalPanel();
	private FlexTable shoppingListFlexTable = new FlexTable();
	private Label Star = new Label("<3");
	private Button deleteRowButton = new Button("x");
	private ArrayList<Article> shoppingList;

	// Konstruktor
	public ShoppingListForm() {

		deleteRowButton.addClickHandler(new DeleteRowClickHandler());

		hpSuggestBox.add(addArticleSuggestBox);
		hpSuggestBox.add(addArticleToShoppingList);

	}

	/**
	 * In dieser Methode wird das Design der <code>ShoppingListForm</code> und der
	 * Buttons festgelegt. Diese Methode wird aufgerufen, sobald ein Objekt der
	 * Klasse <code>ShoppingListForm</code> instanziert wird.
	 */
	public void onLoad() {

		hpSuggestBox.add(addArticleSuggestBox);
		hpSuggestBox.add(addArticleToShoppingList);

		this.add(infoTitleLabel);
		this.add(hpSuggestBox);
		this.add(addArticleSuggestBox);
		this.add(shoppingListFlexTable);

		shoppingListFlexTable.setWidth("%");
		shoppingListFlexTable.setBorderWidth(0);
		shoppingListFlexTable.setSize("%", "%");

		shoppingListFlexTable.setCellPadding(0);

		addArticleSuggestBox.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {

				if (event.getCharCode() == KeyCodes.KEY_ENTER) {

					addArticleToShoppingList.click();
					addArticleSuggestBox.setText("");
				}

			}
		});
	}

	/***********************************************************************
	 * Abschnitt der METHODEN
	 ***********************************************************************
	 */

	/***********************************************************************
	 * Abschnitt der CLICKHANDLER
	 ***********************************************************************
	 */

	/**
	 * Die Nested-Class <code>DeleteRowClickHandler</code> implementiert das
	 * ClickHandler-Interface und startet so die Lösch-Kaskade einer Artikelzeile in
	 * der Shoppingliste.
	 */

	private class AddArticleToShoppingListClickHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

		}
	}

	private class DeleteRowClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// elv.delete(u, new DeleteRowCallback());
		}
	}

	/***********************************************************************
	 * Abschnitt der CALLBACKS
	 ***********************************************************************
	 */

	/**
	 * Callback wird benötigt, um die Gruppe umzubenennen
	 */
	private class DeleteRowCallback implements AsyncCallback<Void> {

		public void onFailure(Throwable caught) {
			Notification.show("Die Artikelzeile konnte nicht gelöscht werden");
		}

		public void onSuccess(Void article) {
			Notification.show("Die Artikelzeile wurde erfolgreich gelöscht");
		}
	}
}
