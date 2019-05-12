package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.Article;

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
	
	

}
