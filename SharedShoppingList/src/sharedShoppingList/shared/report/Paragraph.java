package sharedShoppingList.shared.report;

import java.io.Serializable;


/**
 * Für die Reports sollte die Möglichkeit bestehen, die Texte strukturiert 
 * abzuspeichern. Später können die Texte durch den <code>ReportWriter</code>
 * in verschiedene Zielformate konvertiert werden. Die Nutzung der Klasse 
 * <code>String</code> ist hier nicht ausreichend, denn allein das Hinzufügen eines 
 * Zeilenumbruchs zur Markierung eines Absatzendes, Kenntnisse über das Zielformat
 * voraussetzt. Zwar würde für eine rein textuelle Darstellung ein doppeltes
 * "<code>\n</code>" ausreichen, jedoch müsste für den HTML-Zielformat der gesamte Absatz
 * in entsprechende Markup eingefügt werden.
 * <p>
 * <code>Paragraph</code> ist <code>Serializable</code>, so das Objekte dieser
 * Klasse durch das Netzwerk übertragbar sind.
 * 
 * @see Report
 * @author Nico Weiler
 * @version 1.0
 */

public class Paragraph implements Serializable {
	
	 private static final long serialVersionUID = 1L;

	    /**
	     * Default Konstruktor
	     */
	    public Paragraph() {
	    	
	    }

}
