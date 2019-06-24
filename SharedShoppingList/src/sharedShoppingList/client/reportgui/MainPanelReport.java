package sharedShoppingList.client.reportgui;

import java.util.Vector;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import sharedShoppingList.client.ClientsideSettings;
import sharedShoppingList.shared.ReportClientAsync;
import sharedShoppingList.shared.bo.Store;

public class MainPanelReport extends VerticalPanel {

	ReportClientAsync repoClient = ClientsideSettings.getReportGenerator();

	Label fromText = new Label("Von:");
	HorizontalPanel hp = new HorizontalPanel();
	VerticalPanel fromVp = new VerticalPanel();
	ListBox storeListBox = new ListBox();
	DateBox FromDateBox = new DateBox();
	Button confirmButton = new Button("Report erstellen");

	public void onLoad() {

		fromVp.add(fromText);
		fromVp.add(FromDateBox);
		hp.add(fromVp);
		hp.add(storeListBox);
		hp.add(confirmButton);

		this.add(hp);

		DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();
		FromDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		FromDateBox.getDatePicker().setYearArrowsVisible(true);

		repoClient.getStores(new AsyncCallback<Vector<Store>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Stores konnten nicht in die ListBox geladen werden");

			}

			@Override
			public void onSuccess(Vector<Store> result) {

				Window.alert("store" + result);

				for (Store store : result) {
					Window.alert("store" + store.getName());
					storeListBox.addItem(store.getName());
//					storeListBox.setValue(1, store.getName());

				}

			}

		});

	}

}