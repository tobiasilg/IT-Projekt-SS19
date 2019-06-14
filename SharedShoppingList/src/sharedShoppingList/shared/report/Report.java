package sharedShoppingList.shared.report;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;



/**
 * <p>
 * Dies ist die Basisklasse aller Reports. Die als <code>Serializable</code>
 * deklarierten Reports, können von dem Server an den Client gesendet werden. Das 
 * heißt, dass der Zugriff auf die Reports nach deren Bereitstellung, lokal auf dem 
 * Client erfolgt.
 * </p>
 * <p>
 * Ein Report besitzt eine Reihe von Standardelementen, welche mittels
 * Attributen modelliert und dort dokumentiert wird.
 * </p>
 * 
 * @see Report
 * @author Nico Weiler, Tobias Ilg
 * @version 1.0
 */

public abstract class Report implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	    
	    
	    /**
	     * Default Konstruktor.
	     */
	 public Report(){
	 }
	 
	 /**
	   * Jeder Bericht kann einen individuellen Titel besitzen.
	   */
	 private String title = "Report";

	 /**
	     * Hier wird das Erstellungsdatum des Berichts erstellt.
	     */
	    private Date creationDate;
	    
	    /**
	     * Kopfdaten des Berichts.
	     */
	    private Paragraph headerData = null;
	 
	 /**
	   * Auslesen des Berichtstitels.
	   * 
	   * @return Titeltext
	   */
	  public String getTitle() {
	    return this.title;
	  }

	  /**
	   * Setzen des Berichtstitels.
	   * 
	   * @param title Titeltext
	   */
	  public void setTitle(String title) {
	    this.title = title;
	  }

	  /**
	   * Auslesen des Erstellungsdatums.
	   * 
	   * @return Datum der Erstellung des Berichts
	   */
	  public Date getCreated() {
	    return this.creationDate;
	  }

	  /**
	   * Setzen des Erstellungsdatums. <b>Hinweis:</b> Der Aufruf dieser Methoden
	   * ist nicht unbedingt erforderlich, da jeder Report bei seiner Erstellung
	   * automatisch den aktuellen Zeitpunkt festhält.
	   * 
	   * @param created Zeitpunkt der Erstellung
	   */
	  public void setCreated(Date date) {
	    this.creationDate = date;
	  }
	  
	  /**
	   * Setzen der Kopfdaten.
	   * 
	   * @param headerData Text der Kopfdaten.
	   */
	  public void setHeaderData(Paragraph headerData) {
	    this.headerData = headerData;
	  }
	  
	  /**
	     * Hier wird das Erstellungsdatum in einen String gespeichert und gekürzt.
	     *
	     * @return Das zum Anzeigen formatierte Creationdate wird zurückgegeben.
	     */
	    public String getCreationDateString() {
	    	
	    	String creationDate = this.creationDate.toString().split("\\.")[0];
	    	return creationDate;
		}


}
