package sharedShoppingList.client.gui;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.EinkaufslistenverwaltungAsync;
import sharedShoppingList.shared.bo.Favourite;

/**
 * Formular für das Anzeigen der favorisierten Artikel im Datenstamm
 * 
 * @author patricktreiber
 *
 */

public class FavoriteArticleForm extends VerticalPanel {

	EinkaufslistenverwaltungAsync elv = ClientsideSettings.getEinkaufslistenverwaltung();

	private FlexTable favoriteArticleFlexTable = new FlexTable();
	private Label nameLabel = new Label("Favoritenartikel");
	private Button cancelButton = new Button("abbrechen");
	private Button saveButton = new Button("Änderungen speichern");
	protected HorizontalPanel hpCancelandSafe = new HorizontalPanel();

	// In dieser Methode werden die Widgets der Form hinzugef�gt.
	public void onLoad() {

		hpCancelandSafe.add(cancelButton);
		hpCancelandSafe.add(saveButton);

		this.add(nameLabel);

		this.add(favoriteArticleFlexTable);
		this.add(hpCancelandSafe);

		this.setStylePrimaryName("details");

		cancelButton.addStyleName("Button");
		saveButton.addStyleName("speicherProfilButton.gwt-Button");
		favoriteArticleFlexTable.addStyleName("FlexTable");
		nameLabel.addStyleName("profilTitle");

		nameLabel.setHorizontalAlignment(ALIGN_CENTER);
		nameLabel.setWidth("100%");

		favoriteArticleFlexTable.setWidth("70%");
		favoriteArticleFlexTable.setBorderWidth(2);
		favoriteArticleFlexTable.setSize("100%", "100%");

		favoriteArticleFlexTable.setCellPadding(10);

	}

	ArrayList<Favourite> favoriteArticles;

	// Konstruktor
	public FavoriteArticleForm() {

		cancelButton.addClickHandler(new CancelClickHandler());

	}

	private FlexTable createTable() {
		elv = ClientsideSettings.getEinkaufslistenverwaltung();
		if (favoriteArticleFlexTable == null) {
			favoriteArticleFlexTable = new FlexTable();

		}
//		textboxes = new ArrayList<CustomTextBox>();
//		textboxes.clear();
		favoriteArticleFlexTable.removeAllRows();
		favoriteArticleFlexTable.setText(0, 0, "Artikel");

		favoriteArticles = new ArrayList<Favourite>();

		// Lade alle Store aus der Datenbank
		elv.getAllFavourites(new AsyncCallback<Vector<Favourite>>() {
			@Override
			public void onFailure(Throwable caught) {
				Notification.show("failure");
			}

			@Override
			public void onSuccess(Vector<Favourite> result) {
				// Füge alle Elemente der Datenbank in die Liste hinzu
				for (Favourite favourite : result) {

					favoriteArticles.add(favourite);
					setContentOfFavouriteArticleFlexTable(favourite);
				}
				Notification.show("success");

			}
		});

		return favoriteArticleFlexTable;

	}

	private void setContentOfFavouriteArticleFlexTable(Favourite favourite) {
		// Hole Zeilennummer, die aktuell bearbeitet wird
		int rowCount = favoriteArticleFlexTable.getRowCount();

		// Erstelle x Button
		CustomButton removeButton = new CustomButton();
		removeButton.setFavourite(favourite);

		removeButton.addClickHandler(new DeleteFavouriteClickHandler(removeButton));

		// Füge die TextBox und die ListBox in die FlexTable ein

		favoriteArticleFlexTable.setText(rowCount, 0, String.valueOf(favourite.getId()));
//		HIER BENÖTIGE ICH NOCH GETTER UM DIE WERTE SAUBER IN DIE SPALTEN DER TABELLE EINZUSETZEN
		favoriteArticleFlexTable.setWidget(rowCount, 1, removeButton);
	}

	/**
	 * Hiermit wird der das FavouritenForm gecleant
	 */
	private class CancelClickHandler implements ClickHandler {

		public void onClick(ClickEvent event) {
			RootPanel.get("details").clear();

		}

	}

	private class CustomButton extends Button {
		Favourite favourite;

		public Favourite getFavourite() {
			return favourite;
		}

		public void setFavourite(Favourite favourite) {
			this.favourite = favourite;
		}
	}

	private class DeleteFavouriteClickHandler implements ClickHandler {

		private CustomButton cB;

		public DeleteFavouriteClickHandler(CustomButton cB) {

			this.cB = cB;
		}

		@Override
		public void onClick(ClickEvent event) {
			if (cB != null) {

				elv.deleteArticle(cB.getFavourite(), new DeleteFavouriteCallback());

			} else {
				Notification.show("Der Favourite konnte nicht gelöscht werden");
			}
		}
	}

	private class DeleteFavouriteCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void result) {

			favoriteArticles.clear();
			createTable();
		}

	}

}