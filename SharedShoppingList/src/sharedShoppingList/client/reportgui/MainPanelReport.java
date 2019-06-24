package sharedShoppingList.client.reportgui;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.gui.Notification;
import sharedShoppingList.shared.ReportClientAsync;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.report.AllListEntriesByStoreAndPeriod;
import sharedShoppingList.shared.report.HTMLReportWriter;

/**
 * Diese Klasse stellt die Selektierungsfelder und den Report dar.
 * 
 * @author PatrickTreiber
 */

public class MainPanelReport extends VerticalPanel {

	ReportClientAsync repoClient = ClientsideSettings.getReportGenerator();

	/**
	 * Benoetigte Panel werden hier instanziiert.
	 */
	HorizontalPanel hp = new HorizontalPanel();
	VerticalPanel storeVp = new VerticalPanel();
	VerticalPanel fromVp = new VerticalPanel();

	/**
	 * Label für das Startdatum
	 */
	Label fromText = new Label("Von");

	/**
	 * Label für StoreTextBox
	 */
	Label storeText = new Label("Store auswählen");

	/**
	 * ListBox in der die Store angezeigt werden
	 */
	ListBox storeListBox = new ListBox();

	/**
	 * DateBox um das Startdatum auszuwählen
	 */
	DateBox FromDateBox = new DateBox();

	/**
	 * Button um sich den Report ausgeben zu lassen
	 */
	Button confirmButton = new Button("Report erstellen");

	private Timestamp sqlStartDate = null;
	private ArrayList<Store> allStores;
	private Store selectedStore = null;

	public void onLoad() {

		/**
		 * Zusammensetzung der Panels und Widgets
		 */
		fromVp.add(fromText);
		fromVp.add(FromDateBox);
		storeVp.add(storeText);
		storeVp.add(storeListBox);
		hp.add(fromVp);

		hp.add(storeVp);
		hp.add(confirmButton);

		this.add(hp);

		/**
		 * Der DateBox ein Zeitformat zuweisen
		 */
		DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();
		FromDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		FromDateBox.getDatePicker().setYearArrowsVisible(true);

		/**
		 * Hier holen wir uns alle Stores aus der Datenbank und schreiben diese in die
		 * ListBox
		 */
		repoClient.getStores(new AsyncCallback<Vector<Store>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Vector<Store> result) {
				ArrayList<Store> stores = new ArrayList<Store>();
				Store noStore = new Store();
				noStore.setId(0);
				noStore.setName("kein Store ausgewählt");
				stores.add(noStore);
				stores.addAll(result);
				allStores = stores;

				for (int i = 0; i < stores.size(); i++) {

					storeListBox.addItem(stores.get(i).getName());
					selectedStore = stores.get(0);

				}

			}

		});

		/**
		 * Clickhandler für den ConfirmButton (es erscheint der gewünschte Report)
		 */
		confirmButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				sqlStartDate = new java.sql.Timestamp(FromDateBox.getValue().getYear(),
						FromDateBox.getValue().getMonth(), FromDateBox.getValue().getDate(), 0, 0, 0, 0);

//!!!!!!!!!!!!wie führe ich wietere Aktionen aus wenn kein Datum eingetragen ist
				selectedStore = allStores.get(storeListBox.getSelectedIndex());
				Window.alert("Datum: " + sqlStartDate);
				Window.alert("Store: " + selectedStore.getName());

				if (sqlStartDate == null) {
					repoClient.createListByPeriodAndStore(selectedStore, null,
							new createListByPeriodAndStoreAsyncCallback());
					Window.alert("Nur nach Stores filtern");

				} else if (selectedStore.getId() == 0) {
					repoClient.createListByPeriodAndStore(null, sqlStartDate,
							new createListByPeriodAndStoreAsyncCallback());
					Window.alert("Nur nach Datum filtern");
				} else {
					repoClient.createListByPeriodAndStore(selectedStore, sqlStartDate,
							new createListByPeriodAndStoreAsyncCallback());
					Window.alert("Nach allem Filtern");
				}
			}

		});

	}

	/**
	 * Diese Nested Class wird als Callback für das Erzeugen des Reports benötigt.
	 */
	class createListByPeriodAndStoreAsyncCallback implements AsyncCallback<AllListEntriesByStoreAndPeriod> {
		@Override
		public void onFailure(Throwable caught) {
			/*
			 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message aus.
			 */
			Notification.show("Bin in der onFailure vom createListByPeriodAndStoreAsyncCallback");

		}

		@Override
		public void onSuccess(AllListEntriesByStoreAndPeriod result) {

			if (result != null) {
				HTMLReportWriter writer = new HTMLReportWriter();
				writer.process(result);
				RootPanel.get("report").clear();
				RootPanel.get("report").add(new HTML(writer.getReportText()));
			}

			Window.alert("komme bis zur onSuccess");

		}
	}

}