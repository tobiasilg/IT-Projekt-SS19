package sharedShoppingList.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.User;

/*
 * @author: Leon Seiz, Nico Weiler, Tobias Ilg
 */

public interface Einkaufslistenverwaltung extends RemoteService{
	
	public Article createArticle(String name, String unit) throws IllegalArgumentException;
	public void save (Article article) throws IllegalArgumentException;
	public void delete (Article article)throws IllegalArgumentException;
	public Vector<Article> getAllArticles()throws IllegalArgumentException;
	public Vector<Article> getAllArticlesOf(User user)throws IllegalArgumentException;
	
	

}
