package sharedShoppingList.shared;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
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

    void getAllGroups(AsyncCallback<Vector<Group>> callback);

//	void findGroupById(User user, AsyncCallback<Vector<Group>> callback);

	void save(Group group, AsyncCallback<Void> callback);

	void delete(Group group, AsyncCallback<Void> callback);
	
	void createShoppingList(String name, AsyncCallback<ShoppingList> callback);
	
    void getAll(AsyncCallback<Vector<ShoppingList>> callback);
    
    void getAllByGroup(Group group, AsyncCallback<Vector<ShoppingList>> callback);
    
    void findShoppingListById(int id, AsyncCallback<ShoppingList> callback);
    
    void save(ShoppingList shoppingList, AsyncCallback<Void> callback);
  

	void delete(User user, AsyncCallback<Void> callback);

	void createStore(String name, AsyncCallback<Store> callback);

	void delete(Store store, AsyncCallback<Void> callback);

	void getAllStores(AsyncCallback<Vector<Store>> callback);

	void getStoreByID(int id, AsyncCallback<Store> callback);

	void save(Store store, AsyncCallback<Void> callback);

	void createListentry(String name, AsyncCallback<ListEntry> callback);

	void save(ListEntry listentry, AsyncCallback<Void> callback);

	void delete(ListEntry listEntry, AsyncCallback<Void> callback);

	void getAllListEntriesByArticle(Article article, AsyncCallback<Vector<ListEntry>> callback);

	void getAllListEntriesByStore(Store store, AsyncCallback<Vector<ListEntry>> callback);

	void getAllListEntriesByUser(User user, AsyncCallback<Vector<ListEntry>> callback);

	void delete(ShoppingList shoppingList, AsyncCallback<Void> callback);

	void save(User user, AsyncCallback<Void> callback);

	void getAllUsers(AsyncCallback<Vector<User>> callback);

	void getUserByID(int id, AsyncCallback<User> callback);

	void getUsersByGroup(Group group, AsyncCallback<Vector<User>> callback);

}
