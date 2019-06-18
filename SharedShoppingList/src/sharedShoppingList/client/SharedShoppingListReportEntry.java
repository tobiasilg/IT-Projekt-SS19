package sharedShoppingList.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.reportgui.HeaderRepo;
import sharedShoppingList.client.reportgui.MainPanelReport;

public class SharedShoppingListReportEntry implements EntryPoint {

	HeaderRepo headerReport = new HeaderRepo();
	RootPanel rootPanelHeaderReport = RootPanel.get("header");

	MainPanelReport mainPanelReport = new MainPanelReport();
	RootPanel rootPanelMainPanelReport = RootPanel.get("details");

	public void onModuleLoad() {

		rootPanelHeaderReport.add(headerReport);
		rootPanelMainPanelReport.add(mainPanelReport);

	}
}
