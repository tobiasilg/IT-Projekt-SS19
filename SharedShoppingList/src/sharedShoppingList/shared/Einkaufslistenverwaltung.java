package sharedShoppingList.shared;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.bo.Group;

/*
 * @author: Leon Seiz, Nico Weiler, Tobias Ilg
 */

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

	void delete(Group group) throws IllegalArgumentException;

}
