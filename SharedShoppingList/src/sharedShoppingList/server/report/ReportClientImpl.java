package sharedShoppingList.server.report;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import sharedShoppingList.server.EinkaufslistenverwaltungImpl;
import sharedShoppingList.server.db.ListEntryMapper;
import sharedShoppingList.server.db.UserMapper;
import sharedShoppingList.shared.Einkaufslistenverwaltung;
import sharedShoppingList.shared.ReportClient;
import sharedShoppingList.shared.bo.Article;
import sharedShoppingList.shared.bo.ListEntry;
import sharedShoppingList.shared.bo.Store;
import sharedShoppingList.shared.bo.User;
import sharedShoppingList.shared.report.AllListEntries;
import sharedShoppingList.shared.report.AllListEntriesByPeriod;
import sharedShoppingList.shared.report.AllListEntriesByStore;
import sharedShoppingList.shared.report.AllListEntriesByStoreAndPeriod;
import sharedShoppingList.shared.report.Column;
import sharedShoppingList.shared.report.CompositeParagraph;
import sharedShoppingList.shared.report.Row;
import sharedShoppingList.shared.report.SimpleParagraph;
import sharedShoppingList.server.db.ArticleMapper;
import sharedShoppingList.server.db.FavouriteMapper;
import sharedShoppingList.server.db.GroupMapper;
import sharedShoppingList.server.db.ListMapper;
import sharedShoppingList.server.db.StoreMapper;
import sharedShoppingList.shared.bo.Favourite;
import sharedShoppingList.shared.bo.Group;
import sharedShoppingList.shared.bo.ShoppingList;


/**
 * Die Klasse <code>ReportClienImpl</code> implementiert das Interface
 * ReportClient. In der Klasse ist neben EinkaufslistenverwaltungImpls sämtliche
 * Applikationslogik vorhanden.
 * 
 * @author Nico Weiler, Tobias Ilg
 * @version 1.0
 */

public class ReportClientImpl extends RemoteServiceServlet implements ReportClient {

	/**
	 * Der ReportClient benötigt Zugriff auf die ELV, da dort wichtige Methoden für
	 * die Koexistenz von Datenobjekten enthalten sind.
	 */

	private Einkaufslistenverwaltung elv = null;

	/**
	 * OriginalKommentar
	 * <p>
	 * Ein <code>RemoteServiceServlet</code> wird unter GWT mittels
	 * <code>GWT.create(Klassenname.class)</code> Client-seitig erzeugt. Hierzu ist
	 * ein solcher No-Argument-Konstruktor anzulegen. Ein Aufruf eines anderen
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

	public void init() throws IllegalArgumentException {

		/*
		 * Ein ReportClientImpl-Objekt instantiiert für seinen Eigenbedarf eine
		 * EinkaufslistenverwaltungImpl-Instanz.
		 */
		EinkaufslistenverwaltungImpl e = new EinkaufslistenverwaltungImpl();
		e.init();
		
