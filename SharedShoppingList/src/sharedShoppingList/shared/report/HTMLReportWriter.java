package sharedShoppingList.shared.report;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.i18n.shared.DateTimeFormat;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Nico Weiler, Tobias Ilg
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
	  
	  
	/*
	 * Der für eine HTML Datei notwendige Header wird erstellt
	 * Header von: https://www.w3schools.com/html/default.asp
	 * 
	 */
	
	  public String getHeader() {
		
		StringBuffer result = new StringBuffer();

		result.append(
				"<!DOCTYPE html><html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"><title>SharedShoppingList REPORT GENERATOR</title></head><body>");
		
		return result.toString();

	  }
	  
	
	 /*
	 * Die HTML-Datei wird mit diesem Abschnitt abgeschlossen
	 */
	  
	 public String getFooter() {
		 
		return "</body></html>";
	 }
	 
	 
	/*
	 *An dieser Stelle wird der Report aus Header, dem eigentlichen Report-Teil und dem Footer zusammengesetzt. 
	 */
	public String getReportText() {
		
		return this.getHeader() + this.reportText + this.getFooter();
		
	}

	  
	  

	@Override
	public void process(AllListEntries r) {
	
		StringBuffer result = new StringBuffer();
		
		  DateTimeFormat df = DateTimeFormat.getFormat("dd.MM.yyyy");

			String HeadlineDate = df.format(r.getCreated());

			result.append("<H2>" + r.getTitle() + "</H2>");
			result.append("<H3>" + HeadlineDate + "</H3>");

			ArrayList<Row> rows = r.getRows();
			result.append("<table style=\"width:100vw\">");

			for (int i = 0; i < rows.size(); i++) {
				
				Row row = rows.get(i);
				result.append("<tr>");
				
				for (int k = 0; k < row.getColumnsSize(); k++) {
					if (i == 0) {
						result.append(
								"<td style=\"background:darkgrey; color: white; font-weight:bold\">" + row.getColumnByIndex(k) + "</td>");
					} else {
						
						if (i >= 1) {
							result.append("<td style=\"border-top:1px solid black\">" + row.getColumnByIndex(k) + "</td>");
						
						} else {
							result.append("<td valign=\"top\">" + row.getColumnByIndex(k) + "</td>");

						}
					}
				}
				result.append("</tr>");
			}

			result.append("</table>");

			/**
			 */
			this.reportText = result.toString();
		
	}

	@Override
	public void process(AllListEntriesByStore r) {
		
		StringBuffer result = new StringBuffer();
		
		  DateTimeFormat df = DateTimeFormat.getFormat("dd.MM.yyyy");

			String HeadlineDate = df.format(r.getCreated());

			result.append("<H2>" + r.getTitle() + "</H2>");
			result.append("<H3>" + HeadlineDate + "</H3>");

			ArrayList<Row> rows = r.getRows();
			result.append("<table style=\"width:100vw\">");

			for (int i = 0; i < rows.size(); i++) {
				
				Row row = rows.get(i);
				result.append("<tr>");
				
				for (int k = 0; k < row.getColumnsSize(); k++) {
					if (i == 0) {
						result.append(
								"<td style=\"background:darkgrey; color: white; font-weight:bold\">" + row.getColumnByIndex(k) + "</td>");
					} else {
						
						if (i >= 1) {
							result.append("<td style=\"border-top:1px solid black\">" + row.getColumnByIndex(k) + "</td>");
						
						} else {
							result.append("<td valign=\"top\">" + row.getColumnByIndex(k) + "</td>");

						}
					}
				}
				result.append("</tr>");
			}

			result.append("</table>");

			/**
			 */
			this.reportText = result.toString();
		
	}

	@Override
	public void process(AllListEntriesByPeriod r) {
		
	}

	@Override
	public void process(AllListEntriesByStoreAndPeriod r) {
		StringBuffer result = new StringBuffer();
		
		  DateTimeFormat df = DateTimeFormat.getFormat("dd.MM.yyyy");

			String HeadlineDate = df.format(r.getCreated());

			result.append("<H2>" + r.getTitle() + "</H2>");
			result.append("<H3>" + HeadlineDate + "</H3>");

			ArrayList<Row> rows = r.getRows();
			result.append("<table style=\"width:100vw\">");

			for (int i = 0; i < rows.size(); i++) {
				
				Row row = rows.get(i);
				result.append("<tr>");
				
				for (int k = 0; k < row.getColumnsSize(); k++) {
					if (i == 0) {
						result.append(
								"<td style=\"background:darkgrey; color: white; font-weight:bold\">" + row.getColumnByIndex(k) + "</td>");
					} else {
						
						if (i >= 1) {
							result.append("<td style=\"border-top:1px solid black\">" + row.getColumnByIndex(k) + "</td>");
						
						} else {
							result.append("<td valign=\"top\">" + row.getColumnByIndex(k) + "</td>");

						}
					}
				}
				result.append("</tr>");
			}

			result.append("</table>");

			/**
			 */
			this.reportText = result.toString();
		
	}
	

}
