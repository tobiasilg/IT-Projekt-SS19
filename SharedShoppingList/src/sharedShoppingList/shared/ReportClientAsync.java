package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllArticles;
import sharedShoppingList.shared.report.AllArticlesByPeriod;
import sharedShoppingList.shared.report.AllArticlesByStore;

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

	void setArticle(Article a, AsyncCallback<Void> callback);

	void createAllArticlesByUserReport(User u, AsyncCallback<AllArticles> callback);

	void createAllArticlesByStoreReport(Store store, AsyncCallback<AllArticlesByStore> callback);

	void createAllArticlesByPeriodReport(Article article, AsyncCallback<AllArticlesByPeriod> callback);
	
	

}
