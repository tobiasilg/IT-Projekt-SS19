package sharedShoppingList.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import sharedShoppingList.server.EinkaufslistenverwaltungImpl;
import sharedShoppingList.shared.Einkaufslistenverwaltung;
import sharedShoppingList.shared.ReportClient;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllListEntries;
import sharedShoppingList.shared.report.AllListEntriesByPeriod;
import sharedShoppingList.shared.report.AllListEntriesByStore;

public class ReportClientImpl extends RemoteServiceServlet implements ReportClient {

	/**
	 * 
	 */
	private Einkaufslistenverwaltung verwaltung=null;
	
	 /*
     * Ein ReportClientImpl-Objekt instantiiert für seinen Eigenbedarf eine
     * EinkaufslistenverwaltungImpl-Instanz.
     */
	
	public void init() {
		EinkaufslistenverwaltungImpl elv= new EinkaufslistenverwaltungImpl();
		elv.init();
		this.verwaltung=elv;
	}
	
	  /**
	   * Auslesen der zugehörigen Einkaufslistenverwaltung (interner Gebrauch).
	   * 
	   * @return das Objekt der ELV
	   */
	  protected Einkaufslistenverwaltung getEinkaufslistenverwaltung() {
	    return this.verwaltung;
	  }
	  
	  

	@Override
	public void setListEntry(ListEntry listEntry) throws IllegalArgumentException {
		//Methodenimplementierung in ELVImpl fehlt noch
		
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
