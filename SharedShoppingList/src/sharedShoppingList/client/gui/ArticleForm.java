package sharedShoppingList.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;

/**
 * Formular für das Anlegen eines neuen Artikels im Datenstamm
 * 
 * @author moritzhampe
 *
 */

public class ArticleForm extends AbstractAdministrationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	FlexTable articleFlexTable;
	ListBox articleListBox;

	@Override
	protected String nameForm() {

		return "Artikelverwaltung";
	}

	@Override
	protected ListBox createUnitListBox() {

		if (articleListBox == null) {
			articleListBox = new ListBox();

			articleListBox.addItem("kg");
			articleListBox.addItem("St�ck");
		}

		return articleListBox;
	}

	@Override
	protected FlexTable createTable() {

		if (articleFlexTable == null) {
			articleFlexTable = new FlexTable();

			articleFlexTable.setText(0, 0, "Artikel");
			articleFlexTable.setText(0, 1, "Einheit");
			articleFlexTable.setText(1, 0, "Banane");
			articleFlexTable.setText(1, 1, "Kg");
		}

		return articleFlexTable;
	}

	// Konstruktor
	public ArticleForm() {

		saveButton.addClickHandler(new SaveArticleClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddArticleClickHandler());
		deleteButton.addClickHandler(new DeleteArticleClickHandler());
	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Artikel abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("Details").clear();

		}

	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Artikel nach dem Klicken
	 * des Best�tigungsbutton erstellt.
	 */
	private class SaveArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Artikel nach dem Klicken
	 * des addButton erstellt.
	 */
	private class AddArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			elv.createArticle(nameTextBox.getText(), unitListBox.getSelectedValue(), new ArticleCreationCallback());

		}

	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Artikel nach dem Klicken
	 * des addButton erstellt.
	 */
	private class DeleteArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

		}
	}

	/**
	 * Callback wird ben�tigt, um den Artikel zu erstellen
	 */
	private class ArticleCreationCallback implements AsyncCallback<Article> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Der Artikel konnte nicht erstellt werden");

		}

		@Override
		public void onSuccess(Article article) {
			Notification.show("Der Artikel wurde erfolgreich erstellt");
		}
	}

}
