package sharedShoppingList.client.gui;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;

/**
 * Formular für das Anlegen eines neuen Artikels im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class ArticleForm extends AbstractAdministrationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	FlexTable articleFlexTable;
	ListBox articleListBox;

	ArrayList<String> articles = new ArrayList<>();
	ArrayList<String> units = new ArrayList<>();

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
			articleFlexTable.setText(0, 2, "Favorit");

		}

		return articleFlexTable;
	}

	// Konstruktor
	public ArticleForm() {

		saveButton.addClickHandler(new SaveArticleClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddArticleClickHandler());
		articleFlexTable.addClickHandler(new EditArticleClickHandler());

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

			for (String test : articles) {

//				elv.save(article, new ArticleDeleteCallback());

			}

		}
	}

	/**
	 * Sobald das Textfeld ausgef�llt wurde, wird ein neuer Artikel nach dem Klicken
	 * des addButton erstellt.
	 */
	private class AddArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			final String article = nameTextBox.getValue();
			final String unit = unitListBox.getSelectedItemText();

			if (articles.contains(article)) {
				return;
			}

			articles.add(article);
			units.add(unit);

			int rowCount = articleFlexTable.getRowCount();

			CheckBox checkBox = new CheckBox();

			articleFlexTable.setText(rowCount, 0, article);
			articleFlexTable.setText(rowCount, 1, unit);

			Button removeButton = new Button("x");

			removeButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					final int removedIndex = articles.indexOf(article);
					articles.remove(removedIndex);
					articleFlexTable.removeRow(removedIndex + 1);
				}

			});

			articleFlexTable.setWidget(rowCount, 3, removeButton);
			articleFlexTable.setWidget(rowCount, 2, checkBox);

			elv.createArticle(nameTextBox.getText(), unitListBox.getSelectedItemText(), new ArticleCreationCallback());
		}

	}

	private class EditArticleClickHandler implements ClickHandler {

//	FlexTable.addClickHandler(new ClickHandler() {

		public void onClick(ClickEvent event) {
			Cell cell = articleFlexTable.getCellForEvent(event);
			final int row = cell.getRowIndex();
			final int articleColumn = 0;
			final int unitColumn = 1;
			final TextBox editArticleTextBox = new TextBox();
			final ListBox editUnitListBox = articleListBox;
//			

//			editUnitListBox.addItem("gramm");
//			editUnitListBox.addItem("kg");
//			editUnitListBox.addItem("Stueck");

			// Get the text from the cell in some way. Maybe use flextTable.getHTML(row,
			// column) or what ever you prefer

			articleFlexTable.getHTML(1, 2);

			editArticleTextBox.setText("Änderung vornehmen");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
//					int code = event.getNativeButton();
//					if (KeyCodes.KEY_ENTER == code) {
					articleFlexTable.setWidget(row, articleColumn, new Label(editArticleTextBox.getText()));
					articleFlexTable.setWidget(row, unitColumn, new Label(editUnitListBox.getSelectedValue()));
				}
//				}

			});
			articleFlexTable.setWidget(row, articleColumn, editArticleTextBox);
			articleFlexTable.setWidget(row, unitColumn, editUnitListBox);
//	        

			// You may also need something like this
			editArticleTextBox.setFocus(true);
			editUnitListBox.setFocus(true);
		}
//	
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

	/**
	 * Callback wird ben�tigt, um den Artikel zu erstellen
	 */
	private class ArticleDeleteCallback implements AsyncCallback<Article> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show("Die Änderungen konnten nicht gespeichert werden");

		}

		@Override
		public void onSuccess(Article article) {
			Notification.show("speichern erfolgreich");
		}
	}
}
