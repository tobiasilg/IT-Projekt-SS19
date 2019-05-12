package sharedShoppingList.shared.report;

import java.util.ArrayList;



/**
 * <p>
 * Ein einfacher Report, der neben den Informationen der Superklasse <code>
 * Report</code> eine Tabelle mit "Positionsdaten" aufweist. Die Tabelle greift
 * auf zwei Hilfsklassen namens <code>Row</code> und <code>Column</code> zurück.
 * </p>
 * <p>
 * In diesem Fall sind die Attribute die sich in der Zeile befinden: 
 * Artikel, Menge, Einheit, Händler und Einkäufer
 * </p>
 * @see Row
 * @see Column
 * @author Nico Weiler
 * @version 1.0
 */

public class SimpleReport extends Report{
	
private static final long serialVersionUID = 1L;

	
    /**
     * Default Konstruktor.
     */
    public SimpleReport() {
    }
	

    /*
     * Tabelle mit Positionsdaten. Die Tabelle wird zeilenweise in diesem
     * <code>Vector</code> abgelegt.
     */
    private ArrayList<Row> table = new ArrayList<Row>();


    /*
     * Eine Zeile hinzufügen.
     * 
     * @param row
     */
    public void addRow(Row row) {
       
    	this.table.add(row);
        
    }

    
    /**
     * Eine Zeile löschen
     * 
     * @param row
     */
    public void removeRow(Row row) {
       
    	this.table.remove(row);
        
    }

    /*
     * Ausgeben aller Positionsdaten
     * 
     * @return Die Rows werden zurückgegeben.
     */
    public ArrayList<Row> getRows() {
        return this.table;
    }
	

}
