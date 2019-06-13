package sharedShoppingList.shared.report;

import java.util.ArrayList;

import com.google.gwt.i18n.shared.DateTimeFormat;

/**
 * In dieser Klasse wird ein <code>Report</code> erstellt, der alle gekauften Artikel eines bestimmten Händlers (Supermarkt)
 * innerhalb eines bestimmten Zeitraums darstellt. Es befinden sich aber an dieser Stelle keine weiteren Attribute- und Methoden-
 * Implementierungen, denn diese befinden sich bereits in den Superklassen. Dennoch ist die Bestehung dieser Klasse relevant 
 * für die Deklarierung bestimmter Report-Typen um objektorientiert umgehen zu können. 
 * 
 * @author Nico Weiler
 *
 */

public class AllListEntriesByStoreAndPeriod extends SimpleReport {

	/**
	 *
	 */
	
	private static final long serialVersionUID = 1L;

	
	public void process(AllListEntriesByStoreAndPeriod r) {
		
		StringBuffer result = new StringBuffer();
		
		  DateTimeFormat df = DateTimeFormat.getFormat("dd.MM.yyyy");

			String HeadlineDate = df.format(r.getCreated());
			result.append("<H2>" + r.getTitle() + "</H2>");
			result.append("<H3>" + HeadlineDate + "</H3>");
			ArrayList<Row> rows = r.getRows();
			result.append("<table style=\"width:100vw\">");
			/*?..*/
}}
