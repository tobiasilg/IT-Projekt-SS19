package sharedShoppingList.shared.report;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * Diese Klasse ist die Spalte eines <code>Row</code>-Objektes. Durch die Implementierung des
 * <code>Serializable</code>-Interfaces können die <code>Column</code>-Objekte als Kopien z.B. 
 * vom Server an den Client übertragen werden.
 * 
 * 
 * @author Nico Weiler
 * @version 1.0
 */

public class Column implements IsSerializable {
	
    /**
     * Der Wert eines Spaltenobjekts entspricht dem Zelleneintrag einer Tabelle.
     * In dieser Realisierung handelt es sich um einen einfachen textuellen Wert. 
     */
    private String value = "";
	

    /**
     * Der Konstruktor erzwingt bei der Initialisierung einen Spaltenwert.
     * 
     * @param value 
     */
    public Column(String value) {
    	this.value = value;
    }



    /**Original-Kommentar
     * <p>
     * Sollte anhand von GWT-RPC eine serialisierbare Klasse transportiert werden,
     * besteht die Notwendigkeit dass diese Klasse einen No-Argument Konstruktor 
     * besitzen muss. Wenn kein expliziter Konstruktor besteht, enthält jede
     * Java-Klasse automatisch einen impliziten Default-Konstruktor, welcher dem 
     * No-Argument-Konstruktor entspricht.
     * </p>
     * <p>
     * Wenn jedoch eine Klasse mindestens einen explizit, implementierten Konstruktor 
     * besitzt, gelten auch nur die implementierten Konstruktoren und nicht mehr der
     * Default-Konstruktor. Wird jedoch in dieser Klasse trotzdem ein No-Argument-Konstruktor benötigt,
     * muss dieser explizit implementiert werden.
     * </p>
     * 
     * @see #Column(String)
     * @see SimpleParagraph#SimpleParagraph()
     */
    public Column() {
        
    }

    
    /**
     * Hier wird durch den Konstruktor, die Angabe eines Spaltenwertes, erzwungen.
     * 
     * @param value, Wert welcher durch das Column-Objekt dargestellt werden sollen
     * @see #Column()
     */  
    public void Column(String value) {
        
    	this.value = value;
    }

    
    /**
     * Hier wird der Wert der Spalte ausgelesen.
     * -->Getter
     * @return Eintrag wird zurückgegeben.
     */
    public String getValue() {
        
    	return value;
    }

    
    /**
     * Hier wird der aktuelle Wert der Spalte überschrieben
     * -->Setter
     * @param value
     */
    public void setValue(String value) {
       
    	this.value = value;
  
    }

    
    /**
     * Hier wir das <code>Column</code>-Objekt in einen String umgewandelt.
     * 
     * @see java.lang.Object
     */
    public String toString() {
       
    	return this.value;
    	
    }

}
