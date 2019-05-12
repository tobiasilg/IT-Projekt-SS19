package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllArticles;
import sharedShoppingList.shared.report.AllArticlesByPeriod;
import sharedShoppingList.shared.report.AllArticlesByStore;


/**
 * <p>
 * Synchrone Schnittstelle für eine RPC-fähige Klasse zur Erstellung von
 * Reports. 
 * </p>
 * <p>
 * Der ReportClient bietet die Möglichkeit, eine Menge von Berichten
 * (Reports) zu erstellen, die Menge von Daten bzgl. bestimmter Sachverhalte des
 * Systems zweckspezifisch darstellen.
 * 
 * @author Nico Weiler
 */

public interface ReportClient extends RemoteService {
	
	/**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
	   * SharedShoppingListImpl} notwendig.
	   * 
	   * @throws IllegalArgumentException
	   */
	  public void init() throws IllegalArgumentException;
	  /**
	   * Setzen des zugeordneten Artikels.
	   * 
	   * @para Artikel-Objekt
	   * @throws IllegalArgumentException
	   */
	  public void setArticle(Article a) throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllArticles</code>-Reports. Dieser
	   * Report-Typ stellt alle Artikel eines Einkäufers dar.
	   * 
	   * @param user eine Referenz auf das UserObject bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllArticle
	   */
	  public abstract AllArticles createAllArticlesByUserReport(User user) 
			  throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllArticlesByStore</code>-Reports. Dieser
	   * Report-Typ stellt alle eingekauften Artikel eines bestimmten Händlers dar.
	   * 
	   * @param store eine Referenz auf das StoreObject bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllArticleByStore
	   */
	  
	  public abstract AllArticlesByStore createAllArticlesByStoreReport(Store store) 
			  throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllArticlesByPeriod</code>-Reports. Dieser
	   * Report-Typ stellt alle eingekauften Artikel eines bestimmten Zeitraumes dar.
	   * 
	   * @param article eine Referenz auf das ArticleObject bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllArticle
	   */
	  
	  public abstract AllArticlesByPeriod createAllArticlesByPeriodReport(Article article) 
			  throws IllegalArgumentException;

}