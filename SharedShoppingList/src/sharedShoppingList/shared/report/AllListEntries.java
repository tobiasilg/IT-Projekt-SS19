package sharedShoppingList.shared.report;

import java.util.Vector;

import sharedShoppingList.shared.bo.ListEntry;

/**
 * In dieser Klasse wird ein <code>Report</code> erstellt, der alle gekauften Artikel eines Benutzers darstellt,
 * jedoch befinden sich hier keine weiteren Attribute- und Methoden-Implementierungen, denn diese befinden sich 
 * bereits in den Superklassen. Dennoch ist die Bestehung dieser Klasse relevant für die Deklarierung bestimmter 
 * Report-Typen um objektorientiert umgehen zu können. 
 * 
 * @see sharedShoppingList.shared.report.SimpleReport
 * @author Nico Weiler
 * @version 1.0
 */

public class AllListEntries extends SimpleReport {
	
	private static final long serialVersionUID= 1L;

    
    /**
     * Default Konstruktor.
     */
	
    public AllListEntries() {
    	
    }
	

}
