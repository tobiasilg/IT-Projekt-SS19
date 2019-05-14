package sharedShoppingList.shared.report;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Diese Klasse ist die Zeile einer Tabelle von einem <code>SimpleReport</code>-Objekt.
 * Dabei implementieren die <code>Row</code>-Objekte das <code>Serializable<code/>-Interface.
 * Dadurch können diese als Kopie z.B. vom Server an den Client übertragen werden.
 * 
 * @see SimpleReport
 * @see Column
 * @author Nico Weiler
 * @version 1.0
 */

public class Row implements Serializable {
	
private static final long serialVersionUID = 1L;
    

	/**
	 * Default Konstruktor.
	 */
	public Row() {

	}

	/**
     * Auslesen der SerialVersionUID
     * 
     * @return  SerialVersionUID 
     */
    public long getSerialVersionUID() {
        return serialVersionUID;
    }	
 
    
    /**
     * Hier werden Speicherplätze für die Spalten einer Zeile 
     * zur Verfügung gestellt.
     */
    private ArrayList<Column> columns = new ArrayList<Column>();


    /**
     * Hier wird eine Spalte hinzugefügt.
     * @param c, Das Spaltenobjekt wird übergeben.
     */
    public void addColumn(Column c) {
      
    	this.columns.add(c);
    	   
    }


    /**
     * Hier wird eine entsprechend angegebene Spalte entfernt
     * 
     * @param c Die zu entfernende Spalte wird übergeben.
     */
    public void removeColumn(Column c) {
      
    	this.columns.remove(c);
    	
    }

    
    /**
     * Hier werden die ganzen Spalten ausgelesen.
     * 
     * @return <code>ArrayList</code>-Objekte mit sämtlichen Spalten wird zurückgegeben.
     */
    public ArrayList<Column> getColumns() {

        return this.columns;
    }

    
    /**
     * Hier wird die Anzahl der ganzen Spalten ausgelesen
     * 
     * @return Anzahl der Spalten wird zurückgegeben.
     */
    public int getColumnsSize() {
      
    	
        return this.columns.size();
    }

    
    /**
     * Hier wird ein einzelnes Spalten-Objekt anhand des Indexes ausgelesen.
     * 
     * @param i Der Index der Spalte wird übergeben.
     * @return Das gewünschte Spaltenobjekt wird zurückgegeben.
     */
    public Column getColumnByIndex(int i) {
       
    	return this.columns.get(i);
    	
    }

}
