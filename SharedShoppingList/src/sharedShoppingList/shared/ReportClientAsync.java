package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllListEntries;
import sharedShoppingList.shared.report.AllListEntriesByPeriod;
import sharedShoppingList.shared.report.AllListEntriesByStore;

/**
 * Das asynchrone Gegenstück des Interface ReportClient. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link ReportClient}.
 * 
 * @author Nico Weiler
 */

public interface ReportClientAsync {

	void init(AsyncCallback<Void> callback);


	void createAllListEntriesByUserReport(User user, AsyncCallback<AllListEntries> callback);

	void createAllListEntriesByStoreReport(Store store, AsyncCallback<AllListEntriesByStore> callback);

	void createAllListEntriesByPeriodReport(Article article, AsyncCallback<AllListEntriesByPeriod> callback);

	void setListEntry(ListEntry listEntry, AsyncCallback<Void> callback);

	
	
	

}
