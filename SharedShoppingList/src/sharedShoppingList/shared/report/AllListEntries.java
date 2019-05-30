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
    
    public AllListEntriesFromUserReport createAllListEntriesFromUserReport(User u, Date dateFrom, Date dateTill)
			throws IllegalArgumentException {

		if (this.getEditorAdministration() == null) {
			return null;
		}
		AllListEntriesFromUserReport result = new AllListEntriesFromUserReport();

		int i = 0;

		result.setCreateDate(new Date());

		Row headline = new Row();

		headline.addColumn(new Column("Listeneintrag"));
		headline.addColumn(new Column("Erstellungsdatum"));
		headline.addColumn(new Column("Änderungsdatum"));

		result.addRow(headline);

		Vector<ListEntry> listEntry = this.admin.getAllListEntriessOfUser(u);

		for (ListEntry le : listEntry) {
			Row listEntryRow = new Row();

			if (dateFrom == null) {
				i++;

				listEntryRow.addColumn(new Column(String.valueOf(le.getContent())));
				
                // Format-Modifikation
				String fullcdate = le.getCreateDate().toString();
				String[] cparts = fullcdate.split(" ");
				String cutcdate = cparts[0];
				listEntryRow.addColumn(new Column(String.valueOf(cutcdate)));
                
				// Format-Modifikation
				String fullmdate = le.getCreateDate().toString();
				String[] mparts = fullmdate.split(" ");
				String cutmdate = mparts[0];
                listEntryRow.addColumn(new Column(String.valueOf(cutmdate)));
				
                // Ausgabe der Resultate
                result.addRow(listEntryRow);
				result.setTitel("All Ihre Listeneinträge ");
				result.setAmount("Anzahl Ihrer Listeneinträge: " + i);

			} else {
				if (le.getModDate().after(dateFrom) && le.getModDate().before(dateTill)) {
					i++;
					listEntryRow.addColumn(new Column(String.valueOf(le.getContent())));
				    
                    // Format-Modifikation
					String fullcdate = le.getCreateDate().toString();
					String[] cparts = fullcdate.split(" ");
					String cutcdate = cparts[0];
					listEntryRow.addColumn(new Column(String.valueOf(cutcdate)));
					
                    // Format-Modifikation
					String fullmdate = le.getCreateDate().toString();
					String[] mparts = fullmdate.split(" ");
					String cutmdate = mparts[0];
					listEntryRow.addColumn(new Column(String.valueOf(cutmdate)));
					listEntryRow.addColumn(new Column(String.valueOf(this.admin.ListEntry(le).size())));

					result.addRow(listEntryRow);

					result.setTitel(" Anzahl Ihrer Listeneinträge in dem ausgewählten Zeitraum " + i);

				} else if (i >= 1) {
					result.setTitel(" Anzahl Ihrer Listeneinträge in dem ausgewählten Zeitraum " + i);

				} else {
					result.setTitel("Tut uns leid in dem Ausgewählten Zeitraum wurden keine Listeneinträge erstellt");
				}

			}

		}

		return result;

	}
	

}
