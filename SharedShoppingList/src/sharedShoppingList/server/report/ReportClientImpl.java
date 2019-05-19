package sharedShoppingList.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import sharedShoppingList.shared.Einkaufslistenverwaltung;
import sharedShoppingList.shared.ReportClient;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllListEntries;
import sharedShoppingList.shared.report.AllListEntriesByPeriod;
import sharedShoppingList.shared.report.AllListEntriesByStore;

/**
 * Die Klasse <code>ReportClienImpl</code> implementiert das Interface
 * ReportClient. In der Klasse ist neben EinkaufslistenverwaltungImpls sämtliche
 * Applikationslogik vorhanden.
 * 
 * @author Nico Weiler
 * @version 1.0
 */

public class ReportClientImpl extends RemoteServiceServlet implements ReportClient {
	
	/**
	 * Der ReportClient benötigt Zugriff auf die ELV,
	 * da dort wichtige Methoden für die Koexistenz von Datenobjekten enthalten sind.
	 */
	
	private Einkaufslistenverwaltung verwaltung;
	
	/** OriginalKommentar
     * <p>
     * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
     * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu
     * ist ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
     * Konstruktors ist durch die Client-seitige Instantiierung durch
     * <code>GWT.create(Klassenname.class)</code> nach derzeitigem Stand nicht
     * möglich.
     * </p>
     * <p>
     * Es bietet sich also an, eine separate Instanzenmethode zu erstellen, die
     * Client-seitig direkt nach <code>GWT.create(Klassenname.class)</code>
     * aufgerufen wird, um eine Initialisierung der Instanz vorzunehmen.
     * </p>
     */

	public ReportClientImpl() throws IllegalArgumentException {
		
	}
	
	/**
     * Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor.
     * 
     * @see ReportClientImpl
     */
	
	public void init()throws IllegalArgumentException {
		
	}
	@Override
	public void setArticle(Article a) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AllListEntries createAllListEntriesByUserReport(User user) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllListEntriesByStore createAllListEntriesByStoreReport(Store store) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllListEntriesByPeriod createAllListEntriesByPeriodReport(Article article) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
