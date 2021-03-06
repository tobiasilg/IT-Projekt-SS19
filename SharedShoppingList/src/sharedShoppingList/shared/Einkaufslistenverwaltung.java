package sharedShoppingList.shared;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Favourite;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.ShoppingList;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;

/**
 * @author: Leon Seiz, Nico Weiler, Tobias Ilg
 * 
 */

@RemoteServiceRelativePath("einkaufslistenverwaltung")
public interface Einkaufslistenverwaltung extends RemoteService {

	public List<ListEntry> getEntriesByStoreAndDate(Store store, Timestamp beginningDate, Timestamp endDate,
			int groupId);

	public List<ListEntry> getEntriesByDate(Timestamp beginningDate);

	Article createArticle(String name, String unit) throws IllegalArgumentException;

	void save(Article article) throws IllegalArgumentException;

	Article delete(Article article) throws IllegalArgumentException;

	Vector<Article> getAllArticles() throws IllegalArgumentException;

	Vector<Article> getAllArticlesOf(User user) throws IllegalArgumentException;

	Vector<Article> getArticleByGroup(Group group) throws IllegalArgumentException;

	Vector<Store> findStoreByGroup(Group group) throws IllegalArgumentException;

	Group createGroup(User user, String name) throws IllegalArgumentException;

	Vector<Group> getAllGroups() throws IllegalArgumentException;

	Group getGroupById(int id) throws IllegalArgumentException;

	void save(Group group) throws IllegalArgumentException;

	public void delete(ShoppingList shoppingList) throws IllegalArgumentException;

	ShoppingList createShoppingList(String name, Group group) throws IllegalArgumentException;

	Vector<ShoppingList> getAll() throws IllegalArgumentException;

	Vector<ShoppingList> getAllByGroup(Group group) throws IllegalArgumentException;

	ShoppingList findShoppingListById(int id) throws IllegalArgumentException;

	void save(ShoppingList shoppingList) throws IllegalArgumentException;

	void delete(User user) throws IllegalArgumentException;

	public Store createStore(String name) throws IllegalArgumentException;

	
	public Store delete(Store store) throws IllegalArgumentException;
	

	public Vector<Store> getAllStores() throws IllegalArgumentException;

	public Store getStoreByID(int id) throws IllegalArgumentException;

	public void save(Store store) throws IllegalArgumentException;

	public ListEntry createListentry(String name, User user, Article article, double amount, Store store,
			ShoppingList sl) throws IllegalArgumentException;

	public void save(ListEntry listentry) throws IllegalArgumentException;

	public void delete(ListEntry listEntry) throws IllegalArgumentException;

	public Vector<ListEntry> getAllListEntriesByArticle(Article article) throws IllegalArgumentException;

	public Vector<ListEntry> getAllListEntriesByStore(Store store) throws IllegalArgumentException;

	public Vector<ListEntry> getAllListEntriesByUser(User user) throws IllegalArgumentException;

	void delete(Group group) throws IllegalArgumentException;

	public void save(User user) throws IllegalArgumentException;

	public Vector<User> getAllUsers() throws IllegalArgumentException;

	public User getUserByID(int id) throws IllegalArgumentException;

	public User getUserByName(String name) throws IllegalArgumentException;

	public User getUserByMail(String gmail) throws IllegalArgumentException;

	Vector<User> getUsersByGroup(Group group) throws IllegalArgumentException;

	
	public Vector<Group> getGroupsByUser (User user) throws IllegalArgumentException;
	
	public Favourite createFavourite (ListEntry listentry, Group group) throws IllegalArgumentException;
	
	public void delete(ListEntry listentry, Group group) throws IllegalArgumentException;
	
	public Vector <Favourite> getAllFavourites() throws IllegalArgumentException;
	
	public Vector<ListEntry>getAllListEntriesByShoppingList (ShoppingList sl) throws IllegalArgumentException;
	
	public Boolean changed(Vector<ListEntry> listEntry, ShoppingList shoppingList) throws IllegalArgumentException;

	public Boolean changed(ShoppingList shoppingList) throws IllegalArgumentException;

	public Boolean changed(Group group, User user) throws IllegalArgumentException;

	public Vector<ListEntry> filterByStore(Store store) throws IllegalArgumentException;

	public Vector<ListEntry> filterByUser(User user, ShoppingList sl) throws IllegalArgumentException;

	public Article getArticleById(int id) throws IllegalArgumentException;

	void addUser(User user, Group group) throws IllegalArgumentException;

	void removeUserMembership(User user, Group group) throws IllegalArgumentException;

	public User createUser(String name, String gmail, String username) throws IllegalArgumentException;
	
	public void setNewOne(ShoppingList sl, ListEntry le) throws IllegalArgumentException;

}
