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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.client.SharedShoppingListReportEntry.CurrentReportUser;
import sharedShoppingList.client.gui.Notification;
import sharedShoppingList.shared.ReportClientAsync;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
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
	VerticalPanel toVp = new VerticalPanel();
	HorizontalPanel dateVp = new HorizontalPanel();
	VerticalPanel groupVp = new VerticalPanel();

	Grid reportGrid = new Grid(5, 5);
	/**
	 * Label für StoreTextBox
	 */
	Label storeText = new Label("Händler");

	/**
	 * Label für GridTextBox
	 */
	Label gridText = new Label("Filter");

	/**
	 * Label für GroupTextBox
	 */
	Label groupText = new Label("Gruppe");

	/**
	 * ListBox in der die Store angezeigt werden
	 */
	ListBox storeListBox = new ListBox();

	/**
	 * Text Datum auswählen
	 */
	Label dateLabel = new Label("Datum");

	/**
	 * DateBox um das Startdatum auszuwählen
	 */
	DateBox FromDateBox = new DateBox();

	/**
	 * DateBox um das Enddatum auszuwählen
	 */
	DateBox toDateBox = new DateBox();

	/**
	 * Button um sich den Report ausgeben zu lassen
	 */
	Button confirmButton = new Button("Report erstellen");

	/**
	 * Drop-Down-Liste zur Gruppenauswahl
	 */
	private ListBox groupSelectorListBox = new ListBox();

	private Timestamp sqlStartDate = null;
	private Timestamp sqlEndDate = null;
	private ArrayList<Store> allStores;
	private ArrayList<Group> allGroups;
	private Store selectedStore = null;
	private Group selectedGroup = null;
	private User user = CurrentReportUser.getUser();
	int currentGroupID;

	public Group getGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public void onLoad() {

		Window.alert("User: " + user.getId());

		/**
		 * Zusammensetzung der Panels und Widgets
		 */
		reportGrid.setWidget(0, 0, gridText);

		reportGrid.setWidget(1, 0, groupText);
		reportGrid.setWidget(1, 1, groupSelectorListBox);

		reportGrid.setWidget(2, 0, storeText);
		reportGrid.setWidget(2, 1, storeListBox);

		reportGrid.setWidget(3, 0, dateLabel);
		reportGrid.setWidget(3, 1, FromDateBox);
		reportGrid.setWidget(3, 2, toDateBox);

		reportGrid.setWidget(4, 2, confirmButton);

		reportGrid.setCellPadding(2);
		reportGrid.setCellSpacing(4);

		this.add(reportGrid);

		FromDateBox.getElement().setPropertyString("placeholder", "von ");
		toDateBox.getElement().setPropertyString("placeholder", "bis ");

		FromDateBox.setStyleName("Datebox");
		toDateBox.setStyleName("Datebox");

		/**
		 * Der DateBox ein Zeitformat zuweisen
		 */
		DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();
		FromDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		FromDateBox.getDatePicker().setYearArrowsVisible(true);

		toDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		toDateBox.getDatePicker().setYearArrowsVisible(true);

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

		repoClient.getGroupsOfUser(user, new AsyncCallback<Vector<Group>>() {

			@Override
			public void onFailure(Throwable caught) {
				Notification.show("Error get Group: " + caught);
			}

			@Override
			public void onSuccess(Vector<Group> result) {

//				Group currentGroup = result;
//				currentGroup.getId();

				Window.alert("result: " + result);

				ArrayList<Group> groups = new ArrayList<Group>();
				Group noGroup = new Group();
				noGroup.setId(0);
				noGroup.setName("keine Gruppe ausgewählt");

				groups.add(noGroup);
				groups.addAll(result);
				allGroups = groups;

				for (int i = 0; i < groups.size(); i++) {

					groupSelectorListBox.addItem(groups.get(i).getName());
					selectedGroup = result.get(0);
				}

			}

		});

		/**
		 * Clickhandler für den ConfirmButton (es erscheint der gewünschte Report)
		 */
		confirmButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				selectedStore = allStores.get(storeListBox.getSelectedIndex());

				Window.alert("Store: " + selectedStore.getName());

				if (FromDateBox.getValue() == null && toDateBox.getValue() == null) {

					repoClient.createListByPeriodAndStore(selectedStore, null, null, currentGroupID,
							new createListByPeriodAndStoreAsyncCallback());
					Window.alert("Nur nach Stores filtern");

				} else if (selectedStore.getId() == 0) {
					sqlStartDate = new java.sql.Timestamp(FromDateBox.getValue().getTime());
					sqlEndDate = new java.sql.Timestamp(toDateBox.getValue().getTime());
					Window.alert("Datum von: " + sqlStartDate);
					Window.alert("Datum bis: " + sqlEndDate);
					repoClient.createListByPeriodAndStore(null, sqlStartDate, sqlEndDate, currentGroupID,
							new createListByPeriodAndStoreAsyncCallback());
					Window.alert("Nur nach Datum filtern");
				} else {
					sqlStartDate = new java.sql.Timestamp(FromDateBox.getValue().getTime());
					sqlEndDate = new java.sql.Timestamp(toDateBox.getValue().getTime());
					Window.alert("Datum: " + sqlStartDate);
					Window.alert("Datum bis: " + sqlEndDate);
					repoClient.createListByPeriodAndStore(selectedStore, sqlStartDate, sqlEndDate, currentGroupID,
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
			Notification.show(caught.getMessage());

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

//	/**
//	 * Diese Nested Class wird als Callback für das Erzeugen des Reports benötigt.
//	 */
//	class GetGroupCallback implements AsyncCallback<Group> {
//		@Override
//		public void onFailure(Throwable caught) {
//			/*
//			 * Wenn ein Fehler auftritt, dann geben wir eine kurze Log Message aus.
//			 */
//			Notification.show(caught.getMessage());
//
//		}
//
//		@Override
//		public void onSuccess(Group result) {
//
//			if (result != null) {
//				HTMLReportWriter writer = new HTMLReportWriter();
//				writer.process(result);
//				RootPanel.get("report").clear();
//				RootPanel.get("report").add(new HTML(writer.getReportText()));
//			}
//
//			Window.alert("komme bis zur onSuccess");
//
//		}
//	}
}