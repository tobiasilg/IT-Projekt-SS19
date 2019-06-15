package sharedShoppingList.server.report;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import sharedShoppingList.server.EinkaufslistenverwaltungImpl;
import sharedShoppingList.server.db.ListEntryMapper;
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
	 * Der ReportClient benötigt Zugriff auf die ELV, da dort wichtige Methoden für
	 * die Koexistenz von Datenobjekten enthalten sind.
	 */

	private Einkaufslistenverwaltung elv;

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

	/**
	 * Report, der alle Listeneinträge ausgibt, je nachdem, was Selektiert wird.
	 * 
	 * @see ListEntryMapper.findByStoreAndDate --> Hier befinden sich die jeweiligen
	 *      SQL Statements
	 * @see sharedShoppingList.shared.ReportClient#createListByPeriodAndStore(sharedShoppingList.shared.bo.Store,
	 *      java.sql.Timestamp)
	 */

	@Override
	public AllListEntriesByStoreAndPeriod createListByPeriodAndStore(Store store, Timestamp beginningDate) {

		if (this.getEinkaufslistenverwaltung() == null) {
			return null;
		}

		List<ListEntry> listEntries = elv.getEntriesByStoreAndDate(store, beginningDate);
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
		header.addSubParagraph(new SimpleParagraph(store.getName()));

		// StoreID aufnehmen
		header.addSubParagraph(new SimpleParagraph("Store-ID.: " + store.getId()));

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

}
