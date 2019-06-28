package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Article;

/**
 * Formular fÃ¼r das Anlegen eines neuen Artikels im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class ArticleForm extends VerticalPanel {

	private Label nameLabel = new Label("Artikelverwaltung");
	private TextBox nameTextBox = new TextBox();

	private Button cancelButton = new Button("abbrechen");
	private Button saveButton = new Button("Änderungen speichern");
	private Button addButton = new Button("hinzufügen");

	private ListBox unitListBox = createUnitListBox();
	private ArrayList<String> units;

	private HorizontalPanel hpCreate = new HorizontalPanel();

	private ScrollPanel scrollPanel = new ScrollPanel();

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	// Create a data provider.
	private ListDataProvider<Article> dataProvider = new ListDataProvider<Article>();

	private List<Article> list = dataProvider.getList();

	// create Table
	private CellTable<Article> table = new CellTable<Article>(KEY_PROVIDER);

	/**
	 * The key provider that allows us to identify Contacts even if a field changes.
	 * We identify contacts by their unique ID.
	 */
	private static final ProvidesKey<Article> KEY_PROVIDER = new ProvidesKey<Article>() {
		@Override
		public Object getKey(Article aricle) {
			return aricle.getId();
		}
	};

	// Konstruktor
	public ArticleForm() {

//		saveButton.addClickHandler(new SaveArticleClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());
		addButton.addClickHandler(new AddArticleClickHandler());
//		deleteButton.addClickHandler(new DeleteStoreClickHandler());
	}

	protected ListBox createUnitListBox() {
		if (units == null) {
			units = new ArrayList<String>();
			units.addAll(Arrays.asList("kg", "Gramm", "Stück", "Pack", "Liter", "Milliliter", "Kasten", "Flasche"));
		}
		if (unitListBox == null) {
			unitListBox = new ListBox();
		}
		for (String unit : units) {
			unitListBox.addItem(unit);
		}

		return unitListBox;
	}

	public void onLoad() {

		hpCreate.add(nameTextBox);
		hpCreate.add(unitListBox);
		hpCreate.add(addButton);
		hpCreate.add(saveButton);
		hpCreate.add(cancelButton);

		scrollPanel.add(table);

		scrollPanel.setHeight("12");

		this.add(nameLabel);
		this.add(hpCreate);

		this.add(scrollPanel);

		nameLabel.addStyleName("profilTitle");
		nameTextBox.addStyleName("profilTextBox");
		saveButton.addStyleName("speicherButton");
		addButton.addStyleName("speicherButton");

		// Lade alle Store aus der Datenbank
		elv.getAllArticles(new AsyncCallback<Vector<Article>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Vector<Article> result) {
				// Add the data to the data provider, which automatically pushes it to the
				// widget.
				for (Article aricle : result) {

					list.add(aricle);
				}
			}

		});

		EditTextCell editTextCell = new EditTextCell();
		Column<Article, String> stringColumn = new Column<Article, String>(editTextCell) {
			@Override
			public String getValue(Article article) {

				return article.getName();
			}
		};

		ButtonCell deleteButton = new ButtonCell();
		Column<Article, String> deleteColumn = new Column<Article, String>(deleteButton) {
			public String getValue(Article object) {
				return "x";
			}

		};

		units = new ArrayList<String>();
		units.addAll(Arrays.asList("kg", "Gramm", "Stück", "Pack", "Liter", "Milliliter", "Kasten", "Flasche"));

		SelectionCell categoryCell = new SelectionCell(units);
		Column<Article, String> categoryColumn = new Column<Article, String>(categoryCell) {
			@Override
			public String getValue(Article object) {
				return object.getUnit();
			}
		};

		categoryColumn.setFieldUpdater(new FieldUpdater<Article, String>() {
			public void update(int index, Article article, String value) {

				article.setUnit(value);

				EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

				AsyncCallback<Void> saveCallback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						Notification.show("Artikel wurde gespeichert");
					}

				};

				elv.save(article, saveCallback);
			}

		});
		deleteColumn.setFieldUpdater(new FieldUpdater<Article, String>() {

			public void update(int index, Article article, String value) {
				// Value is the button value. Object is the row object.
//				Window.alert("You clicked: " + value);
//				Window.alert("Object: " + store);
//				Window.alert("Name: " + store.getName());

				EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

				AsyncCallback<Void> deletecallback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub

						dataProvider.getList().remove(article);
						Notification.show("Artikel wurde gelöscht");
					}

				};

				elv.delete(article, deletecallback);
			}

		});
		stringColumn.setFieldUpdater(new FieldUpdater<Article, String>() {

			public void update(int index, Article article, String value) {
				// Value is the textCell value. Object is the row object.
				EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();
				article.setName(value);

				AsyncCallback<Void> saveCallback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						Notification.show("Artikel wurde gespeichert");
					}

				};

				elv.save(article, saveCallback);
			}

		});

// Add the columns.
		table.addColumn(stringColumn, "Artikel");
		table.addColumn(deleteColumn, "");
		table.addColumn(categoryColumn, "Einheit");
		table.setColumnWidth(stringColumn, 20, Unit.PC);

// Connect the table to the data provider.
		dataProvider.addDataDisplay(table);
	}

	/**
	 * Sobald das Textfeld ausgefï¿½llt wurde, wird ein neuer Artikel nach dem
	 * Klicken des addButton erstellt.
	 */
	private class AddArticleClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {

//			Window.alert("Artikelname hinzufügen :" + nameTextBox.getValue());
//			Window.alert("Artikeleinheit hinzufügen :" + unitListBox.getSelectedValue());
			// Persistiere in die Datenbank
			elv.createArticle(nameTextBox.getValue(), unitListBox.getSelectedValue(), new ArticleCreationCallback());

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
			if (article == null) {
				Notification.show("Der Artikel existiert bereits");
			} else {
				Notification.show("Der Artikel wurde erfolgreich erstellt");

				dataProvider.getList().add(article);
				dataProvider.refresh();
			}

		}
	}

	/**
	 * Hiermit wird der Erstellvorgang einer neuen Artikel abbgebrochen.
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();
		}

	}

}