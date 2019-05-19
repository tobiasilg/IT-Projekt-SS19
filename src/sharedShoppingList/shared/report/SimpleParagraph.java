package sharedShoppingList.shared.report;

import java.io.Serializable;

/**
 * Diese Klasse stellt einzelne Absätze dar. Der Absatzinhalt wird als String
 * gespeichert. Der Anwender sollte in diesem Strinig keinerlei
 * Formatierungssymbole einfügen, da diese in der Regel zielformatspezifisch
 * sind.
 * 
 * @author Nico Weiler
 */

public class SimpleParagraph extends Paragraph implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	   
    public SimpleParagraph() {
    }
    

    /**
     * Diese Eigenschaft enthält den Inhalt eines Absatzes.
     */
    private String text = "";

    /**
     * <p>
     * Sollte anhand von GWT-RPC eine serialisierbare Klasse transportiert werden,
     * besteht die Notwendigkeit, dass diese Klasse einen No-Argument Konstruktor 
     * besitzen muss. Wenn kein expliziter Konstruktor besteht, enthält jede
     * Java-Klasse automatisch einen impliziten Default-Konstruktor, welcher dem 
     * No-Argument-Konstruktor entspricht.
     * </p>
     * <p>
     * Wenn jedoch eine Klasse mindestens ein explizit, implementierten Konstruktor 
     * besitzt, gelten auch nur die implementierten Konstruktoren und nicht mehr der 
     * Default-Konstruktor. Wird jedoch wie in dieser Klasse trotzdem ein No-Argument-Konstruktor benötigt,
     * muss dieser explizit implementiert werden.
     * </p>
     * 
     */
    

    /**
     * Mit diesem Konstruktor ist es möglich, bei einer instanziierung eines 
     * <code>SimpleParagraph</code>-Objektes den Inhalt direkt anzugeben. 
     * 
     * @see #SimpleParagraph()
     * @param text Der Text wird übergeben.
     */
    public SimpleParagraph(String text) {
       
    	this.text = text;
  	
    }

    
    /**
     * Hier wird der Inhalt ausgelesen.
     * 
     * @return Der Text wird zurückgegeben.
     */
    public String getText() {
        
        return this.text;
    }

    
    /**
     * Hier wird der Inhalt überschrieben.
     * 
     * @param text Der neue Inhalt des Absatzes wird zurückgegeben.
     */
    public void setText(String text) {
       
    	this.text = text;
       
    }

    
    /**
     * Hier wird der <code>SimpleParagraph</code>-Objekt in einen String umgewandelt.
     * 
     * @return Der Text wird zurückgegeben.
     */
    public String toString() {
       
        return this.text;
    }

}
