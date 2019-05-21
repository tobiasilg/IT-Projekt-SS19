package sharedShoppingList.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import sharedShoppingList.client.gui.Header;

public class SharedShoppingListReportEntry implements EntryPoint {

		Header headerReport = new Header();
		RootPanel rootPanelHeaderReport = RootPanel.get("header");
		
		
		public void onModuleLoad() {

			rootPanelHeaderReport.add(headerReport);

		}
}
