package sharedShoppingList.shared.report;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Nico Weiler
 */

public class HTMLReportWriter extends ReportWriter {
	
	/**
	   * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	   * <code>process...</code>-Methoden) belegt. Format: HTML-Text
	   */
	  private String reportText = "";
	  
	  /*
	   * Process Methoden Implementierung folgt hier...
	   */
	  
	  /*
	   * Die folgende Methode setzt den Report Text zurück
	   */
	  
	  public void resetReportText() {
		  this.reportText="";
	  }
	  
	  /*
	   * Paragraph Objekt wird zu HTML konvertiert
	   * @param Paragraph p
	   * @return Text in HTML Format
	   */
	  
	  public String paragraphToHTML(Paragraph p) {
		  if(p instanceof CompositeParagraph) {
			  return this.paragraphToHTML((CompositeParagraph)p);
		  }else {
			  return this.paragraphToHTML((SimpleParagraph)p);
		  }
	  }
	  
	  /*
	   * CompositeParagraph Objekt wird zu HTML konvertiert
	   * @param CompositeParagraph cp
	   * @return Text in HTML Format
	   */
	  
	  public String paragraphToHTML(CompositeParagraph cp) {
		  StringBuffer res= new StringBuffer();
		  
		  for(int i=0; i<cp.getSubParagraphsSize();i++) {
			  res.append(cp.getSubParagraphByIndex(i));
		  }
		  return res.toString();
	  }
	  
	  /*
	   * SimpleParagraph Objekt wird zu HTML konvertiert
	   * @param SimpleParagraph sp
	   * @return Text in HTML Format
	   */
	  
	  public String paragraphToHTML(SimpleParagraph sp) {
		  return sp.toString();
	  }
	

}
