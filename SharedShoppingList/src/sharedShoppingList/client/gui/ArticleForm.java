package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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

	ArrayList<String> articles = new ArrayList<>();

	@Override
	protected String nameForm() {

		return "Artikelverwaltung";
	}

	@Override
	protected ListBox createUnitListBox() {

		if (articleListBox == null) {
			articleListBox = new ListBox();

			articleListBox.addItem("gramm");
			articleListBox.addItem("kg");
			articleListBox.addItem("Stueck");
		}

		return articleListBox;
	}

	@Override
	protected FlexTable createTable() {

		if (articleFlexTable == null) {
			articleFlexTable = new FlexTable();

			articleFlexTable.setText(0, 0, "Artikel");
			articleFlexTable.setText(0, 1, "Einheit");

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
			RootPanel.get("details").clear();

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

//			elv.createArticle(nameTextBox.getText(), unitListBox.getSelectedValue(), new ArticleCreationCallback());

			final String article = nameTextBox.getValue();
			final String unit = unitListBox.getSelectedItemText();

			if (articles.contains(article)) {
				return;
			}

			articles.add(article);
			int rowCount = articleFlexTable.getRowCount();
			articleFlexTable.setText(rowCount, 0, article);

			Button removeButton = new Button("x");

			removeButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					final int removedIndex = articles.indexOf(article);
					articles.remove(removedIndex);
					articleFlexTable.removeRow(removedIndex + 1);
				}

			});

//			if (units.contains(unit)) {
//				return;
//			}

//			units.add(unit);
//			int rowCountUnit = articleFlexTable.getRowCount();
//			articleFlexTable.setText(rowCountUnit, 1, unit);

//			Button removeButtonUnit = new Button("x");
//
//			removeButton.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					final int removedIndex = articles.indexOf(article);
//					articles.remove(removedIndex);
//					articleFlexTable.removeRow(removedIndex + 1);
//				}
//
//			});

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
