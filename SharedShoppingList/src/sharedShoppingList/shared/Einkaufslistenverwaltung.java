package sharedShoppingList.shared;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.bo.Group;

/*
 * @author: Leon Seiz, Nico Weiler, Tobias Ilg
 */

@RemoteServiceRelativePath("einkaufslistenverwaltung")
public interface Einkaufslistenverwaltung extends RemoteService{
	
	public List<ListEntry> getEntriesByStoreAndDate(Store store, Timestamp beginningDate);

	Article createArticle(String name, String unit) throws IllegalArgumentException;

	void save(Article article) throws IllegalArgumentException;

	void delete(Article article) throws IllegalArgumentException;

	Vector<Article> getAllArticles() throws IllegalArgumentException;

	Vector<Article> getAllArticlesOf(User user) throws IllegalArgumentException;
	
	Group createGroup(String name) throws IllegalArgumentException;

	Vector<Group> getAllGroups() throws IllegalArgumentException;

//	Group findGroupById(int id) throws IllegalArgumentException;

	void save(Group group) throws IllegalArgumentException;

	public void delete(ShoppingList shoppingList) throws IllegalArgumentException;
	
	ShoppingList createShoppingList(String name) throws IllegalArgumentException;

	Vector<ShoppingList> getAll() throws IllegalArgumentException;

	Vector<ShoppingList> getAllByGroup(Group group) throws IllegalArgumentException;

	ShoppingList findShoppingListById(int id) throws IllegalArgumentException;

	void save(ShoppingList shoppingList) throws IllegalArgumentException;
	
	void delete(User user) throws IllegalArgumentException;
	
	public Store createStore(String name) throws IllegalArgumentException;
	
	public void delete(Store store) throws IllegalArgumentException;
	
	public Vector<Store> getAllStores(Store store) throws IllegalArgumentException;
	
	public Store getStoreByID( int id) throws IllegalArgumentException;
	
	public void save(Store store) throws IllegalArgumentException;
	
	public ListEntry createListentry(String name) throws IllegalArgumentException;
	
	public void save(ListEntry listentry) throws IllegalArgumentException;
	
	public void delete(ListEntry listEntry) throws IllegalArgumentException;
	
	public Vector<ListEntry> getAllListEntriesByArticle(Article article) throws IllegalArgumentException;
	
	public Vector<ListEntry> getAllListEntriesByStore(Store store) throws IllegalArgumentException;
	
	public Vector<ListEntry>getAllListEntriesByUser(User user) throws IllegalArgumentException;

	void delete(Group group)throws IllegalArgumentException;
	
	public void save(User user) throws IllegalArgumentException;

}
