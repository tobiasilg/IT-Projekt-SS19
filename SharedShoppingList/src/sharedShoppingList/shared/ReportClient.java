package sharedShoppingList.shared;

import com.google.gwt.user.client.rpc.RemoteService;

import sharedShoppingList.shared.bo.Article;


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

}
