package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;

/**
 * Formular fÃ¼r das Anlegen eines neuen Artikels im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class ArticleForm extends AbstractAdministrationForm {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	FlexTable articleFlexTable;

	ListBox articleListBox;

	ArrayList<Article> articles;

	String[] units;

	// Konstruktor
	public ArticleForm() {

		saveButton.addClickHandler(new SaveArticleClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddArticleClickHandler());

	}

	@Override
	protected String nameForm() {

		return "Artikelverwaltung";
	}

	@Override
	protected ListBox createUnitListBox() {
		units = new String[] { "kg", "Gramm", "Stück", "Pack" };

		if (articleListBox == null) {
			articleListBox = new ListBox();
		}
		for (String unit : units) {
			articleListBox.addItem(unit);
		}

		return articleListBox;
	}

	@Override
	protected FlexTable createTable() {
		elv = ClientsideSettings.getEinkaufslistenverwaltung();

		// Erstelle FlexTable, falls nicht existent
		if (articleFlexTable == null) {
			articleFlexTable = new FlexTable();

			articleFlexTable.setText(0, 0, "Artikel");
			articleFlexTable.setText(0, 1, "Einheit");
			articleFlexTable.setText(0, 2, "Favorit");
		}

		articles = new ArrayList<Article>();

		// Lade alle Artikel aus der Datenbank
		elv.getAllArticles(new AsyncCallback<Vector<Article>>() {
			@Override
			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			@Override
			public void onSuccess(Vector<Article> result) {
				// Füge alle Elemente der Datenbank in die Liste hinzu
				for (Article article : result) {
					articles.add(article);
					setContentOfArticleFlexTable(article);
				}
				Notification.show("success");

			}
		});

		return articleFlexTable;
	}

	private void setContentOfArticleFlexTable(Article article) {
		// Hole Zeilennummer, die aktuell bearbeitet wird
		int rowCount = articleFlexTable.getRowCount();

		// Erstelle neue Textbox für eigetragenen Artikel und setze den Namen
		TextBox articleTextBox = new TextBox();
		articleTextBox.setText(article.getName());

		// Erstelle neue ListBox für die Einheit und setze die selektierte Einheit
		ListBox unitListBox = new ListBox();
		for (String unit : units) {
			unitListBox.addItem(unit);
		}
		unitListBox.setSelectedIndex(Arrays.asList(units).indexOf(article.getUnit()));

		// Erstelle x Button
		Button removeButton = new Button("x");
		removeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

//				PROBLEM WEIL ARTICLE NICHT FINAL IST 

//				final int removedIndex = articles.indexOf(article);
//				articles.remove(removedIndex);
//				articleFlexTable.removeRow(removedIndex + 1);
			}
		});

		// Erstelle CheckBox für Favoriten
		CheckBox checkBox = new CheckBox();

		// Füge die TextBox und die ListBox in die FlexTable ein
		articleFlexTable.setWidget(rowCount, 0, articleTextBox);
		articleFlexTable.setWidget(rowCount, 1, unitListBox);
		articleFlexTable.setWidget(rowCount, 3, removeButton);
		articleFlexTable.setWidget(rowCount, 2, checkBox);
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
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des Bestï¿½tigungsbutton erstellt.
	 */
	private class SaveArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			for (Article article : articles) {
				elv.save(article, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Notification.show("successfilly saved");
					}
				});
			}
		}

	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des addButton erstellt.
	 */
	private class AddArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// Erstelle neues Article Objekt
			Article article = new Article();
			article.setName(nameTextBox.getValue());
			article.setUnit(unitListBox.getSelectedItemText());

			setContentOfArticleFlexTable(article);

//			// Persistiere in die Datenbank
			elv.createArticle(article.getName(), article.getUnit(), new ArticleCreationCallback());
		}

	}

	/**
	 * Callback wird benï¿½tigt, um den Artikel zu erstellen
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