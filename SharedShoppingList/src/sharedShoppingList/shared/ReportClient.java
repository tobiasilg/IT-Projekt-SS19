package sharedShoppingList.shared;

import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllListEntries;
import sharedShoppingList.shared.report.AllListEntriesByPeriod;
import sharedShoppingList.shared.report.AllListEntriesByStore;
import sharedShoppingList.shared.report.AllListEntriesByStoreAndPeriod;


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

@RemoteServiceRelativePath("reportClient")
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
	  
	  public Group getGroup(User user) throws IllegalArgumentException;
	  
	  public Vector<Store> getStores() throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllListEntries</code>-Reports. Dieser
	   * Report-Typ stellt alle Artikel eines Einkäufers dar.
	   * 
	   * @param user eine Referenz auf das UserObject bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllArticle
	   */
	  public abstract AllListEntries createAllListEntriesByUserReport(User user) 
			  throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllListEntriesByStore</code>-Reports. Dieser
	   * Report-Typ stellt alle eingekauften Artikel eines bestimmten Händlers dar.
	   * 
	   * @param store eine Referenz auf das StoreObject bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllArticleByStore
	   */
	  
	  public abstract AllListEntriesByStore createAllListEntriesByStoreReport(Store store) 
			  throws IllegalArgumentException;
	  
	  /**
	   * Erstellen eines <code>AllListEntriesByPeriod</code>-Reports. Dieser
	   * Report-Typ stellt alle eingekauften Artikel eines bestimmten Zeitraumes dar.
	   * 
	   * @param article eine Referenz auf das ArticleObject bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllArticle
	   */
	  
	  public abstract AllListEntriesByPeriod createAllListEntriesByPeriodReport(Timestamp beginningDate) 
			  throws IllegalArgumentException;
	  
	  public abstract AllListEntriesByStoreAndPeriod createListByPeriodAndStore(Store store, Timestamp beginningDate, Timestamp endDate);

}