		// Rückgabe des ELVerwaltungsobjekts
		this.elv = e;

	}

	/**
	 * Auslesen der zugehörigen Einkaufslistenverwaaltung (interner Gebrauch).
	 * 
	 * @return das elv Objekt
	 */
	protected Einkaufslistenverwaltung getEinkaufslistenverwaltung() {
		return this.elv;
	}
	
	
	/**
	* Nutzer-Identifikation (auch zu Testzwecken)
	*/
	public User getUser(int id) throws IllegalArgumentException {
	return elv.getUserByID(id);
	}


	@Override
	public AllListEntries createAllListEntriesByUserReport(User user) throws IllegalArgumentException {

		if (this.getEinkaufslistenverwaltung() == null) {
				return null;
			}

			List<ListEntry> listEntries = elv.getAllListEntriesByUser(user);
			AllListEntries result = new AllListEntries();

			/**
			 * Titel des Reports
			 */

			result.setTitle("Report aller Listeneinträge des Nutzers:");

			/**
			 * Erstellungsdatum wird gesetzt
			 */

			result.setCreated(new Date());

			for (ListEntry le : listEntries) {

				// Eine leere Zeile anlegen.
				Row entryRow = new Row();

				// Erste Spalte: ListEntry ID
				entryRow.addColumn(new Column(String.valueOf(le.getId())));

				// Zweite Spalte: Artikel ID
				entryRow.addColumn(new Column(String.valueOf(le.getArticleId())));

				// Dritte Spalte: Menge
				entryRow.addColumn(new Column(String.valueOf(le.getAmount())));

				// Vierte Spalte: Käufer
				entryRow.addColumn(new Column(String.valueOf(le.getUserId())));

				// Fünfte Spalte: Händler
				entryRow.addColumn(new Column(String.valueOf(le.getStoreId())));

				// und schließlich die Zeile dem Report hinzufügen.
				result.addRow(entryRow);
			}
			return result;
	
	}

	/**
	 * Dieser Report gibt alle Listeneinträge eines spez. Stores aus
	 */

	@Override
	public AllListEntriesByStore createAllListEntriesByStoreReport(Store store) {

		if (this.getEinkaufslistenverwaltung() == null) {
			return null;
		}

		List<ListEntry> listEntries = elv.getAllListEntriesByStore(store);
		AllListEntriesByStore result = new AllListEntriesByStore();

		/**
		 * Titel des Reports
		 */

		result.setTitle("Report:");

		/**
		 * Erstellungsdatum wird gesetzt
		 */

		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben auf
		 * dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher die
		 * Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name des Händlers aufnehmen
		header.addSubParagraph(new SimpleParagraph(store.getName()));

		// StoreID aufnehmen
		header.addSubParagraph(new SimpleParagraph("Store-ID.: " + store.getId()));

		// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		for (ListEntry le : listEntries) {

			// Eine leere Zeile anlegen.
			Row entryRow = new Row();

			// Erste Spalte: ListEntry ID
			entryRow.addColumn(new Column(String.valueOf(le.getId())));

			// Zweite Spalte: Artikel ID
			entryRow.addColumn(new Column(String.valueOf(le.getArticleId())));

			// Dritte Spalte: Menge
			entryRow.addColumn(new Column(String.valueOf(le.getAmount())));

			// Vierte Spalte: Käufer
			entryRow.addColumn(new Column(String.valueOf(le.getUserId())));

			// Fünfte Spalte: Händler
			entryRow.addColumn(new Column(String.valueOf(le.getStoreId())));

			// und schließlich die Zeile dem Report hinzufügen.
			result.addRow(entryRow);
		}
		return result;
	}



	/**
	 * Report, der alle Listeneinträge ausgibt, je nachdem, was Selektiert wird.
	 * 
	 * @see ListEntryMapper.findByStoreAndDate --> Hier befinden sich die jeweiligen
	 *      SQL Statements
	 * @see sharedShoppingList.shared.ReportClient#createListByPeriodAndStore(sharedShoppingList.shared.bo.Store,
	 *      java.sql.Timestamp)
	 */

	
	public AllListEntriesByStoreAndPeriod createListByPeriodAndStore(Store store, Timestamp beginningDate, Timestamp endDate, int groupId) {

		if (this.getEinkaufslistenverwaltung() == null) {
			return null;
		}

		List<ListEntry> listEntries = elv.getEntriesByStoreAndDate(store, beginningDate, endDate, groupId);
		AllListEntriesByStoreAndPeriod result = new AllListEntriesByStoreAndPeriod();
		

		/**
		 * Titel des Reports
		 */

		result.setTitle("Report:");

		/**
		 * Erstellungsdatum wird gesetzt
		 */

		result.setCreated(new Date());

		/*
		 * Ab hier erfolgt die Zusammenstellung der Kopfdaten (die Dinge, die oben auf
		 * dem Report stehen) des Reports. Die Kopfdaten sind mehrzeilig, daher die
		 * Verwendung von CompositeParagraph.
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Name des Händlers aufnehmen
		if(store != null) {
			
			header.addSubParagraph(new SimpleParagraph(store.getName()));

			// StoreID aufnehmen
			header.addSubParagraph(new SimpleParagraph("Store-ID.: " + store.getId()));
			
		}
		

		// Zeitraum anzeigen lassen
		
		if(beginningDate != null) {
			
			header.addSubParagraph(new SimpleParagraph("Zeitraum: Von " + beginningDate.getTime() + "bis heute"));

		}
		
		// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);
		
		Row headerRow= new Row();
		headerRow.addColumn(new Column("Artikel"));
		headerRow.addColumn(new Column("Menge"));
		headerRow.addColumn(new Column("Einheit"));
		headerRow.addColumn(new Column("Käufer"));
		headerRow.addColumn(new Column("Händler"));
		
		result.addRow(headerRow);
		
		for (ListEntry le : listEntries) {

			// Eine leere Zeile anlegen.
			Row entryRow = new Row();
			
			User user=elv.getUserByID(le.getUserId());
			Store listEntryStore=elv.getStoreByID(le.getStoreId());
			Article article = elv.getArticleById(le.getArticleId());
			
		
			
			
			entryRow.addColumn(new Column(article.getName()));
			
			entryRow.addColumn(new Column(String.valueOf(le.getAmount())));

			// Zweite Spalte: Artikel ID
			entryRow.addColumn(new Column(article.getUnit()));

			// Dritte Spalte: Menge
			

			// Vierte Spalte: Käufer
			entryRow.addColumn(new Column(user.getName()));

			// Fünfte Spalte: Händler
			entryRow.addColumn(new Column(listEntryStore.getName()));

			// und schließlich die Zeile dem Report hinzufügen.
			result.addRow(entryRow);
		}
		return result;
	}

	
	
	@Override
	public AllListEntriesByPeriod createAllListEntriesByPeriodReport(Timestamp beginningDate)  {

		if (this.getEinkaufslistenverwaltung() == null) {
			return null;
		}

		List<ListEntry> listEntries = elv.getEntriesByDate(beginningDate);
		AllListEntriesByPeriod result = new AllListEntriesByPeriod();

		/**
		 * Titel des Reports
		 */

		result.setTitle("Report:");

		/**
		 * Erstellungsdatum wird gesetzt
		 */

		result.setCreated(new Date());

		/*
		 * Zu den Kopfdaten wird der Zeitraum der Reportdaten hinzugefügt:
		 */
		CompositeParagraph header = new CompositeParagraph();

		// Zeitraum anzeigen lassen
		header.addSubParagraph(new SimpleParagraph("Zeitraum: Von " + beginningDate.getDate() + "bis heute"));

		// Hinzufügen der zusammengestellten Kopfdaten zu dem Report
		result.setHeaderData(header);

		for (ListEntry le : listEntries) {

			// Eine leere Zeile anlegen.
			Row entryRow = new Row();

			// Erste Spalte: ListEntry ID
			entryRow.addColumn(new Column(String.valueOf(le.getId())));

			// Zweite Spalte: Artikel ID
			entryRow.addColumn(new Column(String.valueOf(le.getArticleId())));

			// Dritte Spalte: Menge
			entryRow.addColumn(new Column(String.valueOf(le.getAmount())));

			// Vierte Spalte: Käufer
			entryRow.addColumn(new Column(String.valueOf(le.getUserId())));

			// Fünfte Spalte: Händler
			entryRow.addColumn(new Column(String.valueOf(le.getStoreId())));

			// und schließlich die Zeile dem Report hinzufügen.
			result.addRow(entryRow);
		}
		return result;
	}

	
	/** Muss implementiert werden, aber faktisch nicht benötigt*/
	@Override
	public void setArticle(Article a) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector<Store> getStores() throws IllegalArgumentException {
		
		/*
		 * Zugriff auf die bereits definierte Methode getAllStores mit hilfe des Zugriffs auf die ELV
		 */
		Vector<Store> stores = elv.getAllStores();
		
		if(stores!= null) {
			
			return stores;
		}else {
			return null;
		}
		
		
	}
	
	public Group getGroup(User user) throws IllegalArgumentException{
		
		Group group = elv.getGroupByUser(user);
		
		return group;
		
	}

}
