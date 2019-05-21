package sharedShoppingList.shared;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/*
 * @author: Leon Seiz, Nico Weiler, Tobias Ilg
 */

public interface EinkaufslistenverwaltungAsync {

	void getEntriesByStoreAndDate(Store store, Timestamp beginningDate, AsyncCallback<List<ListEntry>> callback);

	void createArticle(String name, String unit, AsyncCallback<Article> callback);

	void save(Article article, AsyncCallback<Void> callback);

	void delete(Article article, AsyncCallback<Void> callback);

	void getAllArticles(AsyncCallback<Vector<Article>> callback);

	void getAllArticlesOf(User user, AsyncCallback<Vector<Article>> callback);
	
	void createGroup(String name, AsyncCallback<Group> callback);

    void getAll(AsyncCallback<Vector<Group>> callback);

	void findById(User user, AsyncCallback<Vector<Group>> callback);

	void save(Group group, AsyncCallback<Void> callback);

	void delete(Group group, AsyncCallback<Void> callback);

}
