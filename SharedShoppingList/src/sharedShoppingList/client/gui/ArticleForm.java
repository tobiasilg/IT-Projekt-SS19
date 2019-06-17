package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
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

	ArrayList<ArticleCustomTextBox> textboxes;
	ArrayList<CustomListBox> listboxes;
	ArrayList<CustomRow> rows;

	String[] units;

	Article newArticle;

	public Article getNewArticle() {
		return newArticle;
	}

	public void setNewArticle(Article newArticle) {
		this.newArticle = newArticle;
	}

	// Konstruktor
	public ArticleForm() {

		saveButton.addClickHandler(new SaveArticleClickHandler(textboxes, listboxes, rows));
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddArticleClickHandler());

	}

	@Override
	protected String nameForm() {

		return "Artikelverwaltung";
	}

	@Override
	protected ListBox createUnitListBox() {
		units = new String[] { "kg", "Gramm", "Stück", "Pack", "Liter", "Milliliter" };

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

		}
		textboxes = new ArrayList<ArticleCustomTextBox>();
		textboxes.clear();
		articleFlexTable.removeAllRows();
		articleFlexTable.setText(0, 0, "Artikel");
		articleFlexTable.setText(0, 1, "Einheit");

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
					// Frage: Schreibt Daten aus der DB rein obwohl diese leer ist
					Window.alert("Artikel aus DB:" + article.getName());
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

		Window.alert(article.getName());

		CustomRow row = new CustomRow();

		// Erstelle neue Textbox für eigetragenen Artikel und setze den Namen
		ArticleCustomTextBox articleTextBox = new ArticleCustomTextBox();
		articleTextBox.setValue(article.getName());

		row.setTextBox(articleTextBox);
		row.setListBoxValue(article.getUnit());
		row.setArticle(article);

		// textboxes.add(articleTextBox);

		// TODO create rows and add textbox and select

		// Erstelle neue ListBox für die Einheit und setze die selektierte Einheit
		ListBox unitListBox = new ListBox();
		for (String unit : units) {
			unitListBox.addItem(unit);
//			CustomListBox cListbox = new CustomListBox();

		}
		unitListBox.setSelectedIndex(Arrays.asList(units).indexOf(article.getUnit()));

		// Erstelle x Button
		ArticleCustomButton removeButton = new ArticleCustomButton();
		removeButton.setArticle(article);

		removeButton.addClickHandler(new DeleteArticleClickHandler(removeButton));

		// Füge die TextBox und die ListBox in die FlexTable ein
		articleFlexTable.setWidget(rowCount, 0, articleTextBox);
		articleFlexTable.setWidget(rowCount, 1, unitListBox);
		articleFlexTable.setWidget(rowCount, 3, removeButton);

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
	 * Klicken des addButton erstellt.
	 */
	private class AddArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

			// Persistiere in die Datenbank
			elv.createArticle(nameTextBox.getValue(), unitListBox.getSelectedValue(), new ArticleCreationCallback());

			setContentOfArticleFlexTable(newArticle);

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

			createTable();

			// Klappt noch nicht
			setNewArticle(article);

		}
	}

	private class ArticleCustomButton extends Button {
		Article article;

		public Article getArticle() {
			return article;
		}

		public void setArticle(Article article) {
			this.article = article;
		}
	}

	private class DeleteArticleClickHandler implements ClickHandler {

		private ArticleCustomButton cB;

		public DeleteArticleClickHandler(ArticleCustomButton cB) {

			this.cB = cB;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (cB != null) {

				elv.delete(cB.getArticle(), new DeleteArticleCallback());

			} else {
				Notification.show("Der Artikel konnte nicht gelöscht werden");
			}
		}
	}

	private class ArticleCustomTextBox extends TextBox {
		Article article;

		public Article getArticle() {
			return article;
		}

		public void setArticle(Article article) {
			this.article = article;
		}
	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des Bestï¿½tigungsbutton erstellt.
	 */
	private class SaveArticleClickHandler implements ClickHandler {

		private ArrayList<ArticleCustomTextBox> list;
		private ArrayList<CustomListBox> listbox;

		private ArrayList<CustomRow> rows;

		public SaveArticleClickHandler(ArrayList<ArticleCustomTextBox> list, ArrayList<CustomListBox> listbox,
				ArrayList<CustomRow> rows) {

			this.list = list;
			this.listbox = listbox;
			this.rows = rows;
		}

		public void onClick(ClickEvent event) {

			for (CustomRow row : rows) {

				Article article = row.getArticle();

				article.setName(row.getTextBox().getName());
				article.setUnit(row.getListBoxValue());
				// textbox.getArticle().setUnit(this.listbox.get(index).getSelectedItemText());

				elv.save(article, new SaveArticleCallback());

			}
//			int index = 0;
//			for (ArticleCustomTextBox textbox : textboxes) {
//
//				textbox.getArticle().setName(textbox.getValue());
//				textbox.getArticle().setUnit(this.listbox.get(index).getSelectedItemText());
//
//				Window.alert("TextBox Objekt: " + textbox.getArticle());
//				Window.alert("TextBox Unit: " + this.listbox.get(index).getSelectedItemText());
//				Window.alert("Artikel name: " + textbox.getArticle().getName());
//				Window.alert("Artikel ID: " + textbox.getArticle().getId());
//
//				elv.save(textbox.getArticle(), new SaveArticleCallback());
//
//			}
		}

	}

	private class CustomListBox extends ListBox {
		Article article;

		public Article getArticle() {
			return article;
		}

		public void setArticle(Article article) {
			this.article = article;
		}
	}

	/**
	 * @author treib
	 *
	 */
	private class CustomRow {
		String listBoxValue;
		ArticleCustomTextBox textBox;
		Article article;

		public String getListBoxValue() {
			return listBoxValue;
		}

		public void setListBoxValue(String listBoxValue) {
			this.listBoxValue = listBoxValue;
		}

		public ArticleCustomTextBox getTextBox() {
			return textBox;
		}

		public void setTextBox(ArticleCustomTextBox textBox) {
			this.textBox = textBox;
		}

		public Article getArticle() {
			return article;
		}

		public void setArticle(Article article) {
			this.article = article;
		}

	}

	private class DeleteArticleCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			articles.clear();
			createTable();
		}
	}

	private class SaveArticleCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());

		}

		@Override
		public void onSuccess(Void result) {
//			Window.alert();

		}

	}

}