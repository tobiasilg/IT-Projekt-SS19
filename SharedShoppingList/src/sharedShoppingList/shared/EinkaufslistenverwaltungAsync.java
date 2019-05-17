package sharedShoppingList.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.User;

/**
 * Das asynchrone Gegenstück des Interface {@link Einkaufslistenverwaltung}. Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link Einkaufslistenverwaltung}.
 * 
 * @author: Leon Seiz, Nico Weiler, Tobias Ilg
 */

public interface EinkaufslistenverwaltungAsync {

	void createArticle(String name, String unit, AsyncCallback<Article> callback);

	void save(Article article, AsyncCallback<Void> callback);

	void delete(Article article, AsyncCallback<Void> callback);

	void getAllArticles(AsyncCallback<Vector<Article>> callback);

	void getAllArticlesOf(User user, AsyncCallback<Vector<Article>> callback);

}
