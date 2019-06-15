package sharedShoppingList.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.reportgui.HeaderRepo;

public class SharedShoppingListReportEntry implements EntryPoint {

	HeaderRepo headerReport = new HeaderRepo();
	RootPanel rootPanelHeaderReport = RootPanel.get("header");

	public void onModuleLoad() {

		rootPanelHeaderReport.add(headerReport);

	}
}
